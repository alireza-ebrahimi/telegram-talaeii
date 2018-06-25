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
    private static final String TAG = "ScreenshotUtils";

    public static String buildScreenshotPath(Context context) {
        String filename = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.US).format(new Date());
        File publicDirectory = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "AsanPardakht");
        if (!publicDirectory.exists()) {
            publicDirectory.mkdirs();
        }
        return new File(publicDirectory, filename + ".jpg").getAbsolutePath();
    }

    public static boolean saveScreenshot(View view, String pathToSave, boolean visibleInGallery) {
        try {
            Context appContext = view.getContext().getApplicationContext();
            view.setDrawingCacheEnabled(true);
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();
            Bitmap screenshot = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);
            File screenshotFile = new File(pathToSave);
            screenshotFile.createNewFile();
            OutputStream outputStream = new FileOutputStream(screenshotFile);
            screenshot.compress(CompressFormat.JPEG, 80, outputStream);
            outputStream.flush();
            outputStream.close();
            screenshot.recycle();
            if (!visibleInGallery) {
                return true;
            }
            ContentValues values = new ContentValues();
            values.put("title", "AsanPardakht");
            values.put("description", "Transaction Screenshot");
            values.put("datetaken", Long.valueOf(System.currentTimeMillis()));
            values.put("mime_type", "image/jpeg");
            values.put("_data", pathToSave);
            appContext.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);
            return true;
        } catch (Exception e) {
            SDKLog.m36e(TAG, "error in take screenshot from view", e, new Object[0]);
            return false;
        }
    }
}
