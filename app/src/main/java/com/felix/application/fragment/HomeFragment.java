package com.felix.application.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.felix.application.HomeActivity;
import com.felix.application.MainActivity;
import com.felix.application.R;
import com.felix.application.Utils;
import com.felix.application.adapters.AdapterNews;
import com.felix.application.api.ApiNews;
import com.felix.application.api.ApiNewsInterface;
import com.felix.application.models.Article;
import com.felix.application.models.News;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    public static final String  API_KEY = "6672684af0cf4d53a250bd29817471c2";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles = new ArrayList<>();
    private AdapterNews adapterNews;
    private String TAG = HomeFragment.class.getSimpleName();




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View home_view = inflater.inflate(R.layout.home_fragment,container,false);

        recyclerView = (RecyclerView) home_view.findViewById(R.id.recyclerView_home);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        LoadJson();

        return home_view;
    }

    public void LoadJson(){
        ApiNewsInterface apiNewsInterface= ApiNews.getApiClient().create(ApiNewsInterface.class);
        Call<News> call;

        call = apiNewsInterface.getNews("r&b",API_KEY);

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if(response.isSuccessful()&&response.body().getArticle()!=null) {
                    if (!articles.isEmpty()) {
                        articles.clear();
                    }
                    articles = response.body().getArticle();
                    adapterNews = new AdapterNews(articles, getContext());
                    recyclerView.setAdapter(adapterNews);
                    adapterNews.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(getContext(),"No Result", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });
    }
}
