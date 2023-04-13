package com.brandrecon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.tabs.TabLayout;


public class SearchByNameActivity extends AppCompatActivity{
    private View decorView;

    TabLayout tabLayout;
    ViewPager viewPager;
    MyFragmentPagerAdapter myFragmentPagerAdapter;
    SearchView searchName;
    ListView suggestionList;
    ArrayAdapter<String> adapter;

    int status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_by_name);

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



        searchName = (SearchView) findViewById(R.id.searchName);
        suggestionList =findViewById(R.id.suggestionList);
        suggestionList.setVisibility(View.GONE);
        String[] brandNames = {"Amazon.com Inc.","Bayerische Motoren Werke AG","Apple Inc.","Balaji Wafers","Burger King Corporation","Chipotle Mexican Grill", "Coca Cola","Dell Inc.","Dominos Pizza Inc.", "Flipkart Private Limited", "Google LLC", "HDFC Bank","HP Inc.", "ICICI Bank", "Instagram" ,"Kentucky Fried Chicken Corporation", "Kotak Mahindra Bank Ltd", "McDonalds Corporation", "Mercedes Benz Group AG", "Meta Platforms Inc." ,"Microsoft Corporation", "Nestle S.A.", "OnePlus","PepsiCo Inc.", "Pizza Hut", "Raymond Ltd", "State Bank of India", "Samsung Electronics Co. Ltd." ,"Starbucks Corporation","Walmart Inc."};


        adapter = new ArrayAdapter<>(this,R.layout.list_row,brandNames);
        suggestionList.setAdapter(adapter);

        suggestionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                searchName.setQuery(suggestionList.getAdapter().getItem(i).toString(),true);
            }
        });


        BrandDataFragmentPart1 brandDataFragmentPart1 = new BrandDataFragmentPart1();
        BrandDataFragmentPart2 brandDataFragmentPart2 = new BrandDataFragmentPart2();
        searchName.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String brandName = searchName.getQuery().toString();
//                Bundle bundle = new Bundle();
//                bundle.putString("brandName",brandName);

//                LogoAnimationFragment logoAnimationFragment =new LogoAnimationFragment();
//                getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,logoAnimationFragment).commit();
                Bundle bundleForFragment1 = new Bundle();
                bundleForFragment1.putString("brandName", brandName);

                Bundle bundleForFragment2 = new Bundle();
                bundleForFragment2.putString("brandName", brandName);
                //int currentTab = viewPager.getCurrentItem();


                myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
                myFragmentPagerAdapter.addFragment(brandDataFragmentPart1, "Tab 1");
                myFragmentPagerAdapter.addFragment(brandDataFragmentPart2, "Tab 2");
                brandDataFragmentPart1.setArguments(bundleForFragment1);
                brandDataFragmentPart2.setArguments(bundleForFragment2);
                viewPager.setAdapter(myFragmentPagerAdapter);
                tabLayout.setupWithViewPager(viewPager);
                

//                BrandDataFragment brandDataFragment = new BrandDataFragment();
//                brandDataFragment.setArguments(bundle);
//                getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,brandDataFragment ).commit();
                searchName.setQuery("",false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.isEmpty()){
                    suggestionList.setVisibility(View.GONE);
                }
                else{
                    suggestionList.setVisibility(View.VISIBLE);
                }
                adapter.getFilter().filter(s);
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        status = 1;
        super.onResume();
    }

    @Override
    protected void onRestart() {
        status = 1;
        super.onRestart();
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