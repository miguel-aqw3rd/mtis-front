package com.app.mtis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.app.mtis.models.Goal;
import com.app.mtis.recyclers.GoalProfileAdapter;
import com.app.mtis.requestAPI.VolleyBall;

import java.util.ArrayList;

public class ProfileActivity extends Fragment {
    private Context context;
    private VolleyBall volleyBall;

    private RecyclerView goalsRecycler;
    private ImageView settingsBtn;
    private ImageView bookBtn;
    private ImageView avatar;
    private ImageView addGoalBtn;
    private ImageView editGoalsBtn;
    private ArrayList<Goal> goalsArray;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_profile, container, false);
        // Init context
        context = requireContext();

        volleyBall = new VolleyBall(context);

        goalsRecycler = rootView.findViewById(R.id.profile_recyclerview_goals);
        settingsBtn = rootView.findViewById(R.id.profile_imgview_settings);
        bookBtn = rootView.findViewById(R.id.profile_imgview_book);
        avatar = rootView.findViewById(R.id.profile_imgview_avatar);
        addGoalBtn = rootView.findViewById(R.id.profile_imgview_addgoal);
        editGoalsBtn = rootView.findViewById(R.id.profile_imgview_editgoals);

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Añadir Settings
            }
        });
        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Esto llevará a alguna feature interesante
            }
        });
        addGoalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewGoalActivity.class);
                startActivity(intent);
            }
        });
        editGoalsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Añadir EditGoalsActivity
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateGoalsRecycler();
    }

    private void updateGoalsRecycler(){
        volleyBall.getGoals(new VolleyBall.VolleyCallback() {
            @Override
            public void onSuccess() {
                goalsArray = VolleyBall.getGoals();
                // After downloading goal list, the adapter is updated
                GoalProfileAdapter myAdapter = new GoalProfileAdapter(goalsArray, volleyBall);
                goalsRecycler.setAdapter(myAdapter);
                goalsRecycler.setLayoutManager(new LinearLayoutManager(context));
            }
            @Override
            public void onError(VolleyError error) {
                Toast.makeText(context, "Failed to fetch daily goals", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
