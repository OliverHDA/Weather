package ru.oliverhd.weather.network;

/*
* Класс запроса к погодному серверу.
* Сохранён для примера. Удалить, когда станет не нужен.
* */

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;

import ru.oliverhd.weather.BuildConfig;
import ru.oliverhd.weather.R;
import ru.oliverhd.weather.json_model.weather.WeatherRequest;

public class WeatherHandler {

    private static final String TAG = "Weather";





    /*
     * Вариант решения ДЗ3 №1
     * */
    public void getData(String city, final ResultCallback callback) {

        final NetworkHandler networkHandler = new NetworkHandler();

        try {
            final String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", city, BuildConfig.WEATHER_API_KEY);
            final URL uri = new URL(url);
            new Thread(new Runnable() {
                public void run() {
                    String result = networkHandler.getData(uri);
                    Gson gson = new Gson();
                    final WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);
                    callback.onSuccess(Float.toString((weatherRequest.getMain().getTemp() - 273)));
                }
            }).start();
        } catch (MalformedURLException e) {
            Log.e(TAG, "Fail URI", e);
            e.printStackTrace();
        }
    }

    /*
     * Вариант решения ДЗ3 №2
     * */
    public void getData2(String city, final Handler handler, final View view) {

        final NetworkHandler networkHandler = new NetworkHandler();

        try {
            final String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", city, BuildConfig.WEATHER_API_KEY);
            final URL uri = new URL(url);
            new Thread(new Runnable() {
                public void run() {
                    String result = networkHandler.getData(uri);
                    Gson gson = new Gson();
                    final WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            showData(view, Float.toString((weatherRequest.getMain().getTemp() - 273)));
                        }
                    });
                }
            }).start();
        } catch (MalformedURLException e) {
            Log.e(TAG, "Fail URI", e);
            e.printStackTrace();
        }
    }

    public interface ResultCallback {
        void onSuccess(String result);

        void onError(String error);
    }

    void showData(View view, String string) {
        TextView textView = view.findViewById(R.id.temperature_text_view2);
        textView.setText(string);
    }
}
