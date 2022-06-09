package com.TugasAkhir.ikanku.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.TugasAkhir.ikanku.MainActivity;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    public static final String PREF_NAME = "LOGIN";
    public static final String LOGIN = "IS_LOGIN";
    public static final String ID_PENGGUNA = "ID_PENGGUNA";
    public static final String USERNAME = "USERNAME";
    public static final String NAMA = "NAMA";
    public static final String TELP = "TELP";
    public static final String ALAMAT = "ALAMAT";
    public static final String NAMATOKO = "NAMATOKO";
    public static final String STATUS = "STATUS";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createsession(String id_pengguna, String username, String nama, String telp, String alamat, String namatoko, String status){
        editor.putBoolean(LOGIN,true);
        editor.putString(ID_PENGGUNA,id_pengguna);
        editor.putString(USERNAME,username);
        editor.putString(NAMA,nama);
        editor.putString(TELP,telp);
        editor.putString(ALAMAT,alamat);
        editor.putString(NAMATOKO,namatoko);
        editor.putString(STATUS,status);
        editor.apply();
    }

    public boolean isLogin(){
        return sharedPreferences.getBoolean(LOGIN,false);
    }

    public void checkLogin(){
        if (!this.isLogin()){
            Intent i = new Intent(context, Login.class);
            context.startActivity(i);
            ((MainActivity)context).finish();
        }
    }

    public HashMap<String,String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(ID_PENGGUNA, sharedPreferences.getString(ID_PENGGUNA,null));
        user.put(USERNAME, sharedPreferences.getString(USERNAME,null));
        user.put(NAMA, sharedPreferences.getString(NAMA,null));
        user.put(TELP, sharedPreferences.getString(TELP,null));
        user.put(ALAMAT, sharedPreferences.getString(ALAMAT,null));
        user.put(NAMATOKO, sharedPreferences.getString(NAMATOKO,null));
        user.put(STATUS, sharedPreferences.getString(STATUS,null));
        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
//        Intent i = new Intent(context,Login.class);
//        context.startActivity(i);
//        ((MainActivity)context).finish();
    }
}
