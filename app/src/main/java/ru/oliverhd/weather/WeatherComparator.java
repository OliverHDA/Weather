package ru.oliverhd.weather;


public class WeatherComparator {

    private final String city;
    private String temperature;

    public WeatherComparator(String city) {
        this.city = city;
        this.temperature = temperature;
    }

    public String getTemperature() {
        if (city.equals("Moscow")) {
            temperature = "10°C";
            return temperature;
        }
        if (city.equals("London")) {
            temperature = "15°C";
            return temperature;
        }
        if (city.equals("New York")) {
            temperature = "6°C";
            return temperature;
        }
        if (city.equals("Saint Petersburg")) {
            temperature = "В Питере отличная погода)";
            return temperature;
        }
        if (city.equals("Voronezh")) {
            temperature = "-2°C";
            return temperature;
        }
        if (city.equals("Vologda")) {
            temperature = "3°C";
            return temperature;
        }
        if (city.equals("Penza")) {
            temperature = "9°C";
            return temperature;
        }
        if (city.equals("Cherepovets")) {
            temperature = "-1°C";
            return temperature;
        } else {
            temperature = "Погода для города не известна.";
        }
        return temperature;
    }
}
