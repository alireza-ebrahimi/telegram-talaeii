package com.mohamadamin.persianmaterialdatetimepicker;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.util.SimpleArrayMap;

public class TypefaceHelper {
    private static final SimpleArrayMap<String, Typeface> cache = new SimpleArrayMap();

    public static Typeface get(Context c, String name) {
        synchronized (cache) {
            if (cache.containsKey(name)) {
                Typeface typeface = (Typeface) cache.get(name);
                return typeface;
            }
            Typeface t = Typeface.createFromAsset(c.getAssets(), String.format("fonts/%s.ttf", new Object[]{name}));
            cache.put(name, t);
            return t;
        }
    }
}
