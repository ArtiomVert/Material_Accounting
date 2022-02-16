package ru.logistic.materialaccounting.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.database.Item;
import ru.logistic.materialaccounting.database.ItemDatabase;
import ru.logistic.materialaccounting.database.ItemsDao;

public class ActivityItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Intent intent = getIntent();
        long id = intent.getLongExtra("id", 0);
        Toast.makeText(this, id + "", Toast.LENGTH_SHORT).show();
        ItemsDao dao = ItemDatabase.getInstance(this).itemDao();
        Item item = dao.getItem(id);

    }
}