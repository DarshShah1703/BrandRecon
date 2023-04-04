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
import android.widget.TextView;

import render.animations.Attention;
import render.animations.Bounce;
import render.animations.Render;
import render.animations.Zoom;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView all_corners,left_top_corner,left_bottom_corner,right_top_corner,right_bottom_corner,outer_circle_border,inner_solid_circle,inner_text_r;

    Animation sp_rotation,sp_move_toptodown,sp_move_downtotop,sp_move_lefttoright,sp_move_righttoleft,sp_fade,sp_zoom_in,sp_zoom_in_and_out,sp_zoom_in_and_out_right,sp_zoom_out_and_in_left,sp_zoom_in_version2,sp_ruberband;

    TextView appName,tageLinePart1,tageLinePart2;
    Render render;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        setXmlIds();
        int duration = setAnimations();

        SetIndividualCornersAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this,HomeActivity.class));
                finish();
            }
        },duration);
    }

    private void setXmlIds() {
        all_corners = findViewById(R.id.all_corners);
        left_top_corner = findViewById(R.id.left_top_corner);
        left_bottom_corner = findViewById(R.id.left_bottom_corner);
        right_top_corner = findViewById(R.id.right_top_corner);
        right_bottom_corner = findViewById(R.id.right_bottom_corner);
        outer_circle_border = findViewById(R.id.outer_circle_border);
        inner_solid_circle = findViewById(R.id.inner_solid_circle);
        inner_text_r = findViewById(R.id.inner_text_r);
        appName = findViewById(R.id.appName);
        tageLinePart1 = findViewById(R.id.tageLinePart1);
        tageLinePart2 = findViewById(R.id.tageLinePart2);
    }

    private int setAnimations() {
        sp_move_toptodown = AnimationUtils.loadAnimation(this,R.anim.sp_move_toptodown);
        sp_move_lefttoright = AnimationUtils.loadAnimation(this,R.anim.sp_move_lefttoright);
        sp_move_righttoleft = AnimationUtils.loadAnimation(this,R.anim.sp_move_righttoleft);
        sp_move_downtotop = AnimationUtils.loadAnimation(this,R.anim.sp_move_downtotop);
        sp_rotation = AnimationUtils.loadAnimation(this,R.anim.sp_rotation);
        sp_fade = AnimationUtils.loadAnimation(this,R.anim.sp_fade);
        sp_zoom_in = AnimationUtils.loadAnimation(this,R.anim.sp_zoom_in);
        sp_ruberband = AnimationUtils.loadAnimation(this,R.anim.sp_ruberband);
        sp_zoom_in_and_out = AnimationUtils.loadAnimation(this,R.anim.sp_zoom_in_and_out);
        sp_zoom_in_and_out_right = AnimationUtils.loadAnimation(this,R.anim.sp_zoom_in_and_out_right);
        sp_zoom_in_version2 = AnimationUtils.loadAnimation(this,R.anim.sp_zoom_in_version2);
        sp_zoom_out_and_in_left = AnimationUtils.loadAnimation(this,R.anim.sp_zoom_out_and_in_left);

        render = new Render(SplashScreenActivity.this);
        render.setAnimation(Zoom.InDown(appName));

        int duration = (int) (sp_move_lefttoright.getDuration()+sp_rotation.getDuration()+sp_fade.getDuration()+sp_zoom_in.getDuration()+sp_ruberband.getDuration()+2000+sp_zoom_in_and_out.getDuration()+sp_zoom_in_and_out_right.getDuration()+sp_zoom_in_version2.getDuration()+sp_zoom_out_and_in_left.getDuration()-1000);

        return duration;
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
        inner_text_r.setAnimation(sp_ruberband);
        sp_ruberband.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setAppNameAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    private void setAppNameAnimation(){
        appName.setVisibility(View.VISIBLE);
        render.setDuration(1000);
        render.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                clearAnimation();
                removeVisibility();
                slideOutAppNameAnimation();
            }
        },2000);
    }

    private void slideOutAppNameAnimation(){
        appName.setAnimation(sp_zoom_in_and_out);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                clearAnimation();
                appName.clearAnimation();
                appName.setVisibility(View.GONE);
                setTageLinePart1Animation();
            }
        },1000);

    }
    private void setTageLinePart1Animation(){
        tageLinePart1.setVisibility(View.VISIBLE);
        render.setAnimation(Bounce.InUp(tageLinePart1));
        render.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tageLinePart1.setAnimation(sp_zoom_in_and_out_right);
                setTageLinePart2Animation();
            }
        },900);

    }
    private void setTageLinePart2Animation() {
        tageLinePart1.clearAnimation();
        tageLinePart1.setVisibility(View.GONE);
        tageLinePart2.setVisibility(View.VISIBLE);
        tageLinePart2.setAnimation(sp_zoom_out_and_in_left);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tageLinePart2.clearAnimation();
                tageLinePart2.startAnimation(sp_zoom_in_version2);
            }
        },1500);

        
    }

    private void clearAnimation(){
        left_top_corner.clearAnimation();
        left_bottom_corner.clearAnimation();
        right_top_corner.clearAnimation();
        right_bottom_corner.clearAnimation();
        all_corners.clearAnimation();
        outer_circle_border.clearAnimation();
        inner_solid_circle.clearAnimation();
        inner_text_r.clearAnimation();
    }
    private void removeVisibility(){
        left_top_corner.setVisibility(View.GONE);
        left_bottom_corner.setVisibility(View.GONE);
        right_top_corner.setVisibility(View.GONE);
        right_bottom_corner.setVisibility(View.GONE);
        all_corners.setVisibility(View.GONE);
        outer_circle_border.setVisibility(View.GONE);
        inner_solid_circle.setVisibility(View.GONE);
        inner_text_r.setVisibility(View.GONE);
    }

}
