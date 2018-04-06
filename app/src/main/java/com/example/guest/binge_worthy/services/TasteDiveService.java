package com.example.guest.binge_worthy.services;

import android.util.Log;
import com.example.guest.binge_worthy.Constants;
import com.example.guest.binge_worthy.models.Recommendation;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TasteDiveService {
    public static final String TAG = TasteDiveService.class.getSimpleName();

    public static void apiCall(String query, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.QUERY_PARAMETER, query);
        urlBuilder.addQueryParameter(Constants.INFO_PARAMETER, "1");
        urlBuilder.addQueryParameter(Constants.API_KEY_PARAMETER, Constants.TASTEDIVE_API_KEY);
        String url = urlBuilder.build().toString();
        Log.d(TAG, url);

        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Recommendation> processResults(Response response) {
        ArrayList<Recommendation> output = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            JSONObject resultsJSON = new JSONObject(jsonData);
            JSONArray queryArray = resultsJSON.getJSONObject("Similar").getJSONArray("Info");
            JSONObject queryFound = queryArray.getJSONObject(0);
            String mname = queryFound.getString("Name");
            String mtype = queryFound.getString("Type");
            String mwTeaser = queryFound.getString("wTeaser");
            String mwURL = queryFound.optString("wUrl", "no wikipedia page available");
            String myUrl = queryFound.getString("yUrl");
            String myID = queryFound.optString("yID", "");
            if (mwURL.equals("null")) {
                mwURL = "no wikipedia page available";
            }
            if (myUrl.equals("null")) {
                myUrl = "https://www.youtube.com/results?search_query=" +  mname.replace(" ", "+");
            }
            if (myID.equals("null")) {
                myID = "";
            }
            Recommendation mRecommendation = new Recommendation(mname, mtype, mwTeaser, mwURL, myUrl, myID);
            output.add(mRecommendation);

            JSONArray resultsARRAY = resultsJSON.getJSONObject("Similar").getJSONArray("Results");

            for (int i =  0; i < resultsARRAY.length(); i++) {
                JSONObject recommendationJSON = resultsARRAY.getJSONObject(i);
                String name = recommendationJSON.getString("Name");
                String type = recommendationJSON.getString("Type");
                String wTeaser = recommendationJSON.getString("wTeaser");
                String wURL = recommendationJSON.optString("wUrl", "no wikipedia page available");
                String yUrl = recommendationJSON.getString("yUrl");
                String yID = recommendationJSON.optString("yID", "");
                if (wURL.equals("null")) {
                    wURL = "no wikipedia page available";
                }
                if (yUrl.equals("null")) {
                    yUrl = "https://www.youtube.com/results?search_query=" +  name.replace(" ", "+");
                }
                if (yID.equals("null")) {
                    yID = "";
                }
                Recommendation recommendation = new Recommendation(name, type, wTeaser, wURL, yUrl, yID);
                output.add(recommendation);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return output;
    }
}
