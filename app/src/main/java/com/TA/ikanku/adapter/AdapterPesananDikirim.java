package com.TA.ikanku.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.TA.ikanku.R;
import com.TA.ikanku.model.ModelDataPesanan;
import com.TA.ikanku.ui.pesanan.DetailPesananDikirim;
import com.TA.ikanku.util.FormatCurrency;

import java.util.List;

public class AdapterPesananDikirim extends RecyclerView.Adapter<AdapterPesananDikirim.HolderData> {
    private List<ModelDataPesanan> mItems ;
    private Context context;

    public AdapterPesananDikirim(Context context, List<ModelDataPesanan> items)
    {
        this.mItems = items;
        this.context = context;
    }

    @Override
    public AdapterPesananDikirim.HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.daftarpesanandikirim,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(AdapterPesananDikirim.HolderData holder, int position) {
        FormatCurrency currency = new FormatCurrency();
        ModelDataPesanan md  = mItems.get(position);
        holder.tvidproduk.setText(md.getIdProduk());
        holder.tvidpenjual.setText(md.getIdPenjual());
        holder.tvnamaproduk.setText(md.getNamaProduk());
        holder.tvstok.setText(md.getStok());
        holder.tvhargaproduk.setText(currency.formatRupiah(md.getHargaProduk()));
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
        ModelDataPesanan md;

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

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent update = new Intent(context, DetailPesananDikirim.class);
                    update.putExtra("update",1);
                    update.putExtra("idproduk",md.getIdProduk());
                    update.putExtra("idpenjual",md.getIdPenjual());
                    update.putExtra("namaproduk",md.getNamaProduk());
                    update.putExtra("stok",md.getStok());
                    update.putExtra("hargaproduk",md.getHargaProduk());
                    update.putExtra("deskripsi",md.getDeskripsi());
                    update.putExtra("gambarproduk",md.getGambarproduk());
                    update.putExtra("idpesanan",md.getIdpesanan());
                    update.putExtra("invoice",md.getInvoice());
                    update.putExtra("pembeli",md.getPembeli());
                    update.putExtra("jumlah",md.getJumlah());
                    update.putExtra("bayar",md.getBayar());
                    update.putExtra("pengiriman",md.getPengiriman());
                    update.putExtra("alamat",md.getAlamat());
                    update.putExtra("telp",md.getTelp());
                    update.putExtra("status",md.getStatus());

                    context.startActivity(update);
                }
            });
        }
    }
}
