package com.suleiman.notesapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ListFragment extends Fragment {

    interface onFragmentSendDataListener {
        void onSendData(String data);
    }

    private onFragmentSendDataListener mFragmentSendDataListener;

    private static final String ARG_NOTE = "ListFragment.note";
    private static final String BACK_STACK = "addToBackStack.note";

    private String mName;
    private String mDate;

    private Publisher publisher;

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance(Data data) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mName = getArguments().getParcelable(ARG_NOTE);
            mDate = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentSendDataListener = (onFragmentSendDataListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardView card1 = view.findViewById(R.id.card1);
        CardView card2 = view.findViewById(R.id.card2);
        CardView card3 = view.findViewById(R.id.card3);
        CardView card4 = view.findViewById(R.id.card4);
        CardView card5 = view.findViewById(R.id.card5);
        CardView card6 = view.findViewById(R.id.card6);

        card1.setOnClickListener(clickListener);
        card2.setOnClickListener(clickListener);
        card3.setOnClickListener(clickListener);
        card4.setOnClickListener(clickListener);
        card5.setOnClickListener(clickListener);
        card6.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = (view) -> {
        DetailsFragment detailsFragment = new DetailsFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, detailsFragment);
        transaction.setReorderingAllowed(true);
        transaction.addToBackStack(BACK_STACK);
        transaction.commit();
    };

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_menu, menu);
    }
}