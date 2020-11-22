package ru.oliverhd.weather;

/*
* Адаптер для RecyclerView для детального отображения погоды по часам.
* */

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {

    private String[] dataSource;

    public DetailAdapter(String[] dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public DetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.detail_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAdapter.ViewHolder viewHolder, int i) {
        viewHolder.getTime().setText(dataSource[i]);
    }

    @Override
    public int getItemCount() {
        return dataSource.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView time;
//        private TextView temperature;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            time = (TextView) itemView;
            time = itemView.findViewById(R.id.hour);
//            temperature = itemView.findViewById(R.id.hours_temperature);
        }

        public TextView getTime() {
            return time;
        }

//        public TextView getTemperature() {
//            return temperature;
//        }
    }
}

