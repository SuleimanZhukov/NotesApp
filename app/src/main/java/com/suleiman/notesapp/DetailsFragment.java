package com.suleiman.notesapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class DetailsFragment extends Fragment {

    private static final String ARG_DETAILS = "DetailsFragment.details";

    private String mName;
    private String mDetails;
    private String mDate;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(Data data) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_DETAILS, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mName = getArguments().getParcelable(ARG_DETAILS);
            mDetails = getArguments().getParcelable(ARG_DETAILS);
            mDate = getArguments().getParcelable(ARG_DETAILS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    public void setSelectedItem(String data) {
        EditText editText = (EditText) getView().findViewById(R.id.main_title);
        editText.setText(data);
    }
}