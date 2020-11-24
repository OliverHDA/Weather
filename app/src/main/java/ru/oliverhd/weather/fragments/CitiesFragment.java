package ru.oliverhd.weather.fragments;

/*
 * Фрагмент для отображения списка городов
 * */

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.oliverhd.weather.CityAdapter;
import ru.oliverhd.weather.Parcel;
import ru.oliverhd.weather.R;
import ru.oliverhd.weather.interfaces.Constants;

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

        String[] data = getResources().getStringArray(R.array.cities);
        initRecyclerView(data, view);
//        initList(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isExistWeatherDetail = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            currentParcel = (Parcel) savedInstanceState.getSerializable(CURRENT_CITY);
        } else {
            currentParcel = new Parcel(getResources().getStringArray(R.array.cities)[1]);
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

    private void initRecyclerView(String[] data, View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_city);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        CityAdapter adapter = new CityAdapter(data);
        recyclerView.setAdapter(adapter);
        adapter.SetOnItemClickListener(new CityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String city) {
                currentParcel = new Parcel(city);
                if (isExistWeatherDetail) {
                    showWeatherDetail(currentParcel);
                } else {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, MainFragment.create(currentParcel))
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }

//    private void initList(View view) {
//        LinearLayout layoutView = (LinearLayout)view;
//        String[] cities = getResources().getStringArray(R.array.cities);
//
//        LayoutInflater ltInflater = getLayoutInflater();
//
//        for(int i=0; i < cities.length; i++){
//            String city = cities[i];
//
//            View item = ltInflater.inflate(R.layout.item, layoutView, false);
//
//            TextView tv = item.findViewById(R.id.textView);
//            tv.setText(city);
//            layoutView.addView(item);
//            final int fi = i;
//            tv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    currentParcel = new Parcel(fi, getResources().getStringArray(R.array.cities)[fi]);
////                    showWeatherDetail(currentParcel);
//                }
//            });
//        }
//    }

    public void showWeatherDetail(Parcel parcel) {
        if (isExistWeatherDetail) {
            WeatherDetailFragment detail = (WeatherDetailFragment) getFragmentManager().findFragmentById(R.id.temperature_text_view);
            if (detail == null) {
                detail = WeatherDetailFragment.create(parcel);

                getFragmentManager().
                        beginTransaction()
                        .replace(R.id.temperature_text_view, detail)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.temperature_text_view, WeatherDetailFragment.create(parcel))
                        .addToBackStack(null)
                        .commit();
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