package com.example.skripsi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.TextView;
import com.example.skripsi.Model.MGejala;
import com.example.skripsi.R;

import java.util.ArrayList;

public class AdapterDiagnosa extends BaseAdapter {
    Context context;
    ArrayList<MGejala> gejalas;
    ArrayList<MGejala> arrayList;
    CheckBox cbgejala;
    TextView tvgejala, idgejala;


    public AdapterDiagnosa (Context context, ArrayList<MGejala> gejalas){
        this.context =context;
        this.gejalas = gejalas;
        this.arrayList = gejalas;
    }

    @Override
    public int getCount() {
        return gejalas.size();
    }

    @Override
    public Object getItem(int position) {
        return gejalas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.lvdiagnosa, null);
        cbgejala = convertView.findViewById(R.id.cbGejala);
        tvgejala = convertView.findViewById(R.id.gejala);
        idgejala = convertView.findViewById(R.id.gejalaid);


        MGejala gejala= gejalas.get(position);

        tvgejala.setText(gejala.getGejala());

        if (gejala.isCheck()){
            cbgejala.setChecked(true);
        }else{
            cbgejala.setChecked(false);
        }

        return convertView;
    }

    public Filter getFilter() {

        return filter;
    }
    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint.toString().isEmpty()){
                filterResults.count = arrayList.size();
                filterResults.values = arrayList;
            }else {
                ArrayList<MGejala> filterd = new ArrayList<>();
                for (MGejala gejala1 : arrayList){
                    if (gejala1.getGejala().toLowerCase().contains(constraint.toString().toLowerCase().trim())){
                        filterd.add(gejala1);
                    }
                    filterResults.count = filterd.size();
                    filterResults.values = filterd;
                }
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            gejalas = (ArrayList<MGejala>) results.values;
            notifyDataSetChanged();
        }
    };

    public ArrayList <MGejala> getAllData(){
        return gejalas;
    }

    public void setCheckBox(int position){
        MGejala gejala = gejalas.get(position);
        gejala.setCheck(!gejala.isCheck());
        notifyDataSetChanged();
    }

}
