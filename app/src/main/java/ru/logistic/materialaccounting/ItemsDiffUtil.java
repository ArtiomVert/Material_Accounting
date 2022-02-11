package ru.logistic.materialaccounting;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class ItemsDiffUtil extends DiffUtil.Callback {

    private final List<Item> oldlist;
    private final List<Item> newlist;

    public ItemsDiffUtil(List<Item> oldlist, List<Item> newlist) {
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
        Item olditem = oldlist.get(oldItemPosition);
        Item newitem = newlist.get(newItemPosition);
        return olditem.id == newitem.id;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Item olditem = oldlist.get(oldItemPosition);
        Item newitem = newlist.get(newItemPosition);
        return (olditem.name.equals(newitem.name)) && (newitem.image.equals(olditem.name))
                && olditem.id == newitem.id;
    }
}
