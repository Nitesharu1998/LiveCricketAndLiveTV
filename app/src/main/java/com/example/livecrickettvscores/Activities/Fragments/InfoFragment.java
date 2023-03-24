package com.example.livecrickettvscores.Activities.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdUtils;
import com.example.livecrickettvscores.Activities.Retrofit.AppAsyncTasks;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.R;
import com.example.livecrickettvscores.databinding.FragmentInfoBinding;

import org.jsoup.select.Elements;

public class InfoFragment extends Fragment {
    FragmentInfoBinding binding;
    String playerURL,playerImage,playerName;
    Context context;

    public InfoFragment(String playerURL, String playerImage, String playerName) {
        this.playerURL = playerURL;
        this.playerImage = playerImage;
        this.playerName = playerName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentInfoBinding.inflate(LayoutInflater.from(getContext()), container, false);
        context = binding.getRoot().getContext();

        AdUtils.showNativeAd(requireActivity(), Constants.adsJsonPOJO.getParameters().getNative_id().getDefaultValue().getValue(), binding.nativeAds,false);


        AppAsyncTasks.CallClickedPlayerDetails callClickedPlayerDetails = new AppAsyncTasks.CallClickedPlayerDetails(playerURL, requireActivity(), new AppInterfaces.WebScrappingInterface() {
            @Override
            public void getScrapedDocument(Elements document) {
                if (document != null) {
                    Elements playerInfo=document.select("div.cb-col.cb-col-33.text-black");
                    Elements playerCareer = document.select("div.cb-col.cb-col-100.cb-player-bio");

                    updateUI(document ,playerCareer,playerInfo);
                }
            }
        });
        callClickedPlayerDetails.execute();

        return binding.getRoot();
    }


    private void updateUI(Elements document, Elements playerCareer, Elements playerBasicInfo) {

        binding.tvProfile.setText(playerCareer.text());
        Elements playerInfo = document.get(0).getElementsByClass("cb-col cb-col-60 cb-lst-itm-sm");
        Elements playerOverall = document.get(0).getElementsByClass("cb-col cb-col-25 cb-plyr-rank text-right");
        binding.tvTestbat.setText(playerOverall.get(0).ownText());
        binding.tvOdibat.setText(playerOverall.get(1).ownText());
        binding.tvT20bat.setText(playerOverall.get(2).ownText());
        binding.tvTestbowl.setText(playerOverall.get(3).ownText());
        binding.tvOdibowl.setText(playerOverall.get(4).ownText());
        binding.tvT20bowl.setText(playerOverall.get(5).ownText());
        Global.sout("profleURL>>>>>>>>>>", "https://www.cricbuzz.com/"+document.select("div.cb-col.cb-col-20.cb-col-rt").select("img").attr("src"));
        Glide.with(context).load("https://www.cricbuzz.com/"+document.select("div.cb-col.cb-col-20.cb-col-rt").select("img").attr("src")).into(binding.civPlayerimage);


        binding.tvPlayername.setText(playerName);
        binding.tvPlayerdob.setText(playerInfo.get(0).ownText());
        binding.tvBirthplace.setText(playerInfo.get(1).ownText());
        binding.tvRole.setText(playerInfo.get(2).ownText());
        binding.tvBattingstyle.setText(playerInfo.get(3).ownText());
        binding.tvTeamname.setText(playerInfo.get(5).ownText());


    }
}