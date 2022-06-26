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
import com.TugasAkhir.ikanku.model.ModelDataLaporan;
import com.TugasAkhir.ikanku.ui.penjualan.DetailPenjualan;
import com.TugasAkhir.ikanku.util.FormatCurrency;
import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterPendapatan  extends RecyclerView.Adapter<AdapterPendapatan.HolderData> {
    private List<ModelDataLaporan> mItems;
    private Context context;

    public AdapterPendapatan(Context context, List<ModelDataLaporan> items) {
        this.mItems = items;
        this.context = context;
    }

    @Override
    public AdapterPendapatan.HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.daftarpendapatan,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(HolderData holder, int position) {
        FormatCurrency currency = new FormatCurrency();
        ModelDataLaporan md  = mItems.get(position);
        holder.tvidproduk.setText(md.getIdproduk());
        holder.tvidpenjual.setText(md.getIdpenjual());
        holder.tvnamaproduk.setText(md.getNamaproduk());
        holder.tvstok.setText(md.getStok());
        holder.tvhargaproduk.setText(currency.formatRupiah(md.getHargaproduk()));
        holder.tvidpesanan.setText(md.getIdpesanan());
        holder.tvinvoice.setText(md.getInvoice());
        holder.tvpembeli.setText(md.getPembeli());
        holder.tvjumlah.setText(md.getJumlah());
        holder.tvbayar.setText(currency.formatRupiah(md.getBayar()));
        holder.tvpengiriman.setText(md.getPengiriman());
        holder.tvalamat.setText(md.getAlamat());
        holder.tvtelp.setText(md.getTelp());
        holder.tvstatus.setText(md.getStatus());
        Glide.with(context).load(mItems.get(position).getGambarproduk()).centerCrop().into(holder.ivgambarproduk);

        holder.md = md;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    class HolderData extends RecyclerView.ViewHolder
    {
        TextView tvidproduk, tvidpenjual, tvnamaproduk, tvstok, tvhargaproduk,tvdeskripsi,tvidpesanan,tvinvoice,tvpembeli,tvjumlah,tvbayar,tvpengiriman,tvalamat,tvtelp,tvstatus;
        ImageView ivgambarproduk;
        ModelDataLaporan md;

        public  HolderData (View view)
        {
            super(view);

            tvidproduk      = (TextView) view.findViewById(R.id.ppidproduk);
            tvidpenjual     = (TextView) view.findViewById(R.id.ppidpenjual);
            tvnamaproduk    = (TextView) view.findViewById(R.id.ppnamaproduk);
            tvstok          = (TextView) view.findViewById(R.id.ppstok);
            tvhargaproduk   = (TextView) view.findViewById(R.id.ppharga);
            tvdeskripsi     = (TextView) view.findViewById(R.id.ppdeskripsi);
            tvidpesanan     = (TextView) view.findViewById(R.id.ppidpesanan);
            tvinvoice       = (TextView) view.findViewById(R.id.ppinvoice);
            tvpembeli       = (TextView) view.findViewById(R.id.pppembeli);
            tvjumlah        = (TextView) view.findViewById(R.id.ppjumlah);
            tvbayar         = (TextView) view.findViewById(R.id.ppbayar);
            tvpengiriman    = (TextView) view.findViewById(R.id.pppengiriman);
            tvalamat        = (TextView) view.findViewById(R.id.ppalamat);
            tvtelp          = (TextView) view.findViewById(R.id.pptelp);
            tvstatus        = (TextView) view.findViewById(R.id.ppstatus);
            ivgambarproduk  = (ImageView) view.findViewById(R.id.ppgambarproduk);
        }
    }
}