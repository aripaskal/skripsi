package com.example.skripsi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnlogin, btndiagnosa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnlogin = findViewById(R.id.btn_login);
        btndiagnosa = findViewById(R.id.btn_diagnosa);
        pindah1();
        pindah2();
        getSupportActionBar().setTitle("Sistem Pakar Gigi dan Mulut");

    }

    private void pindah1(){
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
            }
        });
    }

    private void pindah2(){
        btndiagnosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a= new Intent(MainActivity.this, Diagnosa.class);
                startActivity(a);
            }
        });
    }
}

