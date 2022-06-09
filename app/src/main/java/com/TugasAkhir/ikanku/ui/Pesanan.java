package com.TugasAkhir.ikanku.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.TugasAkhir.ikanku.R;
import com.TugasAkhir.ikanku.ui.pesanan.fragment.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class Pesanan extends AppCompatActivity {

    TabLayout tablayoutpesanan;
    ViewPager2 viewPager2;
    FragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan);

        tablayoutpesanan = findViewById(R.id.tabpesanan);
        viewPager2 = findViewById(R.id.viewpesanan);

        FragmentManager fm = getSupportFragmentManager();
        fragmentAdapter = new FragmentAdapter(fm, getLifecycle());
        viewPager2.setAdapter(fragmentAdapter);

        tablayoutpesanan.addTab(tablayoutpesanan.newTab().setText("Dikemas"));
        tablayoutpesanan.addTab(tablayoutpesanan.newTab().setText("Dikirim"));
        tablayoutpesanan.addTab(tablayoutpesanan.newTab().setText("Diterima"));

        tablayoutpesanan.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tablayoutpesanan.selectTab(tablayoutpesanan.getTabAt(position));
            }
        });

    }
}