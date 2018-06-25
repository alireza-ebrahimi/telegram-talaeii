package com.h6ah4i.android.widget.advrecyclerview.draggable;

import android.graphics.Canvas;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;

abstract class BaseEdgeEffectDecorator extends ItemDecoration {
    protected static final int EDGE_BOTTOM = 3;
    protected static final int EDGE_LEFT = 0;
    protected static final int EDGE_RIGHT = 2;
    protected static final int EDGE_TOP = 1;
    private EdgeEffectCompat mGlow1;
    private int mGlow1Dir;
    private EdgeEffectCompat mGlow2;
    private int mGlow2Dir;
    private RecyclerView mRecyclerView;
    private boolean mStarted;

    protected abstract int getEdgeDirection(int i);

    public BaseEdgeEffectDecorator(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    public void onDrawOver(Canvas c, RecyclerView parent, State state) {
        boolean needsInvalidate = false;
        if (this.mGlow1 != null) {
            needsInvalidate = false | drawGlow(c, parent, this.mGlow1Dir, this.mGlow1);
        }
        if (this.mGlow2 != null) {
            needsInvalidate |= drawGlow(c, parent, this.mGlow2Dir, this.mGlow2);
        }
        if (needsInvalidate) {
            ViewCompat.postInvalidateOnAnimation(parent);
        }
    }

    private static boolean drawGlow(Canvas c, RecyclerView parent, int dir, EdgeEffectCompat edge) {
        if (edge.isFinished()) {
            return false;
        }
        int restore = c.save();
        boolean clipToPadding = getClipToPadding(parent);
        switch (dir) {
            case 0:
                c.rotate(-90.0f);
                if (!clipToPadding) {
                    c.translate((float) (-parent.getHeight()), 0.0f);
                    break;
                }
                c.translate((float) ((-parent.getHeight()) + parent.getPaddingTop()), (float) parent.getPaddingLeft());
                break;
            case 1:
                if (clipToPadding) {
                    c.translate((float) parent.getPaddingLeft(), (float) parent.getPaddingTop());
                    break;
                }
                break;
            case 2:
                c.rotate(90.0f);
                if (!clipToPadding) {
                    c.translate(0.0f, (float) (-parent.getWidth()));
                    break;
                }
                c.translate((float) parent.getPaddingTop(), (float) ((-parent.getWidth()) + parent.getPaddingRight()));
                break;
            case 3:
                c.rotate(180.0f);
                if (!clipToPadding) {
                    c.translate((float) (-parent.getWidth()), (float) (-parent.getHeight()));
                    break;
                }
                c.translate((float) ((-parent.getWidth()) + parent.getPaddingRight()), (float) ((-parent.getHeight()) + parent.getPaddingBottom()));
                break;
        }
        boolean needsInvalidate = edge.draw(c);
        c.restoreToCount(restore);
        return needsInvalidate;
    }

    public void start() {
        if (!this.mStarted) {
            this.mGlow1Dir = getEdgeDirection(0);
            this.mGlow2Dir = getEdgeDirection(1);
            this.mRecyclerView.addItemDecoration(this);
            this.mStarted = true;
        }
    }

    public void finish() {
        if (this.mStarted) {
            this.mRecyclerView.removeItemDecoration(this);
        }
        releaseBothGlows();
        this.mRecyclerView = null;
        this.mStarted = false;
    }

    public void pullFirstEdge(float deltaDistance) {
        ensureGlow1(this.mRecyclerView);
        if (this.mGlow1.onPull(deltaDistance, 0.5f)) {
            ViewCompat.postInvalidateOnAnimation(this.mRecyclerView);
        }
    }

    public void pullSecondEdge(float deltaDistance) {
        ensureGlow2(this.mRecyclerView);
        if (this.mGlow2.onPull(deltaDistance, 0.5f)) {
            ViewCompat.postInvalidateOnAnimation(this.mRecyclerView);
        }
    }

    public void releaseBothGlows() {
        boolean needsInvalidate = false;
        if (this.mGlow1 != null) {
            needsInvalidate = false | this.mGlow1.onRelease();
        }
        if (this.mGlow2 != null) {
            needsInvalidate |= this.mGlow2.onRelease();
        }
        if (needsInvalidate) {
            ViewCompat.postInvalidateOnAnimation(this.mRecyclerView);
        }
    }

    private void ensureGlow1(RecyclerView rv) {
        if (this.mGlow1 == null) {
            this.mGlow1 = new EdgeEffectCompat(rv.getContext());
        }
        updateGlowSize(rv, this.mGlow1, this.mGlow1Dir);
    }

    private void ensureGlow2(RecyclerView rv) {
        if (this.mGlow2 == null) {
            this.mGlow2 = new EdgeEffectCompat(rv.getContext());
        }
        updateGlowSize(rv, this.mGlow2, this.mGlow2Dir);
    }

    private static void updateGlowSize(RecyclerView rv, EdgeEffectCompat glow, int dir) {
        int width = rv.getMeasuredWidth();
        int height = rv.getMeasuredHeight();
        if (getClipToPadding(rv)) {
            width -= rv.getPaddingLeft() + rv.getPaddingRight();
            height -= rv.getPaddingTop() + rv.getPaddingBottom();
        }
        width = Math.max(0, width);
        height = Math.max(0, height);
        if (dir == 0 || dir == 2) {
            int t = width;
            width = height;
            height = t;
        }
        glow.setSize(width, height);
    }

    private static boolean getClipToPadding(RecyclerView rv) {
        return rv.getLayoutManager().getClipToPadding();
    }

    public void reorderToTop() {
        if (this.mStarted) {
            this.mRecyclerView.removeItemDecoration(this);
            this.mRecyclerView.addItemDecoration(this);
        }
    }
}
