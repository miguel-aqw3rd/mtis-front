package com.app.mtis;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.app.mtis.models.Question;
import com.app.mtis.requestAPI.VolleyBall;

public class QuestionActivity extends AppCompatActivity {
    private Context context = this;
    private VolleyBall volleyBall;


    private int questionId = 0;
    private Question question;
    private int nextQA;
    private int nextQB;
    private TextView questionTextView;
    private TextView answerATextView;
    private TextView answerBTextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        volleyBall = new VolleyBall(context);
        questionTextView = findViewById(R.id.question_textview_question);
        answerATextView = findViewById(R.id.question_textview_answerA);
        answerBTextView = findViewById(R.id.question_textview_answerB);

        Intent intent = getIntent();
        questionId = intent.getIntExtra("questionId", 0);
        if(questionId != 0){
            loadQuestion(questionId);
        }else finish();



    }
    private void loadQuestion(int id){
        volleyBall.getQuestion(id, new VolleyBall.VolleyCallback() {
            @Override
            public void onSuccess() {
                question = VolleyBall.getQuestion();

                questionId = question.getId();
                questionTextView.setText(question.getText());
                answerATextView.setText(question.getA());
                answerBTextView.setText(question.getB());
                nextQA = question.getNextQa();
                nextQB = question.getNextQb();

                updateOnClickListeners();
            }
            @Override
            public void onError(VolleyError error) {}
        });

    }

    private void updateOnClickListeners(){
        answerATextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer("A");
                if(nextQA!=-1)
                loadQuestion(nextQA);
                else finish();
            }
        });
        answerBTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer("B");
                if(nextQB!=-1)
                loadQuestion(nextQB);
                else finish();
            }
        });
    }
    private void answer(String AorB){
        volleyBall.postAnswer(questionId, AorB.equalsIgnoreCase("a") ? "a" : "b", new VolleyBall.VolleyCallback() {
            @Override
            public void onSuccess() {}
            @Override
            public void onError(VolleyError error) {}
        });
    }
}
