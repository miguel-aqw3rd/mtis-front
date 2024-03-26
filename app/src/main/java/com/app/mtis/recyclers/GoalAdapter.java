package com.app.mtis.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mtis.R;
import com.app.mtis.models.Goal;
import com.app.mtis.requestAPI.VolleyBall;

import java.util.ArrayList;

public class GoalAdapter extends RecyclerView.Adapter<GoalViewHolder> {
    private ArrayList<Goal> goals;
    private VolleyBall volleyBall;

    public GoalAdapter(ArrayList<Goal> goals, VolleyBall volleyBall) {
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

    }

    @Override
    public int getItemCount() {
        return this.goals.size();
    }
}
