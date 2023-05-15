package com.example.livecrickettvscores.Activities.Retrofit.ResponseModel;
import java.util.ArrayList;

public class PlayerCareerDetailsResponseModel {

    private ArrayList<ValuesDTO> values =new ArrayList<>();

    public ArrayList<ValuesDTO> getValues() {
        return values;
    }

    public void setValues(ArrayList<ValuesDTO> values) {
        this.values = values;
    }

    public static class ValuesDTO {
        private String name;
        private String debut;
        private String lastPlayed;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDebut() {
            return debut;
        }

        public void setDebut(String debut) {
            this.debut = debut;
        }

        public String getLastPlayed() {
            return lastPlayed;
        }

        public void setLastPlayed(String lastPlayed) {
            this.lastPlayed = lastPlayed;
        }
    }
}
