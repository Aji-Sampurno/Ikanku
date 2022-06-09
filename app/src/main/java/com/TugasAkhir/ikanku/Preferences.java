package com.TugasAkhir.ikanku;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {
    static final String KEY_USER_TEREGISTER ="username", KEY_PASS_TEREGISTER ="password";
    static final String KEY_ID_PENGGUNA ="id_pengguna";
    static final String KEY_NAMA = "nama";
    static final String KEY_ALAMAT = "alamat";
    static final String KEY_TELEPON = "telp";

    public static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setRegisteredUser(Context context, String username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USER_TEREGISTER, username);
        editor.apply();
    }
    public static String getRegisteredUser(Context context){
        return getSharedPreference(context).getString(KEY_USER_TEREGISTER,"");
    }

    public static void setRegisteredPass(Context context, String password){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_PASS_TEREGISTER, password);
        editor.apply();
    }
    public static String getRegisteredPass(Context context){
        return getSharedPreference(context).getString(KEY_PASS_TEREGISTER,"");
    }

    public static void clearLoggedInUser (Context context){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(KEY_USER_TEREGISTER);
        editor.remove(KEY_PASS_TEREGISTER);
        editor.remove(KEY_ID_PENGGUNA);
        editor.remove(KEY_NAMA);
        editor.remove(KEY_ALAMAT);
        editor.remove(KEY_TELEPON);
        editor.apply();
    }

    //    Id pemesan
    public static void setKeyIdPengguna(Context context, String id_pengguna){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_ID_PENGGUNA, id_pengguna);
        editor.apply();
    }
    public static String getKeyIdPengguna(Context context){
        return getSharedPreference(context).getString(KEY_ID_PENGGUNA,"");
    }

    //    Nama pemesan
    public static void setKeyNama(Context context, String nama){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_NAMA, nama);
        editor.apply();
    }
    public static String getKeyNama(Context context){
        return getSharedPreference(context).getString(KEY_NAMA,"");
    }

    //    Alamat
    public static void setKeyAlamat(Context context, String alamat){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_ALAMAT, alamat);
        editor.apply();
    }
    public static String getKeyAlamat(Context context){
        return getSharedPreference(context).getString(KEY_ALAMAT,"");
    }
    //    Telepon
    public static void setKeyTelepon(Context context, String telp){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_TELEPON, telp);
        editor.apply();
    }
    public static String getKeyTelepon(Context context){
        return getSharedPreference(context).getString(KEY_TELEPON,"");
    }
}
