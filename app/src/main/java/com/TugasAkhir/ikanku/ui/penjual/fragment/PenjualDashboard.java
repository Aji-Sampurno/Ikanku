package com.TugasAkhir.ikanku.ui.penjual.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.TugasAkhir.ikanku.adapter.AdapterKategori;
import com.TugasAkhir.ikanku.model.ModelDataKategori;
import com.TugasAkhir.ikanku.util.ServerApi;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.TugasAkhir.ikanku.R;
import com.TugasAkhir.ikanku.ui.SessionManager;
import com.TugasAkhir.ikanku.adapter.AdapterBarang;
import com.TugasAkhir.ikanku.model.ModelDataProduk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PenjualDashboard extends Fragment {

    private static final String TAG = PenjualProduk.class.getSimpleName();
    RecyclerView mRecyclerview, mRecyclerviewK;
    RecyclerView.Adapter mAdapter, mAdapterK;
    RecyclerView.LayoutManager mManager, mManagerK;
    SwipeRefreshLayout refreshLayout;
    List<ModelDataProduk> mItems;
    List<ModelDataKategori> mItemsK;
    SessionManager sessionManager;
    String getId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_penjual_dashboard, container, false);
        mRecyclerview = root.findViewById(R.id.recyclerviewBarang);
        mRecyclerviewK = root.findViewById(R.id.recyclerviewKategori);
        refreshLayout = root.findViewById(R.id.swiperefresh);
        mItems = new ArrayList<>();
        mItemsK = new ArrayList<>();

        loadJson(true);
        loadKategori(true);

        sessionManager = new SessionManager(getActivity());
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID_PENGGUNA);

        mManager = new GridLayoutManager(getActivity(),2);
        mRecyclerview.setLayoutManager(mManager);
        mAdapter = new AdapterBarang(getActivity(),mItems);
        mRecyclerview.setAdapter(mAdapter);

        mManagerK = new GridLayoutManager(getActivity(),3);
        LinearLayoutManager horizontal = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        mRecyclerviewK.setLayoutManager(horizontal);
        mAdapterK = new AdapterKategori(getActivity(),mItemsK);
        mRecyclerviewK.setAdapter(mAdapterK);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                DaftarProduk();
                loadJson(false);
            }
        });
        return root;
    }

    private void loadJson(boolean showProgressDialog) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        if (showProgressDialog) progressDialog.show();
        else progressDialog.cancel();

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_DAFTARBARANG,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (showProgressDialog) progressDialog.cancel();
                        else refreshLayout.setRefreshing(false);
                        mItems.clear();
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                ModelDataProduk md = new ModelDataProduk();
                                md.setIdProduk(data.getString("idproduk"));
                                md.setIdPenjual(data.getString("idpenjual"));
                                md.setNamaProduk(data.getString("namaproduk"));
                                md.setStok(data.getString("stok"));
                                md.setHargaProduk(data.getString("hargaproduk"));
                                md.setDeskripsi(data.getString("deskripsi"));
                                md.setGambarproduk(data.getString("gambarproduk"));
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
                        progressDialog.dismiss();
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

        queue.add(reqData);
    }

    private void loadKategori(boolean showProgressDialog) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        if (showProgressDialog) progressDialog.show();
        else progressDialog.cancel();

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_DAFTARKATEGORI,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (showProgressDialog) progressDialog.cancel();
                        else refreshLayout.setRefreshing(false);
                        mItemsK.clear();
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                ModelDataKategori md = new ModelDataKategori();
                                md.setIdkategori(data.getString("idkategori"));
                                md.setNamakategori(data.getString("namakategori"));
                                md.setGambarkategori(data.getString("gambarkategori"));
                                mItemsK.add(md);
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
                        mAdapterK.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
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
        queue.add(reqData);
    }
}