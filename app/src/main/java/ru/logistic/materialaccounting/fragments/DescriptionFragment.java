package ru.logistic.materialaccounting.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.SaveImage;
import ru.logistic.materialaccounting.activity.ActivityItem;
import ru.logistic.materialaccounting.adapters.DescriptionAdapter;
import ru.logistic.materialaccounting.database.Item;
import ru.logistic.materialaccounting.database.ItemDatabase;
import ru.logistic.materialaccounting.database.ItemsDao;

public class DescriptionFragment extends Fragment {


    public DescriptionFragment(){
        super(R.layout.description_fragment);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Intent intent = requireActivity().getIntent();
        long id = intent.getLongExtra("id", 0);
        Toast.makeText(requireContext(), id+"", Toast.LENGTH_SHORT).show();
        ItemsDao dao = ItemDatabase.getInstance(requireContext()).itemDao();
        dao.getItem(id).observe(getViewLifecycleOwner(), item->{
            Glide
                    .with(requireContext())
                    .load(SaveImage.loadImageFromStorage(requireContext(), item.image))
                    .into((ImageView) view.findViewById(R.id.im));
            RecyclerView rec = view.findViewById(R.id.description_rec);
            String text = item.content;
            rec.setAdapter(new DescriptionAdapter(text.split("\n")));
        });

    }
}
