package com.example.constellation.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class UserInfoDto implements Serializable {
    private final String name;
    private final LocalDate dateOfBirth;

    public UserInfoDto(String name, LocalDate dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public final String getName() {
        return name;
    }

    public final LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
}
