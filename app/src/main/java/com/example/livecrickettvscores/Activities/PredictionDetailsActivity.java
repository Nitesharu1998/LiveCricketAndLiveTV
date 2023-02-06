package com.example.livecrickettvscores.Activities;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.AppAsyncTasks;
import com.example.livecrickettvscores.Activities.Utils.ConnectionDetector;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.databinding.ActivityPredictionDetailsBinding;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PredictionDetailsActivity extends AppCompatActivity {
    Activity activity;
    ConnectionDetector cd;
    Element element;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPredictionDetailsBinding bidnding = ActivityPredictionDetailsBinding.inflate(getLayoutInflater());
        activity = PredictionDetailsActivity.this;
        getThePredictionDetails(getIntent().getStringExtra("predictionURL") != null ? getIntent().getStringExtra("predictionURL") : "");
        cd = new ConnectionDetector(activity);
        setContentView(bidnding.getRoot());
    }

    private void getThePredictionDetails(String predictionURL) {
        if (cd.isConnectingToInternet()){
            AppAsyncTasks.GetPredictionDetailsTask task = new AppAsyncTasks.GetPredictionDetailsTask(predictionURL,activity,new AppInterfaces.WebScrappingInterface() {
                @Override
                public void getScrapedDocument(Elements document) {

                }
            });
        }
    }
}