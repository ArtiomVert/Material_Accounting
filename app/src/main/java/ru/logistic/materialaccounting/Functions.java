package ru.logistic.materialaccounting;

import android.content.Context;
import android.icu.util.Calendar;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class Functions {

    public static String load(Context ctx, String key) {
        return ctx.getSharedPreferences("materialaccouting", Context.MODE_PRIVATE).getString(key, "");
    }

    public static void save(Context ctx, String key, String str) {
        ctx.getSharedPreferences("materialaccouting", Context.MODE_PRIVATE).edit().putString(key, str).apply();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String newName(){
        Calendar c = Calendar.getInstance();
        String aboba = "";
        aboba += c.get(Calendar.SECOND);
        aboba += "_";
        aboba += c.get(Calendar.MINUTE);
        aboba += "_";
        aboba += c.get(Calendar.HOUR_OF_DAY);
        aboba += "_";
        aboba += c.get(Calendar.DAY_OF_MONTH);
        aboba += "_";
        aboba += c.get(Calendar.MONTH);
        aboba += "_";
        aboba += c.get(Calendar.YEAR);
        aboba += ".png";
        String finalAboba = aboba;
        return finalAboba;
    }
}