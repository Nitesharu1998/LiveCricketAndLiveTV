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
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.NewsListResponseModel;
import com.example.livecrickettvscores.Activities.Utils.DateUtil;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.databinding.SinglefeaturednewsBinding;

import java.util.ArrayList;

public class FeaturedNewsAdapter extends RecyclerView.Adapter<FeaturedNewsAdapter.ViewHolder> {
    Context context;
    ArrayList<NewsListResponseModel.NewsListDTO> newsList;
    AppInterfaces.NewsAdapterClick newsAdapterClick;
    int state;

    public FeaturedNewsAdapter(int state, Context context, ArrayList<NewsListResponseModel.NewsListDTO> newsList, AppInterfaces.NewsAdapterClick newsAdapterClick) {
        this.context = context;
        this.newsList = newsList;
        this.newsAdapterClick = newsAdapterClick;
        this.state = state;
    }

    @NonNull
    @Override
    public FeaturedNewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SinglefeaturednewsBinding binding = SinglefeaturednewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedNewsAdapter.ViewHolder holder, int position) {
        NewsListResponseModel.NewsListDTO newsListDTO = newsList.get(position);
        holder.tv_newstitle.setText(newsListDTO.getStory().getHline());
        holder.tv_newssource.setText(newsListDTO.getStory().getSource());


        if (state == 0) {
            holder.tv_newssource.setVisibility(View.VISIBLE);

            holder.rl_matchview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    newsAdapterClick.getClickedNewsID(newsListDTO.getStory().getId());
                }
            });
            holder.tv_newsdate.setText(DateUtil.getDateFromSeconds(Long.parseLong(newsListDTO.getStory().getPubTime())));
            Glide.with(context).load(Global.getTheImage(context, String.valueOf(newsListDTO.getStory().getImageId()))).into(holder.iv_news);
        } else {
            holder.tv_newssource.setVisibility(View.GONE);
            holder.rl_matchview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    newsAdapterClick.getClickedNewsID(position);
                }
            });
            holder.tv_newsdate.setText((newsListDTO.getStory().getPubTime()));
            Glide.with(context).load(newsListDTO.getStory().getImageURL()).into(holder.iv_news);
        }


    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_news;
        TextView tv_newssource, tv_newsdate, tv_newstitle;
        RelativeLayout rl_matchview;

        public ViewHolder(SinglefeaturednewsBinding binding) {
            super(binding.getRoot());
            rl_matchview = binding.rlMatchview;
            iv_news = binding.ivNews;
            tv_newssource = binding.tvNewssource;
            tv_newsdate = binding.tvNewsdate;
            tv_newstitle = binding.tvNewstitle;
        }
    }
}

