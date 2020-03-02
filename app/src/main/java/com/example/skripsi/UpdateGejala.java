package com.example.skripsi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.skripsi.Model.Gejala;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateGejala extends AppCompatActivity{
    private EditText edtId, edtGejala, edtKode;
    private Button btnUbah, btnKembali, btnHapus;
    public static final String extra = "extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_gejala);

        edtGejala = findViewById(R.id.nama_gejala1);
        edtId = findViewById(R.id.ID_gejala);
        edtKode = findViewById(R.id.kode_gejala1);
        btnUbah = findViewById(R.id.btn_ubah);
        btnKembali = findViewById(R.id.btn_kembali1);
        btnHapus =findViewById(R.id.btn_Hapus);
        //adapter1 = ArrayAdapter.createFromResource(this, R.array.kondisi, android.R.layout.simple_spinner_item);
        //adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Gejala gejala = getIntent().getParcelableExtra(extra);

        edtKode.setText(gejala.getKode());
        edtId.setText(String.valueOf(gejala.getId()));
        edtGejala.setText(gejala.getNama());
        btnUpdate();
        btnHapusData();
        kembali();
        getSupportActionBar().setTitle("Ubah Gejala");
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    private void btnHapusData(){
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapusData();
            }
        });
    }

    private void btnUpdate(){
        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
    }

    private void updateData(){
        StringRequest updateReq = new StringRequest(Request.Method.POST, ServerApi.URL_UPD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(UpdateGejala.this, "Berhasil" , Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateGejala.this, "Gagal", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("id",edtId.getText().toString());
                map.put("kode_gejala",edtKode.getText().toString());
                map.put("nama_gejala",edtGejala.getText().toString());
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(updateReq);
    }

    private void hapusData(){
        StringRequest delReq = new StringRequest(Request.Method.POST,ServerApi.URL_DEL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("volley","response : " + response.toString());
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(UpdateGejala.this,"Data Dihapus", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("volley", "error : " + error.getMessage());
                        Toast.makeText(UpdateGejala.this, "Gagal Menghapus Data", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id",edtId.getText().toString());
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(delReq);
    }

    public void kembali(){
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getApplicationContext(), MenuGejala.class);
                startActivity(a);
            }
        });
    }
}
