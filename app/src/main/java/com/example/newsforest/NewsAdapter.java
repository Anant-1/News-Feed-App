package com.example.newsforest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News>  {

    public NewsAdapter(Context context, ArrayList<News> newsList) {
        super(context, 0, newsList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        News currentNews = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false);
        }

        TextView author = convertView.findViewById(R.id.author);
        TextView title = convertView.findViewById(R.id.title);
        ImageView image = convertView.findViewById(R.id.image_view);

        if(currentNews.getAuthorName().equals("null")) {
            String authorName = "News Agencies";
            author.setText(authorName);
        }else{
            author.setText(currentNews.getAuthorName());
        }

        Glide.with(getContext()).load(currentNews.getImage()).into(image);
        title.setText(currentNews.getTitle());



        return convertView;
    }

}
