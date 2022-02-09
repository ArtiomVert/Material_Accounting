package ru.logistic.materialaccounting.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;

import ru.logistic.materialaccounting.Category;
import ru.logistic.materialaccounting.CategoryDatabase;
import ru.logistic.materialaccounting.Functions;
import ru.logistic.materialaccounting.Item;
import ru.logistic.materialaccounting.ItemDatabase;
import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.SaveImage;
import ru.logistic.materialaccounting.database.ItemsDao;
import ru.logistic.materialaccounting.database.StorageDao;

public class CustomDialog extends DialogFragment {

    private int layout = 0;

    @SuppressLint("NonConstantResourceId")
    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri image = result.getData().getData();
                    InputStream stream = null;
                    try {
                        stream = requireContext().getContentResolver().openInputStream(image);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bitmap = BitmapFactory.decodeStream(stream);
                    int maxSize = Math.max(bitmap.getHeight(), bitmap.getWidth());
                    if (maxSize<5000) {
                        switch (layout){
                            case R.layout.activity_add_category:
                                ((ImageView) requireView().findViewById(R.id.image)).setImageBitmap(bitmap);
                                break;
                            case R.layout.activity_add_item:
                                ((ImageView) requireView().findViewById(R.id.image2)).setImageBitmap(bitmap);
                                break;
                        }
                    } else{
                        Toast.makeText(requireContext(), "Слишком большое изображение", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    public CustomDialog(@LayoutRes Integer layout, @Nullable Bundle args) {
        super(layout);
        setArguments(args);
        this.layout=layout;
    }



    @Override
    public void onStart() {
        super.onStart();
        requireDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.Theme_MaterialAccouting);
    }

    @SuppressLint("NonConstantResourceId")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        switch (layout){
            case R.layout.activity_add_item:
                Bundle arguments = getArguments();
                assert arguments != null;
                long idcategory = Long.parseLong(arguments.get("idcategory").toString());
                EditText name = view.findViewById(R.id.name2);
                TextInputLayout textInputLayout = view.findViewById(R.id.content2);
                EditText content = textInputLayout.getEditText();
                Button btn = view.findViewById(R.id.btn2);
                ItemsDao dao = ItemDatabase.getInstance(requireContext()).itemDao();
                ImageView imageitem = view.findViewById(R.id.image2);
                imageitem.setOnClickListener(v -> {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    resultLauncher.launch(intent);


                });
                btn.setOnClickListener(v -> {
                    Drawable i = imageitem.getDrawable();
                    Bitmap b = Bitmap.createBitmap(i.getIntrinsicWidth(), i.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(b);
                    i.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                    i.draw(canvas);
                    //Bitmap bitmap = ((BitmapDrawable) imageitem.getDrawable()).getBitmap();
                    String aboba = Functions.newName();
                    Item it = new Item(0, idcategory, name.getText().toString(), content.getText().toString(), aboba);
                    new Thread(() -> {
                        SaveImage.saveToInternalStorage(requireContext().getApplicationContext(), b, aboba);
                        dao.insertItem(it);
                    }).start();
                    dismiss();
                });
                break;
            case R.layout.activity_add_category:
                TextInputLayout textInputLayout2 = view.findViewById(R.id.name);
                EditText name2 = textInputLayout2.getEditText();
                Button btn2 = view.findViewById(R.id.btn);
                StorageDao dao2 = CategoryDatabase.getInstance(requireContext()).categoryDao();
                ImageView imageitem2 = view.findViewById(R.id.image);

                imageitem2.setOnClickListener(v -> {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    resultLauncher.launch(intent);
                });

                btn2.setOnClickListener(v -> {
                    Drawable i = imageitem2.getDrawable();
                    Bitmap b = Bitmap.createBitmap(i.getIntrinsicWidth(), i.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(b);
                    i.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                    i.draw(canvas);
                    String aboba = Functions.newName();
                    Category nc = new Category(0, name2.getText().toString(), aboba);
                    new Thread(() -> {
                        SaveImage.saveToInternalStorage(requireContext().getApplicationContext(), b, aboba);
                        dao2.insertCategory(nc);
                    }).start();
                    dismiss();
                });
                break;

        }

    }


}