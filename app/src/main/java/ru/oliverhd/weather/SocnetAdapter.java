package ru.oliverhd.weather;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SocnetAdapter extends RecyclerView.Adapter<SocnetAdapter.ViewHolder> {

    private String[] dataSource;
    private OnItemClickListener itemClickListener;

    public SocnetAdapter(String[] dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public SocnetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SocnetAdapter.ViewHolder viewHolder, int i) {
        viewHolder.getTextView().setText(dataSource[i]);
    }

    @Override
    public int getItemCount() {
        return dataSource.length;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, String city);
    }

    public void SetOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, (String) ((TextView) v).getText());
                    }
                }
            });
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
