package com.felix.application.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.felix.application.R;
import com.felix.application.adapters.YouTubeSearchItemAdapter;
import com.felix.application.api.YouTubeService;
import com.felix.application.models.YouTubeSearchItem;
import com.felix.application.models.YouTubeSearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YoutubeFragment extends Fragment {

    private static final String TAG = "YoutubeFragment";
    private static final String API_KEY = "AIzaSyBy_LE8dm8I8bu1NxGO-IjmelqWbdj2-0A";

    private EditText editText;
    private RecyclerView recyclerView;
    private YouTubeService service;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View youtube_view =  inflater.inflate(R.layout.youtube_fragment,container,false);

        recyclerView = (RecyclerView) youtube_view.findViewById(R.id.recyclerView_youtube);
        editText = (EditText)youtube_view.findViewById(R.id.editText);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/youtube/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(YouTubeService.class);

        launchSearch("ella mai");

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 0) {
                    launchSearch(s.toString());
                } else {
                    launchSearch("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
            });
        return youtube_view;
    }

    private void launchSearch(String query) {
        service.search(query, API_KEY).enqueue(new Callback<YouTubeSearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<YouTubeSearchResponse> call, @NonNull Response<YouTubeSearchResponse> response) {
                Log.d(TAG, "onResponse");
                if (response.isSuccessful()) {
                    YouTubeSearchResponse youTubeSearchResponse = response.body();
                    List<YouTubeSearchItem> itemList = youTubeSearchResponse.getItems();
                    recyclerView.setAdapter(new YouTubeSearchItemAdapter(itemList));

                }
            }

            @Override
            public void onFailure(Call<YouTubeSearchResponse> call, Throwable t) {
                Log.e(TAG, "onFailure", t);
            }
        });
    }

}
