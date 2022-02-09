package ru.logistic.materialaccounting;

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

import java.io.FileNotFoundException;
import java.io.InputStream;

import ru.logistic.materialaccounting.database.ItemsDao;

public class CustomDialog extends DialogFragment {

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
                        ((ImageView) requireView().findViewById(R.id.image2)).setImageBitmap(bitmap);
                    } else{
                        Toast.makeText(requireContext(), "Слишком большое изображение", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    public CustomDialog(@LayoutRes Integer layout, @Nullable Bundle args) {
        super(layout);
        setArguments(args);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TODO: Code
        Bundle arguments = getArguments();
        long idcategory = Long.parseLong(arguments.get("idcategory").toString());
        EditText name = view.findViewById(R.id.name2);
        EditText content = view.findViewById(R.id.content2);
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
            Calendar c = Calendar.getInstance();
            String aboba = "";
            aboba += c.get(Calendar.SECOND);
            aboba += "_";
            aboba += c.get(Calendar.MINUTE);
            aboba += "_";
            aboba += c.get(Calendar.HOUR_OF_DAY);
            aboba += "_";
            aboba += c.get(Calendar.DAY_OF_MONTH);
            aboba += "_";
            aboba += c.get(Calendar.MONTH);
            aboba += "_";
            aboba += c.get(Calendar.YEAR);
            aboba += ".png";
            String finalAboba = aboba;
            Item it = new Item(0, idcategory, name.getText().toString(), content.getText().toString(), finalAboba);
            new Thread(() -> {
                SaveImage.saveToInternalStorage(requireContext().getApplicationContext(), b, finalAboba);
                dao.insertItem(it);
            }).start();
            dismiss();
        });
    }


}
