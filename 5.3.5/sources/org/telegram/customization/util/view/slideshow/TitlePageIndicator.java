package org.telegram.customization.util.view.slideshow;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.messenger.C0906R;

public class TitlePageIndicator extends View implements PageIndicator {
    private static final float BOLD_FADE_PERCENTAGE = 0.05f;
    private static final String EMPTY_TITLE = "";
    private static final int INVALID_POINTER = -1;
    private static final float SELECTION_FADE_PERCENTAGE = 0.25f;
    private int mActivePointerId;
    private boolean mBoldText;
    private final Rect mBounds;
    private OnCenterItemClickListener mCenterItemClickListener;
    private float mClipPadding;
    private int mColorSelected;
    private int mColorText;
    private int mCurrentPage;
    private float mFooterIndicatorHeight;
    private IndicatorStyle mFooterIndicatorStyle;
    private float mFooterIndicatorUnderlinePadding;
    private float mFooterLineHeight;
    private float mFooterPadding;
    private boolean mIsDragging;
    private float mLastMotionX;
    private LinePosition mLinePosition;
    private OnPageChangeListener mListener;
    private float mPageOffset;
    private final Paint mPaintFooterIndicator;
    private final Paint mPaintFooterLine;
    private final Paint mPaintText;
    private Path mPath;
    private int mScrollState;
    private float mTitlePadding;
    private float mTopPadding;
    private int mTouchSlop;
    private ViewPager mViewPager;

    public enum IndicatorStyle {
        None(0),
        Triangle(1),
        Underline(2);
        
        public final int value;

        private IndicatorStyle(int value) {
            this.value = value;
        }

        public static IndicatorStyle fromValue(int value) {
            for (IndicatorStyle style : values()) {
                if (style.value == value) {
                    return style;
                }
            }
            return null;
        }
    }

    public enum LinePosition {
        Bottom(0),
        Top(1);
        
        public final int value;

        private LinePosition(int value) {
            this.value = value;
        }

        public static LinePosition fromValue(int value) {
            for (LinePosition position : values()) {
                if (position.value == value) {
                    return position;
                }
            }
            return null;
        }
    }

    public interface OnCenterItemClickListener {
        void onCenterItemClick(int i);
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new C12761();
        int currentPage;

        /* renamed from: org.telegram.customization.util.view.slideshow.TitlePageIndicator$SavedState$1 */
        static class C12761 implements Creator<SavedState> {
            C12761() {
            }

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.currentPage = in.readInt();
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.currentPage);
        }
    }

    public TitlePageIndicator(Context context) {
        this(context, null);
    }

    public TitlePageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.vpiTitlePageIndicatorStyle);
    }

    public TitlePageIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mCurrentPage = -1;
        this.mPaintText = new Paint();
        this.mPath = new Path();
        this.mBounds = new Rect();
        this.mPaintFooterLine = new Paint();
        this.mPaintFooterIndicator = new Paint();
        this.mLastMotionX = -1.0f;
        this.mActivePointerId = -1;
        if (!isInEditMode()) {
            Resources res = getResources();
            int defaultFooterColor = res.getColor(R.color.default_title_indicator_footer_color);
            float defaultFooterLineHeight = res.getDimension(R.dimen.default_title_indicator_footer_line_height);
            int defaultFooterIndicatorStyle = res.getInteger(R.integer.default_title_indicator_footer_indicator_style);
            float defaultFooterIndicatorHeight = res.getDimension(R.dimen.default_title_indicator_footer_indicator_height);
            float defaultFooterIndicatorUnderlinePadding = res.getDimension(R.dimen.default_title_indicator_footer_indicator_underline_padding);
            float defaultFooterPadding = res.getDimension(R.dimen.default_title_indicator_footer_padding);
            int defaultLinePosition = res.getInteger(R.integer.default_title_indicator_line_position);
            int defaultSelectedColor = res.getColor(R.color.default_title_indicator_selected_color);
            boolean defaultSelectedBold = res.getBoolean(R.bool.default_title_indicator_selected_bold);
            int defaultTextColor = res.getColor(R.color.default_title_indicator_text_color);
            float defaultTextSize = res.getDimension(R.dimen.default_title_indicator_text_size);
            float defaultTitlePadding = res.getDimension(R.dimen.default_title_indicator_title_padding);
            float defaultClipPadding = res.getDimension(R.dimen.default_title_indicator_clip_padding);
            float defaultTopPadding = res.getDimension(R.dimen.default_title_indicator_top_padding);
            TypedArray a = context.obtainStyledAttributes(attrs, C0906R.styleable.TitlePageIndicator, defStyle, 0);
            this.mFooterLineHeight = a.getDimension(6, defaultFooterLineHeight);
            this.mFooterIndicatorStyle = IndicatorStyle.fromValue(a.getInteger(7, defaultFooterIndicatorStyle));
            this.mFooterIndicatorHeight = a.getDimension(8, defaultFooterIndicatorHeight);
            this.mFooterIndicatorUnderlinePadding = a.getDimension(9, defaultFooterIndicatorUnderlinePadding);
            this.mFooterPadding = a.getDimension(10, defaultFooterPadding);
            this.mLinePosition = LinePosition.fromValue(a.getInteger(11, defaultLinePosition));
            this.mTopPadding = a.getDimension(14, defaultTopPadding);
            this.mTitlePadding = a.getDimension(13, defaultTitlePadding);
            this.mClipPadding = a.getDimension(4, defaultClipPadding);
            this.mColorSelected = a.getColor(3, defaultSelectedColor);
            this.mColorText = a.getColor(1, defaultTextColor);
            this.mBoldText = a.getBoolean(12, defaultSelectedBold);
            float textSize = a.getDimension(0, defaultTextSize);
            int footerColor = a.getColor(5, defaultFooterColor);
            this.mPaintText.setTextSize(textSize);
            this.mPaintText.setAntiAlias(true);
            this.mPaintFooterLine.setStyle(Style.FILL_AND_STROKE);
            this.mPaintFooterLine.setStrokeWidth(this.mFooterLineHeight);
            this.mPaintFooterLine.setColor(footerColor);
            this.mPaintFooterIndicator.setStyle(Style.FILL_AND_STROKE);
            this.mPaintFooterIndicator.setColor(footerColor);
            Drawable background = a.getDrawable(2);
            if (background != null) {
                setBackgroundDrawable(background);
            }
            a.recycle();
            this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(context));
        }
    }

    public int getFooterColor() {
        return this.mPaintFooterLine.getColor();
    }

    public void setFooterColor(int footerColor) {
        this.mPaintFooterLine.setColor(footerColor);
        this.mPaintFooterIndicator.setColor(footerColor);
        invalidate();
    }

    public float getFooterLineHeight() {
        return this.mFooterLineHeight;
    }

    public void setFooterLineHeight(float footerLineHeight) {
        this.mFooterLineHeight = footerLineHeight;
        this.mPaintFooterLine.setStrokeWidth(this.mFooterLineHeight);
        invalidate();
    }

    public float getFooterIndicatorHeight() {
        return this.mFooterIndicatorHeight;
    }

    public void setFooterIndicatorHeight(float footerTriangleHeight) {
        this.mFooterIndicatorHeight = footerTriangleHeight;
        invalidate();
    }

    public float getFooterIndicatorPadding() {
        return this.mFooterPadding;
    }

    public void setFooterIndicatorPadding(float footerIndicatorPadding) {
        this.mFooterPadding = footerIndicatorPadding;
        invalidate();
    }

    public IndicatorStyle getFooterIndicatorStyle() {
        return this.mFooterIndicatorStyle;
    }

    public void setFooterIndicatorStyle(IndicatorStyle indicatorStyle) {
        this.mFooterIndicatorStyle = indicatorStyle;
        invalidate();
    }

    public LinePosition getLinePosition() {
        return this.mLinePosition;
    }

    public void setLinePosition(LinePosition linePosition) {
        this.mLinePosition = linePosition;
        invalidate();
    }

    public int getSelectedColor() {
        return this.mColorSelected;
    }

    public void setSelectedColor(int selectedColor) {
        this.mColorSelected = selectedColor;
        invalidate();
    }

    public boolean isSelectedBold() {
        return this.mBoldText;
    }

    public void setSelectedBold(boolean selectedBold) {
        this.mBoldText = selectedBold;
        invalidate();
    }

    public int getTextColor() {
        return this.mColorText;
    }

    public void setTextColor(int textColor) {
        this.mPaintText.setColor(textColor);
        this.mColorText = textColor;
        invalidate();
    }

    public float getTextSize() {
        return this.mPaintText.getTextSize();
    }

    public void setTextSize(float textSize) {
        this.mPaintText.setTextSize(textSize);
        invalidate();
    }

    public float getTitlePadding() {
        return this.mTitlePadding;
    }

    public void setTitlePadding(float titlePadding) {
        this.mTitlePadding = titlePadding;
        invalidate();
    }

    public float getTopPadding() {
        return this.mTopPadding;
    }

    public void setTopPadding(float topPadding) {
        this.mTopPadding = topPadding;
        invalidate();
    }

    public float getClipPadding() {
        return this.mClipPadding;
    }

    public void setClipPadding(float clipPadding) {
        this.mClipPadding = clipPadding;
        invalidate();
    }

    public void setTypeface(Typeface typeface) {
        this.mPaintText.setTypeface(typeface);
        invalidate();
    }

    public Typeface getTypeface() {
        return this.mPaintText.getTypeface();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mViewPager != null) {
            int count = this.mViewPager.getAdapter().getCount();
            if (count != 0) {
                if (this.mCurrentPage == -1 && this.mViewPager != null) {
                    this.mCurrentPage = this.mViewPager.getCurrentItem();
                }
                ArrayList<Rect> bounds = calculateAllBounds(this.mPaintText);
                int boundsSize = bounds.size();
                if (this.mCurrentPage >= boundsSize) {
                    setCurrentItem(boundsSize - 1);
                    return;
                }
                float offsetPercent;
                int i;
                Rect bound;
                int w;
                Rect rightBound;
                int countMinusOne = count - 1;
                float halfWidth = ((float) getWidth()) / 2.0f;
                int left = getLeft();
                float leftClip = ((float) left) + this.mClipPadding;
                int width = getWidth();
                int height = getHeight();
                int right = left + width;
                float rightClip = ((float) right) - this.mClipPadding;
                int page = this.mCurrentPage;
                if (((double) this.mPageOffset) <= 0.5d) {
                    offsetPercent = this.mPageOffset;
                } else {
                    page++;
                    offsetPercent = 1.0f - this.mPageOffset;
                }
                boolean currentSelected = offsetPercent <= SELECTION_FADE_PERCENTAGE;
                boolean currentBold = offsetPercent <= BOLD_FADE_PERCENTAGE;
                float selectedPercent = (SELECTION_FADE_PERCENTAGE - offsetPercent) / SELECTION_FADE_PERCENTAGE;
                Rect curPageBound = (Rect) bounds.get(this.mCurrentPage);
                float curPageWidth = (float) (curPageBound.right - curPageBound.left);
                if (((float) curPageBound.left) < leftClip) {
                    clipViewOnTheLeft(curPageBound, curPageWidth, left);
                }
                if (((float) curPageBound.right) > rightClip) {
                    clipViewOnTheRight(curPageBound, curPageWidth, right);
                }
                if (this.mCurrentPage > 0) {
                    for (i = this.mCurrentPage - 1; i >= 0; i--) {
                        bound = (Rect) bounds.get(i);
                        if (((float) bound.left) < leftClip) {
                            w = bound.right - bound.left;
                            clipViewOnTheLeft(bound, (float) w, left);
                            rightBound = (Rect) bounds.get(i + 1);
                            if (((float) bound.right) + this.mTitlePadding > ((float) rightBound.left)) {
                                bound.left = (int) (((float) (rightBound.left - w)) - this.mTitlePadding);
                                bound.right = bound.left + w;
                            }
                        }
                    }
                }
                if (this.mCurrentPage < countMinusOne) {
                    for (i = this.mCurrentPage + 1; i < count; i++) {
                        bound = (Rect) bounds.get(i);
                        if (((float) bound.right) > rightClip) {
                            w = bound.right - bound.left;
                            clipViewOnTheRight(bound, (float) w, right);
                            Rect leftBound = (Rect) bounds.get(i - 1);
                            if (((float) bound.left) - this.mTitlePadding < ((float) leftBound.right)) {
                                bound.left = (int) (((float) leftBound.right) + this.mTitlePadding);
                                bound.right = bound.left + w;
                            }
                        }
                    }
                }
                int colorTextAlpha = this.mColorText >>> 24;
                i = 0;
                while (i < count) {
                    bound = (Rect) bounds.get(i);
                    if ((bound.left > left && bound.left < right) || (bound.right > left && bound.right < right)) {
                        boolean currentPage = i == page;
                        CharSequence pageTitle = getTitle(i);
                        Paint paint = this.mPaintText;
                        boolean z = currentPage && currentBold && this.mBoldText;
                        paint.setFakeBoldText(z);
                        this.mPaintText.setColor(this.mColorText);
                        if (currentPage && currentSelected) {
                            this.mPaintText.setAlpha(colorTextAlpha - ((int) (((float) colorTextAlpha) * selectedPercent)));
                        }
                        if (i < boundsSize - 1) {
                            rightBound = (Rect) bounds.get(i + 1);
                            if (((float) bound.right) + this.mTitlePadding > ((float) rightBound.left)) {
                                w = bound.right - bound.left;
                                bound.left = (int) (((float) (rightBound.left - w)) - this.mTitlePadding);
                                bound.right = bound.left + w;
                            }
                        }
                        canvas.drawText(pageTitle, 0, pageTitle.length(), (float) bound.left, this.mTopPadding + ((float) bound.bottom), this.mPaintText);
                        if (currentPage && currentSelected) {
                            this.mPaintText.setColor(this.mColorSelected);
                            this.mPaintText.setAlpha((int) (((float) (this.mColorSelected >>> 24)) * selectedPercent));
                            canvas.drawText(pageTitle, 0, pageTitle.length(), (float) bound.left, this.mTopPadding + ((float) bound.bottom), this.mPaintText);
                        }
                    }
                    i++;
                }
                float footerLineHeight = this.mFooterLineHeight;
                float footerIndicatorLineHeight = this.mFooterIndicatorHeight;
                if (this.mLinePosition == LinePosition.Top) {
                    height = 0;
                    footerLineHeight = -footerLineHeight;
                    footerIndicatorLineHeight = -footerIndicatorLineHeight;
                }
                this.mPath.reset();
                this.mPath.moveTo(0.0f, ((float) height) - (footerLineHeight / 2.0f));
                this.mPath.lineTo((float) width, ((float) height) - (footerLineHeight / 2.0f));
                this.mPath.close();
                canvas.drawPath(this.mPath, this.mPaintFooterLine);
                float heightMinusLine = ((float) height) - footerLineHeight;
                switch (this.mFooterIndicatorStyle) {
                    case Triangle:
                        this.mPath.reset();
                        this.mPath.moveTo(halfWidth, heightMinusLine - footerIndicatorLineHeight);
                        this.mPath.lineTo(halfWidth + footerIndicatorLineHeight, heightMinusLine);
                        this.mPath.lineTo(halfWidth - footerIndicatorLineHeight, heightMinusLine);
                        this.mPath.close();
                        canvas.drawPath(this.mPath, this.mPaintFooterIndicator);
                        return;
                    case Underline:
                        if (currentSelected && page < boundsSize) {
                            Rect underlineBounds = (Rect) bounds.get(page);
                            float rightPlusPadding = ((float) underlineBounds.right) + this.mFooterIndicatorUnderlinePadding;
                            float leftMinusPadding = ((float) underlineBounds.left) - this.mFooterIndicatorUnderlinePadding;
                            float heightMinusLineMinusIndicator = heightMinusLine - footerIndicatorLineHeight;
                            this.mPath.reset();
                            this.mPath.moveTo(leftMinusPadding, heightMinusLine);
                            this.mPath.lineTo(rightPlusPadding, heightMinusLine);
                            this.mPath.lineTo(rightPlusPadding, heightMinusLineMinusIndicator);
                            this.mPath.lineTo(leftMinusPadding, heightMinusLineMinusIndicator);
                            this.mPath.close();
                            this.mPaintFooterIndicator.setAlpha((int) (255.0f * selectedPercent));
                            canvas.drawPath(this.mPath, this.mPaintFooterIndicator);
                            this.mPaintFooterIndicator.setAlpha(255);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (super.onTouchEvent(ev)) {
            return true;
        }
        if (this.mViewPager == null || this.mViewPager.getAdapter().getCount() == 0) {
            return false;
        }
        int action = ev.getAction() & 255;
        switch (action) {
            case 0:
                this.mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                this.mLastMotionX = ev.getX();
                break;
            case 1:
            case 3:
                if (!this.mIsDragging) {
                    int count = this.mViewPager.getAdapter().getCount();
                    int width = getWidth();
                    float halfWidth = ((float) width) / 2.0f;
                    float sixthWidth = ((float) width) / 6.0f;
                    float leftThird = halfWidth - sixthWidth;
                    float rightThird = halfWidth + sixthWidth;
                    float eventX = ev.getX();
                    if (eventX < leftThird) {
                        if (this.mCurrentPage > 0) {
                            if (action != 3) {
                                this.mViewPager.setCurrentItem(this.mCurrentPage - 1);
                            }
                            return true;
                        }
                    } else if (eventX > rightThird) {
                        if (this.mCurrentPage < count - 1) {
                            if (action != 3) {
                                this.mViewPager.setCurrentItem(this.mCurrentPage + 1);
                            }
                            return true;
                        }
                    } else if (!(this.mCenterItemClickListener == null || action == 3)) {
                        this.mCenterItemClickListener.onCenterItemClick(this.mCurrentPage);
                    }
                }
                this.mIsDragging = false;
                this.mActivePointerId = -1;
                if (this.mViewPager.isFakeDragging()) {
                    this.mViewPager.endFakeDrag();
                    break;
                }
                break;
            case 2:
                float x = MotionEventCompat.getX(ev, MotionEventCompat.findPointerIndex(ev, this.mActivePointerId));
                float deltaX = x - this.mLastMotionX;
                if (!this.mIsDragging && Math.abs(deltaX) > ((float) this.mTouchSlop)) {
                    this.mIsDragging = true;
                }
                if (this.mIsDragging) {
                    this.mLastMotionX = x;
                    if (this.mViewPager.isFakeDragging() || this.mViewPager.beginFakeDrag()) {
                        this.mViewPager.fakeDragBy(deltaX);
                        break;
                    }
                }
                break;
            case 5:
                int index = MotionEventCompat.getActionIndex(ev);
                this.mLastMotionX = MotionEventCompat.getX(ev, index);
                this.mActivePointerId = MotionEventCompat.getPointerId(ev, index);
                break;
            case 6:
                int pointerIndex = MotionEventCompat.getActionIndex(ev);
                if (MotionEventCompat.getPointerId(ev, pointerIndex) == this.mActivePointerId) {
                    this.mActivePointerId = MotionEventCompat.getPointerId(ev, pointerIndex == 0 ? 1 : 0);
                }
                this.mLastMotionX = MotionEventCompat.getX(ev, MotionEventCompat.findPointerIndex(ev, this.mActivePointerId));
                break;
        }
        return true;
    }

    private void clipViewOnTheRight(Rect curViewBound, float curViewWidth, int right) {
        curViewBound.right = (int) (((float) right) - this.mClipPadding);
        curViewBound.left = (int) (((float) curViewBound.right) - curViewWidth);
    }

    private void clipViewOnTheLeft(Rect curViewBound, float curViewWidth, int left) {
        curViewBound.left = (int) (((float) left) + this.mClipPadding);
        curViewBound.right = (int) (this.mClipPadding + curViewWidth);
    }

    private ArrayList<Rect> calculateAllBounds(Paint paint) {
        ArrayList<Rect> list = new ArrayList();
        int count = this.mViewPager.getAdapter().getCount();
        int width = getWidth();
        int halfWidth = width / 2;
        for (int i = 0; i < count; i++) {
            Rect bounds = calcBounds(i, paint);
            int w = bounds.right - bounds.left;
            int h = bounds.bottom - bounds.top;
            bounds.left = (int) ((((float) halfWidth) - (((float) w) / 2.0f)) + ((((float) (i - this.mCurrentPage)) - this.mPageOffset) * ((float) width)));
            bounds.right = bounds.left + w;
            bounds.top = 0;
            bounds.bottom = h;
            list.add(bounds);
        }
        return list;
    }

    private Rect calcBounds(int index, Paint paint) {
        Rect bounds = new Rect();
        CharSequence title = getTitle(index);
        bounds.right = (int) paint.measureText(title, 0, title.length());
        bounds.bottom = (int) (paint.descent() - paint.ascent());
        return bounds;
    }

    public void setViewPager(ViewPager view) {
        if (this.mViewPager != view) {
            if (this.mViewPager != null) {
                this.mViewPager.setOnPageChangeListener(null);
            }
            if (view.getAdapter() == null) {
                throw new IllegalStateException("ViewPager does not have adapter instance.");
            }
            this.mViewPager = view;
            this.mViewPager.setOnPageChangeListener(this);
            invalidate();
        }
    }

    public void setViewPager(ViewPager view, int initialPosition) {
        setViewPager(view);
        setCurrentItem(initialPosition);
    }

    public void notifyDataSetChanged() {
        invalidate();
    }

    public void setOnCenterItemClickListener(OnCenterItemClickListener listener) {
        this.mCenterItemClickListener = listener;
    }

    public void setCurrentItem(int item) {
        if (this.mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        this.mViewPager.setCurrentItem(item);
        this.mCurrentPage = item;
        invalidate();
    }

    public void onPageScrollStateChanged(int state) {
        this.mScrollState = state;
        if (this.mListener != null) {
            this.mListener.onPageScrollStateChanged(state);
        }
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        this.mCurrentPage = position;
        this.mPageOffset = positionOffset;
        invalidate();
        if (this.mListener != null) {
            this.mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    public void onPageSelected(int position) {
        if (this.mScrollState == 0) {
            this.mCurrentPage = position;
            invalidate();
        }
        if (this.mListener != null) {
            this.mListener.onPageSelected(position);
        }
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.mListener = listener;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float height;
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        if (MeasureSpec.getMode(heightMeasureSpec) == 1073741824) {
            height = (float) MeasureSpec.getSize(heightMeasureSpec);
        } else {
            this.mBounds.setEmpty();
            this.mBounds.bottom = (int) (this.mPaintText.descent() - this.mPaintText.ascent());
            height = ((((float) (this.mBounds.bottom - this.mBounds.top)) + this.mFooterLineHeight) + this.mFooterPadding) + this.mTopPadding;
            if (this.mFooterIndicatorStyle != IndicatorStyle.None) {
                height += this.mFooterIndicatorHeight;
            }
        }
        setMeasuredDimension(measuredWidth, (int) height);
    }

    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mCurrentPage = savedState.currentPage;
        requestLayout();
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.currentPage = this.mCurrentPage;
        return savedState;
    }

    private CharSequence getTitle(int i) {
        CharSequence title = this.mViewPager.getAdapter().getPageTitle(i);
        if (title == null) {
            return "";
        }
        return title;
    }
}
