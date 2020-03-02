package com.example.skripsi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.skripsi.Adapter.AdapterDiagnosa;
import com.example.skripsi.Model.Data;
import com.example.skripsi.Model.MGejala;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Diagnosa extends AppCompatActivity {

    ListView LvDiagnosa;
    ArrayList<MGejala> gejalas, penyakits;
    AdapterDiagnosa adpt;
    Button btnDiagnosa, btnKembali;
    String dipilihl, dipilih2;
    TextView penyakit1, keparahan1, gejala, prosedur, peralatan, lama;
    EditText edtNama;
    ProgressDialog pdialog;
    List<Data> dataArray;
    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosa);

        LvDiagnosa = findViewById(R.id.lv_diagnosa);
        gejalas = new ArrayList<>();
        penyakits = new ArrayList<>();
        adpt = new AdapterDiagnosa(this, gejalas);
        LvDiagnosa.setAdapter(adpt);
        btnDiagnosa = findViewById(R.id.btn_digns);
        btnKembali = findViewById(R.id.btn_kemb);
        dataArray = new ArrayList<Data>();
        mQueue = Volley.newRequestQueue(this);
        loadJson();
        kembali();
        getSupportActionBar().setTitle("Menu Diagnosa");
        getSupportActionBar().setHomeButtonEnabled(true);
        btnDiagnosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkbox = "";
                String checkbox1= "";
                for (MGejala hold : adpt.getAllData()) {
                    if (hold.isCheck()) {
                        checkbox += "- "+hold.getGejala()+"\n";
                        checkbox1 += hold.getIdgejala()+",";
                    }
                }
                if (!checkbox.isEmpty()) {
                    dipilihl = checkbox;
                    dipilih2 = checkbox1;
                } else {
                    Toast.makeText(Diagnosa.this, "Gejala belum dipilih", Toast.LENGTH_SHORT).show();
                }
                formHasilDiagnosa(dipilihl, dipilih2);
            }
        });
        LvDiagnosa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adpt.setCheckBox(position);
            }
        });
    }

    private void kembali(){
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void formHasilDiagnosa( String hasil, String request){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.hasil_diagnosa, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        penyakit1 = dialogView.findViewById(R.id.penyakit);
        keparahan1 = dialogView.findViewById(R.id.keparahan);
        gejala = dialogView.findViewById(R.id.gejpenyakit);
        prosedur = dialogView.findViewById(R.id.pros);
        peralatan = dialogView.findViewById(R.id.perlat);
        lama = dialogView.findViewById(R.id.lama_);
        gejala.setText(hasil);

       dialog.setPositiveButton("Detail Penyakit", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               String penyakitsa = penyakit1.getText().toString();
               String gej = gejala.getText().toString();
               String parah = keparahan1.getText().toString();
               String pros = prosedur.getText().toString();
               String pera = peralatan.getText().toString();
               String lam = lama.getText().toString();
               Intent a = new Intent(getApplicationContext(), HasilDiagnosa.class);
               a.putExtra("PENY", penyakitsa);
               a.putExtra("GEJ", gej);
               a.putExtra("PAR", parah);
               a.putExtra("PER", pera);
               a.putExtra("PRO", pros);
               a.putExtra("LAM", lam);
               startActivity(a);
           }
       });
       loadHasilDiagnosa(request);
       dialog.show();
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
                                MGejala gejala = new MGejala();
                                gejala.setGejala(data.getString("nama_gejala"));
                                gejala.setIdgejala(data.getInt("id"));
                                gejalas.add(gejala);
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        adpt.notifyDataSetChanged();
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

    private void loadHasilDiagnosa(String req){
        Data dt = new Data(req);
        dataArray.add(dt);
        Gson gson = new Gson();
        final String newdataArray =gson.toJson(dataArray);
        StringRequest request = new StringRequest(Request.Method.POST, ServerApi.KIRIM_DATA,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("volley", "response : " + response.toString());
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                    Log.d("Output",jsonObject.getString("nama_penyakit"));
                                    Log.d("Nilai", jsonObject.getString("hasil"));
                                    penyakit1.setText(jsonObject.getString("nama_penyakit"));
                                    keparahan1.setText(jsonObject.getString("hasil"));
                                    prosedur.setText(jsonObject.getString("prosedur"));
                                    peralatan.setText(jsonObject.getString("peralatan"));
                                    lama.setText(jsonObject.getString("lama_perawatan"));
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("volley", "error: " + error.getMessage());
                        error.printStackTrace();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("array", newdataArray);
                return map;
            }
        };
        mQueue.add(request);
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
