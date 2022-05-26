package ru.logistic.materialaccounting.fragments;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.SimpleItemTouchHelperCallback;
import ru.logistic.materialaccounting.adapters.ItemAdapter;
import ru.logistic.materialaccounting.database.DatabaseHelper;
import ru.logistic.materialaccounting.database.ItemsDao;
import ru.logistic.materialaccounting.diffutils.ItemsDiffUtil;

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
        TextView textView = view.findViewById(R.id.state);
        ItemsDao dao = DatabaseHelper.getInstance(requireContext()).itemDao();

        ItemAdapter adapter = new ItemAdapter(requireContext());
        rec.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rec);

        if (id == 1) {
            dao.getAllItems().observe(getViewLifecycleOwner(), items -> {
                if (items.size() == 0) {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(R.string.nothing);
                } else {
                    textView.setVisibility(View.GONE);
                    rec.setAdapter(adapter);
                    ItemsDiffUtil dif = new ItemsDiffUtil(adapter.list, items);
                    DiffUtil.DiffResult d = DiffUtil.calculateDiff(dif);
                    adapter.submitList(items);
                    d.dispatchUpdatesTo(adapter);
                }
            });
        } else {
            dao.getAllItemsByIdCategory(id).observe(getViewLifecycleOwner(), items -> {
                if (items.size() == 0) {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(R.string.nothing);
                } else {
                    textView.setVisibility(View.GONE);
                    rec.setAdapter(adapter);
                    ItemsDiffUtil dif = new ItemsDiffUtil(adapter.list, items);
                    DiffUtil.DiffResult d = DiffUtil.calculateDiff(dif);
                    adapter.submitList(items);
                    d.dispatchUpdatesTo(adapter);
                }
            });
        }
        Button btn = view.findViewById(R.id.add);
        btn.setOnClickListener(v -> {
            Bundle bundle1 = new Bundle();
            bundle1.putLong("id", id);
            Navigation.findNavController(requireView()).navigate(R.id.add_item_fragment, bundle1);

        });

    }
}
