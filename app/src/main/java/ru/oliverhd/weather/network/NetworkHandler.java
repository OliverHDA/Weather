package ru.oliverhd.weather.network;

/*
* Класс соединения с погодным сервисом.
* */

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import ru.oliverhd.weather.interfaces.Constants;

public class NetworkHandler implements Constants {

    String getData(URL url) {
        String result = null;
        HttpsURLConnection urlConnection = null;
        try {
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);

            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            result = in.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            Log.e(TAG, "Fail connection", e);
//                        вызов диалогового окна при дисконекте.
//                        DialogConnectionErrorFragment dialogFragment =
//                                DialogConnectionErrorFragment.newInstance();
//                        dialogFragment.show(getFragmentManager(),
//                                "dialog_fragment");

            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }


        return result;
    }
}
