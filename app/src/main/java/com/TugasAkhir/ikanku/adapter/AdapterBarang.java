package com.TugasAkhir.ikanku.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.TugasAkhir.ikanku.R;
import com.TugasAkhir.ikanku.model.ModelDataProduk;
import com.TugasAkhir.ikanku.ui.DetailProduk;
import com.TugasAkhir.ikanku.util.FormatCurrency;

import java.util.ArrayList;
import java.util.List;

//digunakan untuk membuat public class bernama AdapterData dan merupakan extend dari class RecyclerView.Adapter<AdapterData.HolderData>
public class AdapterBarang extends RecyclerView.Adapter<AdapterBarang.HolderData> {
    private List<ModelDataProduk> mItems ;
    private Context context;

    public AdapterBarang(Context context, List<ModelDataProduk> items)
    {
        this.mItems = items;
        this.context = context;
    }

    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.daftarbarang,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(HolderData holder, int position) {
        FormatCurrency currency = new FormatCurrency();
        ModelDataProduk md  = mItems.get(position);
        holder.tvidproduk.setText(md.getIdProduk());
        holder.tvidpenjual.setText(md.getIdPenjual());
        holder.tvnamaproduk.setText(md.getNamaProduk());
        holder.tvstok.setText(md.getStok());
        holder.tvhargaproduk.setText(currency.formatRupiah(md.getHargaProduk()));
        holder.tvdeskripsi.setText(md.getDeskripsi());
        Glide.with(context).load(mItems.get(position).getGambarproduk()).centerCrop().into(holder.ivgambarproduk);

        holder.md = md;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class HolderData extends RecyclerView.ViewHolder
    {
        TextView tvidproduk, tvidpenjual, tvnamaproduk, tvstok, tvhargaproduk,tvdeskripsi;
        ImageView ivgambarproduk;
        ModelDataProduk md;

        public  HolderData (View view)
        {
            super(view);

            tvidproduk = (TextView) view.findViewById(R.id.ppidproduk);
            tvidpenjual = (TextView) view.findViewById(R.id.ppidpenjual);
            tvnamaproduk = (TextView) view.findViewById(R.id.ppnamaproduk);
            tvstok = (TextView) view.findViewById(R.id.ppstok);
            tvhargaproduk = (TextView) view.findViewById(R.id.ppharga);
            tvdeskripsi = (TextView) view.findViewById(R.id.ppdeskripsi);
            ivgambarproduk = (ImageView) view.findViewById(R.id.ppgambarproduk);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent update = new Intent(context, DetailProduk.class);
                    update.putExtra("update",1);
                    update.putExtra("idproduk",md.getIdProduk());
                    update.putExtra("idpenjual",md.getIdPenjual());
                    update.putExtra("namaproduk",md.getNamaProduk());
                    update.putExtra("stok",md.getStok());
                    update.putExtra("hargaproduk",md.getHargaProduk());
                    update.putExtra("deskripsi",md.getDeskripsi());
                    update.putExtra("gambarproduk",md.getGambarproduk());

                    context.startActivity(update);
                }
            });
        }
    }
}