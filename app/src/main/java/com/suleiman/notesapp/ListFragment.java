package com.suleiman.notesapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        recyclerView.setLayoutManager(layoutManager);

        ViewHolderAdapter viewHolderAdapter = new ViewHolderAdapter(inflater, new CardDataSourceImpl(getResources()));
        viewHolderAdapter.setOnClickListener((view, position) -> {
            clickAction();
        });

        recyclerView.setAdapter(viewHolderAdapter);

        return recyclerView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardView card = view.findViewById(R.id.card_view);

    }

    private void clickAction() {
        DetailsFragment detailsFragment = new DetailsFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, detailsFragment);
        transaction.setReorderingAllowed(true);
        transaction.addToBackStack(BACK_STACK);
        transaction.commit();
    }

    private View.OnClickListener clickListener = (view) -> {
        clickAction();
    };

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_menu, menu);
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView titleText;
        public final TextView dateText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.title_text);
            dateText = itemView.findViewById(R.id.date_text);
        }

        public void populate(CardData cardData) {
            titleText.setText(cardData.title);
            dateText.setText(cardData.date);
        }
    }

    private interface OnClickListener {
        void onItemClick(View view, int position);
    }

    private static class ViewHolderAdapter extends RecyclerView.Adapter<ViewHolder> {
        private final LayoutInflater mInflater;
        private final CardDataSource mDataSource;

        private OnClickListener mOnClickListener;

        public ViewHolderAdapter(LayoutInflater inflater, CardDataSource dataSource) {
            mInflater = inflater;
            mDataSource = dataSource;
        }

        public void setOnClickListener(OnClickListener onClickListener) {
            mOnClickListener = onClickListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.card_view, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            CardData cardData = mDataSource.getItemAt(position);
            holder.populate(cardData);
            holder.itemView.setOnClickListener((view) -> {
                if (mOnClickListener != null) {
                    mOnClickListener.onItemClick(view, position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDataSource.getItemsCount();
        }
    }
}