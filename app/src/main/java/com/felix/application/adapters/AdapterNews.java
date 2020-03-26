package com.felix.application.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.felix.application.NewsWeb;
import com.felix.application.R;
import com.felix.application.Utils;
import com.felix.application.models.Article;

import java.util.List;

public class AdapterNews extends RecyclerView.Adapter<AdapterNews.MyViewHolder> {

    private List<Article> articles;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public AdapterNews(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item,parent,false);
        return new MyViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders, int position) {
        final MyViewHolder holder = holders;
        final Article model = articles.get(position);

        RequestOptions requestOptions = new RequestOptions();
        RequestOptions.placeholderOf(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        holder.title.setText(model.getTitle());
        holder.desc.setText(model.getDescription());
        holder.author.setText(model.getAuthor());
        holder.published.setText(model.getPublishedAt().substring(0,10));
        holder.source.setText(model.getSource().getName());
        Glide.with(context).load(model.getUrlToImage()).apply(requestOptions).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /*Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getUrl()));
               context.startActivity(intent);*/
                NewsWeb.start(v.getContext(),model.getUrl());
            }
        });

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title, author, desc, published, source;
        ImageView imageView;
        CardView cardView;
        OnItemClickListener onItemClickListener;

        public MyViewHolder(View itemView, OnItemClickListener onItemClickListener){
            super(itemView);

            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            source = itemView.findViewById(R.id.source);
            author = itemView.findViewById(R.id.author);
            published = itemView.findViewById(R.id.published);
            imageView = itemView.findViewById(R.id.img);
            cardView = itemView.findViewById(R.id.cardView);
            this.onItemClickListener = onItemClickListener;

        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v,getAdapterPosition());
        }
    }

}
