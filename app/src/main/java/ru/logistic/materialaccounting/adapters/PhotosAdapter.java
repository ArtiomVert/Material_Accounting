package ru.logistic.materialaccounting.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.logistic.materialaccounting.ImageHelper;
import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.database.History;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {
    private final ArrayList<String> list;
    private final Context ctx;

    public PhotosAdapter(ArrayList<String> list, Context ctx) {
        Collections.reverse(list);
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public PhotosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotosAdapter.ViewHolder holder, int position) {
        Toast.makeText(ctx, list.get(position)+"", Toast.LENGTH_SHORT).show();
        Glide.with(ctx).load(ImageHelper.loadImageFromStorage(ctx, list.get(position))).into(holder.data);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView data;

        ViewHolder(View view) {
            super(view);
            data = view.findViewById(R.id.img);

        }
    }

}

