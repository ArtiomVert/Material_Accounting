package ru.logistic.materialaccounting.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import ru.logistic.materialaccounting.R;

public class ActivityItem extends AppCompatActivity {

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        NavController navController;
        navController = Navigation.findNavController(this, R.id.nav_host2);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        bottomNavigationView.setOnItemSelectedListener(btn -> {
            navController.navigate(btn.getItemId());
            return true;
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}