package com.app.mtis;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.app.mtis.requestAPI.VolleyBall;

public class EntriesTest extends AppCompatActivity {
    private Context context=this;
    private TextView test;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        test = findViewById(R.id.testview);


        VolleyBall volley = new VolleyBall(context);


        String tokenJuan = "71ce3a7803c2f5eccee34ef6d36107";
        String tokenTeodora = "3c1125d181d6ecaf6cb1bb7f059538";

        //volley.setAuthToken(token);

        volley.getEntry(2, new VolleyBall.VolleyCallback() {
            @Override
            public void onSuccess() {
                String text = VolleyBall.getEntry().getText();
                test.setText(text);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });


    }

}
