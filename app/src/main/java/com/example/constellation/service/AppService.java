package com.example.constellation.service;

import com.example.constellation.dto.CompatibilityDto;
import com.example.constellation.dto.HoroscopeDto;
import com.example.constellation.dto.MoonPhaseDto;
import com.example.constellation.dto.enums.PeriodEnum;
import com.example.constellation.dto.enums.PlanetEnum;
import com.example.constellation.dto.SignDto;
import com.example.constellation.dto.enums.SignEnum;
import com.example.constellation.dto.UserInfoDto;

import java.io.IOException;
import java.net.ConnectException;
import java.util.List;
import java.util.Map;

public interface AppService {

    SignDto getSignInPlanet(UserInfoDto userData, PlanetEnum planet) throws ConnectException;

    SignDto getSignInSun(UserInfoDto userData) throws ConnectException;

    HoroscopeDto getHoroscopeOnDate(UserInfoDto userData, PeriodEnum period) throws ConnectException;

    List<MoonPhaseDto> getMoonPhases(PeriodEnum period, Integer daysCount) throws ConnectException;

    CompatibilityDto getCompatibility(Boolean isUserMale, SignEnum userSign, SignEnum otherSign)
        throws ConnectException;

}
