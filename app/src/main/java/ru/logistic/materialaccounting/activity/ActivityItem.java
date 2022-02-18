package ru.logistic.materialaccounting.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.database.Item;
import ru.logistic.materialaccounting.database.ItemDatabase;
import ru.logistic.materialaccounting.database.ItemsDao;

public class ActivityItem extends AppCompatActivity {

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Intent intent = getIntent();
        long id = intent.getLongExtra("id", 0);
        NavController navController;
        navController = Navigation.findNavController(this, R.id.nav_host2);
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        navController.navigate(R.id.description, bundle);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        bottomNavigationView.setOnItemSelectedListener(btn -> {
            Bundle bundle2 = new Bundle();
            bundle2.putLong("id", id);
            navController.navigate(btn.getItemId(), bundle2);
            return true;
        });

    }
}