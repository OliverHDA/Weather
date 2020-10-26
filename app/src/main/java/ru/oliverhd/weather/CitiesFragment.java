package ru.oliverhd.weather;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CitiesFragment extends Fragment implements Constants {

    boolean isExistWeatherDetail;
    Parcel currentParcel;

    public static CitiesFragment create(Parcel parcel) {
        CitiesFragment fragment = new CitiesFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARCEL, parcel);
        fragment.setArguments(args);
        return fragment;
    }

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
            currentParcel = new Parcel(getResources().getStringArray(R.array.cities)[0]);
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
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        SocnetAdapter adapter = new SocnetAdapter(data);
        recyclerView.setAdapter(adapter);
        adapter.SetOnItemClickListener(new SocnetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String city) {
                currentParcel = new Parcel(city);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, MainFragment.create(currentParcel))
                        .addToBackStack(null)
                        .commit();
//                showWeatherDetail(currentParcel);
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
            WeatherDetailFragment detail = (WeatherDetailFragment) getFragmentManager().findFragmentById(R.id.weather_info);
            if (detail == null) {
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