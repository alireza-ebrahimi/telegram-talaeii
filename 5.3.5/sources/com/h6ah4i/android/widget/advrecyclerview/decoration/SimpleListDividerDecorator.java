package com.h6ah4i.android.widget.advrecyclerview.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

public class SimpleListDividerDecorator extends ItemDecoration {
    private final int mHorizontalDividerHeight;
    private final Drawable mHorizontalDrawable;
    private final boolean mOverlap;
    private final int mVerticalDividerWidth;
    private final Drawable mVerticalDrawable;

    public SimpleListDividerDecorator(@Nullable Drawable divider, boolean overlap) {
        this(divider, null, overlap);
    }

    public SimpleListDividerDecorator(@Nullable Drawable horizontalDivider, @Nullable Drawable verticalDivider, boolean overlap) {
        int intrinsicHeight;
        int i = 0;
        this.mHorizontalDrawable = horizontalDivider;
        this.mVerticalDrawable = verticalDivider;
        if (this.mHorizontalDrawable != null) {
            intrinsicHeight = this.mHorizontalDrawable.getIntrinsicHeight();
        } else {
            intrinsicHeight = 0;
        }
        this.mHorizontalDividerHeight = intrinsicHeight;
        if (this.mVerticalDrawable != null) {
            i = this.mVerticalDrawable.getIntrinsicWidth();
        }
        this.mVerticalDividerWidth = i;
        this.mOverlap = overlap;
    }

    public void onDrawOver(Canvas c, RecyclerView parent, State state) {
        int childCount = parent.getChildCount();
        if (childCount != 0) {
            float xPositionThreshold = this.mOverlap ? 1.0f : ((float) this.mVerticalDividerWidth) + 1.0f;
            float yPositionThreshold;
            if (this.mOverlap) {
                yPositionThreshold = 1.0f;
            } else {
                yPositionThreshold = ((float) this.mHorizontalDividerHeight) + 1.0f;
            }
            for (int i = 0; i < childCount - 1; i++) {
                View child = parent.getChildAt(i);
                View nextChild = parent.getChildAt(i + 1);
                if (child.getVisibility() == 0 && nextChild.getVisibility() == 0) {
                    float childBottom = ((float) child.getBottom()) + ViewCompat.getTranslationY(child);
                    float nextChildTop = ((float) nextChild.getTop()) + ViewCompat.getTranslationY(nextChild);
                    float childRight = ((float) child.getRight()) + ViewCompat.getTranslationX(child);
                    float nextChildLeft = ((float) nextChild.getLeft()) + ViewCompat.getTranslationX(nextChild);
                    if ((this.mHorizontalDividerHeight != 0 && Math.abs(nextChildTop - childBottom) < yPositionThreshold) || (this.mVerticalDividerWidth != 0 && Math.abs(nextChildLeft - childRight) < xPositionThreshold)) {
                        if (Math.abs((ViewCompat.getTranslationZ(nextChild) + ViewCompat.getElevation(nextChild)) - (ViewCompat.getTranslationZ(child) + ViewCompat.getElevation(child))) < 1.0f) {
                            int left;
                            int right;
                            int top;
                            int bottom;
                            float childAlpha = ViewCompat.getAlpha(child);
                            float nextChildAlpha = ViewCompat.getAlpha(nextChild);
                            int tx = (int) (ViewCompat.getTranslationX(child) + 0.5f);
                            int ty = (int) (ViewCompat.getTranslationY(child) + 0.5f);
                            if (this.mHorizontalDividerHeight != 0) {
                                left = child.getLeft();
                                right = child.getRight();
                                top = child.getBottom() - (this.mOverlap ? this.mHorizontalDividerHeight : 0);
                                bottom = top + this.mHorizontalDividerHeight;
                                this.mHorizontalDrawable.setAlpha((int) ((127.5f * (childAlpha + nextChildAlpha)) + 0.5f));
                                this.mHorizontalDrawable.setBounds(left + tx, top + ty, right + tx, bottom + ty);
                                this.mHorizontalDrawable.draw(c);
                            }
                            if (this.mVerticalDividerWidth != 0) {
                                left = child.getRight() - (this.mOverlap ? this.mVerticalDividerWidth : 0);
                                right = left + this.mVerticalDividerWidth;
                                top = child.getTop();
                                bottom = child.getBottom();
                                this.mVerticalDrawable.setAlpha((int) ((127.5f * (childAlpha + nextChildAlpha)) + 0.5f));
                                this.mVerticalDrawable.setBounds(left + tx, top + ty, right + tx, bottom + ty);
                                this.mVerticalDrawable.draw(c);
                            }
                        }
                    }
                }
            }
        }
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        if (this.mOverlap) {
            outRect.set(0, 0, 0, 0);
        } else {
            outRect.set(0, 0, this.mVerticalDividerWidth, this.mHorizontalDividerHeight);
        }
    }
}
