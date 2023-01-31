package com.example.livecrickettvscores.Activities.Retrofit.ResponseModel;


import java.util.ArrayList;

public class FixturesResponseModel {


    private ArrayList<TypeMatchesDTO> typeMatches;
    private FiltersDTO filters;
    private AppIndexDTO appIndex;
    private String responseLastUpdated;

    public ArrayList<TypeMatchesDTO> getTypeMatches() {
        return typeMatches;
    }

    public void setTypeMatches(ArrayList<TypeMatchesDTO> typeMatches) {
        this.typeMatches = typeMatches;
    }

    public FiltersDTO getFilters() {
        return filters;
    }

    public void setFilters(FiltersDTO filters) {
        this.filters = filters;
    }

    public AppIndexDTO getAppIndex() {
        return appIndex;
    }

    public void setAppIndex(AppIndexDTO appIndex) {
        this.appIndex = appIndex;
    }

    public String getResponseLastUpdated() {
        return responseLastUpdated;
    }

    public void setResponseLastUpdated(String responseLastUpdated) {
        this.responseLastUpdated = responseLastUpdated;
    }

    public static class FiltersDTO {
        private ArrayList<String> matchType;

        public ArrayList<String> getMatchType() {
            return matchType;
        }

        public void setMatchType(ArrayList<String> matchType) {
            this.matchType = matchType;
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

    public static class TypeMatchesDTO {
        private String matchType;
        private ArrayList<SeriesAdWrapperDTO> seriesAdWrapper;

        public String getMatchType() {
            return matchType;
        }

        public void setMatchType(String matchType) {
            this.matchType = matchType;
        }

        public ArrayList<SeriesAdWrapperDTO> getSeriesAdWrapper() {
            return seriesAdWrapper;
        }

        public void setSeriesAdWrapper(ArrayList<SeriesAdWrapperDTO> seriesAdWrapper) {
            this.seriesAdWrapper = seriesAdWrapper;
        }

        public static class SeriesAdWrapperDTO {
            private SeriesMatchesDTO seriesMatches;
            private AdDetailDTO adDetail;

            public SeriesMatchesDTO getSeriesMatches() {
                return seriesMatches;
            }

            public void setSeriesMatches(SeriesMatchesDTO seriesMatches) {
                this.seriesMatches = seriesMatches;
            }

            public AdDetailDTO getAdDetail() {
                return adDetail;
            }

            public void setAdDetail(AdDetailDTO adDetail) {
                this.adDetail = adDetail;
            }

            public static class SeriesMatchesDTO {
                private int seriesId;
                private String seriesName;
                private ArrayList<MatchesDTO> matches;

                public int getSeriesId() {
                    return seriesId;
                }

                public void setSeriesId(int seriesId) {
                    this.seriesId = seriesId;
                }

                public String getSeriesName() {
                    return seriesName;
                }

                public void setSeriesName(String seriesName) {
                    this.seriesName = seriesName;
                }

                public ArrayList<MatchesDTO> getMatches() {
                    return matches;
                }

                public void setMatches(ArrayList<MatchesDTO> matches) {
                    this.matches = matches;
                }

                public static class MatchesDTO {
                    public MatchInfoDTO matchInfo;
                    public MatchScoreDTO matchScore;

                    public MatchInfoDTO getMatchInfo() {
                        return matchInfo;
                    }

                    public void setMatchInfo(MatchInfoDTO matchInfo) {
                        this.matchInfo = matchInfo;
                    }

                    public MatchScoreDTO getMatchScore() {
                        return matchScore;
                    }

                    public void setMatchScore(MatchScoreDTO matchScore) {
                        this.matchScore = matchScore;
                    }

                    public static class MatchInfoDTO {
                        private int matchId;
                        private int seriesId;
                        private String seriesName;
                        private String matchDesc;
                        private String matchFormat;
                        private String startDate;
                        private String endDate;
                        private String state;
                        private String status;
                        private Team1DTO team1;
                        private Team2DTO team2;
                        private VenueInfoDTO venueInfo;
                        private int currentBatTeamId;
                        private String seriesStartDt;
                        private String seriesEndDt;

                        public int getMatchId() {
                            return matchId;
                        }

                        public void setMatchId(int matchId) {
                            this.matchId = matchId;
                        }

                        public int getSeriesId() {
                            return seriesId;
                        }

                        public void setSeriesId(int seriesId) {
                            this.seriesId = seriesId;
                        }

                        public String getSeriesName() {
                            return seriesName;
                        }

                        public void setSeriesName(String seriesName) {
                            this.seriesName = seriesName;
                        }

                        public String getMatchDesc() {
                            return matchDesc;
                        }

                        public void setMatchDesc(String matchDesc) {
                            this.matchDesc = matchDesc;
                        }

                        public String getMatchFormat() {
                            return matchFormat;
                        }

                        public void setMatchFormat(String matchFormat) {
                            this.matchFormat = matchFormat;
                        }

                        public String getStartDate() {
                            return startDate;
                        }

                        public void setStartDate(String startDate) {
                            this.startDate = startDate;
                        }

                        public String getEndDate() {
                            return endDate;
                        }

                        public void setEndDate(String endDate) {
                            this.endDate = endDate;
                        }

                        public String getState() {
                            return state;
                        }

                        public void setState(String state) {
                            this.state = state;
                        }

                        public String getStatus() {
                            return status;
                        }

                        public void setStatus(String status) {
                            this.status = status;
                        }

                        public Team1DTO getTeam1() {
                            return team1;
                        }

                        public void setTeam1(Team1DTO team1) {
                            this.team1 = team1;
                        }

                        public Team2DTO getTeam2() {
                            return team2;
                        }

                        public void setTeam2(Team2DTO team2) {
                            this.team2 = team2;
                        }

                        public VenueInfoDTO getVenueInfo() {
                            return venueInfo;
                        }

                        public void setVenueInfo(VenueInfoDTO venueInfo) {
                            this.venueInfo = venueInfo;
                        }

                        public int getCurrentBatTeamId() {
                            return currentBatTeamId;
                        }

                        public void setCurrentBatTeamId(int currentBatTeamId) {
                            this.currentBatTeamId = currentBatTeamId;
                        }

                        public String getSeriesStartDt() {
                            return seriesStartDt;
                        }

                        public void setSeriesStartDt(String seriesStartDt) {
                            this.seriesStartDt = seriesStartDt;
                        }

                        public String getSeriesEndDt() {
                            return seriesEndDt;
                        }

                        public void setSeriesEndDt(String seriesEndDt) {
                            this.seriesEndDt = seriesEndDt;
                        }

                        public static class Team1DTO {
                            private int teamId;
                            private String teamName;
                            private String teamSName;
                            private int imageId;

                            public int getTeamId() {
                                return teamId;
                            }

                            public void setTeamId(int teamId) {
                                this.teamId = teamId;
                            }

                            public String getTeamName() {
                                return teamName;
                            }

                            public void setTeamName(String teamName) {
                                this.teamName = teamName;
                            }

                            public String getTeamSName() {
                                return teamSName;
                            }

                            public void setTeamSName(String teamSName) {
                                this.teamSName = teamSName;
                            }

                            public int getImageId() {
                                return imageId;
                            }

                            public void setImageId(int imageId) {
                                this.imageId = imageId;
                            }
                        }

                        public static class Team2DTO {
                            private int teamId;
                            private String teamName;
                            private String teamSName;
                            private int imageId;

                            public int getTeamId() {
                                return teamId;
                            }

                            public void setTeamId(int teamId) {
                                this.teamId = teamId;
                            }

                            public String getTeamName() {
                                return teamName;
                            }

                            public void setTeamName(String teamName) {
                                this.teamName = teamName;
                            }

                            public String getTeamSName() {
                                return teamSName;
                            }

                            public void setTeamSName(String teamSName) {
                                this.teamSName = teamSName;
                            }

                            public int getImageId() {
                                return imageId;
                            }

                            public void setImageId(int imageId) {
                                this.imageId = imageId;
                            }
                        }

                        public static class VenueInfoDTO {
                            private int id;
                            private String ground;
                            private String city;
                            private String timezone;

                            public int getId() {
                                return id;
                            }

                            public void setId(int id) {
                                this.id = id;
                            }

                            public String getGround() {
                                return ground;
                            }

                            public void setGround(String ground) {
                                this.ground = ground;
                            }

                            public String getCity() {
                                return city;
                            }

                            public void setCity(String city) {
                                this.city = city;
                            }

                            public String getTimezone() {
                                return timezone;
                            }

                            public void setTimezone(String timezone) {
                                this.timezone = timezone;
                            }
                        }
                    }

                    public static class MatchScoreDTO {
                        private Team1ScoreDTO team1Score;
                        private Team2ScoreDTO team2Score;

                        public Team1ScoreDTO getTeam1Score() {
                            return team1Score;
                        }

                        public void setTeam1Score(Team1ScoreDTO team1Score) {
                            this.team1Score = team1Score;
                        }

                        public Team2ScoreDTO getTeam2Score() {
                            return team2Score;
                        }

                        public void setTeam2Score(Team2ScoreDTO team2Score) {
                            this.team2Score = team2Score;
                        }

                        public static class Team1ScoreDTO {
                            private Inngs1DTO inngs1;

                            public Inngs1DTO getInngs1() {
                                return inngs1;
                            }

                            public void setInngs1(Inngs1DTO inngs1) {
                                this.inngs1 = inngs1;
                            }

                            public static class Inngs1DTO {
                                private int inningsId;
                                private int runs;
                                private int wickets;
                                private double overs;

                                public int getInningsId() {
                                    return inningsId;
                                }

                                public void setInningsId(int inningsId) {
                                    this.inningsId = inningsId;
                                }

                                public int getRuns() {
                                    return runs;
                                }

                                public void setRuns(int runs) {
                                    this.runs = runs;
                                }

                                public int getWickets() {
                                    return wickets;
                                }

                                public void setWickets(int wickets) {
                                    this.wickets = wickets;
                                }

                                public double getOvers() {
                                    return overs;
                                }

                                public void setOvers(double overs) {
                                    this.overs = overs;
                                }
                            }
                        }

                        public static class Team2ScoreDTO {
                            private Inngs1DTO inngs1;

                            public Inngs1DTO getInngs1() {
                                return inngs1;
                            }

                            public void setInngs1(Inngs1DTO inngs1) {
                                this.inngs1 = inngs1;
                            }

                            public static class Inngs1DTO {
                                private int inningsId;
                                private int runs;
                                private int wickets;
                                private double overs;

                                public int getInningsId() {
                                    return inningsId;
                                }

                                public void setInningsId(int inningsId) {
                                    this.inningsId = inningsId;
                                }

                                public int getRuns() {
                                    return runs;
                                }

                                public void setRuns(int runs) {
                                    this.runs = runs;
                                }

                                public int getWickets() {
                                    return wickets;
                                }

                                public void setWickets(int wickets) {
                                    this.wickets = wickets;
                                }

                                public double getOvers() {
                                    return overs;
                                }

                                public void setOvers(double overs) {
                                    this.overs = overs;
                                }
                            }
                        }
                    }
                }
            }

            public static class AdDetailDTO {
                private String name;
                private String adLayout;
                private int position;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getAdLayout() {
                    return adLayout;
                }

                public void setAdLayout(String adLayout) {
                    this.adLayout = adLayout;
                }

                public int getPosition() {
                    return position;
                }

                public void setPosition(int position) {
                    this.position = position;
                }
            }
        }
    }
}
