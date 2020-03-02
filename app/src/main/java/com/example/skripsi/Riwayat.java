package com.example.skripsi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.skripsi.Adapter.AdapterRiwayat;
import com.example.skripsi.Model.MRiwayat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Riwayat extends AppCompatActivity {
    ListView lvriwayat;
    ArrayList<MRiwayat> riwayats;
    AdapterRiwayat adapterRiwayat;
    ProgressDialog progressDialog;
    SearchView searchView;
    TextView pasien, penyakit, tanggal, umur, jk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);

        lvriwayat = findViewById(R.id.riwayat);
        riwayats = new ArrayList<>();
        adapterRiwayat = new AdapterRiwayat(this, riwayats);
        lvriwayat.setAdapter(adapterRiwayat);
        loadJson();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Riwayat Diagnosa");
        lvriwayat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                output(riwayats.get(position).getNama(), riwayats.get(position).getPenyakitku(), riwayats.get(position).getTgl(), riwayats.get(position).getUmur(), riwayats.get(position).getJk());
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
            public boolean onQueryTextSubmit(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterRiwayat.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    private void output(String txt1, String txt2, String txt3, String txt4, String txt5){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view =  inflater.inflate(R.layout.hasilriwayat,null);
        builder.setView(view);
        builder.setCancelable(true);
        pasien = view.findViewById(R.id.pasien);
        penyakit = view.findViewById(R.id.penyakits);
        tanggal = view.findViewById(R.id.tgl);
        umur = view.findViewById(R.id.riwayat_umur);
        jk = view.findViewById(R.id.riwayat_jk);
        pasien.setText(txt1);
        penyakit.setText(txt2);
        tanggal.setText(txt3);
        umur.setText(txt4);
        jk.setText(txt5);
        builder.setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        builder.show();
    }

    private void loadJson(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        showDialog();
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_DATA_RIW, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("volley", "response : " + response.toString());
                        hideDialog();
                        for (int i =0; i < response.length(); i++){
                            try {
                                JSONObject data = response.getJSONObject(i);
                                MRiwayat mRiwayat = new MRiwayat();
                                mRiwayat.setNama(data.getString("nama"));
                                mRiwayat.setPenyakitku(data.getString("penyakit"));
                                mRiwayat.setTgl(data.getString("tgl_konsultasi"));
                                mRiwayat.setUmur(data.getString("Umur"));
                                mRiwayat.setJk(data.getString("JK"));
                                riwayats.add(mRiwayat);
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        adapterRiwayat.notifyDataSetChanged();
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


    private void showDialog(){
        if (!progressDialog.isShowing())
            progressDialog.show();
    }
    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
