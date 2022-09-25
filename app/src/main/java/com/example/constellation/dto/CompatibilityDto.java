package com.example.constellation.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class CompatibilityDto {

    private final int percentage;
    private final String happinessInMarriage;
    private final String goodLuckCompatibility;
    private final String sexualCompatibility;
    private final String familyCompatibility;
    private final String forKidsCompatibility;

    private CompatibilityDto(int percentage, String happinessInMarriage,
                            String goodLuckCompatibility, String sexualCompatibility,
                            String familyCompatibility, String forKidsCompatibility) {
        this.percentage = percentage;
        this.happinessInMarriage = happinessInMarriage;
        this.goodLuckCompatibility = goodLuckCompatibility;
        this.sexualCompatibility = sexualCompatibility;
        this.familyCompatibility = familyCompatibility;
        this.forKidsCompatibility = forKidsCompatibility;
    }

    public int getPercentage() {
        return percentage;
    }

    public String getHappinessInMarriage() {
        return happinessInMarriage;
    }

    public String getGoodLuckCompatibility() {
        return goodLuckCompatibility;
    }

    public String getSexualCompatibility() {
        return sexualCompatibility;
    }

    public String getFamilyCompatibility() {
        return familyCompatibility;
    }

    public String getForKidsCompatibility() {
        return forKidsCompatibility;
    }

    public static CompatibilityDto parse(String source) throws JSONException {
        JSONObject json = new JSONObject(source);
        JSONObject data = json.getJSONObject("data");
        return new CompatibilityDto(data.getInt("percentage"),
                data.getString("happinessInMarriage"),
                data.getString("goodLuckCompatibility"),
                data.getString("sexualCompatibility"),
                data.getString("familyCompatibility"),
                data.getString("forKidsCompatibility"));
    }
}
