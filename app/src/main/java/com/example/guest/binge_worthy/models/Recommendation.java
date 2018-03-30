package com.example.guest.binge_worthy.models;

import org.parceler.Parcel;

@Parcel
public class Recommendation {
    private String name;
    private String Type;
    private String wTeaser;
    private String wUrl;
    private String yUrl;
    private String yID;
    private String pushId;

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

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getwTeaser() {
        return wTeaser;
    }

    public void setwTeaser(String wTeaser) {
        this.wTeaser = wTeaser;
    }

    public String getwUrl() {
        return wUrl;
    }

    public void setwUrl(String wUrl) {
        this.wUrl = wUrl;
    }

    public String getyUrl() {
        return yUrl;
    }

    public void setyUrl(String yUrl) {
        this.yUrl = yUrl;
    }

    public String getyID() {
        return yID;
    }

    public void setyID(String yID) {
        this.yID = yID;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
