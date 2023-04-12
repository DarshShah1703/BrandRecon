package com.brandrecon;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class BrandDataFragmentPart1 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    BrandDataPartCardAdapter1 brandDataCardAdapter;


    String companyName;
    TextView demo;



    public BrandDataFragmentPart1() {
        // Required empty public constructor
    }

    public static BrandDataFragmentPart1 newInstance(String param1, String param2) {
        BrandDataFragmentPart1 fragment = new BrandDataFragmentPart1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
//            companyName = getArguments().getString("brandName");
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_brand_data_part1, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        demo = view.findViewById(R.id.demo);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        if(companyName.isEmpty()){
//            recyclerView.setVisibility(View.INVISIBLE);
//        }
        Bundle bundle = getArguments();
        if (bundle != null) {
            companyName = bundle.getString("brandName");
        }
        Query query = FirebaseDatabase.getInstance().getReference().child("companies")
                .orderByChild("name").equalTo(companyName);

        FirebaseRecyclerOptions<BrandDataCard> options =
                new FirebaseRecyclerOptions.Builder<BrandDataCard>()
                        .setQuery(query, BrandDataCard.class)
                        .build();

        brandDataCardAdapter = new BrandDataPartCardAdapter1(options,getContext());
        recyclerView.setAdapter(brandDataCardAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        brandDataCardAdapter.startListening();
//        logoAnimationCardAdapter.startListening();


    }   
    @Override
    public void onStop() {
        super.onStop();
        brandDataCardAdapter.stopListening();
//        logoAnimationCardAdapter.stopListening();
    }
}



