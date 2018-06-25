package com.mohamadamin.persianmaterialdatetimepicker.time;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction;
import android.widget.FrameLayout;
import com.mohamadamin.persianmaterialdatetimepicker.C0610R;
import com.mohamadamin.persianmaterialdatetimepicker.HapticFeedbackController;
import com.mohamadamin.persianmaterialdatetimepicker.utils.LanguageUtils;
import java.util.Calendar;

public class RadialPickerLayout extends FrameLayout implements OnTouchListener {
    private static final int AM = 0;
    private static final int AMPM_INDEX = 2;
    private static final int ENABLE_PICKER_INDEX = 3;
    private static final int HOUR_INDEX = 0;
    private static final int HOUR_VALUE_TO_DEGREES_STEP_SIZE = 30;
    private static final int MINUTE_INDEX = 1;
    private static final int MINUTE_VALUE_TO_DEGREES_STEP_SIZE = 6;
    private static final int PM = 1;
    private static final String TAG = "RadialPickerLayout";
    private static final int VISIBLE_DEGREES_STEP_SIZE = 30;
    private final int TAP_TIMEOUT;
    private final int TOUCH_SLOP;
    private AccessibilityManager mAccessibilityManager;
    private AmPmCirclesView mAmPmCirclesView;
    private CircleView mCircleView;
    private int mCurrentHoursOfDay;
    private int mCurrentItemShowing;
    private int mCurrentMinutes;
    private boolean mDoingMove;
    private boolean mDoingTouch;
    private int mDownDegrees;
    private float mDownX;
    private float mDownY;
    private View mGrayBox;
    private Handler mHandler = new Handler();
    private HapticFeedbackController mHapticFeedbackController;
    private boolean mHideAmPm;
    private RadialSelectorView mHourRadialSelectorView;
    private RadialTextsView mHourRadialTextsView;
    private boolean mInputEnabled;
    private boolean mIs24HourMode;
    private int mIsTouchingAmOrPm = -1;
    private int mLastValueSelected;
    private OnValueSelectedListener mListener;
    private RadialSelectorView mMinuteRadialSelectorView;
    private RadialTextsView mMinuteRadialTextsView;
    private int[] mSnapPrefer30sMap;
    private boolean mTimeInitialized;
    private AnimatorSet mTransition;

    /* renamed from: com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout$1 */
    class C06151 implements Runnable {
        C06151() {
        }

        public void run() {
            RadialPickerLayout.this.mAmPmCirclesView.setAmOrPmPressed(RadialPickerLayout.this.mIsTouchingAmOrPm);
            RadialPickerLayout.this.mAmPmCirclesView.invalidate();
        }
    }

    public interface OnValueSelectedListener {
        void onValueSelected(int i, int i2, boolean z);
    }

    public RadialPickerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
        this.TOUCH_SLOP = ViewConfiguration.get(context).getScaledTouchSlop();
        this.TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
        this.mDoingMove = false;
        this.mCircleView = new CircleView(context);
        addView(this.mCircleView);
        this.mAmPmCirclesView = new AmPmCirclesView(context);
        addView(this.mAmPmCirclesView);
        this.mHourRadialSelectorView = new RadialSelectorView(context);
        addView(this.mHourRadialSelectorView);
        this.mMinuteRadialSelectorView = new RadialSelectorView(context);
        addView(this.mMinuteRadialSelectorView);
        this.mHourRadialTextsView = new RadialTextsView(context);
        addView(this.mHourRadialTextsView);
        this.mMinuteRadialTextsView = new RadialTextsView(context);
        addView(this.mMinuteRadialTextsView);
        preparePrefer30sMap();
        this.mLastValueSelected = -1;
        this.mInputEnabled = true;
        this.mGrayBox = new View(context);
        this.mGrayBox.setLayoutParams(new LayoutParams(-1, -1));
        this.mGrayBox.setBackgroundColor(getResources().getColor(C0610R.color.mdtp_transparent_black));
        this.mGrayBox.setVisibility(4);
        addView(this.mGrayBox);
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        this.mTimeInitialized = false;
    }

    public void setOnValueSelectedListener(OnValueSelectedListener listener) {
        this.mListener = listener;
    }

    public void initialize(Context context, HapticFeedbackController hapticFeedbackController, int initialHoursOfDay, int initialMinutes, boolean is24HourMode) {
        if (this.mTimeInitialized) {
            Log.e(TAG, "Time has already been initialized.");
            return;
        }
        this.mHapticFeedbackController = hapticFeedbackController;
        this.mIs24HourMode = is24HourMode;
        boolean z = this.mAccessibilityManager.isTouchExplorationEnabled() || this.mIs24HourMode;
        this.mHideAmPm = z;
        this.mCircleView.initialize(context, this.mHideAmPm);
        this.mCircleView.invalidate();
        if (!this.mHideAmPm) {
            this.mAmPmCirclesView.initialize(context, initialHoursOfDay < 12 ? 0 : 1);
            this.mAmPmCirclesView.invalidate();
        }
        Resources res = context.getResources();
        int[] iArr = new int[12];
        iArr = new int[]{12, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        int[] iArr2 = new int[12];
        iArr2 = new int[]{0, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
        int[] iArr3 = new int[12];
        iArr3 = new int[]{0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55};
        String[] hoursTexts = new String[12];
        String[] innerHoursTexts = new String[12];
        String[] minutesTexts = new String[12];
        for (int i = 0; i < 12; i++) {
            hoursTexts[i] = LanguageUtils.getPersianNumbers(is24HourMode ? String.format("%02d", new Object[]{Integer.valueOf(iArr2[i])}) : String.format("%d", new Object[]{Integer.valueOf(iArr[i])}));
            innerHoursTexts[i] = LanguageUtils.getPersianNumbers(String.format("%d", new Object[]{Integer.valueOf(iArr[i])}));
            minutesTexts[i] = LanguageUtils.getPersianNumbers(String.format("%02d", new Object[]{Integer.valueOf(iArr3[i])}));
        }
        this.mHourRadialTextsView.initialize(res, hoursTexts, is24HourMode ? innerHoursTexts : null, this.mHideAmPm, true);
        this.mHourRadialTextsView.setSelection(is24HourMode ? initialHoursOfDay : initialHoursOfDay % 12);
        this.mHourRadialTextsView.invalidate();
        this.mMinuteRadialTextsView.initialize(res, minutesTexts, null, this.mHideAmPm, false);
        this.mMinuteRadialTextsView.setSelection(initialMinutes);
        this.mMinuteRadialTextsView.invalidate();
        setValueForItem(0, initialHoursOfDay);
        setValueForItem(1, initialMinutes);
        this.mHourRadialSelectorView.initialize(context, this.mHideAmPm, is24HourMode, true, (initialHoursOfDay % 12) * 30, isHourInnerCircle(initialHoursOfDay));
        this.mMinuteRadialSelectorView.initialize(context, this.mHideAmPm, false, false, initialMinutes * 6, false);
        this.mTimeInitialized = true;
    }

    void setTheme(Context context, boolean themeDark) {
        this.mCircleView.setTheme(context, themeDark);
        this.mAmPmCirclesView.setTheme(context, themeDark);
        this.mHourRadialTextsView.setTheme(context, themeDark);
        this.mMinuteRadialTextsView.setTheme(context, themeDark);
        this.mHourRadialSelectorView.setTheme(context, themeDark);
        this.mMinuteRadialSelectorView.setTheme(context, themeDark);
    }

    public void setTime(int hours, int minutes) {
        setItem(0, hours);
        setItem(1, minutes);
    }

    private void setItem(int index, int value) {
        if (index == 0) {
            setValueForItem(0, value);
            this.mHourRadialSelectorView.setSelection((value % 12) * 30, isHourInnerCircle(value), false);
            this.mHourRadialSelectorView.invalidate();
            this.mHourRadialTextsView.setSelection(value);
            this.mHourRadialTextsView.invalidate();
        } else if (index == 1) {
            setValueForItem(1, value);
            this.mMinuteRadialSelectorView.setSelection(value * 6, false, false);
            this.mMinuteRadialSelectorView.invalidate();
            this.mMinuteRadialTextsView.setSelection(value);
            this.mHourRadialTextsView.invalidate();
        }
    }

    private boolean isHourInnerCircle(int hourOfDay) {
        return this.mIs24HourMode && hourOfDay <= 12 && hourOfDay != 0;
    }

    public int getHours() {
        return this.mCurrentHoursOfDay;
    }

    public int getMinutes() {
        return this.mCurrentMinutes;
    }

    private int getCurrentlyShowingValue() {
        int currentIndex = getCurrentItemShowing();
        if (currentIndex == 0) {
            return this.mCurrentHoursOfDay;
        }
        if (currentIndex == 1) {
            return this.mCurrentMinutes;
        }
        return -1;
    }

    public int getIsCurrentlyAmOrPm() {
        if (this.mCurrentHoursOfDay < 12) {
            return 0;
        }
        if (this.mCurrentHoursOfDay < 24) {
            return 1;
        }
        return -1;
    }

    private void setValueForItem(int index, int value) {
        if (index == 0) {
            this.mCurrentHoursOfDay = value;
        } else if (index == 1) {
            this.mCurrentMinutes = value;
        } else if (index != 2) {
        } else {
            if (value == 0) {
                this.mCurrentHoursOfDay %= 12;
            } else if (value == 1) {
                this.mCurrentHoursOfDay = (this.mCurrentHoursOfDay % 12) + 12;
            }
        }
    }

    public void setAmOrPm(int amOrPm) {
        this.mAmPmCirclesView.setAmOrPm(amOrPm);
        this.mAmPmCirclesView.invalidate();
        setValueForItem(2, amOrPm);
    }

    private void preparePrefer30sMap() {
        this.mSnapPrefer30sMap = new int[361];
        int snappedOutputDegrees = 0;
        int count = 1;
        int expectedCount = 8;
        for (int degrees = 0; degrees < 361; degrees++) {
            this.mSnapPrefer30sMap[degrees] = snappedOutputDegrees;
            if (count == expectedCount) {
                snappedOutputDegrees += 6;
                if (snappedOutputDegrees == 360) {
                    expectedCount = 7;
                } else if (snappedOutputDegrees % 30 == 0) {
                    expectedCount = 14;
                } else {
                    expectedCount = 4;
                }
                count = 1;
            } else {
                count++;
            }
        }
    }

    private int snapPrefer30s(int degrees) {
        if (this.mSnapPrefer30sMap == null) {
            return -1;
        }
        return this.mSnapPrefer30sMap[degrees];
    }

    private static int snapOnly30s(int degrees, int forceHigherOrLower) {
        int floor = (degrees / 30) * 30;
        int ceiling = floor + 30;
        if (forceHigherOrLower == 1) {
            return ceiling;
        }
        if (forceHigherOrLower == -1) {
            if (degrees == floor) {
                floor -= 30;
            }
            return floor;
        } else if (degrees - floor < ceiling - degrees) {
            return floor;
        } else {
            return ceiling;
        }
    }

    private int reselectSelector(int degrees, boolean isInnerCircle, boolean forceToVisibleValue, boolean forceDrawDot) {
        int i = -1;
        if (degrees != -1) {
            boolean allowFineGrained;
            RadialSelectorView radialSelectorView;
            int stepSize;
            int currentShowing = getCurrentItemShowing();
            if (forceToVisibleValue || currentShowing != 1) {
                allowFineGrained = false;
            } else {
                allowFineGrained = true;
            }
            if (allowFineGrained) {
                degrees = snapPrefer30s(degrees);
            } else {
                degrees = snapOnly30s(degrees, 0);
            }
            if (currentShowing == 0) {
                radialSelectorView = this.mHourRadialSelectorView;
                stepSize = 30;
            } else {
                radialSelectorView = this.mMinuteRadialSelectorView;
                stepSize = 6;
            }
            radialSelectorView.setSelection(degrees, isInnerCircle, forceDrawDot);
            radialSelectorView.invalidate();
            if (currentShowing == 0) {
                if (this.mIs24HourMode) {
                    if (degrees == 0 && isInnerCircle) {
                        degrees = 360;
                    } else if (degrees == 360 && !isInnerCircle) {
                        degrees = 0;
                    }
                } else if (degrees == 0) {
                    degrees = 360;
                }
            } else if (degrees == 360 && currentShowing == 1) {
                degrees = 0;
            }
            i = degrees / stepSize;
            if (currentShowing == 0 && this.mIs24HourMode && !isInnerCircle && degrees != 0) {
                i += 12;
            }
            if (getCurrentItemShowing() == 0) {
                this.mHourRadialTextsView.setSelection(i);
                this.mHourRadialTextsView.invalidate();
            } else if (getCurrentItemShowing() == 1) {
                this.mMinuteRadialTextsView.setSelection(i);
                this.mMinuteRadialTextsView.invalidate();
            }
        }
        return i;
    }

    private int getDegreesFromCoords(float pointX, float pointY, boolean forceLegal, Boolean[] isInnerCircle) {
        int currentItem = getCurrentItemShowing();
        if (currentItem == 0) {
            return this.mHourRadialSelectorView.getDegreesFromCoords(pointX, pointY, forceLegal, isInnerCircle);
        }
        if (currentItem == 1) {
            return this.mMinuteRadialSelectorView.getDegreesFromCoords(pointX, pointY, forceLegal, isInnerCircle);
        }
        return -1;
    }

    public int getCurrentItemShowing() {
        if (this.mCurrentItemShowing == 0 || this.mCurrentItemShowing == 1) {
            return this.mCurrentItemShowing;
        }
        Log.e(TAG, "Current item showing was unfortunately set to " + this.mCurrentItemShowing);
        return -1;
    }

    public void setCurrentItemShowing(int index, boolean animate) {
        int minuteAlpha = 255;
        if (index == 0 || index == 1) {
            int lastIndex = getCurrentItemShowing();
            this.mCurrentItemShowing = index;
            if (!animate || index == lastIndex) {
                int hourAlpha;
                if (index == 0) {
                    hourAlpha = 255;
                } else {
                    hourAlpha = 0;
                }
                if (index != 1) {
                    minuteAlpha = 0;
                }
                this.mHourRadialTextsView.setAlpha((float) hourAlpha);
                this.mHourRadialSelectorView.setAlpha((float) hourAlpha);
                this.mMinuteRadialTextsView.setAlpha((float) minuteAlpha);
                this.mMinuteRadialSelectorView.setAlpha((float) minuteAlpha);
                return;
            }
            ObjectAnimator[] anims = new ObjectAnimator[4];
            if (index == 1) {
                anims[0] = this.mHourRadialTextsView.getDisappearAnimator();
                anims[1] = this.mHourRadialSelectorView.getDisappearAnimator();
                anims[2] = this.mMinuteRadialTextsView.getReappearAnimator();
                anims[3] = this.mMinuteRadialSelectorView.getReappearAnimator();
            } else if (index == 0) {
                anims[0] = this.mHourRadialTextsView.getReappearAnimator();
                anims[1] = this.mHourRadialSelectorView.getReappearAnimator();
                anims[2] = this.mMinuteRadialTextsView.getDisappearAnimator();
                anims[3] = this.mMinuteRadialSelectorView.getDisappearAnimator();
            }
            if (this.mTransition != null && this.mTransition.isRunning()) {
                this.mTransition.end();
            }
            this.mTransition = new AnimatorSet();
            this.mTransition.playTogether(anims);
            this.mTransition.start();
            return;
        }
        Log.e(TAG, "TimePicker does not support view at index " + index);
    }

    public boolean onTouch(View v, MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        final Boolean[] isInnerCircle = new Boolean[]{Boolean.valueOf(false)};
        int degrees;
        int value;
        switch (event.getAction()) {
            case 0:
                if (!this.mInputEnabled) {
                    return true;
                }
                this.mDownX = eventX;
                this.mDownY = eventY;
                this.mLastValueSelected = -1;
                this.mDoingMove = false;
                this.mDoingTouch = true;
                if (this.mHideAmPm) {
                    this.mIsTouchingAmOrPm = -1;
                } else {
                    this.mIsTouchingAmOrPm = this.mAmPmCirclesView.getIsTouchingAmOrPm(eventX, eventY);
                }
                if (this.mIsTouchingAmOrPm == 0 || this.mIsTouchingAmOrPm == 1) {
                    this.mHapticFeedbackController.tryVibrate();
                    this.mDownDegrees = -1;
                    this.mHandler.postDelayed(new C06151(), (long) this.TAP_TIMEOUT);
                } else {
                    this.mDownDegrees = getDegreesFromCoords(eventX, eventY, this.mAccessibilityManager.isTouchExplorationEnabled(), isInnerCircle);
                    if (this.mDownDegrees != -1) {
                        this.mHapticFeedbackController.tryVibrate();
                        this.mHandler.postDelayed(new Runnable() {
                            public void run() {
                                RadialPickerLayout.this.mDoingMove = true;
                                int value = RadialPickerLayout.this.reselectSelector(RadialPickerLayout.this.mDownDegrees, isInnerCircle[0].booleanValue(), false, true);
                                RadialPickerLayout.this.mLastValueSelected = value;
                                RadialPickerLayout.this.mListener.onValueSelected(RadialPickerLayout.this.getCurrentItemShowing(), value, false);
                            }
                        }, (long) this.TAP_TIMEOUT);
                    }
                }
                return true;
            case 1:
                if (this.mInputEnabled) {
                    this.mHandler.removeCallbacksAndMessages(null);
                    this.mDoingTouch = false;
                    if (this.mIsTouchingAmOrPm == 0 || this.mIsTouchingAmOrPm == 1) {
                        int isTouchingAmOrPm = this.mAmPmCirclesView.getIsTouchingAmOrPm(eventX, eventY);
                        this.mAmPmCirclesView.setAmOrPmPressed(-1);
                        this.mAmPmCirclesView.invalidate();
                        if (isTouchingAmOrPm == this.mIsTouchingAmOrPm) {
                            this.mAmPmCirclesView.setAmOrPm(isTouchingAmOrPm);
                            if (getIsCurrentlyAmOrPm() != isTouchingAmOrPm) {
                                this.mListener.onValueSelected(2, this.mIsTouchingAmOrPm, false);
                                setValueForItem(2, isTouchingAmOrPm);
                            }
                        }
                        this.mIsTouchingAmOrPm = -1;
                        break;
                    }
                    if (this.mDownDegrees != -1) {
                        degrees = getDegreesFromCoords(eventX, eventY, this.mDoingMove, isInnerCircle);
                        if (degrees != -1) {
                            value = reselectSelector(degrees, isInnerCircle[0].booleanValue(), !this.mDoingMove, false);
                            if (getCurrentItemShowing() == 0 && !this.mIs24HourMode) {
                                int amOrPm = getIsCurrentlyAmOrPm();
                                if (amOrPm == 0 && value == 12) {
                                    value = 0;
                                } else if (amOrPm == 1 && value != 12) {
                                    value += 12;
                                }
                            }
                            setValueForItem(getCurrentItemShowing(), value);
                            this.mListener.onValueSelected(getCurrentItemShowing(), value, true);
                        }
                    }
                    this.mDoingMove = false;
                    return true;
                }
                Log.d(TAG, "Input was disabled, but received ACTION_UP.");
                this.mListener.onValueSelected(3, 1, false);
                return true;
                break;
            case 2:
                if (this.mInputEnabled) {
                    float dY = Math.abs(eventY - this.mDownY);
                    float dX = Math.abs(eventX - this.mDownX);
                    if (this.mDoingMove || dX > ((float) this.TOUCH_SLOP) || dY > ((float) this.TOUCH_SLOP)) {
                        if (this.mIsTouchingAmOrPm == 0 || this.mIsTouchingAmOrPm == 1) {
                            this.mHandler.removeCallbacksAndMessages(null);
                            if (this.mAmPmCirclesView.getIsTouchingAmOrPm(eventX, eventY) != this.mIsTouchingAmOrPm) {
                                this.mAmPmCirclesView.setAmOrPmPressed(-1);
                                this.mAmPmCirclesView.invalidate();
                                this.mIsTouchingAmOrPm = -1;
                                break;
                            }
                        } else if (this.mDownDegrees != -1) {
                            this.mDoingMove = true;
                            this.mHandler.removeCallbacksAndMessages(null);
                            degrees = getDegreesFromCoords(eventX, eventY, true, isInnerCircle);
                            if (degrees != -1) {
                                value = reselectSelector(degrees, isInnerCircle[0].booleanValue(), false, true);
                                if (value != this.mLastValueSelected) {
                                    this.mHapticFeedbackController.tryVibrate();
                                    this.mLastValueSelected = value;
                                    this.mListener.onValueSelected(getCurrentItemShowing(), value, false);
                                }
                            }
                            return true;
                        }
                    }
                }
                Log.e(TAG, "Input was disabled, but received ACTION_MOVE.");
                return true;
                break;
        }
        return false;
    }

    public boolean trySettingInputEnabled(boolean inputEnabled) {
        int i = 0;
        if (this.mDoingTouch && !inputEnabled) {
            return false;
        }
        this.mInputEnabled = inputEnabled;
        View view = this.mGrayBox;
        if (inputEnabled) {
            i = 4;
        }
        view.setVisibility(i);
        return true;
    }

    public void onInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        if (VERSION.SDK_INT >= 21) {
            info.addAction(AccessibilityAction.ACTION_SCROLL_BACKWARD);
            info.addAction(AccessibilityAction.ACTION_SCROLL_FORWARD);
            return;
        }
        info.addAction(4096);
        info.addAction(8192);
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() != 32) {
            return super.dispatchPopulateAccessibilityEvent(event);
        }
        event.getText().clear();
        Calendar time = Calendar.getInstance();
        time.set(10, getHours());
        time.set(12, getMinutes());
        long millis = time.getTimeInMillis();
        int flags = 1;
        if (this.mIs24HourMode) {
            flags = 1 | 128;
        }
        event.getText().add(LanguageUtils.getPersianNumbers(DateUtils.formatDateTime(getContext(), millis, flags)));
        return true;
    }

    @SuppressLint({"NewApi"})
    public boolean performAccessibilityAction(int action, Bundle arguments) {
        if (super.performAccessibilityAction(action, arguments)) {
            return true;
        }
        int changeMultiplier = 0;
        if (action == 4096) {
            changeMultiplier = 1;
        } else if (action == 8192) {
            changeMultiplier = -1;
        }
        if (changeMultiplier == 0) {
            return false;
        }
        int maxValue;
        int value = getCurrentlyShowingValue();
        int stepSize = 0;
        int currentItemShowing = getCurrentItemShowing();
        if (currentItemShowing == 0) {
            stepSize = 30;
            value %= 12;
        } else if (currentItemShowing == 1) {
            stepSize = 6;
        }
        value = snapOnly30s(value * stepSize, changeMultiplier) / stepSize;
        int minValue = 0;
        if (currentItemShowing != 0) {
            maxValue = 55;
        } else if (this.mIs24HourMode) {
            maxValue = 23;
        } else {
            maxValue = 12;
            minValue = 1;
        }
        if (value > maxValue) {
            value = minValue;
        } else if (value < minValue) {
            value = maxValue;
        }
        setItem(currentItemShowing, value);
        this.mListener.onValueSelected(currentItemShowing, value, false);
        return true;
    }
}
