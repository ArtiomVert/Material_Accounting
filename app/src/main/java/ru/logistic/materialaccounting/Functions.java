package ru.logistic.materialaccounting;

import android.content.Context;

public class Functions {

    public static String load(Context ctx, String key) {
        return ctx.getSharedPreferences("materialaccouting", Context.MODE_PRIVATE).getString(key, "");
    }

    public static void save(Context ctx, String key, String str) {
        ctx.getSharedPreferences("materialaccouting", Context.MODE_PRIVATE).edit().putString(key, str).apply();
    }

    public static Long newId(Context ctx) {
        long newid= ctx.getSharedPreferences("catgame", Context.MODE_PRIVATE).getLong("id", 1)+1;
        ctx.getSharedPreferences("catgame", Context.MODE_PRIVATE).edit().putLong("id", newid).apply();
        return ctx.getSharedPreferences("catgame", Context.MODE_PRIVATE).getLong("id", 0);
    }
}