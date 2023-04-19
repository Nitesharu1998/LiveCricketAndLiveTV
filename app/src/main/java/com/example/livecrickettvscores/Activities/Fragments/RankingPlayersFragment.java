package com.example.livecrickettvscores.Activities.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecrickettvscores.Activities.Adapters.CountriesAdapter;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.CountryPlayersActivity;
import com.example.livecrickettvscores.Activities.Retrofit.AppAsyncTasks;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.CountriesResponseModel;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.R;
import com.example.livecrickettvscores.databinding.FragmentRankingPlayersBinding;

import org.jsoup.select.Elements;

import java.util.ArrayList;

public class RankingPlayersFragment extends Fragment {
    FragmentRankingPlayersBinding binding;
    ArrayList<CountriesResponseModel> responseModel = new ArrayList<>();
    ArrayList<CountriesResponseModel> filteredCountries;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ranking_players, container, false);
        AppAsyncTasks.CallRankingContries callRankingContries = new AppAsyncTasks.CallRankingContries(Constants.CricketTeamURL, binding.getRoot().getContext(), new AppInterfaces.WebScrappingInterface() {
            @Override
            public void getScrapedDocument(Elements document) {
                setUpCountryList(document);
            }
        });
        callRankingContries.execute();


        binding.tetCountryName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() > 0) {
                    filterCountries(charSequence.toString());
                } else {
                    setUpAdapter(responseModel);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return binding.getRoot();
    }

    private void filterCountries(String countryName) {
        filteredCountries = new ArrayList<>();
        for (int i = 0; i < responseModel.size(); i++) {
            if (responseModel.get(i).getCountryName().toLowerCase().contains(countryName.toLowerCase())) {
                filteredCountries.add(responseModel.get(i));
            }
        }
        setUpAdapter(filteredCountries);
    }

    private void setUpCountryList(Elements document) {
        ArrayList<CountriesResponseModel> countriesResponseModels = new ArrayList<>();

        for (int i = 0; i < document.size(); i++) {
            CountriesResponseModel countriesResponseModel = new CountriesResponseModel();
            countriesResponseModel.setCountryFlag(document.get(i).select("a[class=cb-teams-flag-img]").select("img").attr("src"));
            countriesResponseModel.setCountryName(document.get(i).select("a[class=cb-teams-flag-img]").attr("title"));
            countriesResponseModel.setCountryEndpoint(document.get(i).select("a[class=cb-teams-flag-img]").attr("href"));
            countriesResponseModels.add(countriesResponseModel);
        }

        responseModel = countriesResponseModels;

        setUpAdapter(countriesResponseModels);
    }

    private void setUpAdapter(ArrayList<CountriesResponseModel> countriesResponseModels) {
        binding.rclCountryList.setLayoutManager(Global.getManagerWithOrientation(requireContext(), RecyclerView.VERTICAL));
        CountriesAdapter adapter = new CountriesAdapter(requireContext(), countriesResponseModels, new AppInterfaces.NewsAdapterClick() {
            @Override
            public void getClickedNewsID(Integer countryId) {
                startActivity(new Intent(binding.getRoot().getContext(), CountryPlayersActivity.class).putExtra("playerLink", countriesResponseModels.get(countryId).getCountryEndpoint()));
            }
        });
        binding.rclCountryList.setAdapter(adapter);
    }
}