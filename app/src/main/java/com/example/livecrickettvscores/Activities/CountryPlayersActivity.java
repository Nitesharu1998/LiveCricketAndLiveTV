package com.example.livecrickettvscores.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecrickettvscores.Activities.Adapters.CountriesAdapter;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.AppAsyncTasks;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.CountriesResponseModel;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.R;
import com.example.livecrickettvscores.databinding.ActivityCountryPlayersBinding;

import org.jsoup.select.Elements;

import java.util.ArrayList;

public class CountryPlayersActivity extends AppCompatActivity {
    Activity activity;
    ActivityCountryPlayersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = CountryPlayersActivity.this;
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_country_players);
        String countryLink = Constants.CricBuzzBaseURL + getIntent().getStringExtra("playerLink") + "/players";
        AppAsyncTasks.GetPlayers getPlayers = new AppAsyncTasks.GetPlayers(countryLink, activity, new AppInterfaces.WebScrappingInterface() {
            @Override
            public void getScrapedDocument(Elements document) {
                setUpPlayerList(document);
            }
        });
        getPlayers.execute();
    }

    private void setUpPlayerList(Elements document) {
        ArrayList<CountriesResponseModel> responseModelArrayList = new ArrayList<>();

        for (int i = 0; i < document.size(); i++) {
            CountriesResponseModel responseModel = new CountriesResponseModel();
            responseModel.setCountryName(document.get(i).select("div[class=cb-font-16 text-hvr-underline]").text());
            responseModel.setCountryFlag(document.get(i).select("div[class=cb-col cb-col-27]").select("img").attr("src"));
            responseModel.setCountryEndpoint(document.get(i).select("a").attr("href"));
            responseModelArrayList.add(responseModel);

        }
        CountriesAdapter adapter = new CountriesAdapter(activity, responseModelArrayList, new AppInterfaces.NewsAdapterClick() {
            @Override
            public void getClickedNewsID(Integer newsID) {
                Intent intent = new Intent(activity, PlayerInformation.class);
                intent.putExtra("playerURL",Constants.CricBuzzBaseURL+ responseModelArrayList.get(newsID).getCountryEndpoint());
                intent.putExtra("playerName", responseModelArrayList.get(newsID).getCountryName());
                intent.putExtra("playerImage",Constants.CricBuzzBaseURL+responseModelArrayList.get(newsID).getCountryFlag());
                startActivity(intent);
            }
        });
        binding.rclCountryPlayers.setLayoutManager(Global.getManagerWithOrientation(activity, RecyclerView.VERTICAL));
        binding.rclCountryPlayers.setAdapter(adapter);


    }
}