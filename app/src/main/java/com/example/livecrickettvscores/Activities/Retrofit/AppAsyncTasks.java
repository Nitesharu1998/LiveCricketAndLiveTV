package com.example.livecrickettvscores.Activities.Retrofit;

import android.app.Activity;
import android.os.AsyncTask;

import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.Utils.ConstantsMessages;
import com.example.livecrickettvscores.Activities.Utils.Global;

import org.jsoup.select.Elements;

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
            elements = Global.getPredictionDetails(predictionURL);
        }

        @Override
        protected void onPreExecute() {
            global.showProgressDialog(activity, ConstantsMessages.PLEASE_WAIT);
        }
    }
}
