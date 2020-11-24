package ru.oliverhd.weather.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.oliverhd.weather.HistoryRecyclerAdapter;
import ru.oliverhd.weather.R;
import ru.oliverhd.weather.db.App;
import ru.oliverhd.weather.db.SearchHistorySource;
import ru.oliverhd.weather.db.dao.HistoryDao;

public class HistoryFragment extends Fragment {

    private HistoryRecyclerAdapter adapter;
    private SearchHistorySource searchHistorySource;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);
    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_history);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        HistoryDao historyDao = App
                .getInstance()
                .getHistoryDao();
        searchHistorySource = new SearchHistorySource(historyDao);

        adapter = new HistoryRecyclerAdapter(searchHistorySource, getActivity());
        recyclerView.setAdapter(adapter);

    }
}