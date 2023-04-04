package com.example.livecrickettvscores.Activities.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.PlayerCareerDetailsResponseModel;
import com.example.livecrickettvscores.databinding.SinglecareerlayoutBinding;

import java.util.ArrayList;

public class CareerDetailsAdapter extends RecyclerView.Adapter<CareerDetailsAdapter.ViewHolder> {
    Context context;
    ArrayList<PlayerCareerDetailsResponseModel.ValuesDTO> values;
    SinglecareerlayoutBinding binding;

    public CareerDetailsAdapter(Context context, ArrayList<PlayerCareerDetailsResponseModel.ValuesDTO> values) {
        this.context = context;
        this.values = values;
    }

    @NonNull
    @Override
    public CareerDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = SinglecareerlayoutBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CareerDetailsAdapter.ViewHolder holder, int position) {
        binding.tvCareertype.setText(values.get(position).getName());
        binding.tvBattingstyle.setText(values.get(position).getLastPlayed());
        binding.tvBirthplace.setText(values.get(position).getDebut());
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull SinglecareerlayoutBinding binding) {
            super(binding.getRoot());
        }
    }
}
