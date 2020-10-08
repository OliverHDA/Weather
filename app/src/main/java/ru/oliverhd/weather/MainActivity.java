package ru.oliverhd.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView imageView = findViewById(R.id.background);
        final TextView textView = findViewById(R.id.city);
        final Toast toast = Toast.makeText(MainActivity.this, dateFormat.format(date), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);

        Button button1 = findViewById(R.id.button_moscow);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast.show();
                imageView.setImageResource(R.drawable.moscow);
                textView.setText(R.string.moscow);
            }
        });

        Button button2 = findViewById(R.id.button_london);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast.show();
                imageView.setImageResource(R.drawable.london);
                textView.setText(R.string.london);
            }
        });

        Button button3 = findViewById(R.id.button_ny);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast.show();
                imageView.setImageResource(R.drawable.new_york);
                textView.setText(R.string.new_york);
            }
        });
    }
}