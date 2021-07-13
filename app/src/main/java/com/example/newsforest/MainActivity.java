package com.example.newsforest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>> {

    private static String NEWS_URL = "https://newsapi.org/v2/top-headlines?country=us&category=sports&apiKey=7b289db234b64dca86576c4b594b5614";
    private NewsAdapter mNewsAdapter;
    private ProgressBar mProgressBar;
    private TextView mEmptyTextView;
    private final int LOADER_ID = 1;
    private String category = "sports";
    private String country = "us";
    private SharedPreferences shrd;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("on create method called");

        ListView listView = (ListView) findViewById(R.id.list_view);

        mNewsAdapter = new NewsAdapter(this, new ArrayList<News>());
        listView.setAdapter(mNewsAdapter);



        mEmptyTextView = (TextView) findViewById(R.id.empty_text_view);
        listView.setEmptyView(mEmptyTextView);

        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh_layout);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doMyUpdate();
                refreshLayout.setRefreshing(false);
            }
        });

        LoaderManager loaderManager = getSupportLoaderManager();

        shrd = getSharedPreferences("demo", MODE_PRIVATE);

        Button healthButton = (Button) findViewById(R.id.health_btn);
        healthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //category = "health";

                SharedPreferences.Editor editor = shrd.edit();
                editor.putString("category", "health");
                editor.apply();
                Toast.makeText(MainActivity.this, "Health", Toast.LENGTH_SHORT).show();
                loaderManager.restartLoader(LOADER_ID, null, MainActivity.this);
            }
        });

        Button businessButton = (Button) findViewById(R.id.buisness_btn);
        businessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //category = "business";

                SharedPreferences.Editor editor = shrd.edit();
                editor.putString("category", "business");
                editor.apply();
                Toast.makeText(MainActivity.this, "Business", Toast.LENGTH_LONG).show();
                loaderManager.restartLoader(LOADER_ID, null, MainActivity.this);
            }
        });

        Button generalButton = (Button) findViewById(R.id.general_btn);
        generalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //category = "general";
                SharedPreferences.Editor editor = shrd.edit();
                editor.putString("category", "general");
                editor.apply();
                Toast.makeText(MainActivity.this, "General", Toast.LENGTH_SHORT).show();
                loaderManager.restartLoader(LOADER_ID, null, MainActivity.this);

            }
        });

        Button entertainmentButton = (Button) findViewById(R.id.entertainment_btn);
        entertainmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //category = "entertainment";
                SharedPreferences.Editor editor = shrd.edit();
                editor.putString("category", "entertainment");
                editor.apply();
                Toast.makeText(MainActivity.this, "Entertainment", Toast.LENGTH_SHORT).show();
                loaderManager.restartLoader(LOADER_ID, null, MainActivity.this);
            }
        });

        Button scienceButton = (Button) findViewById(R.id.science_btn);
        scienceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //category = "science";
                SharedPreferences.Editor editor = shrd.edit();
                editor.putString("category", "science");
                editor.apply();
                Toast.makeText(MainActivity.this, "Science", Toast.LENGTH_SHORT).show();
                loaderManager.restartLoader(LOADER_ID, null, MainActivity.this);
            }
        });

        Button sportsButton = (Button) findViewById(R.id.sports_btn);
        sportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //category = "sports";
                SharedPreferences.Editor editor = shrd.edit();
                editor.putString("category", "sports");
                editor.apply();
                Toast.makeText(MainActivity.this, "Sports", Toast.LENGTH_SHORT).show();
                loaderManager.restartLoader(LOADER_ID, null, MainActivity.this);
            }
        });

        Button technologyButton = (Button) findViewById(R.id.technology_btn);
        technologyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //category = "technology";
                SharedPreferences.Editor editor = shrd.edit();
                editor.putString("category", "technology");
                editor.apply();
                Toast.makeText(MainActivity.this, "Technology", Toast.LENGTH_SHORT).show();
                loaderManager.restartLoader(LOADER_ID, null, MainActivity.this);
                //loaderManager.initLoader(LOADER_ID, null, MainActivity.this);

            }
        });

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()) {
            System.out.println("init loader method called");

            loaderManager.initLoader(LOADER_ID, null, this);
        }else {
            mProgressBar = (ProgressBar)findViewById(R.id.progress_bar);
            mProgressBar.setVisibility(View.GONE);
            mEmptyTextView.setText("No Internet Connection");
        }




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News curenntNews = mNewsAdapter.getItem(i);
                String url = curenntNews.getWebUrl();
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(MainActivity.this, Uri.parse(url));
            }
        });


    }

    private void doMyUpdate() {
        System.out.println("do my update called");
        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, @Nullable Bundle args) {

        System.out.println("On create loader called");

        Context context;
        shrd = getSharedPreferences("demo", MODE_PRIVATE);
        category = shrd.getString("category", "health");


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        country = sharedPreferences.getString(getString(R.string.country_key), getString(R.string.country_default));
        System.out.println("Category -> " + category + "Country - > " + country);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(getString(R.string.newsapi))
                .appendPath("NewsAPI")
                .appendPath("top-headlines").appendPath("category").appendPath(category).appendPath(country+".json");
        final String NEWS_URL = builder.build().toString();

        System.out.println("News url : " + NEWS_URL);

        return new NewsLoader(this, NEWS_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<News>> loader, ArrayList<News> newsArrayList) {

        System.out.println("on load finished called");

        mProgressBar = (ProgressBar)findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.GONE);
        mNewsAdapter.clear();


        if(newsArrayList != null && !newsArrayList.isEmpty()) {
            mNewsAdapter.addAll(newsArrayList);
        }else {
            System.out.println("data not found");
        }
        mEmptyTextView.setText("No News Found");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<News>> loader) {
        System.out.println("on loader reset called");
        mNewsAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_setting) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);


    }

/*    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("on restart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("on pause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("on start");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("on stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("on destroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("on resume");
    }*/
}