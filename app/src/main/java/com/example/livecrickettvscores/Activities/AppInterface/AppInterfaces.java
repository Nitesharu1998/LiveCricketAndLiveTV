package com.example.livecrickettvscores.Activities.AppInterface;

import android.media.Image;

import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FixturesResponseModel;

public class AppInterfaces {
    public interface LiveMatchInterface {
        void getLiveMatchesResponseModel(FixturesResponseModel fixturesResponseModel);
    }

    public interface FixturesInterface {
        void getAllMatchesData(FixturesResponseModel fixturesResponseModel);
    }
    public interface ImageInterface{
        void getImage (Image image);
    }
}
