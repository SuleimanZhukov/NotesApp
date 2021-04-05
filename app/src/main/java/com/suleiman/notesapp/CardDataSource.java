package com.suleiman.notesapp;

import java.util.List;

public interface CardDataSource {
    interface CardDataSourceListener {
        void onItemAdded(int idx);
        void onItemRemoved(int idx);
        void onItemUpdated(int idx);
        void onDataSetChanged();
    }

    void addCardDataSourceListener(CardDataSourceListener listener);
    void removeCardDataSourceListener(CardDataSourceListener listener);

    List<CardData> getCardData();
    CardData getItemAt(int index);
    int getItemsCount();

    void add(CardData cardData);
    void update(CardData cardData);
    void remove(int position);
    void clear();
}
