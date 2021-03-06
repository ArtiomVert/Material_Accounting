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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
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
import ru.logistic.materialaccounting.database.DatabaseHelper;
import ru.logistic.materialaccounting.database.History;
import ru.logistic.materialaccounting.database.HistoryDao;
import ru.logistic.materialaccounting.database.Item;
import ru.logistic.materialaccounting.database.ItemsDao;

public class AddItemFragment extends Fragment {
    private long id;

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
                        ((ImageView) requireView().findViewById(R.id.image2)).setImageBitmap(bitmap);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

    public AddItemFragment() {
        super(R.layout.activity_add_item);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        assert bundle != null;
        id = bundle.getLong("id");
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
                Toast.makeText(requireContext(), "?????????????????? ?????? ????????\n?????? ??????????????????????", Toast.LENGTH_SHORT).show();
            } else if (count.getText().toString().length() > 8) {
                textInputLayout3.setErrorEnabled(true);
                textInputLayout3.setError("???????????????????? ????????");
            } else {
                Drawable i = imageitem.getDrawable();
                Bitmap b = Bitmap.createBitmap(i.getIntrinsicWidth(), i.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(b);
                i.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                i.draw(canvas);
                String nameimg = Functions.newName();
                Item it = new Item(id,
                        name.getText().toString(),
                        content.getText().toString(),
                        Integer.parseInt(count.getText().toString()),
                        nameimg,
                        link.getText().toString(),
                        mera.getText().toString());
                History h = new History(0, Functions.Time(), ":???????????????????? ????????????????:", it.name);
                new Thread(() -> {
                    ImageHelper.saveToInternalStorage(requireContext().getApplicationContext(), b, nameimg);
                    dao.insertItem(it);
                    dao2.insertHistory(h);
                }).start();
                Bundle bundle1 = new Bundle();
                bundle1.putLong("id", id);
                Navigation.findNavController(requireView()).navigate(R.id.storage_items, bundle1);
            }
        });
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
