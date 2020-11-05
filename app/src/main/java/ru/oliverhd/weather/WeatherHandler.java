package ru.oliverhd.weather;


import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import javax.net.ssl.HttpsURLConnection;

import ru.oliverhd.weather.model.WeatherRequest;

public class WeatherHandler {

    final String WEATHER_API_KEY = "dc3945dfcd58d08f6f16096ba342c20a";

    private final String city;
    private float temperature;
    String temp;

    public WeatherHandler(String city) {
        this.city = city;
        try {
            final String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", city, WEATHER_API_KEY);
            final URL uri = new URL(url);
            final Handler handler = new Handler();
            new Thread(new Runnable() {
                public void run() {
                    HttpsURLConnection urlConnection = null;
                    try {
                        urlConnection = (HttpsURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setReadTimeout(10000);

                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        String result = getLines(in);

                        Gson gson = new Gson();
                        final WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);


                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                temperature = weatherRequest.getMain().getTemp();
                            }
                        });


                    } catch (Exception e) {
                        Log.e(TAG, "Fail connection", e);
                        e.printStackTrace();
                    } finally {
                        if (urlConnection != null) {
                            urlConnection.disconnect();
                        }
                    }
                }
            }).start();
        } catch (MalformedURLException e) {
            Log.e(TAG, "Fail URI", e);
            e.printStackTrace();
        }
    }
    private static final String TAG = "Weather";

    public String getTemperature() {

        return temp = String.valueOf(temperature);
    }


//        if (city.equals("Moscow")) {
//            temperature = "10°C";
//            return temperature;
//        }
//        if (city.equals("London")) {
//            temperature = "15°C";
//            return temperature;
//        }
//        if (city.equals("New York")) {
//            temperature = "6°C";
//            return temperature;
//        }
//        if (city.equals("Saint Petersburg")) {
//            temperature = "В Питере отличная погода)";
//            return temperature;
//        }
//        if (city.equals("Voronezh")) {
//            temperature = "-2°C";
//            return temperature;
//        }
//        if (city.equals("Vologda")) {
//            temperature = "3°C";
//            return temperature;
//        }
//        if (city.equals("Penza")) {
//            temperature = "9°C";
//            return temperature;
//        }
//        if (city.equals("Cherepovets")) {
//            temperature = "-1°C";
//            return temperature;
//        } else {
//            temperature = "Погода для города не известна.";
//        }

    private String getLines(BufferedReader in) {
        return in.lines().collect(Collectors.joining("\n"));
    }

}
