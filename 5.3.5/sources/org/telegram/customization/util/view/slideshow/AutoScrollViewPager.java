package org.telegram.customization.util.view.slideshow;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.Interpolator;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

public class AutoScrollViewPager extends ViewPager {
    public static final int DEFAULT_INTERVAL = 1500;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int SCROLL_WHAT = 0;
    public static final int SLIDE_BORDER_MODE_CYCLE = 1;
    public static final int SLIDE_BORDER_MODE_NONE = 0;
    public static final int SLIDE_BORDER_MODE_TO_PARENT = 2;
    public Activity activity;
    private double autoScrollFactor = 1.0d;
    private int direction = 1;
    private float downX = 0.0f;
    private float downY = 0.0f;
    private Handler handler;
    private float helpDownX = 0.0f;
    private long interval = 1500;
    private boolean isAutoScroll = false;
    private boolean isBorderAnimation = true;
    private boolean isCycle = true;
    private boolean isStopByTouch = false;
    private CustomDurationScroller scroller = null;
    private int slideBorderMode = 0;
    private boolean stopScrollWhenTouch = true;
    private double swipeScrollFactor = 1.0d;
    private float touchX = 0.0f;
    private float touchY = 0.0f;

    private static class MyHandler extends Handler {
        private final WeakReference<AutoScrollViewPager> autoScrollViewPager;

        public MyHandler(AutoScrollViewPager autoScrollViewPager) {
            this.autoScrollViewPager = new WeakReference(autoScrollViewPager);
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    AutoScrollViewPager pager = (AutoScrollViewPager) this.autoScrollViewPager.get();
                    if (pager != null) {
                        pager.scroller.setScrollDurationFactor(pager.autoScrollFactor);
                        pager.scrollOnce();
                        pager.scroller.setScrollDurationFactor(pager.swipeScrollFactor);
                        pager.sendScrollMessage(pager.interval + ((long) pager.scroller.getDuration()));
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public AutoScrollViewPager(Context paramContext) {
        super(paramContext);
        init();
    }

    public AutoScrollViewPager(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init();
    }

    private void init() {
        this.handler = new MyHandler(this);
        setViewPagerScroller();
    }

    public void startAutoScroll() {
        this.isAutoScroll = true;
        sendScrollMessage((long) (((double) this.interval) + ((((double) this.scroller.getDuration()) / this.autoScrollFactor) * this.swipeScrollFactor)));
    }

    public void startAutoScroll(int delayTimeInMills) {
        this.isAutoScroll = true;
        sendScrollMessage((long) delayTimeInMills);
    }

    public void stopAutoScroll() {
        this.isAutoScroll = false;
        this.handler.removeMessages(0);
    }

    public void setSwipeScrollDurationFactor(double scrollFactor) {
        this.swipeScrollFactor = scrollFactor;
    }

    public void setAutoScrollDurationFactor(double scrollFactor) {
        this.autoScrollFactor = scrollFactor;
    }

    private void sendScrollMessage(long delayTimeInMills) {
        this.handler.removeMessages(0);
        this.handler.sendEmptyMessageDelayed(0, delayTimeInMills);
    }

    private void setViewPagerScroller() {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            Field interpolatorField = ViewPager.class.getDeclaredField("sInterpolator");
            interpolatorField.setAccessible(true);
            this.scroller = new CustomDurationScroller(getContext(), (Interpolator) interpolatorField.get(null));
            scrollerField.set(this, this.scroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void scrollOnce() {
        PagerAdapter adapter = getAdapter();
        int nextItem = getCurrentItem();
        if (adapter != null) {
            int totalCount = adapter.getCount();
            if (totalCount > 1) {
                int currentItem;
                if (this.direction == 0) {
                    nextItem--;
                    currentItem = nextItem;
                } else {
                    nextItem++;
                    currentItem = nextItem;
                }
                if (nextItem < 0) {
                    if (this.isCycle) {
                        setCurrentItem(totalCount - 1, this.isBorderAnimation);
                    }
                } else if (nextItem != totalCount) {
                    setCurrentItem(nextItem, true);
                } else if (this.isCycle) {
                    setCurrentItem(0, this.isBorderAnimation);
                }
                nextItem = currentItem;
            }
        }
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = MotionEventCompat.getActionMasked(ev);
        this.touchY = ev.getY();
        if (this.stopScrollWhenTouch) {
            if (action == 0 && this.isAutoScroll) {
                this.isStopByTouch = true;
                stopAutoScroll();
            } else if (ev.getAction() == 1 && this.isStopByTouch && this.isAutoScroll) {
                startAutoScroll();
            }
        }
        if (ev.getAction() == 0) {
            this.downY = this.touchY;
            this.helpDownX = ev.getX();
        } else if (this.isStopByTouch && (ev.getAction() == 1 || (ev.getAction() == 3 && this.isAutoScroll))) {
            startAutoScroll();
        }
        if (Math.abs(this.downY - this.touchY) > Math.abs(this.helpDownX - ev.getX())) {
            getParent().requestDisallowInterceptTouchEvent(false);
            return super.dispatchTouchEvent(ev);
        }
        if (Math.abs(this.downY - this.touchY) > 5.0f && 5.0f < Math.abs(this.helpDownX - ev.getX())) {
            this.helpDownX = ev.getX();
            this.downY = this.touchY;
        }
        if (this.slideBorderMode == 2 || this.slideBorderMode == 1) {
            this.touchX = ev.getX();
            if (ev.getAction() == 0) {
                this.downX = this.touchX;
            }
            int currentItem = getCurrentItem();
            PagerAdapter adapter = getAdapter();
            int pageCount = adapter == null ? 0 : adapter.getCount();
            if ((currentItem == 0 && this.downX <= this.touchX) || (currentItem == pageCount - 1 && this.downX >= this.touchX)) {
                if (this.slideBorderMode == 2) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    if (pageCount > 1) {
                        setCurrentItem((pageCount - currentItem) - 1, this.isBorderAnimation);
                    }
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                return super.dispatchTouchEvent(ev);
            }
        }
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    public long getInterval() {
        return this.interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public int getDirection() {
        return this.direction == 0 ? 0 : 1;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isCycle() {
        return this.isCycle;
    }

    public void setCycle(boolean isCycle) {
        this.isCycle = isCycle;
    }

    public boolean isStopScrollWhenTouch() {
        return this.stopScrollWhenTouch;
    }

    public void setStopScrollWhenTouch(boolean stopScrollWhenTouch) {
        this.stopScrollWhenTouch = stopScrollWhenTouch;
    }

    public int getSlideBorderMode() {
        return this.slideBorderMode;
    }

    public void setSlideBorderMode(int slideBorderMode) {
        this.slideBorderMode = slideBorderMode;
    }

    public boolean isBorderAnimation() {
        return this.isBorderAnimation;
    }

    public void setBorderAnimation(boolean isBorderAnimation) {
        this.isBorderAnimation = isBorderAnimation;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, 0));
            int h = child.getMeasuredHeight();
            if (h > height) {
                height = h;
            }
        }
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, 1073741824));
    }

    public boolean canScrollVertically(int direction) {
        return false;
    }
}
