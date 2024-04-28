package com.app.mtis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.app.mtis.requestAPI.VolleyBall;

public class StoryActivity extends Fragment {
    private Context context;
    private VolleyBall volleyBall;

    private int firstChapter = 7;

    private ImageView characterImageView;
    private TextView titleTextView;
    private TextView characterTextView;
    private TextView authorTextView;
    private Button readStoryButton;

    public StoryActivity(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_story, container, false);
        // Init context
        context = requireContext();

        volleyBall = new VolleyBall(context);
        characterImageView = rootView.findViewById(R.id.story_imgview_character);
        titleTextView = rootView.findViewById(R.id.story_textview_title);
        characterTextView = rootView.findViewById(R.id.story_textview_character);
        authorTextView = rootView.findViewById(R.id.story_textview_author);
        readStoryButton = rootView.findViewById(R.id.story_button_read);

        readStoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuestionActivity.class);
                intent.putExtra("questionId", firstChapter);
                startActivity(intent);
            }
        });


        return rootView;
    }

}
