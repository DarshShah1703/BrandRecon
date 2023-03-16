package com.brandrecon;

import android.app.Application;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class BrandDataCardAdapter extends FirebaseRecyclerAdapter<BrandDataCard,BrandDataCardAdapter.BrandHolder> {

    public BrandDataCardAdapter(@NonNull FirebaseRecyclerOptions<BrandDataCard> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BrandHolder holder, int position, @NonNull BrandDataCard model) {
        holder.setVideo(model);
    }

    @NonNull
    @Override
    public BrandHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_info,parent,false);
        return new BrandHolder(view);
    }

    public static class BrandHolder extends RecyclerView.ViewHolder {
        TextView name, ceo, field, netWorth, parentCompany, revenue, visitAt;
        String logoUrl;
        ImageView logo;
       // VideoView logoAnimation;

        LinearLayout layoutVideo,layoutDetails;
        public BrandHolder(@NonNull View itemView) {

            super(itemView);
            name = itemView.findViewById(R.id.brandName);
            ceo = itemView.findViewById(R.id.brandCeo);
            field = itemView.findViewById(R.id.brandField);
            netWorth =itemView.findViewById(R.id.brandNetWorth);
            parentCompany = itemView.findViewById(R.id.brandParentCompany);
            logoUrl = "";
            revenue = itemView.findViewById(R.id.brandRevenue);
            visitAt = itemView.findViewById(R.id.visitAt);
            logo = itemView.findViewById(R.id.brandLogo);

            //logoAnimation = (VideoView) itemView.findViewById(R.id.logoAnimation);
            //layoutVideo =itemView.findViewById(R.id.layoutVideo);
            layoutDetails =itemView.findViewById(R.id.layoutDetails);
        }

        void setVideo(BrandDataCard obj){


            name.setText(obj.getName());
            ceo.setText(obj.getCeo());
            field.setText(obj.getField());
            netWorth.setText(obj.getNetWorth());
            parentCompany.setText(obj.getParentCompany());
            Picasso.get().load(obj.getLogoUrl()).into(logo);
            revenue.setText(obj.getRevenue());
            visitAt.setText(obj.getVisitAt());
//            logoAnimation.setVideoPath(obj.getLogoAnimationUrl());
//            logoAnimation.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mediaPlayer) {
//                    mediaPlayer.start();
//                }
//            });
//
//            logoAnimation.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mediaPlayer) {
//                    mediaPlayer.start();
//                    //itemView.setVisibility(View.GONE);
//                }
//            });
        }
    }
}
