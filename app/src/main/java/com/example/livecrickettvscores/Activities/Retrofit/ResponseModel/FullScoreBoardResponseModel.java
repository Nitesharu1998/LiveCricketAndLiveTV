package com.example.livecrickettvscores.Activities.Retrofit.ResponseModel;

import java.util.ArrayList;

public class FullScoreBoardResponseModel {

    ArrayList<MatchInningDTO> matchInningDTOS;

    public ArrayList<MatchInningDTO> getMatchInningDTOS() {
        return matchInningDTOS;
    }

    public void setMatchInningDTOS(ArrayList<MatchInningDTO> matchInningDTOS) {
        this.matchInningDTOS = matchInningDTOS;
    }

    public class MatchInningDTO {
        String teamName, teamTotal, teamFallOfWicket;
        ArrayList<BatsmanDTO> batsmanDTOS;
        ArrayList<BowlerDTO> bowlerDTOS;

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public String getTeamTotal() {
            return teamTotal;
        }

        public void setTeamTotal(String teamTotal) {
            this.teamTotal = teamTotal;
        }

        public String getTeamFallOfWicket() {
            return teamFallOfWicket;
        }

        public void setTeamFallOfWicket(String teamFallOfWicket) {
            this.teamFallOfWicket = teamFallOfWicket;
        }

        public ArrayList<BatsmanDTO> getBatsmanDTOS() {
            return batsmanDTOS;
        }

        public void setBatsmanDTOS(ArrayList<BatsmanDTO> batsmanDTOS) {
            this.batsmanDTOS = batsmanDTOS;
        }

        public ArrayList<BowlerDTO> getBowlerDTOS() {
            return bowlerDTOS;
        }

        public void setBowlerDTOS(ArrayList<BowlerDTO> bowlerDTOS) {
            this.bowlerDTOS = bowlerDTOS;
        }
    }

    public class BatsmanDTO {
        String batsmanName, status, R, B, M, fours, sixes, SR;

        public String getBatsmanName() {
            return batsmanName;
        }

        public void setBatsmanName(String batsmanName) {
            this.batsmanName = batsmanName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getR() {
            return R;
        }

        public void setR(String r) {
            R = r;
        }

        public String getB() {
            return B;
        }

        public void setB(String b) {
            B = b;
        }

        public String getM() {
            return M;
        }

        public void setM(String m) {
            M = m;
        }

        public String getFours() {
            return fours;
        }

        public void setFours(String fours) {
            this.fours = fours;
        }

        public String getSixes() {
            return sixes;
        }

        public void setSixes(String sixes) {
            this.sixes = sixes;
        }

        public String getSR() {
            return SR;
        }

        public void setSR(String SR) {
            this.SR = SR;
        }
    }

    public class BowlerDTO {
        String bowlerName, O, M, R, W, econ;

        public String getBowlerName() {
            return bowlerName;
        }

        public void setBowlerName(String bowlerName) {
            this.bowlerName = bowlerName;
        }

        public String getO() {
            return O;
        }

        public void setO(String o) {
            O = o;
        }

        public String getM() {
            return M;
        }

        public void setM(String m) {
            M = m;
        }

        public String getR() {
            return R;
        }

        public void setR(String r) {
            R = r;
        }

        public String getW() {
            return W;
        }

        public void setW(String w) {
            W = w;
        }

        public String getEcon() {
            return econ;
        }

        public void setEcon(String econ) {
            this.econ = econ;
        }
    }
}
