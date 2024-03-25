package com.app.mtis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.app.mtis.requestAPI.VolleyBall;

import org.json.JSONException;

public class LoginActivity extends AppCompatActivity {
    private Context context = this;
    private VolleyBall volleyBall;

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button signupButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEditText = findViewById(R.id.login_edittext_username);
        passwordEditText = findViewById(R.id.login_edittext_password);
        loginButton = findViewById(R.id.login_button_login);
        signupButton = findViewById(R.id.login_button_signup);
        volleyBall = new VolleyBall(context);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SignupActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                try {
                    volleyBall.logIn(username, password, new VolleyBall.VolleyCallback() {
                        @Override
                        public void onSuccess() {
                            Intent intent = new Intent(context, MainActivity.class);
                            startActivity(intent);
                        }
                        @Override
                        public void onError(VolleyError error) {
                            int status = volleyBall.getStatus(error);
                            if (status==401){
                                passwordEditText.setText("");
                                passwordEditText.requestFocus();
                                Toast.makeText(context, "Wrong password", Toast.LENGTH_LONG).show();
                            }
                            else if (status==404){
                                usernameEditText.requestFocus();
                                Toast.makeText(context, "Username doesn't exist", Toast.LENGTH_LONG).show();
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
