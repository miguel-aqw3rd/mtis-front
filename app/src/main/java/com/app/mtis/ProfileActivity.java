package com.app.mtis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.app.mtis.models.Goal;
import com.app.mtis.recyclers.GoalProfileAdapter;
import com.app.mtis.requestAPI.VolleyBall;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private Context context = this;
    private VolleyBall volleyBall;

    private RecyclerView goalsRecycler;
    private ImageView settingsBtn;
    private ImageView bookBtn;
    private ImageView avatar;
    private ImageView addGoalBtn;
    private ImageView editGoalsBtn;
    private ArrayList<Goal> goalsArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        volleyBall = new VolleyBall(context);

        goalsRecycler = findViewById(R.id.profile_recyclerview_goals);
        settingsBtn = findViewById(R.id.profile_imgview_settings);
        bookBtn = findViewById(R.id.profile_imgview_book);
        avatar = findViewById(R.id.profile_imgview_avatar);
        addGoalBtn = findViewById(R.id.profile_imgview_addgoal);
        editGoalsBtn = findViewById(R.id.profile_imgview_editgoals);

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



    }

    @Override
    protected void onResume() {
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
