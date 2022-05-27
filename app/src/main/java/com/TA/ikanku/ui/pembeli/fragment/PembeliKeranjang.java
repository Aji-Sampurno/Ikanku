package com.TA.ikanku.ui.pembeli.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.TA.ikanku.Preferences;
import com.TA.ikanku.R;
import com.TA.ikanku.adapter.AdapterKeranjang;
import com.TA.ikanku.model.ModelDataKeranjang;
import com.TA.ikanku.ui.Keranjang;
import com.TA.ikanku.ui.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PembeliKeranjang extends Fragment {

    private static final String TAG = Keranjang.class.getSimpleName();
    RecyclerView mRecyclerview;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    SwipeRefreshLayout refreshLayout;
    List<ModelDataKeranjang> mItems;
    SessionManager sessionManager;
    String getId;
    private static String URL_DAFTARKERANJANG="https://jualanikan.000webhostapp.com/Penjual/ListKeranjang?idpengguna=";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_pembeli_keranjang, container, false);

        mRecyclerview = root.findViewById(R.id.recyclerviewKeranjang);
        refreshLayout = root.findViewById(R.id.swiperefresh);
        mItems = new ArrayList<>();
        loadJson(true);

        sessionManager = new SessionManager(getActivity());
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID_PENGGUNA);

        mManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRecyclerview.setLayoutManager(mManager);
        mAdapter = new AdapterKeranjang(getActivity(), mItems);
        mRecyclerview.setAdapter(mAdapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadJson(false);
            }
        });
        return root;
    }

    private void loadJson(boolean showProgressDialog)
    {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        if (showProgressDialog) progressDialog.show();
        else progressDialog.cancel();

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, URL_DAFTARKERANJANG +  Preferences.getKeyIdPengguna(getActivity()),null,
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
                                ModelDataKeranjang md = new ModelDataKeranjang();
                                md.setIdProduk(data.getString("idproduk"));
                                md.setIdPenjual(data.getString("idpenjual"));
                                md.setNamaProduk(data.getString("namaproduk"));
                                md.setStok(data.getString("stok"));
                                md.setHargaProduk(data.getString("hargaproduk"));
                                md.setDeskripsi(data.getString("deskripsi"));
                                md.setIdkeranjang(data.getString("idkeranjang"));
                                md.setIdpengguna(data.getString("idpengguna"));
                                md.setJumlah(data.getString("jumlah"));
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

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Kesalahan Jaringan").
                                setIcon(R.mipmap.ic_kesalahan_jaringan_foreground).
                                setMessage("Terdapat kesalahan jaringan saat memuat data");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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