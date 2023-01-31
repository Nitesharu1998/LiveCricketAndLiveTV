package com.example.livecrickettvscores.Activities.Retrofit;

import android.media.Image;

import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FixturesResponseModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetAPIInterfaces {

    /*@GET("currentMatches")
    Call<> getLiveMatchesData(@Query("apikey") String cricAPIkey, @Query("offset") String offeset);*/

    @GET("matches/list")
    Call<FixturesResponseModel> getFixturesAsPerType(@Query("matchState") String matchState);
    @GET("/get-image")
    Call<ResponseBody> getImageOfTeam(@Query("id") String idOfTeam);

}
