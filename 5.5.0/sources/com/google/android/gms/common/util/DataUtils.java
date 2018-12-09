package com.google.android.gms.common.util;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.CharArrayBuffer;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public final class DataUtils {
    public static void copyStringToBuffer(String str, CharArrayBuffer charArrayBuffer) {
        if (TextUtils.isEmpty(str)) {
            charArrayBuffer.sizeCopied = 0;
        } else if (charArrayBuffer.data == null || charArrayBuffer.data.length < str.length()) {
            charArrayBuffer.data = str.toCharArray();
        } else {
            str.getChars(0, str.length(), charArrayBuffer.data, 0);
        }
        charArrayBuffer.sizeCopied = str.length();
    }

    public static byte[] loadImageBytes(AssetManager assetManager, String str) {
        try {
            return IOUtils.readInputStreamFully(assetManager.open(str));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @VisibleForTesting
    public static byte[] loadImageBytes(Resources resources, int i) {
        try {
            return IOUtils.readInputStreamFully(resources.openRawResource(i));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] loadImageBytes(Bitmap bitmap) {
        return loadImageBytes(bitmap, 100);
    }

    public static byte[] loadImageBytes(Bitmap bitmap, int i) {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, i, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] loadImageBytes(BitmapDrawable bitmapDrawable) {
        return loadImageBytes(bitmapDrawable.getBitmap());
    }
}
