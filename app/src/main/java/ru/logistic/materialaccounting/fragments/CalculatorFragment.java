package ru.logistic.materialaccounting.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import ru.logistic.materialaccounting.R;

public class CalculatorFragment extends Fragment {

    public CalculatorFragment() {
        super(R.layout.calculator_fragment);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextInputLayout til1 = view.findViewById(R.id.kol_vo);
        TextInputLayout til2 = view.findViewById(R.id.ves1);
        TextInputLayout til3 = view.findViewById(R.id.ves2);
        TextInputLayout til4 = view.findViewById(R.id.cost1);
        TextInputLayout til5 = view.findViewById(R.id.cost2);
        EditText count = til1.getEditText();
        EditText ves1 = til2.getEditText();
        EditText ves2 = til3.getEditText();
        EditText cost1 = til4.getEditText();
        EditText cost2 = til5.getEditText();
        Button button = view.findViewById(R.id.found);
        button.setOnClickListener(v -> {
            for (int i = 0; i < 5; i++) {
                if (count.getText().toString().equals("")) {
                    try {
                        count.setText((Float.parseFloat(ves2.getText().toString()) / Float.parseFloat(ves1.getText().toString())) + "");
                    } catch (Exception e) {
                    }
                    try {
                        count.setText((Float.parseFloat(cost2.getText().toString()) / Float.parseFloat(cost1.getText().toString())) + "");
                    } catch (Exception e) {
                    }
                }

                if (ves1.getText().toString().equals("")) {
                    try {
                        ves1.setText((Float.parseFloat(ves2.getText().toString()) / Float.parseFloat(count.getText().toString())) + "");
                    } catch (Exception e) {
                    }
                }

                if (cost1.getText().toString().equals("")) {
                    try {
                        cost1.setText((Float.parseFloat(cost2.getText().toString()) / Float.parseFloat(count.getText().toString())) + "");
                    } catch (Exception e) {
                    }
                }

                if (cost2.getText().toString().equals("")) {
                    try {
                        cost2.setText((Float.parseFloat(cost1.getText().toString()) * Float.parseFloat(count.getText().toString())) + "");
                    } catch (Exception e) {
                    }
                }

                if (ves2.getText().toString().equals("")) {
                    try {
                        ves2.setText((Float.parseFloat(ves1.getText().toString()) * Float.parseFloat(count.getText().toString())) + "");
                    } catch (Exception e) {
                    }
                }
            }
        });
    }
}