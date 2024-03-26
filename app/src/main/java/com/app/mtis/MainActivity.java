package com.app.mtis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.app.mtis.requestAPI.VolleyBall;

public class MainActivity extends AppCompatActivity {

    private Context context = this;
    private TextView text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.main_text);

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

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EntryGroupDetailActivity.class);
                intent.putExtra("entryGroupId", 1);
                context.startActivity(intent);
            }
        });

    }
}
