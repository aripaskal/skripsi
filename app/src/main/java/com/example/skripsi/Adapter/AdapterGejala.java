package com.example.skripsi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.skripsi.Model.Gejala;
import com.example.skripsi.R;

import java.util.ArrayList;

public class AdapterGejala extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<Gejala> arrayList;
    ArrayList<Gejala> gejalas;
    TextView tv_Gejala, tv_Kode, tv_ID;

    public AdapterGejala(Context context, ArrayList<Gejala> arrayList){
       this.context = context;
       this.gejalas = arrayList;
       this.arrayList = arrayList;
    }


    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_gejala, null);
            tv_Gejala = convertView.findViewById(R.id.txt_Nama);
            tv_ID = convertView.findViewById(R.id.txt_ID);
            tv_Kode = convertView.findViewById(R.id.txt_Kode);

            Gejala gejala = arrayList.get(position);


            tv_Kode.setText(gejala.getKode());
            tv_ID.setText(String.valueOf(gejala.getId()));
            tv_Gejala.setText(gejala.getNama());


        return convertView;
    }


    @Override
    public Filter getFilter() {

        return filter;
    }
    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
             if (constraint.toString().isEmpty()){
                 filterResults.count = gejalas.size();
                 filterResults.values = gejalas;
             }else {
                 ArrayList<Gejala> filterd = new ArrayList<>();
                 for (Gejala gejala : gejalas){
                     if (gejala.getNama().toLowerCase().contains(constraint.toString().toLowerCase().trim())){
                         filterd.add(gejala);
                     }
                     filterResults.count = filterd.size();
                     filterResults.values = filterd;
                 }
             }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
           arrayList = (ArrayList<Gejala>) results.values;
            notifyDataSetChanged();
        }
    };
}
