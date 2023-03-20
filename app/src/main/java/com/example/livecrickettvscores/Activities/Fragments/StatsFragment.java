package com.example.livecrickettvscores.Activities.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecrickettvscores.Activities.APIControllers.StatsAPIController;
import com.example.livecrickettvscores.Activities.Adapters.TrendingPlayersAdapter;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.PlayerInformation;
import com.example.livecrickettvscores.Activities.Retrofit.AppAsyncTasks;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.TrendingPlayersResponseModel;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.databinding.FragmentStatsBinding;

import org.jsoup.select.Elements;

import java.util.ArrayList;

public class StatsFragment extends Fragment {
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentStatsBinding fragmentStatsBinding = FragmentStatsBinding.inflate(LayoutInflater.from(getContext()), container, false);
        context = fragmentStatsBinding.getRoot().getContext();
        fragmentStatsBinding.rclPlayers.setHasFixedSize(true);
        //callPopularPlayerAPI(fragmentStatsBinding.rclPlayers);

        AppAsyncTasks.CallTrendingPlayers callTrendingPlayers = new AppAsyncTasks.CallTrendingPlayers("https://www.cricbuzz.com/cricket-stats/icc-rankings/men/batting", requireActivity(), new AppInterfaces.WebScrappingInterface() {
            @Override
            public void getScrapedDocument(Elements document) {
                setUpTrendingPlayersList(fragmentStatsBinding.rclPlayers, getPlayerList(document));
            }
        });
        callTrendingPlayers.execute();

        return fragmentStatsBinding.getRoot();
    }

    private ArrayList<TrendingPlayersResponseModel.PlayerDTO> getPlayerList(Elements document) {
        ArrayList<TrendingPlayersResponseModel.PlayerDTO> playerList = new ArrayList<>();
        TrendingPlayersResponseModel.PlayerDTO singePlayer;
        for (int i = 0; i < 10; i++) {
            singePlayer = new TrendingPlayersResponseModel.PlayerDTO();
            singePlayer.setFaceImageId(document.get(i).select("div.cb-col.cb-col-50").select("img").attr("src"));
            singePlayer.setName(document.get(i).select("div.cb-col.cb-col-50").select("img").attr("title"));
            singePlayer.setTeamName(document.get(i).select("div.cb-col.cb-col-67.cb-rank-plyr").select("div.cb-font-12.text-gray").text());
            singePlayer.setId(document.get(i).select("div.cb-col.cb-col-67.cb-rank-plyr").select("a").attr("href"));
            playerList.add(singePlayer);
        }


        return playerList;
    }

    private void callPopularPlayerAPI(RecyclerView rclPlayers) {
        StatsAPIController statsAPIController = new StatsAPIController(context);
        statsAPIController.callTrendingPlayerAPI(new AppInterfaces.APIResponseInterface() {
            @Override
            public void theApiResponse(TrendingPlayersResponseModel trendingPlayersResponseModel) {
                if (!Global.isClassNull(trendingPlayersResponseModel) && !Global.isArrayListNull(trendingPlayersResponseModel.getPlayer())) {
                    setUpTrendingPlayersList(rclPlayers, trendingPlayersResponseModel.getPlayer());
                }
            }
        });
    }

    private void setUpTrendingPlayersList(RecyclerView rclPlayers, ArrayList<TrendingPlayersResponseModel.PlayerDTO> playerDTO) {
        rclPlayers.setLayoutManager(Global.getManagerWithOrientation(context, RecyclerView.VERTICAL));

        TrendingPlayersAdapter adapter = new TrendingPlayersAdapter(context, playerDTO, new AppInterfaces.NewsAdapterClick() {
            @Override
            public void getClickedNewsID(Integer someID) {
                if (someID != null) {
                    Intent intent = new Intent(context, PlayerInformation.class);
                    intent.putExtra("playerURL", "https://www.cricbuzz.com/"+playerDTO.get(someID).getId());
                    startActivity(intent);
                }


            }
        });
        rclPlayers.setAdapter(adapter);
    }
}