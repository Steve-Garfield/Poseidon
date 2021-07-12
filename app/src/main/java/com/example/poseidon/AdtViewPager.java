package com.example.poseidon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AdtViewPager extends FragmentPagerAdapter {
    public AdtViewPager(@NonNull FragmentManager fm) {
        super(fm);
    }

    // 프래그먼트 교체를 보여주는 처리를 구현한 곳

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FragCategoryAll.newInstance();
            case 1:
                return FragCategoryCoffee.newInstance();
            case 2:
                return FragCategoryRamen.newInstance();
            case 3:
                return FragCategoryTea.newInstance();
            case 4:
                return FragCategoryEtc.newInstance();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 5;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "전체";
            case 1:
                return "커피";
            case 2:
                return "라면";
            case 3:
                return "차";
            case 4:
                return "기타";
            default:
                return null;
        }
    }
}