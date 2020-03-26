package com.felix.application.adapters;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.felix.application.DetailActivity;
import com.felix.application.R;
import com.felix.application.models.Snippet;
import com.felix.application.models.YouTubeSearchItem;

import java.util.List;


public class YouTubeSearchItemAdapter extends RecyclerView.Adapter<SearchItemViewHolder> {
	
	private final List<YouTubeSearchItem> items;
	
	public YouTubeSearchItemAdapter(List<YouTubeSearchItem> items) {
		this.items = items;
	}
	
	@NonNull
	@Override
	public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_search_item, viewGroup, false);
		return new SearchItemViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(@NonNull SearchItemViewHolder searchItemViewHolder, int position) {
		searchItemViewHolder.bind(items.get(position));
	}
	
	@Override
	public int getItemCount() {
		return items != null ? items.size() : 0;
	}
}

class SearchItemViewHolder extends RecyclerView.ViewHolder {

	private TextView title;
	private TextView description;
	private ImageView thumbnail;

	public SearchItemViewHolder(@NonNull View itemView) {
		super(itemView);
		this.title = itemView.findViewById(R.id.title_yt);
		this.description = itemView.findViewById(R.id.description_yt);
		this.thumbnail = itemView.findViewById(R.id.thumbnail_yt);
	}

	public void bind(final YouTubeSearchItem youTubeSearchItem) {
		final Snippet snippet = youTubeSearchItem.getSnippet();
		String title_tmp = snippet.getTitle();
		String desc_tmp = snippet.getTitle();
		title.setText(Html.fromHtml(title_tmp));
		description.setText(Html.fromHtml(desc_tmp));

		Glide.with(itemView).load(snippet.getThumbnailUrl()).into(thumbnail);

		itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DetailActivity.start(v.getContext(), youTubeSearchItem.getId().getVideoId(),youTubeSearchItem.getSnippet().getDescription(),youTubeSearchItem.getSnippet().getTitle());
			}
		});
	}
}
