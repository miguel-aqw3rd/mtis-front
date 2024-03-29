package com.app.mtis.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Question {
    private int id;
    private String text;
    private String a;
    private String b;
    private int nextQa;
    private int nextQb;

    public Question(int id, String text, String a, String b, int nextQa, int nextQb) {
        this.id = id;
        this.text = text;
        this.a = a;
        this.b = b;
        this.nextQa = nextQa;
        this.nextQb = nextQb;
    }
    public Question(JSONObject json) throws JSONException {
        this.id = json.getInt("id");
        this.text = json.getString("text");
        this.a = json.getString("a");
        this.b = json.getString("b");
        try{
            this.nextQa = json.getInt("nextQa");
        }catch (JSONException jsonException){
            /*In case there is no next chapter*/
            this.nextQa = -1;
        }
        try{
            this.nextQb = json.getInt("nextQb");
        }catch (JSONException jsonException){
            /*In case there is no next chapter*/
            this.nextQb = -1;
        }
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

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public int getNextQa() {
        return nextQa;
    }

    public void setNextQa(int nextQa) {
        this.nextQa = nextQa;
    }

    public int getNextQb() {
        return nextQb;
    }

    public void setNextQb(int nextQb) {
        this.nextQb = nextQb;
    }
}
