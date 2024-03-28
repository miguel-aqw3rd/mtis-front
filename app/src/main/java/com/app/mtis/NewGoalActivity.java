package com.app.mtis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.app.mtis.models.Goal;
import com.app.mtis.requestAPI.VolleyBall;

import org.json.JSONException;

public class NewGoalActivity extends AppCompatActivity {

    private Context context = this;
    private VolleyBall volleyBall;

    private EditText descriptionEditText;
    private Spinner frequencySpinner;
    private Button saveButton;
    private Button closeButton;

    private int entryId = 0;
    private String description;
    private String frequency;
    private boolean active = true;
    private boolean favorite = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newgoal);
        volleyBall = new VolleyBall(context);
        descriptionEditText = findViewById(R.id.newgoal_edittext_description);
        frequencySpinner = findViewById(R.id.newgoal_spinner_frequency);
        saveButton = findViewById(R.id.newgoal_button_save);
        closeButton = findViewById(R.id.newgoal_button_back);

        Intent intent = getIntent();
        entryId = intent.getIntExtra("entryId", 0);

        // Adapter for the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.goal_frequency, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frequencySpinner.setAdapter(adapter);
        frequencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:  // Daily
                        frequency = "D";
                        break;
                    case 1:  // Twice a Day
                        frequency = "2D";
                        break;
                    case 2:  // Weekly
                        frequency = "W";
                        break;
                    case 3:  // Twice a Week
                        frequency = "2W";
                        break;
                    case 4:  // 3 Times a Week
                        frequency = "3W";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description = descriptionEditText.getText().toString();
                Goal newGoal = new Goal(description, frequency, active, favorite);
                if(entryId!=0){
                    try {
                        volleyBall.postGoal(newGoal, entryId, new VolleyBall.VolleyCallback() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(context, "Goal saved", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            @Override
                            public void onError(VolleyError error) {
                                Toast.makeText(context, "Error saving goal", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    try {
                        volleyBall.postGoal(newGoal, new VolleyBall.VolleyCallback() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(context, "Goal saved", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            @Override
                            public void onError(VolleyError error) {
                                Toast.makeText(context, "Error saving goal", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
