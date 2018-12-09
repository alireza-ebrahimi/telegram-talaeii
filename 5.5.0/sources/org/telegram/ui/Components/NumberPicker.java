package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.Locale;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.ui.ActionBar.Theme;

public class NumberPicker extends LinearLayout {
    private static final int DEFAULT_LAYOUT_RESOURCE_ID = 0;
    private static final long DEFAULT_LONG_PRESS_UPDATE_INTERVAL = 300;
    private static final int SELECTOR_ADJUSTMENT_DURATION_MILLIS = 800;
    private static final int SELECTOR_MAX_FLING_VELOCITY_ADJUSTMENT = 8;
    private static final int SELECTOR_MIDDLE_ITEM_INDEX = 1;
    private static final int SELECTOR_WHEEL_ITEM_COUNT = 3;
    private static final int SIZE_UNSPECIFIED = -1;
    private static final int SNAP_SCROLL_DURATION = 300;
    private static final float TOP_AND_BOTTOM_FADING_EDGE_STRENGTH = 0.9f;
    private static final int UNSCALED_DEFAULT_SELECTION_DIVIDERS_DISTANCE = 48;
    private static final int UNSCALED_DEFAULT_SELECTION_DIVIDER_HEIGHT = 2;
    private Scroller mAdjustScroller;
    private int mBottomSelectionDividerBottom;
    private ChangeCurrentByOneFromLongPressCommand mChangeCurrentByOneFromLongPressCommand;
    private boolean mComputeMaxWidth;
    private int mCurrentScrollOffset;
    private boolean mDecrementVirtualButtonPressed;
    private String[] mDisplayedValues;
    private Scroller mFlingScroller;
    private Formatter mFormatter;
    private boolean mIncrementVirtualButtonPressed;
    private boolean mIngonreMoveEvents;
    private int mInitialScrollOffset;
    private TextView mInputText;
    private long mLastDownEventTime;
    private float mLastDownEventY;
    private float mLastDownOrMoveEventY;
    private int mLastHandledDownDpadKeyCode;
    private int mLastHoveredChildVirtualViewId;
    private long mLongPressUpdateInterval;
    private int mMaxHeight;
    private int mMaxValue;
    private int mMaxWidth;
    private int mMaximumFlingVelocity;
    private int mMinHeight;
    private int mMinValue;
    private int mMinWidth;
    private int mMinimumFlingVelocity;
    private OnScrollListener mOnScrollListener;
    private OnValueChangeListener mOnValueChangeListener;
    private PressedStateHelper mPressedStateHelper;
    private int mPreviousScrollerY;
    private int mScrollState;
    private Paint mSelectionDivider;
    private int mSelectionDividerHeight;
    private int mSelectionDividersDistance;
    private int mSelectorElementHeight;
    private final SparseArray<String> mSelectorIndexToStringCache;
    private final int[] mSelectorIndices;
    private int mSelectorTextGapHeight;
    private Paint mSelectorWheelPaint;
    private int mSolidColor;
    private int mTextSize;
    private int mTopSelectionDividerTop;
    private int mTouchSlop;
    private int mValue;
    private VelocityTracker mVelocityTracker;
    private boolean mWrapSelectorWheel;

    public interface Formatter {
        String format(int i);
    }

    class ChangeCurrentByOneFromLongPressCommand implements Runnable {
        private boolean mIncrement;

        ChangeCurrentByOneFromLongPressCommand() {
        }

        private void setStep(boolean z) {
            this.mIncrement = z;
        }

        public void run() {
            NumberPicker.this.changeValueByOne(this.mIncrement);
            NumberPicker.this.postDelayed(this, NumberPicker.this.mLongPressUpdateInterval);
        }
    }

    public interface OnScrollListener {
        public static final int SCROLL_STATE_FLING = 2;
        public static final int SCROLL_STATE_IDLE = 0;
        public static final int SCROLL_STATE_TOUCH_SCROLL = 1;

        void onScrollStateChange(NumberPicker numberPicker, int i);
    }

    public interface OnValueChangeListener {
        void onValueChange(NumberPicker numberPicker, int i, int i2);
    }

    class PressedStateHelper implements Runnable {
        public static final int BUTTON_DECREMENT = 2;
        public static final int BUTTON_INCREMENT = 1;
        private final int MODE_PRESS = 1;
        private final int MODE_TAPPED = 2;
        private int mManagedButton;
        private int mMode;

        PressedStateHelper() {
        }

        public void buttonPressDelayed(int i) {
            cancel();
            this.mMode = 1;
            this.mManagedButton = i;
            NumberPicker.this.postDelayed(this, (long) ViewConfiguration.getTapTimeout());
        }

        public void buttonTapped(int i) {
            cancel();
            this.mMode = 2;
            this.mManagedButton = i;
            NumberPicker.this.post(this);
        }

        public void cancel() {
            this.mMode = 0;
            this.mManagedButton = 0;
            NumberPicker.this.removeCallbacks(this);
            if (NumberPicker.this.mIncrementVirtualButtonPressed) {
                NumberPicker.this.mIncrementVirtualButtonPressed = false;
                NumberPicker.this.invalidate(0, NumberPicker.this.mBottomSelectionDividerBottom, NumberPicker.this.getRight(), NumberPicker.this.getBottom());
            }
            NumberPicker.this.mDecrementVirtualButtonPressed = false;
            if (NumberPicker.this.mDecrementVirtualButtonPressed) {
                NumberPicker.this.invalidate(0, 0, NumberPicker.this.getRight(), NumberPicker.this.mTopSelectionDividerTop);
            }
        }

        public void run() {
            switch (this.mMode) {
                case 1:
                    switch (this.mManagedButton) {
                        case 1:
                            NumberPicker.this.mIncrementVirtualButtonPressed = true;
                            NumberPicker.this.invalidate(0, NumberPicker.this.mBottomSelectionDividerBottom, NumberPicker.this.getRight(), NumberPicker.this.getBottom());
                            return;
                        case 2:
                            NumberPicker.this.mDecrementVirtualButtonPressed = true;
                            NumberPicker.this.invalidate(0, 0, NumberPicker.this.getRight(), NumberPicker.this.mTopSelectionDividerTop);
                            return;
                        default:
                            return;
                    }
                case 2:
                    switch (this.mManagedButton) {
                        case 1:
                            if (!NumberPicker.this.mIncrementVirtualButtonPressed) {
                                NumberPicker.this.postDelayed(this, (long) ViewConfiguration.getPressedStateDuration());
                            }
                            NumberPicker.this.mIncrementVirtualButtonPressed = NumberPicker.this.mIncrementVirtualButtonPressed ^ 1;
                            NumberPicker.this.invalidate(0, NumberPicker.this.mBottomSelectionDividerBottom, NumberPicker.this.getRight(), NumberPicker.this.getBottom());
                            return;
                        case 2:
                            if (!NumberPicker.this.mDecrementVirtualButtonPressed) {
                                NumberPicker.this.postDelayed(this, (long) ViewConfiguration.getPressedStateDuration());
                            }
                            NumberPicker.this.mDecrementVirtualButtonPressed = NumberPicker.this.mDecrementVirtualButtonPressed ^ 1;
                            NumberPicker.this.invalidate(0, 0, NumberPicker.this.getRight(), NumberPicker.this.mTopSelectionDividerTop);
                            return;
                        default:
                            return;
                    }
                default:
                    return;
            }
        }
    }

    public NumberPicker(Context context) {
        super(context);
        this.mLongPressUpdateInterval = DEFAULT_LONG_PRESS_UPDATE_INTERVAL;
        this.mSelectorIndexToStringCache = new SparseArray();
        this.mSelectorIndices = new int[3];
        this.mInitialScrollOffset = Integer.MIN_VALUE;
        this.mScrollState = 0;
        this.mLastHandledDownDpadKeyCode = -1;
        init();
    }

    public NumberPicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLongPressUpdateInterval = DEFAULT_LONG_PRESS_UPDATE_INTERVAL;
        this.mSelectorIndexToStringCache = new SparseArray();
        this.mSelectorIndices = new int[3];
        this.mInitialScrollOffset = Integer.MIN_VALUE;
        this.mScrollState = 0;
        this.mLastHandledDownDpadKeyCode = -1;
        init();
    }

    public NumberPicker(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mLongPressUpdateInterval = DEFAULT_LONG_PRESS_UPDATE_INTERVAL;
        this.mSelectorIndexToStringCache = new SparseArray();
        this.mSelectorIndices = new int[3];
        this.mInitialScrollOffset = Integer.MIN_VALUE;
        this.mScrollState = 0;
        this.mLastHandledDownDpadKeyCode = -1;
        init();
    }

    private void changeValueByOne(boolean z) {
        this.mInputText.setVisibility(4);
        if (!moveToFinalScrollerPosition(this.mFlingScroller)) {
            moveToFinalScrollerPosition(this.mAdjustScroller);
        }
        this.mPreviousScrollerY = 0;
        if (z) {
            this.mFlingScroller.startScroll(0, 0, 0, -this.mSelectorElementHeight, SNAP_SCROLL_DURATION);
        } else {
            this.mFlingScroller.startScroll(0, 0, 0, this.mSelectorElementHeight, SNAP_SCROLL_DURATION);
        }
        invalidate();
    }

    private void decrementSelectorIndices(int[] iArr) {
        System.arraycopy(iArr, 0, iArr, 1, iArr.length - 1);
        int i = iArr[1] - 1;
        if (this.mWrapSelectorWheel && i < this.mMinValue) {
            i = this.mMaxValue;
        }
        iArr[0] = i;
        ensureCachedScrollSelectorValue(i);
    }

    private void ensureCachedScrollSelectorValue(int i) {
        SparseArray sparseArray = this.mSelectorIndexToStringCache;
        if (((String) sparseArray.get(i)) == null) {
            Object obj;
            if (i < this.mMinValue || i > this.mMaxValue) {
                obj = TtmlNode.ANONYMOUS_REGION_ID;
            } else if (this.mDisplayedValues != null) {
                obj = this.mDisplayedValues[i - this.mMinValue];
            } else {
                obj = formatNumber(i);
            }
            sparseArray.put(i, obj);
        }
    }

    private boolean ensureScrollWheelAdjusted() {
        int i = this.mInitialScrollOffset - this.mCurrentScrollOffset;
        if (i == 0) {
            return false;
        }
        this.mPreviousScrollerY = 0;
        if (Math.abs(i) > this.mSelectorElementHeight / 2) {
            i += i > 0 ? -this.mSelectorElementHeight : this.mSelectorElementHeight;
        }
        this.mAdjustScroller.startScroll(0, 0, 0, i, SELECTOR_ADJUSTMENT_DURATION_MILLIS);
        invalidate();
        return true;
    }

    private void fling(int i) {
        this.mPreviousScrollerY = 0;
        if (i > 0) {
            this.mFlingScroller.fling(0, 0, 0, i, 0, 0, 0, Integer.MAX_VALUE);
        } else {
            this.mFlingScroller.fling(0, Integer.MAX_VALUE, 0, i, 0, 0, 0, Integer.MAX_VALUE);
        }
        invalidate();
    }

    private String formatNumber(int i) {
        return this.mFormatter != null ? this.mFormatter.format(i) : formatNumberWithLocale(i);
    }

    private static String formatNumberWithLocale(int i) {
        return String.format(Locale.getDefault(), "%d", new Object[]{Integer.valueOf(i)});
    }

    private int getSelectedPos(String str) {
        if (this.mDisplayedValues == null) {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException e) {
                return this.mMinValue;
            }
        }
        for (int i = 0; i < this.mDisplayedValues.length; i++) {
            str = str.toLowerCase();
            if (this.mDisplayedValues[i].toLowerCase().startsWith(str)) {
                return i + this.mMinValue;
            }
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e2) {
            return this.mMinValue;
        }
    }

    private int getWrappedSelectorIndex(int i) {
        return i > this.mMaxValue ? (this.mMinValue + ((i - this.mMaxValue) % (this.mMaxValue - this.mMinValue))) - 1 : i < this.mMinValue ? (this.mMaxValue - ((this.mMinValue - i) % (this.mMaxValue - this.mMinValue))) + 1 : i;
    }

    private void incrementSelectorIndices(int[] iArr) {
        System.arraycopy(iArr, 1, iArr, 0, iArr.length - 1);
        int i = iArr[iArr.length - 2] + 1;
        if (this.mWrapSelectorWheel && i > this.mMaxValue) {
            i = this.mMinValue;
        }
        iArr[iArr.length - 1] = i;
        ensureCachedScrollSelectorValue(i);
    }

    private void init() {
        this.mSolidColor = 0;
        this.mSelectionDivider = new Paint();
        this.mSelectionDivider.setColor(Theme.getColor(Theme.key_dialogButton));
        this.mSelectionDividerHeight = (int) TypedValue.applyDimension(1, 2.0f, getResources().getDisplayMetrics());
        this.mSelectionDividersDistance = (int) TypedValue.applyDimension(1, 48.0f, getResources().getDisplayMetrics());
        this.mMinHeight = -1;
        this.mMaxHeight = (int) TypedValue.applyDimension(1, 180.0f, getResources().getDisplayMetrics());
        if (this.mMinHeight == -1 || this.mMaxHeight == -1 || this.mMinHeight <= this.mMaxHeight) {
            this.mMinWidth = (int) TypedValue.applyDimension(1, 64.0f, getResources().getDisplayMetrics());
            this.mMaxWidth = -1;
            if (this.mMinWidth == -1 || this.mMaxWidth == -1 || this.mMinWidth <= this.mMaxWidth) {
                this.mComputeMaxWidth = this.mMaxWidth == -1;
                this.mPressedStateHelper = new PressedStateHelper();
                setWillNotDraw(false);
                this.mInputText = new TextView(getContext());
                this.mInputText.setGravity(17);
                this.mInputText.setSingleLine(true);
                this.mInputText.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
                this.mInputText.setBackgroundResource(0);
                this.mInputText.setTextSize(1, 18.0f);
                addView(this.mInputText, new LayoutParams(-1, -2));
                ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
                this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
                this.mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
                this.mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity() / 8;
                this.mTextSize = (int) this.mInputText.getTextSize();
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setTextAlign(Align.CENTER);
                paint.setTextSize((float) this.mTextSize);
                paint.setTypeface(this.mInputText.getTypeface());
                paint.setColor(this.mInputText.getTextColors().getColorForState(ENABLED_STATE_SET, -1));
                this.mSelectorWheelPaint = paint;
                this.mFlingScroller = new Scroller(getContext(), null, true);
                this.mAdjustScroller = new Scroller(getContext(), new DecelerateInterpolator(2.5f));
                updateInputTextView();
                return;
            }
            throw new IllegalArgumentException("minWidth > maxWidth");
        }
        throw new IllegalArgumentException("minHeight > maxHeight");
    }

    private void initializeFadingEdges() {
        setVerticalFadingEdgeEnabled(true);
        setFadingEdgeLength(((getBottom() - getTop()) - this.mTextSize) / 2);
    }

    private void initializeSelectorWheel() {
        initializeSelectorWheelIndices();
        int[] iArr = this.mSelectorIndices;
        this.mSelectorTextGapHeight = (int) ((((float) ((getBottom() - getTop()) - (iArr.length * this.mTextSize))) / ((float) iArr.length)) + 0.5f);
        this.mSelectorElementHeight = this.mTextSize + this.mSelectorTextGapHeight;
        this.mInitialScrollOffset = (this.mInputText.getBaseline() + this.mInputText.getTop()) - (this.mSelectorElementHeight * 1);
        this.mCurrentScrollOffset = this.mInitialScrollOffset;
        updateInputTextView();
    }

    private void initializeSelectorWheelIndices() {
        this.mSelectorIndexToStringCache.clear();
        int[] iArr = this.mSelectorIndices;
        int value = getValue();
        for (int i = 0; i < this.mSelectorIndices.length; i++) {
            int i2 = (i - 1) + value;
            if (this.mWrapSelectorWheel) {
                i2 = getWrappedSelectorIndex(i2);
            }
            iArr[i] = i2;
            ensureCachedScrollSelectorValue(iArr[i]);
        }
    }

    private int makeMeasureSpec(int i, int i2) {
        if (i2 == -1) {
            return i;
        }
        int size = MeasureSpec.getSize(i);
        int mode = MeasureSpec.getMode(i);
        switch (mode) {
            case Integer.MIN_VALUE:
                return MeasureSpec.makeMeasureSpec(Math.min(size, i2), 1073741824);
            case 0:
                return MeasureSpec.makeMeasureSpec(i2, 1073741824);
            case 1073741824:
                return i;
            default:
                throw new IllegalArgumentException("Unknown measure mode: " + mode);
        }
    }

    private boolean moveToFinalScrollerPosition(Scroller scroller) {
        scroller.forceFinished(true);
        int finalY = scroller.getFinalY() - scroller.getCurrY();
        int i = this.mInitialScrollOffset - ((this.mCurrentScrollOffset + finalY) % this.mSelectorElementHeight);
        if (i == 0) {
            return false;
        }
        if (Math.abs(i) > this.mSelectorElementHeight / 2) {
            i = i > 0 ? i - this.mSelectorElementHeight : i + this.mSelectorElementHeight;
        }
        scrollBy(0, i + finalY);
        return true;
    }

    private void notifyChange(int i, int i2) {
        if (this.mOnValueChangeListener != null) {
            this.mOnValueChangeListener.onValueChange(this, i, this.mValue);
        }
    }

    private void onScrollStateChange(int i) {
        if (this.mScrollState != i) {
            this.mScrollState = i;
            if (this.mOnScrollListener != null) {
                this.mOnScrollListener.onScrollStateChange(this, i);
            }
        }
    }

    private void onScrollerFinished(Scroller scroller) {
        if (scroller == this.mFlingScroller) {
            if (!ensureScrollWheelAdjusted()) {
                updateInputTextView();
            }
            onScrollStateChange(0);
        } else if (this.mScrollState != 1) {
            updateInputTextView();
        }
    }

    private void postChangeCurrentByOneFromLongPress(boolean z, long j) {
        if (this.mChangeCurrentByOneFromLongPressCommand == null) {
            this.mChangeCurrentByOneFromLongPressCommand = new ChangeCurrentByOneFromLongPressCommand();
        } else {
            removeCallbacks(this.mChangeCurrentByOneFromLongPressCommand);
        }
        this.mChangeCurrentByOneFromLongPressCommand.setStep(z);
        postDelayed(this.mChangeCurrentByOneFromLongPressCommand, j);
    }

    private void removeAllCallbacks() {
        if (this.mChangeCurrentByOneFromLongPressCommand != null) {
            removeCallbacks(this.mChangeCurrentByOneFromLongPressCommand);
        }
        this.mPressedStateHelper.cancel();
    }

    private void removeChangeCurrentByOneFromLongPress() {
        if (this.mChangeCurrentByOneFromLongPressCommand != null) {
            removeCallbacks(this.mChangeCurrentByOneFromLongPressCommand);
        }
    }

    public static int resolveSizeAndState(int i, int i2, int i3) {
        int mode = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i2);
        switch (mode) {
            case Integer.MIN_VALUE:
                if (size < i) {
                    i = size | 16777216;
                    break;
                }
                break;
            case 1073741824:
                i = size;
                break;
        }
        return (Theme.ACTION_BAR_VIDEO_EDIT_COLOR & i3) | i;
    }

    private int resolveSizeAndStateRespectingMinSize(int i, int i2, int i3) {
        return i != -1 ? resolveSizeAndState(Math.max(i, i2), i3, 0) : i2;
    }

    private void setValueInternal(int i, boolean z) {
        if (this.mValue != i) {
            int wrappedSelectorIndex = this.mWrapSelectorWheel ? getWrappedSelectorIndex(i) : Math.min(Math.max(i, this.mMinValue), this.mMaxValue);
            int i2 = this.mValue;
            this.mValue = wrappedSelectorIndex;
            updateInputTextView();
            if (z) {
                notifyChange(i2, wrappedSelectorIndex);
            }
            initializeSelectorWheelIndices();
            invalidate();
        }
    }

    private void tryComputeMaxWidth() {
        int i = 0;
        if (this.mComputeMaxWidth) {
            int i2;
            if (this.mDisplayedValues == null) {
                float f = BitmapDescriptorFactory.HUE_RED;
                int i3 = 0;
                while (i3 <= 9) {
                    float measureText = this.mSelectorWheelPaint.measureText(formatNumberWithLocale(i3));
                    if (measureText <= f) {
                        measureText = f;
                    }
                    i3++;
                    f = measureText;
                }
                for (i2 = this.mMaxValue; i2 > 0; i2 /= 10) {
                    i++;
                }
                i2 = (int) (((float) i) * f);
            } else {
                i2 = 0;
                for (String measureText2 : this.mDisplayedValues) {
                    float measureText3 = this.mSelectorWheelPaint.measureText(measureText2);
                    if (measureText3 > ((float) i2)) {
                        i2 = (int) measureText3;
                    }
                }
            }
            i2 += this.mInputText.getPaddingLeft() + this.mInputText.getPaddingRight();
            if (this.mMaxWidth != i2) {
                if (i2 > this.mMinWidth) {
                    this.mMaxWidth = i2;
                } else {
                    this.mMaxWidth = this.mMinWidth;
                }
                invalidate();
            }
        }
    }

    private boolean updateInputTextView() {
        CharSequence formatNumber = this.mDisplayedValues == null ? formatNumber(this.mValue) : this.mDisplayedValues[this.mValue - this.mMinValue];
        if (TextUtils.isEmpty(formatNumber) || formatNumber.equals(this.mInputText.getText().toString())) {
            return false;
        }
        this.mInputText.setText(formatNumber);
        return true;
    }

    public void computeScroll() {
        Scroller scroller = this.mFlingScroller;
        if (scroller.isFinished()) {
            scroller = this.mAdjustScroller;
            if (scroller.isFinished()) {
                return;
            }
        }
        scroller.computeScrollOffset();
        int currY = scroller.getCurrY();
        if (this.mPreviousScrollerY == 0) {
            this.mPreviousScrollerY = scroller.getStartY();
        }
        scrollBy(0, currY - this.mPreviousScrollerY);
        this.mPreviousScrollerY = currY;
        if (scroller.isFinished()) {
            onScrollerFinished(scroller);
        } else {
            invalidate();
        }
    }

    protected int computeVerticalScrollExtent() {
        return getHeight();
    }

    protected int computeVerticalScrollOffset() {
        return this.mCurrentScrollOffset;
    }

    protected int computeVerticalScrollRange() {
        return ((this.mMaxValue - this.mMinValue) + 1) * this.mSelectorElementHeight;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean dispatchKeyEvent(android.view.KeyEvent r6) {
        /*
        r5 = this;
        r4 = 20;
        r1 = 1;
        r0 = r6.getKeyCode();
        switch(r0) {
            case 19: goto L_0x0013;
            case 20: goto L_0x0013;
            case 23: goto L_0x000f;
            case 66: goto L_0x000f;
            default: goto L_0x000a;
        };
    L_0x000a:
        r1 = super.dispatchKeyEvent(r6);
    L_0x000e:
        return r1;
    L_0x000f:
        r5.removeAllCallbacks();
        goto L_0x000a;
    L_0x0013:
        r2 = r6.getAction();
        switch(r2) {
            case 0: goto L_0x001b;
            case 1: goto L_0x004f;
            default: goto L_0x001a;
        };
    L_0x001a:
        goto L_0x000a;
    L_0x001b:
        r2 = r5.mWrapSelectorWheel;
        if (r2 != 0) goto L_0x0021;
    L_0x001f:
        if (r0 != r4) goto L_0x0042;
    L_0x0021:
        r2 = r5.getValue();
        r3 = r5.getMaxValue();
        if (r2 >= r3) goto L_0x000a;
    L_0x002b:
        r5.requestFocus();
        r5.mLastHandledDownDpadKeyCode = r0;
        r5.removeAllCallbacks();
        r2 = r5.mFlingScroller;
        r2 = r2.isFinished();
        if (r2 == 0) goto L_0x000e;
    L_0x003b:
        if (r0 != r4) goto L_0x004d;
    L_0x003d:
        r0 = r1;
    L_0x003e:
        r5.changeValueByOne(r0);
        goto L_0x000e;
    L_0x0042:
        r2 = r5.getValue();
        r3 = r5.getMinValue();
        if (r2 <= r3) goto L_0x000a;
    L_0x004c:
        goto L_0x002b;
    L_0x004d:
        r0 = 0;
        goto L_0x003e;
    L_0x004f:
        r2 = r5.mLastHandledDownDpadKeyCode;
        if (r2 != r0) goto L_0x000a;
    L_0x0053:
        r0 = -1;
        r5.mLastHandledDownDpadKeyCode = r0;
        goto L_0x000e;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.NumberPicker.dispatchKeyEvent(android.view.KeyEvent):boolean");
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getActionMasked()) {
            case 1:
            case 3:
                removeAllCallbacks();
                break;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        switch (motionEvent.getActionMasked()) {
            case 1:
            case 3:
                removeAllCallbacks();
                break;
        }
        return super.dispatchTrackballEvent(motionEvent);
    }

    protected float getBottomFadingEdgeStrength() {
        return TOP_AND_BOTTOM_FADING_EDGE_STRENGTH;
    }

    public String[] getDisplayedValues() {
        return this.mDisplayedValues;
    }

    public int getMaxValue() {
        return this.mMaxValue;
    }

    public int getMinValue() {
        return this.mMinValue;
    }

    public int getSolidColor() {
        return this.mSolidColor;
    }

    protected float getTopFadingEdgeStrength() {
        return TOP_AND_BOTTOM_FADING_EDGE_STRENGTH;
    }

    public int getValue() {
        return this.mValue;
    }

    public boolean getWrapSelectorWheel() {
        return this.mWrapSelectorWheel;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeAllCallbacks();
    }

    protected void onDraw(Canvas canvas) {
        float right = (float) ((getRight() - getLeft()) / 2);
        float f = (float) this.mCurrentScrollOffset;
        int[] iArr = this.mSelectorIndices;
        float f2 = f;
        for (int i = 0; i < iArr.length; i++) {
            String str = (String) this.mSelectorIndexToStringCache.get(iArr[i]);
            if (i != 1 || this.mInputText.getVisibility() != 0) {
                canvas.drawText(str, right, f2, this.mSelectorWheelPaint);
            }
            f2 += (float) this.mSelectorElementHeight;
        }
        int i2 = this.mTopSelectionDividerTop;
        int i3 = i2 + this.mSelectionDividerHeight;
        canvas.drawRect(BitmapDescriptorFactory.HUE_RED, (float) i2, (float) getRight(), (float) i3, this.mSelectionDivider);
        i2 = this.mBottomSelectionDividerBottom;
        canvas.drawRect(BitmapDescriptorFactory.HUE_RED, (float) (i2 - this.mSelectionDividerHeight), (float) getRight(), (float) i2, this.mSelectionDivider);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled()) {
            return false;
        }
        switch (motionEvent.getActionMasked()) {
            case 0:
                removeAllCallbacks();
                this.mInputText.setVisibility(4);
                float y = motionEvent.getY();
                this.mLastDownEventY = y;
                this.mLastDownOrMoveEventY = y;
                this.mLastDownEventTime = motionEvent.getEventTime();
                this.mIngonreMoveEvents = false;
                if (this.mLastDownEventY < ((float) this.mTopSelectionDividerTop)) {
                    if (this.mScrollState == 0) {
                        this.mPressedStateHelper.buttonPressDelayed(2);
                    }
                } else if (this.mLastDownEventY > ((float) this.mBottomSelectionDividerBottom) && this.mScrollState == 0) {
                    this.mPressedStateHelper.buttonPressDelayed(1);
                }
                getParent().requestDisallowInterceptTouchEvent(true);
                if (!this.mFlingScroller.isFinished()) {
                    this.mFlingScroller.forceFinished(true);
                    this.mAdjustScroller.forceFinished(true);
                    onScrollStateChange(0);
                } else if (!this.mAdjustScroller.isFinished()) {
                    this.mFlingScroller.forceFinished(true);
                    this.mAdjustScroller.forceFinished(true);
                } else if (this.mLastDownEventY < ((float) this.mTopSelectionDividerTop)) {
                    postChangeCurrentByOneFromLongPress(false, (long) ViewConfiguration.getLongPressTimeout());
                } else if (this.mLastDownEventY > ((float) this.mBottomSelectionDividerBottom)) {
                    postChangeCurrentByOneFromLongPress(true, (long) ViewConfiguration.getLongPressTimeout());
                }
                return true;
            default:
                return false;
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int measuredWidth2 = this.mInputText.getMeasuredWidth();
        int measuredHeight2 = this.mInputText.getMeasuredHeight();
        measuredWidth = (measuredWidth - measuredWidth2) / 2;
        measuredHeight = (measuredHeight - measuredHeight2) / 2;
        this.mInputText.layout(measuredWidth, measuredHeight, measuredWidth2 + measuredWidth, measuredHeight2 + measuredHeight);
        if (z) {
            initializeSelectorWheel();
            initializeFadingEdges();
            this.mTopSelectionDividerTop = ((getHeight() - this.mSelectionDividersDistance) / 2) - this.mSelectionDividerHeight;
            this.mBottomSelectionDividerBottom = (this.mTopSelectionDividerTop + (this.mSelectionDividerHeight * 2)) + this.mSelectionDividersDistance;
        }
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(makeMeasureSpec(i, this.mMaxWidth), makeMeasureSpec(i2, this.mMaxHeight));
        setMeasuredDimension(resolveSizeAndStateRespectingMinSize(this.mMinWidth, getMeasuredWidth(), i), resolveSizeAndStateRespectingMinSize(this.mMinHeight, getMeasuredHeight(), i2));
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled()) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        switch (motionEvent.getActionMasked()) {
            case 1:
                removeChangeCurrentByOneFromLongPress();
                this.mPressedStateHelper.cancel();
                VelocityTracker velocityTracker = this.mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumFlingVelocity);
                int yVelocity = (int) velocityTracker.getYVelocity();
                if (Math.abs(yVelocity) > this.mMinimumFlingVelocity) {
                    fling(yVelocity);
                    onScrollStateChange(2);
                } else {
                    yVelocity = (int) motionEvent.getY();
                    long eventTime = motionEvent.getEventTime() - this.mLastDownEventTime;
                    if (((int) Math.abs(((float) yVelocity) - this.mLastDownEventY)) > this.mTouchSlop || eventTime >= ((long) ViewConfiguration.getTapTimeout())) {
                        ensureScrollWheelAdjusted();
                    } else {
                        yVelocity = (yVelocity / this.mSelectorElementHeight) - 1;
                        if (yVelocity > 0) {
                            changeValueByOne(true);
                            this.mPressedStateHelper.buttonTapped(1);
                        } else if (yVelocity < 0) {
                            changeValueByOne(false);
                            this.mPressedStateHelper.buttonTapped(2);
                        }
                    }
                    onScrollStateChange(0);
                }
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
                break;
            case 2:
                if (!this.mIngonreMoveEvents) {
                    float y = motionEvent.getY();
                    if (this.mScrollState == 1) {
                        scrollBy(0, (int) (y - this.mLastDownOrMoveEventY));
                        invalidate();
                    } else if (((int) Math.abs(y - this.mLastDownEventY)) > this.mTouchSlop) {
                        removeAllCallbacks();
                        onScrollStateChange(1);
                    }
                    this.mLastDownOrMoveEventY = y;
                    break;
                }
                break;
        }
        return true;
    }

    public void scrollBy(int i, int i2) {
        int[] iArr = this.mSelectorIndices;
        if (!this.mWrapSelectorWheel && i2 > 0 && iArr[1] <= this.mMinValue) {
            this.mCurrentScrollOffset = this.mInitialScrollOffset;
        } else if (this.mWrapSelectorWheel || i2 >= 0 || iArr[1] < this.mMaxValue) {
            this.mCurrentScrollOffset += i2;
            while (this.mCurrentScrollOffset - this.mInitialScrollOffset > this.mSelectorTextGapHeight) {
                this.mCurrentScrollOffset -= this.mSelectorElementHeight;
                decrementSelectorIndices(iArr);
                setValueInternal(iArr[1], true);
                if (!this.mWrapSelectorWheel && iArr[1] <= this.mMinValue) {
                    this.mCurrentScrollOffset = this.mInitialScrollOffset;
                }
            }
            while (this.mCurrentScrollOffset - this.mInitialScrollOffset < (-this.mSelectorTextGapHeight)) {
                this.mCurrentScrollOffset += this.mSelectorElementHeight;
                incrementSelectorIndices(iArr);
                setValueInternal(iArr[1], true);
                if (!this.mWrapSelectorWheel && iArr[1] >= this.mMaxValue) {
                    this.mCurrentScrollOffset = this.mInitialScrollOffset;
                }
            }
        } else {
            this.mCurrentScrollOffset = this.mInitialScrollOffset;
        }
    }

    public void setDisplayedValues(String[] strArr) {
        if (this.mDisplayedValues != strArr) {
            this.mDisplayedValues = strArr;
            updateInputTextView();
            initializeSelectorWheelIndices();
            tryComputeMaxWidth();
        }
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.mInputText.setEnabled(z);
    }

    public void setFormatter(Formatter formatter) {
        if (formatter != this.mFormatter) {
            this.mFormatter = formatter;
            initializeSelectorWheelIndices();
            updateInputTextView();
        }
    }

    public void setMaxValue(int i) {
        if (this.mMaxValue != i) {
            if (i < 0) {
                throw new IllegalArgumentException("maxValue must be >= 0");
            }
            this.mMaxValue = i;
            if (this.mMaxValue < this.mValue) {
                this.mValue = this.mMaxValue;
            }
            setWrapSelectorWheel(this.mMaxValue - this.mMinValue > this.mSelectorIndices.length);
            initializeSelectorWheelIndices();
            updateInputTextView();
            tryComputeMaxWidth();
            invalidate();
        }
    }

    public void setMinValue(int i) {
        if (this.mMinValue != i) {
            if (i < 0) {
                throw new IllegalArgumentException("minValue must be >= 0");
            }
            this.mMinValue = i;
            if (this.mMinValue > this.mValue) {
                this.mValue = this.mMinValue;
            }
            setWrapSelectorWheel(this.mMaxValue - this.mMinValue > this.mSelectorIndices.length);
            initializeSelectorWheelIndices();
            updateInputTextView();
            tryComputeMaxWidth();
            invalidate();
        }
    }

    public void setOnLongPressUpdateInterval(long j) {
        this.mLongPressUpdateInterval = j;
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    public void setOnValueChangedListener(OnValueChangeListener onValueChangeListener) {
        this.mOnValueChangeListener = onValueChangeListener;
    }

    public void setSelectorColor(int i) {
        this.mSelectionDivider.setColor(i);
    }

    public void setTextColor(int i) {
        this.mInputText.setTextColor(i);
        this.mSelectorWheelPaint.setColor(i);
    }

    public void setValue(int i) {
        setValueInternal(i, false);
    }

    public void setWrapSelectorWheel(boolean z) {
        Object obj = this.mMaxValue - this.mMinValue >= this.mSelectorIndices.length ? 1 : null;
        if ((!z || obj != null) && z != this.mWrapSelectorWheel) {
            this.mWrapSelectorWheel = z;
        }
    }
}
