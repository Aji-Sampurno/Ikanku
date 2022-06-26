package com.TugasAkhir.ikanku.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.TugasAkhir.ikanku.R;
import com.TugasAkhir.ikanku.model.ModelDataTemp;
import com.TugasAkhir.ikanku.util.FormatCurrency;
import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterTemp  extends RecyclerView.Adapter<AdapterTemp.HolderData> {

    private List<ModelDataTemp> mItems ;
    private Context context;

    public AdapterTemp(Context context, List<ModelDataTemp> items)
    {
        this.mItems = items;
        this.context = context;
    }

    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.daftartemp,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(HolderData holder, int position) {
        FormatCurrency currency = new FormatCurrency();
        ModelDataTemp md  = mItems.get(position);
        holder.tvnamaproduk.setText(md.getNamaproduk());
        holder.tvhargaproduk.setText(currency.formatRupiah(md.getHargaproduk()));
        holder.tvjumlah.setText(md.getJumlah());
        holder.tvharga.setText(currency.formatRupiah(md.getBayar()));
        Glide.with(context).load(mItems.get(position).getGambarproduk()).centerCrop().into(holder.ivgambarproduk);

        holder.md = md;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    class HolderData extends RecyclerView.ViewHolder
    {
        TextView tvnamaproduk,tvhargaproduk,tvjumlah,tvharga;
        ImageView ivgambarproduk;
        ModelDataTemp md;

        public  HolderData (View view)
        {
            super(view);

            tvnamaproduk = (TextView) view.findViewById(R.id.ppnamaproduk);
            tvhargaproduk = (TextView) view.findViewById(R.id.ppharga);
            tvjumlah = (TextView) view.findViewById(R.id.ppjumlah);
            tvharga = (TextView) view.findViewById(R.id.pptotal);
            ivgambarproduk = (ImageView) view.findViewById(R.id.ppgambarproduk);

        }
    }
}
