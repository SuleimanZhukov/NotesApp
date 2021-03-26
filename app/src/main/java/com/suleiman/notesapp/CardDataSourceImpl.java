package com.suleiman.notesapp;

import android.content.res.Resources;

import java.util.LinkedList;
import java.util.List;

public class CardDataSourceImpl implements CardDataSource {

    private final LinkedList<CardData> mData = new LinkedList<>();

    public CardDataSourceImpl(Resources resources) {
        String[] titles = resources.getStringArray(R.array.notes);
        String[] dates = resources.getStringArray(R.array.dates);

        for (int i = 0; i < titles.length; i++) {
            mData.add(new CardData(titles[i], dates[i]));
        }
    }

    @Override
    public List<CardData> getCardData() {
        return mData;
    }
}
