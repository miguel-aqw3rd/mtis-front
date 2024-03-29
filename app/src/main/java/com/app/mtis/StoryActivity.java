package com.app.mtis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.mtis.requestAPI.VolleyBall;

public class StoryActivity extends AppCompatActivity {
    private Context context = this;
    private VolleyBall volleyBall;

    private int firstChapter = 7;

    private ImageView characterImageView;
    private TextView titleTextView;
    private TextView characterTextView;
    private TextView authorTextView;
    private Button readStoryButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        volleyBall = new VolleyBall(context);
        characterImageView = findViewById(R.id.story_imgview_character);
        titleTextView = findViewById(R.id.story_textview_title);
        characterTextView = findViewById(R.id.story_textview_character);
        authorTextView = findViewById(R.id.story_textview_author);
        readStoryButton = findViewById(R.id.story_button_read);


        readStoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuestionActivity.class);
                intent.putExtra("questionId", firstChapter);
                startActivity(intent);
            }
        });





    }
}
