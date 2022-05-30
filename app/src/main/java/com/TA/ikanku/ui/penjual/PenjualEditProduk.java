package com.TA.ikanku.ui.penjual;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.TA.ikanku.R;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PenjualEditProduk extends AppCompatActivity {

    private static final String TAG = PenjualEditProduk.class.getSimpleName();
    TextView idproduk;
    EditText namaproduk,stok,hargaproduk,deskripsi;
    Button btnhapus;
    private ProgressBar loading;
    ProgressDialog pd;
    String getId;
    Bitmap bitmap;
    private Menu action;
    final int CODE_GALLERY_REQUEST = 999;
//    private static String URL_DETAILPRODUK="https://jualanikan.000webhostapp.com/Penjual/DetailProduk?idproduk=";
    private static String URL_EDITPRODUK="https://jualanikan.000webhostapp.com/Penjual/EditProduk";
    private static String URL_DELETEPRODUK="https://jualanikan.000webhostapp.com/Penjual/DeleteProduk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjual_edit_produk);

        Intent data = getIntent();
        final int update          = data.getIntExtra("update",0);
        String intentidproduk     = data.getStringExtra("idproduk");
        String intentidpenjual    = data.getStringExtra("idpenjual");
        String intentnamaproduk   = data.getStringExtra("namaproduk");
        String intentstok         = data.getStringExtra("stok");
        String intenthargaproduk  = data.getStringExtra("hargaproduk");
        String intentdeskripsi    = data.getStringExtra("deskripsi");
        String intentgambarproduk = data.getStringExtra("gambarproduk");

        idproduk = (TextView) findViewById(R.id.editidproduk);
        namaproduk = (EditText) findViewById(R.id.editnamaproduk);
        stok = (EditText) findViewById(R.id.editstok);
        hargaproduk = (EditText) findViewById(R.id.edithargaproduk);
        deskripsi = (EditText) findViewById(R.id.editdeskripsi);
        btnhapus = (Button) findViewById(R.id.btn_hapus);
        pd = new ProgressDialog(PenjualEditProduk.this);

        if(update == 1)
        {
            idproduk.setText(intentidproduk);
            namaproduk.setText(intentnamaproduk);
            stok.setText(intentstok);
            hargaproduk.setText(intenthargaproduk);
            deskripsi.setText(intentdeskripsi);
        }

//        loadJson(intentidproduk);

        btnhapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PenjualEditProduk.this);
                builder.setTitle("Konfirmasi Hapus Produk").
                        setIcon(R.mipmap.ic_confirm_foreground).
                        setMessage("apakah kamu yakin untuk menghapus produk ini ?");
                builder.setPositiveButton("Ya",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Delete_data();
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
    }

//    private void loadJson(String intentidproduk)
//    {
//        pd.setMessage("Memuat...");
//        pd.setCancelable(false);
//        pd.show();
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//        StringRequest updateReq = new StringRequest(Request.Method.GET, URL_DETAILPRODUK + intentidproduk,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        pd.cancel();
//                        try {
//                            JSONArray jArray = new JSONArray(response);
//                            for(int i=0;i<jArray.length();i++){
//                                JSONObject res = jArray.getJSONObject(i);
//                                idproduk.setText(res.getString("idproduk").trim());
//                                namaproduk.setText(res.getString("namaproduk").trim());
//                                stok.setText(res.getString("stok").trim());
//                                hargaproduk.setText(res.getString("hargaproduk").trim());
//                                deskripsi.setText(res.getString("deskripsi").trim());
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//
//                            AlertDialog.Builder builder = new AlertDialog.Builder(PenjualEditProduk.this);
//                            builder.setTitle("Kesalahan Memuat").
//                                    setIcon(R.mipmap.ic_warning_foreground).
//                                    setMessage("Terdapat Kesalahan saat memuat data");
//                            builder.setPositiveButton("OK",
//                                    new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            dialog.cancel();
//                                        }
//                                    });
//                            AlertDialog alert11 = builder.create();
//                            alert11.show();
//
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        pd.cancel();
//
//                        AlertDialog.Builder builder = new AlertDialog.Builder(PenjualEditProduk.this);
//                        builder.setTitle("Kesalahan Jaringan").
//                                setIcon(R.mipmap.ic_kesalahan_jaringan_foreground).
//                                setMessage("Terdapat kesalahan jaringan saat memuat data");
//                        builder.setPositiveButton("OK",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        dialog.cancel();
//                                    }
//                                });
//                        AlertDialog alert11 = builder.create();
//                        alert11.show();
//
//                    }
//                }){
//        };
//
//        queue.add(updateReq);
//    }

    private void Update_data()
    {
        pd.setMessage("Menyimpan Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDITPRODUK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);

                            AlertDialog.Builder builder = new AlertDialog.Builder(PenjualEditProduk.this);
                            builder.setTitle("Update Produk").
                                    setIcon(R.mipmap.ic_sukses_foreground).
                                    setMessage("Produk anda berhasil diupdate");
                            builder.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Intent i = new Intent(PenjualEditProduk.this, PenjualMain.class);
                                            startActivity(i);
                                            finish();
                                        }
                                    });
                            AlertDialog alert11 = builder.create();
                            alert11.show();

                        } catch (JSONException e) {
                            e.printStackTrace();

                            AlertDialog.Builder builder = new AlertDialog.Builder(PenjualEditProduk.this);
                            builder.setTitle("Update Produk").
                                    setIcon(R.mipmap.ic_warning_foreground).
                                    setMessage("Produk anda gagal diupdate");
                            builder.setPositiveButton("Ulangi",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert11 = builder.create();
                            alert11.show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();

                        AlertDialog.Builder builder = new AlertDialog.Builder(PenjualEditProduk.this);
                        builder.setTitle("Kesalahan Jaringan").
                                setIcon(R.mipmap.ic_kesalahan_jaringan_foreground).
                                setMessage("Terdapat kesalahan jaringan saat melakukan update");
                        builder.setPositiveButton("Ulangi",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        builder.setNegativeButton("Batal",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent i = new Intent(PenjualEditProduk.this, PenjualMain.class);
                                        startActivity(i);
                                        finish();
                                    }
                                });
                        AlertDialog alert11 = builder.create();
                        alert11.show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("idproduk",idproduk.getText().toString());
                map.put("namaproduk",namaproduk.getText().toString());
                map.put("stok",stok.getText().toString());
                map.put("hargaproduk",hargaproduk.getText().toString());
                map.put("deskripsi",deskripsi.getText().toString());
                System.out.println(map);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void Delete_data()
    {
        pd.setMessage("Menghapus Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DELETEPRODUK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        AlertDialog.Builder builder = new AlertDialog.Builder(PenjualEditProduk.this);
                        builder.setTitle("Hapus Produk").
                                setIcon(R.mipmap.ic_deleted_foreground).
                                setMessage("Produk anda berhasil dihapus");
                        builder.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent i = new Intent(PenjualEditProduk.this, PenjualMain.class);
                                        startActivity(i);
                                        finish();
                                    }
                                });
                        AlertDialog alert11 = builder.create();
                        alert11.show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();

                        AlertDialog.Builder builder = new AlertDialog.Builder(PenjualEditProduk.this);
                        builder.setTitle("Kesalahan Jaringan").
                                setIcon(R.mipmap.ic_kesalahan_jaringan_foreground).
                                setMessage("Terdapat kesalahan jaringan saat melakukan update");
                        builder.setPositiveButton("Ulangi",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        builder.setNegativeButton("Batal",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent i = new Intent(PenjualEditProduk.this, PenjualMain.class);
                                        startActivity(i);
                                        finish();
                                    }
                                });
                        AlertDialog alert11 = builder.create();
                        alert11.show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("idproduk",idproduk.getText().toString());
                System.out.println(map);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_action,menu);

        action = menu;
        action.findItem(R.id.menusave).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuedit:

                namaproduk.setFocusableInTouchMode(true);
                stok.setFocusableInTouchMode(true);
                hargaproduk.setFocusableInTouchMode(true);
                deskripsi.setFocusableInTouchMode(true);

                action.findItem(R.id.menuedit).setVisible(false);
                action.findItem(R.id.menusave).setVisible(true);

                return true;

            case R.id.menusave:

                Update_data();

                action.findItem(R.id.menuedit).setVisible(true);
                action.findItem(R.id.menusave).setVisible(false);

                namaproduk.setFocusableInTouchMode(false);
                hargaproduk.setFocusableInTouchMode(false);
                stok.setFocusableInTouchMode(false);
                deskripsi.setFocusableInTouchMode(false);
                namaproduk.setFocusable(false);
                stok.setFocusable(false);
                hargaproduk.setFocusable(false);
                deskripsi.setFocusable(false);

                return true;

            default:

                return super.onOptionsItemSelected(item);
        }
    }
}