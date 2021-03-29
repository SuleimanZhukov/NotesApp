package com.suleiman.notesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final ListFragment mFragment;
    private final LayoutInflater mInflater;
    private final CardDataSource mDataSource;

    private ListFragment.OnClickListener mOnClickListener;

    public ViewHolderAdapter(ListFragment fragment, CardDataSource dataSource) {
        mFragment = fragment;
        mInflater = mFragment.getLayoutInflater();
        mDataSource = dataSource;
    }

    public void setOnClickListener(ListFragment.OnClickListener onClickListener) {
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
        holder.populate(mFragment, cardData);
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
