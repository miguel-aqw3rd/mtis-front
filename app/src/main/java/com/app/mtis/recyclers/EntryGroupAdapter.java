package com.app.mtis.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mtis.R;
import com.app.mtis.models.EntryGroupAbridged;
import com.app.mtis.requestAPI.VolleyBall;

import java.util.ArrayList;

public class EntryGroupAdapter extends RecyclerView.Adapter<EntryGroupViewHolder> {
    private ArrayList<EntryGroupAbridged> entryGroups;
    private VolleyBall volleyBall;

    public EntryGroupAdapter(ArrayList<EntryGroupAbridged> entryGroups, VolleyBall volleyBall) {
        this.entryGroups = entryGroups;
        this.volleyBall = volleyBall;
    }

    @NonNull
    @Override
    public EntryGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View cellView = inflater.inflate(R.layout.recyclercell_entrygroup, parent, false);
        return new EntryGroupViewHolder(cellView);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryGroupViewHolder holder, int position) {
        EntryGroupAbridged entryGroup = this.entryGroups.get(position);
        holder.bindData(entryGroup);

    }

    @Override
    public int getItemCount() {
        return this.entryGroups.size();
    }
}
