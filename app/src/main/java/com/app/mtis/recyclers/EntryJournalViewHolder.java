package com.app.mtis.recyclers;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mtis.R;
import com.app.mtis.custom.UpArrowImageView;
import com.app.mtis.models.Entry;

public class EntryJournalViewHolder extends RecyclerView.ViewHolder {
    private TextView text;
    private UpArrowImageView entryGroupButton;

    public EntryJournalViewHolder(@NonNull View itemView) {
        super(itemView);
        text = itemView.findViewById(R.id.entryjournalcell_textview_text);
        entryGroupButton = itemView.findViewById(R.id.entryjournalcell_goto_entrygroup);

    }
    public void bindData(Entry entry){
        text.setText(entry.getText());
        if (entry.getChildEntryGroupId() == 0) entryGroupButton.setSrcArrow(false);
        else entryGroupButton.setSrcArrow(true);
    }

}
