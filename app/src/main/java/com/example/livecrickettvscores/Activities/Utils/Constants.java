package com.example.livecrickettvscores.Activities.Utils;


import com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdsJsonPOJO;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.CricketFlagsResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FixturesResponseModel;

public class Constants {

    public static final String LIVEMATHCES = "https://www.espncricinfo.com/live-cricket-score";
    public static final String UPCOMINGMATCHES = "https://www.espncricinfo.com/live-cricket-match-schedule-fixtures";
    public static final String RECENTMATCHES = "https://www.espncricinfo.com/live-cricket-match-results";
    public static final String PREDICTIONS_URL = "https://www.onlinecricketbetting.net/cricket-betting-tips/?utm_source=yt&utm_medium=vd&utm_campaign=youthiya";
    public static final String IS_FIRST_RUN = "ISFIRSTRUN";
    public static final String USERNAME = "USERNAME";
    public static final String USER_AVATAR = "USER_AVATAR";

    public static final String MenBattingRanking = "https://www.cricbuzz.com/cricket-stats/icc-rankings/men/batting";
    public static final String MenBowlingRanking = "https://www.cricbuzz.com/cricket-stats/icc-rankings/men/bowling";
    public static final String MenAllRounderRanking = "https://www.cricbuzz.com/cricket-stats/icc-rankings/men/all-rounder";

    public static final String WomenBattingRanking = "https://www.cricbuzz.com/cricket-stats/icc-rankings/women/batting";
    public static final String WomenBowlingRanking = "https://www.cricbuzz.com/cricket-stats/icc-rankings/women/bowling";
    public static final String WomenAllRounderRanking = "https://www.cricbuzz.com/cricket-stats/icc-rankings/women/all-rounder";

    public static AdsJsonPOJO adsJsonPOJO;
    public static int hitCounter = 0;
    public static final String ADSJSON = "LIVECRICKET_ADS_JSON";


    public static String CricketNewsURL = "https://www.cricbuzz.com/cricket-news";
    public static String CricketSpotlightNewsURL = "https://www.cricbuzz.com/cricket-news/editorial/spotlight";
    public static String CricBuzzBaseURL = "https://www.cricbuzz.com";
    public static String ESPNBaseURL = "https://www.espncricinfo.com";
    public static CricketFlagsResponseModel cricketFlagsModel;
    public static String FlagsModel = "FlagsModel";
    public static FixturesResponseModel.MatchesDTO matchDTO;
    public static String CricketTeamURL = "https://www.cricbuzz.com/cricket-team";
    public static String CricketTeamWomenURL = "https://www.cricbuzz.com/cricket-team/women";
}
