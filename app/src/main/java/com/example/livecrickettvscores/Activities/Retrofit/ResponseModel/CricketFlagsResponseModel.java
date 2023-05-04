package com.example.livecrickettvscores.Activities.Retrofit.ResponseModel;

import java.util.ArrayList;

public class CricketFlagsResponseModel {
    public ArrayList<CricketFlagsResponseModel.FlagList> getFlagList() {
        return FlagList;
    }

    public void setFlagList(ArrayList<CricketFlagsResponseModel.FlagList> flagList) {
        FlagList = flagList;
    }

    ArrayList<FlagList> FlagList = new ArrayList<>();

    public class FlagList {
        String FlagName, FlagURL;

        public String getFlagURL() {
            return FlagURL;
        }

        public void setFlagURL(String flagURL) {
            FlagURL = flagURL;
        }

        public String getFlagName() {
            return FlagName;
        }

        public void setFlagName(String flagName) {
            FlagName = flagName;
        }
    }
}
