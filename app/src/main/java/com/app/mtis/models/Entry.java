package com.app.mtis.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Entry {
    private int id;
    private String text;
    private int type;
    private int level;

    public Entry(int id, String text, int type, int level) {
        this.id = id;
        this.text = text;
        this.type = type;
        this.level = level;
    }
    public Entry(JSONObject json) throws JSONException {
        this.id = json.getInt("id");
        this.text = json.getString("text");
        this.type = json.getInt("type");
        this.level = json.getInt("level");
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("text", this.text);
        json.put("type", this.type);
        json.put("level", this.level);
        return json;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
