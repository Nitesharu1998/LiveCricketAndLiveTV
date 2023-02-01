package com.example.livecrickettvscores.Activities.AppInterface;

import android.media.Image;

import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FixturesResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.NewsDetailsResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.NewsListResponseModel;

public class AppInterfaces {
    public interface LiveMatchInterface {
        void getLiveMatchesResponseModel(FixturesResponseModel fixturesResponseModel);
    }

    public interface FixturesInterface {
        void getAllMatchesData(FixturesResponseModel fixturesResponseModel);
    }

    public interface NewsInterface {
        void getNewsList(NewsListResponseModel newsListResponseModel);
    }

    public interface NewsDetailInterface {
        void getNewsDetail(NewsDetailsResponseModel newsDetailsResponseModel);
    }

    public interface NewsAdapterClick {
        void getClickedNewsID(Integer newsID);
    }

    public interface ImageInterface {
        void getImage(Image image);
    }

}
