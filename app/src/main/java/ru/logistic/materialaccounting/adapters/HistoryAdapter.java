package ru.logistic.materialaccounting.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.database.History;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{
    private final List<History> list;

    public HistoryAdapter(List<History> list) {
        this.list=list;
    }

    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.ViewHolder holder, int position) {
        History history = list.get(position);
        String text = history.time + "\n" + history.action + "\n" + history.details;

        holder.data.setText(text);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView data;

        ViewHolder(View view) {
            super(view);
            data = view.findViewById(R.id.data);

        }
    }

}
