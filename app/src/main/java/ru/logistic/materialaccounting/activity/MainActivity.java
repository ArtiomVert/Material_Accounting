package ru.logistic.materialaccounting.activity;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import ru.logistic.materialaccounting.Functions;
import ru.logistic.materialaccounting.ImageHelper;
import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.Values;
import ru.logistic.materialaccounting.database.Category;
import ru.logistic.materialaccounting.database.DatabaseHelper;
import ru.logistic.materialaccounting.database.StorageDao;

public class MainActivity extends AppCompatActivity {

    NavController navController;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navController = Navigation.findNavController(this, R.id.nav_host);
        if (Functions.load(this, "first").equals("")) {
            StorageDao dao = DatabaseHelper.getInstance(this).categoryDao();
            String nameImage = "category special image";
            Bitmap b = ImageHelper.getBitmapFromXml(this, R.drawable.ic_category_other);
            ImageHelper.saveToInternalStorage(this.getApplicationContext(), b, nameImage);
            Category other = new Category(0, "Остальное", nameImage);
            Category all = new Category(0, "Все элементы", nameImage);
            new Thread(() -> {
                dao.insertCategory(all);
                dao.insertCategory(other);
            }).start();
            Functions.save(this, "first", "1");
        }
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            navController.navigate(item.getItemId());
            return true;
        });
    }

    @Override
    public void onBackPressed() {
        if (Values.mainIds.contains(Objects.requireNonNull(navController.getCurrentDestination()).getId()))
            openQuitDialog();
        else super.onBackPressed();
    }

    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                this);
        quitDialog.setTitle("Вы уверены, что хотите выйти?");
        quitDialog.setPositiveButton("Да", (dialog, which) -> finish());

        quitDialog.setNegativeButton("Нет", (dialog, which) -> {
        });

        quitDialog.show();
    }

}