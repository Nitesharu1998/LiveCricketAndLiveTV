package com.example.livecrickettvscores.Activities.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FixturesResponseModel;
import com.example.livecrickettvscores.Activities.Utils.ConnectionDetector;
import com.example.livecrickettvscores.Activities.Utils.DateUtil;
import com.example.livecrickettvscores.Activities.Utils.EncryptionUtils;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.Activities.Utils.InputUtils;
import com.example.livecrickettvscores.R;
import com.example.livecrickettvscores.databinding.SinglelivematchlayoutBinding;

import java.util.ArrayList;

public class FixturesAdapter extends RecyclerView.Adapter<FixturesAdapter.ViewHolder> {
    Context context;
    ArrayList<FixturesResponseModel.TypeMatchesDTO.SeriesAdWrapperDTO.SeriesMatchesDTO.MatchesDTO> matchesDTOArrayList;
    ConnectionDetector cd;
    public FixturesAdapter(Context context, ArrayList<FixturesResponseModel.TypeMatchesDTO.SeriesAdWrapperDTO.SeriesMatchesDTO.MatchesDTO> matchesDTOArrayList) {
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
        FixturesResponseModel.TypeMatchesDTO.SeriesAdWrapperDTO.SeriesMatchesDTO.MatchesDTO singlematch = matchesDTOArrayList.get(position);
        loadImagesForTeams(holder, singlematch.getMatchInfo().getTeam1().getImageId(), singlematch.getMatchInfo().getTeam2().getImageId());
        if (InputUtils.CheckEqualCaseSensitive(singlematch.getMatchInfo().getState(), "Complete")) {
            holder.tv_matchstatus.setText(singlematch.getMatchInfo().getStatus());
        } else {
            holder.tv_matchstatus.setText("Scheduled for " + DateUtil.getDateFromSeconds(Long.parseLong(singlematch.getMatchInfo().getStartDate())));
        }


        holder.tv_matchtitle.setText(singlematch.getMatchInfo().getSeriesName());
        holder.tv_match1score.setText(getScoreForTeam(1, singlematch.getMatchScore()));
        holder.tv_match2score.setText(getScoreForTeam(2, singlematch.getMatchScore()));
        holder.tv_match1team.setText(singlematch.getMatchInfo().getTeam1().getTeamName());
        holder.tv_match2team.setText(singlematch.getMatchInfo().getTeam2().getTeamName());


    }

    private String getScoreForTeam(int teamNumber, FixturesResponseModel.TypeMatchesDTO.SeriesAdWrapperDTO.SeriesMatchesDTO.MatchesDTO.MatchScoreDTO matchScore) {
        if (teamNumber == 1) {
            if (matchScore != null && matchScore.getTeam1Score() != null && matchScore.getTeam1Score().getInngs1() != null) {
                return matchScore.getTeam1Score().getInngs1().getRuns() + " - " + matchScore.getTeam1Score().getInngs1().getWickets();
            }
        } else {
            if (matchScore != null && matchScore.getTeam2Score() != null && matchScore.getTeam2Score().getInngs1() != null) {
                return matchScore.getTeam2Score().getInngs1().getRuns() + " - " + matchScore.getTeam2Score().getInngs1().getWickets();
            }
        }
        return "";
    }


    private void loadImagesForTeams(ViewHolder holder, int team1Image, int team2Image) {
        if (cd.isConnectingToInternet()) {
            Glide.with(context).load(Global.getTheImage(context, String.valueOf(team1Image))).into(holder.iv_team1);

            Glide.with(context).load(Global.getTheImage(context, String.valueOf(team2Image))).into(holder.iv_team2);
        }
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
