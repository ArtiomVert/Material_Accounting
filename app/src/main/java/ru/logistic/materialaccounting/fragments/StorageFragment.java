package ru.logistic.materialaccounting.fragments;


import android.graphics.Typeface;
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

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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
            Navigation.findNavController(requireView()).navigate(R.id.add_category_fragment);
        });


        String guide = Functions.load(requireContext(), "guide");
        if (guide.equals("")) {
            guide1();
        } else if (guide.equals("2")){
            guide3();
        }

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

    private void guide1() {
        new MaterialAlertDialogBuilder(requireContext())
                .setCancelable(false)
                .setTitle(getString(R.string.guide_title_question))
                .setMessage(getString(R.string.guide_question))
                .setPositiveButton(getString(R.string.yes), (dialog, id) -> {
                    TapTargetView.showFor(requireActivity(),
                            TapTarget.forView(requireActivity().findViewById(R.id.bottomNavigationView), getString(R.string.guide1), getString(R.string.guide2))
                                    .outerCircleColor(R.color.teal_700)
                                    .outerCircleAlpha(0.95f)
                                    .targetCircleColor(R.color.white)
                                    .titleTextSize(30)
                                    .titleTextColor(R.color.white)
                                    .descriptionTextSize(15)
                                    .textTypeface(Typeface.SANS_SERIF)
                                    .dimColor(R.color.black)
                                    .drawShadow(true)
                                    .cancelable(true)
                                    .transparentTarget(true),
                            new TapTargetView.Listener() {
                                @Override
                                public void onTargetCancel(TapTargetView view) {
                                    super.onTargetCancel(view);
                                    guide2();
                                }

                                @Override
                                public void onTargetClick(TapTargetView view) {
                                    super.onTargetClick(view);
                                    guide2();
                                }

                                @Override
                                public void onTargetLongClick(TapTargetView view) {
                                    super.onTargetLongClick(view);
                                    guide2();
                                }
                            });
                })
                .setNegativeButton(getString(R.string.no), (dialog, id) -> {
                }).show();
    }

    private void guide2() {
        TapTargetView.showFor(requireActivity(),
                TapTarget.forView(requireView().findViewById(R.id.add), getString(R.string.guide3), getString(R.string.guide4))
                        .outerCircleColor(R.color.teal_700)
                        .outerCircleAlpha(0.95f)
                        .targetCircleColor(R.color.white)
                        .titleTextSize(30)
                        .titleTextColor(R.color.white)
                        .descriptionTextSize(20)
                        .textTypeface(Typeface.SANS_SERIF)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(false)
                        .transparentTarget(true),
                new TapTargetView.Listener() {
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetCancel(view);
                        Functions.save(requireContext(), "guide", "1");
                        Navigation.findNavController(requireView()).navigate(R.id.add_category_fragment);
                    }

                    @Override
                    public void onTargetLongClick(TapTargetView view) {
                        super.onTargetLongClick(view);
                        Functions.save(requireContext(), "guide", "1");
                        Navigation.findNavController(requireView()).navigate(R.id.add_category_fragment);
                    }
                });
    }

    private void guide3(){
        TapTargetView.showFor(requireActivity(),
                TapTarget.forView(requireView().findViewById(R.id.storage_rec), getString(R.string.guide11), getString(R.string.guide12))
                        .outerCircleColor(R.color.teal_700)
                        .outerCircleAlpha(0.95f)
                        .targetCircleColor(R.color.white)
                        .titleTextSize(30)
                        .titleTextColor(R.color.white)
                        .descriptionTextSize(15)
                        .textTypeface(Typeface.SANS_SERIF)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(false)
                        .transparentTarget(true)
                        .targetRadius(250),
                new TapTargetView.Listener() {
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);
                        Functions.save(requireContext(), "guide", "3");
                        Bundle bundle = new Bundle();
                        bundle.putLong("id", 2);
                        Navigation.findNavController(requireView()).navigate(R.id.storage_items, bundle);
                    }

                    @Override
                    public void onTargetLongClick(TapTargetView view) {
                        super.onTargetLongClick(view);
                        Functions.save(requireContext(), "guide", "3");
                        Bundle bundle = new Bundle();
                        bundle.putLong("id", 2);
                        Navigation.findNavController(requireView()).navigate(R.id.storage_items, bundle);
                    }
                });
    }
}