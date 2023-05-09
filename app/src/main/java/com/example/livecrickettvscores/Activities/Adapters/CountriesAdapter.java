package com.example.livecrickettvscores.Activities.Adapters;

import android.content.Context;
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
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.R;
import com.example.livecrickettvscores.databinding.SingleCountryBinding;

import java.util.ArrayList;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder> {
    Context context;
    ArrayList<CountriesResponseModel> countriesResponseModels;
    AppInterfaces.NewsAdapterClick newsAdapterClick;
    boolean isBaseURL;
    boolean isMen;

    public CountriesAdapter(boolean isMen, boolean isBaseURL, Context context, ArrayList<CountriesResponseModel> countriesResponseModels, AppInterfaces.NewsAdapterClick newsAdapterClick) {
        this.context = context;
        this.isBaseURL = isBaseURL;
        this.isMen = isMen;
        this.countriesResponseModels = countriesResponseModels;
        this.newsAdapterClick = newsAdapterClick;
    }

    @NonNull
    @Override
    public CountriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(DataBindingUtil.inflate(inflater, R.layout.single_country, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesAdapter.ViewHolder holder, int position) {
        if (isBaseURL) {
            Glide.with(context).load(Constants.CricBuzzBaseURL + countriesResponseModels.get(position).getCountryFlag()).error(R.drawable.default_flag).into(holder.binding.ivCountry);
        } else {
            Glide.with(context).load(Global.getFlagOfCountry(isMen, countriesResponseModels.get(position).getCountryName())).error(R.drawable.default_flag).into(holder.binding.ivCountry);

        }
        holder.binding.tvCountryName.setText(countriesResponseModels.get(position).getCountryName());
        holder.binding.tvCountryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsAdapterClick.getClickedNewsID(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return countriesResponseModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        SingleCountryBinding binding;
        public ViewHolder(@NonNull SingleCountryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
