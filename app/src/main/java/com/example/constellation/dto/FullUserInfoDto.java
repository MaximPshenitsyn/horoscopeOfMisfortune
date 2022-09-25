package com.example.constellation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public final class FullUserInfoDto extends UserInfoDto {

    private final String placeOfBirth;
    private final LocalTime timeOfBirth;

    public FullUserInfoDto(String name, LocalDate dateOfBirth, String placeOfBirth, LocalTime timeOfBirth) {
        super(name, dateOfBirth);
        this.placeOfBirth = placeOfBirth;
        this.timeOfBirth = timeOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public LocalTime getTimeOfBirth() {
        return timeOfBirth;
    }
}
