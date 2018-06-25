package com.persianswitch.sdk.base.utils;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import com.persianswitch.sdk.base.log.SDKLog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class ScreenshotUtils {
    /* renamed from: a */
    public static String m10766a(Context context) {
        String format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.US).format(new Date());
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "AsanPardakht");
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(file, format + ".jpg").getAbsolutePath();
    }

    /* renamed from: a */
    public static boolean m10767a(View view, String str, boolean z) {
        try {
            Context applicationContext = view.getContext().getApplicationContext();
            view.setDrawingCacheEnabled(true);
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();
            Bitmap createBitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);
            File file = new File(str);
            file.createNewFile();
            OutputStream fileOutputStream = new FileOutputStream(file);
            createBitmap.compress(CompressFormat.JPEG, 80, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            createBitmap.recycle();
            if (!z) {
                return true;
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("title", "AsanPardakht");
            contentValues.put("description", "Transaction Screenshot");
            contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
            contentValues.put("mime_type", "image/jpeg");
            contentValues.put("_data", str);
            applicationContext.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, contentValues);
            return true;
        } catch (Throwable e) {
            SDKLog.m10659b("ScreenshotUtils", "error in take screenshot from view", e, new Object[0]);
            return false;
        }
    }
}
