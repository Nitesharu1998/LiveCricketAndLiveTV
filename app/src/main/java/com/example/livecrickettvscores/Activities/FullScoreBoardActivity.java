package com.example.livecrickettvscores.Activities;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.AppAsyncTasks;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FixturesResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FullScoreBoardResponseModel;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.Activities.Utils.StringUtils;
import com.example.livecrickettvscores.R;
import com.example.livecrickettvscores.databinding.ActivityFullScoreBoardBinding;

import org.jsoup.select.Elements;

public class FullScoreBoardActivity extends AppCompatActivity {
    ActivityFullScoreBoardBinding binding;
    FixturesResponseModel.MatchesDTO matchesDTO = Constants.matchDTO;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_full_score_board);
        activity=FullScoreBoardActivity.this;
        if (!StringUtils.isNull(matchesDTO.getMatchScoreLink())){
            AppAsyncTasks.GetFinishedScoreBoard getFinishedScoreBoard =new AppAsyncTasks.GetFinishedScoreBoard(true,Constants.ESPNBaseURL+matchesDTO.getMatchScoreLink(),activity, new AppInterfaces.WebScrappingInterface() {
                @Override
                public void getScrapedDocument(Elements scoreBoardElements) {
                    setUpResponseModel(scoreBoardElements);
                }
            });
            getFinishedScoreBoard.execute();
        }

    }

    private FullScoreBoardResponseModel setUpResponseModel(Elements scoreBoardElements) {
        FullScoreBoardResponseModel responseModel =new FullScoreBoardResponseModel();
        for (int i = 0; i < scoreBoardElements.size(); i++) {
         FullScoreBoardResponseModel
        }

        return responseModel;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}