package com.example.skripsi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TambahHubungan extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText idhubungan, bbt, edtpenyakit, edtgejala;
    TextView idgejala, idpenyakit;
    Button btntambah, btnkembali;
    Spinner spinner, spn1;
    ArrayList<String> gejalas, penyakits;
    JSONArray result,  result1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_hubungan);
        idhubungan = findViewById(R.id.id_hubungan);
        idgejala = findViewById(R.id.tv_gejala);
        edtgejala = findViewById(R.id.n_gejala);
        edtpenyakit = findViewById(R.id.n_penyakit);
        idpenyakit = findViewById(R.id.tv_penyakit);
        bbt = findViewById(R.id.bobot);
        btntambah = findViewById(R.id.btn_tambah1);
        btnkembali = findViewById(R.id.btn_kembali1);
        gejalas =new ArrayList<String>();
        penyakits = new ArrayList<String>();
        spinner = findViewById(R.id.id_gejalas);
        spn1 = findViewById(R.id.idpenyakits);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Tambah Hubungan");
        tambah();
        keluar();
        spinner.setOnItemSelectedListener(this);
        spn1.setOnItemSelectedListener(this);
        //get();
        getData();

    }

    private void tambah(){
        btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idhubungan.getText().toString();
                String nama = spinner.getSelectedItem().toString();
                String namap = spn1.getSelectedItem().toString();
                String bobot = bbt.getText().toString();

                if (id.isEmpty()){
                    Toast.makeText(TambahHubungan.this, "Masukkan id Hubungan", Toast.LENGTH_SHORT).show();
                }else if(nama.isEmpty()){
                    Toast.makeText(TambahHubungan.this, "Anda belum memilih gejala", Toast.LENGTH_SHORT).show();
                }else if(namap.isEmpty()){
                    Toast.makeText(TambahHubungan.this, "Anda belum memilih penyakit", Toast.LENGTH_SHORT).show();
                }else if(bobot.isEmpty()){
                    Toast.makeText(TambahHubungan.this, "Masukkan Nilai Bobot", Toast.LENGTH_SHORT).show();
                }else {
                    tambahData();
                }
            }
        });
    }

    private void keluar(){
        btnkembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getApplicationContext(), MenuHubungan.class);
                startActivity(a);
            }
        });
    }

    /*private void get(){
        StringRequest stringReq = new StringRequest(Request.Method.GET, ServerApi.URL_DATA_PENY_1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject v = null;
                        Log.d("volley", "response : " + response.toString());
                        try {
                            v = new JSONObject(response);
                            result1 = v.getJSONArray("penyakit");
                            getPenyakit(result1);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.add(stringReq);
    }*/

    private void getPenyakit(JSONArray v){
        for (int i = 0; i<v.length(); i++){
            try {
                JSONObject jsonObject1 = v.getJSONObject(i);
                penyakits.add(jsonObject1.getString("nama_penyakit"));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        spn1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, penyakits));
    }

    private int getI(int position1){
        int id1 = 0;
        try {
            JSONObject jsonObject1 = result.getJSONObject(position1);

            id1 = jsonObject1.getInt("idpenyakit");
        }catch (JSONException e){
            e.printStackTrace();
        }
        return id1;
    }

    /*private String getNama(int posistion2){
        String nama = "";
        try {
            JSONObject jsonObject = result1.getJSONObject(posistion2);

            nama = jsonObject.getString("nama_penyakit");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return nama;
    }*/

   private void getData(){
       StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerApi.URL_DATA_GEJ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                            try {
                                j = new JSONObject(response);
                                result = j.getJSONArray("result");
                                getGejala(result);
                                getPenyakit(result);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void getGejala(JSONArray j){
        for (int i = 0; i<j.length(); i++){
            try {
                JSONObject jsonObject = j.getJSONObject(i);
                gejalas.add(jsonObject.getString("nama_gejala"));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, gejalas));
    }

    private int getID(int position){
        int id = 0;
        try {
            JSONObject jsonObject = result.getJSONObject(position);

            id = jsonObject.getInt("idgejala");
        }catch (JSONException e){
            e.printStackTrace();
        }
        return id;
    }

    /*private String getGej(int position3){
        String gej = "";
        try {
            JSONObject jsonObject = result.getJSONObject(position3);

            gej = jsonObject.getString("nama_gejala");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return gej;
    }*/

    private void tambahData() {
        StringRequest sendData = new StringRequest(Request.Method.POST, ServerApi.URL_INSERT_HUB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("volley", "response : " + response.toString());
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(TambahHubungan.this, "Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TambahHubungan.this, "Gagal Disimpan", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", idhubungan.getText().toString());
                map.put("idgejala", edtgejala.getText().toString());
                map.put("n_gejala", spinner.getSelectedItem().toString());
                map.put("idpenyakit", edtpenyakit.getText().toString());
                map.put("n_penyakit", spn1.getSelectedItem().toString());
                map.put("bobot", bbt.getText().toString());
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sendData);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.id_gejalas:{
                edtgejala.setText(String.valueOf(getID(position)));
                //edtgejala.setText(getGej(position));
            }
            case R.id.idpenyakits:{
                edtpenyakit.setText(String.valueOf(getI(position)));
                //edtpenyakit.setText(getNama(position));
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
