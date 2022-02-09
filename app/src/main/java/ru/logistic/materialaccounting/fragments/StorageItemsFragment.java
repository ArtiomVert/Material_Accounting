package ru.logistic.materialaccounting.fragments;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import ru.logistic.materialaccounting.ItemDatabase;
import ru.logistic.materialaccounting.R;
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

        dao.getAllItemsByIdCategory(id).observe(getViewLifecycleOwner(), items -> {
            rec.setAdapter(new ItemAdapter(requireContext(), items));
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
