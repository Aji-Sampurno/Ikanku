package com.TA.ikanku.ui.penjualan.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentAdapterPenjualan extends FragmentStateAdapter {
    public FragmentAdapterPenjualan(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 1:
                return new PenjualanDikirim();
            case 2:
                return new PenjualanSelesai();
        }

        return new PenjualanBaru();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
