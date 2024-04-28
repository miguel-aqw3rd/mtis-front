package com.app.mtis;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.app.mtis.models.EntryGroupAbridged;
import com.app.mtis.recyclers.EntryGroupAdapter;
import com.app.mtis.requestAPI.VolleyBall;

import java.util.ArrayList;

public class EntryGroupsListActivity extends Fragment {
    private Context context;
    private VolleyBall volleyBall;

    private RecyclerView entryGroupsRecycler;
    private ArrayList<EntryGroupAbridged> entryGroupAbridgedArrayList = new ArrayList<>();

    public EntryGroupsListActivity(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_entrygroupslist, container, false);
        // Init context
        context = requireContext();

        volleyBall = new VolleyBall(context);
        entryGroupsRecycler = rootView.findViewById(R.id.entrygroupslist_recyclerview_entrygroups);

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


        return rootView;
    }

    private void setEntryGroupsRecycler(){
        EntryGroupAdapter myAdapter = new EntryGroupAdapter(entryGroupAbridgedArrayList, volleyBall);
        entryGroupsRecycler.setAdapter(myAdapter);
        entryGroupsRecycler.setLayoutManager(new LinearLayoutManager(context));
    }

}
