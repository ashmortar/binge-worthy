package com.example.guest.binge_worthy.models;



public class Movie {
    private int id;
    private String title;
    private String overview;
    private String tagline;
    private String posterUrl;
    private String[] genres;
    private String vote_average;
    private String vote_count;
    private CastMember[] cast;
    private CrewMember[] crew;

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

    public CastMember[] getCast() {
        return cast;
    }

    public CrewMember[] getCrew() {
        return crew;
    }
}
