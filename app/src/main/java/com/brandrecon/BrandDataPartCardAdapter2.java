package com.brandrecon;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class BrandDataPartCardAdapter2 extends FirebaseRecyclerAdapter<BrandDataCard, BrandDataPartCardAdapter2.BrandHolder> {


    Context context;

    public BrandDataPartCardAdapter2(@NonNull FirebaseRecyclerOptions<BrandDataCard> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull BrandHolder holder, int position, @NonNull BrandDataCard model) {
        holder.setData(model);
        setAnimation(holder.itemView,position);
    }

    @NonNull
    @Override
    public BrandHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_info_part2,parent,false);
        return new BrandHolder(view);
    }

    public static class BrandHolder extends RecyclerView.ViewHolder {
        TextView brandEmployees,
                brandLiveProject,
                brandNetWorth,
                brandRank,
                brandRankedBy,
                brandRankIn,
                brandRevenue,
                CustomerServiceNo,
                brandStockPrice,
                visitAt;
       // VideoView logoAnimation;

        //LinearLayout layoutVideo,layoutDetails;
        public BrandHolder(@NonNull View itemView) {

            super(itemView);

            brandEmployees = itemView.findViewById(R.id.brandEmployees);

            brandLiveProject = itemView.findViewById(R.id.brandLiveProject);
            brandNetWorth = itemView.findViewById(R.id.brandNetWorth);
            brandRank =itemView.findViewById(R.id.brandRank);
            brandRankedBy = itemView.findViewById(R.id.brandRankedBy);
            brandRankIn = itemView.findViewById(R.id.brandRankIn);
            brandRevenue = itemView.findViewById(R.id.brandRevenue);
            CustomerServiceNo = itemView.findViewById(R.id.CustomerServiceNo);
            brandStockPrice = itemView.findViewById(R.id.brandStockPrice);
            visitAt = itemView.findViewById(R.id.visitAt);

//            brandStockPrice.setAnimation(animation);


            //logoAnimation = (VideoView) itemView.findViewById(R.id.logoAnimation);
            //layoutVideo =itemView.findViewById(R.id.layoutVideo);
            //layoutDetails =itemView.findViewById(R.id.layoutDetails);
        }

        void setData(BrandDataCard obj){

            brandEmployees.setText(obj.getEmployees());
            brandLiveProject.setText(obj.getLiveProject());
            brandNetWorth.setText(obj.getNetWorth());
            brandRank.setText(obj.getRanked());
            brandRankedBy.setText(obj.getRankedBy());
            brandRankIn.setText(obj.getRankedIn());
            brandRevenue.setText(obj.getRevenue());
            CustomerServiceNo.setText(obj.getServiceNo());
            brandStockPrice.setText(obj.getStockPrice());
            visitAt.setText(obj.getVisitAt());

        }
    }
    public  void setAnimation(View view,int position){
        Animation slidIn = AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left);
        view.startAnimation(slidIn);
    }
}





