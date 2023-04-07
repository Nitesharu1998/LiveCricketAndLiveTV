package com.example.livecrickettvscores.Activities.Retrofit;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.FirebaseADHandlers.FirebaseUtils;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.CricketFlagsResponseModel;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.example.livecrickettvscores.Activities.Utils.ConstantsMessages;
import com.example.livecrickettvscores.Activities.Utils.Global;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class AppAsyncTasks {

    public static class GetPredictionTask extends AsyncTask<String, Void, String> {
        Activity activity;
        AppInterfaces.WebScrappingInterface webScrappingInterface;
        Global global;
        Elements elements;

        public GetPredictionTask(Activity activity, AppInterfaces.WebScrappingInterface webScrappingInterface) {
            this.activity = activity;
            this.webScrappingInterface = webScrappingInterface;
            this.global = new Global(activity);
        }

        @Override
        protected void onPreExecute() {
            global.showProgressDialog(activity, ConstantsMessages.PLEASE_WAIT);
        }

        @Override
        protected void onPostExecute(String s) {
            global.hideProgressDialog();
            webScrappingInterface.getScrapedDocument(elements);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                elements = Global.getListOfPredictions();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return "";
        }
    }

    public static class GetPredictionDetailsTask extends AsyncTask<String, Void, String> {
        Activity activity;
        AppInterfaces.WebScrappingInterface webScrappingInterface;
        Global global;
        Elements elements;
        String predictionURL;

        public GetPredictionDetailsTask(String predictionURL, Activity activity, AppInterfaces.WebScrappingInterface webScrappingInterface) {
            this.activity = activity;
            this.webScrappingInterface = webScrappingInterface;
            this.global = new Global(activity);
            this.predictionURL = predictionURL;
        }

        @Override
        protected void onPostExecute(String s) {
            global.hideProgressDialog();
            webScrappingInterface.getScrapedDocument(elements);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                elements = Global.getPredictionDetails(predictionURL);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return "";
        }

        @Override
        protected void onPreExecute() {
            global.showProgressDialog(activity, ConstantsMessages.PLEASE_WAIT);
        }
    }

    public static class CallTrendingPlayers extends AsyncTask<String, Void, String> {
        Context context;
        AppInterfaces.WebScrappingInterface webScrappingInterface;
        Global global;
        Elements elements;
        String predictionURL;

        public CallTrendingPlayers(String predictionURL, Context context, AppInterfaces.WebScrappingInterface webScrappingInterface) {
            this.context = context;
            this.webScrappingInterface = webScrappingInterface;
            this.global = new Global(context);
            this.predictionURL = predictionURL;
        }

        @Override
        protected void onPostExecute(String s) {
            global.hideProgressDialog();
            webScrappingInterface.getScrapedDocument(elements);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                Document document = Jsoup.connect(predictionURL).get();
                elements=document.select("div.cb-col.cb-col-100.cb-font-14.cb-lst-itm.text-center");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return "";
        }

        @Override
        protected void onPreExecute() {
            global.showProgressDialog(context, ConstantsMessages.PLEASE_WAIT);
        }
    }

    public static class CallClickedPlayerDetails extends AsyncTask<String, Void, String> {
        Context context;
        AppInterfaces.WebScrappingInterface webScrappingInterface;
        Global global;
        Elements elements;
        String playerDetailsURL;

        public CallClickedPlayerDetails(String playerDetailsURL, Context context, AppInterfaces.WebScrappingInterface webScrappingInterface) {
            this.context = context;
            this.webScrappingInterface = webScrappingInterface;
            this.global = new Global(context);
            this.playerDetailsURL = playerDetailsURL;
        }

        @Override
        protected void onPostExecute(String s) {
            global.hideProgressDialog();
            webScrappingInterface.getScrapedDocument(elements);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                Document document = Jsoup.connect(playerDetailsURL).get();
                elements = document.getElementById("page-wrapper").getAllElements();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return "";
        }

        @Override
        protected void onPreExecute() {
            global.showProgressDialog(context, ConstantsMessages.PLEASE_WAIT);
        }
    }

    public static class CallNewsDetails extends AsyncTask<String, Void, String> {
        Context context;
        AppInterfaces.WebScrappingInterface webScrappingInterface;
        Global global;
        Elements elements;
        String playerDetailsURL;

        public CallNewsDetails(String playerDetailsURL, Context context, AppInterfaces.WebScrappingInterface webScrappingInterface) {
            this.context = context;
            this.webScrappingInterface = webScrappingInterface;
            this.global = new Global(context);
            this.playerDetailsURL = playerDetailsURL;
        }

        @Override
        protected void onPostExecute(String s) {
            global.hideProgressDialog();
            webScrappingInterface.getScrapedDocument(elements);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Document document = Jsoup.connect(playerDetailsURL).get();
                elements = document.getElementById("page-wrapper").getAllElements();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return "";
        }

        @Override
        protected void onPreExecute() {
            global.showProgressDialog(context, ConstantsMessages.PLEASE_WAIT);
        }
    }

    public static class CallFixturesDetails extends AsyncTask<String, Void, String> {
        Context context;
        AppInterfaces.WebScrappingInterface webScrappingInterface;
        Global global;
        Elements elements;
        String UpcomingMatchesURL;
        Document document;

        public CallFixturesDetails(String UpcomingMatchesURL, Context context, AppInterfaces.WebScrappingInterface webScrappingInterface) {
            this.context = context;
            this.webScrappingInterface = webScrappingInterface;
            this.global = new Global(context);
            this.UpcomingMatchesURL = UpcomingMatchesURL;
        }

        @Override
        protected void onPostExecute(String s) {
            global.hideProgressDialog();
            webScrappingInterface.getScrapedDocument(elements);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Document document = Jsoup.connect(UpcomingMatchesURL).get();
                elements = document.select("div[class=ds-mb-4]");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return "";
        }

        @Override
        protected void onPreExecute() {
            global.showProgressDialog(context, ConstantsMessages.PLEASE_WAIT);
        }

    }

    public static class CallUpComingDetails extends AsyncTask<String, Void, String> {
        Context context;
        AppInterfaces.WebScrappingInterface webScrappingInterface;
        Global global;
        Elements elements;
        String UpcomingMatchesURL;
        Document document;

        public CallUpComingDetails(String UpcomingMatchesURL, Context context, AppInterfaces.WebScrappingInterface webScrappingInterface) {
            this.context = context;
            this.webScrappingInterface = webScrappingInterface;
            this.global = new Global(context);
            this.UpcomingMatchesURL = UpcomingMatchesURL;
        }

        @Override
        protected void onPostExecute(String s) {
            global.hideProgressDialog();
            webScrappingInterface.getScrapedDocument(elements);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Document document = Jsoup.connect(UpcomingMatchesURL).get();
                elements = document.select("div[class=debug-fixture-date-item]");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return "";
        }

        @Override
        protected void onPreExecute() {
            global.showProgressDialog(context, ConstantsMessages.PLEASE_WAIT);
        }

    }

    public static class GetFeaturedNews extends AsyncTask<String, Void, String> {
        Context context;
        AppInterfaces.WebScrappingInterface webScrappingInterface;
        Global global;
        Elements elements;
        String NewsURL;
        Document document;

        public GetFeaturedNews(String NewsURL, Context context, AppInterfaces.WebScrappingInterface webScrappingInterface) {
            this.context = context;
            this.webScrappingInterface = webScrappingInterface;
            this.global = new Global(context);
            this.NewsURL = NewsURL;
        }

        @Override
        protected void onPostExecute(String s) {
            global.hideProgressDialog();
            webScrappingInterface.getScrapedDocument(elements);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Document document = Jsoup.connect(NewsURL).get();
                elements = document.select("div[class=cb-col cb-col-100 cb-lst-itm cb-pos-rel cb-lst-itm-lg]");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return "";
        }

        @Override
        protected void onPreExecute() {
            global.showProgressDialog(context, ConstantsMessages.PLEASE_WAIT);
        }

    }

    public static class GetNewsDetails extends AsyncTask<String, Void, String> {
        Context context;
        AppInterfaces.NewsWebScrappingInterface webScrappingInterface;
        Global global;
        Elements elements;
        String NewsURL;
        Document document;
        int state = 0;

        public GetNewsDetails(String NewsURL, Context context, AppInterfaces.NewsWebScrappingInterface webScrappingInterface) {
            this.context = context;
            this.webScrappingInterface = webScrappingInterface;
            this.global = new Global(context);
            this.NewsURL = NewsURL;
        }

        @Override
        protected void onPostExecute(String s) {
            global.hideProgressDialog();
            webScrappingInterface.getScrapedDocument(elements, state);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Document document = Jsoup.connect(NewsURL).get();
                elements = document.select("div[class=cb-col cb-col-67 cb-nws-dtl-lft-col]");
                if (elements.isEmpty()){
                    state =1;
                    elements=document.select("div[class=cb-col cb-col-100 cb-bg-white]");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return "";
        }

        @Override
        protected void onPreExecute() {
            global.showProgressDialog(context, ConstantsMessages.PLEASE_WAIT);
        }

    }


    public static class GetLiveScoreBoard extends AsyncTask<String, Void, String> {
        Context context;
        AppInterfaces.WebScrappingInterface webScrappingInterface;
        Global global;
        Elements elements;
        String NewsURL;
        Elements ElementsTest = new Elements();
        boolean showLoader;

        public GetLiveScoreBoard(boolean showLoader, String NewsURL, Context context, AppInterfaces.WebScrappingInterface webScrappingInterface) {
            this.context = context;
            this.webScrappingInterface = webScrappingInterface;
            this.global = new Global(context);
            this.NewsURL = NewsURL;
            this.showLoader = showLoader;
        }

        @Override
        protected void onPostExecute(String s) {
            if (showLoader) {
                global.hideProgressDialog();
            }
            webScrappingInterface.getScrapedDocument(ElementsTest);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Document document = Jsoup.connect(NewsURL).get();
                elements = document.select("div.ds-grow").select("div.ds-w-full.ds-bg-fill-content-prime.ds-overflow-hidden.ds-rounded-xl.ds-border.ds-border-line");
                ElementsTest.addAll(elements);
                elements = document.select("div.ds-grow").select("div[class=ds-mt-3]");
                ElementsTest.addAll(elements);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return "";
        }

        @Override
        protected void onPreExecute() {
            if (showLoader)
                global.showProgressDialog(context, ConstantsMessages.PLEASE_WAIT);
        }

    }

    public static class GetFinishedScoreBoard extends AsyncTask<String, Void, String> {
        Context context;
        AppInterfaces.WebScrappingInterface webScrappingInterface;
        Global global;
        Elements elements;
        String NewsURL;
        Elements ElementsTest = new Elements();
        boolean showLoader;

        public GetFinishedScoreBoard(boolean showLoader, String NewsURL, Context context, AppInterfaces.WebScrappingInterface webScrappingInterface) {
            this.context = context;
            this.webScrappingInterface = webScrappingInterface;
            this.global = new Global(context);
            this.NewsURL = NewsURL;
            this.showLoader = showLoader;
        }

        @Override
        protected void onPostExecute(String s) {
            if (showLoader) {
                global.hideProgressDialog();
            }
            webScrappingInterface.getScrapedDocument(ElementsTest);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Document document = Jsoup.connect(NewsURL).get();
                ElementsTest.addAll(document.select("div[class=ds-rounded-lg ds-mt-2]"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return "";
        }

        @Override
        protected void onPreExecute() {
            if (showLoader)
                global.showProgressDialog(context, ConstantsMessages.PLEASE_WAIT);
        }

    }


    public static class GetFlags extends AsyncTask<Void, Void, Void> {
        Context context;

        public GetFlags(Context context) {
            this.context = context;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            FirebaseUtils.getFlagsFromFirebase(context, new AppInterfaces.FlagsInterface() {
                @Override
                public void setFlagsData(CricketFlagsResponseModel cricketFlagsResponseModel) {
                    Constants.cricketFlagsModel = cricketFlagsResponseModel;
                }
            });
            return null;
        }
    }

}
