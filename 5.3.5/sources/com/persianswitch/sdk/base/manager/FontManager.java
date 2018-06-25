package com.persianswitch.sdk.base.manager;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public final class FontManager {
    private static final String PERSIAN_FONT_PATH = "nyekan.ttf";
    private static FontManager instance;
    private final Context context;
    private Typeface englishFont = null;
    private Typeface persianFont = Typeface.createFromAsset(this.context.getAssets(), PERSIAN_FONT_PATH);

    private FontManager(Context context) {
        this.context = context;
    }

    public static void overrideFonts(View view) {
        if (view != null) {
            getInstance(view.getContext()).overrideTypeface(view);
        }
    }

    public static Typeface getTypefaceForLanguage(Context context) {
        return getInstance(context).typefaceForLang(LanguageManager.getInstance(context).getCurrentLanguage());
    }

    private Typeface typefaceForLang(String lang) {
        if (LanguageManager.PERSIAN.equals(lang)) {
            return this.persianFont;
        }
        return this.englishFont;
    }

    public static FontManager getInstance(Context context) {
        if (instance == null) {
            instance = new FontManager(context);
        }
        return instance;
    }

    public void overrideTypeface(View view, Typeface typeface) {
        if (view instanceof TextView) {
            ((TextView) view).setTypeface(typeface);
        } else if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                overrideTypeface(((ViewGroup) view).getChildAt(i), typeface);
            }
        }
    }

    public void overrideTypeface(View view) {
        overrideTypeface(view, typefaceForLang(LanguageManager.getInstance(this.context).getCurrentLanguage()));
    }
}
