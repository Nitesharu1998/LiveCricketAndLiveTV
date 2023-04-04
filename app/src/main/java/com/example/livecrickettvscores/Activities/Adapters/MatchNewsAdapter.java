package com.example.livecrickettvscores.Activities.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.LatestNewsModel;
import com.example.livecrickettvscores.R;
import com.example.livecrickettvscores.databinding.SinglefeaturednewsBinding;

import java.util.ArrayList;

public class MatchNewsAdapter extends RecyclerView.Adapter<MatchNewsAdapter.ViewHolder> {
    Context context;
    ArrayList<LatestNewsModel.NewsListDTO> newsListDTO;
    AppInterfaces.NewsAdapterClick newsAdapterClick;

    public MatchNewsAdapter(Context context, ArrayList<LatestNewsModel.NewsListDTO> newsListDTO, AppInterfaces.NewsAdapterClick newsAdapterClick) {
        this.context = context;
        this.newsListDTO = newsListDTO;
        this.newsAdapterClick = newsAdapterClick;
    }

    @NonNull
    @Override
    public MatchNewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SinglefeaturednewsBinding binding = SinglefeaturednewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MatchNewsAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchNewsAdapter.ViewHolder holder, int position) {
        holder.tv_newstitle.setText(newsListDTO.get(position).getHline());
        holder.tv_newssource.setVisibility(View.GONE);
        holder.rl_matchview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsAdapterClick.getClickedNewsID(position);
            }
        });
        holder.tv_newsdate.setText((newsListDTO.get(position).getPubTime()));
        Glide.with(context).load(newsListDTO.get(position).getImageURL()).error(R.drawable.ic_applogo).into(holder.iv_news);
    }

    @Override
    public int getItemCount() {
        return newsListDTO.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_news;
        TextView tv_newssource, tv_newsdate, tv_newstitle;
        RelativeLayout rl_matchview;

        public ViewHolder(@NonNull SinglefeaturednewsBinding binding) {
            super(binding.getRoot());
            rl_matchview = binding.rlMatchview;
            iv_news = binding.ivNews;
            tv_newssource = binding.tvNewssource;
            tv_newsdate = binding.tvNewsdate;
            tv_newstitle = binding.tvNewstitle;
        }
    }
}

