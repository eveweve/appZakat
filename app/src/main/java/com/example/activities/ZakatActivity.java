package com.example.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.models.Zakat;
import com.example.zakatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class ZakatActivity extends AppCompatActivity {
    private Spinner zakatTypeSpinner;
    private EditText amountField;
    private Button submitButton;
    private FirebaseAuth mAuth;
    private DatabaseReference zakatRef;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakat);

        mAuth = FirebaseAuth.getInstance();
        zakatRef = FirebaseDatabase.getInstance().getReference("zakat");

        zakatTypeSpinner = findViewById(R.id.zakatTypeSpinner);
        amountField = findViewById(R.id.amountField);
        submitButton = findViewById(R.id.submitButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.zakat_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zakatTypeSpinner.setAdapter(adapter);

        submitButton.setOnClickListener(v -> processZakat());
    }

    private  void processZakat(){
        String zakatType = zakatTypeSpinner.getSelectedItem().toString();
        String amountStr = amountField.getText().toString().trim();

        if (amountStr.isEmpty()){
            Toast.makeText(this, "Masukkan jumlah zakat", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountStr);
        if (amount < 10){
            Toast.makeText(this, "jumlah zakat minimal adalah 10", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = mAuth.getCurrentUser().getUid();
        String date = java.text.DateFormat.getDateInstance().format(new Date());

        Zakat zakat = new Zakat(zakatType, amount, date);

        zakatRef.child(userId).push().setValue(zakat).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(this, "zakat berhasil disimpan", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(this, "gagal menyimpan zakat : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
