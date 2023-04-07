package com.example.livecrickettvscores.Activities.Adapters;

import android.content.Context;
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
    Context context;
    ArrayList<FixturesResponseModel> fixturesResponseModel;
    AppInterfaces.NewsAdapterClick newsAdapterClick;

    public MatchMainAdapater(Context context, ArrayList<FixturesResponseModel> fixturesResponseModel, AppInterfaces.NewsAdapterClick newsAdapterClick) {
        this.context = context;
        this.fixturesResponseModel = fixturesResponseModel;
        this.newsAdapterClick=newsAdapterClick;
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

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(RecyclerView.VERTICAL);
        holder.rcl_matches.setLayoutManager(manager);
        holder.rcl_matches.removeAllViews();
        FixturesAdapter adapter = new FixturesAdapter(context, fixturesResponseModel.get(position).getMatches(), new AppInterfaces.NewsAdapterClick() {
            @Override
            public void getClickedNewsID(Integer newsID) {
                Constants.matchDTO = fixturesResponseModel.get(position).getMatches().get(newsID);
                if (!fixturesResponseModel.get(position).getMatches().get(newsID).getSession().contains("won")) {
                    context.startActivity(new Intent(context, LiveMatchScoreBoardActivity.class));
                } else if (fixturesResponseModel.get(position).getMatches().get(newsID).getSession().contains("starts in")) {
                    Toast.makeText(context, "Scores are not currently available", Toast.LENGTH_SHORT).show();
                } else if (Constants.matchDTO.getMatchScoreLink().contains("live-cricket-score")) {
                    Constants.matchDTO.getMatchScoreLink().replace("live-cricket-score", "full-scorecard");
                    context.startActivity(new Intent(context, FullScoreBoardActivity.class));
                } else if (Constants.matchDTO.getMatchScoreLink().contains("match-preview")) {
                    Constants.matchDTO.getMatchScoreLink().replace("match-preview", "full-scorecard");
                    context.startActivity(new Intent(context, FullScoreBoardActivity.class));
                }else{
                    context.startActivity(new Intent(context, FullScoreBoardActivity.class));
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
