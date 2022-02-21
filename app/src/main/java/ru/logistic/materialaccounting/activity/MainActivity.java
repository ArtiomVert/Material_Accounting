package ru.logistic.materialaccounting.activity;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.Values;

public class MainActivity extends AppCompatActivity {

    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navController = Navigation.findNavController(this, R.id.nav_host);
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