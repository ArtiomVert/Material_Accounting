package ru.logistic.materialaccounting.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ru.logistic.materialaccounting.ImageHelper;
import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.database.Category;
import ru.logistic.materialaccounting.interfaces.StorageChangeInterface;

public class StorageChangeAdapter extends RecyclerView.Adapter<StorageChangeAdapter.ViewHolder> {
    private List<Category> list = new ArrayList<>();
    private Context ctx;
    private StorageChangeInterface c;

    public StorageChangeAdapter(Context ctx, List<Category> list, StorageChangeInterface c) {
        this.ctx = ctx;
        this.list = list;
        this.c = c;
    }


    @Override
    public StorageChangeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StorageChangeAdapter.ViewHolder holder, int position) {
        Category cat = list.get(position);
        holder.name1.setText(cat.name);
        Glide.with(ctx).load(ImageHelper.loadImageFromStorage(ctx, cat.image)).into(holder.image1);
        holder.pb.setVisibility(View.GONE);
        holder.vi1.setOnClickListener(v -> {
            c.ChooseCategory(cat.id, cat.name);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name1;
        ImageView image1;
        ProgressBar pb;
        View vi1;

        ViewHolder(View view1) {
            super(view1);
            vi1 = view1;
            name1 = view1.findViewById(R.id.nameitem);
            image1 = view1.findViewById(R.id.imagemat);
            pb = view1.findViewById(R.id.progressBar2);
        }
    }

}
