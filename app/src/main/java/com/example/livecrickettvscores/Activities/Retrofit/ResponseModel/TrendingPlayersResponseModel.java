package com.example.livecrickettvscores.Activities.Retrofit.ResponseModel;

import java.util.ArrayList;

public class TrendingPlayersResponseModel {

    private ArrayList<PlayerDTO> player;
    private String category;

    public ArrayList<PlayerDTO> getPlayer() {
        return player;
    }

    public void setPlayer(ArrayList<PlayerDTO> player) {
        this.player = player;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static class PlayerDTO {
        private String id;
        private String name;
        private String teamName;
        private String faceImageId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public String getFaceImageId() {
            return faceImageId;
        }

        public void setFaceImageId(String faceImageId) {
            this.faceImageId = faceImageId;
        }
    }
}
