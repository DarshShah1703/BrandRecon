package com.brandrecon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class LogoDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_details);

        String brandName = getIntent().getStringExtra("name");
        Bundle bundle = new Bundle();
        bundle.putString("brandName",brandName);

        BrandDataFragment brandDataFragment = new BrandDataFragment();
        brandDataFragment.setArguments(bundle);


//                Intent intent = new Intent(SearchByNameActivity.this, BrandDataFragment.class);
//                intent.putExtra("brandName", brandName);
        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,brandDataFragment ).commit();
    }
}