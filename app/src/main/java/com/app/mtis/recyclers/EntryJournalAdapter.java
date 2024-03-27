package com.app.mtis.recyclers;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.app.mtis.EntryGroupDetailActivity;
import com.app.mtis.R;
import com.app.mtis.models.Entry;
import com.app.mtis.models.EntryGroup;
import com.app.mtis.requestAPI.VolleyBall;

import java.util.ArrayList;

public class EntryJournalAdapter extends RecyclerView.Adapter<EntryJournalViewHolder> {
// TODO: Mirar de refactorizar esto con herencia de clases. ¿Son los dos RecyclerView tipo Entry suficientemente similares/compatibles?
    private ArrayList<Entry> entries;
    private VolleyBall volleyBall;

    public EntryJournalAdapter(ArrayList<Entry> entries, VolleyBall volleyBall) {
        this.entries = entries;
        this.volleyBall = volleyBall;
    }

    @NonNull
    @Override
    public EntryJournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View cellView = inflater.inflate(R.layout.recyclercell_entry_journal, parent, false);
        return new EntryJournalViewHolder(cellView);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryJournalViewHolder holder, int position) {
        Entry entry = this.entries.get(position);
        holder.bindData(entry);

        holder.itemView.findViewById(R.id.entryjournalcell_goto_entrygroup).setOnClickListener(new View.OnClickListener() {
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


    }

    @Override
    public int getItemCount() {
        return this.entries.size();
    }
}
