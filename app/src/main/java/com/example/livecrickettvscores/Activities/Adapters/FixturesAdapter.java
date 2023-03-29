package com.example.livecrickettvscores.Activities.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FixturesResponseModel;
import com.example.livecrickettvscores.Activities.Utils.ConnectionDetector;
import com.example.livecrickettvscores.databinding.SinglelivematchlayoutBinding;

import java.util.ArrayList;

public class FixturesAdapter extends RecyclerView.Adapter<FixturesAdapter.ViewHolder> {
    Context context;
    ArrayList<FixturesResponseModel.MatchesDTO> matchesDTOArrayList;
    ConnectionDetector cd;

    public FixturesAdapter(Context context, ArrayList<FixturesResponseModel.MatchesDTO> matchesDTOArrayList) {
        this.context = context;
        this.matchesDTOArrayList = matchesDTOArrayList;
        cd = new ConnectionDetector(context);
    }

    @NonNull
    @Override
    public FixturesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SinglelivematchlayoutBinding singlelivematchlayoutBinding = SinglelivematchlayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(singlelivematchlayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FixturesAdapter.ViewHolder holder, int position) {
        holder.tv_matchstatus.setText(matchesDTOArrayList.get(position).getSession());
        holder.tv_matchtitle.setText(matchesDTOArrayList.get(position).getMatchTitle());
        holder.tv_match1team.setText(matchesDTOArrayList.get(position).getTeamOne());
        holder.tv_match2team.setText(matchesDTOArrayList.get(position).getTeamTwo());
        holder.tv_match1score.setText(matchesDTOArrayList.get(position).getTeamOneScore());
        holder.tv_match2score.setText(matchesDTOArrayList.get(position).getTeamTwoScore());
    }


    @Override
    public int getItemCount() {
        return matchesDTOArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_team2, iv_team1, iv_bgteam2, iv_bgteam1;
        TextView tv_matchstatus, tv_match2score, tv_match2team, tv_match1score, tv_match1team, tv_matchtitle;

        public ViewHolder(SinglelivematchlayoutBinding singlelivematchlayoutBinding) {
            super(singlelivematchlayoutBinding.getRoot());
            iv_team2 = singlelivematchlayoutBinding.ivTeam2;
            iv_team1 = singlelivematchlayoutBinding.ivTeam1;
            iv_bgteam2 = singlelivematchlayoutBinding.ivBgteam2;
            iv_bgteam1 = singlelivematchlayoutBinding.ivBgteam1;

            tv_matchstatus = singlelivematchlayoutBinding.tvMatchstatus;
            tv_match2score = singlelivematchlayoutBinding.tvMatch2score;
            tv_match2team = singlelivematchlayoutBinding.tvMatch2team;
            tv_match1score = singlelivematchlayoutBinding.tvMatch1score;
            tv_match1team = singlelivematchlayoutBinding.tvMatch1team;
            tv_matchtitle = singlelivematchlayoutBinding.tvMatchtitle;

        }
    }
}
