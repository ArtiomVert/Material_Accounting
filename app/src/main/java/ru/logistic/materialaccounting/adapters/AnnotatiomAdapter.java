package ru.logistic.materialaccounting.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.database.History;

public class AnnotatiomAdapter extends RecyclerView.Adapter<AnnotatiomAdapter.ViewHolder> {
    private final String text;

    public AnnotatiomAdapter(String text) {
        this.text = text;
    }

    @Override
    public AnnotatiomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_annotation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnnotatiomAdapter.ViewHolder holder, int position) {


        holder.data.setText(text);
    }

    @Override
    public int getItemCount() {
        return 1;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView data;

        ViewHolder(View view) {
            super(view);
            data = view.findViewById(R.id.annotation);

        }
    }

}
