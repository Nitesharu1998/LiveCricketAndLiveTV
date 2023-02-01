package com.example.livecrickettvscores.Activities.Retrofit.ResponseModel;

import java.util.ArrayList;

public class NewsListResponseModel {

    private ArrayList<NewsListDTO> newsList;

    public ArrayList<NewsListDTO> getNewsList() {
        return newsList;
    }

    public void setNewsList(ArrayList<NewsListDTO> newsList) {
        this.newsList = newsList;
    }

    public static class NewsListDTO {
        private StoryDTO story;
        public StoryDTO getStory() {
            return story;
        }

        public void setStory(StoryDTO story) {
            this.story = story;
        }


        public static class StoryDTO {
            private int id;
            private String hline;
            private String intro;
            private String pubTime;
            private String source;
            private String storyType;
            private int imageId;
            private String seoHeadline;
            private String context;
            private CoverImageDTO coverImage;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getHline() {
                return hline;
            }

            public void setHline(String hline) {
                this.hline = hline;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getPubTime() {
                return pubTime;
            }

            public void setPubTime(String pubTime) {
                this.pubTime = pubTime;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getStoryType() {
                return storyType;
            }

            public void setStoryType(String storyType) {
                this.storyType = storyType;
            }

            public int getImageId() {
                return imageId;
            }

            public void setImageId(int imageId) {
                this.imageId = imageId;
            }

            public String getSeoHeadline() {
                return seoHeadline;
            }

            public void setSeoHeadline(String seoHeadline) {
                this.seoHeadline = seoHeadline;
            }

            public String getContext() {
                return context;
            }

            public void setContext(String context) {
                this.context = context;
            }

            public CoverImageDTO getCoverImage() {
                return coverImage;
            }

            public void setCoverImage(CoverImageDTO coverImage) {
                this.coverImage = coverImage;
            }

            public static class CoverImageDTO {
                private String id;
                private String caption;
                private String source;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getCaption() {
                    return caption;
                }

                public void setCaption(String caption) {
                    this.caption = caption;
                }

                public String getSource() {
                    return source;
                }

                public void setSource(String source) {
                    this.source = source;
                }
            }
        }


    }
}
