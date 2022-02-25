package ru.logistic.materialaccounting.adapters;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ru.logistic.materialaccounting.Functions;
import ru.logistic.materialaccounting.ImageHelper;
import ru.logistic.materialaccounting.database.Category;
import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.database.DatabaseHelper;
import ru.logistic.materialaccounting.database.History;
import ru.logistic.materialaccounting.database.HistoryDao;
import ru.logistic.materialaccounting.database.StorageDao;
import ru.logistic.materialaccounting.interfaces.Click;
import ru.logistic.materialaccounting.interfaces.ItemTouchHelperAdapter;

public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.ViewHolder> implements ItemTouchHelperAdapter {
    public List<Category> list = new ArrayList<>();
    private Context ctx;
    private final Click clck;

    public StorageAdapter(Context ctx, Click c) {
        this.ctx = ctx;
        this.clck = c;
    }

    public void submitList(List<Category> list) {
        this.list = list;
    }

    @Override
    public StorageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StorageAdapter.ViewHolder holder, int position) {

        holder.name1.setText(list.get(position).name);
        Glide.with(ctx).load(ImageHelper.loadImageFromStorage(ctx, list.get(position).image)).into(holder.image1);

        //holder.image1.setImageBitmap(ImageHelper.loadImageFromStorage(ctx, list.get(position).image));
        holder.vi1.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putLong("id", list.get(position).id);
            clck.onClick(bundle);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onItemDismiss(int position) {
        StorageDao dao = DatabaseHelper.getInstance(ctx).categoryDao();
        HistoryDao dao2 = DatabaseHelper.getInstance(ctx).historyDao();
        Category c = list.get(position);
        ImageHelper.deleteImageFromStorage(ctx, c.image);
        History h = new History(0, Functions.Time(), ":Удаление категории:", c.name);
        new Thread(() -> {
            dao.delete(c);
            dao2.insertHistory(h);
        }).start();

        list.remove(c);
        notifyItemRemoved(position);
    }




    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name1;
        ImageView image1;
        View vi1;

        ViewHolder(View view1) {
            super(view1);
            vi1 = view1;
            name1 = view1.findViewById(R.id.nameitem);
            image1 = view1.findViewById(R.id.imagemat);
        }
    }

}
