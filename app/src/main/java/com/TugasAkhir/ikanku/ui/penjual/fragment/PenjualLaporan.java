package com.TugasAkhir.ikanku.ui.penjual.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.TugasAkhir.ikanku.R;
import com.TugasAkhir.ikanku.ui.SessionManager;
import com.TugasAkhir.ikanku.ui.Penjualan;
import com.TugasAkhir.ikanku.util.FormatCurrency;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PenjualLaporan extends Fragment {

    private TextView        riwayatpenjualan,pendapatan,pengeluaran,badgebaru,badgedikirim,badgeselesai;
    private LinearLayout    baru,dikirim,selesai;
    SessionManager          sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_penjual_laporan, container, false);

        sessionManager = new SessionManager(getActivity());

        baru             = root.findViewById(R.id.baru);
        dikirim          = root.findViewById(R.id.dikirim);
        selesai          = root.findViewById(R.id.selesai);
        pendapatan       = root.findViewById(R.id.tvpendapatan);
        pengeluaran      = root.findViewById(R.id.tvpengeluaran);
        riwayatpenjualan = root.findViewById(R.id.riwayatpesanan);
        badgebaru        = root.findViewById(R.id.badgebaru);
        badgedikirim     = root.findViewById(R.id.badgedikirim);
        badgeselesai     = root.findViewById(R.id.badgeselesai);

        riwayatpenjualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), Penjualan.class);
                getActivity().startActivity(in);
            }
        });

        baru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), Penjualan.class);
                getActivity().startActivity(in);
            }
        });

        dikirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), Penjualan.class);
                getActivity().startActivity(in);
            }
        });

        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), Penjualan.class);
                getActivity().startActivity(in);
            }
        });

        return root;
    }

    private void pendapatan()
    {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_PENDAPATAN + Preferences.getKeyIdPengguna(getActivity()),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        FormatCurrency currency = new FormatCurrency();
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);

                                String strTotal = data.getString("total");
                                String strJual  = data.getString("beli");

                                if (strJual.equals("0")){
                                    pendapatan.setText(currency.formatRupiah("000000"));
                                }else {
                                    pendapatan.setText(currency.formatRupiah(strTotal));
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

    private void pengeluaran()
    {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_PENGELUARAN + Preferences.getKeyIdPengguna(getActivity()),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        FormatCurrency currency = new FormatCurrency();
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);

                                String strPengeluaran = data.getString("total");
                                String strBeli        = data.getString("beli");

                                if (strBeli.equals("0")){
                                    pengeluaran.setText(currency.formatRupiah("000000"));
                                }else {
                                    pengeluaran.setText(currency.formatRupiah(strPengeluaran));
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

    private void pesananbaru()
    {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_PENJUALANBARU + Preferences.getKeyIdPengguna(getActivity()),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);

                                String strTotal = data.getString("total");

                                if (strTotal.equals("0")){
                                    badgebaru.setVisibility(View.GONE);
                                }else {
                                    badgebaru.setVisibility(View.VISIBLE);
                                    badgebaru.setText(strTotal);
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
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_PENJUALANDIKIRIM + Preferences.getKeyIdPengguna(getActivity()),null,
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

    private void pesananselesai()
    {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_PENJUALANSELESAI +  Preferences.getKeyIdPengguna(getActivity()),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);

                                String strTotal = data.getString("total");

                                if (strTotal.equals("0")){
                                    badgeselesai.setVisibility(View.GONE);
                                }else {
                                    badgeselesai.setVisibility(View.VISIBLE);
                                    badgeselesai.setText(strTotal);
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
        pendapatan();
        pengeluaran();
        pesananbaru();
        pesanandikirim();
        pesananselesai();
    }

}