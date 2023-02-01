package com.example.livecrickettvscores.Activities.Retrofit.ResponseModel;

import java.util.ArrayList;

public class NewsDetailsResponseModel {

    private int id;
    private String context;
    private String headline;
    private String publishTime;
    private CoverImageDTO coverImage;
    private ArrayList<ContentDTO> content;
    private ArrayList<EmbedsDTO> embeds;
    private ArrayList<AuthorsDTO> authors;
    private ArrayList<TagsDTO> tags;
    private AppIndexDTO appIndex;
    private String storyType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public CoverImageDTO getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(CoverImageDTO coverImage) {
        this.coverImage = coverImage;
    }

    public ArrayList<ContentDTO> getContent() {
        return content;
    }

    public void setContent(ArrayList<ContentDTO> content) {
        this.content = content;
    }

    public ArrayList<EmbedsDTO> getEmbeds() {
        return embeds;
    }

    public void setEmbeds(ArrayList<EmbedsDTO> embeds) {
        this.embeds = embeds;
    }

    public ArrayList<AuthorsDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<AuthorsDTO> authors) {
        this.authors = authors;
    }

    public ArrayList<TagsDTO> getTags() {
        return tags;
    }

    public void setTags(ArrayList<TagsDTO> tags) {
        this.tags = tags;
    }

    public AppIndexDTO getAppIndex() {
        return appIndex;
    }

    public void setAppIndex(AppIndexDTO appIndex) {
        this.appIndex = appIndex;
    }

    public String getStoryType() {
        return storyType;
    }

    public void setStoryType(String storyType) {
        this.storyType = storyType;
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

    public static class AppIndexDTO {
        private String seoTitle;
        private String webURL;

        public String getSeoTitle() {
            return seoTitle;
        }

        public void setSeoTitle(String seoTitle) {
            this.seoTitle = seoTitle;
        }

        public String getWebURL() {
            return webURL;
        }

        public void setWebURL(String webURL) {
            this.webURL = webURL;
        }
    }

    public static class ContentDTO {
        private ContentDetailsDTO content;
        private AdDTO ad;

        public ContentDetailsDTO getContent() {
            return content;
        }

        public void setContent(ContentDetailsDTO content) {
            this.content = content;
        }

        public AdDTO getAd() {
            return ad;
        }

        public void setAd(AdDTO ad) {
            this.ad = ad;
        }

        public class ContentDetailsDTO {
            private String contentType;
            private String contentValue;

            public String getContentType() {
                return contentType;
            }

            public void setContentType(String contentType) {
                this.contentType = contentType;
            }

            public String getContentValue() {
                return contentValue;
            }

            public void setContentValue(String contentValue) {
                this.contentValue = contentValue;
            }
        }

        public class AdDTO {
            private String name;
            private int position;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getPosition() {
                return position;
            }

            public void setPosition(int position) {
                this.position = position;
            }
        }
    }

    public static class EmbedsDTO {
        private String key;
        private String embedType;
        private String embedValue;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getEmbedType() {
            return embedType;
        }

        public void setEmbedType(String embedType) {
            this.embedType = embedType;
        }

        public String getEmbedValue() {
            return embedValue;
        }

        public void setEmbedValue(String embedValue) {
            this.embedValue = embedValue;
        }
    }

    public static class AuthorsDTO {
        private int id;
        private String name;
        private int imageId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getImageId() {
            return imageId;
        }

        public void setImageId(int imageId) {
            this.imageId = imageId;
        }
    }

    public static class TagsDTO {
        private String itemName;
        private String itemType;
        private String itemId;

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }
    }
}
