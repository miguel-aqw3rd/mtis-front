package com.app.mtis.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EntryGroupAbridged {
    private int id;
    private int rootId;
    private Entry main;
    private int level;
    private boolean favorite;

    public EntryGroupAbridged(int id, int rootId, Entry main, int level, boolean favorite, ArrayList<Entry> entries) {
        this.id = id;
        this.rootId = rootId;
        this.main = main;
        this.level = level;
        this.favorite = favorite;
    }
    public EntryGroupAbridged(JSONObject json) throws JSONException {
        this.id = json.getInt("id");
        this.rootId = json.getInt("root_id");
        this.main = new Entry(json.getJSONObject("main"));
        this.level = json.getInt("level");
        this.favorite = json.getBoolean("favorite");

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRootId() {
        return rootId;
    }

    public void setRootId(int rootId) {
        this.rootId = rootId;
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
}
