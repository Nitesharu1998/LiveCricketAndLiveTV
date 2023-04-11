package com.example.livecrickettvscores.Activities.Adapters;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FullScoreBoardResponseModel;
import com.example.livecrickettvscores.R;
import com.example.livecrickettvscores.databinding.SingleBatsmanLayoutBinding;

import java.util.ArrayList;

public class BatsmanAdapter extends RecyclerView.Adapter<BatsmanAdapter.ViewHolder> {
    Activity actvity;
    ArrayList<FullScoreBoardResponseModel.BatsmanDTO> batsmanDTOS;

    public BatsmanAdapter(Activity actvity, ArrayList<FullScoreBoardResponseModel.BatsmanDTO> batsmanDTOS) {
        this.actvity = actvity;
        this.batsmanDTOS = batsmanDTOS;
    }

    @NonNull
    @Override
    public BatsmanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(DataBindingUtil.inflate(layoutInflater, R.layout.single_batsman_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BatsmanAdapter.ViewHolder holder, int position) {
        holder.binding.batman1.setText(batsmanDTOS.get(position).getBatsmanName());
        holder.binding.batman1Runs.setText(batsmanDTOS.get(position).getR());
        holder.binding.batman1Balls.setText(batsmanDTOS.get(position).getB());
        holder.binding.batman1Six.setText(batsmanDTOS.get(position).getSixes());
        holder.binding.batman1Fours.setText(batsmanDTOS.get(position).getFours());
        holder.binding.batman1Strike.setText(batsmanDTOS.get(position).getSR());

    }

    @Override
    public int getItemCount() {
        return batsmanDTOS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SingleBatsmanLayoutBinding binding;

        public ViewHolder(@NonNull SingleBatsmanLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
