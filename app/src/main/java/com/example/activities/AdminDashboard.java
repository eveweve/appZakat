package com.example.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.models.Zakat;
import com.example.zakatapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminDashboard extends AppCompatActivity {
    private ListView adminListView;
    private DatabaseReference zakatRef;
    private ArrayList<String> adminZakatData;
    private ArrayAdapter<String> adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        zakatRef = FirebaseDatabase.getInstance().getReference("zakat");
        adminListView = findViewById(R.id.adminListView);

        adminZakatData = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, adminZakatData);
        adminListView.setAdapter(adapter);

        loadAllZakatData();
    }

    private void loadAllZakatData(){
        zakatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adminZakatData.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()){
                    for (DataSnapshot zakatSnapshot : userSnapshot.getChildren()){
                        Zakat zakat = zakatSnapshot.getValue(Zakat.class);
                        adminZakatData.add("UserID : " + userSnapshot.getKey()+
                                "\nTipe: " + zakat.getZakatType()+
                                "\nJumlah: " + zakat.getAmount()+
                                "\nTanggal" + zakat.getDate());
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminDashboard.this, "Gagal memuat data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
