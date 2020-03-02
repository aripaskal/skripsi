package com.example.skripsi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.skripsi.Adapter.AdapterPenyakit;
import com.example.skripsi.Model.Penyakit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuPenyakit extends AppCompatActivity {

    ListView lvView;
    ArrayList<Penyakit> penyakits;
    AdapterPenyakit adapterPenyakit;
    ProgressDialog pdialog;
    SearchView searchView;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_penyakit);

        lvView = findViewById(R.id.listPenyakit);
        penyakits = new ArrayList<>();
        adapterPenyakit = new AdapterPenyakit(this, penyakits);
        lvView.setAdapter(adapterPenyakit);
        floatingActionButton = findViewById(R.id.fab1);
        loadJson();
        pindahData();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Daftar Penyakit");
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent(getApplicationContext(), TambahPenyakit.class);
                startActivity(e);
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
                adapterPenyakit.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    private void loadJson(){
        pdialog = new ProgressDialog(this);
        pdialog.setMessage("Loading...");
        showDialog();
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_DATA_PENY, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("volley", "response : " + response.toString());
                        hideDialog();
                        for (int i =0; i < response.length(); i++){
                            try {
                                JSONObject data = response.getJSONObject(i);
                                Penyakit penyakit =new Penyakit();
                                penyakit.setId(data.getInt("id"));
                                penyakit.setKodePenyakit(data.getString("kode_penyakit"));
                                penyakit.setNamaPenyakit(data.getString("nama_penyakit"));
                                penyakit.setProsedur(data.getString("prosedur"));
                                penyakit.setPeralatan(data.getString("peralatan"));
                                penyakit.setLama_perawatan(data.getString("lama_perawatan"));
                                penyakits.add(penyakit);
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        adapterPenyakit.notifyDataSetChanged();
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
        lvView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent c = new Intent(getApplicationContext(),UpdatePenyakit.class);
                c.putExtra(UpdatePenyakit.EXTRA, penyakits.get(position));
                startActivity(c);
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
