package com.example.livecrickettvscores.Activities.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FullScoreBoardResponseModel;
import com.example.livecrickettvscores.R;
import com.example.livecrickettvscores.databinding.SingleBowlerLayoutBinding;

import java.util.ArrayList;

public class BowlerAdapter extends RecyclerView.Adapter<BowlerAdapter.ViewHolder> {
    SingleBowlerLayoutBinding binding;
    Activity actvity;
    ArrayList<FullScoreBoardResponseModel.BowlerDTO> bowlerDTOS;

    public BowlerAdapter(Activity actvity, ArrayList<FullScoreBoardResponseModel.BowlerDTO> bowlerDTOS) {
        this.actvity = actvity;
        this.bowlerDTOS = bowlerDTOS;
    }

    @NonNull
    @Override
    public BowlerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(DataBindingUtil.inflate(inflater, R.layout.single_bowler_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BowlerAdapter.ViewHolder holder, int position) {
        holder.binding.bowler1.setText(bowlerDTOS.get(position).getBowlerName());
        holder.binding.bowler1Maiden.setText(bowlerDTOS.get(position).getM());
        holder.binding.bowler1Over.setText(bowlerDTOS.get(position).getO());
        holder.binding.bowler1Runs.setText(bowlerDTOS.get(position).getR());
        holder.binding.bowler1Wikets.setText(bowlerDTOS.get(position).getW());
        holder.binding.bowler1Economy.setText(bowlerDTOS.get(position).getEcon());
    }

    @Override
    public int getItemCount() {
        return bowlerDTOS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SingleBowlerLayoutBinding binding;

        public ViewHolder(@NonNull SingleBowlerLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
