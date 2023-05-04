package com.example.livecrickettvscores.Activities.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.CountriesResponseModel;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.example.livecrickettvscores.R;
import com.example.livecrickettvscores.databinding.SingleCountryPlayerLayoutBinding;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {
    Activity activity;
    ArrayList<CountriesResponseModel> responseModelArrayList;

    AppInterfaces.NewsAdapterClick newsAdapterClick;

    public PlayerAdapter(Activity activity, ArrayList<CountriesResponseModel> responseModelArrayList, AppInterfaces.NewsAdapterClick newsAdapterClick) {
        this.activity = activity;
        this.responseModelArrayList = responseModelArrayList;
        this.newsAdapterClick = newsAdapterClick;
    }


    @NonNull
    @Override
    public PlayerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new PlayerAdapter.ViewHolder(DataBindingUtil.inflate(inflater, R.layout.single_country_player_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerAdapter.ViewHolder holder, int position) {
        String[] playerfullName = responseModelArrayList.get(position).getCountryName().split(" ");
        Glide.with(activity).load(Constants.CricBuzzBaseURL + responseModelArrayList.get(position).getCountryFlag()).into(holder.binding.ivPlayerImage);
        if (playerfullName.length > 1) {
            holder.binding.tvPlayerLastName.setText(playerfullName[1]);
            holder.binding.tvPlayerFirstName.setText(playerfullName[0]);
        } else {
            holder.binding.tvPlayerFirstName.setText(responseModelArrayList.get(position).getCountryName());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsAdapterClick.getClickedNewsID(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return responseModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SingleCountryPlayerLayoutBinding binding;

        public ViewHolder(@NonNull SingleCountryPlayerLayoutBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
