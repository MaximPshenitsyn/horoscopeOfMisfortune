package com.example.constellation.dto;

import com.example.constellation.dto.enums.ElementEnum;
import com.example.constellation.dto.enums.SignEnum;

import org.json.JSONException;
import org.json.JSONObject;

public class SignDto {
    private SignEnum signName;
    private ElementEnum signElement;
    private String signDesc;

    public SignDto(SignEnum signName, ElementEnum signElement, String signDesc) {
        this.signName = signName;
        this.signElement = signElement;
        this.signDesc = signDesc;
    }

    public SignDto() {

    }

    public static SignDto parse(String source) throws JSONException {
        JSONObject json = new JSONObject(source);
        JSONObject data = json.getJSONObject("data");
        String signNameStr = data.getString("signName");
        String signElementStr = data.getString("signElement");
        String signDescStr = data.getString("signDesc");
        return new SignDto(SignEnum.valueOf(signNameStr), ElementEnum.valueOf(signElementStr), signDescStr);
    }

    public SignEnum getSignName() {
        return signName;
    }

    public ElementEnum getSignElement() {
        return signElement;
    }

    public String getSignDesc() {
        return signDesc;
    }

    public void setSignName(SignEnum signName) {
        this.signName = signName;
    }

    public void setSignElement(ElementEnum signElement) {
        this.signElement = signElement;
    }

    public void setSignDesc(String signDesc) {
        this.signDesc = signDesc;
    }
}
