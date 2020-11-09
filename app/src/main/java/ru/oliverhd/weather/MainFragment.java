package ru.oliverhd.weather;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import ru.oliverhd.weather.model.WeatherRequest;

public class MainFragment extends Fragment implements Constants {

    boolean isOrientationLandscape;
    Parcel currentParcel;
    private static final String TAG = "Weather";

    public static MainFragment create(Parcel parcel) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARCEL, parcel);
        fragment.setArguments(args);
        return fragment;
    }

    public Parcel getParcel() {
        if (getArguments().getSerializable(PARCEL) != null) {
            Parcel parcel = (Parcel) getArguments().getSerializable(PARCEL);
            return parcel;
        }
        return new Parcel("Saint Petersburg");
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView cityView = view.findViewById(R.id.city_text_view);
        cityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new CitiesFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        if (getArguments() != null) {
            currentParcel = getParcel();
            cityView.setText(currentParcel.getCityName());
        }
            currentParcel = new Parcel((String) cityView.getText());




        final TextView temperatureView = view.findViewById(R.id.temperature_text_view);
        try {
            final String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", cityView.getText(), BuildConfig.WEATHER_API_KEY);
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
                                temperatureView.setText(Float.toString((weatherRequest.getMain().getTemp() - 273)));
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

        temperatureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, WeatherDetailFragment.create(currentParcel))
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private String getLines(BufferedReader in) {
        return in.lines().collect(Collectors.joining("\n"));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isOrientationLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            currentParcel = (Parcel) savedInstanceState.getSerializable(CURRENT_CITY);
        }
//        else {
//            currentParcel = new Parcel(getResources().getStringArray(R.array.cities)[0]);
//        }

//        if (isOrientationLandscape) {
//            showWeatherDetail(currentParcel);
//        }

    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        outState.putSerializable(CURRENT_CITY, currentParcel);
//        super.onSaveInstanceState(outState);
//    }
}