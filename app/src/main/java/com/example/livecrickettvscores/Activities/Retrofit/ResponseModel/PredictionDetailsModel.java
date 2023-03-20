package com.example.livecrickettvscores.Activities.Retrofit.ResponseModel;

public class PredictionDetailsModel {
    String time, matchLocation, matchName;
    String team1, team2;
    String team1Score, team2Score;
    String predictionStatus;
    String matchPredictionURL;
    String team1Img, team2Img;
    String matchStatus;
    boolean isMatchWin,isDreamElevenPredicted;

    public boolean isMatchWin() {
        return isMatchWin;
    }

    public void setMatchWin(boolean matchWin) {
        isMatchWin = matchWin;
    }

    public boolean isDreamElevenPredicted() {
        return isDreamElevenPredicted;
    }

    public void setDreamElevenPredicted(boolean dreamElevenPredicted) {
        isDreamElevenPredicted = dreamElevenPredicted;
    }

    public String getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    public String getTeam1Img() {
        return team1Img;
    }

    public void setTeam1Img(String team1Img) {
        this.team1Img = team1Img;
    }

    public String getTeam2Img() {
        return team2Img;
    }

    public void setTeam2Img(String team2Img) {
        this.team2Img = team2Img;
    }

    public String getMatchPredictionURL() {
        return matchPredictionURL;
    }

    public void setMatchPredictionURL(String matchPredictionURL) {
        this.matchPredictionURL = matchPredictionURL;
    }

    public String getPredictionStatus() {
        return predictionStatus;
    }

    public void setPredictionStatus(String predictionStatus) {
        this.predictionStatus = predictionStatus;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMatchLocation() {
        return matchLocation;
    }

    public void setMatchLocation(String matchLocation) {
        this.matchLocation = matchLocation;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getTeam1Score() {
        return team1Score;
    }

    public void setTeam1Score(String team1Score) {
        this.team1Score = team1Score;
    }

    public String getTeam2Score() {
        return team2Score;
    }

    public void setTeam2Score(String team2Score) {
        this.team2Score = team2Score;
    }
}
