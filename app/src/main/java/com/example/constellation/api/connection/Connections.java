package com.example.constellation.api.connection;

import com.example.constellation.dto.enums.PeriodEnum;
import com.example.constellation.dto.enums.SignEnum;
import com.example.constellation.dto.UserInfoDto;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

public final class Connections {
    private static final String SERVER_URL = "http://79.139.195.61:1234";
    // private static final String SERVER_URL = "http://127.0.0.1:5000";

    public static final String KEY_BIRTH = "birthDate";
    public static final String KEY_ON_DATE = "onDate";
    public static final String KEY_MAN_SIGN = "manSign";
    public static final String KEY_WOMAN_SIGN = "womanSign";

    public static String getSignInSun(UserInfoDto userData) throws ConnectException {
        String url = SERVER_URL + "/sign/sun";
        return RequestController.postRequest(url, formSignInSun(userData));
    }

    public static String getHoroscopeOnDate(UserInfoDto userData, PeriodEnum onDate)
            throws ConnectException {
        String url = String.join("/", SERVER_URL, "horoscope");
        return RequestController.postRequest(url, formHoroscope(userData, onDate));
    }

    public static String getMoonPhases(PeriodEnum period, Integer daysCount) throws ConnectException {
        throw new ConnectException("Not available yet");
    }

    public static String getCompatibility(Boolean isUserMale, SignEnum userSign, SignEnum otherSign)
            throws ConnectException {
        String url = String.join("/", SERVER_URL, "compatibility");
        return RequestController.postRequest(url, formCompatibility(isUserMale, userSign, otherSign));
    }

    private static Map<String, String> formSignInSun(UserInfoDto userData) {
        Map<String, String> postParams = new HashMap<>();
        postParams.put(KEY_BIRTH, userData.getDateOfBirth().toString());
        return postParams;
    }

    private static Map<String, String> formHoroscope(UserInfoDto userData, PeriodEnum period) {
        Map<String, String> postParams = new HashMap<>();
        postParams.put(KEY_BIRTH, userData.getDateOfBirth().toString());
        postParams.put(KEY_ON_DATE, Integer.valueOf(period.ordinal()).toString());
        return postParams;
    }

    private static Map<String, String> formCompatibility(Boolean isUserMale, SignEnum user, SignEnum other) throws ConnectException {
        Map<String, String> postParams = new HashMap<>();
        if (user == null) {
            throw new ConnectException("User is null!");
        }
        String manSign = Integer.valueOf(user.ordinal()).toString();
        String womanSign = Integer.valueOf(other.ordinal()).toString();
        if (!isUserMale) {
            manSign = Integer.valueOf(other.ordinal()).toString();
            womanSign = Integer.valueOf(user.ordinal()).toString();
        }
        postParams.put(KEY_MAN_SIGN, manSign);
        postParams.put(KEY_WOMAN_SIGN, womanSign);
        return postParams;
    }
}
