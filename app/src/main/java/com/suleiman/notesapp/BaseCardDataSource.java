package com.suleiman.notesapp;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public abstract class BaseCardDataSource implements CardDataSource {
    private HashSet<CardDataSourceListener> mListeners = new HashSet<>();
    protected final LinkedList<CardData> mData = new LinkedList<>();

    public void addCardDataSourceListener(CardDataSourceListener listener) {
        mListeners.add(listener);
    }

    public void removeCardDataSourceListener(CardDataSourceListener listener) {
        mListeners.remove(listener);
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
        int idx = mData.size() - 1;
        for (CardDataSourceListener listener : mListeners) {
            listener.onItemAdded(idx);
        }
    }

    @Override
    public void remove(int position) {
        mData.remove(position);
        for (CardDataSourceListener listener : mListeners) {
            listener.onItemRemoved(position);
        }
    }

    @Override
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public void update(CardData data) {
        String id = data.getId();
        if (id != null) {
            int idx = 0;
            for (CardData cardData : mData) {
                if (id.equals(cardData.getId())) {
                    cardData.setTitle(data.getTitle());
                    notifyUpdate(idx);
                    return;
                }
                idx++;
            }
        }
        add(data);
    }


    protected final void notifyUpdate(int idx) {
        for (CardDataSourceListener listener : mListeners) {
            listener.onItemUpdated(idx);
        }
    }

    protected final void notifyDataSetChanged() {
        for (CardDataSourceListener listener : mListeners) {
            listener.onDataSetChanged();
        }
    }
}
