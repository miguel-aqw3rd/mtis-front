package com.app.mtis.recyclers;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mtis.EntryGroupDetailActivity;
import com.app.mtis.R;
import com.app.mtis.custom.ExpandImageView;
import com.app.mtis.custom.FavoriteImageView;
import com.app.mtis.models.EntryGroupAbridged;
import com.app.mtis.requestAPI.VolleyBall;

import java.util.ArrayList;

public class EntryGroupViewHolder extends RecyclerView.ViewHolder {
    private TextView mainEntryText;
    private FavoriteImageView favoriteImageView;
    private ExpandImageView expandImageView;
    private RecyclerView childEntryGroups;

    private boolean isExpanded = false;

    public EntryGroupViewHolder(@NonNull View itemView) {
        super(itemView);
        childEntryGroups = itemView.findViewById(R.id.entrygroupcell_recyclerview_childentrygroups);
        mainEntryText = itemView.findViewById(R.id.entrygroupcell_textview_text);
        favoriteImageView = itemView.findViewById(R.id.entrygroupcell_imgview_favorite);
        expandImageView = itemView.findViewById(R.id.entrygroupcell_imgview_expand);
    }
    public void bindData(EntryGroupAbridged entryGroupAbridged){
        mainEntryText.setText(entryGroupAbridged.getMain().getText());
        favoriteImageView.setSrcFavorite(entryGroupAbridged.isFavorite());
        expandImageView.setSrcExpanded(isExpanded);

        formatViewAccordingToLevel(entryGroupAbridged);
    }
    public void setRecyclerView(ArrayList<EntryGroupAbridged> entryGroupAbridgedArrayList, VolleyBall volleyBall){
        Context recyclersContext = itemView.getContext();
        EntryGroupAdapter myAdapter = new EntryGroupAdapter(entryGroupAbridgedArrayList, volleyBall);
        childEntryGroups.setAdapter(myAdapter);
        childEntryGroups.setLayoutManager(new LinearLayoutManager(recyclersContext));

    }
    private void formatViewAccordingToLevel(EntryGroupAbridged entryGroupAbridged){
        int level = entryGroupAbridged.getLevel();
        //TODO: Hay que lidiar mejor con la casuística. Además... esto no tiene en cuenta el nivel de los EntryGroups "base" (i.e. los supone de nivel 1)
        if(level>5) level=5;
        itemView.setPadding(level*7, 0, 0, 0);
        switch (level){
            case 1:
                break;
            case 2:
                itemView.setBackgroundResource(R.color.naranja_muyclaro);
                break;
            case 3:
                itemView.setBackgroundResource(R.color.lima_claro);
                break;
            case 4:
                itemView.setBackgroundResource(R.color.lila_palido);
                break;
            case 5:
                itemView.setBackgroundResource(R.color.turquesa_claro);
                break;
        }
    }
    public boolean isExpanded() {
        return isExpanded;
    }
    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
    public FavoriteImageView getFavoriteImageView() {
        return favoriteImageView;
    }

    public ExpandImageView getExpandImageView() {
        return expandImageView;
    }
}
