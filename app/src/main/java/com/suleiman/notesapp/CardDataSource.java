package com.suleiman.notesapp;

import java.util.List;

public interface CardDataSource {
    List<CardData> getCardData();
    CardData getItemAt(int index);
    int getItemsCount();

    void add(CardData cardData);
    void remove(int position);
    void clear();
}
