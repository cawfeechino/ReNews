package com.example.rkjc.news_app_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class NewsRecyclerViewAdapter  extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {
    List<NewsItem> newsList;
    Context mContext;
    private final LayoutInflater mInflator;
    private NewsItemViewModel viewModel;

    public class NewsViewHolder extends RecyclerView.ViewHolder{

        public ImageView image;
        public TextView title;
        public TextView description;

        public NewsViewHolder(View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
        }

        void bind(final int newsIndex){
            Picasso.get().load(Uri.parse(newsList.get(newsIndex).getUrlToImage())).into(image);
            title.setText(newsList.get(newsIndex).getTitle());
            description.setText(newsList.get(newsIndex).getPublishedAt() + ". " + newsList.get(newsIndex).getDescription());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(newsList.get(newsIndex).getUrl()));
                    mContext.startActivity(intent);
                }
            });
        }
    }

    public NewsRecyclerViewAdapter(Context mContext, NewsItemViewModel viewModel){
        this.viewModel = viewModel;
        this.mContext = mContext;
        mInflator = LayoutInflater.from(mContext);
    }

    void setNewsItems(List<NewsItem> newsItems){
        newsList = newsItems;
        notifyDataSetChanged();
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(R.layout.news_item, parent, shouldAttachToParentImmediately);
        NewsViewHolder viewHolder = new NewsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsRecyclerViewAdapter.NewsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

}
