package com.app.mtis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.app.mtis.custom.CustomDivider;
import com.app.mtis.custom.FavoriteImageView;
import com.app.mtis.custom.UpArrowImageView;
import com.app.mtis.models.EntryGroup;
import com.app.mtis.recyclers.EntryAdapter;
import com.app.mtis.requestAPI.VolleyBall;

public class EntryGroupDetailActivity extends AppCompatActivity {

    private EntryGroup entryGroup;
    private int entryGroupId = 1;
    private Context context = this;
    private VolleyBall volleyBall;


    private TextView mainEntryTextView;
    private UpArrowImageView mainEntryChildEntryGroupButton;
    private FavoriteImageView entryGroupFavoriteButton;
    private RecyclerView entriesRecyclerView;
    private ImageView addButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        volleyBall = new VolleyBall(context);
        setContentView(R.layout.activity_entrygroup_detail);
        mainEntryTextView = findViewById(R.id.entrygroupdetail_textview_mainentry);
        mainEntryChildEntryGroupButton = findViewById(R.id.entrygroupdetail_imgview_mainentry_childentrygroup);
        entryGroupFavoriteButton = findViewById(R.id.entrygroupdetail_imgview_favorite);
        entriesRecyclerView = findViewById(R.id.entrygroupdetail_recyclerview_entries);
        addButton = findViewById(R.id.entrygroupdetail_imgview_add);
        //TODO: Poner un onClick al frame de la main Entry que lleve a su EntryDetail

        entryGroupFavoriteButton.setOnClickListener(new View.OnClickListener() {
            // TODO: Cambiar el icono <3 en la UI instantaneamente, y luego deshacer el cambio solo en caso de error en la peticion PUT
            @Override
            public void onClick(View v) {
                volleyBall.putEntryGroupFavorite(entryGroup.getId(), new VolleyBall.VolleyCallback() {
                    @Override
                    public void onSuccess() {
                        volleyBall.getEntryGroup(entryGroup.getId(), new VolleyBall.VolleyCallback() {
                            @Override
                            public void onSuccess() {
                                entryGroup = VolleyBall.getEntryGroup();
                                updateMain();
                            }
                            @Override
                            public void onError(VolleyError error) {}
                        });
                    }
                    @Override
                    public void onError(VolleyError error) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        mainEntryChildEntryGroupButton.setOnClickListener(new View.OnClickListener() {
            // TODO: Por ahora es un tanto confuso... Marcar de algun modo cuando la main entry es tambien la root entry, porque su EntryGroup hijo es el actual
            @Override
            public void onClick(View v) {
                int mainEntryId = entryGroup.getMain().getId();
                int entryGroupId = entryGroup.getMain().getChildEntryGroupId();
                if (entryGroupId != 0) { //Default value is 0. If entryGroupId is 0, it means the entry has no associated child entry group
                    Intent intent = new Intent(v.getContext(), EntryGroupDetailActivity.class);
                    intent.putExtra("entryGroupId", entryGroupId);
                    v.getContext().startActivity(intent);
                }else { // POST: Create a new EntryGroup with this entry as root
                    volleyBall.postEntryGroup(mainEntryId, new VolleyBall.VolleyCallback() {
                        @Override
                        public void onSuccess() { // I don't know the new entrygroup's ID. I get it from the updated Entry object
                            volleyBall.getEntry(mainEntryId, new VolleyBall.VolleyCallback() {
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
                        public void onError(VolleyError error) {}
                    });
                }
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Starts the activity to write a new entry in the current entrygroup. The group's id is passed
                Intent intent = new Intent(context, NewEntryActivity.class);
                intent.putExtra("entryGroupId", entryGroup.getId());
                startActivity(intent);
            }
        });



        






        Intent intent = getIntent();
        entryGroupId = intent.getIntExtra("entryGroupId", 0);

        if(entryGroupId != 0){
            volleyBall.getEntryGroup(entryGroupId, new VolleyBall.VolleyCallback() {
                @Override
                public void onSuccess() {
                    entryGroup = VolleyBall.getEntryGroup();

                    updateDisplay();
                }
                @Override
                public void onError(VolleyError error) {}
            });
        }



    }
    //TODO: Actualizar el RecyclerView en onResume para que una nueva Entry aparezca después de ser añadida
    @Override
    protected void onResume() {
        super.onResume();

        if(entryGroupId != 0){
            volleyBall.getEntryGroup(entryGroupId, new VolleyBall.VolleyCallback() {
                @Override
                public void onSuccess() {
                    entryGroup = VolleyBall.getEntryGroup();

                    updateDisplay();
                }
                @Override
                public void onError(VolleyError error) {}
            });
        }
    }

    //TODO: Que el RecyclerView posicione en primera posicion (realtiva) la nueva Entry

    public void updateDisplay(){
        updateMain();
        updateAdapter();
    }
    private void updateMain(){
        mainEntryTextView.setText(entryGroup.getMain().getText());
        entryGroupFavoriteButton.setSrcFavorite(entryGroup.isFavorite());  // Selects the appropiate image source to reflect ths status (favorite/not favorite)
        mainEntryChildEntryGroupButton.setSrcArrow(entryGroup.getMain().getChildEntryGroupId() != 0);  // Analogous: main Entry has a child EntryGroup iff its id is not 0
    }
    private void updateAdapter(){
        EntryAdapter myAdapter = new EntryAdapter(EntryGroupDetailActivity.this);
        entriesRecyclerView.setAdapter(myAdapter);
        entriesRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        //entriesRecyclerView.addItemDecoration(new CustomDivider(context));
        // TODO: Arreglar el CustomDivider... Dibuja la linea inclinada
    }


    public EntryGroup getEntryGroup() {
        return entryGroup;
    }

    public void setEntryGroup(EntryGroup entryGroup) {
        this.entryGroup = entryGroup;
    }

    public VolleyBall getVolleyBall() {
        return volleyBall;
    }
}
