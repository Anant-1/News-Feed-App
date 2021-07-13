package com.example.newsforest;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public final class QueryUtils {

    private QueryUtils() {
    }

    private static URL createURL(String stringUrl) {
        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            System.out.println("Problem in building the url");
        }

        return url;
    }

    public static ArrayList<News> fetchNewsData(String requestUrl) {

//        System.out.println("fetch news data called");
//
//        System.out.println("request url "+requestUrl);

        URL url = createURL(requestUrl);

        //System.out.println("url after call create url" + url.toString() + " in fetch news data");

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            System.out.println("Error in making http request");
        }

        ArrayList<News> newsList = extractFeaturesFromJson(jsonResponse);

        return newsList;


    }

    private static String makeHttpRequest(URL url) throws IOException{

        String jsonResponse = "";
        HttpURLConnection hp = null;
        InputStream inputStream = null;

        //System.out.println("url after call create url" + url.toString() + " in make http request");

        try {
            hp = (HttpURLConnection) url.openConnection();
            hp.setReadTimeout(10000);
            hp.setConnectTimeout(15000);
            hp.setRequestMethod("GET");

            if(hp.getResponseCode() == 200) {
                //System.out.println("Response code : " + hp.getResponseCode());
                hp.connect();
                inputStream = hp.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else {
                System.out.println("error in making request : " + hp.getResponseCode());
            }
        } catch (IOException e) {
            System.out.println("Error" + e);
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {

        //System.out.println("read from stream call");

        StringBuilder output = new StringBuilder();
        if(inputStream != null) {
          //  System.out.println("hi");
            InputStream in;
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader br = new BufferedReader(inputStreamReader);

            String line = null;
            line = br.readLine();

            if(line != null) {
                output.append(line);
                line = br.readLine();
            }
        }else {
            System.out.println("inputstream is null");
        }

        return output.toString();
    }

    private static ArrayList<News> extractFeaturesFromJson(String jsonResponse) {

        if(TextUtils.isEmpty(jsonResponse)) {
            System.out.println("Json response is empty");
            return null;
        }

        ArrayList<News> newsList = new ArrayList<News>();

        try {
            JSONObject rootObject = new JSONObject(jsonResponse);
            JSONArray articlesArray = rootObject.optJSONArray("articles");

            for(int i = 0; i < articlesArray.length(); i++) {
                JSONObject currentArticle = articlesArray.getJSONObject(i);
                String authorName = currentArticle.getString("author");
                String title = currentArticle.getString("title");
                String imageUrl = currentArticle.getString("urlToImage");
                String webUrl = currentArticle.getString("url");

                newsList.add(new News(title, authorName, imageUrl, webUrl));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsList;
    }
}
