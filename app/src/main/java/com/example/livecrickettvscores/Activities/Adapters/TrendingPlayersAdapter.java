package com.example.livecrickettvscores.Activities.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.TrendingPlayersResponseModel;
import com.example.livecrickettvscores.databinding.SingleplayerLayoutBinding;

import java.util.ArrayList;

public class TrendingPlayersAdapter extends RecyclerView.Adapter<TrendingPlayersAdapter.ViewHolder> {
    ArrayList<TrendingPlayersResponseModel.PlayerDTO> playerDTO;
    AppInterfaces.NewsAdapterClick newsAdapterClick;
    Context context;

    public TrendingPlayersAdapter(Context context, ArrayList<TrendingPlayersResponseModel.PlayerDTO> playerDTO, AppInterfaces.NewsAdapterClick newsAdapterClick) {
        this.playerDTO = playerDTO;
        this.newsAdapterClick = newsAdapterClick;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleplayerLayoutBinding binding = SingleplayerLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_playercount.setText("" + Integer.valueOf(position + 1));
        holder.tv_playercountry.setText(playerDTO.get(position).getTeamName());
        holder.tv_playername.setText(playerDTO.get(position).getName());

        Glide.with(context).load("https://www.cricbuzz.com" + playerDTO.get(position).getFaceImageId())
                .encodeQuality(70)

                .into(holder.iv_player);


        holder.tv_playername.setOnClickListener(v -> newsAdapterClick.getClickedNewsID(position));
    }

    @Override
    public int getItemCount() {
        return playerDTO.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_playercount, tv_playercountry, tv_playername;
        ImageView iv_player;
        RelativeLayout rl_player;

        public ViewHolder(SingleplayerLayoutBinding binding) {
            super(binding.getRoot());
            tv_playercount = binding.tvPlayercount;
            tv_playercountry = binding.tvPlayercountry;
            tv_playername = binding.tvPlayername;
            iv_player = binding.ivPlayer;
            //rl_player = binding.rlPlayer;
        }
    }
}
