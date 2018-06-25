package com.h6ah4i.android.widget.advrecyclerview.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

public class ItemShadowDecorator extends ItemDecoration {
    private final boolean mCastShadowForTransparentBackgroundItem;
    private final NinePatchDrawable mShadowDrawable;
    private final Rect mShadowPadding;

    public ItemShadowDecorator(@NonNull NinePatchDrawable shadow) {
        this(shadow, true);
    }

    public ItemShadowDecorator(@NonNull NinePatchDrawable shadow, boolean castShadowForTransparentBackgroundItem) {
        this.mShadowPadding = new Rect();
        this.mShadowDrawable = shadow;
        this.mShadowDrawable.getPadding(this.mShadowPadding);
        this.mCastShadowForTransparentBackgroundItem = castShadowForTransparentBackgroundItem;
    }

    public void onDraw(Canvas c, RecyclerView parent, State state) {
        int childCount = parent.getChildCount();
        if (childCount != 0) {
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                if (shouldDrawDropShadow(child)) {
                    int tx = (int) (ViewCompat.getTranslationX(child) + 0.5f);
                    int ty = (int) (ViewCompat.getTranslationY(child) + 0.5f);
                    int top = child.getTop() - this.mShadowPadding.top;
                    this.mShadowDrawable.setBounds((child.getLeft() - this.mShadowPadding.left) + tx, top + ty, (child.getRight() + this.mShadowPadding.right) + tx, (child.getBottom() + this.mShadowPadding.bottom) + ty);
                    this.mShadowDrawable.draw(c);
                }
            }
        }
    }

    private boolean shouldDrawDropShadow(View child) {
        if (child.getVisibility() != 0 || ViewCompat.getAlpha(child) != 1.0f) {
            return false;
        }
        Drawable background = child.getBackground();
        if (background == null) {
            return false;
        }
        if (!this.mCastShadowForTransparentBackgroundItem && (background instanceof ColorDrawable) && ((ColorDrawable) background).getAlpha() == 0) {
            return false;
        }
        return true;
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        outRect.set(0, 0, 0, 0);
    }
}
