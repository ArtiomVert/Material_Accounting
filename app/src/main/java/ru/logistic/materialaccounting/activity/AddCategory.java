package ru.logistic.materialaccounting.activity;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;

import ru.logistic.materialaccounting.Category;
import ru.logistic.materialaccounting.CategoryDatabase;
import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.SaveImage;
import ru.logistic.materialaccounting.database.StorageDao;

public class AddCategory extends AppCompatActivity {

    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri image = result.getData().getData();
                    InputStream stream = null;
                    try {
                        stream = getContentResolver().openInputStream(image);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bitmap = BitmapFactory.decodeStream(stream);
                    int maxSize = Math.max(bitmap.getHeight(), bitmap.getWidth());
                    if (maxSize < 5000) {
                        ((ImageView) findViewById(R.id.image)).setImageBitmap(bitmap);
                    } else {
                        Toast.makeText(this, "Слишком большое изображение", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        EditText name = findViewById(R.id.name);
        Button btn = findViewById(R.id.btn);
        StorageDao dao = CategoryDatabase.getInstance(this).categoryDao();
        ImageView imageitem = findViewById(R.id.image);

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

            //TODO: Move to Functions.java
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

            Category nc = new Category(0, name.getText().toString(), finalAboba);
            new Thread(() -> {
                SaveImage.saveToInternalStorage(getApplicationContext(), b, finalAboba);
                dao.insertCategory(nc);
            }).start();
            finish();
        });

    }
}