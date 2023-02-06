package com.example.livecrickettvscores.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.livecrickettvscores.Activities.Utils.EncryptionUtils;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitySplashBinding binding = ActivitySplashBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        Global.sout("Crickbuzz base>>>>>>>>>>> ", EncryptionUtils.Ecrp_Hex("https://unofficial-cricbuzz.p.rapidapi.com/"));
        Global.sout("Crickbuzz rapid api key>>>>>>>>>>> ", EncryptionUtils.Ecrp_Hex("9008284513msh0c98ccd4665e950p16b992jsnaa622c8d33b1"));
        Global.sout("Crickbuzz rapid host>>>>>>>>>>> ", EncryptionUtils.Ecrp_Hex("unofficial-cricbuzz.p.rapidapi.com"));
        Activity activity = SplashActivity.this;


        Thread mSplashThread = new Thread() {

            @Override
            public void run() {
                synchronized (this) {
                    try {
                        wait(3000);
                    } catch (InterruptedException e) {
                        Global.sout("running thread interrupted", e.getLocalizedMessage());
                    } finally {
                        finish();
                        Intent intent = new Intent(activity, AppHomeActivity.class);
                        startActivity(intent);
                    }
                }
            }

        };
        mSplashThread.start();
    }


}