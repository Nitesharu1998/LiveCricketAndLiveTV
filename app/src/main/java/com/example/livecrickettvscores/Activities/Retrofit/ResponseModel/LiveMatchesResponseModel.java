package com.example.livecrickettvscores.Activities.Retrofit.ResponseModel;

import java.util.ArrayArrayList;
import java.util.ArrayList;

public class LiveMatchesResponseModel {

    private String apikey;
    private ArrayList<DataDTO> data;
    private String status;
    private InfoDTO info;

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public ArrayList<DataDTO> getData() {
        return data;
    }

    public void setData(ArrayList<DataDTO> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public InfoDTO getInfo() {
        return info;
    }

    public void setInfo(InfoDTO info) {
        this.info = info;
    }

    public static class InfoDTO {
        private int hitsToday;
        private int hitsUsed;
        private int hitsLimit;
        private int credits;
        private int server;
        private int offsetRows;
        private int totalRows;
        private double queryTime;
        private int s;
        private int cache;

        public int getHitsToday() {
            return hitsToday;
        }

        public void setHitsToday(int hitsToday) {
            this.hitsToday = hitsToday;
        }

        public int getHitsUsed() {
            return hitsUsed;
        }

        public void setHitsUsed(int hitsUsed) {
            this.hitsUsed = hitsUsed;
        }

        public int getHitsLimit() {
            return hitsLimit;
        }

        public void setHitsLimit(int hitsLimit) {
            this.hitsLimit = hitsLimit;
        }

        public int getCredits() {
            return credits;
        }

        public void setCredits(int credits) {
            this.credits = credits;
        }

        public int getServer() {
            return server;
        }

        public void setServer(int server) {
            this.server = server;
        }

        public int getOffsetRows() {
            return offsetRows;
        }

        public void setOffsetRows(int offsetRows) {
            this.offsetRows = offsetRows;
        }

        public int getTotalRows() {
            return totalRows;
        }

        public void setTotalRows(int totalRows) {
            this.totalRows = totalRows;
        }

        public double getQueryTime() {
            return queryTime;
        }

        public void setQueryTime(double queryTime) {
            this.queryTime = queryTime;
        }

        public int getS() {
            return s;
        }

        public void setS(int s) {
            this.s = s;
        }

        public int getCache() {
            return cache;
        }

        public void setCache(int cache) {
            this.cache = cache;
        }
    }

    public static class DataDTO {
        private String id;
        private String name;
        private String matchType;
        private String status;
        private String venue;
        private String date;
        private String dateTimeGMT;
        private ArrayArrayList<String> teams;
        private ArrayArrayList<TeamInfoDTO> teamInfo;
        private ArrayArrayList<ScoreDTO> score;
        private String series_id;
        private boolean fantasyEnabled;
        private boolean bbbEnabled;
        private boolean hasSquad;
        private boolean matchStarted;
        private boolean matchEnded;

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

        public String getMatchType() {
            return matchType;
        }

        public void setMatchType(String matchType) {
            this.matchType = matchType;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getVenue() {
            return venue;
        }

        public void setVenue(String venue) {
            this.venue = venue;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDateTimeGMT() {
            return dateTimeGMT;
        }

        public void setDateTimeGMT(String dateTimeGMT) {
            this.dateTimeGMT = dateTimeGMT;
        }

        public ArrayArrayList<String> getTeams() {
            return teams;
        }

        public void setTeams(ArrayArrayList<String> teams) {
            this.teams = teams;
        }

        public ArrayArrayList<TeamInfoDTO> getTeamInfo() {
            return teamInfo;
        }

        public void setTeamInfo(ArrayArrayList<TeamInfoDTO> teamInfo) {
            this.teamInfo = teamInfo;
        }

        public ArrayArrayList<ScoreDTO> getScore() {
            return score;
        }

        public void setScore(ArrayArrayList<ScoreDTO> score) {
            this.score = score;
        }

        public String getSeries_id() {
            return series_id;
        }

        public void setSeries_id(String series_id) {
            this.series_id = series_id;
        }

        public boolean isFantasyEnabled() {
            return fantasyEnabled;
        }

        public void setFantasyEnabled(boolean fantasyEnabled) {
            this.fantasyEnabled = fantasyEnabled;
        }

        public boolean isBbbEnabled() {
            return bbbEnabled;
        }

        public void setBbbEnabled(boolean bbbEnabled) {
            this.bbbEnabled = bbbEnabled;
        }

        public boolean isHasSquad() {
            return hasSquad;
        }

        public void setHasSquad(boolean hasSquad) {
            this.hasSquad = hasSquad;
        }

        public boolean isMatchStarted() {
            return matchStarted;
        }

        public void setMatchStarted(boolean matchStarted) {
            this.matchStarted = matchStarted;
        }

        public boolean isMatchEnded() {
            return matchEnded;
        }

        public void setMatchEnded(boolean matchEnded) {
            this.matchEnded = matchEnded;
        }

        public static class TeamInfoDTO {
            private String name;
            private String shortname;
            private String img;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getShortname() {
                return shortname;
            }

            public void setShortname(String shortname) {
                this.shortname = shortname;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }

        public static class ScoreDTO {
            private int r;
            private int w;
            private int o;
            private String inning;

            public int getR() {
                return r;
            }

            public void setR(int r) {
                this.r = r;
            }

            public int getW() {
                return w;
            }

            public void setW(int w) {
                this.w = w;
            }

            public int getO() {
                return o;
            }

            public void setO(int o) {
                this.o = o;
            }

            public String getInning() {
                return inning;
            }

            public void setInning(String inning) {
                this.inning = inning;
            }
        }
    }
}
