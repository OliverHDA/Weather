package ru.oliverhd.weather.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.oliverhd.weather.db.model.SearchHistory;

@Dao
public interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSearchHistory(SearchHistory searchHistory);

    @Update
    void updateSearchHistory(SearchHistory searchHistory);

    @Delete
    void deleteSearchHistory(SearchHistory searchHistory);

    @Query("DELETE FROM searchHistory WHERE id = :id")
    void deleteSearchHistoryById(long id);

    @Query("SELECT * FROM searchHistory")
    List<SearchHistory> getAllSearchHistory();

    @Query("SELECT * FROM searchHistory WHERE id = :id")
    SearchHistory getSearchHistoryById(long id);

    @Query("SELECT COUNT() FROM searchHistory")
    long getCountSearchHistory();
}
