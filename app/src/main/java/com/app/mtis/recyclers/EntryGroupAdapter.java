package com.app.mtis.recyclers;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.app.mtis.EntryGroupDetailActivity;
import com.app.mtis.R;
import com.app.mtis.custom.ExpandImageView;
import com.app.mtis.custom.FavoriteImageView;
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

        FavoriteImageView favoriteImageView = holder.getFavoriteImageView();
        ExpandImageView expandImageView = holder.getExpandImageView();

        // I presume this doesn't conflict with the onClicks of other views
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int entryGroupId = entryGroup.getId();
                Intent intent = new Intent(v.getContext(), EntryGroupDetailActivity.class);
                intent.putExtra("entryGroupId", entryGroupId);
                v.getContext().startActivity(intent);
            }
        });

        favoriteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volleyBall.putEntryGroupFavorite(entryGroup.getId(), new VolleyBall.VolleyCallback() {
                    @Override
                    public void onSuccess() {
                        //Toggles favorite status locally,
                        entryGroup.setFavorite(!entryGroup.isFavorite());
                        holder.bindData(entryGroup);
                    }
                    @Override
                    public void onError(VolleyError error) {
                        Toast.makeText(holder.itemView.getContext(), "Error: Favorite status couldn't be updated", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        expandImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Expandir el RecyclerView de los EntryGroups hijos
                if(! holder.isExpanded()) {
                    volleyBall.getEntryGroups(-1, -1, entryGroup.getId(), new VolleyBall.VolleyCallback() {
                        @Override
                        public void onSuccess() {
                            ArrayList<EntryGroupAbridged> childEntryGroups = VolleyBall.getEntryGroupsAbridged();
                            holder.setExpanded(true);
                            holder.bindData(entryGroup);
                            holder.setRecyclerView(childEntryGroups, volleyBall);

                        }
                        @Override
                        public void onError(VolleyError error) {
                            ArrayList<EntryGroupAbridged> childEntryGroups = new ArrayList<>(); //Empty ArrayList
                            holder.setExpanded(true);
                            holder.bindData(entryGroup);
                            holder.setRecyclerView(childEntryGroups, volleyBall);
                        }
                    });
                }else{
                    ArrayList<EntryGroupAbridged> childEntryGroups = new ArrayList<>(); //Empty ArrayList
                    holder.setExpanded(false);
                    holder.bindData(entryGroup);
                    holder.setRecyclerView(childEntryGroups, volleyBall);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.entryGroups.size();
    }
}
