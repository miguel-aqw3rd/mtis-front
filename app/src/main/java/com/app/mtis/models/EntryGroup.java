package com.app.mtis.models;

import com.app.mtis.models.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EntryGroup {
    private int id;
    private Entry root;
    private Entry main;
    private int level;
    private boolean favorite;
    private ArrayList<Entry> entries;

    public EntryGroup(int id, Entry root, Entry main, int level, boolean favorite, ArrayList<Entry> entries) {
        this.id = id;
        this.root = root;
        this.main = main;
        this.level = level;
        this.favorite = favorite;
        this.entries = entries;
    }
    public EntryGroup(JSONObject json) throws JSONException {
        this.id = json.getInt("id");
        this.root = new Entry(json.getJSONObject("root"));
        this.main = new Entry(json.getJSONObject("main"));
        this.level = json.getInt("level");
        this.favorite = json.getBoolean("favorite");
        JSONArray entriesArray = json.getJSONArray("entries");
        for (int i = 0; i < entriesArray.length(); i++) {
            this.entries.add(new Entry((JSONObject) entriesArray.get(i)));
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Entry getRoot() {
        return root;
    }

    public void setRoot(Entry root) {
        this.root = root;
    }

    public Entry getMain() {
        return main;
    }

    public void setMain(Entry main) {
        this.main = main;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public ArrayList<Entry> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<Entry> entries) {
        this.entries = entries;
    }
}
