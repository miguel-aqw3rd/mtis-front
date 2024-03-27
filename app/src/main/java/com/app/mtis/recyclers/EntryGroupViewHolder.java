package com.app.mtis.recyclers;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mtis.R;
import com.app.mtis.custom.ExpandImageView;
import com.app.mtis.custom.FavoriteImageView;
import com.app.mtis.models.EntryGroupAbridged;

public class EntryGroupViewHolder extends RecyclerView.ViewHolder {
    private TextView mainEntryText;
    private FavoriteImageView favoriteImageView;
    private ExpandImageView expandImageView;
    private RecyclerView childEntryGroups;

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
    }
    public void setRecyclerView(){
        //TODO: Rellena el RecyclerView de EntryGroups hijos
    }

    public FavoriteImageView getFavoriteImageView() {
        return favoriteImageView;
    }

    public ExpandImageView getExpandImageView() {
        return expandImageView;
    }
}
