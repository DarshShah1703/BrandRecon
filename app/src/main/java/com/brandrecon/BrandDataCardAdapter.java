package com.brandrecon;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class BrandDataCardAdapter extends FirebaseRecyclerAdapter<BrandDataCard,BrandDataCardAdapter.BrandHolder> {


    Context context;

    public BrandDataCardAdapter(@NonNull FirebaseRecyclerOptions<BrandDataCard> options,Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull BrandHolder holder, int position, @NonNull BrandDataCard model) {
        holder.setData(model);
        holder.setVideo(model);

        setAnimation(holder.itemView,position);
    }

    @NonNull
    @Override
    public BrandHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_info,parent,false);
        return new BrandHolder(view);
    }

    public static class BrandHolder extends RecyclerView.ViewHolder {
        TextView brandCeo,
                brandDescription,
                brandEmployees,
                brandField,
                brandFonded,
                brandFounder,
                brandHeadquarters,
                brandLiveProject,
                brandName,
                brandNetWorth,
                brandParentCompany,
                brandRank,
                brandRankedBy,
                brandRankIn,
                brandRevenue,
                CustomerServiceNo,
                brandStockPrice,
                visitAt;
        String logoUrl,headquartersPhoto,ceoPhoto;
        ImageView brandCeoImg,brandHeadquartersImg,brandLogo;
        VideoView logoAnimation;
        CardView logoAnimationCardView;
        // VideoView logoAnimation;

        //LinearLayout layoutVideo,layoutDetails;
        public BrandHolder(@NonNull View itemView) {

            super(itemView);
            brandCeo = itemView.findViewById(R.id.brandCeo);
            brandDescription = itemView.findViewById(R.id.brandDescription);
            brandEmployees = itemView.findViewById(R.id.brandEmployees);
            brandField =itemView.findViewById(R.id.brandField);
            brandFonded = itemView.findViewById(R.id.brandFonded);
            brandFounder = itemView.findViewById(R.id.brandFounder);
            brandHeadquarters = itemView.findViewById(R.id.brandHeadquarters);
            brandLiveProject = itemView.findViewById(R.id.brandLiveProject);
            brandName = itemView.findViewById(R.id.brandName);
            brandNetWorth = itemView.findViewById(R.id.brandNetWorth);
            brandParentCompany = itemView.findViewById(R.id.brandParentCompany);
            brandRank =itemView.findViewById(R.id.brandRank);
            brandRankedBy = itemView.findViewById(R.id.brandRankedBy);
            brandRankIn = itemView.findViewById(R.id.brandRankIn);
            brandRevenue = itemView.findViewById(R.id.brandRevenue);
            CustomerServiceNo = itemView.findViewById(R.id.CustomerServiceNo);
            brandStockPrice = itemView.findViewById(R.id.brandStockPrice);
            visitAt = itemView.findViewById(R.id.visitAt);

            brandCeoImg = itemView.findViewById(R.id.brandCeoImg);
            brandHeadquartersImg = itemView.findViewById(R.id.brandHeadquartersImg);
            brandLogo = itemView.findViewById(R.id.brandLogo);

            logoAnimation = itemView.findViewById(R.id.logoAnimation);
            logoAnimationCardView = itemView.findViewById(R.id.logoAnimationCardView);


//            brandStockPrice.setAnimation(animation);



            logoUrl = "";
            headquartersPhoto = "";
            ceoPhoto = "";


            //logoAnimation = (VideoView) itemView.findViewById(R.id.logoAnimation);
            //layoutVideo =itemView.findViewById(R.id.layoutVideo);
            //layoutDetails =itemView.findViewById(R.id.layoutDetails);
        }

        void setData(BrandDataCard obj){

            brandCeo.setText(obj.getCeo());
            Picasso.get().load(obj.getCeoPhoto()).into(brandCeoImg);
            brandDescription.setText(obj.getDescription());
            brandEmployees.setText(obj.getEmployees());
            brandField.setText(obj.getField());
            brandFonded.setText(obj.getFounded());
            brandFounder.setText(obj.getFounders());
            brandHeadquarters.setText(obj.getHeadquarters());
            Picasso.get().load(obj.getHeadquartersPhoto()).into(brandHeadquartersImg);
            brandLiveProject.setText(obj.getLiveProject());
            Picasso.get().load(obj.getLogoUrl()).into(brandLogo);
            brandName.setText(obj.getName());
            brandNetWorth.setText(obj.getNetWorth());
            brandParentCompany.setText(obj.getParentCompany());
            brandRank.setText(obj.getRanked());
            brandRankedBy.setText(obj.getRankedBy());
            brandRankIn.setText(obj.getRankedIn());
            brandRevenue.setText(obj.getRevenue());
            CustomerServiceNo.setText(obj.getServiceNo());
            brandStockPrice.setText(obj.getStockPrice());
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

        public void setVideo(BrandDataCard obj){

            logoAnimation.setVideoPath(obj.getLogoAnimationUrl());
            logoAnimation.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                    float videoRatio=mediaPlayer.getVideoWidth()/(float)mediaPlayer.getVideoHeight();
                    float screenRatio=logoAnimation.getWidth()/(float) logoAnimation.getHeight();
                    float scale=videoRatio/screenRatio;
                    if(scale>=1f)
                        logoAnimation.setScaleX(scale);
                    else
                        logoAnimation.setScaleY(1f/scale);
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
    public  void setAnimation(View view,int position){
        Animation slidIn = AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left);
        view.startAnimation(slidIn);
    }
}




