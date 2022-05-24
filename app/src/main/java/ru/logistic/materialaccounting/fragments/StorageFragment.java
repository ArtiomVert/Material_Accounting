package ru.logistic.materialaccounting.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;

import ru.logistic.materialaccounting.Functions;
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
    private StorageAdapter adapter;
    private StorageDao dao;
    private RecyclerView rec;
    private ItemTouchHelper.Callback callback;
    private ItemTouchHelper touchHelper;

    public StorageFragment() {
        super(R.layout.storage_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rec = view.findViewById(R.id.storage_rec);
        dao = DatabaseHelper.getInstance(requireContext()).categoryDao();
        adapter = new StorageAdapter(requireContext(), this);
        rec.setAdapter(adapter);
        callback = new SimpleItemTouchHelperCallback(adapter);
        touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rec);

        dao.getAllCategories().observe(getViewLifecycleOwner(), categories -> {
            //Collections.sort(categories,(o1, o2)->o1.name.toLowerCase(Locale.ROOT).compareTo(o2.name.toLowerCase(Locale.ROOT)));
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
                item.idcategory = 2;
                new Thread(() -> {
                    dao.update(item);
                }).start();
            }
        });
    }
}