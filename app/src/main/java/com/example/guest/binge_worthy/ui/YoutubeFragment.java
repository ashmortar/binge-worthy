package com.example.guest.binge_worthy.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guest.binge_worthy.Constants;
import com.example.guest.binge_worthy.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;


public class YoutubeFragment extends Fragment {
    private static final String TAG = YoutubeFragment.class.getSimpleName();
    private static String VIDEO_ID = "3TeW2ryh-sQ";

    public YoutubeFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_youtube, container, false);

        YouTubePlayerSupportFragment yFrag = YouTubePlayerSupportFragment.newInstance();

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.add(R.id.youtube_layout, yFrag).commit();
        yFrag.initialize(Constants.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                youTubePlayer.loadVideo(VIDEO_ID);
                youTubePlayer.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                String errorMessage = youTubeInitializationResult.toString();
                Log.d(TAG, "onInitializationFailure: " + errorMessage);
            }
        });

        return rootView;
    }

}
