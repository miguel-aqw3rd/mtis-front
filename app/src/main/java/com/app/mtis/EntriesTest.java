package com.app.mtis;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

public class EntriesTest extends AppCompatActivity {
    private Context context=this;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VolleyBall volley = new VolleyBall(context);


        String token = "1234";
        // Almacenar el nombre de usuario y el token en las preferencias compartidas.
        SharedPreferences preferences = context.getSharedPreferences("SESSIONS_APP_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("auth-token", token);
        editor.apply();




        volley.getQuestion(1, new VolleyBall.VolleyCallback() {
            @Override
            public void onSuccess() {
                String text = VolleyBall.getQuestion().getText();
                Log.d("Response", text);
            }

            @Override
            public void onError(VolleyError error) {

            }

        });


        volley.getGoals(new VolleyBall.VolleyCallback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(VolleyError error) {

                            }
                        }, 1);
        VolleyBall.getGoals();

    }

}
