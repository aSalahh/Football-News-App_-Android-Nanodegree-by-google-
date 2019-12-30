package com.example.footballnews;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class ReportLoader extends AsyncTaskLoader<List<Report>> {
    private String URL;

    public ReportLoader(Context context, String url) {
        super(context);
        URL = url;
    }

    @Nullable
    @Override
    public List<Report> loadInBackground() {
        if (URL == null) {
            return null;
        }
        List<Report> report = Utils.newReports(URL);
        return report;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
