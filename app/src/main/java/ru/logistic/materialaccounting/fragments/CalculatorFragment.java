package ru.logistic.materialaccounting.fragments;


import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.logistic.materialaccounting.R;

public class CalculatorFragment extends Fragment {

    public CalculatorFragment() {
        super(R.layout.calculator_fragment);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((ViewGroup) view).addView(new TextView(requireContext()));
    }
}