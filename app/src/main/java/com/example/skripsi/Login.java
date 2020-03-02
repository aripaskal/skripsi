package com.example.skripsi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText edUser, edPass;
    Button btnMasuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edPass = findViewById(R.id.password);
        edUser = findViewById(R.id.username);
        btnMasuk = findViewById(R.id.btn_masuk);

        getSupportActionBar().setTitle("Login Admin");

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = edUser.getText().toString();
                String pass = edPass.getText().toString();

                if (user.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Username Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                } else if (pass.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Password Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }
                else if (user.equals("admin")&& pass.equals("admin")){
                    Toast.makeText(getApplicationContext(), "Anda Berhasil Masuk", Toast.LENGTH_SHORT).show();
                    Intent a = new Intent(getApplicationContext(), DashboardAdmin.class);
                    startActivity(a);
                }else {
                    Toast.makeText(getApplicationContext(), "Username Atau Password Anda Salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
