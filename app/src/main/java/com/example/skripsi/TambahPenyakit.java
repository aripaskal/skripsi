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

public class TambahPenyakit extends AppCompatActivity{
    EditText edtKode, edtPenyakit, edtID, edttindakan, edtperawatan, edtperalatan;
    Button btnTambah, btnKembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_penyakit);

        edtKode = findViewById(R.id.kode_penyakit);
        edtPenyakit = findViewById(R.id.nama_penyakit);
        edtID = findViewById(R.id.id_penyakit);
        edttindakan = findViewById(R.id.prosedur);
        edtperawatan = findViewById(R.id.lama);
        edtperalatan = findViewById(R.id.peralatan);
        btnTambah = findViewById(R.id.btn_tambah2);
        btnKembali = findViewById(R.id.btn_kembali2);
        //adptr = ArrayAdapter.createFromResource(this, R.array.keterangan, android.R.layout.simple_spinner_item);
        //adptr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buttonTambah();
        Kembali();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Tambah Penyakit");
    }

    private void buttonTambah(){
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id  = edtID.getText().toString();
                String kode = edtKode.getText().toString();
                String nama = edtPenyakit.getText().toString();
                String prosedur = edttindakan.getText().toString();
                String peralatan = edtperalatan.getText().toString();
                String lama = edtperawatan.getText().toString();

                if (id.isEmpty()){
                    Toast.makeText(TambahPenyakit.this, "Masukkan Id Penyakit", Toast.LENGTH_SHORT).show();
                }else if(kode.isEmpty()){
                    Toast.makeText(TambahPenyakit.this, "Masukkan Kode Penyakit", Toast.LENGTH_SHORT).show();
                }else if (nama.isEmpty()){
                    Toast.makeText(TambahPenyakit.this, "Masukkan Nama Penyakit", Toast.LENGTH_SHORT).show();
                }else if(prosedur.isEmpty()){
                    Toast.makeText(TambahPenyakit.this, "Masukkan Prosedur Tindakan Dokter Gigi", Toast.LENGTH_SHORT).show();
                }else if (peralatan.isEmpty()){
                    Toast.makeText(TambahPenyakit.this, "Masukkan Peeralatan dan Bahan/Obat", Toast.LENGTH_SHORT).show();
                }else if (lama.isEmpty()){
                    Toast.makeText(TambahPenyakit.this, "Masukkan Lama Perawatan", Toast.LENGTH_SHORT).show();
                }else {
                    addPenyakit();
                }
            }
        });
    }

    private void addPenyakit(){
        StringRequest sendData = new StringRequest(Request.Method.POST, ServerApi.URL_INSERT_PENY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(TambahPenyakit.this, "Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TambahPenyakit.this, "Gagal Disimpan", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", edtID.getText().toString());
                map.put("kode_penyakit", edtKode.getText().toString());
                map.put("nama_penyakit", edtPenyakit.getText().toString());
                map.put("prosedur", edttindakan.getText().toString());
                map.put("peralatan", edtperalatan.getText().toString());
                map.put("lama_perawatan", edtperawatan.getText().toString());
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(sendData);
    }

    public void Kembali(){
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getApplicationContext(), MenuPenyakit.class);
                startActivity(a);
            }
        });
    }

}
