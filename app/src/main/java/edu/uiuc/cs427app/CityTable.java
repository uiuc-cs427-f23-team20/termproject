package edu.uiuc.cs427app;

public class CityTable {
    String citiId;
    String citiName;
    String state;
    String country;
    double latitude;
    double longitude;

    public String getCitiId() {
        return citiId;
    }

    public String getCitiName() {
        return citiName;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setCitiId(String citiId) {
        this.citiId = citiId;
    }

    public void setCitiName(String citiName) {
        this.citiName = citiName;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "CityTable{" +
                "citiId='" + citiId + '\'' +
                ", citiName='" + citiName + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
