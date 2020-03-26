package com.felix.application.adapters;

import android.app.DownloadManager;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.felix.application.DetailActivity;
import com.felix.application.HomeActivity;
import com.felix.application.NewsWeb;
import com.felix.application.R;
import com.felix.application.VideoActivity;
import com.felix.application.fragment.DownloadFragment;

import java.util.ArrayList;

public class AdapterDownload extends RecyclerView.Adapter<AdapterDownload.downloadView> {
    ArrayList<String> arrayList;
    private AdapterNews.OnItemClickListener onItemClickListener;
    public AdapterDownload(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public downloadView onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.download_item,viewGroup,false);
        return new downloadView(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull downloadView holder, int position) {
        holder.dl_title.setText(arrayList.get(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoActivity.start(v.getContext(), "test");
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class downloadView extends RecyclerView.ViewHolder implements View.OnClickListener {

        AdapterNews.OnItemClickListener onItemClickListener;
        TextView dl_title;
        CardView cardView;

        public downloadView(@NonNull View itemView, AdapterNews.OnItemClickListener onItemClickListener) {

            super(itemView);
            dl_title = itemView.findViewById(R.id.dl_title);
            cardView = itemView.findViewById(R.id.cardView2);
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v,getAdapterPosition());
        }




    }


}
