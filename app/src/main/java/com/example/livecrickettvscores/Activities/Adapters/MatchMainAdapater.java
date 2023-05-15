package com.example.livecrickettvscores.Activities.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.FullScoreBoardActivity;
import com.example.livecrickettvscores.Activities.LiveMatchScoreBoardActivity;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FixturesResponseModel;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.example.livecrickettvscores.databinding.SinglelivematchlayoutBinding;

import java.util.ArrayList;

public class MatchMainAdapater extends RecyclerView.Adapter<MatchMainAdapater.ViewHolder> {
    Activity activity;
    ArrayList<FixturesResponseModel> fixturesResponseModel;
    AppInterfaces.NewsAdapterClick newsAdapterClick;

    public MatchMainAdapater(Activity activity, ArrayList<FixturesResponseModel> fixturesResponseModel, AppInterfaces.NewsAdapterClick newsAdapterClick) {
        this.activity = activity;
        this.fixturesResponseModel = fixturesResponseModel;
        this.newsAdapterClick = newsAdapterClick;
    }

    @NonNull
    @Override
    public MatchMainAdapater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SinglelivematchlayoutBinding binding = SinglelivematchlayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchMainAdapater.ViewHolder holder, int position) {
        holder.tv_matchTitle.setText(fixturesResponseModel.get(position).getMatchTitle());
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        manager.setOrientation(RecyclerView.VERTICAL);
        holder.rcl_matches.setLayoutManager(manager);
        holder.rcl_matches.removeAllViews();
        FixturesAdapter adapter = new FixturesAdapter(false, activity, fixturesResponseModel.get(position).getMatches(), new AppInterfaces.NewsAdapterClick() {
            @Override
            public void getClickedNewsID(Integer newsID) {
                Constants.matchDTO = fixturesResponseModel.get(position).getMatches().get(newsID);
                if (Constants.matchDTO.getSession().contains("won") || Constants.matchDTO.getSession().contains("draw") || Constants.matchDTO.getSession().contains("match over") || Constants.matchDTO.getSession().contains("abandoned") || Constants.matchDTO.getSession().contains("No Result")) {
                    Constants.matchDTO.setMatchScoreLink(Constants.matchDTO.getMatchScoreLink().replace("live-cricket-score", "full-scoreboard"));
                    activity.startActivity(new Intent(activity, FullScoreBoardActivity.class));
                } else if (Constants.matchDTO.getSession().contains("chose") || Constants.matchDTO.getSession().contains("lead") || Constants.matchDTO.getSession().contains("trail") || Constants.matchDTO.getSession().contains("need")) {
                    activity.startActivity(new Intent(activity, LiveMatchScoreBoardActivity.class));
                } else if (Constants.matchDTO.getSession().contains("yet")) {
                    Toast.makeText(activity, "Score are not available yet", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "Score are not available yet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.rcl_matches.setAdapter(adapter);
    }


    @Override
    public int getItemCount() {
        return fixturesResponseModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_matchTitle;
        RecyclerView rcl_matches;

        public ViewHolder(SinglelivematchlayoutBinding binding) {
            super(binding.getRoot());
            tv_matchTitle =binding.tvMatchTitle;
            rcl_matches=binding.rclMatchList;
        }
    }
}
