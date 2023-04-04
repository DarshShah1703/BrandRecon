package com.brandrecon;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class BrandDataFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    BrandDataCardAdapter brandDataCardAdapter;
    logoAnimationCardAdapter logoAnimationCardAdapter;
    ConcatAdapter concatAdapter;

    public BrandDataFragment() {
        // Required empty public constructor
    }

    public static BrandDataFragment newInstance(String param1, String param2) {
        BrandDataFragment fragment = new BrandDataFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_brand_data, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle bundle = getArguments();
        String companyName = null;
        if (bundle != null) {
            companyName = bundle.getString("brandName");
        }

        //String companyName="Google";
        Query query = FirebaseDatabase.getInstance().getReference().child("companies")
                .orderByChild("name").equalTo(companyName);

        FirebaseRecyclerOptions<BrandDataCard> options =
                new FirebaseRecyclerOptions.Builder<BrandDataCard>()
                        .setQuery(query, BrandDataCard.class)
                        .build();

        brandDataCardAdapter = new BrandDataCardAdapter(options,getContext());
        recyclerView.setAdapter(brandDataCardAdapter);


//
//        logoAnimationCardAdapter = new logoAnimationCardAdapter(options);
//        concatAdapter = new ConcatAdapter(logoAnimationCardAdapter,brandDataCardAdapter);
//        recyclerView.setAdapter(concatAdapter);

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

//    @Override
//    public void onResume() {
//        super.onResume();
//        brandDataCardAdapter.startListening();
////        logoAnimationCardAdapter.startListening();
//    }
}


