package com.TugasAkhir.ikanku.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.TugasAkhir.ikanku.R;
import com.TugasAkhir.ikanku.model.ModelDataKategori;
import com.TugasAkhir.ikanku.ui.KategoriProduk;
import com.TugasAkhir.ikanku.util.FormatCurrency;
import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterKategori extends RecyclerView.Adapter<AdapterKategori.HolderData> {
    private List<ModelDataKategori> mItems ;
    private Context context;

    public AdapterKategori(Context context, List<ModelDataKategori> items)
    {
        this.mItems = items;
        this.context = context;
    }

    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.daftarkategori,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(HolderData holder, int position) {
        FormatCurrency currency = new FormatCurrency();
        ModelDataKategori md  = mItems.get(position);
        holder.tvidkategori.setText(md.getIdkategori());
        holder.tvnamakategori.setText(md.getNamakategori());
        Glide.with(context).load(mItems.get(position).getGambarkategori()).centerCrop().into(holder.ivgambarkategori);

        holder.md = md;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class HolderData extends RecyclerView.ViewHolder
    {
        TextView tvidkategori, tvnamakategori;
        ImageView ivgambarkategori;
        ModelDataKategori md;

        public  HolderData (View view)
        {
            super(view);

            tvidkategori = (TextView) view.findViewById(R.id.ppidkategori);
            tvnamakategori = (TextView) view.findViewById(R.id.ppnamakategori);
            ivgambarkategori = (ImageView) view.findViewById(R.id.ppgambarkategori);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent update = new Intent(context, KategoriProduk.class);
                    update.putExtra("update",1);
                    update.putExtra("idkategori",md.getIdkategori());
                    update.putExtra("namakategori",md.getNamakategori());
                    update.putExtra("gambarkategori",md.getGambarkategori());

                    context.startActivity(update);
                }
            });
        }
    }
}
