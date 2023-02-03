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
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.TrendingPlayersResponseModel;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.databinding.FragmentStatsBinding;

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
        callPopularPlayerAPI(fragmentStatsBinding.rclPlayers);
        return fragmentStatsBinding.getRoot();
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
                if (someID != null && someID != 0) {
                    Intent intent = new Intent(context, PlayerInformation.class);
                    intent.putExtra("playerID", someID);
                    startActivity(intent);
                }


            }
        });
        rclPlayers.setAdapter(adapter);
    }
}