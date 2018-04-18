package com.wridmob.hissenmuslem.activites;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.wridmob.hissenmuslem.R;

public class SplashScreenActivity extends AppCompatActivity {
    PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, SummaryActivity.class));
                finish();
            }
        }, 1000);
    }





}
