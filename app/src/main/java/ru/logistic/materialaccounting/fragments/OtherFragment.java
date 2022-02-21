package ru.logistic.materialaccounting.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.database.DatabaseHelper;
import ru.logistic.materialaccounting.database.ItemsDao;

public class OtherFragment extends Fragment {
    public OtherFragment() {
        super(R.layout.other_fragment);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Intent intent = requireActivity().getIntent();
        long id = intent.getLongExtra("id", 0);
        ItemsDao dao = DatabaseHelper.getInstance(requireContext()).itemDao();
        TextView link = view.findViewById(R.id.textlink);
        TextView count = view.findViewById(R.id.textcount);
        dao.getItem(id).observe(getViewLifecycleOwner(), item -> {
            link.setText("Ссылка на магазин: " + item.link);
            Linkify.addLinks(link, Linkify.ALL);
            count.setText("Количество: " + item.count);
        });
    }
}