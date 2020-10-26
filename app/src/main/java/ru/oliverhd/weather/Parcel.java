package ru.oliverhd.weather;

import java.io.Serializable;

public class Parcel implements Serializable {

    private final String cityName;

    public Parcel(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }
}
