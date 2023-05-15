package com.example.livecrickettvscores.Activities.Fragments;

import static com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdUtils.loadInitialInterstitialAds;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecrickettvscores.Activities.Adapters.TrendingPlayersAdapter;
import com.example.livecrickettvscores.Activities.AppHomeActivity;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdUtils;
import com.example.livecrickettvscores.Activities.PlayerInformation;
import com.example.livecrickettvscores.Activities.Retrofit.AppAsyncTasks;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.TrendingPlayersResponseModel;
import com.example.livecrickettvscores.Activities.Utils.ConnectionDetector;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.R;
import com.example.livecrickettvscores.databinding.FragmentStatsBinding;
import com.google.android.material.tabs.TabLayout;

import org.jsoup.select.Elements;

import java.util.ArrayList;

public class StatsFragment extends Fragment {
    Context context;
    FragmentStatsBinding fragmentStatsBinding;
    ConnectionDetector cd;
    ArrayList<String> playerTypes = new ArrayList<>();
    public static int tabPosition;
    @Override
    public void onResume() {
        super.onResume();
        loadInitialInterstitialAds(requireActivity());
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentStatsBinding = FragmentStatsBinding.inflate(LayoutInflater.from(getContext()), container, false);
        context = fragmentStatsBinding.getRoot().getContext();
        fragmentStatsBinding.rclPlayers.setHasFixedSize(true);
        cd = new ConnectionDetector(requireActivity());
        setUpTabs();
        initListeners();
        playerTypes.add("Batting");
        playerTypes.add("Bowling");
        playerTypes.add("All Rounder");
        ArrayAdapter adapter = new ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_dropdown_item, playerTypes);
        adapter.setDropDownViewResource(R.layout.spinner_player_type);
        fragmentStatsBinding.spnPlayersType.setAdapter(adapter);

        callRankingURL(Constants.MenBattingRanking);


        return fragmentStatsBinding.getRoot();
    }

    private void initListeners() {

        fragmentStatsBinding.spnPlayersType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (fragmentStatsBinding.tablayout.getSelectedTabPosition()) {
                    case 0:

                        switch (i) {
                            case 0:
                                callRankingURL(Constants.MenBattingRanking);
                                break;
                            case 1:
                                callRankingURL(Constants.MenBowlingRanking);
                                break;
                            case 2:
                                callRankingURL(Constants.MenAllRounderRanking);
                                break;
                        }

                        break;
                    case 1:

                        switch (i) {
                            case 0:
                                callRankingURL(Constants.WomenBattingRanking);
                                break;
                            case 1:
                                callRankingURL(Constants.WomenBowlingRanking);
                                break;
                            case 2:
                                callRankingURL(Constants.WomenAllRounderRanking);
                                break;
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fragmentStatsBinding.tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();
                switch (tab.getPosition()) {
                    case 0:
                        AdUtils.showInterstitialAd(requireActivity(), new AppInterfaces.InterStitialADInterface() {
                            @Override
                            public void adLoadState(boolean isLoaded) {
                                fragmentStatsBinding.spnPlayersType.setSelection(0);
                                if (cd.isConnectingToInternet())
                                    callRankingURL(Constants.MenBattingRanking);
                            }
                        });
                        break;
                    case 1:
                        AdUtils.showInterstitialAd(requireActivity(), new AppInterfaces.InterStitialADInterface() {
                            @Override
                            public void adLoadState(boolean isLoaded) {
                                fragmentStatsBinding.spnPlayersType.setSelection(0);
                                if (cd.isConnectingToInternet())
                                    callRankingURL(Constants.WomenBattingRanking);
                            }
                        });
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpTabs() {
        fragmentStatsBinding.tablayout.addTab(fragmentStatsBinding.tablayout.newTab().setText("Men's"));
        fragmentStatsBinding.tablayout.addTab(fragmentStatsBinding.tablayout.newTab().setText("Women's"));
    }


    private void callRankingURL(String playerURL) {
        AppAsyncTasks.CallTrendingPlayers callTrendingPlayers = new AppAsyncTasks.CallTrendingPlayers(playerURL, requireActivity(), new AppInterfaces.WebScrappingInterface() {
            @Override
            public void getScrapedDocument(Elements document) {
                setUpTrendingPlayersList(fragmentStatsBinding.rclPlayers, getPlayerList(document));
            }
        });
        callTrendingPlayers.execute();
    }

    private ArrayList<TrendingPlayersResponseModel.PlayerDTO> getPlayerList(Elements document) {
        ArrayList<TrendingPlayersResponseModel.PlayerDTO> playerList = new ArrayList<>();
        TrendingPlayersResponseModel.PlayerDTO singePlayer;
        for (int i = 0; i < document.size(); i++) {
            singePlayer = new TrendingPlayersResponseModel.PlayerDTO();
            singePlayer.setFaceImageId(document.get(i).select("div.cb-col.cb-col-50").select("img").attr("src"));
            singePlayer.setName(document.get(i).select("div.cb-col.cb-col-50").select("img").attr("title"));
            singePlayer.setTeamName(document.get(i).select("div.cb-col.cb-col-67.cb-rank-plyr").select("div.cb-font-12.text-gray").text());
            singePlayer.setId(document.get(i).select("div.cb-col.cb-col-67.cb-rank-plyr").select("a").attr("href"));
            playerList.add(singePlayer);
        }
        return playerList;
    }

    private void setUpTrendingPlayersList(RecyclerView rclPlayers, ArrayList<TrendingPlayersResponseModel.PlayerDTO> playerDTO) {
        rclPlayers.setLayoutManager(Global.getManagerWithOrientation(context, RecyclerView.VERTICAL));

        TrendingPlayersAdapter adapter = new TrendingPlayersAdapter(context, playerDTO, new AppInterfaces.NewsAdapterClick() {
            @Override
            public void getClickedNewsID(Integer someID) {
                if (someID != null) {

                    AdUtils.showInterstitialAd(requireActivity(), new AppInterfaces.InterStitialADInterface() {
                        @Override
                        public void adLoadState(boolean isLoaded) {
                            Intent intent = new Intent(context, PlayerInformation.class);
                            intent.putExtra("playerURL", Constants.CricBuzzBaseURL + playerDTO.get(someID).getId());
                            intent.putExtra("playerName", playerDTO.get(someID).getName());
                            intent.putExtra("playerImage", "https://www.cricbuzz.com" + playerDTO.get(someID).getFaceImageId());
                            intent.putExtra("playerCountry", playerDTO.get(someID).getTeamName());
                            startActivity(intent);
                        }
                    });


                }


            }
        });
        rclPlayers.setAdapter(adapter);
    }
}