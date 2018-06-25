package android.support.v7.content.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import java.util.WeakHashMap;

public final class AppCompatResources {
    private static final String LOG_TAG = "AppCompatResources";
    private static final ThreadLocal<TypedValue> TL_TYPED_VALUE = new ThreadLocal();
    private static final Object sColorStateCacheLock = new Object();
    private static final WeakHashMap<Context, SparseArray<ColorStateListCacheEntry>> sColorStateCaches = new WeakHashMap(0);

    private static class ColorStateListCacheEntry {
        final Configuration configuration;
        final ColorStateList value;

        ColorStateListCacheEntry(@NonNull ColorStateList value, @NonNull Configuration configuration) {
            this.value = value;
            this.configuration = configuration;
        }
    }

    private AppCompatResources() {
    }

    public static ColorStateList getColorStateList(@NonNull Context context, @ColorRes int resId) {
        if (VERSION.SDK_INT >= 23) {
            return context.getColorStateList(resId);
        }
        ColorStateList csl = getCachedColorStateList(context, resId);
        if (csl != null) {
            return csl;
        }
        csl = inflateColorStateList(context, resId);
        if (csl == null) {
            return ContextCompat.getColorStateList(context, resId);
        }
        addColorStateListToCache(context, resId, csl);
        return csl;
    }

    @Nullable
    public static Drawable getDrawable(@NonNull Context context, @DrawableRes int resId) {
        return AppCompatDrawableManager.get().getDrawable(context, resId);
    }

    @Nullable
    private static ColorStateList inflateColorStateList(Context context, int resId) {
        ColorStateList colorStateList = null;
        if (!isColorInt(context, resId)) {
            Resources r = context.getResources();
            try {
                colorStateList = AppCompatColorStateListInflater.createFromXml(r, r.getXml(resId), context.getTheme());
            } catch (Exception e) {
                Log.e(LOG_TAG, "Failed to inflate ColorStateList, leaving it to the framework", e);
            }
        }
        return colorStateList;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @android.support.annotation.Nullable
    private static android.content.res.ColorStateList getCachedColorStateList(@android.support.annotation.NonNull android.content.Context r5, @android.support.annotation.ColorRes int r6) {
        /*
        r3 = sColorStateCacheLock;
        monitor-enter(r3);
        r2 = sColorStateCaches;	 Catch:{ all -> 0x0035 }
        r0 = r2.get(r5);	 Catch:{ all -> 0x0035 }
        r0 = (android.util.SparseArray) r0;	 Catch:{ all -> 0x0035 }
        if (r0 == 0) goto L_0x0032;
    L_0x000d:
        r2 = r0.size();	 Catch:{ all -> 0x0035 }
        if (r2 <= 0) goto L_0x0032;
    L_0x0013:
        r1 = r0.get(r6);	 Catch:{ all -> 0x0035 }
        r1 = (android.support.v7.content.res.AppCompatResources.ColorStateListCacheEntry) r1;	 Catch:{ all -> 0x0035 }
        if (r1 == 0) goto L_0x0032;
    L_0x001b:
        r2 = r1.configuration;	 Catch:{ all -> 0x0035 }
        r4 = r5.getResources();	 Catch:{ all -> 0x0035 }
        r4 = r4.getConfiguration();	 Catch:{ all -> 0x0035 }
        r2 = r2.equals(r4);	 Catch:{ all -> 0x0035 }
        if (r2 == 0) goto L_0x002f;
    L_0x002b:
        r2 = r1.value;	 Catch:{ all -> 0x0035 }
        monitor-exit(r3);	 Catch:{ all -> 0x0035 }
    L_0x002e:
        return r2;
    L_0x002f:
        r0.remove(r6);	 Catch:{ all -> 0x0035 }
    L_0x0032:
        monitor-exit(r3);	 Catch:{ all -> 0x0035 }
        r2 = 0;
        goto L_0x002e;
    L_0x0035:
        r2 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0035 }
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.content.res.AppCompatResources.getCachedColorStateList(android.content.Context, int):android.content.res.ColorStateList");
    }

    private static void addColorStateListToCache(@NonNull Context context, @ColorRes int resId, @NonNull ColorStateList value) {
        synchronized (sColorStateCacheLock) {
            SparseArray<ColorStateListCacheEntry> entries = (SparseArray) sColorStateCaches.get(context);
            if (entries == null) {
                entries = new SparseArray();
                sColorStateCaches.put(context, entries);
            }
            entries.append(resId, new ColorStateListCacheEntry(value, context.getResources().getConfiguration()));
        }
    }

    private static boolean isColorInt(@NonNull Context context, @ColorRes int resId) {
        Resources r = context.getResources();
        TypedValue value = getTypedValue();
        r.getValue(resId, value, true);
        if (value.type < 28 || value.type > 31) {
            return false;
        }
        return true;
    }

    @NonNull
    private static TypedValue getTypedValue() {
        TypedValue tv = (TypedValue) TL_TYPED_VALUE.get();
        if (tv != null) {
            return tv;
        }
        tv = new TypedValue();
        TL_TYPED_VALUE.set(tv);
        return tv;
    }
}
