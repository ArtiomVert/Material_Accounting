package ru.logistic.materialaccounting.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.concurrent.atomic.AtomicReference;

import ru.logistic.materialaccounting.ImageHelper;
import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.adapters.DescriptionAdapter;
import ru.logistic.materialaccounting.database.DatabaseHelper;
import ru.logistic.materialaccounting.database.Item;
import ru.logistic.materialaccounting.database.ItemsDao;

public class DescriptionFragment extends Fragment {

    private Item it;

    public DescriptionFragment() {
        super(R.layout.description_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProgressBar pb = view.findViewById(R.id.progressBar);
        Intent intent = requireActivity().getIntent();
        long id = intent.getLongExtra("id", 0);

        ItemsDao dao = DatabaseHelper.getInstance(requireContext()).itemDao();
        dao.getItem(id).observe(getViewLifecycleOwner(), item -> {
            it =item;
            Glide
                    .with(requireContext())
                    .load(ImageHelper.loadImageFromStorage(requireContext(), item.image))
                    .into((ImageView) view.findViewById(R.id.im));
            RecyclerView rec = view.findViewById(R.id.description_rec);
            String text = item.content;
            rec.setAdapter(new DescriptionAdapter(text.split("\n")));
            pb.setVisibility(View.GONE);
        });
        Button changeItem = view.findViewById(R.id.changeitem);
        changeItem.setOnClickListener(v -> {
            new ChangeItemDialog(it)
                    .show(requireActivity().getSupportFragmentManager(), "customTag");
        });


    }
}
