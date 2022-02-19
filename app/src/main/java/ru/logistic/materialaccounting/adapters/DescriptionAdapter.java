package ru.logistic.materialaccounting.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.logistic.materialaccounting.R;

public class DescriptionAdapter extends RecyclerView.Adapter<DescriptionAdapter.ViewHolder> {

    private final String[] str;

    public DescriptionAdapter(String[] str) {
        this.str = str;
    }

    @NonNull
    @Override
    public DescriptionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_description, parent, false);
        return new DescriptionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DescriptionAdapter.ViewHolder holder, int position) {
        holder.text.setText(str[position]);
    }

    @Override
    public int getItemCount() {
        return str.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        ViewHolder(View view) {
            super(view);
            text = view.findViewById(R.id.textdescription);
        }
    }
}
