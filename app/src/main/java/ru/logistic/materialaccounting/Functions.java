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
    public static String newName() {
        Calendar c = Calendar.getInstance();
        String name = "";
        name += c.get(Calendar.SECOND);
        name += "_";
        name += c.get(Calendar.MINUTE);
        name += "_";
        name += c.get(Calendar.HOUR_OF_DAY);
        name += "_";
        name += c.get(Calendar.DAY_OF_MONTH);
        name += "_";
        name += c.get(Calendar.MONTH);
        name += "_";
        name += c.get(Calendar.YEAR);
        name += ".png";
        return name;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String Time() {
        Calendar c = Calendar.getInstance();
        String date = "";
        date += c.get(Calendar.SECOND);
        date += ":";
        date += c.get(Calendar.MINUTE);
        date += ":";
        date += c.get(Calendar.HOUR_OF_DAY);
        date += "\n";
        date += c.get(Calendar.DAY_OF_MONTH);
        date += ".";
        date += c.get(Calendar.MONTH);
        date += ".";
        date += c.get(Calendar.YEAR);
        return date;
    }
}