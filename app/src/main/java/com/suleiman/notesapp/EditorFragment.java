package com.suleiman.notesapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class EditorFragment extends Fragment {

    private static final String ARG_EDITOR = "EditorFragment.index";

    private int mCurrentItemIdx = -1;

    public EditorFragment() {
        // Required empty public constructor
    }

    public static EditorFragment newInstance(int currentItemIdx) {
        EditorFragment fragment = new EditorFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_EDITOR, currentItemIdx);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCurrentItemIdx = getArguments().getInt(ARG_EDITOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editor, container, false);

        CardDataSource dataSource = CardDataSourceImpl.getInstance(getResources());
        CardData cardData = dataSource.getItemAt(mCurrentItemIdx);

        final TextInputEditText editText = view.findViewById(R.id.title_text_edit);
        final MaterialButton btnSave = view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener((v) -> {
            if (editText.getText() != null) {
                cardData.setTitle(editText.getText().toString());
            }
            getChildFragmentManager().popBackStack();
        });
        return view;
    }
}