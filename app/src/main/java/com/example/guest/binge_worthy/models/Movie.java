package com.example.guest.binge_worthy.models;



public class Movie {
    private int id;
    private String title;
    private String overview;
    private String tagline;
    private String posterUrl;
    private String[] genres;
    private double vote_average;
    private int vote_count;
    private CastMember[] cast;
    private CrewMember[] crew;

    public Movie(int id, String title, String overview, String tagline, String posterUrl, String[] genres, double vote_average, int vote_count, CastMember[] cast, CrewMember[] crew) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.tagline = tagline;
        this.posterUrl = posterUrl;
        this.genres = genres;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.cast = cast;
        this.crew = crew;
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

    public double getVote_average() {
        return vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public CastMember[] getCast() {
        return cast;
    }

    public CrewMember[] getCrew() {
        return crew;
    }
}
