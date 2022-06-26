package com.TugasAkhir.ikanku.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.TugasAkhir.ikanku.R;
import com.TugasAkhir.ikanku.ui.laporan.fragment.FragmentAdapterLaporan;
import com.google.android.material.tabs.TabLayout;

public class Laporan extends AppCompatActivity {

    TabLayout tablayoutlaporan;
    ViewPager2 viewPager2;
    FragmentAdapterLaporan fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);

        tablayoutlaporan = findViewById(R.id.tablaporan);
        viewPager2 = findViewById(R.id.viewlaporan);

        FragmentManager fm = getSupportFragmentManager();
        fragmentAdapter = new FragmentAdapterLaporan(fm, getLifecycle());
        viewPager2.setAdapter(fragmentAdapter);

        tablayoutlaporan.addTab(tablayoutlaporan.newTab().setText("Pendapatan"));
        tablayoutlaporan.addTab(tablayoutlaporan.newTab().setText("Pengeluaran"));

        tablayoutlaporan.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
                tablayoutlaporan.selectTab(tablayoutlaporan.getTabAt(position));
            }
        });
    }
}