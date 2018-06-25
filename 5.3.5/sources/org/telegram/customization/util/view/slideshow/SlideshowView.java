package org.telegram.customization.util.view.slideshow;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import org.ir.talaeii.R;
import org.telegram.messenger.C0906R;

public class SlideshowView extends FrameLayout {
    IconPageIndicator indicator;
    AutoScrollViewPager pager;
    View root;

    public static abstract class SlideshowAdapter extends PagerAdapter implements IconPagerAdapter {
    }

    public SlideshowView(Context paramContext) {
        super(paramContext);
        generateView();
        init();
    }

    public SlideshowView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        generateView();
        handleAttributes(paramContext, paramAttributeSet);
        init();
    }

    public SlideshowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        generateView();
        handleAttributes(context, attrs);
        init();
    }

    @TargetApi(21)
    public SlideshowView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        generateView();
        handleAttributes(context, attrs);
        init();
    }

    private void generateView() {
        this.root = inflate(getContext(), R.layout.slideshow, null);
        this.pager = (AutoScrollViewPager) this.root.findViewById(R.id.autoViewPager);
        this.indicator = (IconPageIndicator) this.root.findViewById(R.id.indicator);
        addView(this.root);
    }

    void handleAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, C0906R.styleable.SlideshowView);
        int N = a.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case 0:
                    this.pager.setInterval((long) a.getInteger(attr, 2000));
                    break;
                case 1:
                    this.pager.setDirection(a.getBoolean(attr, true) ? 0 : 1);
                    break;
                case 2:
                    if (!a.getBoolean(attr, true)) {
                        break;
                    }
                    this.pager.startAutoScroll();
                    break;
                case 3:
                    this.pager.setAutoScrollDurationFactor((double) a.getFloat(attr, 5.0f));
                    break;
                default:
                    break;
            }
        }
        a.recycle();
    }

    void init() {
    }

    public void setAdapter(SlideshowAdapter adapter) {
        this.pager.setAdapter(adapter);
        this.indicator.setViewPager(this.pager);
    }
}
