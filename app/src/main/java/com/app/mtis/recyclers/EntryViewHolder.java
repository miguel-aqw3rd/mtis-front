package com.app.mtis.recyclers;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mtis.R;
import com.app.mtis.models.Entry;

public class EntryViewHolder extends RecyclerView.ViewHolder {
    private TextView text;
    private Button entryGroupButton;
    private Button mainEntryButton;

    public EntryViewHolder(@NonNull View itemView) {
        super(itemView);

        text = itemView.findViewById(R.id.entrycell_textview_text);
        entryGroupButton = itemView.findViewById(R.id.entrycell_goto_entrygroup);
        mainEntryButton = itemView.findViewById(R.id.entrycell_makemain);

    }
    public void bindData(Entry entry){
        text.setText(entry.getText());

    }

}
