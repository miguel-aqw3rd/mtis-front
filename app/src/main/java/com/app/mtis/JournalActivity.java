package com.app.mtis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.app.mtis.custom.FavoriteImageView;
import com.app.mtis.custom.UpArrowImageView;
import com.app.mtis.models.Entry;
import com.app.mtis.models.EntryGroup;
import com.app.mtis.recyclers.EntryAdapter;
import com.app.mtis.recyclers.EntryJournalAdapter;
import com.app.mtis.requestAPI.VolleyBall;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class JournalActivity extends AppCompatActivity {
    private Context context = this;
    private VolleyBall volleyBall;

    private ArrayList<Entry> entries = new ArrayList<>();
    private RecyclerView entriesRecyclerView;
    private FloatingActionButton addButton;
    private View mainEntryFrame;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        volleyBall = new VolleyBall(context);
        setContentView(R.layout.activity_journal);
        entriesRecyclerView = findViewById(R.id.journal_recyclerview_entries);
        addButton = findViewById(R.id.journal_imgview_add);
        //Maybe ill do something with the frame later
        mainEntryFrame = findViewById(R.id.journal_frame_mainentry);

        // Write new entry directly in journal
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Starts the activity to write a new entry in the journal. No entrygroup id is passed, so the new entry will be level=0 and will not be part of any entrygroup
                Intent intent = new Intent(context, NewEntryActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        downloadJournalEntries();
    }

    public void updateDisplay(){
        updateMain();
        updateAdapter();
    }
    private void updateMain(){
        // I dont delete this just in case its useful later
    }
    private void updateAdapter(){
        EntryJournalAdapter myAdapter = new EntryJournalAdapter(entries, volleyBall);
        entriesRecyclerView.setAdapter(myAdapter);
        entriesRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        //entriesRecyclerView.addItemDecoration(new CustomDivider(context));
        // TODO: Arreglar el CustomDivider... Dibuja la linea inclinada
    }

    private void downloadJournalEntries(){
        volleyBall.getEntries(new VolleyBall.VolleyCallback() {
            @Override
            public void onSuccess() {
                entries = VolleyBall.getEntries();
                updateAdapter();
            }
            @Override
            public void onError(VolleyError error) {
                Toast.makeText(context, "Failed to fetch journal entries", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public VolleyBall getVolleyBall() {
        return volleyBall;
    }

}
