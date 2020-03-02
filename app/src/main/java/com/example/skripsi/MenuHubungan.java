package com.example.skripsi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.skripsi.Adapter.AdapterHubungan;
import com.example.skripsi.Model.Hubungan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuHubungan extends AppCompatActivity {
    ListView listHubungan;
    ArrayList<Hubungan> hubungans;
    AdapterHubungan adapterHubungan;
    ProgressDialog pdialog;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_hubungan);

        listHubungan = findViewById(R.id.lv_hubungan);
        hubungans = new ArrayList<>();
        adapterHubungan = new AdapterHubungan(this, hubungans);
        listHubungan.setAdapter(adapterHubungan);
        floatingActionButton = findViewById(R.id.fab2);
        loadJson();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Daftar Rule");
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent g = new Intent(getApplicationContext(), TambahHubungan.class);
                startActivity(g);
            }
        });
    }

    private void loadJson(){
        pdialog = new ProgressDialog(this);
        pdialog.setMessage("Loading...");
        showDialog();
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_DATA_HUB, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("volley", "response : " + response.toString());
                        hideDialog();
                        for (int i =0; i < response.length(); i++){
                            try {
                                JSONObject data = response.getJSONObject(i);
                                Hubungan hubungan = new Hubungan();
                                hubungan.setgejala(data.getString("n_gejala"));
                                hubungan.setpenyakit(data.getString("n_penyakit"));
                                hubungans.add(hubungan);
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        adapterHubungan.notifyDataSetChanged();
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
        if (!pdialog.isShowing())
            pdialog.show();
    }
    private void hideDialog() {
        if (pdialog.isShowing())
            pdialog.dismiss();
    }
}
