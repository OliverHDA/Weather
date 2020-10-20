package ru.oliverhd.weather;

import java.io.Serializable;

public class Parcel implements Serializable {

    private final int imageIndex;
    private final String cityName;

    public Parcel(int imageIndex, String cityName) {
        this.imageIndex = imageIndex;
        this.cityName = cityName;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public String getCityName() {
        return cityName;
    }


}
