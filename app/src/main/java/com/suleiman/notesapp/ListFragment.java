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

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private ViewHolderAdapter mViewHolderAdapter;
    private CardDataSource mCardDataSource;
    private RecyclerView mRecyclerView;
    private int mLastSelectedPosition = -1;

    private String mName;
    private String mDate;

    private Publisher publisher;

    private CardDataSource.CardDataSourceListener mListener = new CardDataSource.CardDataSourceListener() {
        @Override
        public void onItemAdded(int idx) {
            if (mViewHolderAdapter != null) {
                mViewHolderAdapter.notifyItemInserted(idx);
            }
        }

        @Override
        public void onItemRemoved(int idx) {
            if (mViewHolderAdapter != null) {
                mViewHolderAdapter.notifyItemRemoved(idx);
            }
        }

        @Override
        public void onItemUpdated(int idx) {
            if (mViewHolderAdapter != null) {
                mViewHolderAdapter.notifyItemChanged(idx);
            }
        }

        @Override
        public void onDataSetChanged() {
            if (mViewHolderAdapter != null) {
                mViewHolderAdapter.notifyDataSetChanged();
            }
        }
    };

    public ListFragment() {
        // Required empty public constructor
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
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mCardDataSource = CardDataSourceFirebaseImpl.getInstance();
        mViewHolderAdapter = new ViewHolderAdapter(this, mCardDataSource);
        mCardDataSource.addCardDataSourceListener(mListener);
        mViewHolderAdapter.setOnClickListener((view, position) -> {
            clickAction();
        });

        mRecyclerView.setAdapter(mViewHolderAdapter);

        return mRecyclerView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCardDataSource.removeCardDataSourceListener(mListener);
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

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.note_item_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_menu_edit) {
            if (mLastSelectedPosition != -1) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.fragment_container, EditorFragment.newInstance(mLastSelectedPosition));
                transaction.setReorderingAllowed(true);
                transaction.addToBackStack(BACK_STACK);
                transaction.commit();
            }
        } else if (item.getItemId() == R.id.item_menu_delete) {
            if (mLastSelectedPosition != -1) {
                mCardDataSource.remove(mLastSelectedPosition);
                mViewHolderAdapter.notifyItemRemoved(mLastSelectedPosition);
            }
        } else {
            return super.onContextItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.toolbar_menu_add) {
            mCardDataSource.add(new CardData("Untitled", "27/03/2021"));
            int position = mCardDataSource.getItemsCount() - 1;
            mViewHolderAdapter.notifyItemInserted(position);
            mRecyclerView.scrollToPosition(position);
        } else if (item.getItemId() == R.id.toolbar_menu_clear) {
            mCardDataSource.clear();
            mViewHolderAdapter.notifyDataSetChanged();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void setLastSelectedPosition(int lastSelectedPosition) {
        mLastSelectedPosition = lastSelectedPosition;
    }

    public interface OnClickListener {
        void onItemClick(View view, int position);
    }

}