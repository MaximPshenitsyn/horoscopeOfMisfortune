package com.example.constellation.dto;

public class HoroscopeRequestDto {
    private String dateOfBirth;
    private int period;

    public HoroscopeRequestDto(String dateOfBirth, int period) {
        this.dateOfBirth = dateOfBirth;
        this.period = period;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
}
