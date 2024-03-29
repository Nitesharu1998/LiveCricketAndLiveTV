package com.example.livecrickettvscores.Activities.videoplayer.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdUtils;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.example.livecrickettvscores.Activities.videoplayer.data.VideoData;
import com.example.livecrickettvscores.R;

import java.util.List;

public class VideoFilesAdapter extends RecyclerView.Adapter<VideoFilesAdapter.ViewHolder> {

    private final List<VideoData> list;
    private final VideoFilesHandler handler;
    Context context;

    public VideoFilesAdapter(List<VideoData> list, Context context, VideoFilesHandler handler) {
        this.list = list;
        this.handler = handler;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ImageView image = holder.videoPreview;
        if (position != 0 && (position % 10) == 0) {
            holder.relativeLayout.setVisibility(View.VISIBLE);
            AdUtils.showNativeAd((Activity) holder.itemView.getContext(), Constants.adsJsonPOJO.getParameters().getNative_id().getDefaultValue().getValue(), holder.nativeads, false);
        }
//        Glide.with(image)
//                .load(list.get(position).videouri)
//                .into(image);
        Glide.with(context).load(list.get(position).videouri).into(image);
        holder.videoTitle.setText(list.get(position).videoName);
//        holder.videoduration.setText(list.get(position).vide);

        holder.itemView.setOnClickListener(view -> handler.onChoose(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView videoPreview;
        TextView videoTitle, videoduration;
        RelativeLayout relativeLayout;
        LinearLayout nativeads;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            videoPreview = itemView.findViewById(R.id.iv_song_cover);
            videoTitle = itemView.findViewById(R.id.tv_song_title);
            relativeLayout = itemView.findViewById(R.id.rl);
            nativeads = itemView.findViewById(R.id.native_ads);
//            videoduration = itemView.findViewById(R.id.tv_song_artist);

        }
    }

    public interface VideoFilesHandler {
        void onChoose(int position);
    }
}
