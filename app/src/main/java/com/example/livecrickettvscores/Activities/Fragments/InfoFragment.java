package com.example.livecrickettvscores.Activities.Fragments;

import android.content.Context;
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
import com.example.livecrickettvscores.Activities.Utils.InputUtils;
import com.example.livecrickettvscores.databinding.FragmentInfoBinding;

public class InfoFragment extends Fragment {
    FragmentInfoBinding binding;
    Integer playerID;
    Context context;

    public InfoFragment(Integer playerID) {
        this.playerID = playerID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentInfoBinding.inflate(LayoutInflater.from(getContext()), container, false);
        context = binding.getRoot().getContext();
        callPlayerInfoAPI();
        return binding.getRoot();
    }

    private void callPlayerInfoAPI() {
        StatsAPIController controller = new StatsAPIController(context);
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

        Glide.with(context).load(playerDetailsResponseModel.getImage()).into(binding.civPlayerimage);
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
        binding.tvTestbat.setText(InputUtils.isNull(playerDetailsResponseModel.getCurrRank().getBat().getTestRank()) ? "-" : playerDetailsResponseModel.getCurrRank().getBat().getTestRank());
        binding.tvOdibat.setText(InputUtils.isNull(playerDetailsResponseModel.getCurrRank().getBat().getOdiRank()) ? "-" : playerDetailsResponseModel.getCurrRank().getBat().getOdiRank());
        binding.tvT20bat.setText(InputUtils.isNull(playerDetailsResponseModel.getCurrRank().getBat().getT20Rank()) ? "-" : playerDetailsResponseModel.getCurrRank().getBat().getT20Rank());

        binding.tvTestbowl.setText(InputUtils.isNull(playerDetailsResponseModel.getCurrRank().getBowl().getTestRank()) ? "-" : playerDetailsResponseModel.getCurrRank().getBowl().getTestRank());
        binding.tvOdibowl.setText(InputUtils.isNull(playerDetailsResponseModel.getCurrRank().getBowl().getOdiRank()) ? "-" : playerDetailsResponseModel.getCurrRank().getBowl().getOdiRank());
        binding.tvT20bowl.setText(InputUtils.isNull(playerDetailsResponseModel.getCurrRank().getBowl().getT20Rank()) ? "-" : playerDetailsResponseModel.getCurrRank().getBowl().getT20Rank());


        if (playerDetailsResponseModel.getRole().contains("Bowl")) {
            binding.tvOdiallround.setText(InputUtils.isNull(playerDetailsResponseModel.getCurrRank().getBowl().getOdiBestRank()) ? "-" : playerDetailsResponseModel.getCurrRank().getBowl().getOdiBestRank());
            binding.tvTestallround.setText(InputUtils.isNull(playerDetailsResponseModel.getCurrRank().getBowl().getTestBestRank()) ? "-" : playerDetailsResponseModel.getCurrRank().getBowl().getTestBestRank());
            binding.tvT20allround.setText(InputUtils.isNull(playerDetailsResponseModel.getCurrRank().getBowl().getT20BestRank()) ? "-" : playerDetailsResponseModel.getCurrRank().getBowl().getT20BestRank());

        } else if (playerDetailsResponseModel.getRole().contains("bat")) {
            binding.tvOdiallround.setText(InputUtils.isNull(playerDetailsResponseModel.getCurrRank().getBat().getOdiBestRank()) ? "-" : playerDetailsResponseModel.getCurrRank().getBat().getOdiBestRank());
            binding.tvTestallround.setText(InputUtils.isNull(playerDetailsResponseModel.getCurrRank().getBat().getTestBestRank()) ? "-" : playerDetailsResponseModel.getCurrRank().getBat().getTestBestRank());
            binding.tvT20allround.setText(InputUtils.isNull(playerDetailsResponseModel.getCurrRank().getBat().getT20BestRank()) ? "-" : playerDetailsResponseModel.getCurrRank().getBat().getT20BestRank());
        }
    }
}