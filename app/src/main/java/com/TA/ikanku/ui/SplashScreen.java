package com.TA.ikanku.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.TA.ikanku.R;
import com.TA.ikanku.ui.pembeli.PembeliMain;
import com.TA.ikanku.ui.penjual.PenjualMain;

import java.util.HashMap;

public class SplashScreen extends AppCompatActivity {

    Animation anim;
    Handler handler;
    Runnable runnable;
    ImageView img;
    String getStatus;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        img = findViewById(R.id.img);
//        img.animate().alpha(2000).setDuration(0);

        sessionManager = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetail();
        String mUsername = user.get(sessionManager.USERNAME);
        getStatus = user.get(sessionManager.STATUS);

        anim = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.fade_in);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (mUsername != null) {
                    if (getStatus.equals("1")){
                        Intent intent = new Intent(SplashScreen.this, PembeliMain.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashScreen.this, PenjualMain.class);
                        startActivity(intent);
                        finish();
                    }
//                    Intent intent = new Intent(com.example.ikanku.ui.SplashScreen.this, PenjualMain.class);
//                    startActivity(intent);
                } else {
                    Intent dsp = new Intent(SplashScreen.this, Login.class);
                    startActivity(dsp);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        img.startAnimation(anim);

//        handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (mUsername != null) {
//                    if (getStatus.equals("1")){
//                        Intent intent = new Intent(SplashScreen.this, PembeliMain.class);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        Intent intent = new Intent(SplashScreen.this, PenjualMain.class);
//                        startActivity(intent);
//                        finish();
//                    }
////                    Intent intent = new Intent(com.example.ikanku.ui.SplashScreen.this, PenjualMain.class);
////                    startActivity(intent);
//                } else {
//                    Intent dsp = new Intent(SplashScreen.this, Login.class);
//                    startActivity(dsp);
//                }
//                finish();
//            }
//        },1000);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}