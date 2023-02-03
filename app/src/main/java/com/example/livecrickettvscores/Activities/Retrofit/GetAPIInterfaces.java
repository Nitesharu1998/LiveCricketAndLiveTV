package com.example.livecrickettvscores.Activities.Retrofit;

import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FixturesResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.NewsDetailsResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.NewsListResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.PlayerCareerDetailsResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.PlayerDetailsResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.TrendingPlayersResponseModel;

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

    @GET("players/list-trending")
    Call<TrendingPlayersResponseModel> getTrendingPlayers();

    @GET("players/get-info")
    Call<PlayerDetailsResponseModel> getPlayerInfo(@Query("playerId") Integer playerID);

    @GET("players/get-news")
    Call<NewsListResponseModel> getPlayerNews(@Query("playerId") Integer playerID);

    @GET("players/get-career")
    Call<PlayerCareerDetailsResponseModel> getPlayerCareer(@Query("playerId") Integer playerID);

}
