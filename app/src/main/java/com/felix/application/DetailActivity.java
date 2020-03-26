package com.felix.application;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.felix.application.fragment.YoutubeFragment;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import at.huber.youtubeExtractor.YouTubeUriExtractor;
import at.huber.youtubeExtractor.YtFile;

public class DetailActivity extends YouTubeBaseActivity {
    private static final String VIDEO_ID = "VIDEO_ID";
    private static final String VIDEO_TITLE = "VIDEO_TITLE";
    private static final String VIDEO_DESC = "VIDEO_DESC";
    private static final String TAG = "DetailActivity";
    private static final String API_KEY = "AIzaSyBy_LE8dm8I8bu1NxGO-IjmelqWbdj2-0A";
    private String videoId;
    private String videoTitle;
    private String videoDesc;


    private TextView title;
    private TextView dl;
    private TextView desc;

    private YouTubePlayerView mYoutubePlayerView;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;

    public static void start(Context context, String videoId, String videoDesc, String videoTitle) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(VIDEO_ID, videoId);
        intent.putExtra(VIDEO_TITLE, videoTitle);
        intent.putExtra(VIDEO_DESC, videoDesc);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent != null) {
            videoId = intent.getStringExtra(VIDEO_ID);
            videoTitle = intent.getStringExtra(VIDEO_TITLE);
            videoDesc = intent.getStringExtra(VIDEO_DESC);
        }
        title = findViewById(R.id.titleVideo);
        desc = findViewById(R.id.descriptionVideo);
        dl = findViewById(R.id.download);

        dl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = ("https://www.youtube.com/watch?v=" + videoId);
                Log.e("dl url", link);
                @SuppressLint("StaticFieldLeak") YouTubeUriExtractor ytExt = new YouTubeUriExtractor(DetailActivity.this) {
                    @Override
                    public void onUrisAvailable(String VideoId, String VideoTitle, SparseArray<YtFile> ytFiles) {

                        if (ytFiles != null) {
                            int itag = 18;
                            String downloadURL = ytFiles.get(itag).getUrl();
                            Log.e("dl url", downloadURL);

                            DownloadManager.Request request=new DownloadManager.Request(Uri.parse(downloadURL));

                            request.setTitle(videoTitle);
                            request.setDescription("Your file is downloading");

                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,videoTitle+".mp4");
                            DownloadManager downloadManager=(DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
                            request.allowScanningByMediaScanner();
                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
                            downloadManager.enqueue(request);
                        }

                    }
                };
                ytExt.execute(link);
            }
        });

        title.setText(Html.fromHtml(videoTitle));
        desc.setText(Html.fromHtml(videoDesc));

        mYoutubePlayerView = findViewById(R.id.videoView);
        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(videoId);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        mYoutubePlayerView.initialize(API_KEY, mOnInitializedListener);


    }

}
