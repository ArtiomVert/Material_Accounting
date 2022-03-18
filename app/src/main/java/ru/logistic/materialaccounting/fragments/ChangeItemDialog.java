package ru.logistic.materialaccounting.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;

import ru.logistic.materialaccounting.ImageHelper;
import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.database.DatabaseHelper;
import ru.logistic.materialaccounting.database.Item;
import ru.logistic.materialaccounting.database.ItemsDao;

public class ChangeItemDialog extends DialogFragment {

    Item item;

    public ChangeItemDialog(Item item) {
        super(R.layout.change_item_dialog);
        this.item = item;
    }

    public ChangeItemDialog() {
        super(R.layout.change_item_dialog);
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

        Glide
                .with(requireContext())
                .load(ImageHelper.loadImageFromStorage(requireContext(), item.image))
                .into((ImageView) view.findViewById(R.id.image3));
        TextInputLayout til1 = view.findViewById(R.id.name3);
        TextInputLayout til2 = view.findViewById(R.id.link3);
        TextInputLayout til3 = view.findViewById(R.id.content3);
        EditText name = til1.getEditText();
        EditText link = til2.getEditText();
        EditText content = til3.getEditText();
        name.setText(item.name);
        link.setText(item.link);
        content.setText(item.content);
        Button save = view.findViewById(R.id.btn3);
        save.setOnClickListener(v->{
            item.name = name.getText().toString();
            item.link = link.getText().toString();
            item.content = content.getText().toString();
            ItemsDao dao = DatabaseHelper.getInstance(requireContext()).itemDao();
            new Thread(()->{
                dao.update(item);
            }).start();
            dismiss();
        });

    }

}
