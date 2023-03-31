package com.brandrecon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import render.animations.Attention;
import render.animations.Render;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView all_corners,left_top_corner,left_bottom_corner,right_top_corner,right_bottom_corner,outer_circle_border,inner_solid_circle,inner_text_r;

    Animation sp_rotation,sp_move_toptodown,sp_move_downtotop,sp_move_lefttoright,sp_move_righttoleft,sp_fade,sp_zoom_in;

    Render render;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        all_corners = findViewById(R.id.all_corners);
        left_top_corner = findViewById(R.id.left_top_corner);
        left_bottom_corner = findViewById(R.id.left_bottom_corner);
        right_top_corner = findViewById(R.id.right_top_corner);
        right_bottom_corner = findViewById(R.id.right_bottom_corner);
        outer_circle_border = findViewById(R.id.outer_circle_border);
        inner_solid_circle = findViewById(R.id.inner_solid_circle);
        inner_text_r = findViewById(R.id.inner_text_r);

        sp_move_toptodown = AnimationUtils.loadAnimation(this,R.anim.sp_move_toptodown);
        sp_move_lefttoright = AnimationUtils.loadAnimation(this,R.anim.sp_move_lefttoright);
        sp_move_righttoleft = AnimationUtils.loadAnimation(this,R.anim.sp_move_righttoleft);
        sp_move_downtotop = AnimationUtils.loadAnimation(this,R.anim.sp_move_downtotop);
        sp_rotation = AnimationUtils.loadAnimation(this,R.anim.sp_rotation);
        sp_fade = AnimationUtils.loadAnimation(this,R.anim.sp_fade);
        sp_zoom_in = AnimationUtils.loadAnimation(this,R.anim.sp_zoom_in);

        int duration = (int) (sp_move_lefttoright.getDuration()+sp_rotation.getDuration()+sp_fade.getDuration()+sp_zoom_in.getDuration())+2000;

        render = new Render(SplashScreenActivity.this);

        render.setAnimation(Attention.RuberBand(inner_text_r));

        SetIndividualCornersAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this,HomeActivity.class));
                finish();
            }
        },duration);
    }

    private void SetIndividualCornersAnimation() {
        left_top_corner.setVisibility(View.VISIBLE);
        left_top_corner.setAnimation(sp_move_toptodown);

        left_bottom_corner.setVisibility(View.VISIBLE);
        left_bottom_corner.setAnimation(sp_move_lefttoright);

        right_top_corner.setVisibility(View.VISIBLE);
        right_top_corner.setAnimation(sp_move_righttoleft);

        right_bottom_corner.setVisibility(View.VISIBLE);
        right_bottom_corner.setAnimation(sp_move_downtotop);

        sp_move_downtotop.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setAllCornersAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });



    }
    private void setAllCornersAnimation(){
//        int original_wight = all_corners.getWidth();
//        int original_height = all_corners.getHeight();
//        all_corners.getLayoutParams().height = 100;
//        all_corners.getLayoutParams().width = 100;
        all_corners.setVisibility(View.VISIBLE);
        all_corners.setAnimation(sp_rotation);
        sp_rotation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setOuterCircleAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

    }
    private void setOuterCircleAnimation() {

        outer_circle_border.setVisibility(View.VISIBLE);
        outer_circle_border.setAnimation(sp_fade);
        sp_fade.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setInnerCircleAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    private void setInnerCircleAnimation() {

        inner_solid_circle.setVisibility(View.VISIBLE);
        inner_solid_circle.setAnimation(sp_zoom_in);
        sp_zoom_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setInnerTextAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
    private void setInnerTextAnimation() {
        inner_text_r.setVisibility(View.VISIBLE);
        render.start();
    }






}



//
//        left_top_corner.setVisibility(View.GONE);
//                left_bottom_corner.setVisibility(View.GONE);
//                right_top_corner.setVisibility(View.GONE);
//                right_bottom_corner.setVisibility(View.GONE);