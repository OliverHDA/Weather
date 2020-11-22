package ru.oliverhd.weather.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.oliverhd.weather.model.WeatherRequest;

public interface OpenWeather {

    @GET ("/data/2.5/weather")
    Call <WeatherRequest> loadWeather (@Query("q") String cityName, @Query("appid") String appID);
}
