package ru.oliverhd.weather;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements Constants {

    private final static int REQUEST_CODE = 101;

    DateFormat dateFormat = DateFormat.getDateTimeInstance();
    Date date = new Date();
    final MainPresenter presenter = MainPresenter.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textCounter = findViewById(R.id.textCounter);
        textCounter.setText(String.valueOf(presenter.getCounter()));

        String instanceState;
        if (savedInstanceState == null) {
            instanceState = "Первый запуск!";
        } else {
            instanceState = "Повторный запуск!";
        }
        Log.d("MainActivity", instanceState + " - onCreate()");

        Button button1 = findViewById(R.id.button_moscow);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicker(R.drawable.moscow, R.string.moscow);
            }
        });

        Button button2 = findViewById(R.id.button_london);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicker(R.drawable.london, R.string.london);
            }
        });

        Button button3 = findViewById(R.id.button_ny);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicker(R.drawable.new_york, R.string.new_york);
            }
        });

        Button buttonShowWeather = findViewById(R.id.button_show_weather);
        buttonShowWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText city = findViewById(R.id.edit_text_city);
                Parcel parcel = new Parcel();
                parcel.text = city.getText().toString();

                Intent intent = new Intent(MainActivity.this, WeatherShowActivity.class);
                intent.putExtra(TEXT, parcel);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });



    }

    private void clicker(int resID1, int resID2) {
        Toast toast = Toast.makeText(MainActivity.this, dateFormat.format(date), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        ImageView imageView = findViewById(R.id.background);
        TextView textView = findViewById(R.id.city);
        TextView textCounter = findViewById(R.id.textCounter);
        EditText editText = findViewById(R.id.edit_text_city);
        editText.setText(resID2);
        toast.show();
        imageView.setImageResource(resID1);
        textView.setText(resID2);
        presenter.incrementCounter();
        textCounter.setText(String.valueOf(presenter.getCounter()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode != REQUEST_CODE) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        if (resultCode == RESULT_OK) {
            TextView textView = findViewById(R.id.last_answer);
            textView.setText(data.getStringExtra(RESULT_TEXT));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
                Log.d("MainActivity", "onStart()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState) {
        super.onRestoreInstanceState(saveInstanceState);
                Log.d("MainActivity", "Повторный запуск!! - onRestoreInstanceState()");
    }

    @Override
    protected void onResume() {
        super.onResume();
                Log.d("MainActivity", "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
                Log.d("MainActivity", "onPause()");
    }

    @Override
    protected void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
                Log.d("MainActivity", "onSaveInstanceState()");
    }

    @Override
    protected void onStop() {
        super.onStop();
                Log.d("MainActivity", "onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MainActivity", "onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity", "onDestroy()");
    }

}