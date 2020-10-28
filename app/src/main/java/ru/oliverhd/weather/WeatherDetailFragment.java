package ru.oliverhd.weather;

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

public class WeatherDetailFragment extends Fragment implements Constants {

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

        TextView textView = view.findViewById(R.id.weather_info);
        TextView city = view.findViewById(R.id.city);
        Parcel parcel = getParcel();
        city.setText(parcel.getCityName());

        WeatherComparator weatherComparator = new WeatherComparator(parcel.getCityName());
        textView.setText(weatherComparator.getTemperature());

        String[] data = getResources().getStringArray(R.array.time);

        RecyclerView recyclerView = view.findViewById(R.id.days_recycler);
//        GridLayoutManager gridLayout = new GridLayoutManager(getActivity(), GridLayoutManager.DEFAULT_SPAN_COUNT, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);

        DetailAdapter adapter = new DetailAdapter(data);
        recyclerView.setAdapter(adapter);
    }
}