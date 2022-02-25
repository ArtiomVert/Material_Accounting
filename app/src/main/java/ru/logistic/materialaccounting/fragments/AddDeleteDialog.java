package ru.logistic.materialaccounting.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;

import ru.logistic.materialaccounting.Functions;
import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.database.DatabaseHelper;
import ru.logistic.materialaccounting.database.History;
import ru.logistic.materialaccounting.database.HistoryDao;
import ru.logistic.materialaccounting.database.ItemsDao;

public class AddDeleteDialog extends DialogFragment {
    private final long id;

    public AddDeleteDialog(long id) {
        super(R.layout.add_delete_dialog);
        this.id = id;
    }

    @Override
    public void onStart() {
        super.onStart();
        requireDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.Theme_MaterialAccouting);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button add = view.findViewById(R.id.btnplus);
        Button deduct = view.findViewById(R.id.btndeduct);
        TextInputLayout textInputLayout = view.findViewById(R.id.count2);
        EditText editText = textInputLayout.getEditText();
        add.setOnClickListener(v -> {
            String number = editText.getText().toString();
            if (number.equals("")) {
                Toast.makeText(requireContext(), "Введите значение", Toast.LENGTH_SHORT).show();
            } else if (number.length() > 8) {
                textInputLayout.setErrorEnabled(true);
                textInputLayout.setError("Соблюдайте меры");
            } else {
                int c = Integer.parseInt(number);
                ItemsDao dao = DatabaseHelper.getInstance(requireContext()).itemDao();
                HistoryDao dao2 = DatabaseHelper.getInstance(requireContext()).historyDao();
                dao.getItem(id).observe(getViewLifecycleOwner(), item -> {
                    item.count += c;

                    History h = new History(0, Functions.Time(), "Изменение количества(добавление в " + item.name + ")", c + "");
                    new Thread(() -> {
                        dao.update(item);
                        dao2.insertHistory(h);
                    }).start();
                });
                dismiss();
            }
        });

        deduct.setOnClickListener(v -> {
            String number = editText.getText().toString();
            if (number.equals("")) {
                Toast.makeText(requireContext(), "Введите значение", Toast.LENGTH_SHORT).show();
            } else if (number.length() > 8) {
                textInputLayout.setErrorEnabled(true);
                textInputLayout.setError("Соблюдайте меры");
            } else {
                int c = Integer.parseInt(number);
                ItemsDao dao = DatabaseHelper.getInstance(requireContext()).itemDao();
                HistoryDao dao2 = DatabaseHelper.getInstance(requireContext()).historyDao();
                dao.getItem(id).observe(getViewLifecycleOwner(), item -> {
                    item.count -= c;
                    History h = new History(0, Functions.Time(), "Изменение количества(вычет из " + item.name + ")", c + "");
                    new Thread(() -> {
                        dao.update(item);
                        dao2.insertHistory(h);
                    }).start();
                });
                dismiss();
            }
        });
    }
}
