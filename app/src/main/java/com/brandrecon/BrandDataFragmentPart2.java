package com.brandrecon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class BrandDataFragmentPart2 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String companyName;

    RecyclerView recyclerView;
    BrandDataPartCardAdapter2 brandDataPartCardAdapter2;

    public BrandDataFragmentPart2() {
        // Required empty public constructor
    }

    public static BrandDataFragmentPart2 newInstance(String param1, String param2) {
        BrandDataFragmentPart2 fragment = new BrandDataFragmentPart2();
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
        View view = inflater.inflate(R.layout.fragment_brand_data_part2, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle bundle = getArguments();
        if (bundle != null) {
            companyName = bundle.getString("brandName");
        }
//        String companyName = getActivity().getIntent().getStringExtra("brandName");

        //String companyName="Google";
        Query query = FirebaseDatabase.getInstance().getReference().child("companies")
                .orderByChild("name").equalTo(companyName);

        FirebaseRecyclerOptions<BrandDataCard> options =
                new FirebaseRecyclerOptions.Builder<BrandDataCard>()
                        .setQuery(query, BrandDataCard.class)
                        .build();

        brandDataPartCardAdapter2 = new BrandDataPartCardAdapter2(options,getContext());
        recyclerView.setAdapter(brandDataPartCardAdapter2);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        brandDataPartCardAdapter2.startListening();
    }   
    @Override
    public void onStop() {
        super.onStop();
        brandDataPartCardAdapter2.stopListening();

    }
}



