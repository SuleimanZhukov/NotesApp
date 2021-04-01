package com.suleiman.notesapp;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CardDataFromFirebase extends CardData {
    public static final String FIELD_ID = "id";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_DATE = "date";

    public CardDataFromFirebase(String title, String date) {
        super(title, date);
    }

    public CardDataFromFirebase(String id, String title, String date) {
        this(title, date);
        setId(id);
    }

    public CardDataFromFirebase(String id, Map<String, Object> fields) {
        this(id, (String) fields.get(FIELD_TITLE), (String) fields.get(FIELD_DATE));
    }

    public CardDataFromFirebase(CardData data) {
        this(data.getId(), data.getTitle(), data.getDate());
    }

    public Map<String, Object> getFields() {
        HashMap<String, Object>  fields = new HashMap<>();
        fields.put(FIELD_TITLE, getTitle());
        fields.put(FIELD_DATE, getDate());
        return Collections.unmodifiableMap(fields);
    }
}
