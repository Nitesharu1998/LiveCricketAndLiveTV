package com.example.livecrickettvscores.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdUtils;
import com.example.livecrickettvscores.Activities.Retrofit.AppAsyncTasks;
import com.example.livecrickettvscores.Activities.Utils.ConnectionDetector;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.Activities.Utils.StringUtils;
import com.example.livecrickettvscores.R;
import com.example.livecrickettvscores.databinding.ActivityPredictionDetailsBinding;

import org.jsoup.select.Elements;

public class PredictionDetailsActivity extends AppCompatActivity {
    Activity activity;
    ConnectionDetector cd;
    ActivityPredictionDetailsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPredictionDetailsBinding.inflate(getLayoutInflater());
        activity = PredictionDetailsActivity.this;
        cd = new ConnectionDetector(activity);
        AdUtils.showNativeAd(PredictionDetailsActivity.this, Constants.adsJsonPOJO.getParameters().getNative_id().getDefaultValue().getValue(),binding.nativeAds,false);

        getThePredictionDetails(getIntent().getStringExtra("predictionURL") != null ? getIntent().getStringExtra("predictionURL") : "");
        binding.tvInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog popupWindow = new Dialog(PredictionDetailsActivity.this);
                popupWindow.setContentView(R.layout.pred_wizard_layout);
                popupWindow.setCancelable(true);
                popupWindow.show();
            }
        });
        setContentView(binding.getRoot());

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AdUtils.showInterstitialAd(activity, new AppInterfaces.InterStitialADInterface() {
            @Override
            public void adLoadState(boolean isLoaded) {
                finish();
            }
        });
    }

    private void getThePredictionDetails(String predictionURL) {
        if (cd.isConnectingToInternet()){
            AppAsyncTasks.GetPredictionDetailsTask task = new AppAsyncTasks.GetPredictionDetailsTask(predictionURL,activity,new AppInterfaces.WebScrappingInterface() {
                @Override
                public void getScrapedDocument(Elements document) {
                    Elements LI_Elements1;
                    if (!Global.isClassNull(document) && document.size() > 0) {
                        Elements LI_Elements;
                        int probmatchdraw = 0, probmatch2 = 0, probmatch1 = 0;
                        String team1image = document.select("div.home").select("img").attr("src");
                        String team2image = document.select("div.away").select("img").attr("src");
                        String team1name = document.select("div.home").select("div.name").get(0).text();
                        String team2name = document.select("div.away").select("div.name").get(0).text();

                        binding.tvTitle.setText(team1name + " vs " + team2name + " Predictions");

                        probmatch1 = Math.round(Float.parseFloat(StringUtils.isNull(document.select("div.container-2").select("div.wizard").select("div.row").select("div.box").select("div.teams").select("div.home").select("div.prob").text().replace("%", "")) ? "0" : document.select("div.container-2").select("div.wizard").select("div.row").select("div.box").select("div.teams").select("div.home").select("div.prob").text().replace("%", "")));
                        probmatch2 = Math.round(Float.parseFloat(StringUtils.isNull(document.select("div.container-2").select("div.wizard").select("div.row").select("div.box").select("div.teams").select("div.away").select("div.prob").text().replace("%", "")) ? "0" : document.select("div.container-2").select("div.wizard").select("div.row").select("div.box").select("div.teams").select("div.away").select("div.prob").text().replace("%", "")));
                        probmatchdraw = Math.round(Float.parseFloat(StringUtils.isNull(document.select("div.container-2").select("div.wizard").select("div.row").select("div.box").select("div.teams").select("div.draw").select("div.prob").text().replace("%", "")) ? "0" : document.select("div.container-2").select("div.wizard").select("div.row").select("div.box").select("div.teams").select("div.draw").select("div.prob").text().replace("%", "")));

                        StringBuilder predictionText = new StringBuilder();

                        LI_Elements = document.select("div.match-info").select("div.row > ul").select("li");
                        binding.tvDate.setText(LI_Elements.get(1).select("span").text());
                        binding.tvMatchlocation.setText(LI_Elements.get(3).select("span").text());
                        binding.tvTime.setText(LI_Elements.get(4).select("span").text());
                        Glide.with(activity).load(team1image).into(binding.ivTeam1);
                        Glide.with(activity).load(team2image).into(binding.ivTeam2);
                        binding.tvTeam1.setText(team1name);
                        binding.tvTeam2.setText(team2name);
                        binding.tvTeam1name.setText(team1name);
                        binding.tvTeam2name.setText(team2name);
                        binding.tvTeamscore1.setText(document.select("div.home").select("div.score").text());
                        binding.tvTeamscore2.setText(document.select("div.away").select("div.score").text());
                        binding.tvWinningprob.setText(team1name + " VS " + team2name + " wining probability");
                        binding.tvMatchtitle.setText(team1name.toUpperCase() + " vs " + team2name.toUpperCase() + " BETTING TIPS");
                        LI_Elements1 = document.select("div.prediction.js-toc-item").select("div.row").select("div.box > ul.keys").select("li");
                        for (int i = 0; i < LI_Elements1.size(); i++) {
                            predictionText.append(LI_Elements1.get(i).text()).append("\n");
                        }
                        binding.tvMatchtips.setText(predictionText.toString().trim());
                        binding.tvTeam1prob.setText(document.select("div.container-2").select("div.wizard").select("div.row").select("div.box").select("div.teams").select("div.home").select("div.prob").text());
                        binding.tvTeam2prob.setText(document.select("div.container-2").select("div.wizard").select("div.row").select("div.box").select("div.teams").select("div.away").select("div.prob").text());

                        binding.tvDraw.setText(document.select("div.container-2").select("div.wizard").select("div.row").select("div.box").select("div.teams").select("div.draw").select("div.prob").text());

                        //TODO below toast is returning decimal value round it to single digit

                        binding.tvProb1.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, probmatch1));
                        binding.tvProb2.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, probmatch2));
                        binding.tvProbdraw.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, probmatchdraw));
                        binding.tvTeam1prob.setText(probmatch1 + "%");
                        binding.tvTeam2prob.setText(probmatch2 + "%");
                        binding.tvDraw.setText(probmatchdraw + "%");

                    }
                }
            });
            task.execute();
        }
    }
}