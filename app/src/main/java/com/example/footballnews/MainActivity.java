package com.example.footballnews;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Report>> {
    private static final int NEWS_LOADER_ID = 1;
    //Url which get
    /*
               Football News
               from [22-2-2019] to [22-3-2019]
               with key[ df6f2d58-6bcb-4a37-9dbd-0dfb0365defe ]
     */
    private static final String FOOTBALL_URL =
            "https://content.guardianapis.com/search?from-date=2019-02-22&to-date=2019-03-22&order-by=newest&show-tags=contributor&show-references=author&q=football&api-key=df6f2d58-6bcb-4a37-9dbd-0dfb0365defe";
    private FootballAdapter fotAdapter;
    private TextView noInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        noInternet = findViewById(R.id.noResult);
        listView.setEmptyView(noInternet);

        fotAdapter = new FootballAdapter(this, new ArrayList<Report>());
        listView.setAdapter(fotAdapter);


        //on click on listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Report currentReport = fotAdapter.getItem(position);
                Uri newsUri = Uri.parse(currentReport.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, newsUri);
                //open Report
                startActivity(intent);


            }
        });
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo Info = connectivityManager.getActiveNetworkInfo();
        if (Info != null && Info.isConnected()) {
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.progressBar);
            loadingIndicator.setVisibility(View.GONE);
            noInternet.setText(R.string.noInternet);
        }
    }

    @NonNull
    @Override
    public Loader<List<Report>> onCreateLoader(int id, @Nullable Bundle args) {
        return new ReportLoader(this, FOOTBALL_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Report>> loader, List<Report> data) {
        View loadingBar = findViewById(R.id.progressBar);
        loadingBar.setVisibility(View.GONE);
        noInternet.setText(R.string.no_result);
        fotAdapter.clear();

        if (data != null && !data.isEmpty()) {
            fotAdapter.addAll(data);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Report>> loader) {
        fotAdapter.clear();

    }
}
