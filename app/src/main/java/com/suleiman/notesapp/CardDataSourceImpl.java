package com.suleiman.notesapp;

import android.content.res.Resources;

import java.util.LinkedList;
import java.util.List;

public class CardDataSourceImpl implements CardDataSource {
    private volatile static CardDataSourceImpl sInstance;
    private final LinkedList<CardData> mData = new LinkedList<>();

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
    }

    @Override
    public List<CardData> getCardData() {
        return mData;
    }

    @Override
    public CardData getItemAt(int index) {
        return mData.get(index);
    }

    @Override
    public int getItemsCount() {
        return mData.size();
    }

    @Override
    public void add(CardData cardData) {
        mData.add(cardData);
    }

    @Override
    public void remove(int position) {
        mData.remove(position);
    }

    @Override
    public void clear() {
        mData.clear();
    }

}
