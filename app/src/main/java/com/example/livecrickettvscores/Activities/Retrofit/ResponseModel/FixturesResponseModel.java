package com.example.livecrickettvscores.Activities.Retrofit.ResponseModel;


import java.util.ArrayList;

public class FixturesResponseModel {
    public ArrayList<MatchesDTO> getMatches() {
        return matches;
    }

    public void setMatches(ArrayList<MatchesDTO> matches) {
        this.matches = matches;
    }

    ArrayList<MatchesDTO> matches;

    public static class MatchesDTO {
        String matchTitle, matchDate, matchTime, teamOne, teamTwo, teamOneScore, teamTwoScore, session;

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
