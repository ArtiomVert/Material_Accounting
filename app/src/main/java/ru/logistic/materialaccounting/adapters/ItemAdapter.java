package ru.logistic.materialaccounting.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ru.logistic.materialaccounting.Functions;
import ru.logistic.materialaccounting.ImageHelper;
import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.activity.ActivityItem;
import ru.logistic.materialaccounting.database.DatabaseHelper;
import ru.logistic.materialaccounting.database.History;
import ru.logistic.materialaccounting.database.HistoryDao;
import ru.logistic.materialaccounting.database.Item;
import ru.logistic.materialaccounting.database.ItemsDao;
import ru.logistic.materialaccounting.interfaces.ItemTouchHelperAdapter;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    public List<Item> list = new ArrayList<>();
    private final Context ctx;

    public ItemAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public void submitList(List<Item> list) {
        this.list = list;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onItemDismiss(int position) {

        ItemsDao dao = DatabaseHelper.getInstance(ctx).itemDao();
        HistoryDao dao2 = DatabaseHelper.getInstance(ctx).historyDao();
        Item c = list.get(position);
        History h = new History(0, Functions.Time(), ": Удаление элемента:", c.name);
        new Thread(() -> {
            dao.delete(c);
            dao2.insertHistory(h);
        }).start();
        list.remove(c);
        notifyItemRemoved(position);
    }


    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item2, parent, false);
        return new ItemAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder holder, int position) {
        holder.name2.setText(list.get(position).name);
        int cnt = list.get(position).count;
        String mera = list.get(position).mera;
        holder.count2.setText(cnt + " " + mera);

        //ctx.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("")));
        Glide
                .with(ctx)
                .load(ImageHelper.loadImageFromStorage(ctx, list.get(position).image))
                .into(holder.image2);
        //holder.image2.setImageBitmap(ImageHelper.loadImageFromStorage(ctx, list.get(position).image));
        holder.vi2.setOnClickListener(v -> {
            //TODO
            Intent intent = new Intent(ctx, ActivityItem.class);
            intent.putExtra("id", list.get(position).id);
            ctx.startActivity(intent);

        });
        holder.pb.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name2;
        TextView count2;
        ImageView image2;
        View vi2;
        ProgressBar pb;

        ViewHolder(View view) {
            super(view);
            vi2 = view;
            name2 = view.findViewById(R.id.nameitem2);
            count2 = view.findViewById(R.id.count2);
            image2 = view.findViewById(R.id.imagemat2);
            pb = view.findViewById(R.id.progressBar3);
        }
    }
}
