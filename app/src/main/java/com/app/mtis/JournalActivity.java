package com.app.mtis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.app.mtis.models.Entry;
import com.app.mtis.recyclers.EntryJournalAdapter;
import com.app.mtis.requestAPI.VolleyBall;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class JournalActivity extends Fragment {
    private Context context;
    private VolleyBall volleyBall;

    private ArrayList<Entry> entries = new ArrayList<>();
    private RecyclerView entriesRecyclerView;
    private FloatingActionButton addButton;
    private View mainEntryFrame;

    public JournalActivity(){
        //Empty Constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_journal, container, false);
        // Init context
        context = requireContext();

        volleyBall = new VolleyBall(context);
        entriesRecyclerView = rootView.findViewById(R.id.journal_recyclerview_entries);
        addButton = rootView.findViewById(R.id.journal_imgview_add);
        //Maybe ill do something with the frame later
        mainEntryFrame = rootView.findViewById(R.id.journal_frame_mainentry);

        // Write new entry directly in journal
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Starts the activity to write a new entry in the journal. No entrygroup id is passed, so the new entry will be level=0 and will not be part of any entrygroup
                Intent intent = new Intent(context, NewEntryActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
    @Override
    public void onResume() {
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
