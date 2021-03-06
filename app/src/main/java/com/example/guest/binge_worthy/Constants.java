package com.example.guest.binge_worthy;



public class Constants {
    //make api key available
    public static final String TASTEDIVE_API_KEY = BuildConfig.TASTEDIVE_DB_API_KEY;
    public static final String YOUTUBE_API_KEY = BuildConfig.YOUTUBE_API_KEY;
    //query parameters
    public static final String QUERY_PARAMETER = "q";
    public static final String TYPE_PARAMETER = "type";
    public static final String INFO_PARAMETER = "info";
    public static final String LIMIT_PARAMTER = "limit";
    public static final String API_KEY_PARAMETER = "k";

    //base url(s)
    public static final String BASE_URL = "https://tastedive.com/api/similar";

    // constants
    public static final String POSITION_KEY = "position";
    public static final String RECSARRAY_KEY = "recs";
    public static final String SOURCE_KEY = "source";
    public static final String FROMAPI = "api";
    public static final String FROMFIREBASE = "saved";
}
