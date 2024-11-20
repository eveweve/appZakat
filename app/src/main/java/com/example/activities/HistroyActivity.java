package com.example.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.models.Zakat;
import com.example.zakatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistroyActivity extends AppCompatActivity {
    private ListView historyListView;
    private DatabaseReference zakatRef;
    private FirebaseAuth mAuth;
    private ArrayList<String> zakatHistory;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mAuth = FirebaseAuth.getInstance();
        zakatRef = FirebaseDatabase.getInstance().getReference("zakat");
        historyListView = findViewById(R.id.historyListView);

        zakatHistory = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, zakatHistory);
        historyListView.setAdapter(adapter);;

        loadZakatHistory();
    }

    private void loadZakatHistory(){
        String userId = mAuth.getCurrentUser().getUid();

        zakatRef.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                zakatHistory.clear();
                for (DataSnapshot zakatSnapshot : snapshot.getChildren()){
                    Zakat zakat = zakatSnapshot.getValue(Zakat.class);
                    zakatHistory.add("tipe: " + zakat.getZakatType()+"\n jumlah: " + zakat.getAmount()+
                    "\nTanggal:" + zakat.getDate());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HistroyActivity.this, "Gagal memuat data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
