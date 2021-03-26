package com.suleiman.notesapp;

import java.util.List;

public interface CardDataSource {
    List<CardData> getCardData();
    CardData getItemAt(int index);
    int getItemsCount();
}
