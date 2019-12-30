package com.example.footballnews;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Utils {

    public static List<Report> newReports(String requestUrl) {
        URL url = UrlCreater(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = RequestHTTPMaker(url);
        } catch (IOException d) {
            Log.e(Utils.class.getSimpleName(), "some Problem in request.", d);
        }
        List<Report> reports = extractElementFromJson(jsonResponse);
        // Return the list of {@link Report}
        return reports;
    }


    private static URL UrlCreater(String Url) {
        URL mUrl = null;
        try {
            mUrl = new URL(Url);
        } catch (MalformedURLException e) {
            Log.e(Utils.class.getSimpleName(), "error in create URL ", e);
        }
        return mUrl;
    }


    private static String RequestHTTPMaker(URL url) throws IOException {
        String response = " ";
        if (url == null) {
            return response;
        }

        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == 200) {
                inputStream = connection.getInputStream();
                response = readFromStream(inputStream);
            } else {
                Log.d(Utils.class.getSimpleName(), "some problem in response code: " + connection.getResponseCode());
            }
        } catch (IOException d) {
            Log.d(Utils.class.getSimpleName(), "Problem in  the report JSON results.", d);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return response;
    }

    private static String readFromStream(InputStream input) throws IOException {
        StringBuilder output = new StringBuilder();
        if (input != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(input, Charset.forName("UTF-8"));
            BufferedReader bu = new BufferedReader(inputStreamReader);
            String current = bu.readLine();
            while (current != null) {
                output.append(current);
                current = bu.readLine();
            }
        }
        return output.toString();
    }

    private static List<Report> extractElementFromJson(String reportJSON) {
        if (TextUtils.isEmpty(reportJSON)) {
            return null;
        }
        List<Report> playList = new ArrayList<>();

        try {

            JSONObject jsonObject = new JSONObject(reportJSON);

            JSONObject jsonResults = jsonObject.getJSONObject("response");
            JSONArray resultsArray = jsonResults.getJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject currentReport = resultsArray.getJSONObject(i);
                String section = currentReport.getString("sectionName");

                String title = currentReport.getString("webTitle");

                String date = currentReport.getString("webPublicationDate");
                String Formateddate = formatDate(date);

                String urlForReport = currentReport.getString("webUrl");
                String writer = "";
                JSONArray tagsArray = currentReport.getJSONArray("tags");
                if (tagsArray.length() > 0) {
                    JSONObject currentTagArray = tagsArray.getJSONObject(0);
                    writer = currentTagArray.optString("webTitle");
                }

                Report news = new Report(title, section, Formateddate, urlForReport, writer);
                playList.add(news);

            }

        } catch (JSONException d) {
            Log.e("Utils class", "Problem in  JSON results", d);
        }

        return playList;
    }


    private static String formatDate(String originalDate) {
        String jsonDate = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat jsonDateFormatter = new SimpleDateFormat(jsonDate, Locale.US);
        try {
            Date JsonDate = jsonDateFormatter.parse(originalDate);
            String finalresult = "MMM d, yyy";
            SimpleDateFormat finalDateFormatter = new SimpleDateFormat(finalresult, Locale.US);
            return finalDateFormatter.format(JsonDate);
        } catch (ParseException d) {
            Log.d("Utils File", "some problem in format json date ", d);
            return " ";
        }
    }
}
