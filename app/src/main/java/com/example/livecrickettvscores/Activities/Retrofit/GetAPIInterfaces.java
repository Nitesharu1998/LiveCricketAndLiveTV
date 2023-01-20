package com.example.livecrickettvscores.Activities.Retrofit;

import static com.example.livecrickettvscores.Activities.Utils.Constants.CRICK_APIKEY;

import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.LiveMatchesResponseModel;
import com.example.livecrickettvscores.Activities.Utils.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetAPIInterfaces {

    @GET("currentMatches")
    Call<LiveMatchesResponseModel> getLiveMatchesData(@Query("apikey") String cricAPIkey, @Query("offset") String offeset);

}
