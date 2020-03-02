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
import com.example.skripsi.Model.Penyakit;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdatePenyakit extends AppCompatActivity {
    private EditText edtid, edtkode, edtpenyakit, edtprosedur, edtperalatan, edtlama;
    private Button btnUbah, btnHapus, btnKembali;
    public static final String EXTRA = "extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_penyakit);

        edtid = findViewById(R.id.ID_penyakit);
        edtkode = findViewById(R.id.kode_penyakit1);
        edtpenyakit = findViewById(R.id.nama_penyakit1);
        edtprosedur = findViewById(R.id.prosedur1);
        edtperalatan = findViewById(R.id.peralatan1);
        edtlama = findViewById(R.id.lama1);
        btnHapus = findViewById(R.id.btn_Hapus1);
        btnKembali = findViewById(R.id.btn_keluar);
        btnUbah = findViewById(R.id.btn_ubah1);
        //adapter = ArrayAdapter.createFromResource(this, R.array.keterangan, android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buttonHapus();
        buttonUbah();
        Keluar();

        Penyakit penyakit =getIntent().getParcelableExtra(EXTRA);

        edtid.setText(String.valueOf(penyakit.getId()));
        edtpenyakit.setText(penyakit.getNamaPenyakit());
        edtkode.setText(penyakit.getKodePenyakit());
        edtprosedur.setText(penyakit.getProsedur());
        edtperalatan.setText(penyakit.getPeralatan());
        edtlama.setText(penyakit.getLama_perawatan());
        getSupportActionBar().setTitle("Penyakit");
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void buttonUbah(){
    btnUbah.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ubahData();
        }
    });
    }

    private void buttonHapus(){
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapusData();
            }
        });
    }

    private void ubahData(){
        StringRequest updateReq = new StringRequest(Request.Method.POST, ServerApi.URL_UPD_PENY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(UpdatePenyakit.this, "Berhasil", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdatePenyakit.this, "Gagal", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("id",edtid.getText().toString());
                map.put("kode_penyakit",edtkode.getText().toString());
                map.put("nama_penyakit",edtpenyakit.getText().toString());
                map.put("prosedur",edtprosedur.getText().toString());
                map.put("peralatan",edtperalatan.getText().toString());
                map.put("lama_perawatan",edtlama.getText().toString());
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(updateReq);
    }

    private void hapusData(){
        StringRequest delReq = new StringRequest(Request.Method.POST,ServerApi.URL_DEL_PENY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("volley","response : " + response.toString());
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(UpdatePenyakit.this,"Data Dihapus", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("volley", "error : " + error.getMessage());
                        Toast.makeText(UpdatePenyakit.this, "Gagal Menghapus Data", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id",edtid.getText().toString());
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(delReq);
    }

    public void Keluar(){
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a =new Intent(getApplicationContext(), MenuPenyakit.class);
                startActivity(a);
            }
        });
    }
}
