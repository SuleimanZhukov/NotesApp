package com.suleiman.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ListFragment.onFragmentSendDataListener {

    private Publisher publisher = new Publisher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            Fragment listFragment = new ListFragment();
            createAddFragment(listFragment);
        }
    }

    @Override
    public void onSendData(String data) {
        DetailsFragment detailsFragment = new DetailsFragment();
        createAddFragment(detailsFragment);
        detailsFragment.setSelectedItem(data);
    }

    private void createAddFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, fragment);
        transaction.setReorderingAllowed(true);
        transaction.commit();
    }
}