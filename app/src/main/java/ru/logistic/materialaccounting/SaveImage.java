package ru.logistic.materialaccounting;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public final class SaveImage {


    public static String saveToInternalStorage(Context applicationContext, Bitmap bitmapImage, String name) {
        ContextWrapper cw = new ContextWrapper(applicationContext);
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File path = new File(directory, name);
        FileOutputStream fos = null;
        try {
            try {
                fos = new FileOutputStream(path);
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 85, fos);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();

    }


    public static File loadImageFromStorage(Context context, String name) {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        return new File(directory, name);
    }
}
