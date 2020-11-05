package ru.oliverhd.weather;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainFragment extends Fragment implements Constants {

    boolean isOrientationLandscape;
    Parcel currentParcel;

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




        TextView temperatureView = view.findViewById(R.id.temperature_text_view);
        WeatherHandler weatherHandler = new WeatherHandler((String) cityView.getText());
        temperatureView.setText(weatherHandler.getTemperature());
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