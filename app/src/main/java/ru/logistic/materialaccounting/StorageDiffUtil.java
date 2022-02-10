package ru.logistic.materialaccounting;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class StorageDiffUtil extends DiffUtil.Callback {

    private final List<Category> oldlist;
    private final List<Category> newlist;

    public StorageDiffUtil(List<Category> oldlist, List<Category> newlist) {
        this.oldlist = oldlist;
        this.newlist = newlist;
    }

    @Override
    public int getOldListSize() {
        return oldlist.size();
    }

    @Override
    public int getNewListSize() {
        return newlist.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Category olditem = oldlist.get(oldItemPosition);
        Category newitem = newlist.get(newItemPosition);
        return olditem.id == newitem.id;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Category olditem = oldlist.get(oldItemPosition);
        Category newitem = newlist.get(newItemPosition);
        return (olditem.name.equals(newitem.name)) && (newitem.image.equals(olditem.name))
                && olditem.id == newitem.id;
    }
}
