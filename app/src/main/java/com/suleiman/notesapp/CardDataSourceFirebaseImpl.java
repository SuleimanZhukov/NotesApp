package com.suleiman.notesapp;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;

public class CardDataSourceFirebaseImpl extends BaseCardDataSource {
    private static final String COLLECTION_NOTES = "com.suleiman.CollectionNotes";
    private static final String TAG = "onFetchFailed";
    private volatile static CardDataSourceFirebaseImpl sInstance;

    private final FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private final CollectionReference mCollection = mStore.collection(COLLECTION_NOTES);

    public static CardDataSourceFirebaseImpl getInstance() {
        CardDataSourceFirebaseImpl instance = sInstance;
        if (instance == null) {
            synchronized (CardDataSourceFirebaseImpl.class) {
                if (sInstance == null) {
                    instance = new CardDataSourceFirebaseImpl();
                    sInstance = instance;
                }
            }
        }
        return instance;
    }

    public CardDataSourceFirebaseImpl() {
        mCollection.orderBy("Date", Query.Direction.DESCENDING).get().
                addOnCompleteListener(this::onFetchComplete).
                addOnFailureListener(this::onFetchFailed);
    }

    private void onFetchComplete(Task<QuerySnapshot> task) {
        if (task.isSuccessful()) {
            LinkedList<CardData> data = new LinkedList<>();
            for (QueryDocumentSnapshot document : task.getResult()) {
                data.add(new CardDataFromFirebase(document.getId(), document.getData()));
            }
            mData.clear();
            mData.addAll(data);
            data.clear();
            notifyDataSetChanged();
        }
    }

    private void onFetchFailed(Exception e) {
        android.util.Log.e(TAG, "onFetchFailed", e);
    }

    @Override
    public void add(CardData data) {
        final CardDataFromFirebase cardData;
        if (data instanceof CardDataFromFirebase) {
            cardData = (CardDataFromFirebase) data;
        } else {
            cardData = new CardDataFromFirebase(data);
        }
        mData.add(cardData);
        mCollection.add(cardData.getFields()).addOnSuccessListener(documentReference -> {
            cardData.setId(documentReference.getId());
        });
    }

    @Override
    public void remove(int position) {
        String id = mData.get(position).getId();
        mCollection.document(id).delete();
        super.remove(position);
    }

    @Override
    public void clear() {
        for (CardData cardData : mData) {
            mCollection.document(cardData.getId()).delete();
        }
        super.clear();
    }

}

