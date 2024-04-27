package com.app.mtis.recyclers;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.app.mtis.EntryDetailActivity;
import com.app.mtis.EntryGroupDetailActivity;
import com.app.mtis.R;
import com.app.mtis.custom.UpArrowImageView;
import com.app.mtis.models.Entry;
import com.app.mtis.models.EntryGroup;
import com.app.mtis.requestAPI.VolleyBall;

import java.util.ArrayList;

public class EntryAdapter extends RecyclerView.Adapter<EntryViewHolder> {

    private ArrayList<Entry> entries;
    private VolleyBall volleyBall;
    private int currentEntryGroupId;
    private EntryGroupDetailActivity detailActivity;

    public EntryAdapter(int currentEntryGroupId, ArrayList<Entry> entries, VolleyBall volleyBall) {
        this.entries = entries;
        this.volleyBall = volleyBall;
        this.currentEntryGroupId = currentEntryGroupId;
    }
    public EntryAdapter(EntryGroupDetailActivity detailActivity){
        this.detailActivity = detailActivity;
        this.entries = detailActivity.getEntryGroup().getEntries();
        this.currentEntryGroupId = detailActivity.getEntryGroup().getId();
        this.volleyBall = detailActivity.getVolleyBall();
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View cellView = inflater.inflate(R.layout.recyclercell_entry, parent, false);
        return new EntryViewHolder(cellView);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
        Entry entry = this.entries.get(position);
        holder.bindData(entry);
        // if this entry parent group id == this.currentGroupId
        // When the Parent EntryGroup of an Entry is the current one, the entrygroup button should not be displayed
        if (entry.getChildEntryGroupId() == this.currentEntryGroupId){
            UpArrowImageView button = (UpArrowImageView) holder.itemView.findViewById(R.id.entrycell_goto_entrygroup);
            button.deactivateButton();
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EntryDetailActivity.class);
                intent.putExtra("entryId", entry.getId());
                v.getContext().startActivity(intent);
            }
        });

        holder.itemView.findViewById(R.id.entrycell_goto_entrygroup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int entryGroupId = entry.getChildEntryGroupId();
                if (entryGroupId != 0) { //Default value is 0. If entryGroupId is 0, it means the
                    Intent intent = new Intent(v.getContext(), EntryGroupDetailActivity.class);
                    intent.putExtra("entryGroupId", entryGroupId);
                    v.getContext().startActivity(intent);
                }else { // POST: Create a new EntryGroup with this entry as root
                    volleyBall.postEntryGroup(entry.getId(), new VolleyBall.VolleyCallback() {
                        @Override
                        public void onSuccess() { // I don't know the new entrygroup's ID. I get it from the updated Entry object
                            // TODO: Esto es menos laborioso y confuso si el server devuelve la id del nuevo EntryGroup... Tengo que modificar el endpoint y la correspondiente request en Volleyball
                            volleyBall.getEntry(entry.getId(), new VolleyBall.VolleyCallback() {
                                @Override
                                public void onSuccess() { // The same. I start EntryGroupDetailActivity, with the new EntryGroup
                                    int entryGroupId = VolleyBall.getEntry().getChildEntryGroupId();
                                    Intent intent = new Intent(v.getContext(), EntryGroupDetailActivity.class);
                                    intent.putExtra("entryGroupId", entryGroupId);
                                    v.getContext().startActivity(intent);
                                }
                                @Override
                                public void onError(VolleyError error) {}
                            });
                        }
                        @Override
                        public void onError(VolleyError error) {

                        }
                    });
                }
            }
        });

        holder.itemView.findViewById(R.id.entrycell_makemain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volleyBall.putMainEntry(entry.getId(), EntryAdapter.this.currentEntryGroupId, new VolleyBall.VolleyCallback() {
                    @Override
                    public void onSuccess() {
                        volleyBall.getEntryGroup(currentEntryGroupId, new VolleyBall.VolleyCallback() {
                            @Override
                            public void onSuccess() {
                                EntryGroup entryGroup = VolleyBall.getEntryGroup();
                                detailActivity.setEntryGroup(entryGroup);
                                detailActivity.refreshEntryGroup();
                            }
                            @Override
                            public void onError(VolleyError error) {}
                        });
                    }
                    @Override
                    public void onError(VolleyError error) {}
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.entries.size();
    }
}
