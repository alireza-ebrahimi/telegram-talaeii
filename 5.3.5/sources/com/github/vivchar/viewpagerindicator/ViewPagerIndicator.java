package com.github.vivchar.viewpagerindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import java.util.ArrayList;
import java.util.List;

public class ViewPagerIndicator extends LinearLayoutCompat {
    private static final int DEF_ICON = C0487R.drawable.white_circle;
    private static final int DEF_VALUE = 10;
    private static final float NO_SCALE = 1.0f;
    private int mDelimiterSize;
    @NonNull
    private final List<ImageView> mIndexImages;
    private int mItemColor;
    private int mItemIcon;
    private float mItemScale;
    private int mItemSelectedColor;
    private int mItemSize;
    @Nullable
    private android.support.v4.view.ViewPager.OnPageChangeListener mListener;
    private int mPageCount;
    private int mSelectedIndex;

    private class OnPageChangeListener implements android.support.v4.view.ViewPager.OnPageChangeListener {
        private OnPageChangeListener() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (ViewPagerIndicator.this.mListener != null) {
                ViewPagerIndicator.this.mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        public void onPageSelected(int position) {
            ViewPagerIndicator.this.setSelectedIndex(position);
            if (ViewPagerIndicator.this.mListener != null) {
                ViewPagerIndicator.this.mListener.onPageSelected(position);
            }
        }

        public void onPageScrollStateChanged(int state) {
            if (ViewPagerIndicator.this.mListener != null) {
                ViewPagerIndicator.this.mListener.onPageScrollStateChanged(state);
            }
        }
    }

    public ViewPagerIndicator(@NonNull Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerIndicator(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mItemColor = -1;
        this.mItemSelectedColor = -1;
        this.mItemScale = 1.0f;
        this.mItemSize = 10;
        this.mDelimiterSize = 10;
        this.mItemIcon = 10;
        this.mIndexImages = new ArrayList();
        setOrientation(0);
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, C0487R.styleable.ViewPagerIndicator, 0, 0);
        try {
            this.mItemSize = attributes.getDimensionPixelSize(C0487R.styleable.ViewPagerIndicator_itemSize, 10);
            this.mItemScale = attributes.getFloat(C0487R.styleable.ViewPagerIndicator_itemScale, 1.0f);
            this.mItemSelectedColor = attributes.getColor(C0487R.styleable.ViewPagerIndicator_itemSelectedTint, -1);
            this.mItemColor = attributes.getColor(C0487R.styleable.ViewPagerIndicator_itemTint, -1);
            this.mDelimiterSize = attributes.getDimensionPixelSize(C0487R.styleable.ViewPagerIndicator_delimiterSize, 10);
            this.mItemIcon = attributes.getResourceId(C0487R.styleable.ViewPagerIndicator_itemIcon, DEF_ICON);
            if (isInEditMode()) {
                createEditModeLayout();
            }
        } finally {
            attributes.recycle();
        }
    }

    private void createEditModeLayout() {
        for (int i = 0; i < 5; i++) {
            FrameLayout boxedItem = createBoxedItem(i);
            addView(boxedItem);
            if (i == 1) {
                View item = boxedItem.getChildAt(0);
                LayoutParams layoutParams = item.getLayoutParams();
                layoutParams.height = (int) (((float) layoutParams.height) * this.mItemScale);
                layoutParams.width = (int) (((float) layoutParams.width) * this.mItemScale);
                item.setLayoutParams(layoutParams);
            }
        }
    }

    public void setupWithViewPager(@NonNull ViewPager viewPager) {
        setPageCount(viewPager.getAdapter().getCount());
        viewPager.addOnPageChangeListener(new OnPageChangeListener());
    }

    public void addOnPageChangeListener(android.support.v4.view.ViewPager.OnPageChangeListener listener) {
        this.mListener = listener;
    }

    private void setSelectedIndex(int selectedIndex) {
        if (selectedIndex >= 0 && selectedIndex <= this.mPageCount - 1) {
            ImageView unselectedView = (ImageView) this.mIndexImages.get(this.mSelectedIndex);
            unselectedView.animate().scaleX(1.0f).scaleY(1.0f).setDuration(300).start();
            unselectedView.setColorFilter(this.mItemColor, Mode.SRC_IN);
            ImageView selectedView = (ImageView) this.mIndexImages.get(selectedIndex);
            selectedView.animate().scaleX(this.mItemScale).scaleY(this.mItemScale).setDuration(300).start();
            selectedView.setColorFilter(this.mItemSelectedColor, Mode.SRC_IN);
            this.mSelectedIndex = selectedIndex;
        }
    }

    private void setPageCount(int pageCount) {
        this.mPageCount = pageCount;
        this.mSelectedIndex = 0;
        removeAllViews();
        this.mIndexImages.clear();
        for (int i = 0; i < pageCount; i++) {
            addView(createBoxedItem(i));
        }
        setSelectedIndex(this.mSelectedIndex);
    }

    @NonNull
    private FrameLayout createBoxedItem(int position) {
        FrameLayout box = new FrameLayout(getContext());
        ImageView item = createItem();
        box.addView(item);
        this.mIndexImages.add(item);
        LinearLayoutCompat.LayoutParams boxParams = new LinearLayoutCompat.LayoutParams((int) (((float) this.mItemSize) * this.mItemScale), (int) (((float) this.mItemSize) * this.mItemScale));
        if (position > 0) {
            boxParams.setMargins(this.mDelimiterSize, 0, 0, 0);
        }
        box.setLayoutParams(boxParams);
        return box;
    }

    @NonNull
    private ImageView createItem() {
        ImageView index = new ImageView(getContext());
        FrameLayout.LayoutParams indexParams = new FrameLayout.LayoutParams(this.mItemSize, this.mItemSize);
        indexParams.gravity = 17;
        index.setLayoutParams(indexParams);
        index.setImageResource(this.mItemIcon);
        index.setScaleType(ScaleType.FIT_CENTER);
        index.setColorFilter(this.mItemColor, Mode.SRC_IN);
        return index;
    }
}
