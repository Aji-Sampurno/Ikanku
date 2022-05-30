package com.TA.ikanku.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.TA.ikanku.R;
import com.TA.ikanku.model.ModelDataEdukasi;
import com.TA.ikanku.ui.penjual.DetailEdukasi;
import com.TA.ikanku.util.FormatCurrency;
import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterEdukasi extends RecyclerView.Adapter<AdapterEdukasi.HolderData> {
    private List<ModelDataEdukasi> mItems ;
    private Context context;

    public AdapterEdukasi(Context context, List<ModelDataEdukasi> items)
    {
        this.mItems = items;
        this.context = context;
    }

    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.edukasi,parent,false);
        HolderData holderData = new AdapterEdukasi.HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(HolderData holder, int position) {
        FormatCurrency currency = new FormatCurrency();
        ModelDataEdukasi md  = mItems.get(position);
        holder.tvidedukasi.setText(md.getIdedukasi());
        holder.tvkategori.setText(md.getKategoriedukasi());
        holder.tvjudul.setText(md.getJudul());
        holder.tvisi.setText(md.getIsi());
        Glide.with(context).load(mItems.get(position).getGambaredukasi()).centerCrop().into(holder.ivgambaredukasi);

        holder.md = md;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    class HolderData extends RecyclerView.ViewHolder
    {
        TextView tvidedukasi, tvjudul, tvisi, tvkategori;
        ImageView ivgambaredukasi;
        ModelDataEdukasi md;

        public  HolderData (View view)
        {
            super(view);

            tvidedukasi     = (TextView) view.findViewById(R.id.ppidedukasi);
            tvjudul         = (TextView) view.findViewById(R.id.ppjuduledukasi);
            tvkategori         = (TextView) view.findViewById(R.id.ppkategoriedukasi);
            tvisi           = (TextView) view.findViewById(R.id.ppisiedukasi);
            ivgambaredukasi = (ImageView) view.findViewById(R.id.ppgambaredukasi);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent update = new Intent(context, DetailEdukasi.class);
                    update.putExtra("update",1);
                    update.putExtra("idedukasi",md.getIdedukasi());
                    update.putExtra("judul",md.getJudul());
                    update.putExtra("kategoriedukasi",md.getKategoriedukasi());
                    update.putExtra("isi",md.getIsi());
                    update.putExtra("gambaredukasi",md.getGambaredukasi());

                    context.startActivity(update);
                }
            });
        }
    }
}
