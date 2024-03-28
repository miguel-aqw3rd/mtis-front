package com.app.mtis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.app.mtis.models.Entry;
import com.app.mtis.requestAPI.VolleyBall;

import org.json.JSONException;


public class NewEntryActivity extends AppCompatActivity {
    private Context context = this;
    private VolleyBall volleyBall;
    private Entry entry;
    private int entryGroupId;
    private int entryType;

    private EditText newEntryText;
    private Spinner typeSpinner;
    private Button saveEntryButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newentry);
        newEntryText = findViewById(R.id.newentry_edittext_text);
        typeSpinner = findViewById(R.id.newentry_spinner_type);
        saveEntryButton = findViewById(R.id.newentry_button_save);
        volleyBall = new VolleyBall(context);

        Intent intent = getIntent();
        entryGroupId = intent.getIntExtra("entryGroupId", 0);

        // Adapter for the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.entry_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                entryType = position;
                //Log.d("ENTRY TYPE", Integer.toString(entryType));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        saveEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Entry type is entryType class variable
                String text = newEntryText.getText().toString(); // Text
                if(text == null || text.equals("")) {
                    Toast.makeText(context, "Text body is empty", Toast.LENGTH_SHORT).show();
                    newEntryText.requestFocus();
                }
                else{
                    // Create Entry object and POST it to server
                    entry = new Entry(text, entryType, 0);
                    if (entryGroupId != 0){
                        try {
                            volleyBall.postEntry(entry, entryGroupId, new VolleyBall.VolleyCallback() {
                                @Override
                                public void onSuccess() {
                                    finish();
                                }
                                @Override
                                public void onError(VolleyError error) {}
                            });
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }else{
                        try {
                            volleyBall.postEntry(entry, new VolleyBall.VolleyCallback() {
                                @Override
                                public void onSuccess() {
                                    finish();
                                }
                                @Override
                                public void onError(VolleyError error) {}
                            });
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });


    }
}
