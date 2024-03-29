package com.app.mtis;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.app.mtis.models.EntryGroupAbridged;
import com.app.mtis.recyclers.EntryGroupAdapter;
import com.app.mtis.requestAPI.VolleyBall;

import java.util.ArrayList;

public class EntryGroupsListActivity extends AppCompatActivity {
    private Context context = this;
    private VolleyBall volleyBall;

    private RecyclerView entryGroupsRecycler;
    private ArrayList<EntryGroupAbridged> entryGroupAbridgedArrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrygroupslist);
        volleyBall = new VolleyBall(context);
        entryGroupsRecycler = findViewById(R.id.entrygroupslist_recyclerview_entrygroups);

        //TODO: Ahora muestra la jerarquia de EntryGroups empezando por el nivel 1... Tengo que hacer esto más flexible añadiendo algun filtro
        volleyBall.getEntryGroups(1, new VolleyBall.VolleyCallback() {
            @Override
            public void onSuccess() {
                entryGroupAbridgedArrayList = VolleyBall.getEntryGroupsAbridged();
                setEntryGroupsRecycler();
            }

            @Override
            public void onError(VolleyError error) {

            }
        });





    }
    private void setEntryGroupsRecycler(){
        EntryGroupAdapter myAdapter = new EntryGroupAdapter(entryGroupAbridgedArrayList, volleyBall);
        entryGroupsRecycler.setAdapter(myAdapter);
        entryGroupsRecycler.setLayoutManager(new LinearLayoutManager(context));
    }



}
