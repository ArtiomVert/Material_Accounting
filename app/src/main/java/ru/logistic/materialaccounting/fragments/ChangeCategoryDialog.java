package ru.logistic.materialaccounting.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import ru.logistic.materialaccounting.Functions;
import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.adapters.StorageChangeAdapter;
import ru.logistic.materialaccounting.database.DatabaseHelper;
import ru.logistic.materialaccounting.database.History;
import ru.logistic.materialaccounting.database.HistoryDao;
import ru.logistic.materialaccounting.database.ItemsDao;
import ru.logistic.materialaccounting.database.StorageDao;
import ru.logistic.materialaccounting.interfaces.StorageChangeInterface;

public class ChangeCategoryDialog extends DialogFragment implements StorageChangeInterface {
    private final long id;

    public ChangeCategoryDialog(long id) {
        super(R.layout.change_category_dialog);
        this.id = id;
    }

    @Override
    public void onStart() {
        super.onStart();
        requireDialog().getWindow().setLayout(
                600,
                1500
        );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.Theme_MaterialAccouting);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rec = view.findViewById(R.id.storage_change_rec);
        StorageDao dao = DatabaseHelper.getInstance(requireContext()).categoryDao();
        dao.getAllCategories().observe(getViewLifecycleOwner(), categories -> {
            rec.setAdapter(new StorageChangeAdapter(requireContext(), categories, this));
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void ChooseCategory(long id, String newCategory) {
        ItemsDao dao = DatabaseHelper.getInstance(requireContext()).itemDao();
        HistoryDao dao2 = DatabaseHelper.getInstance(requireContext()).historyDao();
        dao.getItem(this.id).observe(getViewLifecycleOwner(), item -> {
            History h = new History(0, Functions.Time(), ":Перенос элемента " + item.name + " в категорию:", newCategory);
            item.idcategory = id;
            new Thread(() -> {
                dao.update(item);
                dao2.insertHistory(h);
            }).start();
        });
        dismiss();
    }
}
