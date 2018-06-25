package org.telegram.news;

import android.content.Context;
import android.util.Log;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import org.ir.talaeii.R;
import utils.app.AppPreferences;

class simpleOnScaleGestureListener extends SimpleOnScaleGestureListener {
    private final Context context;
    private final IFontSizeChanging iFontSizeChanging;

    public simpleOnScaleGestureListener(Context context, IFontSizeChanging iFontSizeChanging) {
        this.context = context;
        this.iFontSizeChanging = iFontSizeChanging;
    }

    public boolean onScaleBegin(ScaleGestureDetector detector) {
        boolean ans = super.onScaleBegin(detector);
        Log.d("Factor1", "onScaleBegin " + ans);
        return ans;
    }

    public void onScaleEnd(ScaleGestureDetector detector) {
        Log.d("Factor1", "onScaleEnd");
        super.onScaleEnd(detector);
    }

    public boolean onScale(ScaleGestureDetector detector) {
        float size = AppPreferences.getTextSize(this.context);
        float factor = detector.getScaleFactor();
        Log.d("Factor1", String.valueOf(factor));
        Log.d("Factor2", String.valueOf(size));
        float product = size * factor;
        if (product > ((float) this.context.getResources().getInteger(R.integer.news_max_font_size))) {
            product = (float) this.context.getResources().getInteger(R.integer.news_max_font_size);
        }
        if (product < ((float) this.context.getResources().getInteger(R.integer.news_description_int))) {
            product = (float) this.context.getResources().getInteger(R.integer.news_description_int);
        }
        AppPreferences.setTextSize(this.context, Float.valueOf(product));
        this.iFontSizeChanging.fontChanged(product);
        return true;
    }
}
