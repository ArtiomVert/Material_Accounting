package ru.logistic.materialaccounting.fragments;


import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.logistic.materialaccounting.R;

public class HistoryFragment extends Fragment {

    public HistoryFragment() {
        super(R.layout.history_fragment);
    }

    //Nichego net?

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((ViewGroup) view).addView(new TextView(requireContext()));
    }
}