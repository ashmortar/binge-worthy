package com.example.guest.binge_worthy.models;

import org.parceler.Parcel;

@Parcel
public class Recommendation {
    String name;
    String Type;
    String wTeaser;
    String wUrl;
    String yUrl;
    String yID;

    public Recommendation(){}

    public Recommendation(String name, String type, String wTeaser, String wUrl, String yUrl, String yID) {
        this.name = name;
        Type = type;
        this.wTeaser = wTeaser;
        this.wUrl = wUrl;
        this.yUrl = yUrl;
        this.yID = yID;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return Type;
    }

    public String getwTeaser() {
        return wTeaser;
    }

    public String getwUrl() {
        return wUrl;
    }

    public String getyUrl() {
        return yUrl;
    }

    public String getyID() {
        return yID;
    }
}
