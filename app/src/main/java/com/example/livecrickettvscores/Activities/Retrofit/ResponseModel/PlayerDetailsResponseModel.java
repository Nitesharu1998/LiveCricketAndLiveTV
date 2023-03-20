package com.example.livecrickettvscores.Activities.Retrofit.ResponseModel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlayerDetailsResponseModel {


    private String id = "";
    private String bat = "";
    private String bowl = "";
    private String name = "";
    private String nickName = "";
    private String height = "";
    private String role = "";
    private String birthPlace = "";
    private ArrayList<String> intlTeam = new ArrayList<>();
    private ArrayList<String> teams = new ArrayList<>();
    @SerializedName("DoB")
    private String doB = "";
    private String image = "";
    private String bio = "";
    private CurrRankDTO currRank = new CurrRankDTO();
    private AppIndexDTO appIndex = new AppIndexDTO();
    @SerializedName("DoBFormat")
    private String doBFormat = "";
    private String faceImageId = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBat() {
        return bat;
    }

    public void setBat(String bat) {
        this.bat = bat;
    }

    public String getBowl() {
        return bowl;
    }

    public void setBowl(String bowl) {
        this.bowl = bowl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public ArrayList<String> getIntlTeam() {
        return intlTeam;
    }

    public void setIntlTeam(ArrayList<String> intlTeam) {
        this.intlTeam = intlTeam;
    }

    public ArrayList<String> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<String> teams) {
        this.teams = teams;
    }

    public String getDoB() {
        return doB;
    }

    public void setDoB(String doB) {
        this.doB = doB;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public CurrRankDTO getCurrRank() {
        return currRank;
    }

    public void setCurrRank(CurrRankDTO currRank) {
        this.currRank = currRank;
    }

    public AppIndexDTO getAppIndex() {
        return appIndex;
    }

    public void setAppIndex(AppIndexDTO appIndex) {
        this.appIndex = appIndex;
    }

    public String getDoBFormat() {
        return doBFormat;
    }

    public void setDoBFormat(String doBFormat) {
        this.doBFormat = doBFormat;
    }

    public String getFaceImageId() {
        return faceImageId;
    }

    public void setFaceImageId(String faceImageId) {
        this.faceImageId = faceImageId;
    }

    public static class CurrRankDTO {
        private BatDTO bat = new BatDTO();
        private BowlDTO bowl = new BowlDTO();
        private AllDTO all = new AllDTO();

        public BatDTO getBat() {
            return bat;
        }

        public void setBat(BatDTO bat) {
            this.bat = bat;
        }

        public BowlDTO getBowl() {
            return bowl;
        }

        public void setBowl(BowlDTO bowl) {
            this.bowl = bowl;
        }

        public AllDTO getAll() {
            return all;
        }

        public void setAll(AllDTO all) {
            this.all = all;
        }

        public static class BatDTO {
            private String testRank = "";
            private String odiRank = "";
            private String t20Rank = "";
            private String testBestRank = "";
            private String odiBestRank = "";
            private String t20BestRank = "";
            private String odiDiffRank = "";
            private String t20DiffRank = "";

            public String getTestRank() {
                return testRank;
            }

            public void setTestRank(String testRank) {
                this.testRank = testRank;
            }

            public String getOdiRank() {
                return odiRank;
            }

            public void setOdiRank(String odiRank) {
                this.odiRank = odiRank;
            }

            public String getT20Rank() {
                return t20Rank;
            }

            public void setT20Rank(String t20Rank) {
                this.t20Rank = t20Rank;
            }

            public String getTestBestRank() {
                return testBestRank;
            }

            public void setTestBestRank(String testBestRank) {
                this.testBestRank = testBestRank;
            }

            public String getOdiBestRank() {
                return odiBestRank;
            }

            public void setOdiBestRank(String odiBestRank) {
                this.odiBestRank = odiBestRank;
            }

            public String getT20BestRank() {
                return t20BestRank;
            }

            public void setT20BestRank(String t20BestRank) {
                this.t20BestRank = t20BestRank;
            }

            public String getOdiDiffRank() {
                return odiDiffRank;
            }

            public void setOdiDiffRank(String odiDiffRank) {
                this.odiDiffRank = odiDiffRank;
            }

            public String getT20DiffRank() {
                return t20DiffRank;
            }

            public void setT20DiffRank(String t20DiffRank) {
                this.t20DiffRank = t20DiffRank;
            }
        }

        public static class BowlDTO {

            private String testRank = "";
            private String odiRank = "";
            private String t20Rank = "";
            private String testBestRank = "";
            private String odiBestRank = "";
            private String t20BestRank = "";
            private String odiDiffRank = "";
            private String t20DiffRank = "";

            public String getTestRank() {
                return testRank;
            }

            public void setTestRank(String testRank) {
                this.testRank = testRank;
            }

            public String getOdiRank() {
                return odiRank;
            }

            public void setOdiRank(String odiRank) {
                this.odiRank = odiRank;
            }

            public String getT20Rank() {
                return t20Rank;
            }

            public void setT20Rank(String t20Rank) {
                this.t20Rank = t20Rank;
            }

            public String getTestBestRank() {
                return testBestRank;
            }

            public void setTestBestRank(String testBestRank) {
                this.testBestRank = testBestRank;
            }

            public String getOdiBestRank() {
                return odiBestRank;
            }

            public void setOdiBestRank(String odiBestRank) {
                this.odiBestRank = odiBestRank;
            }

            public String getT20BestRank() {
                return t20BestRank;
            }

            public void setT20BestRank(String t20BestRank) {
                this.t20BestRank = t20BestRank;
            }

            public String getOdiDiffRank() {
                return odiDiffRank;
            }

            public void setOdiDiffRank(String odiDiffRank) {
                this.odiDiffRank = odiDiffRank;
            }

            public String getT20DiffRank() {
                return t20DiffRank;
            }

            public void setT20DiffRank(String t20DiffRank) {
                this.t20DiffRank = t20DiffRank;
            }
        }

        public static class AllDTO {
            private String testRank = "";
            private String odiRank = "";
            private String t20Rank = "";
            private String testBestRank = "";
            private String odiBestRank = "";
            private String t20BestRank = "";
            private String odiDiffRank = "";
            private String t20DiffRank = "";

            public String getTestRank() {
                return testRank;
            }

            public void setTestRank(String testRank) {
                this.testRank = testRank;
            }

            public String getOdiRank() {
                return odiRank;
            }

            public void setOdiRank(String odiRank) {
                this.odiRank = odiRank;
            }

            public String getT20Rank() {
                return t20Rank;
            }

            public void setT20Rank(String t20Rank) {
                this.t20Rank = t20Rank;
            }

            public String getTestBestRank() {
                return testBestRank;
            }

            public void setTestBestRank(String testBestRank) {
                this.testBestRank = testBestRank;
            }

            public String getOdiBestRank() {
                return odiBestRank;
            }

            public void setOdiBestRank(String odiBestRank) {
                this.odiBestRank = odiBestRank;
            }

            public String getT20BestRank() {
                return t20BestRank;
            }

            public void setT20BestRank(String t20BestRank) {
                this.t20BestRank = t20BestRank;
            }

            public String getOdiDiffRank() {
                return odiDiffRank;
            }

            public void setOdiDiffRank(String odiDiffRank) {
                this.odiDiffRank = odiDiffRank;
            }

            public String getT20DiffRank() {
                return t20DiffRank;
            }

            public void setT20DiffRank(String t20DiffRank) {
                this.t20DiffRank = t20DiffRank;
            }
        }
    }

    public static class AppIndexDTO {
        private String seoTitle = "";
        private String webURL = "";

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
}
