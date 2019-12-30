package com.example.footballnews;

public class Report {
    private String mTitle;
    private String mSection;
    private String mdate;
    private String mWriter;
    private String mURL;

    public Report(String mTitle, String mSection, String mdate,String URL, String mWriter) {
        this.mTitle = mTitle;
        this.mSection = mSection;
        this.mdate = mdate;
        this.mWriter = mWriter;
        this.mURL=URL;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmSection() {
        return mSection;
    }

    public String getmdate() {
        return mdate;
    }

    public String getmWriter() {
        return mWriter;
    }

    public String getUrl() {
        return mURL;
    }
}
