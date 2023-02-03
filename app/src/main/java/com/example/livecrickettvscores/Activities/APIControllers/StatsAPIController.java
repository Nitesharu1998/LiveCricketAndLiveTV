package com.example.livecrickettvscores.Activities.APIControllers;

import android.content.Context;
import android.widget.Toast;

import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.GetAPIInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.PlayerCareerDetailsResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.PlayerDetailsResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.TrendingPlayersResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.RetroFit_APIClient;
import com.example.livecrickettvscores.Activities.Utils.ConstantsMessages;
import com.example.livecrickettvscores.Activities.Utils.EncryptionUtils;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatsAPIController {
    Context context;
    Global global;

    public StatsAPIController(Context context) {
        this.context = context;
        this.global = new Global(context);
    }

    public void callTrendingPlayerAPI(AppInterfaces.APIResponseInterface apiResponseInterface) {
        global.showProgressDialog(context, ConstantsMessages.PLEASE_WAIT);
        GetAPIInterfaces apiInterfaces = RetroFit_APIClient.getInstance().getClient(context, EncryptionUtils.Dcrp_Hex(context.getString(R.string.CRICKBUZZ_API_BASE)), EncryptionUtils.Dcrp_Hex(context.getString(R.string.CRICKBUZZ_API_KEY)), EncryptionUtils.Dcrp_Hex(context.getString(R.string.CRICKBUZZ_API_HOST))).create(GetAPIInterfaces.class);
        Call<TrendingPlayersResponseModel> call = apiInterfaces.getTrendingPlayers();
        call.enqueue(new Callback<TrendingPlayersResponseModel>() {
            @Override
            public void onResponse(Call<TrendingPlayersResponseModel> call, Response<TrendingPlayersResponseModel> response) {
                global.hideProgressDialog();
                apiResponseInterface.theApiResponse(response.body());
            }

            @Override
            public void onFailure(Call<TrendingPlayersResponseModel> call, Throwable t) {
                Global.sout("Trending players api failure", t.getMessage());
                global.hideProgressDialog();
            }
        });
    }

    public void getSelectedPlayerInfo(Integer playerID, AppInterfaces.PlayerDetailsInterface playerDetailsInterface) {
        global.showProgressDialog(context, ConstantsMessages.PLEASE_WAIT);
        GetAPIInterfaces apiInterfaces = RetroFit_APIClient.getInstance().getClient(context, EncryptionUtils.Dcrp_Hex(context.getString(R.string.CRICKBUZZ_API_BASE)), EncryptionUtils.Dcrp_Hex(context.getString(R.string.CRICKBUZZ_API_KEY)), EncryptionUtils.Dcrp_Hex(context.getString(R.string.CRICKBUZZ_API_HOST))).create(GetAPIInterfaces.class);
        Call<PlayerDetailsResponseModel> call = apiInterfaces.getPlayerInfo(playerID);
        call.enqueue(new Callback<PlayerDetailsResponseModel>() {
            @Override
            public void onResponse(Call<PlayerDetailsResponseModel> call, Response<PlayerDetailsResponseModel> response) {
                global.hideProgressDialog();
                if (response.isSuccessful() && response.body()!=null) {
                    playerDetailsInterface.getPlayerDetails(response.body());
                }
            }

            @Override
            public void onFailure(Call<PlayerDetailsResponseModel> call, Throwable t) {
                Global.sout("Trending players api failure", t.getMessage());
                Toast.makeText(context, ConstantsMessages.SomethingWentWrong, Toast.LENGTH_SHORT).show();
                global.hideProgressDialog();
            }
        });

    }

    public void callPlayerCareerDetailsAPI(Integer playerID, AppInterfaces.PlayerCareerInformation playerCareerInformation) {
        global.showProgressDialog(context, ConstantsMessages.PLEASE_WAIT);
        GetAPIInterfaces apiInterfaces = RetroFit_APIClient.getInstance().getClient(context, EncryptionUtils.Dcrp_Hex(context.getString(R.string.CRICKBUZZ_API_BASE)), EncryptionUtils.Dcrp_Hex(context.getString(R.string.CRICKBUZZ_API_KEY)), EncryptionUtils.Dcrp_Hex(context.getString(R.string.CRICKBUZZ_API_HOST))).create(GetAPIInterfaces.class);
        Call<PlayerCareerDetailsResponseModel> call = apiInterfaces.getPlayerCareer(playerID);
        call.enqueue(new Callback<PlayerCareerDetailsResponseModel>() {
            @Override
            public void onResponse(Call<PlayerCareerDetailsResponseModel> call, Response<PlayerCareerDetailsResponseModel> response) {
                global.hideProgressDialog();
                if (response.isSuccessful() && response.body() != null) {
                    playerCareerInformation.getPlayerCareerInfo(response.body());
                }
            }

            @Override
            public void onFailure(Call<PlayerCareerDetailsResponseModel> call, Throwable t) {
                Global.sout("Trending players api failure", t.getMessage());
                Toast.makeText(context, ConstantsMessages.SomethingWentWrong, Toast.LENGTH_SHORT).show();
                global.hideProgressDialog();
            }
        });

    }
}
