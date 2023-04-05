package com.example.livecrickettvscores.Activities.Retrofit.ResponseModel;


import java.util.ArrayList;

public class FixturesResponseModel {
    public String getMatchTitle() {
        return MatchTitle;
    }

    public void setMatchTitle(String matchTitle) {
        MatchTitle = matchTitle;
    }

    String MatchTitle;
    ArrayList<MatchesDTO> matches;
    public ArrayList<MatchesDTO> getMatches() {
        return matches;
    }

    public void setMatches(ArrayList<MatchesDTO> matches) {
        this.matches = matches;
    }

    public static class MatchesDTO {
        String matchTitle;
        String matchDate;
        String matchTime;
        String teamOne;
        String teamTwo;
        String teamOneScore;
        String teamTwoScore;
        String session;
        String teamOneUrl;
        String teamTwoUrl;
        String MatchType;
        String matchLocation;
        String matchScoreLink;

        public String getMatchScoreLink() {
            return matchScoreLink;
        }

        public void setMatchScoreLink(String matchScoreLink) {
            this.matchScoreLink = matchScoreLink;
        }

        public String getTeamOneUrl() {
            return teamOneUrl;
        }

        public void setTeamOneUrl(String teamOneUrl) {
            this.teamOneUrl = teamOneUrl;
        }

        public String getTeamTwoUrl() {
            return teamTwoUrl;
        }

        public void setTeamTwoUrl(String teamTwoUrl) {
            this.teamTwoUrl = teamTwoUrl;
        }

        public String getMatchType() {
            return MatchType;
        }

        public void setMatchType(String matchType) {
            MatchType = matchType;
        }


        public String getMatchLocation() {
            return matchLocation;
        }

        public void setMatchLocation(String matchLocation) {
            this.matchLocation = matchLocation;
        }


        public String getMatchTitle() {
            return matchTitle;
        }

        public void setMatchTitle(String matchTitle) {
            this.matchTitle = matchTitle;
        }

        public String getMatchDate() {
            return matchDate;
        }

        public void setMatchDate(String matchDate) {
            this.matchDate = matchDate;
        }

        public String getMatchTime() {
            return matchTime;
        }

        public void setMatchTime(String matchTime) {
            this.matchTime = matchTime;
        }

        public String getTeamOne() {
            return teamOne;
        }

        public void setTeamOne(String teamOne) {
            this.teamOne = teamOne;
        }

        public String getTeamTwo() {
            return teamTwo;
        }

        public void setTeamTwo(String teamTwo) {
            this.teamTwo = teamTwo;
        }

        public String getTeamOneScore() {
            return teamOneScore;
        }

        public void setTeamOneScore(String teamOneScore) {
            this.teamOneScore = teamOneScore;
        }

        public String getTeamTwoScore() {
            return teamTwoScore;
        }

        public void setTeamTwoScore(String teamTwoScore) {
            this.teamTwoScore = teamTwoScore;
        }

        public String getSession() {
            return session;
        }

        public void setSession(String session) {
            this.session = session;
        }
    }
}
