package com.brandrecon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

public class LogoDetailsActivity extends AppCompatActivity {
    private View decorView;
    TabLayout tabLayout;
    ViewPager viewPager;
    MyFragmentPagerAdapter myFragmentPagerAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_details);
        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int i) {
                if (i == 0){
                    decorView.setSystemUiVisibility(hideSystemBars());
                }
            }
        });
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        BrandDataFragmentPart1 brandDataFragmentPart1 = new BrandDataFragmentPart1();
        BrandDataFragmentPart2 brandDataFragmentPart2 = new BrandDataFragmentPart2();
        
        String brandName = getIntent().getStringExtra("brandName");
        Bundle bundleForFragment1 = new Bundle();
        bundleForFragment1.putString("brandName", brandName);

        Bundle bundleForFragment2 = new Bundle();
        bundleForFragment2.putString("brandName", brandName);
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        myFragmentPagerAdapter.addFragment(brandDataFragmentPart1, "Tab 1");
        myFragmentPagerAdapter.addFragment(brandDataFragmentPart2, "Tab 2");
        brandDataFragmentPart1.setArguments(bundleForFragment1);
        brandDataFragmentPart2.setArguments(bundleForFragment2);
        viewPager.setAdapter(myFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

//        BrandDataFragment brandDataFragment = new BrandDataFragment();
//        brandDataFragment.setArguments(bundle);


//                Intent intent = new Intent(SearchByNameActivity.this, BrandDataFragment.class);
//                intent.putExtra("brandName", brandName);
//        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,brandDataFragment ).commit();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }

    private int hideSystemBars(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }
}