package com.felix.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;


import com.felix.application.fragment.DownloadFragment;
import com.felix.application.fragment.HomeFragment;
import com.felix.application.fragment.PlaylistFragment;
import com.felix.application.fragment.YoutubeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    final Fragment home_frag = new HomeFragment();
    final Fragment youtube_frag = new YoutubeFragment();
    final Fragment download_frag = new DownloadFragment();
    final Fragment playlist_frag = new PlaylistFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = home_frag;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fm.beginTransaction().add(R.id.fragmentContainer, download_frag, "3").hide(download_frag).commit();
        fm.beginTransaction().add(R.id.fragmentContainer, playlist_frag, "3").hide(playlist_frag).commit();
        fm.beginTransaction().add(R.id.fragmentContainer, youtube_frag, "2").hide(youtube_frag).commit();
        fm.beginTransaction().add(R.id.fragmentContainer,home_frag, "1").commit();


        bottomNavigationView = findViewById(R.id.bottomNavig);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()){
                    case R.id.home:
                        fm.beginTransaction().hide(active).show(home_frag).commit();
                        active = home_frag;
                        break;
                    case R.id.youtube:
                        fm.beginTransaction().hide(active).show(youtube_frag).commit();
                        active = youtube_frag;
                        break;
                    case R.id.concert:
                        fm.beginTransaction().hide(active).show(download_frag).commit();
                        active = download_frag;
                        break;
                    case R.id.playlist:
                        fm.beginTransaction().hide(active).show(playlist_frag).commit();
                        active = playlist_frag;
                        break;
                }
                return true;
            }

        });

    }


    @Override
    public void onBackPressed() {
       //super.onBackPressed();
    }


}



