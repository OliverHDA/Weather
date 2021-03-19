package ru.oliverhd.weather.fragments;

/*
* Основной фрагмент для отображения погоды и города.
* */

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import ru.oliverhd.weather.BuildConfig;
import ru.oliverhd.weather.Parcel;
import ru.oliverhd.weather.R;
import ru.oliverhd.weather.db.App;
import ru.oliverhd.weather.db.SearchHistorySource;
import ru.oliverhd.weather.db.dao.HistoryDao;
import ru.oliverhd.weather.db.model.SearchHistory;
import ru.oliverhd.weather.interfaces.Constants;
import ru.oliverhd.weather.network.WeatherHandler;
import ru.oliverhd.weather.network.WeatherRetrofitHandler;

public class MainFragment extends Fragment implements Constants {

    boolean isOrientationLandscape;
    private TextView cityTextView;
    private TextView temperatureTextView;
    private TextView geolocationTextView;
    Parcel currentParcel;
    SearchHistorySource searchHistorySource;
    private static final int PERMISSION_REQUEST_CODE = 10;
    private String geolocationCity;

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
        requestPermissions();

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

        HistoryDao historyDao = App
                .getInstance()
                .getHistoryDao();
        searchHistorySource = new SearchHistorySource(historyDao);
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.city = (String) cityTextView.getText();
        searchHistory.temperature = (String) temperatureTextView.getText();
        searchHistory.date = "Дата";
        searchHistorySource.addSearchHistory(searchHistory);

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
    }

    private void initGui(View view) {
        cityTextView = view.findViewById(R.id.city_text_view);
        temperatureTextView = view.findViewById(R.id.temperature_text_view);
        geolocationTextView = view.findViewById(R.id.geolocation_city_text_view);
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

        geolocationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLocation();
                if (geolocationCity != null) {

                    cityTextView.setText(geolocationCity);
                }
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

    private void requestPermissions() {
        // Проверим, есть ли Permission’ы, и если их нет, запрашиваем их у
        // пользователя
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Запрашиваем координаты
            requestLocation();
        } else {
            // Permission’ов нет, запрашиваем их у пользователя
            requestLocationPermissions();
        }
    }

    // Запрашиваем координаты
    private void requestLocation() {
        // Если Permission’а всё- таки нет, просто выходим: приложение не имеет
        // смысла
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;
        // Получаем менеджер геолокаций
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);

        // Получаем наиболее подходящий провайдер геолокации по критериям.
        // Но определить, какой провайдер использовать, можно и самостоятельно.
        // В основном используются LocationManager.GPS_PROVIDER или
        // LocationManager.NETWORK_PROVIDER, но можно использовать и
        // LocationManager.PASSIVE_PROVIDER - для получения координат в
        // пассивном режиме
        String provider = locationManager.getBestProvider(criteria, true);
        if (provider != null) {
            // Будем получать геоположение через каждые 10 секунд или каждые
            // 10 метров
            locationManager.requestLocationUpdates(provider, 10000, 10, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double lat = location.getLatitude(); // Широта
                    String latitude = Double.toString(lat);


                    double lng = location.getLongitude(); // Долгота
                    String longitude = Double.toString(lng);


                    String accuracy = Float.toString(location.getAccuracy());   // Точность

                    LatLng currentPosition = new LatLng(lat, lng);
                    final Geocoder geocoder = new Geocoder(requireActivity());
                    // Поскольку Geocoder работает по интернету, создаём отдельный поток
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                final List<Address> addresses = geocoder.getFromLocation(currentPosition.latitude, currentPosition.longitude, 1);
                                geolocationTextView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        geolocationTextView.setText(latitude);
                                    }
                                });

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();



                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            });
        }
    }

    // Запрашиваем Permission’ы для геолокации
    private void requestLocationPermissions() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.CALL_PHONE)) {
            // Запрашиваем эти два Permission’а у пользователя
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    PERMISSION_REQUEST_CODE);
        }
    }


    // Результат запроса Permission’а у пользователя:
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {   // Запрошенный нами
            // Permission
            if (grantResults.length == 2 &&
                    (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                // Все препоны пройдены и пермиссия дана
                // Запросим координаты
                requestLocation();
            }
        }
    }



//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        outState.putSerializable(CURRENT_CITY, currentParcel);
//        super.onSaveInstanceState(outState);
}