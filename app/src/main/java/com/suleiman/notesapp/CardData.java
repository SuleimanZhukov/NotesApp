package com.suleiman.notesapp;


public class CardData {
    private String mId;
    private String mTitle;
    private String mDate;

    public CardData(String title, String date) {
        mTitle = title;
        mDate = date;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
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
