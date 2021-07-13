package com.example.newsforest;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;

public class NewsLoader extends AsyncTaskLoader<ArrayList<News>> {

    private String url;

    public NewsLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        System.out.println("on start loading called");
        forceLoad();
    }

    @Override
    public void deliverResult(@Nullable ArrayList<News> data) {
        super.deliverResult(data);
        System.out.println("on delivery result called");
    }

    @Nullable
    @Override
    public ArrayList<News> loadInBackground() {
        System.out.println("on load in background");
        ArrayList<News> result = QueryUtils.fetchNewsData(url);
        return result;
    }
}
