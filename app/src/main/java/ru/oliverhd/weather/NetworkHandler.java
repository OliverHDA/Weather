package ru.oliverhd.weather;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class NetworkHandler {

    private static final String TAG = "Weather";

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
