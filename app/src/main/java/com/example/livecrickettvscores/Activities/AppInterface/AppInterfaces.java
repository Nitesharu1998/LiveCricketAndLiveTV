package com.example.livecrickettvscores.Activities.AppInterface;

import android.media.Image;

import com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdsJsonPOJO;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.CricketFlagsResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FixturesResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.NewsDetailsResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.NewsListResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.PlayerCareerDetailsResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.PlayerDetailsResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.TrendingPlayersResponseModel;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

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

    public interface APIResponseInterface {
        void theApiResponse(TrendingPlayersResponseModel trendingPlayersResponseModel);
    }

    public interface PlayerDetailsInterface {
        void getPlayerDetails(PlayerDetailsResponseModel playerDetailsResponseModel);
    }

    public interface PlayerCareerInformation {
        void getPlayerCareerInfo(PlayerCareerDetailsResponseModel playerCareerDetailsResponseModel);
    }

    public interface WebScrappingInterface {
        void getScrapedDocument(Elements document);
    }
    public interface NewsWebScrappingInterface{
        void getScrapedDocument(Elements document, int state);
    }

    public interface AdDataInterface {
        void getAdData(AdsJsonPOJO adsJsonPOJO);
    }

    public interface InterStitialADInterface {
        void adLoadState(boolean isLoaded);
    }

    public interface AppOpenADInterface {
        void appOpenAdState(boolean state_load);
    }
    public interface FlagsInterface {
        void setFlagsData(CricketFlagsResponseModel cricketFlagsResponseModel);
    }


}
