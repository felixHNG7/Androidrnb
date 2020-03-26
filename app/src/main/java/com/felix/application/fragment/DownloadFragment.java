package com.felix.application.fragment;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.felix.application.R;
import com.felix.application.adapters.AdapterDownload;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DownloadFragment extends Fragment {

    ArrayList<String> arrayList;
    ListView listView;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Button refresh;

    ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View download_view= inflater.inflate(R.layout.download_fragment,container,false);
        arrayList = new ArrayList<>();
        recyclerView = download_view.findViewById(R.id.recyclerView_download);
        layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        refresh = download_view.findViewById(R.id.refresh);

        getMusics();
        recyclerView.setAdapter(new AdapterDownload(arrayList));

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList = new ArrayList<>();
                getMusics();
                recyclerView.setAdapter(new AdapterDownload(arrayList));
            }
        });


        doStuff();
        return download_view;
    }

    public void getMusics(){
        ContentResolver contentResolver = getContext().getContentResolver();
        Uri songUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Log.e("uhoo",songUri.toString());
        Cursor songCursor = contentResolver.query(songUri,null,null,null,null);

        if(songCursor != null && songCursor.moveToFirst()){
            int songTitle = songCursor.getColumnIndex( MediaStore.Video.Media.TITLE);

            do{
                String currentTitle = songCursor.getString(songTitle);
                arrayList.add(currentTitle);
            }while(songCursor.moveToNext());
        }
    }

    public void doStuff(){

    }
}

