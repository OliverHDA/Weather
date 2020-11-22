package ru.oliverhd.weather;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.oliverhd.weather.interfaces.Constants;
import ru.oliverhd.weather.interfaces.OpenWeather;
import ru.oliverhd.weather.model.WeatherRequest;

public class MainFragment extends Fragment implements Constants {

    boolean isOrientationLandscape;
    private TextView cityTextView;
    private TextView temperatureTextView;
    Parcel currentParcel;
    private OpenWeather openWeather;
    private float AbsoluteZero = -273;
    private static final String TAG = "Weather";

    public static MainFragment create(Parcel parcel) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARCEL, parcel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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


        initGui(view);
        initEvents();

        if (getArguments() != null) {
            currentParcel = getParcel();
            cityTextView.setText(currentParcel.getCityName());
        }
        currentParcel = new Parcel((String) cityTextView.getText());

        WeatherRetrofitHandler weatherRetrofitHandler = new WeatherRetrofitHandler();
        weatherRetrofitHandler.initRetrofit();
        weatherRetrofitHandler.requestRetrofit((String) cityTextView.getText(), BuildConfig.WEATHER_API_KEY, view);
        
        final Handler handler = new Handler();
        WeatherHandler weatherHandler = new WeatherHandler();

        /*
         * Вариант решения ДЗ3 №2
         * */
        weatherHandler.getData2((String) cityTextView.getText(), handler, view);

        /*
         * Вариант решения ДЗ3 №1
         * */
//        weatherHandler.getData((String) cityTextView.getText(), new WeatherHandler.ResultCallback() {
//            @Override
//            public void onSuccess(final String result) {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        temperatureTextView.setText(result);
//                    }
//                });
//            }
//
//            @Override
//            public void onError(String error) {
//
//            }
//        });

//        try {
//            final String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", cityTextView.getText(), BuildConfig.WEATHER_API_KEY);
//            final URL uri = new URL(url);
//            final Handler handler = new Handler();
//            new Thread(new Runnable() {
//                public void run() {
//                    HttpsURLConnection urlConnection = null;
//                    try {
//                        urlConnection = (HttpsURLConnection) uri.openConnection();
//                        urlConnection.setRequestMethod("GET");
//                        urlConnection.setReadTimeout(10000);
//
//                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//                        String result = getLines(in);
//
//                        Gson gson = new Gson();
//                        final WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                temperatureTextView.setText(Float.toString((weatherRequest.getMain().getTemp() - 273)));
//                            }
//                        });
//
//
//                    } catch (Exception e) {
//                        Log.e(TAG, "Fail connection", e);
//                        DialogConnectionErrorFragment dialogFragment =
//                                DialogConnectionErrorFragment.newInstance();
//                        dialogFragment.show(getFragmentManager(),
//                                "dialog_fragment");
//
//                        e.printStackTrace();
//                    } finally {
//                        if (urlConnection != null) {
//                            urlConnection.disconnect();
//                        }
//                    }
//                }
//            }).start();
//        } catch (MalformedURLException e) {
//            Log.e(TAG, "Fail URI", e);
//
//            e.printStackTrace();
//        }

    }

    private void initGui(View view) {
        cityTextView = view.findViewById(R.id.city_text_view);
        temperatureTextView = view.findViewById(R.id.temperature_text_view);
    }

    private void initEvents() {
        cityTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new CitiesFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        temperatureTextView.setOnClickListener(new View.OnClickListener() {
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

    public Parcel getParcel() {
        if (getArguments().getSerializable(PARCEL) != null) {
            Parcel parcel = (Parcel) getArguments().getSerializable(PARCEL);
            return parcel;
        }
        return new Parcel("Saint Petersburg");
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
//
//    private String getLines(BufferedReader in) {
//        return in.lines().collect(Collectors.joining("\n"));
//    }
}