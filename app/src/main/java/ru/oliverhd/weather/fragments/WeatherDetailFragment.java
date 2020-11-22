package ru.oliverhd.weather.fragments;

/*
* Фрагмент для отображения детальной информации по городу и погоде в нём.
* */

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.oliverhd.weather.BuildConfig;
import ru.oliverhd.weather.DetailAdapter;
import ru.oliverhd.weather.Parcel;
import ru.oliverhd.weather.R;
import ru.oliverhd.weather.interfaces.Constants;
import ru.oliverhd.weather.network.WeatherRetrofitHandler;

public class WeatherDetailFragment extends Fragment implements Constants {

    private TextView cityTextView;
    private TextView temperatureTextView;

    public static WeatherDetailFragment create(Parcel parcel) {
        WeatherDetailFragment fragment = new WeatherDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARCEL, parcel);
        fragment.setArguments(args);
        return fragment;
    }

    public Parcel getParcel() {
        Parcel parcel = (Parcel) getArguments().getSerializable(PARCEL);
        return parcel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initGui(view);

        Parcel parcel = getParcel();
        cityTextView.setText(parcel.getCityName());

        WeatherRetrofitHandler weatherRetrofitHandler = new WeatherRetrofitHandler();
        weatherRetrofitHandler.initRetrofit();
        weatherRetrofitHandler.requestRetrofit((String) cityTextView.getText(), BuildConfig.WEATHER_API_KEY, view);

        String[] data = getResources().getStringArray(R.array.time);

        RecyclerView recyclerView = view.findViewById(R.id.days_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);

        DetailAdapter adapter = new DetailAdapter(data);
        recyclerView.setAdapter(adapter);
    }

    private void initGui(View view) {
        cityTextView = view.findViewById(R.id.city);
        temperatureTextView = view.findViewById(R.id.temperature_text_view);
    }
}