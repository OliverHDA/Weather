package ru.oliverhd.weather.db.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.oliverhd.weather.db.dao.HistoryDao;
import ru.oliverhd.weather.db.model.SearchHistory;

@Database(entities = {SearchHistory.class}, version = 1)
public abstract class HistoryDatabase extends RoomDatabase {
    public abstract HistoryDao getHistoryDao();
}
