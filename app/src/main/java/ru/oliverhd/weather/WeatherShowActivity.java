package ru.oliverhd.weather;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WeatherShowActivity extends AppCompatActivity implements Constants {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_show);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        if (savedInstanceState == null) {
            Parcel parcel = (Parcel) getIntent().getSerializableExtra(WeatherDetailFragment.PARCEL);
            WeatherDetailFragment details = WeatherDetailFragment.create(parcel);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, details).commit();
        }

    }
}