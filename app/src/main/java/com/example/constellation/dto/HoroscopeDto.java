package com.example.constellation.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class HoroscopeDto {
    private String text;

    private HoroscopeDto(String text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static HoroscopeDto parse(String source) throws JSONException {
        JSONObject json = new JSONObject(source);
        JSONObject data = json.getJSONObject("data");
        String text = data.getString("text");
        return new HoroscopeDto(text);
    }
}
