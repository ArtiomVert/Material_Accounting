package ru.logistic.materialaccounting.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;

import ru.logistic.materialaccounting.Functions;
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
        ItemsDao dao = DatabaseHelper.getInstance(requireContext()).itemDao();

        if (Functions.load(requireContext(), "2").equals("")) {
            TapTargetView.showFor(requireActivity(),                 // `this` is an Activity
                    TapTarget.forView(view.findViewById(R.id.add), "Добавление элемента", "Нажмите, чтобы добавить элемент")
                            // All options below are optional
                            .outerCircleColor(R.color.white)      // Specify a color for the outer circle
                            .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                            .targetCircleColor(R.color.teal_700)   // Specify a color for the target circle
                            .titleTextSize(30)                  // Specify the size (in sp) of the title text
                            .titleTextColor(R.color.white)      // Specify the color of the title text
                            .descriptionTextSize(20)            // Specify the size (in sp) of the description text
                            .descriptionTextColor(R.color.black)  // Specify the color of the description text
                            .textColor(R.color.black)            // Specify a color for both the title and description text
                            .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                            .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                            .drawShadow(true)                   // Whether to draw a drop shadow or not
                            .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                            .icon(getResources().getDrawable(R.drawable.ic_baseline_add_24))
                            .tintTarget(true)                   // Whether to tint the target view's color
                            .transparentTarget(false)                  // Specify a custom drawable to draw as the target
                            .targetRadius(50),                  // Specify the target radius (in dp)
                    new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                        @Override
                        public void onTargetClick(TapTargetView view) {
                            super.onTargetClick(view);      // This call is optional
                            Functions.save(requireContext(), "2", "1");
                            Bundle b = new Bundle();
                            b.putLong("idcategory", id);
                            new CustomDialog(R.layout.activity_add_item, b)
                                    .show(requireActivity().getSupportFragmentManager(), "customTag");
                            }
                    });
        }

        ItemAdapter adapter = new ItemAdapter(requireContext());
        rec.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rec);

        if (id==1){
            dao.getAllItems().observe(getViewLifecycleOwner(), items -> {
                ItemsDiffUtil dif = new ItemsDiffUtil(adapter.list, items);
                DiffUtil.DiffResult d = DiffUtil.calculateDiff(dif);
                adapter.submitList(items);
                d.dispatchUpdatesTo(adapter);
            });
        }else {
            dao.getAllItemsByIdCategory(id).observe(getViewLifecycleOwner(), items -> {
                ItemsDiffUtil dif = new ItemsDiffUtil(adapter.list, items);
                DiffUtil.DiffResult d = DiffUtil.calculateDiff(dif);
                adapter.submitList(items);
                d.dispatchUpdatesTo(adapter);
            });
        }
        Button btn = view.findViewById(R.id.add);
        btn.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putLong("idcategory", id);
            new CustomDialog(R.layout.activity_add_item, b)
                    .show(requireActivity().getSupportFragmentManager(), "customTag");

        });

    }
}
