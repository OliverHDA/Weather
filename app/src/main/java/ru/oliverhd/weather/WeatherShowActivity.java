package ru.oliverhd.weather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherShowActivity extends AppCompatActivity implements Constants {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_show);

        Parcel parcel = (Parcel) getIntent().getExtras().getSerializable(TEXT);

        final TextView textView = findViewById(R.id.show_weather);
        ImageView imageView = findViewById(R.id.background);

        String s = String.format("The temperature in %s is 15°C", parcel.text);

        switch (parcel.text) {
            case "Moscow":
            case "Москва":
                textView.setText(s);
                imageView.setImageResource(R.drawable.moscow);
                break;
            case "London":
            case "Лондон":
                textView.setText(s);
                imageView.setImageResource(R.drawable.london);
                break;
            case "New York":
            case "Нью-Йорк":
                textView.setText(s);
                imageView.setImageResource(R.drawable.new_york);
                break;
        }

        Button buttonBack = findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentResult = new Intent();
                intentResult.putExtra(RESULT_TEXT, textView.getText().toString());
                setResult(RESULT_OK, intentResult);
                finish();
            }
        });



    }
}