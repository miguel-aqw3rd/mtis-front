package com.app.mtis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.app.mtis.requestAPI.VolleyBall;

public class MainActivity extends AppCompatActivity {

    private Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VolleyBall volleyBall = new VolleyBall(context);

        volleyBall.getEntries(new VolleyBall.VolleyCallback() {
            @Override
            public void onSuccess() {}
            @Override
            public void onError(VolleyError error) {
                if(volleyBall.getStatus(error)==401){
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }

            }
        });

    }
}
