package com.example.livecrickettvscores.Activities.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecrickettvscores.Activities.APIControllers.StatsAPIController;
import com.example.livecrickettvscores.Activities.Adapters.CareerDetailsAdapter;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.PlayerCareerDetailsResponseModel;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.databinding.FragmentCareerBinding;

public class CareerFragment extends Fragment {

    FragmentCareerBinding binding;
    Context context;
    Integer playerID;

    public CareerFragment(Integer playerID) {
        this.playerID = playerID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCareerBinding.inflate(LayoutInflater.from(getContext()), container, false);
        context = binding.getRoot().getContext();
        callCareerDetailsAPI();

        return binding.getRoot();
    }

    private void callCareerDetailsAPI() {
        StatsAPIController controller = new StatsAPIController(context);
        controller.callPlayerCareerDetailsAPI(playerID, new AppInterfaces.PlayerCareerInformation() {
            @Override
            public void getPlayerCareerInfo(PlayerCareerDetailsResponseModel playerCareerDetailsResponseModel) {
                if (Global.isArrayListNull(playerCareerDetailsResponseModel.getValues()))
                    setUpPlayerCareerList(playerCareerDetailsResponseModel);
            }

        });
    }

    private void setUpPlayerCareerList(PlayerCareerDetailsResponseModel playerCareerDetailsResponseModel) {
        binding.rclCareerinfo.setLayoutManager(Global.getManagerWithOrientation(context, RecyclerView.VERTICAL));
        CareerDetailsAdapter adapter = new CareerDetailsAdapter(context, playerCareerDetailsResponseModel.getValues());
        binding.rclCareerinfo.setAdapter(adapter);
    }
}