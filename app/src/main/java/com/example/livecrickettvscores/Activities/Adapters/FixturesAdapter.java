package com.example.livecrickettvscores.Activities.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FixturesResponseModel;
import com.example.livecrickettvscores.Activities.Utils.ConnectionDetector;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.Activities.Utils.StringUtils;
import com.example.livecrickettvscores.R;
import com.example.livecrickettvscores.databinding.MatchLayoutBinding;

import java.util.ArrayList;

public class FixturesAdapter extends RecyclerView.Adapter<FixturesAdapter.ViewHolder> {
    Context context;
    ArrayList<FixturesResponseModel.MatchesDTO> matchesDTOArrayList;
    ConnectionDetector cd;
    AppInterfaces.NewsAdapterClick newsAdapterClick;
    boolean isChangeTint;


    public FixturesAdapter(boolean isChangeTint, Context context, ArrayList<FixturesResponseModel.MatchesDTO> matchesDTOArrayList, AppInterfaces.NewsAdapterClick newsAdapterClick) {
        this.context = context;
        this.matchesDTOArrayList = matchesDTOArrayList;
        this.newsAdapterClick = newsAdapterClick;
        this.isChangeTint = isChangeTint;
        cd = new ConnectionDetector(context);
    }

    @NonNull
    @Override
    public FixturesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MatchLayoutBinding matchLayoutBinding = MatchLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(matchLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FixturesAdapter.ViewHolder holder, int position) {
        if (StringUtils.isNull(matchesDTOArrayList.get(position).getSession())) {
            holder.ll_mainLinear.setVisibility(View.GONE);
        } else {
            holder.ll_mainLinear.setVisibility(View.VISIBLE);
        }
        if (isChangeTint) {
            holder.itemView.setBackgroundTintList(context.getResources().getColorStateList(R.color.deep_blue));
            holder.ll_blank.setBackgroundTintList(context.getResources().getColorStateList(R.color.white));

            holder.tv_matchstatus.setTextColor(context.getColor(R.color.white));
            holder.tv_matchtitle.setTextColor(context.getColor(R.color.white));
            holder.tv_match1team.setTextColor(context.getColor(R.color.white));
            holder.tv_match2team.setTextColor(context.getColor(R.color.white));
            holder.tv_match1score.setTextColor(context.getColor(R.color.white));
            holder.tv_match2score.setTextColor(context.getColor(R.color.white));

        } else {
            holder.itemView.setBackgroundTintList(context.getResources().getColorStateList(R.color.light_gray));
            holder.ll_blank.setBackgroundTintList(context.getResources().getColorStateList(R.color.black));

            holder.tv_matchstatus.setTextColor(context.getColor(R.color.black));
            holder.tv_matchtitle.setTextColor(context.getColor(R.color.black));
            holder.tv_match1team.setTextColor(context.getColor(R.color.black));
            holder.tv_match2team.setTextColor(context.getColor(R.color.black));
            holder.tv_match1score.setTextColor(context.getColor(R.color.black));
            holder.tv_match2score.setTextColor(context.getColor(R.color.black));
        }
        holder.tv_matchstatus.setText(matchesDTOArrayList.get(position).getSession());
        holder.tv_matchtitle.setText(matchesDTOArrayList.get(position).getMatchTitle());
        holder.tv_match1team.setText(matchesDTOArrayList.get(position).getTeamOne());
        holder.tv_match2team.setText(matchesDTOArrayList.get(position).getTeamTwo());
        holder.tv_match1score.setText(matchesDTOArrayList.get(position).getTeamOneScore());
        holder.tv_match2score.setText(matchesDTOArrayList.get(position).getTeamTwoScore());

        Glide.with(context).load(Global.getFlagOfCountry(true,matchesDTOArrayList.get(position).getTeamOne())).into(holder.iv_team1);
        Glide.with(context).load(Global.getFlagOfCountry(true,matchesDTOArrayList.get(position).getTeamTwo())).into(holder.iv_team2);

        holder.ll_mainLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsAdapterClick.getClickedNewsID(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return matchesDTOArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_team2, iv_team1, iv_bgteam2, iv_bgteam1;
        TextView tv_matchstatus, tv_match2score, tv_match2team, tv_match1score, tv_match1team, tv_matchtitle;
        LinearLayout ll_mainLinear, ll_blank;

        public ViewHolder(MatchLayoutBinding matchLayoutBinding) {
            super(matchLayoutBinding.getRoot());
            iv_team2 = matchLayoutBinding.ivTeam2;
            iv_team1 = matchLayoutBinding.ivTeam1;
            iv_bgteam2 = matchLayoutBinding.ivBgteam2;
            iv_bgteam1 = matchLayoutBinding.ivBgteam1;
            ll_mainLinear = matchLayoutBinding.llMainLinear;

            tv_matchstatus = matchLayoutBinding.tvMatchstatus;
            tv_match2score = matchLayoutBinding.tvMatch2score;
            tv_match2team = matchLayoutBinding.tvMatch2team;
            tv_match1score = matchLayoutBinding.tvMatch1score;
            tv_match1team = matchLayoutBinding.tvMatch1team;
            tv_matchtitle = matchLayoutBinding.tvMatchtitle;
            ll_blank = matchLayoutBinding.llBlank;

        }
    }
}
