package com.app.mtis.requestAPI;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.app.mtis.models.Entry;
import com.app.mtis.models.EntryGroup;
import com.app.mtis.models.EntryGroupAbridged;
import com.app.mtis.models.Goal;
import com.app.mtis.models.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VolleyBall {
    private static final String server = "http://10.0.2.2:8000/";
    private RequestQueue queue;
    private Context context;

    private String auxString;
    private static Entry entry;
    private static ArrayList<Entry> entries;
    private static EntryGroup entryGroup;
    private static ArrayList<EntryGroup> entryGroups;
    private static ArrayList<EntryGroupAbridged> entryGroupsAbridged;
    private static Goal goal;
    private static ArrayList<Goal> goals;
    private static Question question;

    public VolleyBall(Context context){
        this.context=context;
        this.queue = Volley.newRequestQueue(this.context);
    }
    public interface VolleyCallback {
        void onSuccess();
        void onError(VolleyError error);
    }
    private String getAuthToken(){ // Function that returns the current locally saved token
        SharedPreferences prefs = context.getSharedPreferences("SESSIONS_APP_PREFS", MODE_PRIVATE);
        return prefs.getString("auth-token", "randomtoken");
    }
    private void setAuthToken(String token){
        SharedPreferences preferences = context.getSharedPreferences("SESSIONS_APP_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("auth-token", token);
        editor.apply();
    }
    public int getStatus(VolleyError error){
        if(error.networkResponse == null) return -1;
        int status = error.networkResponse.statusCode;
        return status;
    }


    public void getQuestion(int questionId, final VolleyCallback callback){
        String url = server + "api/v1/question/" + questionId;
        JsonObjectRequest requestQuestion = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            question = new Question(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        callback.onError(error);
                    }
                }
        );
        this.queue.add(requestQuestion);

    }
    public void postAnswer(int questionId, String answer, final VolleyCallback callback){
        String url = server + "api/v1/answer/" + questionId + "?answer=" + answer;
        JsonObjectRequest requestAnswer = new JsonObjectRequest(
                Request.Method.POST,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        callback.onError(error);
                    }
                }
        ){// Pass on headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("auth-token", getAuthToken());
                return headerMap;
            }
        };
        this.queue.add(requestAnswer);
    }
    public void getEntries(final VolleyCallback callback){
        String url = server + "api/v1/entries";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray data;
                        try {
                            data = response.getJSONArray("entries");
                            setEntries(data);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        callback.onError(error);
                    }
                }
        ){// Pass on headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("auth-token", getAuthToken());
                return headerMap;
            }
        };
        this.queue.add(request);
    }
    public void getEntry(int entryId, final VolleyCallback callback){
        String url = server + "api/v1/entry/"+entryId;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            entry = new Entry(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        callback.onError(error);
                    }
                }
        ){// Pass on headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("auth-token", getAuthToken());
                return headerMap;
            }
        };
        this.queue.add(request);
    }
    public void deleteEntry(int entryId, final VolleyCallback callback){
        String url = server + "api/v1/entry/"+entryId;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        callback.onError(error);
                    }
                }
        ){// Pass on headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("auth-token", getAuthToken());
                return headerMap;
            }
        };
        this.queue.add(request);
    }
    public void postEntry(Entry newEntry, final VolleyCallback callback) throws JSONException {
        postEntry(newEntry, -1, callback);
    }
    public void postEntry(Entry newEntry, int entryGroupId, final VolleyCallback callback) throws JSONException {
        String url = server + "api/v1/entry";
        if(entryGroupId!=-1) url+="?entrygroup="+entryGroupId;
        JSONObject body = newEntry.toJSON();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        callback.onError(error);
                    }
                }
        ){// Pass on headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("auth-token", getAuthToken());
                return headerMap;
            }
        };
        this.queue.add(request);

    }
    public void getEntryGroup(int entryGroupId, final VolleyCallback callback){
        String url = server + "api/v1/entrygroup/"+entryGroupId;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            entryGroup = new EntryGroup(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        callback.onError(error);
                    }
                }
        ){// Pass on headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("auth-token", getAuthToken());
                return headerMap;
            }
        };
        this.queue.add(request);
    }
    public void deleteEntryGroup(int entryGroupId, final VolleyCallback callback){
        String url = server + "api/v1/entrygroup/"+entryGroupId;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        callback.onError(error);
                    }
                }
        ){// Pass on headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("auth-token", getAuthToken());
                return headerMap;
            }
        };
        this.queue.add(request);
    }
    public void postEntryGroup(int entryId, final VolleyCallback callback){
        String url = server + "api/v1/entrygroup/entry/"+entryId;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Receive new entry group id ???

                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        callback.onError(error);
                    }
                }
        ){// Pass on headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("auth-token", getAuthToken());
                return headerMap;
            }
        };
        this.queue.add(request);
    }
    public void putMainEntry(int entryId, int entryGroupId, final VolleyCallback callback){
        String url = server + "api/v1/entrygroup/"+entryGroupId+"/mainentry/"+entryId;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        callback.onError(error);
                    }
                }
        ){// Pass on headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("auth-token", getAuthToken());
                return headerMap;
            }
        };
        this.queue.add(request);
    }
    public void putEntryGroupFavorite(int entryGroupId, final VolleyCallback callback){
        String url = server + "api/v1/entrygroup/"+entryGroupId+"/favorite";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        callback.onError(error);
                    }
                }
        ){// Pass on headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("auth-token", getAuthToken());
                return headerMap;
            }
        };
        this.queue.add(request);
    }
    public void getEntryGroups(final VolleyCallback callback){
        getEntryGroups(-1, -1, callback);
    }
    public void getEntryGroups(int level, final VolleyCallback callback){
        getEntryGroups(level, -1, callback);
    }
    public void getEntryGroups(final VolleyCallback callback, int favorites){
        getEntryGroups(-1, favorites, callback);
    }
    // -1 => None, 0 => False, 1 => True
    public void getEntryGroups(int level, int favorites, final VolleyCallback callback){
        String url = server + "api/v1/entrygroups";
        UrlQuery query = new UrlQuery();
        if (favorites == 1) query.put("favorites", "true");
        else if (favorites == 0) query.put("favorites", "false");
        if (level != -1) query.put("level", Integer.toString(level));
        url += query.getUrl();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray data;
                        try {
                            data = response.getJSONArray("entrygroups");
                            setEntryGroupsAbridged(data);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        callback.onError(error);
                    }
                }
        ){// Pass on headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("auth-token", getAuthToken());
                return headerMap;
            }
        };
        this.queue.add(request);
    }
    public void postChallenge(int entryId, int[] entryIdsList, final VolleyCallback callback){
        String url = server+"api/v1/challenge/"+entryId+"?to=";
        for (int i = 0; i < entryIdsList.length; i++) {
            url += entryIdsList[i];
            if(i!=entryIdsList.length-1) url += "+";
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        callback.onError(error);
                    }
                }
        ){// Pass on headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("auth-token", getAuthToken());
                return headerMap;
            }
        };
        this.queue.add(request);
    }
    public void postGoal(Goal newGoal, final VolleyCallback callback) throws JSONException {
        postGoal(newGoal, -1, callback);
    }
    public void postGoal(Goal newGoal, int entryId, final VolleyCallback callback) throws JSONException {
        String url = server + "api/v1/goal";
        if(entryId!=-1) url+="?entry="+entryId;
        JSONObject body = newGoal.toJSON();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }
        ){// Pass on headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("auth-token", getAuthToken());
                return headerMap;
            }
        };
        this.queue.add(request);
    }
    public void getGoal(int goalId, final VolleyCallback callback){
        String url = server + "api/v1/goal/"+goalId;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            goal = new Goal(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }
        ){// Pass on headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("auth-token", getAuthToken());
                return headerMap;
            }
        };
        this.queue.add(request);
    }
    public void putGoal(int goalId, Goal newGoal, final VolleyCallback callback) throws JSONException {
        String url = server + "api/v1/goal/"+goalId;
        JSONObject body = newGoal.toJSON();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }
        ){// Pass on headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("auth-token", getAuthToken());
                return headerMap;
            }
        };
        this.queue.add(request);
    }
    public void deleteGoal(int goalId, final VolleyCallback callback){
        String url = server + "api/v1/goal/"+goalId;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }
        ){// Pass on headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("auth-token", getAuthToken());
                return headerMap;
            }
        };
        this.queue.add(request);
    }
    public void getGoals(final VolleyCallback callback){
        getGoals(-1, -1, -1, callback);
    }
    public void getGoals(final VolleyCallback callback, int favorite){
        getGoals(-1, -1, favorite, callback);
    }
    public void getGoals(int entryId, final VolleyCallback callback){
        getGoals(entryId, -1, -1, callback);
    }
    public void getGoals(int entryId, final VolleyCallback callback, int favorite){
        getGoals(entryId, -1, favorite, callback);
    }
    // -1 => None, 0 => False, 1 => True
    public void getGoals(int entryId, int active, int favorite, final VolleyCallback callback){
        String url = server + "api/v1/goals";
        UrlQuery query = new UrlQuery();
        if (entryId != -1) query.put("entry", Integer.toString(entryId));
        if (favorite == 1) query.put("favorite", "true");
        else if (favorite == 0) query.put("favorite", "false");
        if (active == 1) query.put("active", "true");
        else if (active == 0) query.put("active", "false");
        url += query.getUrl();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray data;
                        try {
                            data = response.getJSONArray("goals");
                            setGoals(data);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }
        ){// Pass on headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("auth-token", getAuthToken());
                return headerMap;
            }
        };
        this.queue.add(request);
    }



    public void signUp(String username, String password, final VolleyCallback callback) throws JSONException{
        signUp(username, "", password, callback);
    }
    public void signUp(String username, String email, String password, final VolleyCallback callback) throws JSONException {
        String url = server + "api/v1/signup";
        JSONObject body = new JSONObject();
        body.put("username", username);
        if(!email.equals("")) body.put("email", email);
        body.put("password", password);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            setAuthToken(response.getString("token"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }
        );
        this.queue.add(request);
    }
    public void logIn(String username, String password, final VolleyCallback callback) throws JSONException {
        String url = server + "api/v1/login";
        JSONObject body = new JSONObject();
        body.put("username", username);
        body.put("password", password);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            setAuthToken(response.getString("token"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }
        );
        this.queue.add(request);
    }
    public void logOut(final VolleyCallback callback){
        String url = server + "api/v1/logout";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }
        ){// Pass on headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("auth-token", getAuthToken());
                return headerMap;
            }
        };
        this.queue.add(request);
    }
    public void changeName(String newName, final VolleyCallback callback) throws JSONException {
        String url = server + "api/v1/name";
        JSONObject body = new JSONObject();
        body.put("name", newName);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }
        ){// Pass on headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("auth-token", getAuthToken());
                return headerMap;
            }
        };
        this.queue.add(request);
    }


    public static void setEntries(JSONArray array) throws JSONException {
        VolleyBall.entries = new ArrayList<Entry>();
        for (int i = 0; i < array.length(); i++) {
            VolleyBall.entries.add(new Entry((JSONObject) array.get(i)));
        }
    }
    public static void setEntryGroups(JSONArray array) throws JSONException {
        VolleyBall.entryGroups = new ArrayList<EntryGroup>();
        for (int i = 0; i < array.length(); i++) {
            VolleyBall.entryGroups.add(new EntryGroup((JSONObject) array.get(i)));
        }
    }
    public static void setEntryGroupsAbridged(JSONArray array) throws JSONException {
        VolleyBall.entryGroupsAbridged = new ArrayList<EntryGroupAbridged>();
        for (int i = 0; i < array.length(); i++) {
            VolleyBall.entryGroupsAbridged.add(new EntryGroupAbridged((JSONObject) array.get(i)));
        }
    }
    public static void setGoals(JSONArray array) throws JSONException {
        VolleyBall.goals = new ArrayList<Goal>();
        for (int i = 0; i < array.length(); i++) {
            VolleyBall.goals.add(new Goal((JSONObject) array.get(i)));
        }
    }

    public String getAuxString() {
        return auxString;
    }

    public static Entry getEntry() {
        return entry;
    }

    public static ArrayList<Entry> getEntries() {
        return entries;
    }

    public static EntryGroup getEntryGroup() {
        return entryGroup;
    }

    public static ArrayList<EntryGroup> getEntryGroups() {
        return entryGroups;
    }

    public static ArrayList<EntryGroupAbridged> getEntryGroupsAbridged() {
        return entryGroupsAbridged;
    }

    public static Goal getGoal() {
        return goal;
    }

    public static ArrayList<Goal> getGoals() {
        return goals;
    }

    public static Question getQuestion() {
        return question;
    }

    /*
    private Request(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized Request getInstance(Context context) {
        if (instance == null) {
            instance = new Request(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public void get(String url, final VolleyCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> callback.onSuccess(response),
                error -> callback.onError(error));
        addToRequestQueue(stringRequest);
    }

    public void post(String url, JSONObject params, final VolleyCallback callback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, params,
                response -> callback.onSuccess(response.toString()),
                error -> callback.onError(error));
        addToRequestQueue(jsonObjectRequest);
    }

    // Add more methods for other types of requests as needed

    public interface VolleyCallback {
        void onSuccess(String response);
        void onError(Exception error);
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }
*/


}
