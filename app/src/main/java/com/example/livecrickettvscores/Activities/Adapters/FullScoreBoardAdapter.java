package com.example.livecrickettvscores.Activities.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FullScoreBoardResponseModel;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.R;
import com.example.livecrickettvscores.databinding.ScoreInningLayoutBinding;

public class FullScoreBoardAdapter extends RecyclerView.Adapter<FullScoreBoardAdapter.ViewHolder> {
    Activity actvity;
    FullScoreBoardResponseModel fullScoreBoardResponseModel;

    public FullScoreBoardAdapter(Activity actvity, FullScoreBoardResponseModel fullScoreBoardResponseModel) {
        this.actvity = actvity;
        this.fullScoreBoardResponseModel = fullScoreBoardResponseModel;
    }

    @NonNull
    @Override
    public FullScoreBoardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(DataBindingUtil.inflate(layoutInflater, R.layout.score_inning_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull FullScoreBoardAdapter.ViewHolder holder, int position) {
        holder.binding.tvMatchTitle.setText(fullScoreBoardResponseModel.getMatchInningDTOS().get(position).getTeamName());
        holder.binding.tvScoretext.setText(fullScoreBoardResponseModel.getMatchInningDTOS().get(position).getTeamTotal());
        holder.binding.tvFallOfWickets.setText("Fall of Wickets:\n"+fullScoreBoardResponseModel.getMatchInningDTOS().get(position).getTeamFallOfWicket());
        BatsmanAdapter batsmanAdapter = new BatsmanAdapter(actvity, fullScoreBoardResponseModel.getMatchInningDTOS().get(position).getBatsmanDTOS());
        BowlerAdapter bowlerAdapter = new BowlerAdapter(actvity, fullScoreBoardResponseModel.getMatchInningDTOS().get(position).getBowlerDTOS());

        holder.binding.rclBatsmanList.setLayoutManager(Global.getManagerWithOrientation(actvity, RecyclerView.VERTICAL));
        holder.binding.rclBowlerList.setLayoutManager(Global.getManagerWithOrientation(actvity, RecyclerView.VERTICAL));

        holder.binding.rclBatsmanList.setAdapter(batsmanAdapter);
        holder.binding.rclBowlerList.setAdapter(bowlerAdapter);
    }

    @Override
    public int getItemCount() {
        return fullScoreBoardResponseModel.getMatchInningDTOS().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ScoreInningLayoutBinding binding;

        public ViewHolder(@NonNull ScoreInningLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
