package com.example.livecrickettvscores.Activities.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecrickettvscores.Activities.Adapters.CareerDetailsAdapter;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdUtils;
import com.example.livecrickettvscores.Activities.Retrofit.AppAsyncTasks;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.PlayerCareerDetailsResponseModel;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.databinding.FragmentCareerBinding;


import org.jsoup.select.Elements;

import java.util.ArrayList;

public class CareerFragment extends Fragment {
    String[] matchNameArray = {"Test debut","Last Test","ODI debut","Last ODI","T20 debut","Last T20"};
    FragmentCareerBinding binding;
    Context context;
    String playerURL;

    public CareerFragment(String playerURL) {
        this.playerURL = playerURL;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCareerBinding.inflate(LayoutInflater.from(getContext()), container, false);
        context = binding.getRoot().getContext();
        AdUtils.showNativeAd(requireActivity(), Constants.adsJsonPOJO.getParameters().getNative_id().getDefaultValue().getValue(),binding.nativeAds,false);


        AppAsyncTasks.CallClickedPlayerDetails callClickedPlayerDetails = new AppAsyncTasks.CallClickedPlayerDetails(playerURL, requireContext(), new AppInterfaces.WebScrappingInterface() {
            @Override
            public void getScrapedDocument(Elements document) {
                setUpPlayerCareerList(getPlayerDetailsModel(document.select("div.cb-col.cb-col-67.cb-bg-white.cb-plyr-rt-col").select("div.cb-col.cb-col-100")));
            }
        });
        callClickedPlayerDetails.execute();

        return binding.getRoot();
    }

    private PlayerCareerDetailsResponseModel getPlayerDetailsModel(Elements careerElements) {
        PlayerCareerDetailsResponseModel model = new PlayerCareerDetailsResponseModel();
        PlayerCareerDetailsResponseModel.ValuesDTO responseModel;
        ArrayList<PlayerCareerDetailsResponseModel.ValuesDTO> valuesDTOArrayList = new ArrayList<>();
        Elements debutNames = careerElements.get(0).getElementsByClass("cb-text-link");
        for (int i = 0; i < debutNames.size(); i++) {
            responseModel = new PlayerCareerDetailsResponseModel.ValuesDTO();
            responseModel.setLastPlayed(debutNames.get(i).text());
            responseModel.setDebut(i < 6 ? matchNameArray[i] : "");
            responseModel.setName(i < 6 ?matchNameArray[i].replace("debut","Match"):"Other");

            if (i%2==0){
                responseModel.setDebut(i < 6 ? matchNameArray[i] : "");
            }else{
                responseModel.setDebut("");
            }
            valuesDTOArrayList.add(responseModel);
            model.setValues(valuesDTOArrayList);
        }

        return model;
    }

    /*private void callCareerDetailsAPI() {
        StatsAPIController controller = new StatsAPIController(context);
        controller.callPlayerCareerDetailsAPI(playerID, new AppInterfaces.PlayerCareerInformation() {
            @Override
            public void getPlayerCareerInfo(PlayerCareerDetailsResponseModel playerCareerDetailsResponseModel) {
                if (!Global.isArrayListNull(playerCareerDetailsResponseModel.getValues()))
                    setUpPlayerCareerList(playerCareerDetailsResponseModel);
            }

        });
    }*/

    private void setUpPlayerCareerList(PlayerCareerDetailsResponseModel playerCareerDetailsResponseModel) {
        binding.rclCareerinfo.setLayoutManager(Global.getManagerWithOrientation(context, RecyclerView.VERTICAL));
        CareerDetailsAdapter adapter = new CareerDetailsAdapter(context, playerCareerDetailsResponseModel.getValues());
        binding.rclCareerinfo.setAdapter(adapter);
    }
}