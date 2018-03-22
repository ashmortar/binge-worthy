package com.example.guest.binge_worthy.models;


import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class Movie {
    int id;
    String title;
    String overview;
    String tagline;
    String posterUrl;
    String[] genres;
    String vote_average;
    String vote_count;
    ArrayList<CastMember> cast;
    ArrayList<CrewMember> crew;

    public Movie() {}

    public Movie(int id, String title, String overview, String posterUrl, String vote_average, String vote_count) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterUrl = "https://image.tmdb.org/t/p/original" + posterUrl;
        this.vote_average = vote_average + "/10";
        this.vote_count = vote_count + " votes";
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getTagline() {
        return tagline;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String[] getGenres() {
        return genres;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getVote_count() {
        return vote_count;
    }

    public ArrayList<CastMember> getCast() {
        return cast;
    }

    public ArrayList<CrewMember> getCrew() {
        return crew;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public void setCast(ArrayList<CastMember> cast) {
        this.cast = cast;
    }

    public void setCrew(ArrayList<CrewMember> crew) {
        this.crew = crew;
    }
}
