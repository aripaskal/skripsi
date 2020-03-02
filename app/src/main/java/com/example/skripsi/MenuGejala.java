package com.example.skripsi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.skripsi.Adapter.AdapterGejala;
import com.example.skripsi.Model.Gejala;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuGejala extends AppCompatActivity {
ListView listGejala;
ArrayList<Gejala> arrayList;
AdapterGejala adapterGejala;
ProgressDialog pdialog;
SearchView searchView;
FloatingActionButton floatingActionButton;
EditText edtid, edtkode, edtgej;
Button btnhapus, btnubah, btnkeluar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu_gejala);
        listGejala = findViewById(R.id.listgejala);
        arrayList = new ArrayList<>();
        adapterGejala = new AdapterGejala(this, arrayList);
        listGejala.setAdapter(adapterGejala);
        floatingActionButton = findViewById(R.id.fab);
        pindahData();
        loadJson();
        getSupportActionBar().setTitle("Daftar Gejala");
        getSupportActionBar().setHomeButtonEnabled(true);//Memunculkan button kembali
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getApplicationContext(), TambahGejala.class);
                startActivity(a);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.popup, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                    adapterGejala.getFilter().filter(newText);

                return true;
            }
        });

        return true;
    }
    private void modifikasi(int txt1, String txt2, String txt3){
    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
    LayoutInflater inflater = getLayoutInflater();
    View view =  inflater.inflate(R.layout.ubahhapus,null);
        builder.setView(view);
        builder.setCancelable(true);
    edtid= view.findViewById(R.id.idd);
    edtkode = view.findViewById(R.id.kode__gejala);
    edtgej = view.findViewById(R.id.nama__gejala);
    btnhapus = view.findViewById(R.id.btn__hapus);
    btnubah= view.findViewById(R.id.btn__ubah);
    btnkeluar = view.findViewById(R.id.btn__keluar);
        edtid.setText(String.valueOf(txt1));
        edtgej.setText(txt2);
        edtkode.setText(txt3);
     btnhapus.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
           hapusData();
           Intent intent = new Intent(getApplicationContext(), MenuGejala.class);
           startActivity(intent);
         }
     });
     btnubah.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             updateData();
             Intent a = new Intent(getApplicationContext(), MenuGejala.class);
             startActivity(a);
         }
     });
     btnkeluar.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent b = new Intent(getApplicationContext(), MenuGejala.class);
             startActivity(b);
         }
     });
        builder.show();
}
    private void updateData(){
    StringRequest updateReq = new StringRequest(Request.Method.POST, ServerApi.URL_UPD,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject res = new JSONObject(response);
                        Toast.makeText(MenuGejala.this, "Berhasil" , Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MenuGejala.this, "Gagal", Toast.LENGTH_SHORT).show();
                }
            }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> map = new HashMap<>();
            map.put("id",edtid.getText().toString());
            map.put("kode_gejala",edtkode.getText().toString());
            map.put("nama_gejala",edtgej.getText().toString());
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
                            Toast.makeText(MenuGejala.this,"Data Dihapus", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("volley", "error : " + error.getMessage());
                        Toast.makeText(MenuGejala.this, "Gagal Menghapus Data", Toast.LENGTH_SHORT).show();
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


    private void loadJson(){
        pdialog = new ProgressDialog(this);
        pdialog.setMessage("Loading...");
        showDialog();
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_DATA, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("volley", "response : " + response.toString());
                        hideDialog();
                        for (int i =0; i < response.length(); i++){
                            try {
                                JSONObject data = response.getJSONObject(i);
                                Gejala gejala = new Gejala();
                                gejala.setId(data.getInt("id"));
                                gejala.setKode(data.getString("kode_gejala"));
                                gejala.setNama(data.getString("nama_gejala"));
                                arrayList.add(gejala);
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        adapterGejala.notifyDataSetChanged();
                    }
                },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            Log.d("volley", "error: " + error.getMessage());
            hideDialog();
        }
    });
        AppController.getInstance().addToRequestQueue(reqData);
    }

    public void pindahData(){
        listGejala.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               modifikasi(arrayList.get(position).getId(), arrayList.get(position).getNama(), arrayList.get(position).getKode());
            }
        });
    }

    private void showDialog(){
        if (!pdialog.isShowing())
            pdialog.show();
    }
    private void hideDialog() {
        if (pdialog.isShowing())
            pdialog.dismiss();
    }

}
