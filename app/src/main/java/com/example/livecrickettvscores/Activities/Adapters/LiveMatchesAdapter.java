package com.example.livecrickettvscores.Activities.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FixturesResponseModel;
import com.example.livecrickettvscores.R;

import java.util.ArrayList;

public class LiveMatchesAdapter/* extends RecyclerView.Adapter<LiveMatchesAdapter.ViewHolder> */{/*
    ArrayList<FixturesResponseModel.DataDTO> data;

    public LiveMatchesAdapter(ArrayList<FixturesResponseModel.DataDTO> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public LiveMatchesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlelivematchlayout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LiveMatchesAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }*/
}
