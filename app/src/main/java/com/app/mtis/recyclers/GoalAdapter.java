package com.app.mtis.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.app.mtis.EntryDetailActivity;
import com.app.mtis.R;
import com.app.mtis.custom.FavoriteImageView;
import com.app.mtis.models.Goal;
import com.app.mtis.requestAPI.VolleyBall;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.json.JSONException;

import java.util.ArrayList;

public class GoalAdapter extends RecyclerView.Adapter<GoalViewHolder> {
    private ArrayList<Goal> goals;
    private VolleyBall volleyBall;

    private EntryDetailActivity hostActivity;

    public GoalAdapter(ArrayList<Goal> goals, VolleyBall volleyBall) {
        this.goals = goals;
        this.volleyBall = volleyBall;
    }
    public GoalAdapter(EntryDetailActivity activity, ArrayList<Goal> goals, VolleyBall volleyBall){
        //TODO: Una idea es proveer un objeto interfaz que implementen las clases de las actividades que usen GoalAdapter, con métodos como getGoalsArrayList() y updateRecycler()
        this.hostActivity = activity;
        this.goals = goals;
        this.volleyBall = volleyBall;
    }

    @NonNull
    @Override
    public GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View cellView = inflater.inflate(R.layout.recyclercell_goal, parent, false);
        return new GoalViewHolder(cellView);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalViewHolder holder, int position) {
        Goal goal = this.goals.get(position);
        holder.bindData(goal);

        SwitchMaterial switchActive = holder.getActiveSwitch();
        FavoriteImageView favoriteImageView = holder.getFavoriteImageView();

        switchActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int goalId = goal.getId();
                goal.setActive(isChecked); // The same goal, just changing the value of boolean active
                try {
                    volleyBall.putGoal(goalId, goal, new VolleyBall.VolleyCallback() {
                        @Override
                        public void onSuccess() {
                            holder.bindData(goal);
                        }
                        @Override
                        public void onError(VolleyError error) {
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        holder.itemView.findViewById(R.id.goalcell_imgview_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volleyBall.deleteGoal(goal.getId(), new VolleyBall.VolleyCallback() {
                    @Override
                    public void onSuccess() {
                        // Recarga el recyclerview de Goals
                        hostActivity.updateGoalsRecycler();
                    }
                    @Override
                    public void onError(VolleyError error) {
                        Toast.makeText(holder.itemView.getContext(), "Error: Goal couldn't be deleted", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        favoriteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int goalId = goal.getId();
                goal.setFavorite(!goal.isFavorite()); // The same goal, just changing the value of boolean favorite
                try {
                    volleyBall.putGoal(goalId, goal, new VolleyBall.VolleyCallback() {
                        @Override
                        public void onSuccess() {
                            holder.bindData(goal);
                        }
                        @Override
                        public void onError(VolleyError error) {
                            Toast.makeText(holder.itemView.getContext(), "Error: Favorite status couldn't be updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.goals.size();
    }
}
