package ru.logistic.materialaccounting.fragments;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.SimpleItemTouchHelperCallback;
import ru.logistic.materialaccounting.adapters.StorageAdapter;
import ru.logistic.materialaccounting.database.DatabaseHelper;
import ru.logistic.materialaccounting.database.Item;
import ru.logistic.materialaccounting.database.ItemsDao;
import ru.logistic.materialaccounting.database.StorageDao;
import ru.logistic.materialaccounting.diffutils.StorageDiffUtil;
import ru.logistic.materialaccounting.interfaces.StorageActions;

public class StorageFragment extends Fragment implements StorageActions {

    public StorageFragment() {
        super(R.layout.storage_fragment);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rec = view.findViewById(R.id.storage_rec);
        StorageDao dao = DatabaseHelper.getInstance(requireContext()).categoryDao();

        StorageAdapter adapter = new StorageAdapter(requireContext(), this);
        rec.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rec);

        dao.getAllCategories().observe(getViewLifecycleOwner(), categories -> {
            StorageDiffUtil dif = new StorageDiffUtil(adapter.list, categories);
            DiffUtil.DiffResult d = DiffUtil.calculateDiff(dif);
            adapter.submitList(categories);
            d.dispatchUpdatesTo(adapter);
        });
        Button badd = view.findViewById(R.id.add);
        badd.setOnClickListener(v -> {
            new CustomDialog(R.layout.activity_add_category, new Bundle())
                    .show(requireActivity().getSupportFragmentManager(), "customTag");
        });

    }


    @Override
    public void onClick(Bundle bundle) {
        Navigation.findNavController(requireView()).navigate(R.id.storage_items, bundle);
    }

    @Override
    public void reMoveItems(long id) {
        ItemsDao dao = DatabaseHelper.getInstance(requireContext()).itemDao();
        dao.getAllItemsByIdCategory(id).observe(getViewLifecycleOwner(), items -> {
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                item.idcategory = 1;
                new Thread(() -> {
                    dao.update(item);
                }).start();
            }
        });
    }
}
