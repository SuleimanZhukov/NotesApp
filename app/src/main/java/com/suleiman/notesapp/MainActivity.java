package com.suleiman.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements ListFragment.onFragmentSendDataListener {

    private static final String BACK_TAG = "BACK_TAG";
    private Publisher publisher = new Publisher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            Fragment listFragment = new ListFragment();
            createAddFragment(listFragment);
        }

        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);
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
        transaction.addToBackStack(BACK_TAG);
        transaction.commit();
    }

    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        android.util.Log.v("TAG", "new ActionBarDrawerToggle");
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Обработка навигационного меню
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (navigateFragment(id)){
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
                return false;
            }
        });
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    private boolean navigateFragment(int id) {
        switch (id) {
            case R.id.action_settings: {
                SettingsFragment settingsFragment = new SettingsFragment();
                createAddFragment(settingsFragment);
                return true;
            }
            case R.id.action_about: {
                AboutFragment aboutFragment = new AboutFragment();
                createAddFragment(aboutFragment);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}