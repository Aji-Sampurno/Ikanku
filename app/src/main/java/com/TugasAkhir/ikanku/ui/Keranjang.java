package com.TugasAkhir.ikanku.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.TugasAkhir.ikanku.ui.penjual.PenjualMain;
import com.TugasAkhir.ikanku.util.ServerApi;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.TugasAkhir.ikanku.Preferences;
import com.TugasAkhir.ikanku.R;
import com.TugasAkhir.ikanku.adapter.AdapterKeranjang;
import com.TugasAkhir.ikanku.model.ModelDataKeranjang;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Keranjang extends AppCompatActivity {

    private static final String TAG = Keranjang.class.getSimpleName();
    MenuItem                    menuItem;
    Menu                        action;
    TextView                    badgeCounter;
    RecyclerView                mRecyclerview;
    RecyclerView.Adapter        mAdapter;
    RecyclerView.LayoutManager  mManager;
    SwipeRefreshLayout          refreshLayout;
    List<ModelDataKeranjang>    mItems;
    SessionManager              sessionManager;
    String                      getId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);

        mRecyclerview = findViewById(R.id.recyclerviewKeranjang);
        refreshLayout = findViewById(R.id.swiperefresh);
        mItems = new ArrayList<>();
        loadJson(true);

        sessionManager = new SessionManager(Keranjang.this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID_PENGGUNA);

        mManager = new LinearLayoutManager(Keranjang.this,LinearLayoutManager.VERTICAL,false);
        mRecyclerview.setLayoutManager(mManager);
        mAdapter = new AdapterKeranjang(Keranjang.this, mItems);
        mRecyclerview.setAdapter(mAdapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadJson(false);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mItems.remove(viewHolder.getAdapterPosition());
                mAdapter.notifyDataSetChanged();
            }
        }).attachToRecyclerView(mRecyclerview);
    }

    private void loadJson(boolean showProgressDialog) {
        final ProgressDialog progressDialog = new ProgressDialog(Keranjang.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        if (showProgressDialog) progressDialog.show();
        else progressDialog.cancel();

        RequestQueue queue = Volley.newRequestQueue(Keranjang.this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_DAFTARKERANJANG +  Preferences.getKeyIdPengguna(Keranjang.this),null,
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
                                md.setKategoriProduk(data.getString("kategoriproduk"));
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

                                AlertDialog.Builder builder = new AlertDialog.Builder(Keranjang.this);
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

                        AlertDialog.Builder builder = new AlertDialog.Builder(Keranjang.this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.pesanan_menu,menu);

        menuItem = menu.findItem(R.id.menucheckout);
        action = menu;

        RequestQueue queue = Volley.newRequestQueue(Keranjang.this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_TEMP +  Preferences.getKeyIdPengguna(Keranjang.this),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);

                                String strTotal = data.getString("total");

                                if (strTotal.equals("0")) {
                                    menuItem.setActionView(null);
                                } else {
                                    menuItem.setActionView(R.layout.badge_checkout);
                                    View view = menuItem.getActionView();
                                    badgeCounter = view.findViewById(R.id.badge_counter);
                                    badgeCounter.setText(strTotal);

                                    view.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            checkout();
                                        }
                                    });
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

        return super.onCreateOptionsMenu(menu);
    }

    public void checkout(){
        Intent intent = new Intent(Keranjang.this, Checkout.class);
        Keranjang.this.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menucheckout:

                checkout();

                return true;

            default:

                return super.onOptionsItemSelected(item);
        }
    }
}