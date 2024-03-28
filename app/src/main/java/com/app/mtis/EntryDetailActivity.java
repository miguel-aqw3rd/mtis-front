package com.app.mtis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.app.mtis.custom.UpArrowImageView;
import com.app.mtis.models.Entry;
import com.app.mtis.models.Goal;
import com.app.mtis.recyclers.EntryAdapter;
import com.app.mtis.recyclers.GoalAdapter;
import com.app.mtis.requestAPI.VolleyBall;

import java.util.ArrayList;

public class EntryDetailActivity extends AppCompatActivity {
    private Context context = this;
    private VolleyBall volleyBall;
    private Entry entry;
    private int entryId;

    private TextView entryText;
    private UpArrowImageView entryGotoChildEntryGroup;
    private Button entryGotoParentEntryGroup;
    private Spinner goalFilterSpinner;
    private ImageView addGoalButton;
    private RecyclerView entryGoalsRecycler;

    // Filter parameters for Goals' RecyclerView
    private int favoriteGoalsFilter = -1;
    private int activeGoalsFilter = -1;

    private ArrayList<Goal> goalArrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrydetail);
        entryText = findViewById(R.id.entrydetail_textview_entrytext);
        entryGotoChildEntryGroup = findViewById(R.id.entrydetail_imgview_childentrygroup);
        entryGotoParentEntryGroup = findViewById(R.id.entrydetail_imgview_parententrygroup);
        goalFilterSpinner = findViewById(R.id.entrydetail_spinner_goalfilter);
        addGoalButton = findViewById(R.id.entrydetail_imgview_addgoal);
        entryGoalsRecycler = findViewById(R.id.entrydetail_recyclerview_goals);
        volleyBall = new VolleyBall(context);

        // Adapter for the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.goals_filter, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        goalFilterSpinner.setAdapter(adapter);
        goalFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){ // 1 means "yes", 0 means "no", -1 means "whatever.."
                    case 0: //All
                        favoriteGoalsFilter = -1;
                        activeGoalsFilter = -1;
                        break;
                    case 1: //Favorites
                        favoriteGoalsFilter = 1;
                        activeGoalsFilter = -1;
                        break;
                    case 2: //Active
                        favoriteGoalsFilter = -1;
                        activeGoalsFilter = 1;
                        break;
                }
                // Reload the list of goals applying the new filter
                updateGoalsRecycler();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Loads the Entry from server
        Intent intent = getIntent();
        entryId = intent.getIntExtra("entryId", 0);
        if (entryId != 0){
            volleyBall.getEntry(entryId, new VolleyBall.VolleyCallback() {
                @Override
                public void onSuccess() {
                    entry = VolleyBall.getEntry();

                    updateEntryFrame();
                    updateGoalsRecycler();
                }
                @Override
                public void onError(VolleyError error) {
                    Toast.makeText(context, "Error: Couldn't retrieve data", Toast.LENGTH_SHORT).show();
                    // Close activity if data can't be displayed
                    finish();
                }
            });
        }else{
            Toast.makeText(context, "Error: entry undefined", Toast.LENGTH_SHORT).show();
        }


        //TODO: Quizás sería buena idea pedir confirmación al usuario a la hora de crear un nuevo EntryGroup
        //TODO: Refactorizar: las strings harcodeadas que sirven de clave en los intents estaría mejor especificarlas en strings.xml

        entryGotoChildEntryGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(entry.getChildEntryGroupId() != 0){
                    Intent intent = new Intent(context, EntryGroupDetailActivity.class);
                    intent.putExtra("entryGroupId", entry.getChildEntryGroupId());
                    startActivity(intent);
                }else{
                    volleyBall.postEntryGroup(entry.getId(), new VolleyBall.VolleyCallback() {
                        @Override
                        public void onSuccess() {
                            // TODO: Esto es menos laborioso y confuso si el server devuelve la id del nuevo EntryGroup... Tengo que modificar el endpoint y la correspondiente request en Volleyball
                            volleyBall.getEntry(entry.getId(), new VolleyBall.VolleyCallback() {
                                @Override
                                public void onSuccess() { // The same. I start EntryGroupDetailActivity, with the new EntryGroup
                                    int entryGroupId = VolleyBall.getEntry().getChildEntryGroupId();
                                    Intent intent = new Intent(context, EntryGroupDetailActivity.class);
                                    intent.putExtra("entryGroupId", entryGroupId);
                                    startActivity(intent);
                                }
                                @Override
                                public void onError(VolleyError error) {Toast.makeText(context, "Error fetching entryGroupId", Toast.LENGTH_SHORT).show();}
                            });
                        }
                        @Override
                        public void onError(VolleyError error) {
                            Toast.makeText(context, "Error: unable to save new EntryGroup", Toast.LENGTH_SHORT).show();}
                    });
                }

            }
        });

        entryGotoParentEntryGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Esto funciona mientras que el flujo de la app sea tal que la única forma de acceder al detalle de una Entry sea desde su EntryGroup padre
                // TODO: A la larga es más flexible que el servidor devuelva con la Entry la id de su EntryGroup padre (o 0)
                finish();
            }
        });

        addGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Igual queda mejor implementarlo como Fragment y que sustituya en la GUI al RecyclerView en la mitad inferior de la pantalla
                Intent intent = new Intent(context, NewGoalActivity.class);
                intent.putExtra("entryId", entry.getId());
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        updateGoalsRecycler();
    }

    public void updateGoalsRecycler(){
        volleyBall.getGoals(entryId, activeGoalsFilter, favoriteGoalsFilter, new VolleyBall.VolleyCallback() {
            @Override
            public void onSuccess() {
                goalArrayList = VolleyBall.getGoals();


                GoalAdapter myAdapter = new GoalAdapter(EntryDetailActivity.this, goalArrayList, volleyBall);
                entryGoalsRecycler.setAdapter(myAdapter);
                entryGoalsRecycler.setLayoutManager(new LinearLayoutManager(context));
            }
            @Override
            public void onError(VolleyError error) {
                goalArrayList = new ArrayList<>();
                Toast.makeText(context, "Error loading Goals", Toast.LENGTH_SHORT).show();
                GoalAdapter myAdapter = new GoalAdapter(EntryDetailActivity.this, goalArrayList, volleyBall);
                entryGoalsRecycler.setAdapter(myAdapter);
                entryGoalsRecycler.setLayoutManager(new LinearLayoutManager(context));
            }
        });

    }
    private void updateEntryFrame(){
        entryText.setText(entry.getText());
        entryGotoChildEntryGroup.setSrcArrow(entry.getChildEntryGroupId() != 0); // If the id is not 0, it means its child EntryGroup exists
    }
}
