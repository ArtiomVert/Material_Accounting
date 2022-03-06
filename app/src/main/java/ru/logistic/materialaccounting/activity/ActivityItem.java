package ru.logistic.materialaccounting.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.adapters.ViewPagerAdapter;

public class ActivityItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);

        ViewPager2 viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter); // устанавливаем адаптер

        String[] titles = new String[]{getString(R.string.description), getString(R.string.other)};

        TabLayout tb = findViewById(R.id.tabLayout);
        new TabLayoutMediator(tb, viewPager, ((tab, position) -> {
            tab.setText(titles[position]);
        })).attach();

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}