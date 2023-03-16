package com.brandrecon;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class SearchByNameActivity extends AppCompatActivity {

    SearchView searchName;
    ListView suggestionList;
    ArrayAdapter<String> adapter;
    int status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_name);

        searchName = findViewById(R.id.SearchName);
        suggestionList =findViewById(R.id.suggestionList);
        suggestionList.setVisibility(View.GONE);
        String[] brandNames = {"Amazon","Amazon","Apple Inc.","Bayerische Motoren Werke AG"};


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,brandNames);
        suggestionList.setAdapter(adapter);

        suggestionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                searchName.setQuery(suggestionList.getAdapter().getItem(i).toString(),true);
            }
        });

        searchName.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String brandName = searchName.getQuery().toString();
                Bundle bundle = new Bundle();
                bundle.putString("brandName",brandName);

//                LogoAnimationFragment logoAnimationFragment =new LogoAnimationFragment();
//                getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,logoAnimationFragment).commit();

                BrandDataFragment brandDataFragment = new BrandDataFragment();
                brandDataFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,brandDataFragment ).commit();
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
}