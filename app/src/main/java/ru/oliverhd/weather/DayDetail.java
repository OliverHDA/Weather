package ru.oliverhd.weather;

public class DayDetail {

    private String time;
    private String temperature;


    public DayDetail(String time, String temperature) {
        this.time = time;
        this.temperature = temperature;
    }

    public String getTime() {
        return time;
    }

    public String getTemperature() {
        return temperature;
    }
}
