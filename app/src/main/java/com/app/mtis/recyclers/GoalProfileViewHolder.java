package com.app.mtis.recyclers;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mtis.R;
import com.app.mtis.custom.FavoriteImageView;
import com.app.mtis.models.Goal;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class GoalProfileViewHolder extends RecyclerView.ViewHolder {

    private TextView descriptionTextView;
    private CheckBox completedCheckbox;
    public GoalProfileViewHolder(@NonNull View itemView) {
        super(itemView);
        descriptionTextView = itemView.findViewById(R.id.goalcellprofile_textview_description);
        completedCheckbox = itemView.findViewById(R.id.goalcellprofile_checkbox_done);
    }
    public void bindData(Goal goal){
        descriptionTextView.setText(goal.getDescription());
        completedCheckbox.setChecked(goal.isCompleted());
        // Checkea programaticamente el Goal si su status es "completed"
        checkCompletion(goal);
    }
    private void checkCompletion(Goal goal){
        if(goal.isCompleted()){
            completedCheckbox.setChecked(true);
            completedCheckbox.setEnabled(false);
            //TODO: Cambia la GUI para reflejar el logro completado, e.g.  :
            itemView.setBackgroundResource(R.color.lila_palido);
        }
    }
    public CheckBox getCompletedCheckbox() {
        return completedCheckbox;
    }
}
