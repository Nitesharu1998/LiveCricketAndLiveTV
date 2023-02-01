package com.example.livecrickettvscores.Activities.Retrofit;

import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FixturesResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.NewsDetailsResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.NewsListResponseModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetAPIInterfaces {
    @GET("news/list")
    Call<NewsListResponseModel> getNewsList(@Query("categoryId") int categoryId);

    @GET("news/detail")
    Call<NewsDetailsResponseModel> getNewsDetails(@Query("storyId") int storyId);

    @GET("matches/list")
    Call<FixturesResponseModel> getFixturesAsPerType(@Query("matchState") String matchState);

    @GET("/get-image")
    Call<ResponseBody> getImageOfTeam(@Query("id") String idOfTeam);

}
