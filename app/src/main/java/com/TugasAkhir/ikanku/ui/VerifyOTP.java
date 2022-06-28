package com.TugasAkhir.ikanku.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.TugasAkhir.ikanku.R;
import com.TugasAkhir.ikanku.ui.pembeli.PembeliMain;
import com.TugasAkhir.ikanku.ui.penjual.PenjualMain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {

    EditText et1, et2, et3, et4, et5, et6;
    Button btnsubmit;
    String getbackendotp;
    ProgressBar progressBar;
    SessionManager sessionManager;
    String getId,getUsername,getNama,getTelp,getAlamat,getNamatoko,getStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        sessionManager = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID_PENGGUNA);
        getUsername = user.get(sessionManager.USERNAME);
        getNama = user.get(sessionManager.NAMA);
        getTelp = user.get(sessionManager.TELP);
        getAlamat = user.get(sessionManager.ALAMAT);
        getNamatoko = user.get(sessionManager.NAMATOKO);
        getStatus = user.get(sessionManager.STATUS);

        et1 = findViewById(R.id.inputotp1);
        et2 = findViewById(R.id.inputotp2);
        et3 = findViewById(R.id.inputotp3);
        et4 = findViewById(R.id.inputotp4);
        et5 = findViewById(R.id.inputotp5);
        et6 = findViewById(R.id.inputotp6);

        progressBar = findViewById(R.id.probar2);


        //get mobile number from mainActivty to this
        TextView textView = findViewById(R.id.txtmobileno);
        textView.setText(String.format(
                "+62-%S", getIntent().getStringExtra("mobile")
        ));

        getbackendotp = getIntent().getStringExtra("backendotp");


        btnsubmit = findViewById(R.id.btnsubmit);

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!et1.getText().toString().trim().isEmpty() && !et2.getText().toString().trim().isEmpty()
                        && !et3.getText().toString().trim().isEmpty()
                        && !et4.getText().toString().trim().isEmpty()
                        && !et5.getText().toString().trim().isEmpty()
                        && !et6.getText().toString().trim().isEmpty()) {

                    // marging user's input in a string
                    String getuserotp = et1.getText().toString() +
                            et2.getText().toString() +
                            et3.getText().toString() +
                            et4.getText().toString() +
                            et5.getText().toString() +
                            et6.getText().toString();

                    if (getbackendotp != null) {

                        progressBar.setVisibility(View.VISIBLE);
                        btnsubmit.setVisibility(View.INVISIBLE);

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(getbackendotp, getuserotp);
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        progressBar.setVisibility(View.GONE);
                                        btnsubmit.setVisibility(View.VISIBLE);

                                        if (task.isSuccessful()) {
                                            if (getStatus.equals("1")){
                                                Intent intent = new Intent(VerifyOTP.this, PembeliMain.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Intent intent = new Intent(VerifyOTP.this, PenjualMain.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();
                                            }
                                        } else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(VerifyOTP.this);
                                            builder.setTitle("Gagal").
                                                    setIcon(R.mipmap.ic_warning_foreground).
                                                    setMessage("Masukkan Kode OTP yang benar");
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


                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(VerifyOTP.this);
                        builder.setTitle("Terdapat Kesalahan").
                                setIcon(R.mipmap.ic_kesalahan_jaringan_foreground).
                                setMessage("Terdapat Kesalahan Jaringan");
                        builder.setPositiveButton("Coba Lagi",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert11 = builder.create();
                        alert11.show();
                    }

                    //Toast.makeText(MainActivity2.this, "OTP Verify", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(VerifyOTP.this);
                    builder.setTitle("Terdapat Kesalahan").
                            setIcon(R.mipmap.ic_warning_foreground).
                            setMessage("Masukkan Kode OTP");
                    builder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                }


                // movenumtonext();



            }
        });

        findViewById(R.id.sendotp_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+62" + getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        VerifyOTP.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(VerifyOTP.this);
                                builder.setTitle("Terdapat Kesalahan").
                                        setIcon(R.mipmap.ic_warning_foreground).
                                        setMessage("Terdapat kesalahan saat mengirimkan ulang kode OTP");
                                builder.setPositiveButton("Coba Lagi",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alert11 = builder.create();
                                alert11.show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newbackendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {


                                getbackendotp = newbackendotp;

                                AlertDialog.Builder builder = new AlertDialog.Builder(VerifyOTP.this);
                                builder.setTitle("Berhasil").
                                        setIcon(R.mipmap.ic_warning_foreground).
                                        setMessage("Kode OTP berhasil dikirimkan");
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

                );

            }
        });

        movenumtonext(); //move num to next

    }

    private void movenumtonext() {



        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().trim().isEmpty()) {
                    et2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().trim().isEmpty()) {
                    et3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().trim().isEmpty()) {
                    et4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().trim().isEmpty()) {
                    et5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().trim().isEmpty()) {
                    et6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }
}