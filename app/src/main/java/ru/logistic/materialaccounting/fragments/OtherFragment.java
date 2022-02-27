package ru.logistic.materialaccounting.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Date;

import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.database.DatabaseHelper;
import ru.logistic.materialaccounting.database.ItemsDao;

public class OtherFragment extends Fragment {
    public OtherFragment() {
        super(R.layout.other_fragment);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Intent intent = requireActivity().getIntent();
        long id = intent.getLongExtra("id", 0);
        ItemsDao dao = DatabaseHelper.getInstance(requireContext()).itemDao();
        TextView link = view.findViewById(R.id.textlink);
        TextView count = view.findViewById(R.id.textcount);
        GraphView graph = view.findViewById(R.id.graph);

        dao.getItem(id).observe(getViewLifecycleOwner(), item -> {
            link.setText("Ссылка на магазин: " + item.link);
            Linkify.addLinks(link, Linkify.ALL);
            count.setText("Количество: " + item.count);


            graph.removeAllSeries();
            BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{});

            String[] q = item.stat.split("//");
            int c=1;
            for (String i : q){
                series.appendData(new DataPoint(c, Integer.parseInt(i)), true, 2147483647);
                c+=1;
            }
            series.setSpacing(5);
            series.setDrawValuesOnTop(true);
            series.setValuesOnTopColor(Color.BLACK);

            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setMinX(0);
            graph.getViewport().setMaxX(c+1);
            graph.getViewport().setScrollable(true);
            graph.getViewport().setScrollableY(true);
            graph.getViewport().setScalable(true);
            graph.getViewport().setScalableY(true);
            graph.addSeries(series);
        });
        count.setOnClickListener(v->{
            new AddDeleteDialog(id).show(requireActivity().getSupportFragmentManager(), "customTag");
        });
    }
}