package com.app.mtis.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Entry {
    private int id;
    private String text;
    private int type;
    private int level;
    private int childEntryGroupId; // The ID of the EntryGroup of which this Entry is the root
    private int challengerEntryId;  // The ID of the Entry that "challenges" this Entry

    public Entry(int id, String text, int type, int level) {
        this.id = id;
        this.text = text;
        this.type = type;
        this.level = level;
    }
    // Overloaded constructor for POST
    public Entry(String text, int type, int level) {
        this.text = text;
        this.type = type;
        this.level = level;
    }
    public Entry(JSONObject json) throws JSONException {
        this.id = json.getInt("id");
        this.text = json.getString("text");
        this.type = json.getInt("type");
        this.level = json.getInt("level");
        try {
            this.childEntryGroupId = json.getInt("child_entrygroup_id");
        }catch (JSONException jsonException){/*Just in case the entry is not related to any entrygroup*/}
        try {
            this.challengerEntryId = json.getInt("entry_challenger_id");
        }catch (JSONException jsonException){/*Just in case the entry is not challenged by any other entry*/}
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

    public int getChildEntryGroupId() {
        return childEntryGroupId;
    }

    public void setChildEntryGroupId(int childEntryGroupId) {
        this.childEntryGroupId = childEntryGroupId;
    }

    public int getChallengerEntryId() {
        return challengerEntryId;
    }

    public void setChallengerEntryId(int challengerEntryId) {
        this.challengerEntryId = challengerEntryId;
    }
}
