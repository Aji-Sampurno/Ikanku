package com.TugasAkhir.ikanku.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.TugasAkhir.ikanku.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class SendOTP extends AppCompatActivity {

    EditText editText1;
    Button sendotp;
    ProgressBar progressBar;
    SessionManager sessionManager;
    String getId,getUsername,getNama,getTelp,getAlamat,getNamatoko,getStatus;
    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);

        sessionManager = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID_PENGGUNA);
        getUsername = user.get(sessionManager.USERNAME);
        getNama = user.get(sessionManager.NAMA);
        getTelp = user.get(sessionManager.TELP);
        getAlamat = user.get(sessionManager.ALAMAT);
        getNamatoko = user.get(sessionManager.NAMATOKO);
        getStatus = user.get(sessionManager.STATUS);

        editText1=findViewById(R.id.input_mob_no);
        sendotp=findViewById(R.id.btnsend);
        progressBar=findViewById(R.id.probar1);

        editText1.setText(getTelp);
        editText1.setFocusable(false);
        editText1.setFocusableInTouchMode(false);

        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!editText1.getText().toString().trim().isEmpty()){
                    if ((editText1.getText().toString().trim()).length()>=11)
                    {

                        progressBar.setVisibility(View.VISIBLE);
                        sendotp.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+62" + editText1.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                                SendOTP.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                        progressBar.setVisibility(View.GONE);
                                        sendotp.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {

                                        progressBar.setVisibility(View.GONE);
                                        sendotp.setVisibility(View.VISIBLE);

                                        AlertDialog.Builder builder = new AlertDialog.Builder(SendOTP.this);
                                        builder.setTitle("Terdapat Kesalahan").
                                                setIcon(R.mipmap.ic_warning_foreground).
                                                setMessage(""+e);
                                        builder.setPositiveButton("Ok",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });
                                        AlertDialog alert11 = builder.create();
                                        alert11.show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                        progressBar.setVisibility(View.GONE);
                                        sendotp.setVisibility(View.VISIBLE);

                                        Intent intent=new Intent(getApplicationContext(),VerifyOTP.class);
                                        intent.putExtra("mobile",editText1.getText().toString());
                                        intent.putExtra("backendotp",backendotp);
                                        startActivity(intent);

                                    }
                                }

                        );


                    }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SendOTP.this);
                        builder.setTitle("Terdapat Kesalahan").
                                setIcon(R.mipmap.ic_warning_foreground).
                                setMessage("Apakah nomer anda sudah benar ?");
                        builder.setPositiveButton("Ya",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert11 = builder.create();
                        alert11.show();
                    }

                }else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SendOTP.this);
                    builder.setTitle("Terdapat Kesalahan").
                            setIcon(R.mipmap.ic_warning_foreground).
                            setMessage("Masukkan Nomer Telepon");
                    builder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder.create();
                    alert11.show();
                }

            }
        });



    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            sessionManager.logout();
            FirebaseAuth.getInstance().signOut();
            finish();
            super.moveTaskToBack(true);
            return;
        } else {
            sessionManager.logout();
            FirebaseAuth.getInstance().signOut();
            finish();
            Toast.makeText(getBaseContext(), "Tekan Sekali lagi untuk Keluar", Toast.LENGTH_SHORT).show();
        }

        mBackPressed = System.currentTimeMillis();
        sessionManager.logout();
        finish();
        FirebaseAuth.getInstance().signOut();
    }
}