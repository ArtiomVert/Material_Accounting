package ru.logistic.materialaccounting.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import ru.logistic.materialaccounting.fragments.DescriptionFragment;
import ru.logistic.materialaccounting.fragments.OtherFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new DescriptionFragment();
        }
        return new OtherFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
