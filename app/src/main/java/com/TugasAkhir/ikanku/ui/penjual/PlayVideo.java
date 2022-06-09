package com.TugasAkhir.ikanku.ui.penjual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.TugasAkhir.ikanku.R;
import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayVideo extends YouTubeBaseActivity {

    TextView idedukasi,judul,isi,kategori;
    ImageView ivedukasi;
    YouTubePlayerView youTubePlayerView;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        youTubePlayerView = findViewById(R.id.ytbplayerview);
        linearLayout = findViewById(R.id.llaertikel);
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
        String intentlink = data.getStringExtra("link");
        String intentgambar = data.getStringExtra("gambaredukasi");

        if(update == 1)
        {
            idedukasi.setText(intentidedukasi);
            judul.setText(intentjudul);
            isi.setText(intentisi);
            kategori.setText(intentkategori);
            Glide.with(PlayVideo.this).load(intentgambar).centerCrop().into(ivedukasi);
        }

        if (intentlink.isEmpty()){
            youTubePlayerView.setVisibility(View.GONE);
        } else {
            linearLayout.setVisibility(View.GONE);
        }

        YouTubePlayer.OnInitializedListener listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(intentlink);
                youTubePlayer.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(getApplicationContext(),"Gagal Memutar Video",Toast.LENGTH_SHORT).show();
            }
        };

        youTubePlayerView.initialize("AIzaSyBjbM_tf-Nfw3q1jf5AJ0agmUoAwpm6rRw",listener);
    }
}