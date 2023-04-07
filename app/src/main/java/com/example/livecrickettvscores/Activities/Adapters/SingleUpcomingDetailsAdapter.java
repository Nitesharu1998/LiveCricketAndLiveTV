package com.example.livecrickettvscores.Activities.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FixturesResponseModel;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.databinding.SingleUpcomingMatchBinding;

import java.util.ArrayList;

public class SingleUpcomingDetailsAdapter extends RecyclerView.Adapter<SingleUpcomingDetailsAdapter.ViewHolder> {
    Context context;
    ArrayList<FixturesResponseModel.MatchesDTO> fixturesResponseModel;

    public SingleUpcomingDetailsAdapter(Context context, ArrayList<FixturesResponseModel.MatchesDTO> fixturesResponseModel) {
        this.context = context;
        this.fixturesResponseModel = fixturesResponseModel;
    }

    @NonNull
    @Override
    public SingleUpcomingDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleUpcomingMatchBinding binding = SingleUpcomingMatchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SingleUpcomingDetailsAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleUpcomingDetailsAdapter.ViewHolder holder, int position) {
        holder.tv_intl.setText(fixturesResponseModel.get(position).getMatchType());
        holder.tv_match1team.setText(fixturesResponseModel.get(position).getTeamOne());
        holder.tv_match2team.setText(fixturesResponseModel.get(position).getTeamTwo());
        holder.tv_matchtitle.setText(fixturesResponseModel.get(position).getMatchTitle());
        Glide.with(context).load(Global.getFlagOfCountry(fixturesResponseModel.get(position).getTeamOne())).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(holder.binding.ivTeam1);
        Glide.with(context).load(Global.getFlagOfCountry(fixturesResponseModel.get(position).getTeamTwo())).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(holder.binding.ivTeam2);
    }

    @Override
    public int getItemCount() {
        return fixturesResponseModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_team2, iv_team1;
        TextView tv_intl, tv_matchtitle, tv_match1team, tv_match2team;
        SingleUpcomingMatchBinding binding;

        public ViewHolder(@NonNull SingleUpcomingMatchBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            iv_team1 = itemView.ivTeam1;
            iv_team2 = itemView.ivTeam2;
            tv_intl = itemView.tvIntl;
            tv_matchtitle = itemView.tvMatchtitle;
            tv_match1team = itemView.tvMatch1team;
            tv_match2team = itemView.tvMatch2team;
        }
    }
}
