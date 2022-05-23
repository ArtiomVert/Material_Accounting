package ru.logistic.materialaccounting.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import ru.logistic.materialaccounting.Functions;
import ru.logistic.materialaccounting.ImageHelper;
import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.adapters.PhotosAdapter;
import ru.logistic.materialaccounting.database.DatabaseHelper;
import ru.logistic.materialaccounting.database.Item;
import ru.logistic.materialaccounting.database.ItemsDao;
import ru.logistic.materialaccounting.database.StorageDao;

@RequiresApi(api = Build.VERSION_CODES.N)
public class OtherFragment extends Fragment {
    private Item it;

    public OtherFragment() {
        super(R.layout.other_fragment);
    }

    @SuppressLint("NonConstantResourceId")
    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                try {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri image = result.getData().getData();
                        InputStream stream = null;
                        try {
                            stream = requireContext().getContentResolver().openInputStream(image);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Bitmap bitmap = BitmapFactory.decodeStream(stream);
                        String name = Functions.newName();
                        ItemsDao dao = DatabaseHelper.getInstance(requireContext()).itemDao();
                        it.photos += "//" + name;
                        bitmap = Bitmap.createScaledBitmap(bitmap, 1000, 1000, false);
                        Bitmap finalB = bitmap;
                        new Thread(() -> {
                            ImageHelper.saveToInternalStorage(requireContext(), finalB, name);
                            dao.update(it);
                        }).start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Intent intent = requireActivity().getIntent();
        long id = intent.getLongExtra("id", 0);
        ItemsDao dao = DatabaseHelper.getInstance(requireContext()).itemDao();
        StorageDao dao2 = DatabaseHelper.getInstance(requireContext()).categoryDao();
        TextView link = view.findViewById(R.id.textlink);
        TextView count = view.findViewById(R.id.textcount);
        TextView categoryView = view.findViewById(R.id.textcategory);
        TextView photos = view.findViewById(R.id.textphoto);
        TextView countchange = view.findViewById(R.id.textcountchange);
        TextView categoryViewchange = view.findViewById(R.id.textcategorychange);
        GraphView graph = view.findViewById(R.id.graph);

        dao.getItem(id).observe(getViewLifecycleOwner(), item -> {
            it = item;
            link.setText("Ссылка на магазин: " + item.link);
            Linkify.addLinks(link, Linkify.ALL);
            count.setText("Количество: " + item.count + " " + item.mera);
            ArrayList<String> img = new ArrayList<String>(Arrays.asList(item.photos.split("//")));
            photos.setText("Галерея: " + img.size() + " фото. Нажмите для добавления");
            dao2.getCategory(item.idcategory).observe(getViewLifecycleOwner(), category -> {
                categoryView.setText("Категория: " + category.name);
            });
            RecyclerView rec = view.findViewById(R.id.rec_photos);
            ArrayList<String> array_photos = new ArrayList<String>(Arrays.asList(item.photos.split("//")));
            PhotosAdapter adapter = new PhotosAdapter(array_photos, requireContext());
            rec.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
            rec.setAdapter(adapter);


            graph.removeAllSeries();
            BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{});

            String[] q = item.stat.split("//");
            int c = 1;
            for (String i : q) {
                series.appendData(new DataPoint(c, Integer.parseInt(i)), true, 2147483647);
                c += 1;
            }
            series.setSpacing(5);
            series.setDrawValuesOnTop(true);
            series.setValuesOnTopColor(Color.BLACK);

            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setMinX(0);
            graph.getViewport().setMaxX(c + 1);
            graph.getViewport().setScrollable(true);
            graph.getViewport().setScrollableY(true);
            graph.getViewport().setScalable(true);
            graph.getViewport().setScalableY(true);
            graph.addSeries(series);
        });
        countchange.setOnClickListener(v -> {
            new AddDeleteDialog(id).show(requireActivity().getSupportFragmentManager(), "customTag");
        });
        categoryViewchange.setOnClickListener(v -> {
            new ChangeCategoryDialog(id).show(requireActivity().getSupportFragmentManager(), "customTag");
        });
        photos.setOnClickListener(v -> {
            Intent intent2 = new Intent();
            intent2.setType("image/*");
            intent2.setAction(Intent.ACTION_GET_CONTENT);
            resultLauncher.launch(intent2);
        });
    }
}