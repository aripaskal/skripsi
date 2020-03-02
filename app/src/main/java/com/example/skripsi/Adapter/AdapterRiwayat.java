package com.example.skripsi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.skripsi.Model.MRiwayat;
import com.example.skripsi.R;

import java.util.ArrayList;


public class AdapterRiwayat extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<MRiwayat> riwayats;
    ArrayList<MRiwayat> arrayList;
    TextView tv_nama, tv_penyakit, tv_tgl, tv_umur, tv_jk;

    public AdapterRiwayat(Context context, ArrayList<MRiwayat> riwayats){
        this.context=context;
        this.riwayats = riwayats;
        this.arrayList = riwayats;
    }

    @Override
    public int getCount() {
        return riwayats.size();
    }

    @Override
    public Object getItem(int position) {
        return riwayats.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.lvriwayat,null);
        tv_nama = convertView.findViewById(R.id.txt_namapasien);
        tv_penyakit = convertView.findViewById(R.id.txt_namapenyakitku);
        tv_tgl = convertView.findViewById(R.id.txt_tgl);
        tv_umur = convertView.findViewById(R.id.riw_umur);
        tv_jk = convertView.findViewById(R.id.riw_jk);

        MRiwayat mRiwayat = riwayats.get(position);

        tv_tgl.setText(mRiwayat.getTgl());
        tv_penyakit.setText(mRiwayat.getPenyakitku());
        tv_nama.setText(mRiwayat.getNama());
        tv_umur.setText(mRiwayat.getUmur());
        tv_jk.setText(mRiwayat.getJk());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint.toString().isEmpty()){
               filterResults.count = arrayList.size();
               filterResults.values = arrayList;
            }else {
                ArrayList<MRiwayat> filterd = new ArrayList<>();
                for (MRiwayat riwayat : arrayList){
                    if (riwayat.getNama().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filterd.add(riwayat);
                    }
                    filterResults.count=filterd.size();
                    filterResults.values=filterd;
                }
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrayList = (ArrayList<MRiwayat>) results.values;
            notifyDataSetChanged();
        }
    };
}
