package com.TugasAkhir.ikanku.ui.penjual;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.TugasAkhir.ikanku.R;
import com.bumptech.glide.Glide;

public class DetailEdukasi extends AppCompatActivity {

    TextView idedukasi,judul,isi,kategori;
    ImageView ivedukasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_edukasi);

        idedukasi = findViewById(R.id.ppidedukasi);
        judul = findViewById(R.id.ppjuduledukasi);
        isi = findViewById(R.id.ppisiedukasi);
        kategori = findViewById(R.id.ppkategoriedukasi);
        ivedukasi = findViewById(R.id.ppgambaredukasi);

        Intent data = getIntent();
        final int update = data.getIntExtra("update",0);
        String intentidedukasi = data.getStringExtra("idedukasi");
        String intentjudul = data.getStringExtra("judul");
        String intentkategori = data.getStringExtra("kategoriedukasi");
        String intentisi = data.getStringExtra("isi");
        String intentgambar = data.getStringExtra("gambaredukasi");

        if(update == 1)
        {
            idedukasi.setText(intentidedukasi);
            judul.setText(intentjudul);
            isi.setText(intentisi);
            kategori.setText(intentkategori);
            Glide.with(DetailEdukasi.this).load(intentgambar).centerCrop().into(ivedukasi);
        }
    }
}