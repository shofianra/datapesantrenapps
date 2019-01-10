package com.data.pesantren.apps;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements Filterable {
    private ArrayList<Data> listdata;
    private ArrayList<Data> listFilterData;
    private Activity activity;

    public RecyclerAdapter(Activity activity, ArrayList<Data> listdata) {
        this.listdata = listdata;
        this.listFilterData = listdata;
        this.activity = activity;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Data data = listFilterData.get(position);
        //holder.mImage.setImageResource(listdata.get(position).getThubnail());
        holder.id.setText(data.getId());
        holder.nama_institusi.setText(data.getNama_institusi());
        holder.alamat.setText(data.getAlamat());
        //holder.latitude.setText(listdata.get(position).getLatitude());
        //holder.longitude.setText(listdata.get(position).getLongitude());
        holder.jenis.setText(data.getJenis());
        final ViewHolder x=holder;
        holder.id.setVisibility(View.GONE);
        holder.btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Uri gmmIntentUri = Uri.parse("geo:"+listdata.get(mPosition).getLatitude()+","+listdata.get(mPosition).getLongitude());
                Uri gmmIntentUri = Uri.parse( "http://maps.google.com/maps?daddr=" +data.getLatitude() + "," + data.getLongitude());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                activity.startActivity(mapIntent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return listFilterData.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listFilterData = listdata;
                } else {
                    ArrayList<Data> filteredList = new ArrayList<>();
                    for (Data row : listdata) {
                        if (row.getNama_institusi().toLowerCase().contains(charString.toLowerCase()) || row.getAlamat().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    listFilterData = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listFilterData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listFilterData = (ArrayList<Data>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        private TextView id,nama_institusi,alamat,latitude,longitude,jenis;
        private Button btnMaps;

        public ViewHolder(View v) {
            super(v);
            cv=(CardView)v.findViewById(R.id.card_view);
            id=(TextView)v.findViewById(R.id.id);
            nama_institusi=(TextView)v.findViewById(R.id.nama_institusi);
            alamat=(TextView)v.findViewById(R.id.alamat);
            //latitude=(TextView)v.findViewById(R.id.latitude);
            //longitude=(TextView) v.findViewById(R.id.longitude);
            jenis=(TextView)v.findViewById(R.id.jenis);
            btnMaps=v.findViewById(R.id.btn_maps);
        }
    }


}