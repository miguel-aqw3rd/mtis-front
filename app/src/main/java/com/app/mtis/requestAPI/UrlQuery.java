package com.app.mtis.requestAPI;

import com.app.mtis.requestAPI.UrlKeyValue;

import java.util.ArrayList;

public class UrlQuery {
    private ArrayList<UrlKeyValue> queryParameters = new ArrayList<UrlKeyValue>();

    public UrlQuery(){};

    public void put(String key, String value){
        UrlKeyValue keyValue = new UrlKeyValue(key, value);
        this.queryParameters.add(keyValue);
    }
    public String getUrl(){
        String url = "";
        if(this.queryParameters.isEmpty()) return "";
        for (int i = 0; i < this.queryParameters.size(); i++) {
            UrlKeyValue keyValue = this.queryParameters.get(i);
            if(i == 0) url += "?"; else url += "&";
            url += keyValue.getKey()+"="+keyValue.getValue();
        }

        return url;
    }



}
