package com.example.livecrickettvscores.Activities.Retrofit;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
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

}
