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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

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
import ru.logistic.materialaccounting.database.Item;
import ru.logistic.materialaccounting.database.ItemsDao;
import ru.logistic.materialaccounting.database.StorageDao;

public class CustomDialog extends DialogFragment {

    private int layout = 0;

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
                        switch (layout) {
                            case R.layout.activity_add_category:
                                ((ImageView) requireView().findViewById(R.id.image)).setImageBitmap(bitmap);
                                break;
                            case R.layout.activity_add_item:
                                ((ImageView) requireView().findViewById(R.id.image2)).setImageBitmap(bitmap);
                                break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

    public CustomDialog(@LayoutRes Integer layout, @Nullable Bundle args) {
        super(layout);
        setArguments(args);
        this.layout = layout;
    }

    public CustomDialog() {
    }

    @Override
    public void onStart() {
        super.onStart();
        setRetainInstance(true);
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

        switch (layout) {
            case R.layout.activity_add_item:
                Bundle arguments = getArguments();
                assert arguments != null;
                long idcategory = Long.parseLong(arguments.get("idcategory").toString());

                TextInputLayout textInputLayout0 = view.findViewById(R.id.name2);
                EditText name = textInputLayout0.getEditText();
                TextInputLayout textInputLayout = view.findViewById(R.id.content2);
                EditText content = textInputLayout.getEditText();
                TextInputLayout textInputLayout3 = view.findViewById(R.id.count);
                EditText count = textInputLayout3.getEditText();
                TextInputLayout textInputLayout4 = view.findViewById(R.id.link);
                EditText link = textInputLayout4.getEditText();

                Button btn = view.findViewById(R.id.btn2);
                Button mera = view.findViewById(R.id.mera);

                ItemsDao dao = DatabaseHelper.getInstance(requireContext()).itemDao();
                HistoryDao dao2 = DatabaseHelper.getInstance(requireContext()).historyDao();

                ImageView imageitem = view.findViewById(R.id.image2);
                imageitem.setOnClickListener(v -> {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    resultLauncher.launch(intent);
                });
                mera.setOnClickListener(v -> {
                    showPopupMenu(v);
                });

                btn.setOnClickListener(v -> {
                    if (link.getText().toString().equals("") || count.getText().toString().equals("") || name.getText().toString().equals("") || content.getText().toString().equals("")) {
                        Toast.makeText(requireContext(), "Заполните все поля\nдля продолжения", Toast.LENGTH_SHORT).show();
                    } else if (count.getText().toString().length() > 8) {
                        textInputLayout3.setErrorEnabled(true);
                        textInputLayout3.setError("Соблюдайте меры");
                    } else {
                        Drawable i = imageitem.getDrawable();
                        Bitmap b = Bitmap.createBitmap(i.getIntrinsicWidth(), i.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(b);
                        i.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                        i.draw(canvas);
                        //Bitmap bitmap = ((BitmapDrawable) imageitem.getDrawable()).getBitmap();
                        String nameimg = Functions.newName();
                        Item it = new Item(idcategory,
                                name.getText().toString(),
                                content.getText().toString(),
                                Integer.parseInt(count.getText().toString()),
                                nameimg,
                                link.getText().toString(),
                                mera.getText().toString());
                        History h = new History(0, Functions.Time(), ":Добавление элемента:", it.name);
                        new Thread(() -> {
                            ImageHelper.saveToInternalStorage(requireContext().getApplicationContext(), b, nameimg);
                            dao.insertItem(it);
                            dao2.insertHistory(h);
                        }).start();
                        dismiss();
                    }
                });
                break;
            case R.layout.activity_add_category:

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
                        dismiss();
                    }
                });
                break;

        }

    }

    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), v);
        popupMenu.inflate(R.menu.popup_menu);

        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        ((Button) v.findViewById(R.id.mera)).setText(item.getTitle());
                        return false;
                    }
                });

        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
            }
        });
        popupMenu.show();
    }

}
