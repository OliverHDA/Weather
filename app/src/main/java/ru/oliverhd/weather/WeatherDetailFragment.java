package ru.oliverhd.weather;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherDetailFragment extends Fragment{

    public static final String PARCEL = "parcel";

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

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textView = view.findViewById(R.id.weather_info);

        TypedArray imgs = getResources().obtainTypedArray(R.array.cities_img);
        Parcel parcel = getParcel();

        imageView.setImageResource(imgs.getResourceId(parcel.getImageIndex(),-1));
        textView.setText("Погода в Москве просто огонь!");
    }
}