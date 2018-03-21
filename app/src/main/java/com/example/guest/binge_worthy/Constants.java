package com.example.guest.binge_worthy;



public class Constants {
    //make api key available
    public static final String API_KEY = BuildConfig.API_KEY;
    //query parameters
    public static final String API_KEY_PARAMETER = "api_key";
    public static final String LANGUAGE_PARAMETER = "language";
    public static final String QUERY_PARAMETER = "query";
    public static final String ADULT_CONTENT_PARAMETER = "include_adult";
    public static final String RELEASE_DATE_PARAMETER = "year";
    public static final String APPEND_TO_RESPONSE_PARAMETER = "append_to_result";
    //base url(s)
    public static final String MOVIE_SEARCH_URL = "https://api.themoviedb.org/3/search/movie";
    public static final String TV_SEARCH_URL = "https://api.themoviedb.org/3/search/tv";
    public static final String PERSON_SEARCH_URL = "https://api.themoviedb.org/3/search/person";
    public static final String MULTI_SEARCH_URL = "https://api.themoviedb.org/3/search/multi";
    public static final String GET_MOVIE_DETAIL_URL = "https://api.themoviedb.org/3/movie/";
    public static final String RATING_SEARCH_URL = "https://api.themoviedb.org/3/movie/top_rated";
}