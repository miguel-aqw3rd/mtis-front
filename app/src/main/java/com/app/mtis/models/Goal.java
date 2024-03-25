package com.app.mtis.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Goal {
    private int id;
    private String description;
    private String frequency;
    private boolean active;
    private boolean favorite;
    private int entryId;

    public Goal(int id, String description, String frequency, boolean active, boolean favorite, int entryId) {
        this.id = id;
        this.description = description;
        this.frequency = frequency;
        this.active = active;
        this.favorite = favorite;
        this.entryId = entryId;
    }
    public Goal(JSONObject json) throws JSONException {
        this.id = json.getInt("id");
        this.description = json.getString("description");
        this.frequency = json.getString("frequency");
        this.active = json.getBoolean("active");
        this.favorite = json.getBoolean("favorite");
        this.entryId = json.getInt("entry_id");
    }
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("description", this.description);
        json.put("frequency", this.frequency);
        json.put("active", this.active);
        json.put("favorite", this.favorite);
        return json;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }
}
