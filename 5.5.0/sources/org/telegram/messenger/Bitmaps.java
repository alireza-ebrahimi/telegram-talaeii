package org.telegram.messenger;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build.VERSION;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class Bitmaps {
    private static final ThreadLocal<byte[]> jpegData = new C29791();
    private static volatile Matrix sScaleMatrix;

    /* renamed from: org.telegram.messenger.Bitmaps$1 */
    static class C29791 extends ThreadLocal<byte[]> {
        C29791() {
        }

        protected byte[] initialValue() {
            return new byte[]{(byte) -1, (byte) -40, (byte) -1, (byte) -37, (byte) 0, (byte) 67, (byte) 0, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -64, (byte) 0, (byte) 17, (byte) 8, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 3, (byte) 1, (byte) 34, (byte) 0, (byte) 2, (byte) 17, (byte) 0, (byte) 3, (byte) 17, (byte) 0, (byte) -1, (byte) -60, (byte) 0, (byte) 31, (byte) 0, (byte) 0, (byte) 1, (byte) 5, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10, (byte) 11, (byte) -1, (byte) -60, (byte) 0, (byte) -75, (byte) 16, (byte) 0, (byte) 2, (byte) 1, (byte) 3, (byte) 3, (byte) 2, (byte) 4, (byte) 3, (byte) 5, (byte) 5, (byte) 4, (byte) 4, (byte) 0, (byte) 0, (byte) 1, (byte) 125, (byte) 1, (byte) 2, (byte) 3, (byte) 0, (byte) 4, (byte) 17, (byte) 5, (byte) 18, (byte) 33, (byte) 49, (byte) 65, (byte) 6, (byte) 19, (byte) 81, (byte) 97, (byte) 7, (byte) 34, (byte) 113, (byte) 20, (byte) 50, (byte) -127, (byte) -111, (byte) -95, (byte) 8, (byte) 35, (byte) 66, (byte) -79, (byte) -63, (byte) 21, (byte) 82, (byte) -47, (byte) -16, (byte) 36, (byte) 51, (byte) 98, (byte) 114, (byte) -126, (byte) 9, (byte) 10, (byte) 22, (byte) 23, (byte) 24, (byte) 25, (byte) 26, (byte) 37, (byte) 38, (byte) 39, (byte) 40, (byte) 41, (byte) 42, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 58, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) 106, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122, (byte) -125, (byte) -124, (byte) -123, (byte) -122, (byte) -121, (byte) -120, (byte) -119, (byte) -118, (byte) -110, (byte) -109, (byte) -108, (byte) -107, (byte) -106, (byte) -105, (byte) -104, (byte) -103, (byte) -102, (byte) -94, (byte) -93, (byte) -92, (byte) -91, (byte) -90, (byte) -89, (byte) -88, (byte) -87, (byte) -86, (byte) -78, (byte) -77, (byte) -76, (byte) -75, (byte) -74, (byte) -73, (byte) -72, (byte) -71, (byte) -70, (byte) -62, (byte) -61, (byte) -60, (byte) -59, (byte) -58, (byte) -57, (byte) -56, (byte) -55, (byte) -54, (byte) -46, (byte) -45, (byte) -44, (byte) -43, (byte) -42, (byte) -41, (byte) -40, (byte) -39, (byte) -38, (byte) -31, (byte) -30, (byte) -29, (byte) -28, (byte) -27, (byte) -26, (byte) -25, (byte) -24, (byte) -23, (byte) -22, (byte) -15, (byte) -14, (byte) -13, (byte) -12, (byte) -11, (byte) -10, (byte) -9, (byte) -8, (byte) -7, (byte) -6, (byte) -1, (byte) -60, (byte) 0, (byte) 31, (byte) 1, (byte) 0, (byte) 3, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10, (byte) 11, (byte) -1, (byte) -60, (byte) 0, (byte) -75, (byte) 17, (byte) 0, (byte) 2, (byte) 1, (byte) 2, (byte) 4, (byte) 4, (byte) 3, (byte) 4, (byte) 7, (byte) 5, (byte) 4, (byte) 4, (byte) 0, (byte) 1, (byte) 2, (byte) 119, (byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 17, (byte) 4, (byte) 5, (byte) 33, (byte) 49, (byte) 6, (byte) 18, (byte) 65, (byte) 81, (byte) 7, (byte) 97, (byte) 113, (byte) 19, (byte) 34, (byte) 50, (byte) -127, (byte) 8, (byte) 20, (byte) 66, (byte) -111, (byte) -95, (byte) -79, (byte) -63, (byte) 9, (byte) 35, (byte) 51, (byte) 82, (byte) -16, (byte) 21, (byte) 98, (byte) 114, (byte) -47, (byte) 10, (byte) 22, (byte) 36, (byte) 52, (byte) -31, (byte) 37, (byte) -15, (byte) 23, (byte) 24, (byte) 25, (byte) 26, (byte) 38, (byte) 39, (byte) 40, (byte) 41, (byte) 42, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 58, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) 106, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122, (byte) -126, (byte) -125, (byte) -124, (byte) -123, (byte) -122, (byte) -121, (byte) -120, (byte) -119, (byte) -118, (byte) -110, (byte) -109, (byte) -108, (byte) -107, (byte) -106, (byte) -105, (byte) -104, (byte) -103, (byte) -102, (byte) -94, (byte) -93, (byte) -92, (byte) -91, (byte) -90, (byte) -89, (byte) -88, (byte) -87, (byte) -86, (byte) -78, (byte) -77, (byte) -76, (byte) -75, (byte) -74, (byte) -73, (byte) -72, (byte) -71, (byte) -70, (byte) -62, (byte) -61, (byte) -60, (byte) -59, (byte) -58, (byte) -57, (byte) -56, (byte) -55, (byte) -54, (byte) -46, (byte) -45, (byte) -44, (byte) -43, (byte) -42, (byte) -41, (byte) -40, (byte) -39, (byte) -38, (byte) -30, (byte) -29, (byte) -28, (byte) -27, (byte) -26, (byte) -25, (byte) -24, (byte) -23, (byte) -22, (byte) -14, (byte) -13, (byte) -12, (byte) -11, (byte) -10, (byte) -9, (byte) -8, (byte) -7, (byte) -6, (byte) -1, (byte) -38, (byte) 0, (byte) 12, (byte) 3, (byte) 1, (byte) 0, (byte) 2, (byte) 17, (byte) 3, (byte) 17, (byte) 0, (byte) 63, (byte) 0, (byte) -114, (byte) -118, (byte) 40, (byte) -96, (byte) 15, (byte) -1, (byte) -39};
        }
    }

    /* renamed from: org.telegram.messenger.Bitmaps$2 */
    static /* synthetic */ class C29802 {
        static final /* synthetic */ int[] $SwitchMap$android$graphics$Bitmap$Config = new int[Config.values().length];

        static {
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Config.RGB_565.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Config.ALPHA_8.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Config.ARGB_4444.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Config.ARGB_8888.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    private static void checkWidthHeight(int i, int i2) {
        if (i <= 0) {
            throw new IllegalArgumentException("width must be > 0");
        } else if (i2 <= 0) {
            throw new IllegalArgumentException("height must be > 0");
        }
    }

    private static void checkXYSign(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException("x must be >= 0");
        } else if (i2 < 0) {
            throw new IllegalArgumentException("y must be >= 0");
        }
    }

    public static Bitmap createBitmap(int i, int i2, Config config) {
        Bitmap decodeByteArray;
        if (VERSION.SDK_INT < 21) {
            Options options = new Options();
            options.inDither = true;
            options.inPreferredConfig = config;
            options.inPurgeable = true;
            options.inSampleSize = 1;
            options.inMutable = true;
            byte[] bArr = (byte[]) jpegData.get();
            bArr[76] = (byte) (i2 >> 8);
            bArr[77] = (byte) (i2 & 255);
            bArr[78] = (byte) (i >> 8);
            bArr[79] = (byte) (i & 255);
            decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
            Utilities.pinBitmap(decodeByteArray);
            decodeByteArray.setHasAlpha(true);
            decodeByteArray.eraseColor(0);
        } else {
            decodeByteArray = Bitmap.createBitmap(i, i2, config);
        }
        if (config == Config.ARGB_8888 || config == Config.ARGB_4444) {
            decodeByteArray.eraseColor(0);
        }
        return decodeByteArray;
    }

    public static Bitmap createBitmap(Bitmap bitmap, int i, int i2, int i3, int i4) {
        return createBitmap(bitmap, i, i2, i3, i4, null, false);
    }

    public static Bitmap createBitmap(Bitmap bitmap, int i, int i2, int i3, int i4, Matrix matrix, boolean z) {
        Paint paint = null;
        checkXYSign(i, i2);
        checkWidthHeight(i3, i4);
        if (i + i3 > bitmap.getWidth()) {
            throw new IllegalArgumentException("x + width must be <= bitmap.width()");
        } else if (i2 + i4 > bitmap.getHeight()) {
            throw new IllegalArgumentException("y + height must be <= bitmap.height()");
        } else if (!bitmap.isMutable() && i == 0 && i2 == 0 && i3 == bitmap.getWidth() && i4 == bitmap.getHeight() && (matrix == null || matrix.isIdentity())) {
            return bitmap;
        } else {
            Bitmap createBitmap;
            Canvas canvas = new Canvas();
            Rect rect = new Rect(i, i2, i + i3, i2 + i4);
            RectF rectF = new RectF(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) i3, (float) i4);
            Config config = Config.ARGB_8888;
            Config config2 = bitmap.getConfig();
            if (config2 != null) {
                switch (C29802.$SwitchMap$android$graphics$Bitmap$Config[config2.ordinal()]) {
                    case 1:
                        config = Config.ARGB_8888;
                        break;
                    case 2:
                        config = Config.ALPHA_8;
                        break;
                    default:
                        config = Config.ARGB_8888;
                        break;
                }
            }
            if (matrix == null || matrix.isIdentity()) {
                createBitmap = createBitmap(i3, i4, config);
            } else {
                boolean z2 = !matrix.rectStaysRect();
                RectF rectF2 = new RectF();
                matrix.mapRect(rectF2, rectF);
                int round = Math.round(rectF2.width());
                int round2 = Math.round(rectF2.height());
                if (z2) {
                    config = Config.ARGB_8888;
                }
                Bitmap createBitmap2 = createBitmap(round, round2, config);
                canvas.translate(-rectF2.left, -rectF2.top);
                canvas.concat(matrix);
                Paint paint2 = new Paint();
                paint2.setFilterBitmap(z);
                if (z2) {
                    paint2.setAntiAlias(true);
                }
                paint = paint2;
                createBitmap = createBitmap2;
            }
            createBitmap.setDensity(bitmap.getDensity());
            createBitmap.setHasAlpha(bitmap.hasAlpha());
            if (VERSION.SDK_INT >= 19) {
                createBitmap.setPremultiplied(bitmap.isPremultiplied());
            }
            canvas.setBitmap(createBitmap);
            canvas.drawBitmap(bitmap, rect, rectF, paint);
            try {
                canvas.setBitmap(null);
            } catch (Exception e) {
            }
            return createBitmap;
        }
    }

    public static Bitmap createScaledBitmap(Bitmap bitmap, int i, int i2, boolean z) {
        Matrix matrix;
        synchronized (Bitmap.class) {
            matrix = sScaleMatrix;
            sScaleMatrix = null;
        }
        if (matrix == null) {
            matrix = new Matrix();
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        matrix.setScale(((float) i) / ((float) width), ((float) i2) / ((float) height));
        Bitmap createBitmap = createBitmap(bitmap, 0, 0, width, height, matrix, z);
        synchronized (Bitmap.class) {
            if (sScaleMatrix == null) {
                sScaleMatrix = matrix;
            }
        }
        return createBitmap;
    }
}
