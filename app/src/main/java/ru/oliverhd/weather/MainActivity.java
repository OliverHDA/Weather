package ru.oliverhd.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    DateFormat dateFormat = DateFormat.getDateTimeInstance();
    Date date = new Date();
    final MainPresenter presenter = MainPresenter.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toast toast = Toast.makeText(MainActivity.this, dateFormat.format(date), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);

        final TextView textCounter = findViewById(R.id.textCounter);
        textCounter.setText(String.valueOf(presenter.getCounter()));

        String instanceState;
        if (savedInstanceState == null) {
            instanceState = "Первый запуск!";
        } else {
            instanceState = "Повторный запуск!";
        }
        Toast.makeText(getApplicationContext(), instanceState + " - onCreate()", Toast.LENGTH_SHORT).show();
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
    }

    private void clicker(int resID1, int resID2) {
        Toast toast = Toast.makeText(MainActivity.this, dateFormat.format(date), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        ImageView imageView = findViewById(R.id.background);
        TextView textView = findViewById(R.id.city);
        TextView textCounter = findViewById(R.id.textCounter);
        toast.show();
        imageView.setImageResource(resID1);
        textView.setText(resID2);
        presenter.incrementCounter();
        textCounter.setText(String.valueOf(presenter.getCounter()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(), "onStart()", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "onStart()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState) {
        super.onRestoreInstanceState(saveInstanceState);
        Toast.makeText(getApplicationContext(), "Повторный запуск!! - onRestoreInstanceState()", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "Повторный запуск!! - onRestoreInstanceState()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(), "onResume()", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(getApplicationContext(), "onPause()", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "onPause()");
    }

    @Override
    protected void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        Toast.makeText(getApplicationContext(), "onSaveInstanceState()", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "onSaveInstanceState()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(getApplicationContext(), "onStop()", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(getApplicationContext(), "onRestart()", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "onDestroy()", Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", "onDestroy()");
    }

}