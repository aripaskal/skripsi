package com.example.skripsi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.skripsi.Model.Hubungan;
import com.example.skripsi.R;

import java.util.ArrayList;

public class AdapterHubungan extends BaseAdapter {
    Context context;
    ArrayList<Hubungan> hubungans;
    TextView idhubungan, idgejala, idpenyakit;

    public AdapterHubungan(Context context, ArrayList<Hubungan> hubungans){
        this.context = context;
        this.hubungans = hubungans;
    }

    @Override
    public int getCount() {
        return hubungans.size();
    }

    @Override
    public Object getItem(int position) {
        return hubungans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.listview_hubungan,null);
        idgejala = convertView.findViewById(R.id.idgejala);
        idpenyakit = convertView.findViewById(R.id.idpenyakit);

        Hubungan hubungan = hubungans.get(position);

        idgejala.setText(String.valueOf(hubungan.getgejala()));
        idpenyakit.setText(String.valueOf(hubungan.getpenyakit()));
        return convertView;
    }
}
