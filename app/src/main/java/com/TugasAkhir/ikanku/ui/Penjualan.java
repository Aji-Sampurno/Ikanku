package com.TugasAkhir.ikanku.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.TugasAkhir.ikanku.R;
import com.TugasAkhir.ikanku.ui.penjualan.fragment.FragmentAdapterPenjualan;
import com.google.android.material.tabs.TabLayout;

public class Penjualan extends AppCompatActivity {

    TabLayout tablayoutpenjualan;
    ViewPager2 viewPager2;
    FragmentAdapterPenjualan fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan);

        tablayoutpenjualan = findViewById(R.id.tabpenjualan);
        viewPager2 = findViewById(R.id.viewpenjualan);

        FragmentManager fm = getSupportFragmentManager();
        fragmentAdapter = new FragmentAdapterPenjualan(fm, getLifecycle());
        viewPager2.setAdapter(fragmentAdapter);

        tablayoutpenjualan.addTab(tablayoutpenjualan.newTab().setText("Baru"));
        tablayoutpenjualan.addTab(tablayoutpenjualan.newTab().setText("Dikirim"));
        tablayoutpenjualan.addTab(tablayoutpenjualan.newTab().setText("Selesai"));

        tablayoutpenjualan.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
                tablayoutpenjualan.selectTab(tablayoutpenjualan.getTabAt(position));
            }
        });

    }
}