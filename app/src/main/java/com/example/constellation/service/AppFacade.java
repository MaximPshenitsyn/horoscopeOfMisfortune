package com.example.constellation.service;

import android.content.SharedPreferences;

import com.example.constellation.api.controller.ApiController;
import com.example.constellation.dto.CompatibilityDto;
import com.example.constellation.dto.HoroscopeDto;
import com.example.constellation.dto.MoonPhaseDto;
import com.example.constellation.dto.SignDto;
import com.example.constellation.dto.UserInfoDto;
import com.example.constellation.dto.enums.PeriodEnum;
import com.example.constellation.dto.enums.PlanetEnum;
import com.example.constellation.dto.enums.SignEnum;

import org.json.JSONException;

import java.net.ConnectException;
import java.util.List;

public class AppFacade implements AppService {

    private static final String PARSE_EXCEPTION = "Unable to parse response!";

    @Override
    public SignDto getSignInPlanet(UserInfoDto userData, PlanetEnum planet) throws ConnectException {
        return null;
    }

    @Override
    public SignDto getSignInSun(UserInfoDto userData) throws ConnectException {
        try {
            return ApiController.getSignInSun(userData);
        } catch (JSONException e) {
            throw new ConnectException(PARSE_EXCEPTION);
        }
    }

    @Override
    public HoroscopeDto getHoroscopeOnDate(UserInfoDto userData, PeriodEnum period) throws ConnectException {
        try {
            return ApiController.getHoroscopeOnDate(userData, period);
        } catch (JSONException e) {
            throw new ConnectException(PARSE_EXCEPTION);
        }
    }

    @Override
    public List<MoonPhaseDto> getMoonPhases(PeriodEnum period, Integer daysCount) throws ConnectException {
        return null;
    }

    @Override
    public CompatibilityDto getCompatibility(Boolean isUserMale, SignEnum userSign, SignEnum otherSign) throws ConnectException {
        try {
            return ApiController.getCompatibility(isUserMale, userSign, otherSign);
        } catch (JSONException e) {
            throw new ConnectException(PARSE_EXCEPTION);
        }
    }

}
