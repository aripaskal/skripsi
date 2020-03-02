package com.example.skripsi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.skripsi.Model.Penyakit;
import com.example.skripsi.R;

import java.util.ArrayList;

public class AdapterPenyakit extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<Penyakit> penyakits;
    ArrayList<Penyakit> arrayList;
    TextView tv_id, tv_kode, tv_penyakit, tv_prosedur, tv_peralatan, tv_lama;

    public AdapterPenyakit(Context context, ArrayList<Penyakit> penyakits){
      this.context= context;
      this.penyakits = penyakits;
      this.arrayList = penyakits;
    }

    @Override
    public int getCount() {
        return penyakits.size();
    }

    @Override
    public Object getItem(int position) {
        return penyakits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.listview_penyakit, null);
        tv_id = convertView.findViewById(R.id.txt_IDPenyakit);
        tv_kode = convertView.findViewById(R.id.txt_kdPenyakit);
        tv_penyakit= convertView.findViewById(R.id.txt_namaPenyakit);
        tv_prosedur = convertView.findViewById(R.id.dataprosedur);
        tv_peralatan = convertView.findViewById(R.id.dataperalatan);
        tv_lama = convertView.findViewById(R.id.dataperwatan);

        Penyakit penyakit = penyakits.get(position);

        tv_penyakit.setText(penyakit.getNamaPenyakit());
        tv_kode.setText(penyakit.getKodePenyakit());
        tv_id.setText(String.valueOf(penyakit.getId()));
        tv_lama.setText(penyakit.getLama_perawatan());
        tv_peralatan.setText(penyakit.getPeralatan());
        tv_prosedur.setText(penyakit.getProsedur());

        return  convertView;
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
                filterResults.count=arrayList.size();
                filterResults.values = arrayList;
            }else {
                ArrayList<Penyakit> filterd = new ArrayList<>();
                for (Penyakit penyakit : arrayList){
                    if (penyakit.getNamaPenyakit().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filterd.add(penyakit);
                    }
                    filterResults.count = filterd.size();
                    filterResults.values = filterd;
                }
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            penyakits = (ArrayList<Penyakit>) results.values;
            notifyDataSetChanged();
        }
    };
}
