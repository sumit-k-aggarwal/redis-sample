package com.example.redis.redissample.model;

import java.io.Serializable;

public class GeoLocation implements Serializable {
    private String city;
    private Double lat;
    private Double lon;

    public GeoLocation() {
    }

    public GeoLocation(String city, Double lat, Double lon) {
        this.city = city;
        this.lat = lat;
        this.lon = lon;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
