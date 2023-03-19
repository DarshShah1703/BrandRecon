package com.brandrecon;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;


// for text animation
import android.os.Handler;
import android.os.Message;

import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class HomeActivity extends AppCompatActivity {

    private View decorView;

    LinearLayout btnNameSearch,btnLogoSearch;
    VideoView backgroundVideo;
    TextView tagLine;
    ImageView volumeUp,volumeOff;

    int volumeCode;

    MediaPlayer mediaPlayer;
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

        mediaPlayer = MediaPlayer.create(this,R.raw.background_music);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(50,50);
        mediaPlayer.start();
        volumeCode = 0;

        backgroundVideo =findViewById(R.id.backgroundVideo);
        tagLine = findViewById(R.id.tagLine);
        setAnimation(tagLine.getText().toString());
        btnNameSearch = findViewById(R.id.btnNameSearch);
        btnLogoSearch = findViewById(R.id.btnLogoSearch);
        volumeUp =findViewById(R.id.volumeUp);

        volumeOff =findViewById(R.id.volumeOff);

        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.background_video_2);
        backgroundVideo.setVideoURI(uri);
        backgroundVideo.start();

        backgroundVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
        btnNameSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SearchByNameActivity.class));
            }
        });
        btnLogoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, LogoDetectionActivity.class));
            }
        });
        volumeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                volumeCode=0;
                volumeUp.setVisibility(View.GONE);
            }
        });
        volumeOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                volumeCode=1;
                volumeOff.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setAnimation(final String s) {
        final int[] i = new int[1];
        i[0] = 0;
        tagLine.setText("");
        final int length = s.length();
        final Handler handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                char c = s.charAt(i[0]);
                tagLine.append(String.valueOf(c));
                i[0]++;
            }
        };
        final Timer timer = new Timer();
        TimerTask taskEverySplitSecond = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
                if (i[0] == length - 1) {
                    timer.cancel();
                }
            }
        };
        timer.schedule(taskEverySplitSecond, 1, 50);
    }

    @Override
    protected void onStart() {



        if (volumeCode==0){
            mediaPlayer.stop();
        }
        else{
            mediaPlayer.start();
        }

        backgroundVideo.start();

        super.onStart();
    }

    @Override
    protected void onResume() {
        backgroundVideo.resume();
        super.onResume();
    }

    @Override
    protected void onRestart() {
        backgroundVideo.start();
        super.onRestart();
    }

    @Override
    protected void onPause() {
        backgroundVideo.suspend();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        backgroundVideo.stopPlayback();
        super.onDestroy();
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