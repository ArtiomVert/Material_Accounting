package ru.logistic.materialaccounting.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;

import ru.logistic.materialaccounting.Functions;
import ru.logistic.materialaccounting.ImageHelper;
import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.database.Category;
import ru.logistic.materialaccounting.database.DatabaseHelper;
import ru.logistic.materialaccounting.database.History;
import ru.logistic.materialaccounting.database.HistoryDao;
import ru.logistic.materialaccounting.database.StorageDao;


public class AddCategoryFragment extends Fragment {
    @SuppressLint("NonConstantResourceId")
    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                try {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri image = result.getData().getData();
                        InputStream stream = null;
                        try {
                            stream = requireContext().getContentResolver().openInputStream(image);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Bitmap bitmap = BitmapFactory.decodeStream(stream);
                        bitmap = Bitmap.createScaledBitmap(bitmap, 1000, 1000, false);
                        ((ImageView) requireView().findViewById(R.id.image)).setImageBitmap(bitmap);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

    public AddCategoryFragment() {
        super(R.layout.activity_add_category);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextInputLayout textInputLayout2 = view.findViewById(R.id.name);
        EditText name2 = textInputLayout2.getEditText();
        Button btn2 = view.findViewById(R.id.btn);
        StorageDao dao3 = DatabaseHelper.getInstance(requireContext()).categoryDao();
        HistoryDao dao4 = DatabaseHelper.getInstance(requireContext()).historyDao();

        ImageView imageitem2 = view.findViewById(R.id.image);

        imageitem2.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            resultLauncher.launch(intent);
        });

        btn2.setOnClickListener(v -> {
            if (name2.getText().toString().equals("")) {
                Toast.makeText(requireContext(), "Заполните для продолжения", Toast.LENGTH_SHORT).show();
            } else {
                Drawable i = imageitem2.getDrawable();
                Bitmap b = Bitmap.createBitmap(i.getIntrinsicWidth(), i.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(b);
                i.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                i.draw(canvas);
                String nameimg = Functions.newName();
                Category nc = new Category(0,
                        name2.getText().toString(),
                        nameimg);
                History h = new History(0, Functions.Time(), ":Добавление категории:", nc.name);
                new Thread(() -> {
                    ImageHelper.saveToInternalStorage(requireContext().getApplicationContext(), b, nameimg);
                    dao3.insertCategory(nc);
                    dao4.insertHistory(h);
                }).start();
                Navigation.findNavController(requireView()).navigate(R.id.storage);
            }
        });
    }
}
