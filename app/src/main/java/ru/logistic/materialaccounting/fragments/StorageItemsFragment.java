package ru.logistic.materialaccounting.fragments;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import ru.logistic.materialaccounting.database.ItemDatabase;
import ru.logistic.materialaccounting.diffutils.ItemsDiffUtil;
import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.SimpleItemTouchHelperCallback;
import ru.logistic.materialaccounting.adapters.ItemAdapter;
import ru.logistic.materialaccounting.database.ItemsDao;

public class StorageItemsFragment extends Fragment {

    public StorageItemsFragment() {
        super(R.layout.storage_items_fragment);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        assert bundle != null;
        long id = bundle.getLong("id");
        RecyclerView rec = view.findViewById(R.id.item_rec);
        ItemsDao dao = ItemDatabase.getInstance(requireContext()).itemDao();

        ItemAdapter adapter = new ItemAdapter(requireContext());
        rec.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rec);

        dao.getAllItemsByIdCategory(id).observe(getViewLifecycleOwner(), items -> {
            ItemsDiffUtil dif = new ItemsDiffUtil(adapter.list, items);
            DiffUtil.DiffResult d = DiffUtil.calculateDiff(dif);
            adapter.submitList(items);
            d.dispatchUpdatesTo(adapter);
        });

        Button btn = view.findViewById(R.id.add);
        btn.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putLong("idcategory", id);
            new CustomDialog(R.layout.activity_add_item, b)
                    .show(requireActivity().getSupportFragmentManager(), "customTag");

        });

    }
}
