package ru.logistic.materialaccounting.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ru.logistic.materialaccounting.database.Item;
import ru.logistic.materialaccounting.database.ItemDatabase;
import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.SaveImage;
import ru.logistic.materialaccounting.database.ItemsDao;
import ru.logistic.materialaccounting.interfaces.ItemTouchHelperAdapter;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    public List<Item> list = new ArrayList<>();
    private final Context ctx ;

    public ItemAdapter(Context ctx){
        this.ctx = ctx;
    }

    public void submitList(List<Item> list) {
        this.list = list;
    }

    @Override
    public void onItemDismiss(int position) {
        ItemsDao dao = ItemDatabase.getInstance(ctx).itemDao();
        Item c = list.get(position);
        new Thread(() -> dao.delete(c)).start();
        list.remove(c);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item, parent, false);
        return new ItemAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder holder, int position) {
        holder.name2.setText(list.get(position).name);
        int cnt = list.get(position).count;
        holder.count2.setText(cnt + "");
        Glide
                .with(ctx)
                .load(SaveImage.loadImageFromStorage(ctx, list.get(position).image))
                .into(holder.image2);
        //holder.image2.setImageBitmap(SaveImage.loadImageFromStorage(ctx, list.get(position).image));
        holder.vi2.setOnClickListener(v->{
            //TODO
            ItemsDao dao = ItemDatabase.getInstance(ctx).itemDao();
            list.get(position).count=list.get(position).count-10;
            new Thread(()->{
                dao.update(list.get(position));
            }).start();

        });
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
        ViewHolder(View view2){
            super(view2);
            vi2=view2;
            name2 = view2.findViewById(R.id.nameitem2);
            count2 = view2.findViewById(R.id.count2);
            image2 = view2.findViewById(R.id.imagemat2);
        }
    }
}
