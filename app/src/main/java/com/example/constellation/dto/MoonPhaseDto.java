package com.example.constellation.dto;

import com.example.constellation.dto.enums.SignEnum;

import java.time.LocalDate;

public class MoonPhaseDto {
    LocalDate date;
    SignEnum signInMoon;
    String phaseName;
    String phaseDesc;
}
