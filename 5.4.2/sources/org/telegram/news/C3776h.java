package org.telegram.news;

import android.content.Context;
import android.util.Log;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import org.ir.talaeii.R;
import utils.p178a.C3791b;

/* renamed from: org.telegram.news.h */
class C3776h extends SimpleOnScaleGestureListener {
    /* renamed from: a */
    private final Context f10132a;
    /* renamed from: b */
    private final C3733c f10133b;

    public C3776h(Context context, C3733c c3733c) {
        this.f10132a = context;
        this.f10133b = c3733c;
    }

    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        float s = C3791b.m14039s(this.f10132a);
        float scaleFactor = scaleGestureDetector.getScaleFactor();
        Log.d("Factor1", String.valueOf(scaleFactor));
        Log.d("Factor2", String.valueOf(s));
        s *= scaleFactor;
        if (s > ((float) this.f10132a.getResources().getInteger(R.integer.news_max_font_size))) {
            s = (float) this.f10132a.getResources().getInteger(R.integer.news_max_font_size);
        }
        if (s < ((float) this.f10132a.getResources().getInteger(R.integer.news_description_int))) {
            s = (float) this.f10132a.getResources().getInteger(R.integer.news_description_int);
        }
        C3791b.m13929a(this.f10132a, Float.valueOf(s));
        this.f10133b.mo4195a(s);
        return true;
    }

    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        boolean onScaleBegin = super.onScaleBegin(scaleGestureDetector);
        Log.d("Factor1", "onScaleBegin " + onScaleBegin);
        return onScaleBegin;
    }

    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        Log.d("Factor1", "onScaleEnd");
        super.onScaleEnd(scaleGestureDetector);
    }
}
