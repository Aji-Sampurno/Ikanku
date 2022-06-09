package com.TugasAkhir.ikanku.ui.pembeli.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.TugasAkhir.ikanku.util.ServerApi;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.TugasAkhir.ikanku.Preferences;
import com.TugasAkhir.ikanku.ui.Login;
import com.TugasAkhir.ikanku.ui.PengaturanAkun;
import com.TugasAkhir.ikanku.R;
import com.TugasAkhir.ikanku.ui.RegistrasiPenjual;
import com.TugasAkhir.ikanku.ui.SessionManager;
import com.TugasAkhir.ikanku.ui.Pesanan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PembeliAkun extends Fragment {

    private LinearLayout  dikemas,dikirim,diterima;
    private TextView      riwayatpesanan,pengaturanakun,daftarpenjual,pusatbantuan,badgedikemas,badgedikirim,badgediterima;
    private Button        btnlogout;
    SessionManager        sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_pembeli_akun, container, false);

        sessionManager  = new SessionManager(getActivity());

        dikemas         = root.findViewById(R.id.dikemas);
        dikirim         = root.findViewById(R.id.dikirim);
        diterima        = root.findViewById(R.id.diterima);
        badgedikemas    = root.findViewById(R.id.badgedikemas);
        badgedikirim    = root.findViewById(R.id.badgedikirim);
        badgediterima   = root.findViewById(R.id.badgediterima);
        riwayatpesanan  = root.findViewById(R.id.riwayatpesanan);
        pengaturanakun  = root.findViewById(R.id.pengaturanakun);
        daftarpenjual   = root.findViewById(R.id.daftarpenjual);
        pusatbantuan    = root.findViewById(R.id.pusatbantuan);
        btnlogout       = root.findViewById(R.id.btnlogout);

        riwayatpesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), Pesanan.class);
                getActivity().startActivity(in);
            }
        });

        dikirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), Pesanan.class);
                getActivity().startActivity(in);
            }
        });

        dikemas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), Pesanan.class);
                getActivity().startActivity(in);
            }
        });

        diterima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), Pesanan.class);
                getActivity().startActivity(in);
            }
        });

        pengaturanakun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), PengaturanAkun.class);
                getActivity().startActivity(in);
            }
        });

        daftarpenjual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), RegistrasiPenjual.class);
                getActivity().startActivity(in);
            }
        });

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Konfirmasi Logout").
                        setIcon(R.mipmap.ic_confirm_foreground).
                        setMessage("apakah kamu yakin untuk logout?");
                builder.setPositiveButton("Ya",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                sessionManager.logout();
                                Intent i = new Intent(getActivity(), Login.class);
                                startActivity(i);
                                getActivity().finish();
                            }
                        });
                builder.setNegativeButton("Tidak",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder.create();
                alert11.show();
            }
        });

        return root;
    }

    private void pesanandikemas()
    {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_PESANANDIKEMAS +  Preferences.getKeyIdPengguna(getActivity()),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);

                                String strTotal = data.getString("total");

                                if (strTotal.equals("0")){
                                    badgedikemas.setVisibility(View.GONE);
                                }else {
                                    badgedikemas.setVisibility(View.VISIBLE);
                                    badgedikemas.setText(strTotal);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(reqData);
    }

    private void pesanandikirim()
    {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_PESANANDIKIRIM +  Preferences.getKeyIdPengguna(getActivity()),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);

                                String strTotal = data.getString("total");

                                if (strTotal.equals("0")){
                                    badgedikirim.setVisibility(View.GONE);
                                }else {
                                    badgedikirim.setVisibility(View.VISIBLE);
                                    badgedikirim.setText(strTotal);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(reqData);
    }

    private void pesananditerima()
    {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_PESANANDITERIMA +  Preferences.getKeyIdPengguna(getActivity()),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);

                                String strTotal = data.getString("total");

                                if (strTotal.equals("0")){
                                    badgediterima.setVisibility(View.GONE);
                                }else {
                                    badgediterima.setVisibility(View.VISIBLE);
                                    badgediterima.setText(strTotal);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(reqData);
    }

    @Override
    public void onResume() {
        super.onResume();
        pesanandikemas();
        pesanandikirim();
        pesananditerima();
    }
}