package com.example.livecrickettvscores.Activities.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.livecrickettvscores.Activities.APIControllers.StatsAPIController;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.PlayerDetailsResponseModel;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.databinding.FragmentInfoBinding;

public class InfoFragment extends Fragment {
    FragmentInfoBinding binding;
    Integer playerID;

    public InfoFragment(Integer playerID) {
        this.playerID = playerID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentInfoBinding.inflate(LayoutInflater.from(getContext()), container, false);
        callPlayerInfoAPI();
        return binding.getRoot();
    }

    private void callPlayerInfoAPI() {
        StatsAPIController controller = new StatsAPIController(getContext());
        controller.getSelectedPlayerInfo(playerID, new AppInterfaces.PlayerDetailsInterface() {
            @Override
            public void getPlayerDetails(PlayerDetailsResponseModel playerDetailsResponseModel) {
                if (!Global.isClassNull(playerDetailsResponseModel)) {
                    updateUI(playerDetailsResponseModel);
                }
            }
        });
    }

    private void updateUI(PlayerDetailsResponseModel playerDetailsResponseModel) {

        Glide.with(getContext()).load(playerDetailsResponseModel.getImage()).into(binding.civPlayerimage);

        binding.tvPlayername.setText(playerDetailsResponseModel.getName());
        binding.tvPlayercountry.setText(playerDetailsResponseModel.getIntlTeam().get(0));
        binding.tvPlayerdob.setText(playerDetailsResponseModel.getDoB());
        binding.tvBirthplace.setText(playerDetailsResponseModel.getBirthPlace());
        binding.tvNickname.setText(playerDetailsResponseModel.getNickName());
        binding.tvRole.setText(playerDetailsResponseModel.getRole());
        binding.tvBattingstyle.setText(playerDetailsResponseModel.getBat());

        binding.tvTeamname.setText(playerDetailsResponseModel.getTeams().get(0));
        binding.tvProfile.setText(playerDetailsResponseModel.getBio());

        //TODO icc ranking views
        binding.tvTestbat.setText(playerDetailsResponseModel.getCurrRank().getBat().getTestRank());
        binding.tvOdibat.setText(playerDetailsResponseModel.getCurrRank().getBat().getOdiRank());
        binding.tvT20bat.setText(playerDetailsResponseModel.getCurrRank().getBat().getT20Rank());

        binding.tvTestbowl.setText(playerDetailsResponseModel.getCurrRank().getBowl().getTestRank());
        binding.tvOdibowl.setText(playerDetailsResponseModel.getCurrRank().getBowl().getOdiRank());
        binding.tvT20bowl.setText(playerDetailsResponseModel.getCurrRank().getBowl().getT20Rank());


        if (playerDetailsResponseModel.getRole().contains("Bowl")) {
            binding.tvOdiallround.setText(playerDetailsResponseModel.getCurrRank().getBowl().getOdiBestRank());
            binding.tvTestallround.setText(playerDetailsResponseModel.getCurrRank().getBowl().getTestBestRank());
            binding.tvT20allround.setText(playerDetailsResponseModel.getCurrRank().getBowl().getT20BestRank());

        } else if (playerDetailsResponseModel.getRole().contains("bat")) {
            binding.tvOdiallround.setText(playerDetailsResponseModel.getCurrRank().getBat().getOdiBestRank());
            binding.tvTestallround.setText(playerDetailsResponseModel.getCurrRank().getBat().getTestBestRank());
            binding.tvT20allround.setText(playerDetailsResponseModel.getCurrRank().getBat().getT20BestRank());
        }
    }
}