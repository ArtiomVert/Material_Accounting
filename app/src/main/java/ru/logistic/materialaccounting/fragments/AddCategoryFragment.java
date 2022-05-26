package ru.logistic.materialaccounting.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.material.textfield.TextInputLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;

import ru.logistic.materialaccounting.Functions;
import ru.logistic.materialaccounting.ImageHelper;
import ru.logistic.materialaccounting.R;
import ru.logistic.materialaccounting.database.Category;
import ru.logistic.materialaccounting.database.DatabaseHelper;
import ru.logistic.materialaccounting.database.History;
import ru.logistic.materialaccounting.database.HistoryDao;
import ru.logistic.materialaccounting.database.StorageDao;


public class AddCategoryFragment extends Fragment {
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
                        bitmap = Bitmap.createScaledBitmap(bitmap, 1000, 1000, false);
                        ((ImageView) requireView().findViewById(R.id.image)).setImageBitmap(bitmap);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

    public AddCategoryFragment() {
        super(R.layout.activity_add_category);
    }

    Button btn;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextInputLayout textInputLayout2 = view.findViewById(R.id.name);
        EditText name2 = textInputLayout2.getEditText();
        btn = view.findViewById(R.id.btn);
        StorageDao dao3 = DatabaseHelper.getInstance(requireContext()).categoryDao();
        HistoryDao dao4 = DatabaseHelper.getInstance(requireContext()).historyDao();

        ImageView imageitem2 = view.findViewById(R.id.image);

        imageitem2.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            resultLauncher.launch(intent);
        });

        btn.setOnClickListener(v -> {
            if (name2.getText().toString().equals("")) {
                Toast.makeText(requireContext(), "Заполните для продолжения", Toast.LENGTH_SHORT).show();
            } else {
                Drawable i = imageitem2.getDrawable();
                Bitmap b = Bitmap.createBitmap(i.getIntrinsicWidth(), i.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(b);
                i.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                i.draw(canvas);
                String nameimg = Functions.newName();
                Category nc = new Category(0,
                        name2.getText().toString(),
                        nameimg);
                History h = new History(0, Functions.Time(), ":Добавление категории:", nc.name);
                new Thread(() -> {
                    ImageHelper.saveToInternalStorage(requireContext().getApplicationContext(), b, nameimg);
                    dao3.insertCategory(nc);
                    dao4.insertHistory(h);
                }).start();
                Navigation.findNavController(requireView()).navigate(R.id.storage);
            }
        });

        String guide = Functions.load(requireContext(), "guide");
        if (guide.equals("1")) {
            guide1();
        }
    }
    private void guide1(){
        TapTargetView.showFor(requireActivity(),
                TapTarget.forView(requireView().findViewById(R.id.image), getString(R.string.guide5), getString(R.string.guide6))
                        .outerCircleColor(R.color.teal_700)
                        .outerCircleAlpha(0.95f)
                        .targetCircleColor(R.color.white)
                        .titleTextSize(30)
                        .titleTextColor(R.color.white)
                        .descriptionTextSize(20)
                        .textTypeface(Typeface.SANS_SERIF)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(false)
                        .transparentTarget(true)
                        .targetRadius(150),
                new TapTargetView.Listener() {
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetCancel(view);
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        resultLauncher.launch(intent);
                        guide2();
                    }

                    @Override
                    public void onTargetLongClick(TapTargetView view) {
                        super.onTargetLongClick(view);
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        resultLauncher.launch(intent);
                        guide2();
                    }
                });
    }

    private void guide2(){
        TapTargetView.showFor(requireActivity(),
                TapTarget.forView(requireView().findViewById(R.id.name), getString(R.string.guide7), getString(R.string.guide8))
                        .outerCircleColor(R.color.teal_700)
                        .outerCircleAlpha(0.95f)
                        .targetCircleColor(R.color.white)
                        .titleTextSize(30)
                        .titleTextColor(R.color.white)
                        .descriptionTextSize(20)
                        .textTypeface(Typeface.SANS_SERIF)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(false)
                        .transparentTarget(true),
                new TapTargetView.Listener() {
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetCancel(view);
                        TextInputLayout textInputLayout = requireView().findViewById(R.id.name);
                        EditText name = textInputLayout.getEditText();
                        name.setText(getString(R.string.example1));
                        guide3();
                    }

                    @Override
                    public void onTargetLongClick(TapTargetView view) {
                        super.onTargetLongClick(view);
                        TextInputLayout textInputLayout = requireView().findViewById(R.id.name);
                        EditText name = textInputLayout.getEditText();
                        name.setText(getString(R.string.example1));
                        guide3();
                    }
                });
    }

    private void guide3(){
        TapTargetView.showFor(requireActivity(),
                TapTarget.forView(btn, getString(R.string.guide9), getString(R.string.guide10))
                        .outerCircleColor(R.color.teal_700)
                        .outerCircleAlpha(0.95f)
                        .targetCircleColor(R.color.white)
                        .titleTextSize(30)
                        .titleTextColor(R.color.white)
                        .descriptionTextSize(20)
                        .textTypeface(Typeface.SANS_SERIF)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(false)
                        .transparentTarget(true),
                new TapTargetView.Listener() {
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetCancel(view);
                        Functions.save(requireContext(), "guide", "2");
                        btn.callOnClick();
                    }

                    @Override
                    public void onTargetLongClick(TapTargetView view) {
                        super.onTargetLongClick(view);
                        Functions.save(requireContext(), "guide", "2");
                        btn.callOnClick();
                    }
                });
    }
}
