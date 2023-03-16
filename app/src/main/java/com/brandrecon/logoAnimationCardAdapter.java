package com.brandrecon;

import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class logoAnimationCardAdapter extends FirebaseRecyclerAdapter<BrandDataCard,logoAnimationCardAdapter.logoAnimationViewHolder> {


    public logoAnimationCardAdapter(@NonNull FirebaseRecyclerOptions<BrandDataCard> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull logoAnimationViewHolder holder, int position, @NonNull BrandDataCard model) {

        holder.setVideo(model);

    }

    @NonNull
    @Override
    public logoAnimationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_logo_animation,parent,false);

        return new logoAnimationViewHolder(view);
    }

    class logoAnimationViewHolder extends RecyclerView.ViewHolder{

        VideoView logoAnimation;
        CardView logoAnimationCardView;

        public logoAnimationViewHolder(@NonNull View itemView) {
            super(itemView);

            logoAnimation = (VideoView) itemView.findViewById(R.id.logoAnimation);
            logoAnimationCardView = (CardView) itemView.findViewById(R.id.logoAnimationCardView);
        }

        void setVideo(BrandDataCard obj){

            logoAnimation.setVideoPath(obj.getLogoAnimationUrl());
            logoAnimation.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });

            logoAnimation.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                   mediaPlayer.stop();
                   logoAnimationCardView.setVisibility(View.GONE);

                }
            });
            

            
        }
    }
}
