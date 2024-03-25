package com.app.mtis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.app.mtis.requestAPI.VolleyBall;

import org.json.JSONException;

public class SignupActivity extends AppCompatActivity {
    private Context context = this;
    private VolleyBall volleyBall;

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button backButton;
    private Button signupButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        usernameEditText = findViewById(R.id.signup_edittext_username);
        emailEditText = findViewById(R.id.signup_edittext_email);
        passwordEditText = findViewById(R.id.signup_edittext_password);
        backButton = findViewById(R.id.signup_button_back);
        signupButton = findViewById(R.id.signup_button_signup);
        volleyBall = new VolleyBall(context);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                try {
                    volleyBall.signUp(username, email, password, new VolleyBall.VolleyCallback() {
                        @Override
                        public void onSuccess() {
                            Intent intent = new Intent(context, MainActivity.class);
                            startActivity(intent);
                        }
                        @Override
                        public void onError(VolleyError error) {
                            int status = volleyBall.getStatus(error);
                            if (status==409){
                                usernameEditText.requestFocus();
                                Toast.makeText(context, "Username already taken", Toast.LENGTH_LONG).show();
                            }else if (status==400){
                                Toast.makeText(context, "Username and password required", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}
