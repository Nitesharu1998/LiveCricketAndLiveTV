package com.example.livecrickettvscores.Activities.Adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.PredictionDetailsModel;
import com.example.livecrickettvscores.Activities.Utils.InputUtils;
import com.example.livecrickettvscores.databinding.SinglepredictionLayoutBinding;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class FantasyPredictionAdapter extends RecyclerView.Adapter<FantasyPredictionAdapter.ViewHolder> {


    ArrayList<PredictionDetailsModel> predictionDetailsModels;
    AppInterfaces.NewsAdapterClick newsAdapterClick;
    Activity activity;

    public FantasyPredictionAdapter(Activity activity, ArrayList<PredictionDetailsModel> predictionDetailsModels, AppInterfaces.NewsAdapterClick newsAdapterClick) {
        this.predictionDetailsModels = predictionDetailsModels;
        this.newsAdapterClick = newsAdapterClick;
        this.activity = activity;
    }

    @NonNull
    @Override
    public FantasyPredictionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SinglepredictionLayoutBinding binding = SinglepredictionLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FantasyPredictionAdapter.ViewHolder holder, int position) {
        PredictionDetailsModel singleData = predictionDetailsModels.get(position);
        Glide.with(activity).load(singleData.getTeam1Img()).into(holder.iv_team1);
        Glide.with(activity).load(singleData.getTeam2Img()).into(holder.iv_team2);
        holder.tv_team1.setText(singleData.getTeam1());
        holder.tv_team2.setText(singleData.getTeam2());
        holder.tv_matchlocation.setText(singleData.getMatchLocation());
        holder.tv_information.setText(singleData.getMatchName());
        holder.btnMatch.setText("See Match Prediction");
        holder.tv_time.setText(singleData.getTime());
        if (InputUtils.CheckEqualCaseSensitive(singleData.getPredictionStatus(), "Prediction posted!")) {
            holder.btnMatch.setBackgroundColor(Color.parseColor("#103CC5"));
            holder.btnMatch.setClickable(true);

            holder.btnMatch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newsAdapterClick.getClickedNewsID(position);
                }
            });
        } else {
            holder.btnMatch.setBackgroundColor(Color.parseColor("#A3A3A3"));
            holder.btnMatch.setClickable(false);
        }

    }

    @Override
    public int getItemCount() {
        return predictionDetailsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_time, tv_team1, tv_team2, tv_matchlocation, tv_information;
        ImageView iv_team1, iv_team2;
        MaterialButton btnMatch;

        public ViewHolder(@NonNull SinglepredictionLayoutBinding singlepredictionLayoutBinding) {
            super(singlepredictionLayoutBinding.getRoot());
            tv_time = singlepredictionLayoutBinding.tvTime;
            tv_team1 = singlepredictionLayoutBinding.tvTeam1;
            tv_team2 = singlepredictionLayoutBinding.tvTeam2;
            tv_matchlocation = singlepredictionLayoutBinding.tvMatchlocation;
            tv_information = singlepredictionLayoutBinding.tvInformation;
            iv_team1 = singlepredictionLayoutBinding.ivTeam1;
            iv_team2 = singlepredictionLayoutBinding.ivTeam2;
            btnMatch = singlepredictionLayoutBinding.btnMatch;
        }

    }
}
