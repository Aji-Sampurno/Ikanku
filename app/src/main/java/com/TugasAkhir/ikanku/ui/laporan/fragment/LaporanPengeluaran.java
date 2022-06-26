package com.TugasAkhir.ikanku.ui.laporan.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.TugasAkhir.ikanku.Preferences;
import com.TugasAkhir.ikanku.R;
import com.TugasAkhir.ikanku.adapter.AdapterPendapatan;
import com.TugasAkhir.ikanku.model.ModelDataLaporan;
import com.TugasAkhir.ikanku.ui.SessionManager;
import com.TugasAkhir.ikanku.util.FormatCurrency;
import com.TugasAkhir.ikanku.util.ServerApi;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LaporanPengeluaran extends Fragment {

    EditText                    awal, akhir;
    TextView                    dibeli,pengeluaran;
    Button                      cari;
    RecyclerView                laporan;
    ProgressBar                 loading;
    RecyclerView.Adapter        mAdapter;
    RecyclerView.LayoutManager  mManager;
    List<ModelDataLaporan>      mItems;
    SessionManager              sessionManager;
    String                      getId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_laporan_pengeluaran, container, false);



        awal        = root.findViewById(R.id.inp_awal);
        akhir       = root.findViewById(R.id.inp_akhir);
        cari        = root.findViewById(R.id.btncari);
        laporan     = root.findViewById(R.id.rvlaporan);
        pengeluaran = root.findViewById(R.id.pengeluaranproduk);
        dibeli      = root.findViewById(R.id.produkdibeli);
//        loading  = root.findViewById(R.id.loading);

        mItems      = new ArrayList<>();
        mManager    = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mAdapter    = new AdapterPendapatan(getActivity(),mItems);

        laporan.setLayoutManager(mManager);
        laporan.setAdapter(mAdapter);

        sessionManager  = new SessionManager(getActivity());
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID_PENGGUNA);

        datalaporan();
        datadibeli();
        datapengeluaran();

        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mawal  = awal.getText().toString().trim();
                String makhir = akhir.getText().toString().trim();

                if (!mawal.isEmpty() || !makhir.isEmpty()){
//                    datalaporan(mawal,makhir);
                } else {
                    awal.setError("Mohon Masukkan Tanggal");
                    akhir.setError("Mohon Masukkan Tanggal");
                }
            }
        });

        return root;
    }

    private void datalaporan() {
//        loading.setVisibility(View.VISIBLE);
        cari.setVisibility(View.GONE);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_DAFTARFILTERPENGELUARAN + getId,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        loading.setVisibility(View.GONE);
//                        cari.setVisibility(View.VISIBLE);
                        mItems.clear();
                        for(int i = 0 ; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                ModelDataLaporan md = new ModelDataLaporan();
                                md.setIdproduk(data.getString("idproduk"));
                                md.setIdpenjual(data.getString("idpenjual"));
                                md.setNamaproduk(data.getString("namaproduk"));
                                md.setStok(data.getString("stok"));
                                md.setHargaproduk(data.getString("hargaproduk"));
                                md.setDeskripsi(data.getString("deskripsi"));
                                md.setGambarproduk(data.getString("gambarproduk"));
                                md.setIdpesanan(data.getString("idpesanan"));
                                md.setInvoice(data.getString("invoice"));
                                md.setPembeli(data.getString("pembeli"));
                                md.setJumlah(data.getString("jumlah"));
                                md.setBayar(data.getString("bayar"));
                                md.setPengiriman(data.getString("pengiriman"));
                                md.setAlamat(data.getString("alamat"));
                                md.setTelp(data.getString("telp"));
                                md.setStatus(data.getString("status"));
                                mItems.add(md);
                            } catch (JSONException e) {
                                e.printStackTrace();

                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("Kesalahan Memuat").
                                        setIcon(R.mipmap.ic_warning_foreground).
                                        setMessage("Terdapat Kesalahan saat memuat data");
                                builder.setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alert11 = builder.create();
                                alert11.show();

                            }
                        }

                        mAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        loading.setVisibility(View.GONE);
//                        cari.setVisibility(View.VISIBLE);
                        error.printStackTrace();

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Kesalahan Jaringan").
                                setIcon(R.mipmap.ic_kesalahan_jaringan_foreground).
                                setMessage("Terdapat Kesalahan jaringan saat memuat data");
                        builder.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert11 = builder.create();
                        alert11.show();

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(reqData);
    }

    private void datadibeli() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_PRODUKDIBELI + Preferences.getKeyIdPengguna(getActivity()),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        FormatCurrency currency = new FormatCurrency();
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);

                                String strJual  = data.getString("beli");

                                if (strJual.equals("0")){
                                    dibeli.setText("0");
                                }else {
                                    dibeli.setText(strJual);
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

    private void datapengeluaran() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_PRODUKPENGELUARAN + Preferences.getKeyIdPengguna(getActivity()),null,
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
                                    pengeluaran.setText(currency.formatRupiah("000000"));
                                }else {
                                    pengeluaran.setText(currency.formatRupiah(strTotal));
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
}