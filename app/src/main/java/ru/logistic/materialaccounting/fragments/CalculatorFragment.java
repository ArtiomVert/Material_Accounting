package ru.logistic.materialaccounting.fragments;

//это делал не я
//убейте меня

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import ru.logistic.materialaccounting.R;

public class CalculatorFragment extends Fragment {

    private TextInputEditText kol_vo;
    private TextInputEditText ves_one;
    private TextInputEditText ves;
    private TextInputEditText price;
    private TextInputEditText price_one;
    private TextInputEditText pack;
    private TextInputEditText pack_one;
    private TextInputEditText kol_vo1;
    private TextInputEditText ves_one1;
    private TextInputEditText ves1;
    private TextInputEditText price1;
    private TextInputEditText price_one1;
    private TextInputEditText pack1;
    private TextInputEditText pack_one1;


    //что это?
    //класные названия
    private float k(float kol_vo, float ves_one, float ves, float price, float price_one, float pack, float pack_one) {
        if (kol_vo == 0) {
            if (pack != 0 && pack_one != 0) {
                return (pack * pack_one);
            } else if (price != 0 && price_one != 0) {
                return (price / price_one);
            } else if (ves != 0 && ves_one != 0){
                return (ves / ves_one);
            }
            else {
                return  (0);
            }
        }
        else{
            return (kol_vo);
        }
    }
    private float vo(float kol_vo, float ves_one, float ves) {
        if (ves_one == 0){
            if (ves != 0 && kol_vo!= 0){
                return (ves / kol_vo);
            }
            else{
                return (0);
            }
        }
        else{
            return (ves_one);
        }
    }
    private float v(float kol_vo, float ves_one, float ves) {
        if (ves == 0){
            if (ves_one != 0 && kol_vo!= 0){
                return (ves_one * kol_vo);
            }
            else{
                return (0);
            }
        }
        else{
            return (ves);
        }
    }
    private float pro(float kol_vo, float price, float price_one) {
        if (price_one == 0){
            if (price != 0 && kol_vo!= 0){
                return (price / kol_vo);
            }
            else{
                return (0);
            }
        }
        else{
            return (price_one);
        }
    }
    private float pr(float kol_vo, float price, float price_one) {
        if (price == 0){
            if (price_one != 0 && kol_vo!= 0){
                return (price_one * kol_vo);
            }
            else{
                return (0);
            }
        }
        else{
            return (price);
        }
    }
    private float po(float kol_vo, float pack, float pack_one) {
        if (pack_one == 0){
            if (pack != 0 && kol_vo!= 0){
                return (kol_vo / pack);
            }
            else{
                return (0);
            }
        }
        else{
            return (pack_one);
        }
    }
    private float p(float kol_vo, float pack, float pack_one) {
        if (pack == 0){
            if (pack_one != 0 && kol_vo!= 0){
                return (kol_vo / pack_one);
            }
            else{
                return  (0);
            }
        }
        else{
            return (pack);
        }
    }

    public CalculatorFragment() {
        super(R.layout.calculator_fragment);
    }


    @SuppressLint({"SetTextI18n", "CutPasteId"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        kol_vo = view.findViewById(R.id.kol_vo);
        ves = view.findViewById(R.id.ves);
        ves_one = view.findViewById(R.id.ves_one);
        price = view.findViewById(R.id.price);
        price_one = view.findViewById(R.id.price_one);
        pack = view.findViewById(R.id.pack);
        pack_one = view.findViewById(R.id.pack_one);
        kol_vo1 = view.findViewById(R.id.kol_vo);
        ves1 = view.findViewById(R.id.ves);
        ves_one1 = view.findViewById(R.id.ves_one);
        price1 = view.findViewById(R.id.price);
        price_one1 = view.findViewById(R.id.price_one);
        pack1 = view.findViewById(R.id.pack);
        pack_one1 = view.findViewById(R.id.pack_one);

        Button ent = view.findViewById(R.id.enter);
        ent.setOnClickListener(v1 -> {
            //Зачем?
            if (ves.getText().toString().equals("")){
                ves.setText("0");
            }
            if (ves_one.getText().toString().equals("")){
                ves_one.setText("0");
            }
            if (price.getText().toString().equals("")){
                price.setText("0");
            }
            if (price_one.getText().toString().equals("")){
                price_one.setText("0");
            }
            if (pack.getText().toString().equals("")){
                pack.setText("0");
            }
            if (pack_one.getText().toString().equals("")){
                pack_one.setText("0");
            }
            if (kol_vo.getText().toString().equals("")){
                kol_vo.setText("0");
            }
            for (int i = 0; i < 5; i++){
                //это пипец
                float kol_vo = Float.parseFloat(this.kol_vo.getText().toString());
                float ves_one = Float.parseFloat(this.ves_one.getText().toString());
                float ves = Float.parseFloat(this.ves.getText().toString());
                float price_one = Float.parseFloat(this.price_one.getText().toString());
                float price = Float.parseFloat(this.price.getText().toString());
                float pack_one = Float.parseFloat(this.pack_one.getText().toString());
                float pack = Float.parseFloat(this.pack.getText().toString());
                //я не хочу так жить
                kol_vo1.setText(String.valueOf(k(kol_vo, ves_one,ves, price,price_one,pack, pack_one)));
                ves_one1.setText(String.valueOf(vo(kol_vo, ves_one,ves)));
                ves1.setText(String.valueOf(v(kol_vo, ves_one,ves)));
                price_one1.setText(String.valueOf(pro(kol_vo, price,price_one)));
                price1.setText(String.valueOf(pr(kol_vo, price,price_one)));
                pack_one1.setText(String.valueOf(po(kol_vo, pack, pack_one)));
                pack1.setText(String.valueOf(p(kol_vo, pack, pack_one)));
            }

        });

    }
}