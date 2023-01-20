package com.example.livecrickettvscores.Activities.APIControllers;

import android.content.Context;
import android.widget.Toast;

import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.GetAPIInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.LiveMatchesResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.RetroFit_APIClient;
import com.example.livecrickettvscores.Activities.Utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchesAPIController {
    public void calLiveMatchesAPI(Context context, AppInterfaces.LiveMatchInterface liveMatchInterface) {
        GetAPIInterfaces apiInterfaces = RetroFit_APIClient.getInstance().getClient(context, "https://api.cricapi.com/v1/").create(GetAPIInterfaces.class);
        Call<LiveMatchesResponseModel> call = apiInterfaces.getLiveMatchesData(Constants.CRICK_APIKEY, "0");
        call.enqueue(new Callback<LiveMatchesResponseModel>() {
            @Override
            public void onResponse(Call<LiveMatchesResponseModel> call, Response<LiveMatchesResponseModel> response) {
                if (response.isSuccessful() && response.body() != null)
                    liveMatchInterface.getLiveMatchesResponseModel(response.body());
            }

            @Override
            public void onFailure(Call<LiveMatchesResponseModel> call, Throwable t) {

            }
        });
    }
}
