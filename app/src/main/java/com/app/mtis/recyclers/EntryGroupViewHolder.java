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
        //TODO: Tengo que añadir un RecyclerView (por defecto vacio e invisible) a cada una de las celdas. Contendrá los EntryGroups hijos de las Entries hijas de este EntryGroup
        mainEntryText = itemView.findViewById(R.id.entrygroupcell_textview_text);
        favoriteImageView = itemView.findViewById(R.id.entrygroupcell_imgview_favorite);
        expandImageView = itemView.findViewById(R.id.entrygroupcell_imgview_expand);
    }
    //TODO: En base a que se determina el estado de expandImageView?
    public void bindData(EntryGroupAbridged entryGroupAbridged){
        mainEntryText.setText(entryGroupAbridged.getMain().getText());
        favoriteImageView.setSrcFavorite(entryGroupAbridged.isFavorite());
        expandImageView.setSrcExpanded(isExpanded);
    }
    public void setRecyclerView(ArrayList<EntryGroupAbridged> entryGroupAbridgedArrayList, VolleyBall volleyBall){
        //TODO: Rellena el RecyclerView de EntryGroups hijos
        Context recyclersContext = itemView.getContext();
        EntryGroupAdapter myAdapter = new EntryGroupAdapter(entryGroupAbridgedArrayList, volleyBall);
        childEntryGroups.setAdapter(myAdapter);
        childEntryGroups.setLayoutManager(new LinearLayoutManager(recyclersContext));

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
