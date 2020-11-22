package ru.oliverhd.weather;

import android.view.View;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.oliverhd.weather.interfaces.OpenWeather;
import ru.oliverhd.weather.model.WeatherRequest;

public class WeatherRetrofitHandler {

    private static final String TAG = "Weather";
    private OpenWeather openWeather;
    private float AbsoluteZero = -273;

    public void initRetrofit() {
        Retrofit retrofit;
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/") // Базовая часть
                // адреса
                // Конвертер, необходимый для преобразования JSON
                // в объекты
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Создаём объект, при помощи которого будем выполнять запросы
        openWeather = retrofit.create(OpenWeather.class);
    }

    public void requestRetrofit(String city, String keyApi, final View view) {
        final TextView textTemp = view.findViewById(R.id.temperature_text_view);
        System.out.println(city);
        openWeather.loadWeather(city, keyApi)
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                        if (response.body() != null) {
                            float result = response.body().getMain().getTemp() + AbsoluteZero;

                            textTemp.setText(Float.toString(result));
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                        textTemp.setText("Error");
                        System.out.println(t);
                    }
                });
    }
}
