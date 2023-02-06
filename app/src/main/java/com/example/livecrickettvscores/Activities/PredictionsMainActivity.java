package com.example.livecrickettvscores.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecrickettvscores.Activities.Adapters.FantasyPredictionAdapter;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.AppAsyncTasks;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.PredictionDetailsModel;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.databinding.ActivityPredictionsMainBinding;

import org.jsoup.select.Elements;

import java.util.ArrayList;

public class PredictionsMainActivity extends AppCompatActivity {
    ActivityPredictionsMainBinding binding;
    Activity activity;
    ArrayList<PredictionDetailsModel> predictionDetailsModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPredictionsMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = PredictionsMainActivity.this;
        callFantasyPredictionsData();

        initListeners();
    }

    private void callFantasyPredictionsData() {
        AppAsyncTasks.GetPredictionTask getPredictionTask = new AppAsyncTasks.GetPredictionTask(activity, new AppInterfaces.WebScrappingInterface() {
            @Override
            public void getScrapedDocument(Elements multipleElements) {
                if (multipleElements != null && multipleElements.size() > 0) {
                    predictionDetailsModels = new ArrayList<>();
                    for (int i = 0; i < multipleElements.size(); i++) {
                        PredictionDetailsModel model = new PredictionDetailsModel();
                        model.setMatchLocation(multipleElements.get(i).select("div.match-meta").select("div.location").text());
                        model.setMatchName(multipleElements.get(i).select("div.league").text());
                        model.setTeam1(multipleElements.get(i).select("div.home-team").select("div.name").text());
                        model.setTeam2(multipleElements.get(i).select("div.away-team").select("div.name").text());
                        model.setPredictionStatus(multipleElements.get(i).select("div.tip-status").text());
                        model.setTime(multipleElements.get(i).select("div.time").text());
                        model.setMatchPredictionURL(multipleElements.get(i).select("a").attr("href"));
                        model.setTime(multipleElements.get(i).select("div.time").text());
                        model.setTeam1Img(multipleElements.get(i).select("div.home-team").select("img").attr("src"));
                        model.setTeam2Img(multipleElements.get(i).select("div.away-team").select("img").attr("src"));
                        predictionDetailsModels.add(model);
                    }
                    setUpFantasyList(predictionDetailsModels);
                }
            }
        });
        getPredictionTask.execute();
    }

    private void setUpFantasyList(ArrayList<PredictionDetailsModel> predictionDetailsModels) {
        binding.rclPredictionList.setLayoutManager(Global.getManagerWithOrientation(activity, RecyclerView.VERTICAL));
        FantasyPredictionAdapter fantasyPredictionAdapter = new FantasyPredictionAdapter(activity, predictionDetailsModels, new AppInterfaces.NewsAdapterClick() {
            @Override
            public void getClickedNewsID(Integer predictionPosition) {
                startActivity(new Intent(activity, PredictionDetailsActivity.class).putExtra("predictionURL", predictionDetailsModels.get(predictionPosition).getMatchPredictionURL()))
            }
        }); binding.rclPredictionList.setAdapter(fantasyPredictionAdapter);
    }

    private void initListeners() {

    }
}