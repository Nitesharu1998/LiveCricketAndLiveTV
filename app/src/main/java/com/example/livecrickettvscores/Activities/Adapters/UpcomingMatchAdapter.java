package com.example.livecrickettvscores.Activities.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FixturesResponseModel;
import com.example.livecrickettvscores.databinding.SinglelivematchlayoutBinding;

import java.util.ArrayList;

public class UpcomingMatchAdapter extends RecyclerView.Adapter<UpcomingMatchAdapter.ViewHolder> {
    Context context;
    ArrayList<FixturesResponseModel> upComingList;

    public UpcomingMatchAdapter(Context context, ArrayList<FixturesResponseModel> upComingList) {
        this.context = context;
        this.upComingList = upComingList;
    }

    @NonNull
    @Override
    public UpcomingMatchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SinglelivematchlayoutBinding binding = SinglelivematchlayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UpcomingMatchAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingMatchAdapter.ViewHolder holder, int position) {
        holder.tv_matchTitle.setText(upComingList.get(position).getMatchTitle());

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(RecyclerView.VERTICAL);
        holder.rcl_matches.setLayoutManager(manager);
        holder.rcl_matches.removeAllViews();
        SingleUpcomingDetailsAdapter adapter =new SingleUpcomingDetailsAdapter(context, upComingList.get(position).getMatches());
        holder.rcl_matches.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return upComingList.size();
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
