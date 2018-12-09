package com.persianswitch.sdk.base.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public abstract class APCustomView extends LinearLayout {
    public APCustomView(Context context) {
        super(context);
        m10984b(null);
    }

    public APCustomView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m10984b(attributeSet);
    }

    public APCustomView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m10984b(attributeSet);
    }

    /* renamed from: b */
    private void m10984b(AttributeSet attributeSet) {
        LayoutInflater.from(getContext()).inflate(getViewLayoutResourceId(), this, true);
        mo3286a(attributeSet);
        mo3287b();
    }

    /* renamed from: a */
    public void mo3285a() {
    }

    /* renamed from: a */
    protected abstract void mo3286a(AttributeSet attributeSet);

    /* renamed from: b */
    public abstract void mo3287b();

    protected abstract int getViewLayoutResourceId();

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean onTouchEvent = super.onTouchEvent(motionEvent);
        if (!onTouchEvent && motionEvent.getAction() == 0) {
            return true;
        }
        if (onTouchEvent || motionEvent.getAction() != 1) {
            return onTouchEvent;
        }
        mo3285a();
        return true;
    }
}
