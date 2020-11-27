package ru.oliverhd.weather.db;

import android.app.Application;

import androidx.room.Room;

import ru.oliverhd.weather.db.dao.HistoryDao;
import ru.oliverhd.weather.db.database.HistoryDatabase;

public class App extends Application {

    private static App instance;

    private HistoryDatabase db;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        db = Room.databaseBuilder(
                getApplicationContext(),
                HistoryDatabase.class,
                "education_database")
                .allowMainThreadQueries() //Только для примеров и тестирования.
                .build();
    }

    public HistoryDao getHistoryDao() {
        return db.getHistoryDao();
    }
}

