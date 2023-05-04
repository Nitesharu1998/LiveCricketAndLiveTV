package com.example.livecrickettvscores.Activities;

import static com.example.livecrickettvscores.Activities.Fragments.StatsFragment.tabPosition;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.livecrickettvscores.Activities.Adapters.PlayerAdapter;
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
    String PlayerCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = CountryPlayersActivity.this;
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_country_players);
        String countryLink = Constants.CricBuzzBaseURL + getIntent().getStringExtra("playerLink") + "/players";
        PlayerCountry = getIntent().getStringExtra("playerCountry");

        if (tabPosition == 0) {
            binding.tvTitle.setText(PlayerCountry + " Men's Cricket Team");
        } else {
            binding.tvTitle.setText(PlayerCountry + "'s Cricket Team");
        }
        AppAsyncTasks.GetPlayers getPlayers = new AppAsyncTasks.GetPlayers(countryLink, activity, new AppInterfaces.WebScrappingInterface() {
            @Override
            public void getScrapedDocument(Elements document) {
                if (document.isEmpty()) {
                    binding.rclCountryPlayers.setVisibility(View.GONE);
                    binding.tvNodatafound.setVisibility(View.VISIBLE);
                } else {
                    binding.rclCountryPlayers.setVisibility(View.VISIBLE);
                    binding.tvNodatafound.setVisibility(View.GONE);
                    setUpPlayerList(document);
                }
            }
        });
        getPlayers.execute();

        binding.icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
        PlayerAdapter adapter = new PlayerAdapter(activity, responseModelArrayList, new AppInterfaces.NewsAdapterClick() {
            @Override
            public void getClickedNewsID(Integer newsID) {
                Intent intent = new Intent(activity, PlayerInformation.class);
                intent.putExtra("playerURL", Constants.CricBuzzBaseURL + responseModelArrayList.get(newsID).getCountryEndpoint());
                intent.putExtra("playerName", responseModelArrayList.get(newsID).getCountryName());
                intent.putExtra("playerImage", Constants.CricBuzzBaseURL + responseModelArrayList.get(newsID).getCountryFlag());
                intent.putExtra("playerCountry", PlayerCountry);
                startActivity(intent);
            }
        });

        binding.rclCountryPlayers.setLayoutManager(Global.getGridLayoutManager(activity, 2));
        binding.rclCountryPlayers.setAdapter(adapter);


    }
}