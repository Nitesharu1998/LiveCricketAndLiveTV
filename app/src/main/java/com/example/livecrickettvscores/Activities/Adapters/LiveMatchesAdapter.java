package com.example.livecrickettvscores.Activities.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.LiveMatchesResponseModel;
import com.example.livecrickettvscores.R;

import java.util.ArrayList;

public class LiveMatchesAdapter extends RecyclerView.Adapter<LiveMatchesAdapter.ViewHolder> {
    ArrayList<LiveMatchesResponseModel.DataDTO> data;

    public LiveMatchesAdapter(ArrayList<LiveMatchesResponseModel.DataDTO> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public LiveMatchesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       /* LayoutInflater inflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlelivematchlayout, false);*/
        return null/* new ViewHolder()*/;
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
    }
}
