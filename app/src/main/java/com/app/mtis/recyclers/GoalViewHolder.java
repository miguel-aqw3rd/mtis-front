package com.app.mtis.recyclers;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mtis.R;
import com.app.mtis.custom.FavoriteImageView;
import com.app.mtis.models.Goal;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class GoalViewHolder extends RecyclerView.ViewHolder {
    private TextView descriptionTextView;
    private ImageView deleteButton;
    private FavoriteImageView favoriteImageView;
    private SwitchMaterial activeSwitch;
    public GoalViewHolder(@NonNull View itemView) {
        super(itemView);

        descriptionTextView = itemView.findViewById(R.id.goalcell_textview_description);
        deleteButton = itemView.findViewById(R.id.goalcell_imgview_delete);
        favoriteImageView = itemView.findViewById(R.id.goalcell_imgview_favorite);
        activeSwitch = itemView.findViewById(R.id.goalcell_switch_active);
    }

    public void bindData(Goal goal){
        descriptionTextView.setText(goal.getDescription());
        favoriteImageView.setSrcFavorite(goal.isFavorite());
        activeSwitch.setChecked(goal.isActive());
    }

    public ImageView getDeleteButton() {
        return deleteButton;
    }

    public FavoriteImageView getFavoriteImageView() {
        return favoriteImageView;
    }

    public SwitchMaterial getActiveSwitch() {
        return activeSwitch;
    }
}
