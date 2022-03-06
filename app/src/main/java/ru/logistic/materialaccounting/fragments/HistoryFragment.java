package ru.logistic.materialaccounting.fragments;


import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.adapters.HistoryAdapter;
import ru.logistic.materialaccounting.database.DatabaseHelper;
import ru.logistic.materialaccounting.database.HistoryDao;

public class HistoryFragment extends Fragment {

    public HistoryFragment() {
        super(R.layout.history_fragment);
    }

    //Nichego net?
    //Yzhe est'

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((ViewGroup) view).addView(new TextView(requireContext()));
        RecyclerView rec = view.findViewById(R.id.history_rec);
        HistoryDao dao = DatabaseHelper.getInstance(requireContext()).historyDao();
        dao.getHistory().observe(getViewLifecycleOwner(), history -> {
            rec.setAdapter(new HistoryAdapter(history));
        });
    }
}