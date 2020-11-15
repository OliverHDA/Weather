package ru.oliverhd.weather;

/*
* Обработка запроса к погодному серверу в отдельном классе.*
* Не работает, надо исправить.
* */

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import ru.oliverhd.weather.model.WeatherRequest;

public class WeatherHandler {

    private static final String TAG = "Weather";
    float temperature;

    public float getTemperature(String city) {

        try {
            final String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", city, BuildConfig.WEATHER_API_KEY);
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
                                temperature = (weatherRequest.getMain().getTemp() - 273);
                            }
                        });


                    } catch (Exception e) {
                        Log.e(TAG, "Fail connection", e);
//                        вызов диалогового окна при дисконекте.
//                        DialogConnectionErrorFragment dialogFragment =
//                                DialogConnectionErrorFragment.newInstance();
//                        dialogFragment.show(getFragmentManager(),
//                                "dialog_fragment");

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
        return temperature;
    }

    private String getLines(BufferedReader in) {
        return in.lines().collect(Collectors.joining("\n"));
    }
}
