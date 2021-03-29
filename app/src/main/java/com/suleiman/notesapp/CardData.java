package com.suleiman.notesapp;


public class CardData {
    private String mTitle;
    private String mDate;

    public CardData(String title, String date) {
        mTitle = title;
        mDate = date;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDate() {
        return mDate;
    }
}
