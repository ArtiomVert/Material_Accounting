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
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import ru.logistic.materialaccounting.Functions;
import ru.logistic.materialaccounting.ImageHelper;
import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.adapters.PhotosAdapter;
import ru.logistic.materialaccounting.database.DatabaseHelper;
import ru.logistic.materialaccounting.database.Item;
import ru.logistic.materialaccounting.database.ItemsDao;

@RequiresApi(api = Build.VERSION_CODES.N)
public class PhotosDialog extends DialogFragment {
    private long id = 0;
    private Item it;

    public PhotosDialog(long id) {
        super(R.layout.photos_dialog);
        this.id = id;
    }

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
                        String name = Functions.newName();
                        ItemsDao dao = DatabaseHelper.getInstance(requireContext()).itemDao();
                        it.photos += "//" + name;
                        bitmap = Bitmap.createScaledBitmap(bitmap, 1000, 1000, false);
                        Bitmap finalB = bitmap;
                        new Thread(() -> {
                            ImageHelper.saveToInternalStorage(requireContext(), finalB, name);
                            dao.update(it);
                        }).start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rec = view.findViewById(R.id.rec_photos);
        ItemsDao dao = DatabaseHelper.getInstance(requireContext()).itemDao();
        Button btn = view.findViewById(R.id.addimg);
        dao.getItem(id).observe(getViewLifecycleOwner(), item -> {
            it = item;
            ArrayList<String> q = new ArrayList<String>(Arrays.asList(item.photos.split("//")));
            PhotosAdapter adapter = new PhotosAdapter(q, requireContext());
            rec.setAdapter(adapter);
        });

        btn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            resultLauncher.launch(intent);
        });

    }
}