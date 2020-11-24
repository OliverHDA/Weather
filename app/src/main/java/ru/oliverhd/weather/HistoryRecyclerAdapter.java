package ru.oliverhd.weather;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.oliverhd.weather.db.SearchHistorySource;
import ru.oliverhd.weather.db.model.SearchHistory;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolder> {

    private Activity activity;
    // Источник данных
    private SearchHistorySource dataSource;
    // Позиция в списке, на которой было нажато меню
    private long menuPosition;

    public HistoryRecyclerAdapter(SearchHistorySource dataSource, Activity activity) {
        this.dataSource = dataSource;
        this.activity = activity;
    }


    @NonNull
    @Override
    public HistoryRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.history_card_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryRecyclerAdapter.ViewHolder viewHolder, int position) {
        // Заполняем данными записи на экране
        List<SearchHistory> searchHistoryList = dataSource.getSearchHistory();
        SearchHistory searchHistory = searchHistoryList.get(position);
        viewHolder.cityTextView.setText(searchHistory.city);
        viewHolder.temperatureTextView.setText(searchHistory.temperature);
        viewHolder.dateTextView.setText(searchHistory.date);


        // Тут определяем, какой пункт меню был нажат
        viewHolder.cardView.setOnLongClickListener(view -> {
            menuPosition = position;
            return false;
        });

        // Регистрируем контекстное меню
        if (activity != null) {
            activity.registerForContextMenu(viewHolder.cardView);
        }

    }

    @Override
    public int getItemCount() {
        return (int) dataSource.getCountSearchHistory();
    }

    public long getMenuPosition() {
        return menuPosition;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView cityTextView;
        private TextView temperatureTextView;
        private TextView dateTextView;
        View cardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView;
            cityTextView = cardView.findViewById(R.id.city_history);
            temperatureTextView = cardView.findViewById(R.id.temperature_history);
            dateTextView = cardView.findViewById(R.id.date_history);
        }
    }
}
