package com.persianswitch.sdk.base.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public abstract class APCustomView extends LinearLayout {
    protected abstract int getViewLayoutResourceId();

    protected abstract void initialView(@Nullable AttributeSet attributeSet);

    public abstract void updateView();

    public APCustomView(Context context) {
        super(context);
        _initView(null);
    }

    public APCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        _initView(attrs);
    }

    public APCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        _initView(attrs);
    }

    private void _initView(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(getViewLayoutResourceId(), this, true);
        initialView(attrs);
        updateView();
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean handled = super.onTouchEvent(event);
        if (!handled && event.getAction() == 0) {
            return true;
        }
        if (handled || event.getAction() != 1) {
            return handled;
        }
        onViewFocused();
        return true;
    }

    public void onViewFocused() {
    }

    protected int toPixel(int dp) {
        return (int) TypedValue.applyDimension(1, (float) dp, getResources().getDisplayMetrics());
    }
}
