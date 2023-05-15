package com.example.livecrickettvscores.Activities.Fragments;

import static com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdUtils.loadInitialInterstitialAds;

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
import com.example.livecrickettvscores.databinding.FragmentInfoBinding;

import org.jsoup.select.Elements;

public class InfoFragment extends Fragment {
    FragmentInfoBinding binding;
    String playerURL, playerImage, playerName, playerCountry;
    Context context;

    public InfoFragment(String playerURL, String playerImage, String playerName, String playerCountry) {
        this.playerURL = playerURL;
        this.playerImage = playerImage;
        this.playerName = playerName;
        this.playerCountry = playerCountry;
    }
    @Override
    public void onResume() {
        super.onResume();
        loadInitialInterstitialAds(requireActivity());
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
        Elements playerDetailsHeading = document.get(0).getElementsByClass("cb-col cb-col-40 text-bold cb-lst-itm-sm");
        Global.sout("playerInfo>>>>>>>>\n", playerInfo);
        Global.sout("playerOverall>>>>>\n", playerOverall);
        Global.sout("playerDetailsHeading>>>>>\n", playerDetailsHeading);

        if (!playerOverall.isEmpty()) {
            binding.tvTestbat.setText(playerOverall.get(0).ownText());
            binding.tvOdibat.setText(playerOverall.get(1).ownText());
            binding.tvT20bat.setText(playerOverall.get(2).ownText());
            binding.tvTestbowl.setText(playerOverall.get(3).ownText());
            binding.tvOdibowl.setText(playerOverall.get(4).ownText());
            binding.tvT20bowl.setText(playerOverall.get(5).ownText());
            Glide.with(context).load(Constants.CricBuzzBaseURL + document.select("div.cb-col.cb-col-20.cb-col-rt").select("img").attr("src")).into(binding.civPlayerimage);
        }

        binding.tvPlayername.setText(playerName);
        binding.tvPlayercountry.setText(playerCountry);

        if (!playerInfo.isEmpty() && !playerDetailsHeading.isEmpty()) {
            for (int i = 0; i < playerDetailsHeading.size(); i++) {
                if (playerDetailsHeading.get(i).ownText().equals("Born"))
                    binding.tvPlayerdob.setText(playerInfo.get(i).ownText());
                if (playerDetailsHeading.get(i).ownText().equals("Birth Place"))
                    binding.tvBirthplace.setText(playerInfo.get(i).ownText());
                if (playerDetailsHeading.get(i).ownText().equals("Role"))
                    binding.tvRole.setText(playerInfo.get(i).ownText());
                if (playerDetailsHeading.get(i).ownText().equals("Batting Style"))
                    binding.tvBattingstyle.setText(playerInfo.get(i).ownText());
                if (playerDetailsHeading.get(i).ownText().equals("Teams"))
                    binding.tvTeamname.setText(playerInfo.get(i).ownText());
            }
           
           /* binding.tvPlayerdob.setText(playerInfo.get(0).ownText());
            binding.tvBirthplace.setText(playerInfo.get(1).ownText());
            binding.tvRole.setText(playerInfo.get(2).ownText());
            binding.tvBattingstyle.setText(playerInfo.get(3).ownText());

            if (tabPosition == 0 && !binding.tvRole.getText().toString().contains("bowl")) {
                binding.tvTeamname.setText(StringUtils.isNull(playerInfo.get(5).ownText()) ? "--" : playerInfo.get(5).ownText());
            } else {
                binding.tvTeamname.setText(StringUtils.isNull(playerInfo.get(4).ownText()) ? "--" : playerInfo.get(4).ownText());
            }*/
        }


    }
}