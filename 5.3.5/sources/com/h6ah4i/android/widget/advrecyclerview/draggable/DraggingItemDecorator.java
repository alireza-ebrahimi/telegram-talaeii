package com.h6ah4i.android.widget.advrecyclerview.draggable;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.Interpolator;
import com.h6ah4i.android.widget.advrecyclerview.utils.CustomRecyclerViewUtils;

class DraggingItemDecorator extends BaseDraggableItemDecorator {
    private static final String TAG = "DraggingItemDecorator";
    private Interpolator mAlphaInterpolator = null;
    private Bitmap mDraggingItemImage;
    private DraggingItemInfo mDraggingItemInfo;
    private float mInitialDraggingItemScaleX;
    private float mInitialDraggingItemScaleY;
    private boolean mIsScrolling;
    private float mLastDraggingItemAlpha;
    private float mLastDraggingItemRotation;
    private float mLastDraggingItemScaleX;
    private float mLastDraggingItemScaleY;
    private int mLayoutOrientation;
    private Paint mPaint;
    private ItemDraggableRange mRange;
    private Interpolator mRotationInterpolator = null;
    private Interpolator mScaleInterpolator = null;
    private NinePatchDrawable mShadowDrawable;
    private final Rect mShadowPadding = new Rect();
    private long mStartAnimationDurationMillis = 0;
    private long mStartMillis;
    private boolean mStarted;
    private float mTargetDraggingItemAlpha = 1.0f;
    private float mTargetDraggingItemRotation = 0.0f;
    private float mTargetDraggingItemScale = 1.0f;
    private int mTouchPositionX;
    private int mTouchPositionY;
    private int mTranslationBottomLimit;
    private int mTranslationLeftLimit;
    private int mTranslationRightLimit;
    private int mTranslationTopLimit;
    private int mTranslationX;
    private int mTranslationY;

    public DraggingItemDecorator(RecyclerView recyclerView, ViewHolder draggingItem, ItemDraggableRange range) {
        super(recyclerView, draggingItem);
        this.mRange = range;
        this.mPaint = new Paint();
    }

    private static int clip(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }

    private static View findRangeFirstItem(RecyclerView rv, ItemDraggableRange range, int firstVisiblePosition, int lastVisiblePosition) {
        if (firstVisiblePosition == -1 || lastVisiblePosition == -1) {
            return null;
        }
        int childCount = rv.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v2 = rv.getChildAt(i);
            ViewHolder vh = rv.getChildViewHolder(v2);
            if (vh != null) {
                int position = vh.getLayoutPosition();
                if (position >= firstVisiblePosition && position <= lastVisiblePosition && range.checkInRange(position)) {
                    return v2;
                }
            }
        }
        return null;
    }

    private static View findRangeLastItem(RecyclerView rv, ItemDraggableRange range, int firstVisiblePosition, int lastVisiblePosition) {
        if (firstVisiblePosition == -1 || lastVisiblePosition == -1) {
            return null;
        }
        for (int i = rv.getChildCount() - 1; i >= 0; i--) {
            View v2 = rv.getChildAt(i);
            ViewHolder vh = rv.getChildViewHolder(v2);
            if (vh != null) {
                int position = vh.getLayoutPosition();
                if (position >= firstVisiblePosition && position <= lastVisiblePosition && range.checkInRange(position)) {
                    return v2;
                }
            }
        }
        return null;
    }

    public void onDrawOver(Canvas c, RecyclerView parent, State state) {
        if (this.mDraggingItemImage != null) {
            float t = this.mStartAnimationDurationMillis > 0 ? ((float) ((int) Math.min(System.currentTimeMillis() - this.mStartMillis, this.mStartAnimationDurationMillis))) / ((float) this.mStartAnimationDurationMillis) : 1.0f;
            float tScale = getInterpolation(this.mScaleInterpolator, t);
            float scaleX = ((this.mTargetDraggingItemScale - this.mInitialDraggingItemScaleX) * tScale) + this.mInitialDraggingItemScaleX;
            float scaleY = ((this.mTargetDraggingItemScale - this.mInitialDraggingItemScaleY) * tScale) + this.mInitialDraggingItemScaleY;
            float alpha = (getInterpolation(this.mAlphaInterpolator, t) * (this.mTargetDraggingItemAlpha - 1.0f)) + 1.0f;
            float rotation = getInterpolation(this.mRotationInterpolator, t) * this.mTargetDraggingItemRotation;
            if (scaleX > 0.0f && scaleY > 0.0f && alpha > 0.0f) {
                int w = this.mDraggingItemImage.getWidth();
                int h = this.mDraggingItemImage.getHeight();
                int iw = (w - this.mShadowPadding.left) - this.mShadowPadding.right;
                int ih = (h - this.mShadowPadding.top) - this.mShadowPadding.bottom;
                this.mPaint.setAlpha((int) (255.0f * alpha));
                int savedCount = c.save(1);
                c.scale(scaleX, scaleY);
                c.translate((((float) this.mTranslationX) + (0.5f * ((float) iw))) / scaleX, (((float) this.mTranslationY) + (0.5f * ((float) ih))) / scaleY);
                c.rotate(rotation);
                c.translate(-(0.5f * ((float) w)), -(0.5f * ((float) h)));
                c.drawBitmap(this.mDraggingItemImage, 0.0f, 0.0f, this.mPaint);
                c.restoreToCount(savedCount);
            }
            if (t < 1.0f) {
                ViewCompat.postInvalidateOnAnimation(this.mRecyclerView);
            }
            this.mLastDraggingItemScaleX = scaleX;
            this.mLastDraggingItemScaleY = scaleY;
            this.mLastDraggingItemRotation = rotation;
            this.mLastDraggingItemAlpha = alpha;
        }
    }

    public void setupDraggingItemEffects(DraggingItemEffectsInfo info) {
        this.mStartAnimationDurationMillis = (long) info.durationMillis;
        this.mTargetDraggingItemScale = info.scale;
        this.mScaleInterpolator = info.scaleInterpolator;
        this.mTargetDraggingItemRotation = info.rotation;
        this.mRotationInterpolator = info.rotationInterpolator;
        this.mTargetDraggingItemAlpha = info.alpha;
        this.mAlphaInterpolator = info.alphaInterpolator;
    }

    public void start(DraggingItemInfo draggingItemInfo, int touchX, int touchY) {
        if (!this.mStarted) {
            View itemView = this.mDraggingItemViewHolder.itemView;
            this.mDraggingItemInfo = draggingItemInfo;
            this.mDraggingItemImage = createDraggingItemImage(itemView, this.mShadowDrawable);
            this.mTranslationLeftLimit = this.mRecyclerView.getPaddingLeft();
            this.mTranslationTopLimit = this.mRecyclerView.getPaddingTop();
            this.mLayoutOrientation = CustomRecyclerViewUtils.getOrientation(this.mRecyclerView);
            this.mInitialDraggingItemScaleX = ViewCompat.getScaleX(itemView);
            this.mInitialDraggingItemScaleY = ViewCompat.getScaleX(itemView);
            this.mLastDraggingItemScaleX = 1.0f;
            this.mLastDraggingItemScaleY = 1.0f;
            this.mLastDraggingItemRotation = 0.0f;
            this.mLastDraggingItemAlpha = 1.0f;
            itemView.setVisibility(4);
            update(touchX, touchY, true);
            this.mRecyclerView.addItemDecoration(this);
            this.mStartMillis = System.currentTimeMillis();
            this.mStarted = true;
        }
    }

    public void updateDraggingItemView(DraggingItemInfo info, ViewHolder vh) {
        if (this.mStarted) {
            if (this.mDraggingItemViewHolder != vh) {
                invalidateDraggingItem();
                this.mDraggingItemViewHolder = vh;
            }
            this.mDraggingItemImage = createDraggingItemImage(vh.itemView, this.mShadowDrawable);
            this.mDraggingItemInfo = info;
            refresh(true);
        }
    }

    public void finish(boolean animate) {
        if (this.mStarted) {
            this.mRecyclerView.removeItemDecoration(this);
        }
        ItemAnimator itemAnimator = this.mRecyclerView.getItemAnimator();
        if (itemAnimator != null) {
            itemAnimator.endAnimations();
        }
        this.mRecyclerView.stopScroll();
        updateDraggingItemPosition((float) this.mTranslationX, this.mTranslationY);
        if (this.mDraggingItemViewHolder != null) {
            moveToDefaultPosition(this.mDraggingItemViewHolder.itemView, this.mLastDraggingItemScaleX, this.mLastDraggingItemScaleY, this.mLastDraggingItemRotation, this.mLastDraggingItemAlpha, animate);
        }
        if (this.mDraggingItemViewHolder != null) {
            this.mDraggingItemViewHolder.itemView.setVisibility(0);
        }
        this.mDraggingItemViewHolder = null;
        if (this.mDraggingItemImage != null) {
            this.mDraggingItemImage.recycle();
            this.mDraggingItemImage = null;
        }
        this.mRange = null;
        this.mTranslationX = 0;
        this.mTranslationY = 0;
        this.mTranslationLeftLimit = 0;
        this.mTranslationRightLimit = 0;
        this.mTranslationTopLimit = 0;
        this.mTranslationBottomLimit = 0;
        this.mTouchPositionX = 0;
        this.mTouchPositionY = 0;
        this.mStarted = false;
    }

    public boolean update(int touchX, int touchY, boolean force) {
        this.mTouchPositionX = touchX;
        this.mTouchPositionY = touchY;
        return refresh(force);
    }

    public boolean refresh(boolean force) {
        int prevTranslationX = this.mTranslationX;
        int prevTranslationY = this.mTranslationY;
        updateTranslationOffset();
        boolean updated = (prevTranslationX == this.mTranslationX && prevTranslationY == this.mTranslationY) ? false : true;
        if (updated || force) {
            updateDraggingItemPosition((float) this.mTranslationX, this.mTranslationY);
            ViewCompat.postInvalidateOnAnimation(this.mRecyclerView);
        }
        return updated;
    }

    public void setShadowDrawable(NinePatchDrawable shadowDrawable) {
        this.mShadowDrawable = shadowDrawable;
        if (this.mShadowDrawable != null) {
            this.mShadowDrawable.getPadding(this.mShadowPadding);
        }
    }

    public int getDraggingItemTranslationY() {
        return this.mTranslationY;
    }

    public int getDraggingItemTranslationX() {
        return this.mTranslationX;
    }

    public int getDraggingItemMoveOffsetY() {
        return this.mTranslationY - this.mDraggingItemInfo.initialItemTop;
    }

    public int getDraggingItemMoveOffsetX() {
        return this.mTranslationX - this.mDraggingItemInfo.initialItemLeft;
    }

    private void updateTranslationOffset() {
        RecyclerView rv = this.mRecyclerView;
        if (rv.getChildCount() > 0) {
            this.mTranslationLeftLimit = 0;
            this.mTranslationRightLimit = rv.getWidth() - this.mDraggingItemInfo.width;
            this.mTranslationTopLimit = 0;
            this.mTranslationBottomLimit = rv.getHeight() - this.mDraggingItemInfo.height;
            switch (this.mLayoutOrientation) {
                case 0:
                    this.mTranslationTopLimit += rv.getPaddingTop();
                    this.mTranslationBottomLimit -= rv.getPaddingBottom();
                    this.mTranslationLeftLimit = -this.mDraggingItemInfo.width;
                    this.mTranslationRightLimit = rv.getWidth();
                    break;
                case 1:
                    this.mTranslationTopLimit = -this.mDraggingItemInfo.height;
                    this.mTranslationBottomLimit = rv.getHeight();
                    this.mTranslationLeftLimit += rv.getPaddingLeft();
                    this.mTranslationRightLimit -= rv.getPaddingRight();
                    break;
            }
            this.mTranslationRightLimit = Math.max(this.mTranslationLeftLimit, this.mTranslationRightLimit);
            this.mTranslationBottomLimit = Math.max(this.mTranslationTopLimit, this.mTranslationBottomLimit);
            if (!this.mIsScrolling) {
                int firstVisiblePosition = CustomRecyclerViewUtils.findFirstVisibleItemPosition(rv, true);
                int lastVisiblePosition = CustomRecyclerViewUtils.findLastVisibleItemPosition(rv, true);
                View firstChild = findRangeFirstItem(rv, this.mRange, firstVisiblePosition, lastVisiblePosition);
                View lastChild = findRangeLastItem(rv, this.mRange, firstVisiblePosition, lastVisiblePosition);
                switch (this.mLayoutOrientation) {
                    case 0:
                        if (firstChild != null) {
                            this.mTranslationLeftLimit = Math.min(this.mTranslationLeftLimit, firstChild.getLeft());
                        }
                        if (lastChild != null) {
                            this.mTranslationRightLimit = Math.min(this.mTranslationRightLimit, Math.max(0, lastChild.getRight() - this.mDraggingItemInfo.width));
                            break;
                        }
                        break;
                    case 1:
                        if (firstChild != null) {
                            this.mTranslationTopLimit = Math.min(this.mTranslationBottomLimit, firstChild.getTop());
                        }
                        if (lastChild != null) {
                            this.mTranslationBottomLimit = Math.min(this.mTranslationBottomLimit, Math.max(0, lastChild.getBottom() - this.mDraggingItemInfo.height));
                            break;
                        }
                        break;
                }
            }
        }
        int paddingLeft = rv.getPaddingLeft();
        this.mTranslationLeftLimit = paddingLeft;
        this.mTranslationRightLimit = paddingLeft;
        paddingLeft = rv.getPaddingTop();
        this.mTranslationTopLimit = paddingLeft;
        this.mTranslationBottomLimit = paddingLeft;
        this.mTranslationX = this.mTouchPositionX - this.mDraggingItemInfo.grabbedPositionX;
        this.mTranslationY = this.mTouchPositionY - this.mDraggingItemInfo.grabbedPositionY;
        this.mTranslationX = clip(this.mTranslationX, this.mTranslationLeftLimit, this.mTranslationRightLimit);
        this.mTranslationY = clip(this.mTranslationY, this.mTranslationTopLimit, this.mTranslationBottomLimit);
    }

    private static int toSpanAlignedPosition(int position, int spanCount) {
        if (position == -1) {
            return -1;
        }
        return (position / spanCount) * spanCount;
    }

    public boolean isReachedToTopLimit() {
        return this.mTranslationY == this.mTranslationTopLimit;
    }

    public boolean isReachedToBottomLimit() {
        return this.mTranslationY == this.mTranslationBottomLimit;
    }

    public boolean isReachedToLeftLimit() {
        return this.mTranslationX == this.mTranslationLeftLimit;
    }

    public boolean isReachedToRightLimit() {
        return this.mTranslationX == this.mTranslationRightLimit;
    }

    private Bitmap createDraggingItemImage(View v, NinePatchDrawable shadow) {
        int viewTop = v.getTop();
        int viewLeft = v.getLeft();
        int viewWidth = v.getWidth();
        int viewHeight = v.getHeight();
        int canvasWidth = (this.mShadowPadding.left + viewWidth) + this.mShadowPadding.right;
        int canvasHeight = (this.mShadowPadding.top + viewHeight) + this.mShadowPadding.bottom;
        v.measure(MeasureSpec.makeMeasureSpec(viewWidth, 1073741824), MeasureSpec.makeMeasureSpec(viewHeight, 1073741824));
        v.layout(viewLeft, viewTop, viewLeft + viewWidth, viewTop + viewHeight);
        Bitmap bitmap = Bitmap.createBitmap(canvasWidth, canvasHeight, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        if (shadow != null) {
            shadow.setBounds(0, 0, canvasWidth, canvasHeight);
            shadow.draw(canvas);
        }
        int savedCount = canvas.save(3);
        canvas.clipRect(this.mShadowPadding.left, this.mShadowPadding.top, canvasWidth - this.mShadowPadding.right, canvasHeight - this.mShadowPadding.bottom);
        canvas.translate((float) this.mShadowPadding.left, (float) this.mShadowPadding.top);
        v.draw(canvas);
        canvas.restoreToCount(savedCount);
        return bitmap;
    }

    private void updateDraggingItemPosition(float translationX, int translationY) {
        if (this.mDraggingItemViewHolder != null) {
            BaseDraggableItemDecorator.setItemTranslation(this.mRecyclerView, this.mDraggingItemViewHolder, translationX - ((float) this.mDraggingItemViewHolder.itemView.getLeft()), (float) (translationY - this.mDraggingItemViewHolder.itemView.getTop()));
        }
    }

    public void setIsScrolling(boolean isScrolling) {
        if (this.mIsScrolling != isScrolling) {
            this.mIsScrolling = isScrolling;
        }
    }

    public int getTranslatedItemPositionTop() {
        return this.mTranslationY;
    }

    public int getTranslatedItemPositionBottom() {
        return this.mTranslationY + this.mDraggingItemInfo.height;
    }

    public int getTranslatedItemPositionLeft() {
        return this.mTranslationX;
    }

    public int getTranslatedItemPositionRight() {
        return this.mTranslationX + this.mDraggingItemInfo.width;
    }

    public void invalidateDraggingItem() {
        if (this.mDraggingItemViewHolder != null) {
            ViewCompat.setTranslationX(this.mDraggingItemViewHolder.itemView, 0.0f);
            ViewCompat.setTranslationY(this.mDraggingItemViewHolder.itemView, 0.0f);
            this.mDraggingItemViewHolder.itemView.setVisibility(0);
        }
        this.mDraggingItemViewHolder = null;
    }

    public void setDraggingItemViewHolder(ViewHolder holder) {
        if (this.mDraggingItemViewHolder != null) {
            throw new IllegalStateException("A new view holder is attempt to be assigned before invalidating the older one");
        }
        this.mDraggingItemViewHolder = holder;
        holder.itemView.setVisibility(4);
    }

    private static float getInterpolation(Interpolator interpolator, float input) {
        return interpolator != null ? interpolator.getInterpolation(input) : input;
    }
}
