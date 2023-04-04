package com.example.livecrickettvscores.Activities.Retrofit.ResponseModel;

import java.util.ArrayList;

public class LatestNewsModel {
    public ArrayList<NewsListDTO> getNewsListDTO() {
        return newsListDTO;
    }

    public void setNewsListDTO(ArrayList<NewsListDTO> newsListDTO) {
        this.newsListDTO = newsListDTO;
    }

    ArrayList<NewsListDTO> newsListDTO;


    public static class NewsListDTO {
        private String Hline, ImageURL, PubTime, Source, subHeading;

        public String getSubHeading() {
            return subHeading;
        }

        public void setSubHeading(String subHeading) {
            this.subHeading = subHeading;
        }

        public String getHline() {
            return Hline;
        }

        public void setHline(String hline) {
            Hline = hline;
        }

        public String getImageURL() {
            return ImageURL;
        }

        public void setImageURL(String imageURL) {
            ImageURL = imageURL;
        }

        public String getPubTime() {
            return PubTime;
        }

        public void setPubTime(String pubTime) {
            PubTime = pubTime;
        }

        public String getSource() {
            return Source;
        }

        public void setSource(String source) {
            Source = source;
        }
    }
}
