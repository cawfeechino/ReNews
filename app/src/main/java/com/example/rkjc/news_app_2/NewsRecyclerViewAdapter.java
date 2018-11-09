package com.example.rkjc.news_app_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class NewsRecyclerViewAdapter  extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {
    ArrayList<NewsItem> newsList;
    Context mContext;

    public class NewsViewHolder extends RecyclerView.ViewHolder{
        public TextView author;
        public TextView title;
        public TextView description;
        public TextView url;
        public TextView urlToImage;
        public TextView publishedAt;

        public NewsViewHolder(View itemView){
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.author);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            url = (TextView) itemView.findViewById(R.id.url);
            urlToImage = (TextView) itemView.findViewById(R.id.urlToImage);
            publishedAt = (TextView) itemView.findViewById(R.id.publishedAt);
        }

        void bind(final int newsIndex){
            author.setText(newsList.get(newsIndex).getAuthor());
            title.setText(newsList.get(newsIndex).getTitle());
            description.setText(newsList.get(newsIndex).getDescription());
            url.setText(newsList.get(newsIndex).getUrl());
            urlToImage.setText(newsList.get(newsIndex).getUrlToImage());
            publishedAt.setText(newsList.get(newsIndex).getPublishedAt());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url.getText().toString()));
                    mContext.startActivity(intent);
                }
            });
        }
    }

    public NewsRecyclerViewAdapter(Context mContext, ArrayList<NewsItem> newsList){
        this.newsList = newsList;
        this.mContext = mContext;
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
