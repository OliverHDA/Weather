package ru.oliverhd.weather.db;

import java.util.List;

import ru.oliverhd.weather.db.dao.HistoryDao;
import ru.oliverhd.weather.db.model.SearchHistory;

// Вспомогательный класс, развязывающий зависимость между Room и RecyclerView
public class SearchHistorySource {

    private final HistoryDao historyDao;

    // Буфер с данными: сюда будем подкачивать данные из БД
    private List<SearchHistory> searchHistoryList;

    public SearchHistorySource(HistoryDao historyDao){
        this.historyDao = historyDao;
    }

    public List<SearchHistory> getSearchHistory(){
        if (searchHistoryList == null){
            LoadSearchHistory();
        }
        return searchHistoryList;
    }

    // Загружаем студентов в буфер
    public void LoadSearchHistory(){
        searchHistoryList = historyDao.getAllSearchHistory();
    }

    public long getCountSearchHistory(){
        return historyDao.getCountSearchHistory();
    }

    public void addSearchHistory(SearchHistory searchHistory){
        historyDao.insertSearchHistory(searchHistory);
        LoadSearchHistory();
    }

    public void updateSearchHistory(SearchHistory searchHistory){
        historyDao.updateSearchHistory(searchHistory);
        LoadSearchHistory();
    }

    public void removeSearchHistory(long id){
        historyDao.deleteSearchHistoryById(id);
        LoadSearchHistory();
    }

}
