package ru.logistic.materialaccounting.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ru.logistic.materialaccounting.Item;
import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.SaveImage;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    private final List<Item> list;
    private final Context ctx ;

    public ItemAdapter(Context ctx, List<Item> items){
        this.list = items;
        this.ctx = ctx;
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item, parent, false);
        return new ItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder holder, int position) {
        holder.name2.setText(list.get(position).name);
        Glide.with(ctx).load(SaveImage.loadImageFromStorage(ctx, list.get(position).image)).into(holder.image2);
        //holder.image2.setImageBitmap(SaveImage.loadImageFromStorage(ctx, list.get(position).image));
        holder.vi2.setOnClickListener(v->{
            //TODO
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name2;
        ImageView image2;
        View vi2;
        ViewHolder(View view2){
            super(view2);
            vi2=view2;
            name2 = view2.findViewById(R.id.nameitem2);
            image2 = view2.findViewById(R.id.imagemat2);
        }
    }
}
