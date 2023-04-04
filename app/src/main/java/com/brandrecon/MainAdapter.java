package com.brandrecon;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainAdapter extends FragmentPagerAdapter {
    private Context context;
    int totalTabs;

    public MainAdapter(FragmentManager fm, Context context,int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }

    public Fragment getItem(int position){
        switch (position){
            case 0:
                BrandDataFragmentPart1 brandDataFragmentPart1=new BrandDataFragmentPart1();
                return brandDataFragmentPart1;
            case 1:
                BrandDataFragmentPart2 brandDataFragmentPart2=new BrandDataFragmentPart2();
                return brandDataFragmentPart2;
            default:
                return null;
        }
    }
}
