package com.suleiman.notesapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    public final TextView titleText;
    public final TextView dateText;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        titleText = itemView.findViewById(R.id.title_text);
        dateText = itemView.findViewById(R.id.date_text);
    }

    public void populate(ListFragment fragment, CardData cardData) {
        titleText.setText(cardData.getTitle());
        dateText.setText(cardData.getDate());
        itemView.setOnLongClickListener(v -> {
            fragment.setLastSelectedPosition(getLayoutPosition());
            return false;
        });
        fragment.registerForContextMenu(itemView);
    }

    public void clear(Fragment fragment) {
        itemView.setOnLongClickListener(null);
        fragment.unregisterForContextMenu(itemView);
    }
}
