package com.brandrecon;


import android.content.Intent;

import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.motion.widget.MotionLayout;


// for text animation
import android.os.Handler;


import android.widget.LinearLayout;
import android.widget.TextView;
import render.animations.Flip;
import render.animations.Render;


public class HomeActivity extends AppCompatActivity {

    private View decorView;

    CardView btnNameSearch,btnLogoSearch;
    LinearLayout upper_circle_small_pink;
    TextView tagLine;
    int transitionState;
    MotionLayout motionLayout;
    Render renderSearchBtn,renderLogoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int i) {
                if (i == 0){
                    decorView.setSystemUiVisibility(hideSystemBars());
                }
            }
        });

        motionLayout = findViewById(R.id.motion_layout);


        upper_circle_small_pink = findViewById(R.id.upper_circle_small_pink);


        tagLine = findViewById(R.id.appName);
        setAnimation(tagLine.getText().toString());

        btnNameSearch = findViewById(R.id.btnNameSearch);
        btnLogoSearch = findViewById(R.id.btnLogoSearch);

        renderSearchBtn = new Render(HomeActivity.this);
        renderSearchBtn.setAnimation(Flip.InY(btnNameSearch));
        btnNameSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renderSearchBtn.start();
                Thread t= new Thread(){
                    public void run() {
                        try {
                            sleep(500);
                            startActivity(new Intent(HomeActivity.this, SearchByNameActivity.class));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
		        t.start();
            }
        });
        renderLogoBtn = new Render(HomeActivity.this);
        renderLogoBtn.setAnimation(Flip.InY(btnLogoSearch));
        btnLogoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renderLogoBtn.start();
                Thread t= new Thread(){
                    public void run() {
                        try {
                            sleep(500);
                            startActivity(new Intent(HomeActivity.this, LogoDetectionActivity.class));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                t.start();

            }
        });
    }

    private void setAnimation(final String s) {
        tagLine.setText("");
        new Handler().postDelayed(new Runnable() {
            int index = 0;

            @Override
            public void run() {
                if (index < s.length()) {
                    tagLine.append(String.valueOf(s.charAt(index++)));
                    new Handler().postDelayed(this, 50); // Delay between each character animation
                }
            }
        }, 50);
    }

    @Override
    protected void onStart() {
        setAnimation(tagLine.getText().toString());
        if(transitionState == 1) {
            motionLayout.transitionToStart();
            transitionState = 0;
        }
        else {
            motionLayout.transitionToEnd();
            transitionState = 1;
        }
        renderSearchBtn.start();
        renderLogoBtn.start();
        super.onStart();


    }

    @Override
    protected void onResume() {
        setAnimation(tagLine.getText().toString());
        if(transitionState == 1) {
            motionLayout.transitionToStart();
            transitionState = 0;
        }
        else {
            motionLayout.transitionToEnd();
            transitionState = 1;
        }
        renderSearchBtn.start();
        renderLogoBtn.start();
        super.onResume();

    }

    @Override
    protected void onRestart() {
        setAnimation(tagLine.getText().toString());
        if(transitionState == 1) {
            motionLayout.transitionToStart();
            transitionState = 0;
        }
        else {
            motionLayout.transitionToEnd();
            transitionState = 1;
        }
        renderSearchBtn.start();
        renderLogoBtn.start();
        super.onRestart();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
           decorView.setSystemUiVisibility(hideSystemBars());
        }
    }

    private int hideSystemBars(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }
}