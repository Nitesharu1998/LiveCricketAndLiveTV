package com.example.livecrickettvscores.Activities.videoplayer.api;

import com.example.livecrickettvscores.Activities.videoplayer.data.model.youtube.RequestResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IYoutubeApi {
    @GET("search")
    Call<RequestResponse> searchQuery(@Query("key") String key,
                                      @Query("maxResults") int maxResults,
                                      @Query("order") String date,
                                      @Query("part") String snippet,
                                      @Query("q") String query,
                                      @Query("type") String video);
}
