package com.example.skripsi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

public class DashboardAdmin extends AppCompatActivity {
    GridLayout mainGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);

        mainGrid= findViewById(R.id.grid);
        setSingleEvent();

        getSupportActionBar().setTitle("Menu Admin");

    }

    private void setSingleEvent(){
        for (int i = 0; i<mainGrid.getChildCount();i++){
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalI == 0){
                        Intent b = new Intent(getApplicationContext(), MenuGejala.class);
                        startActivity(b);
                    }else if (finalI == 1){
                        Intent d = new Intent(getApplicationContext(), MenuPenyakit.class);
                        startActivity(d);
                    }else if (finalI == 2){
                        Intent f = new Intent(getApplicationContext(), MenuHubungan.class);
                        startActivity(f);
                    }else if (finalI == 3) {
                        Intent h = new Intent(getApplicationContext(), Riwayat.class);
                        startActivity(h);
                    }else if (finalI == 4){
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }else {
                        Toast.makeText(getApplicationContext(), "Menu Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
