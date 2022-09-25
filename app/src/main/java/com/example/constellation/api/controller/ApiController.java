package com.example.constellation.api.controller;

import com.example.constellation.api.connection.Connections;
import com.example.constellation.dto.CompatibilityDto;
import com.example.constellation.dto.HoroscopeDto;
import com.example.constellation.dto.MoonPhaseDto;
import com.example.constellation.dto.enums.PeriodEnum;
import com.example.constellation.dto.SignDto;
import com.example.constellation.dto.enums.SignEnum;
import com.example.constellation.dto.UserInfoDto;

import org.json.JSONException;

import java.net.ConnectException;
import java.util.List;

public class ApiController {

    public static SignDto getSignInSun(UserInfoDto userData) throws ConnectException, JSONException {
        return SignDto.parse(Connections.getSignInSun(userData));
    }

    public static HoroscopeDto getHoroscopeOnDate(UserInfoDto userData, PeriodEnum period)
            throws ConnectException, JSONException {
        return HoroscopeDto.parse(Connections.getHoroscopeOnDate(userData, period));
    }

    public static List<MoonPhaseDto> getMoonPhases(PeriodEnum period, Integer daysCount)
            throws ConnectException {
        throw new ConnectException();
    }

    public static CompatibilityDto getCompatibility(Boolean isUserMale, SignEnum userSign,
                                                    SignEnum otherSign) throws ConnectException, JSONException {
        return CompatibilityDto.parse(Connections.getCompatibility(isUserMale, userSign, otherSign));
    }

}
