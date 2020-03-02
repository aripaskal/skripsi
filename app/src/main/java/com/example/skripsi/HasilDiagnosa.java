package com.example.skripsi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HasilDiagnosa extends AppCompatActivity{
    TextView txtgejala, txtpenyakit, txtpersentasi, txtprosedur, txtperalatan, txtlama;
    EditText nama, umur;
    Spinner JK;
    FloatingActionButton floatingActionButton;
    List<String> spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_diagnosa);
        txtgejala = findViewById(R.id.hasil_gejala);
        txtpenyakit = findViewById(R.id.hasil_penyakit);
        txtpersentasi =findViewById(R.id.hasil_keparahan);
        txtperalatan = findViewById(R.id.hasil_peralatan);
        txtprosedur = findViewById(R.id.hasil_prosedur);
        txtlama = findViewById(R.id.hasil_lama);
        floatingActionButton = findViewById(R.id.fab3);
        Intent intent = getIntent();
        String dipilih = intent.getStringExtra("GEJ");
        String nama = intent.getStringExtra("PENY");
        String prosedur = intent.getStringExtra("PRO");
        String nilai = intent.getStringExtra("PAR");
        String peralatan = intent.getStringExtra("PER");
        String lama = intent.getStringExtra("LAM");
        txtgejala.setText(dipilih);
        txtpenyakit.setText(nama);
        txtprosedur.setText(prosedur);
        txtpersentasi.setText(nilai);
        txtperalatan.setText(peralatan);
        txtlama.setText(lama);
        getSupportActionBar().setTitle("Detail Penyakit");
        getSupportActionBar().setHomeButtonEnabled(true);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    dataDiri();
            }
        });
    }

    private void dataDiri(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.data_diri, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        umur = dialogView.findViewById(R.id.umur);
        nama = dialogView.findViewById(R.id.nama);
        JK = dialogView.findViewById(R.id.jk);
        spinner = new ArrayList<String>();
        spinner.add("Laki-Laki");
        spinner.add("Perempuan");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        JK.setAdapter(adapter);
        JK.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dialog.setPositiveButton("Diagnosa Kembali", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                kirimData();
                Intent intent = new Intent(getApplicationContext(), Diagnosa.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }

    private void kirimData(){
        StringRequest sendData = new StringRequest(Request.Method.POST, ServerApi.URL_INSERT_RIW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HasilDiagnosa.this, "Gagal", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("nama", nama.getText().toString());
                map.put("penyakit", txtpenyakit.getText().toString());
                map.put("Umur", umur.getText().toString());
                map.put("JK", JK.getSelectedItem().toString());
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sendData);
    }
}
