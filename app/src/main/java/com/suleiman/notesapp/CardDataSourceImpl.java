package com.suleiman.notesapp;

import android.content.res.Resources;

import java.util.LinkedList;
import java.util.List;

public class CardDataSourceImpl extends BaseCardDataSource {
    private volatile static CardDataSourceImpl sInstance;

    public static CardDataSourceImpl getInstance(Resources resources) {
        CardDataSourceImpl instance = sInstance;
        if (instance == null) {
            synchronized (CardDataSourceImpl.class) {
                if (sInstance == null) {
                    instance = new CardDataSourceImpl(resources);
                    sInstance = instance;
                }
            }
        }
        return instance;
    }

    public CardDataSourceImpl(Resources resources) {
        String[] titles = resources.getStringArray(R.array.notes);
        String[] dates = resources.getStringArray(R.array.dates);

        for (int i = 0; i < titles.length; i++) {
            mData.add(new CardData(titles[i], dates[i]));
        }
        notifyDataSetChanged();
    }
}
