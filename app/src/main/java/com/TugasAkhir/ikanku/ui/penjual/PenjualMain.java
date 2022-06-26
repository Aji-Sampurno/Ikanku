package com.TugasAkhir.ikanku.ui.penjual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.TugasAkhir.ikanku.util.ServerApi;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.TugasAkhir.ikanku.Preferences;
import com.TugasAkhir.ikanku.ui.Akun;
import com.TugasAkhir.ikanku.ui.Keranjang;
import com.TugasAkhir.ikanku.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PenjualMain extends AppCompatActivity {

    MenuItem menuItem;
    TextView badgeCounter;
    private Menu action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjual_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_laporan, R.id.navigation_edukasi, R.id.navigation_dashboard, R.id.navigation_jadwal, R.id.navigation_produk)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.penjual_menu,menu);

        menuItem = menu.findItem(R.id.menukeranjang);
        action = menu;

        RequestQueue queue = Volley.newRequestQueue(PenjualMain.this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_KERANJANG +  Preferences.getKeyIdPengguna(PenjualMain.this),null,
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

                                    RequestQueue queue = Volley.newRequestQueue(PenjualMain.this);
                                    JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_TEMP +  Preferences.getKeyIdPengguna(PenjualMain.this),null,
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
                                                                menuItem.setActionView(R.layout.badge_notifikasi);
                                                                View view = menuItem.getActionView();
                                                                badgeCounter = view.findViewById(R.id.badge_counter);
                                                                badgeCounter.setText(strTotal);

                                                                view.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {
                                                                        keranjang();
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

                                } else {
                                    menuItem.setActionView(R.layout.badge_notifikasi);
                                    View view = menuItem.getActionView();
                                    badgeCounter = view.findViewById(R.id.badge_counter);
                                    badgeCounter.setText(strTotal);

                                    view.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            keranjang();
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

    public void akun(){
        Intent intent = new Intent(PenjualMain.this, Akun.class);
        PenjualMain.this.startActivity(intent);
    }

    public void keranjang(){
        Intent intent = new Intent(PenjualMain.this, Keranjang.class);
        PenjualMain.this.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuakun:

                akun();

                return true;

            case R.id.menukeranjang:

                keranjang();

                return true;

            default:

                return super.onOptionsItemSelected(item);
        }
    }
}