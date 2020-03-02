package com.example.skripsi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TambahGejala extends AppCompatActivity {
    EditText namaGejala, kodeGejala, idGejala;
    Button btnTambah, btnKembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_gejala);
        idGejala = findViewById(R.id.id_gejala);
        namaGejala = findViewById(R.id.nama_gejala);
        kodeGejala = findViewById(R.id.kode_gejala);
        btnTambah = findViewById(R.id.btn_tambah);
        btnKembali = findViewById(R.id.btn_kembali);
        Kembali();
        buttonTambah();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Tambah Gejala");
    }

    public void Kembali(){
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getApplicationContext(), MenuGejala.class);
                startActivity(a);
            }
        });
    }

    private void buttonTambah(){
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idGejala.getText().toString();
                String nama = namaGejala.getText().toString();
                String kode = kodeGejala.getText().toString();
                if (id.isEmpty()) {
                    Toast.makeText(TambahGejala.this, "Id Gejala Kosong", Toast.LENGTH_SHORT).show();
                }else if(kode.isEmpty()){
                    Toast.makeText(TambahGejala.this, "Kode Gejala Kosong", Toast.LENGTH_SHORT).show();
                }else if(nama.isEmpty()){
                    Toast.makeText(TambahGejala.this, "Nama Gejala Kosong", Toast.LENGTH_SHORT).show();
                }else {
                    simpanData();
                }
            }
        });
    }

    private void simpanData(){
        StringRequest sendData = new StringRequest(Request.Method.POST, ServerApi.URL_INSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(TambahGejala.this, "Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TambahGejala.this, "Gagal Disimpan", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", idGejala.getText().toString());
                map.put("kode_gejala", kodeGejala.getText().toString());
                map.put("nama_gejala", namaGejala.getText().toString());
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sendData);
    }

}
