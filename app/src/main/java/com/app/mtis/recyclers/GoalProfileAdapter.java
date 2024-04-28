package com.app.mtis.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.app.mtis.EntryDetailActivity;
import com.app.mtis.R;
import com.app.mtis.models.Goal;
import com.app.mtis.requestAPI.VolleyBall;

import org.json.JSONException;

import java.util.ArrayList;

public class GoalProfileAdapter extends RecyclerView.Adapter<GoalProfileViewHolder> {

    private ArrayList<Goal> goals;
    private VolleyBall volleyBall;

    public GoalProfileAdapter(ArrayList<Goal> goals, VolleyBall volleyBall) {
        this.goals = goals;
        this.volleyBall = volleyBall;
    }

    @NonNull
    @Override
    public GoalProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View cellView = inflater.inflate(R.layout.recyclercell_goal_profile, parent, false);
        return new GoalProfileViewHolder(cellView);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalProfileViewHolder holder, int position) {
        Goal goal = this.goals.get(position);
        holder.bindData(goal);

        CheckBox checkBox = holder.getCompletedCheckbox();
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //TODO: Manda la peticion al servidor y actualiza el estado del Goal a completado
                    // Ahora no manda la peticion correcta porque el backend no está preparado
                    try {
                        volleyBall.putGoal(goal.getId(), goal, new VolleyBall.VolleyCallback() {
                            @Override
                            public void onSuccess() {
                                // onSuccess
                                goal.markAsCompleted();
                                holder.bindData(goal);
                            }
                            @Override
                            public void onError(VolleyError error) {
                                // onError
                                checkBox.setChecked(false);
                            }
                        });
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return this.goals.size();
    }
}
