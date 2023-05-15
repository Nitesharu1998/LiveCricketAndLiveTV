package com.example.livecrickettvscores.Activities.Fragments;

import static com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdUtils.loadInitialInterstitialAds;
import static com.example.livecrickettvscores.Activities.Fragments.StatsFragment.tabPosition;

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
import com.google.android.material.tabs.TabLayout;

import org.jsoup.select.Elements;

import java.util.ArrayList;

public class RankingPlayersFragment extends Fragment {
    FragmentRankingPlayersBinding binding;
    ArrayList<CountriesResponseModel> responseModel = new ArrayList<>();
    ArrayList<CountriesResponseModel> filteredCountries;
    private boolean isMen=false;
    @Override
    public void onResume() {
        super.onResume();
        loadInitialInterstitialAds(requireActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ranking_players, container, false);
        setUptabs();
        callCountryList(Constants.CricketTeamURL);

        binding.tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        isMen = true;
                        tabPosition = tab.getPosition();
                        callCountryList(Constants.CricketTeamURL);
                        break;
                    case 1:
                        isMen=false;
                        tabPosition = tab.getPosition();
                        callCountryList(Constants.CricketTeamWomenURL);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        binding.edtCountryName.addTextChangedListener(new TextWatcher() {
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

    private void callCountryList(String cricketTeamURL) {
        AppAsyncTasks.CallRankingContries callRankingCountries = new AppAsyncTasks.CallRankingContries(cricketTeamURL, binding.getRoot().getContext(), new AppInterfaces.WebScrappingInterface() {
            @Override
            public void getScrapedDocument(Elements document) {
                setUpCountryList(document);
            }
        }); callRankingCountries.execute();
    }

    private void setUptabs() {
        binding.tablayout.addTab(binding.tablayout.newTab().setText("Men's"));
        binding.tablayout.addTab(binding.tablayout.newTab().setText("Women's"));
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
        CountriesAdapter adapter = new CountriesAdapter(isMen,false, requireContext(), countriesResponseModels, new AppInterfaces.NewsAdapterClick() {
            @Override
            public void getClickedNewsID(Integer countryId) {
                startActivity(new Intent(binding.getRoot().getContext(), CountryPlayersActivity.class).putExtra("playerLink", countriesResponseModels.get(countryId).getCountryEndpoint()).putExtra("playerCountry", countriesResponseModels.get(countryId).getCountryName()));
            }
        });
        binding.rclCountryList.setAdapter(adapter);
    }
}