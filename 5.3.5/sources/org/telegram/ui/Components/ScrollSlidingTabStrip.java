package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$Document;

public class ScrollSlidingTabStrip extends HorizontalScrollView {
    private int currentPosition;
    private LayoutParams defaultTabLayoutParams;
    private ScrollSlidingTabStripDelegate delegate;
    private int dividerPadding = AndroidUtilities.dp(12.0f);
    private int indicatorColor = -10066330;
    private int indicatorHeight;
    private int lastScrollX = 0;
    private Paint rectPaint;
    private int scrollOffset = AndroidUtilities.dp(52.0f);
    private int tabCount;
    private int tabPadding = AndroidUtilities.dp(24.0f);
    private LinearLayout tabsContainer;
    private int underlineColor = 436207616;
    private int underlineHeight = AndroidUtilities.dp(2.0f);

    public interface ScrollSlidingTabStripDelegate {
        void onPageSelected(int i);
    }

    public ScrollSlidingTabStrip(Context context) {
        super(context);
        setFillViewport(true);
        setWillNotDraw(false);
        setHorizontalScrollBarEnabled(false);
        this.tabsContainer = new LinearLayout(context);
        this.tabsContainer.setOrientation(0);
        this.tabsContainer.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        addView(this.tabsContainer);
        this.rectPaint = new Paint();
        this.rectPaint.setAntiAlias(true);
        this.rectPaint.setStyle(Style.FILL);
        this.defaultTabLayoutParams = new LayoutParams(AndroidUtilities.dp(52.0f), -1);
    }

    public void setDelegate(ScrollSlidingTabStripDelegate scrollSlidingTabStripDelegate) {
        this.delegate = scrollSlidingTabStripDelegate;
    }

    public void removeTabs() {
        this.tabsContainer.removeAllViews();
        this.tabCount = 0;
        this.currentPosition = 0;
    }

    public void selectTab(int num) {
        if (num >= 0 && num < this.tabCount) {
            this.tabsContainer.getChildAt(num).performClick();
        }
    }

    public TextView addIconTabWithCounter(Drawable drawable) {
        final int position = this.tabCount;
        this.tabCount = position + 1;
        FrameLayout tab = new FrameLayout(getContext());
        tab.setFocusable(true);
        this.tabsContainer.addView(tab);
        ImageView imageView = new ImageView(getContext());
        imageView.setImageDrawable(drawable);
        imageView.setScaleType(ScaleType.CENTER);
        tab.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ScrollSlidingTabStrip.this.delegate.onPageSelected(position);
            }
        });
        tab.addView(imageView, LayoutHelper.createFrame(-1, -1.0f));
        tab.setSelected(position == this.currentPosition);
        TextView textView = new TextView(getContext());
        textView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        textView.setTextSize(1, 12.0f);
        textView.setTextColor(-1);
        textView.setGravity(17);
        textView.setBackgroundResource(R.drawable.sticker_badge);
        textView.setMinWidth(AndroidUtilities.dp(18.0f));
        textView.setPadding(AndroidUtilities.dp(5.0f), 0, AndroidUtilities.dp(5.0f), AndroidUtilities.dp(1.0f));
        tab.addView(textView, LayoutHelper.createFrame(-2, 18.0f, 51, 26.0f, 6.0f, 0.0f, 0.0f));
        return textView;
    }

    public void addIconTab(Drawable drawable) {
        boolean z = true;
        final int position = this.tabCount;
        this.tabCount = position + 1;
        ImageView tab = new ImageView(getContext());
        tab.setFocusable(true);
        tab.setImageDrawable(drawable);
        tab.setScaleType(ScaleType.CENTER);
        tab.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ScrollSlidingTabStrip.this.delegate.onPageSelected(position);
            }
        });
        this.tabsContainer.addView(tab);
        if (position != this.currentPosition) {
            z = false;
        }
        tab.setSelected(z);
    }

    public void addStickerTab(TLRPC$Chat chat) {
        final int position = this.tabCount;
        this.tabCount = position + 1;
        FrameLayout tab = new FrameLayout(getContext());
        tab.setFocusable(true);
        tab.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ScrollSlidingTabStrip.this.delegate.onPageSelected(position);
            }
        });
        this.tabsContainer.addView(tab);
        tab.setSelected(position == this.currentPosition);
        BackupImageView imageView = new BackupImageView(getContext());
        imageView.setRoundRadius(AndroidUtilities.dp(15.0f));
        TLObject photo = null;
        Drawable avatarDrawable = new AvatarDrawable();
        if (chat.photo != null) {
            photo = chat.photo.photo_small;
        }
        avatarDrawable.setTextSize(AndroidUtilities.dp(14.0f));
        avatarDrawable.setInfo(chat);
        imageView.setImage(photo, "50_50", avatarDrawable);
        imageView.setAspectFit(true);
        tab.addView(imageView, LayoutHelper.createFrame(30, 30, 17));
    }

    public void addStickerTab(TLRPC$Document sticker) {
        final int position = this.tabCount;
        this.tabCount = position + 1;
        FrameLayout tab = new FrameLayout(getContext());
        tab.setTag(sticker);
        tab.setFocusable(true);
        tab.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ScrollSlidingTabStrip.this.delegate.onPageSelected(position);
            }
        });
        this.tabsContainer.addView(tab);
        tab.setSelected(position == this.currentPosition);
        BackupImageView imageView = new BackupImageView(getContext());
        imageView.setAspectFit(true);
        tab.addView(imageView, LayoutHelper.createFrame(30, 30, 17));
    }

    public void updateTabStyles() {
        for (int i = 0; i < this.tabCount; i++) {
            this.tabsContainer.getChildAt(i).setLayoutParams(this.defaultTabLayoutParams);
        }
    }

    private void scrollToChild(int position) {
        if (this.tabCount != 0 && this.tabsContainer.getChildAt(position) != null) {
            int newScrollX = this.tabsContainer.getChildAt(position).getLeft();
            if (position > 0) {
                newScrollX -= this.scrollOffset;
            }
            int currentScrollX = getScrollX();
            if (newScrollX == this.lastScrollX) {
                return;
            }
            if (newScrollX < currentScrollX) {
                this.lastScrollX = newScrollX;
                smoothScrollTo(this.lastScrollX, 0);
            } else if (this.scrollOffset + newScrollX > (getWidth() + currentScrollX) - (this.scrollOffset * 2)) {
                this.lastScrollX = (newScrollX - getWidth()) + (this.scrollOffset * 3);
                smoothScrollTo(this.lastScrollX, 0);
            }
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        setImages();
    }

    public void setImages() {
        int tabSize = AndroidUtilities.dp(52.0f);
        int start = getScrollX() / tabSize;
        int end = Math.min(this.tabsContainer.getChildCount(), (((int) Math.ceil((double) (((float) getMeasuredWidth()) / ((float) tabSize)))) + start) + 1);
        for (int a = start; a < end; a++) {
            View child = this.tabsContainer.getChildAt(a);
            TLRPC$Document object = child.getTag();
            if (object instanceof TLRPC$Document) {
                ((BackupImageView) ((FrameLayout) child).getChildAt(0)).setImage(object.thumb.location, null, "webp", null);
            }
        }
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        int tabSize = AndroidUtilities.dp(52.0f);
        int oldStart = oldl / tabSize;
        int newStart = l / tabSize;
        int count = ((int) Math.ceil((double) (((float) getMeasuredWidth()) / ((float) tabSize)))) + 1;
        int start = Math.max(0, Math.min(oldStart, newStart));
        int end = Math.min(this.tabsContainer.getChildCount(), Math.max(oldStart, newStart) + count);
        int a = start;
        while (a < end) {
            View child = this.tabsContainer.getChildAt(a);
            if (child != null) {
                TLRPC$Document object = child.getTag();
                if (object instanceof TLRPC$Document) {
                    BackupImageView imageView = (BackupImageView) ((FrameLayout) child).getChildAt(0);
                    if (a < newStart || a >= newStart + count) {
                        imageView.setImageDrawable(null);
                    } else {
                        imageView.setImage(object.thumb.location, null, "webp", null);
                    }
                }
            }
            a++;
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInEditMode() && this.tabCount != 0) {
            int height = getHeight();
            this.rectPaint.setColor(this.underlineColor);
            canvas.drawRect(0.0f, (float) (height - this.underlineHeight), (float) this.tabsContainer.getWidth(), (float) height, this.rectPaint);
            View currentTab = this.tabsContainer.getChildAt(this.currentPosition);
            float lineLeft = 0.0f;
            float lineRight = 0.0f;
            if (currentTab != null) {
                lineLeft = (float) currentTab.getLeft();
                lineRight = (float) currentTab.getRight();
            }
            this.rectPaint.setColor(this.indicatorColor);
            if (this.indicatorHeight == 0) {
                canvas.drawRect(lineLeft, 0.0f, lineRight, (float) height, this.rectPaint);
                return;
            }
            canvas.drawRect(lineLeft, (float) (height - this.indicatorHeight), lineRight, (float) height, this.rectPaint);
        }
    }

    public int getCurrentPosition() {
        return this.currentPosition;
    }

    public void onPageScrolled(int position, int first) {
        if (this.currentPosition != position) {
            this.currentPosition = position;
            if (position < this.tabsContainer.getChildCount()) {
                int a = 0;
                while (a < this.tabsContainer.getChildCount()) {
                    this.tabsContainer.getChildAt(a).setSelected(a == position);
                    a++;
                }
                if (first != position || position <= 1) {
                    scrollToChild(position);
                } else {
                    scrollToChild(position - 1);
                }
                invalidate();
            }
        }
    }

    public void setIndicatorHeight(int value) {
        this.indicatorHeight = value;
        invalidate();
    }

    public void setIndicatorColor(int value) {
        this.indicatorColor = value;
        invalidate();
    }

    public void setUnderlineColor(int value) {
        this.underlineColor = value;
        invalidate();
    }

    public void setUnderlineColorResource(int resId) {
        this.underlineColor = getResources().getColor(resId);
        invalidate();
    }

    public void setUnderlineHeight(int value) {
        this.underlineHeight = value;
        invalidate();
    }
}
