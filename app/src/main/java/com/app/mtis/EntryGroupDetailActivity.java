package com.app.mtis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.app.mtis.models.EntryGroup;
import com.app.mtis.recyclers.EntryAdapter;
import com.app.mtis.requestAPI.VolleyBall;

public class EntryGroupDetailActivity extends AppCompatActivity {

    private EntryGroup entryGroup;
    private int entryGroupId = 1;
    private Context context = this;
    private VolleyBall volleyBall;


    private TextView mainEntryTextView;
    private RecyclerView entriesRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VolleyBall volleyBall = new VolleyBall(context);
        setContentView(R.layout.activity_entrygroup_detail);
        mainEntryTextView = findViewById(R.id.entrygroupdetail_textview_mainentry);
        entriesRecyclerView = findViewById(R.id.entrygroupdetail_recyclerview_entries);

        Intent intent = getIntent();
        entryGroupId = intent.getIntExtra("entryGroupId", 0);

        if(entryGroupId != 0){
            volleyBall.getEntryGroup(entryGroupId, new VolleyBall.VolleyCallback() {
                @Override
                public void onSuccess() {
                    entryGroup = VolleyBall.getEntryGroup();

                    updateUI();
                }
                @Override
                public void onError(VolleyError error) {}
            });
        }



    }

    private void updateUI(){
        mainEntryTextView.setText(entryGroup.getMain().getText());

        EntryAdapter myAdapter = new EntryAdapter(entryGroup.getId(), entryGroup.getEntries(), volleyBall);
        entriesRecyclerView.setAdapter(myAdapter);
        entriesRecyclerView.setLayoutManager(new LinearLayoutManager(context));
    }


}
