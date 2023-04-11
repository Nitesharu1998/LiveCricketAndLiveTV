package com.example.livecrickettvscores.Activities.Retrofit.ResponseModel;

public class CountriesResponseModel {
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(String countryFlag) {
        this.countryFlag = countryFlag;
    }

    public String getCountryEndpoint() {
        return countryEndpoint;
    }

    public void setCountryEndpoint(String countryEndpoint) {
        this.countryEndpoint = countryEndpoint;
    }

    String countryName, countryFlag, countryEndpoint;
}
