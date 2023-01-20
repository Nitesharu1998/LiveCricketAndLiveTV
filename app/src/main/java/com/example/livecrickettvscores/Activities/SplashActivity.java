package com.example.livecrickettvscores.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.livecrickettvscores.Activities.Utils.EncryptionUtils;

import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Global.sout("CRICKAPI base>>>>>>>>>>> ", EncryptionUtils.Ecrp_Hex("https://api.cricapi.com/v1"));
        Global.sout("CRICK live data>>>>>>>>>>> ", EncryptionUtils.Ecrp_Hex("https://cricket-live-data.p.rapidapi.com"));
        Activity activity = SplashActivity.this;

        Thread mSplashThread = new Thread() {

            @Override
            public void run() {
                synchronized (this) {
                    try {
                        wait(3000);
                    } catch (InterruptedException e) {
                    } finally {
                        finish();
                        Intent intent = new Intent(activity,
                                AppHomeActivity.class);
                        startActivity(intent);
                    }
                }
            }

        };
        mSplashThread.start();
    }
}