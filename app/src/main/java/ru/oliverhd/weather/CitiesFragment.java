package ru.oliverhd.weather;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CitiesFragment extends Fragment implements Constants {

    boolean isExistWeatherDetail;
    Parcel currentParcel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cities, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isExistWeatherDetail = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            currentParcel = (Parcel) savedInstanceState.getSerializable(CURRENT_CITY);
        } else {
            currentParcel = new Parcel(0, getResources().getStringArray(R.array.cities)[0]);
        }

        if (isExistWeatherDetail) {
            showWeatherDetail(currentParcel);
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(CURRENT_CITY, currentParcel);
        super.onSaveInstanceState(outState);
    }

    private void initList (View view) {
        LinearLayout layoutView = (LinearLayout) view;
        String[] cities = getResources().getStringArray(R.array.cities);

        for (int i = 0; i < cities.length ; i++) {
            String city = cities[i];
            TextView tv = new TextView(getContext());
            tv.setText(city);
            tv.setTextSize(30);
            layoutView.addView(tv);
            final int fi = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentParcel = new Parcel(fi, getResources().getStringArray(R.array.cities)[fi]);
                    showWeatherDetail (currentParcel);
                }
            });
        }
    }

    public void showWeatherDetail (Parcel parcel) {
        if (isExistWeatherDetail) {
            WeatherDetailFragment detail = (WeatherDetailFragment) getFragmentManager().findFragmentById(R.id.weather_info);
            if (detail == null || detail.getParcel().getImageIndex() != parcel.getImageIndex()) {
                detail = WeatherDetailFragment.create(parcel);

                getFragmentManager().beginTransaction().replace(R.id.weather_info, detail).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            }
        } else {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, WeatherDetailFragment.create(parcel))
                    .addToBackStack(null)
                    .commit();
        }
    }
}