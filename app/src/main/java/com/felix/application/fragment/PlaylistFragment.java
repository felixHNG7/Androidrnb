package com.felix.application.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.felix.application.R;
import com.felix.application.SpotifyService;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;


public class PlaylistFragment extends Fragment {

    private static final String CLIENT_ID = "5c529ae7dcab4894be0930cf9091a405";
    private static final String REDIRECT_URI = "com.felix.application://callback";
    private SpotifyAppRemote mSpotifyAppRemote;

    private ImageButton play,stop,resume;
    private ImageView thumbnail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View playlist_view = inflater.inflate(R.layout.playlist_fragment,container,false);

        play = playlist_view.findViewById(R.id.playButton);
        stop=playlist_view.findViewById(R.id.pauseButton);
        resume=playlist_view.findViewById(R.id.resumeButton);
        thumbnail = playlist_view.findViewById(R.id.trackImageView);
        Glide.with(playlist_view).load("https://data.whicdn.com/images/315424694/original.jpg?t=1530864421").into(thumbnail);
        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                play();
            }
        });
        stop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                stop();
            }
        });
        resume.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                resume();
            }
        });

        return playlist_view;
    }
    @Override
    public void onStart() {
        super.onStart();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(getContext(), connectionParams,
                new Connector.ConnectionListener() {

                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("PlaylistFragment", "Connected! Yay!");

                        // Now you can start interacting with App Remote

                    }

                    public void onFailure(Throwable throwable) {
                        Log.e("PlaylistFragment", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }

    public void play(){
        mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:37i9dQZF1DX4SBhb3fqCJd");
        play.setVisibility(View.GONE);
        stop.setVisibility(View.VISIBLE);
        resume.setVisibility(View.GONE);
    }

    public void stop(){
        mSpotifyAppRemote.getPlayerApi().pause();
        play.setVisibility(View.GONE);
        stop.setVisibility(View.GONE);
        resume.setVisibility(View.VISIBLE);
    }

    public void resume(){
        mSpotifyAppRemote.getPlayerApi().resume();
        mSpotifyAppRemote.getPlayerApi();
        play.setVisibility(View.GONE);
        stop.setVisibility(View.VISIBLE);
        resume.setVisibility(View.GONE);


    }


}
