package com.mohamadamin.persianmaterialdatetimepicker.date;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.ExploreByTouchHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.View.MeasureSpec;
import android.view.accessibility.AccessibilityEvent;
import com.mohamadamin.persianmaterialdatetimepicker.C0610R;
import com.mohamadamin.persianmaterialdatetimepicker.TypefaceHelper;
import com.mohamadamin.persianmaterialdatetimepicker.Utils;
import com.mohamadamin.persianmaterialdatetimepicker.date.MonthAdapter.CalendarDay;
import com.mohamadamin.persianmaterialdatetimepicker.utils.LanguageUtils;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;

public abstract class MonthView extends View {
    protected static int DAY_SELECTED_CIRCLE_SIZE = 0;
    protected static int DAY_SEPARATOR_WIDTH = 1;
    protected static final int DEFAULT_FOCUS_MONTH = -1;
    protected static int DEFAULT_HEIGHT = 32;
    protected static final int DEFAULT_NUM_DAYS = 7;
    protected static final int DEFAULT_NUM_ROWS = 6;
    protected static final int DEFAULT_SELECTED_DAY = -1;
    protected static final int DEFAULT_SHOW_WK_NUM = 0;
    protected static final int DEFAULT_WEEK_START = 7;
    protected static final int MAX_NUM_ROWS = 6;
    protected static int MINI_DAY_NUMBER_TEXT_SIZE = 0;
    protected static int MIN_HEIGHT = 10;
    protected static int MONTH_DAY_LABEL_TEXT_SIZE = 0;
    protected static int MONTH_HEADER_SIZE = 0;
    protected static int MONTH_LABEL_TEXT_SIZE = 0;
    private static final int SELECTED_CIRCLE_ALPHA = 255;
    private static final String TAG = "MonthView";
    public static final String VIEW_PARAMS_FOCUS_MONTH = "focus_month";
    public static final String VIEW_PARAMS_HEIGHT = "height";
    public static final String VIEW_PARAMS_MONTH = "month";
    public static final String VIEW_PARAMS_NUM_DAYS = "num_days";
    public static final String VIEW_PARAMS_SELECTED_DAY = "selected_day";
    public static final String VIEW_PARAMS_SHOW_WK_NUM = "show_wk_num";
    public static final String VIEW_PARAMS_WEEK_START = "week_start";
    public static final String VIEW_PARAMS_YEAR = "year";
    protected static float mScale = 0.0f;
    protected DatePickerController mController;
    protected final PersianCalendar mDayLabelCalendar;
    private int mDayOfWeekStart;
    private String mDayOfWeekTypeface;
    protected int mDayTextColor;
    protected int mDisabledDayTextColor;
    protected int mEdgePadding;
    protected int mFirstJulianDay;
    protected int mFirstMonth;
    protected boolean mHasToday;
    protected int mHighlightedDayTextColor;
    protected int mLastMonth;
    private boolean mLockAccessibilityDelegate;
    protected int mMonth;
    protected Paint mMonthDayLabelPaint;
    protected int mMonthDayTextColor;
    protected Paint mMonthNumPaint;
    protected int mMonthTitleColor;
    protected Paint mMonthTitlePaint;
    private String mMonthTitleTypeface;
    protected int mNumCells;
    protected int mNumDays;
    protected int mNumRows;
    protected OnDayClickListener mOnDayClickListener;
    private final PersianCalendar mPersianCalendar;
    protected int mRowHeight;
    protected Paint mSelectedCirclePaint;
    protected int mSelectedDay;
    protected int mSelectedDayTextColor;
    protected int mSelectedLeft;
    protected int mSelectedRight;
    private final StringBuilder mStringBuilder;
    protected int mToday;
    protected int mTodayNumberColor;
    private final MonthViewTouchHelper mTouchHelper;
    protected int mWeekStart;
    protected int mWidth;
    protected int mYear;

    public interface OnDayClickListener {
        void onDayClick(MonthView monthView, CalendarDay calendarDay);
    }

    protected class MonthViewTouchHelper extends ExploreByTouchHelper {
        private final PersianCalendar mTempCalendar = new PersianCalendar();
        private final Rect mTempRect = new Rect();

        public MonthViewTouchHelper(View host) {
            super(host);
        }

        public void setFocusedVirtualView(int virtualViewId) {
            getAccessibilityNodeProvider(MonthView.this).performAction(virtualViewId, 64, null);
        }

        public void clearFocusedVirtualView() {
            int focusedVirtualView = getFocusedVirtualView();
            if (focusedVirtualView != Integer.MIN_VALUE) {
                getAccessibilityNodeProvider(MonthView.this).performAction(focusedVirtualView, 128, null);
            }
        }

        protected int getVirtualViewAt(float x, float y) {
            int day = MonthView.this.getDayFromLocation(x, y);
            return day >= 0 ? day : Integer.MIN_VALUE;
        }

        protected void getVisibleVirtualViews(List<Integer> virtualViewIds) {
            for (int day = 1; day <= MonthView.this.mNumCells; day++) {
                virtualViewIds.add(Integer.valueOf(day));
            }
        }

        protected void onPopulateEventForVirtualView(int virtualViewId, AccessibilityEvent event) {
            event.setContentDescription(getItemDescription(virtualViewId));
        }

        protected void onPopulateNodeForVirtualView(int virtualViewId, AccessibilityNodeInfoCompat node) {
            getItemBounds(virtualViewId, this.mTempRect);
            node.setContentDescription(getItemDescription(virtualViewId));
            node.setBoundsInParent(this.mTempRect);
            node.addAction(16);
            if (virtualViewId == MonthView.this.mSelectedDay) {
                node.setSelected(true);
            }
        }

        protected boolean onPerformActionForVirtualView(int virtualViewId, int action, Bundle arguments) {
            switch (action) {
                case 16:
                    MonthView.this.onDayClick(virtualViewId);
                    return true;
                default:
                    return false;
            }
        }

        protected void getItemBounds(int day, Rect rect) {
            int offsetX = MonthView.this.mEdgePadding;
            int offsetY = MonthView.this.getMonthHeaderSize();
            int cellHeight = MonthView.this.mRowHeight;
            int cellWidth = (MonthView.this.mWidth - (MonthView.this.mEdgePadding * 2)) / MonthView.this.mNumDays;
            int index = (day - 1) + MonthView.this.findDayOffset();
            int x = offsetX + ((index % MonthView.this.mNumDays) * cellWidth);
            int y = offsetY + ((index / MonthView.this.mNumDays) * cellHeight);
            rect.set(x, y, x + cellWidth, y + cellHeight);
        }

        protected CharSequence getItemDescription(int day) {
            this.mTempCalendar.setPersianDate(MonthView.this.mYear, MonthView.this.mMonth, day);
            String date = LanguageUtils.getPersianNumbers(this.mTempCalendar.getPersianLongDate());
            if (day != MonthView.this.mSelectedDay) {
                return date;
            }
            return MonthView.this.getContext().getString(C0610R.string.mdtp_item_is_selected, new Object[]{date});
        }
    }

    public abstract void drawMonthDay(Canvas canvas, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9);

    public MonthView(Context context) {
        this(context, null, null);
    }

    public MonthView(Context context, AttributeSet attr, DatePickerController controller) {
        boolean darkTheme = false;
        super(context, attr);
        this.mEdgePadding = 0;
        this.mFirstJulianDay = -1;
        this.mFirstMonth = -1;
        this.mLastMonth = -1;
        this.mRowHeight = DEFAULT_HEIGHT;
        this.mHasToday = false;
        this.mSelectedDay = -1;
        this.mToday = -1;
        this.mWeekStart = 7;
        this.mNumDays = 7;
        this.mNumCells = this.mNumDays;
        this.mSelectedLeft = -1;
        this.mSelectedRight = -1;
        this.mNumRows = 6;
        this.mDayOfWeekStart = 0;
        this.mController = controller;
        Resources res = context.getResources();
        this.mDayLabelCalendar = new PersianCalendar();
        this.mPersianCalendar = new PersianCalendar();
        this.mDayOfWeekTypeface = res.getString(C0610R.string.mdtp_day_of_week_label_typeface);
        this.mMonthTitleTypeface = res.getString(C0610R.string.mdtp_sans_serif);
        if (this.mController != null && this.mController.isThemeDark()) {
            darkTheme = true;
        }
        if (darkTheme) {
            this.mDayTextColor = res.getColor(C0610R.color.mdtp_date_picker_text_normal_dark_theme);
            this.mMonthDayTextColor = res.getColor(C0610R.color.mdtp_date_picker_month_day_dark_theme);
            this.mDisabledDayTextColor = res.getColor(C0610R.color.mdtp_date_picker_text_disabled_dark_theme);
            this.mHighlightedDayTextColor = res.getColor(C0610R.color.mdtp_date_picker_text_highlighted_dark_theme);
        } else {
            this.mDayTextColor = res.getColor(C0610R.color.mdtp_date_picker_text_normal);
            this.mMonthDayTextColor = res.getColor(C0610R.color.mdtp_date_picker_month_day);
            this.mDisabledDayTextColor = res.getColor(C0610R.color.mdtp_date_picker_text_disabled);
            this.mHighlightedDayTextColor = res.getColor(C0610R.color.mdtp_date_picker_text_highlighted);
        }
        this.mSelectedDayTextColor = res.getColor(C0610R.color.mdtp_white);
        this.mTodayNumberColor = res.getColor(C0610R.color.mdtp_accent_color);
        this.mMonthTitleColor = res.getColor(C0610R.color.mdtp_white);
        this.mStringBuilder = new StringBuilder(50);
        MINI_DAY_NUMBER_TEXT_SIZE = res.getDimensionPixelSize(C0610R.dimen.mdtp_day_number_size);
        MONTH_LABEL_TEXT_SIZE = res.getDimensionPixelSize(C0610R.dimen.mdtp_month_label_size);
        MONTH_DAY_LABEL_TEXT_SIZE = res.getDimensionPixelSize(C0610R.dimen.mdtp_month_day_label_text_size);
        MONTH_HEADER_SIZE = res.getDimensionPixelOffset(C0610R.dimen.mdtp_month_list_item_header_height);
        DAY_SELECTED_CIRCLE_SIZE = res.getDimensionPixelSize(C0610R.dimen.mdtp_day_number_select_circle_radius);
        this.mRowHeight = (res.getDimensionPixelOffset(C0610R.dimen.mdtp_date_picker_view_animator_height) - getMonthHeaderSize()) / 6;
        this.mTouchHelper = getMonthViewTouchHelper();
        ViewCompat.setAccessibilityDelegate(this, this.mTouchHelper);
        ViewCompat.setImportantForAccessibility(this, 1);
        this.mLockAccessibilityDelegate = true;
        initView();
    }

    public void setDatePickerController(DatePickerController controller) {
        this.mController = controller;
    }

    protected MonthViewTouchHelper getMonthViewTouchHelper() {
        return new MonthViewTouchHelper(this);
    }

    public void setAccessibilityDelegate(AccessibilityDelegate delegate) {
        if (!this.mLockAccessibilityDelegate) {
            super.setAccessibilityDelegate(delegate);
        }
    }

    public void setOnDayClickListener(OnDayClickListener listener) {
        this.mOnDayClickListener = listener;
    }

    public boolean dispatchHoverEvent(@NonNull MotionEvent event) {
        if (this.mTouchHelper.dispatchHoverEvent(event)) {
            return true;
        }
        return super.dispatchHoverEvent(event);
    }

    public boolean onTouchEvent(@NonNull MotionEvent event) {
        switch (event.getAction()) {
            case 1:
                int day = getDayFromLocation(event.getX(), event.getY());
                if (day >= 0) {
                    onDayClick(day);
                    break;
                }
                break;
        }
        return true;
    }

    protected void initView() {
        this.mMonthTitlePaint = new Paint();
        this.mMonthTitlePaint.setFakeBoldText(true);
        this.mMonthTitlePaint.setAntiAlias(true);
        this.mMonthTitlePaint.setTextSize((float) MONTH_LABEL_TEXT_SIZE);
        this.mMonthTitlePaint.setTypeface(Typeface.create(this.mMonthTitleTypeface, 1));
        this.mMonthTitlePaint.setColor(this.mDayTextColor);
        this.mMonthTitlePaint.setTextAlign(Align.CENTER);
        this.mMonthTitlePaint.setStyle(Style.FILL);
        this.mSelectedCirclePaint = new Paint();
        this.mSelectedCirclePaint.setFakeBoldText(true);
        this.mSelectedCirclePaint.setAntiAlias(true);
        this.mSelectedCirclePaint.setColor(this.mTodayNumberColor);
        this.mSelectedCirclePaint.setTextAlign(Align.CENTER);
        this.mSelectedCirclePaint.setStyle(Style.FILL);
        this.mSelectedCirclePaint.setAlpha(255);
        this.mMonthDayLabelPaint = new Paint();
        this.mMonthDayLabelPaint.setAntiAlias(true);
        this.mMonthDayLabelPaint.setTextSize((float) MONTH_DAY_LABEL_TEXT_SIZE);
        this.mMonthDayLabelPaint.setColor(this.mMonthDayTextColor);
        this.mMonthDayLabelPaint.setTypeface(TypefaceHelper.get(getContext(), "Roboto-Medium"));
        this.mMonthDayLabelPaint.setStyle(Style.FILL);
        this.mMonthDayLabelPaint.setTextAlign(Align.CENTER);
        this.mMonthDayLabelPaint.setFakeBoldText(true);
        this.mMonthNumPaint = new Paint();
        this.mMonthNumPaint.setAntiAlias(true);
        this.mMonthNumPaint.setTextSize((float) MINI_DAY_NUMBER_TEXT_SIZE);
        this.mMonthNumPaint.setStyle(Style.FILL);
        this.mMonthNumPaint.setTextAlign(Align.CENTER);
        this.mMonthNumPaint.setFakeBoldText(false);
    }

    protected void onDraw(Canvas canvas) {
        drawMonthTitle(canvas);
        drawMonthDayLabels(canvas);
        drawMonthNums(canvas);
    }

    public void setMonthParams(HashMap<String, Integer> params) {
        if (params.containsKey(VIEW_PARAMS_MONTH) || params.containsKey(VIEW_PARAMS_YEAR)) {
            setTag(params);
            if (params.containsKey("height")) {
                this.mRowHeight = ((Integer) params.get("height")).intValue();
                if (this.mRowHeight < MIN_HEIGHT) {
                    this.mRowHeight = MIN_HEIGHT;
                }
            }
            if (params.containsKey(VIEW_PARAMS_SELECTED_DAY)) {
                this.mSelectedDay = ((Integer) params.get(VIEW_PARAMS_SELECTED_DAY)).intValue();
            }
            this.mMonth = ((Integer) params.get(VIEW_PARAMS_MONTH)).intValue();
            this.mYear = ((Integer) params.get(VIEW_PARAMS_YEAR)).intValue();
            PersianCalendar today = new PersianCalendar();
            this.mHasToday = false;
            this.mToday = -1;
            this.mPersianCalendar.setPersianDate(this.mYear, this.mMonth, 1);
            this.mDayOfWeekStart = this.mPersianCalendar.get(7);
            if (params.containsKey(VIEW_PARAMS_WEEK_START)) {
                this.mWeekStart = ((Integer) params.get(VIEW_PARAMS_WEEK_START)).intValue();
            } else {
                this.mWeekStart = 7;
            }
            this.mNumCells = Utils.getDaysInMonth(this.mMonth, this.mYear);
            for (int i = 0; i < this.mNumCells; i++) {
                int day = i + 1;
                if (sameDay(day, today)) {
                    this.mHasToday = true;
                    this.mToday = day;
                }
            }
            this.mNumRows = calculateNumRows();
            this.mTouchHelper.invalidateRoot();
            return;
        }
        throw new InvalidParameterException("You must specify month and year for this view");
    }

    public void setSelectedDay(int day) {
        this.mSelectedDay = day;
    }

    public void reuse() {
        this.mNumRows = 6;
        requestLayout();
    }

    private int calculateNumRows() {
        int offset = findDayOffset();
        return ((this.mNumCells + offset) % this.mNumDays > 0 ? 1 : 0) + ((this.mNumCells + offset) / this.mNumDays);
    }

    private boolean sameDay(int day, PersianCalendar today) {
        return this.mYear == today.getPersianYear() && this.mMonth == today.getPersianMonth() && day == today.getPersianDay();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), ((this.mRowHeight * this.mNumRows) + getMonthHeaderSize()) + 5);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.mWidth = w;
        this.mTouchHelper.invalidateRoot();
    }

    public int getMonth() {
        return this.mMonth;
    }

    public int getYear() {
        return this.mYear;
    }

    protected int getMonthHeaderSize() {
        return MONTH_HEADER_SIZE;
    }

    private String getMonthAndYearString() {
        this.mStringBuilder.setLength(0);
        return LanguageUtils.getPersianNumbers(this.mPersianCalendar.getPersianMonthName() + " " + this.mPersianCalendar.getPersianYear());
    }

    protected void drawMonthTitle(Canvas canvas) {
        canvas.drawText(getMonthAndYearString(), (float) ((this.mWidth + (this.mEdgePadding * 2)) / 2), (float) ((getMonthHeaderSize() - MONTH_DAY_LABEL_TEXT_SIZE) / 2), this.mMonthTitlePaint);
    }

    protected void drawMonthDayLabels(Canvas canvas) {
        int y = getMonthHeaderSize() - (MONTH_DAY_LABEL_TEXT_SIZE / 2);
        int dayWidthHalf = (this.mWidth - (this.mEdgePadding * 2)) / (this.mNumDays * 2);
        for (int i = 0; i < this.mNumDays; i++) {
            int x = (((i * 2) + 1) * dayWidthHalf) + this.mEdgePadding;
            this.mDayLabelCalendar.set(7, (this.mWeekStart + i) % this.mNumDays);
            canvas.drawText(this.mDayLabelCalendar.getPersianWeekDayName().substring(0, 1), (float) x, (float) y, this.mMonthDayLabelPaint);
        }
    }

    protected void drawMonthNums(Canvas canvas) {
        int y = (((this.mRowHeight + MINI_DAY_NUMBER_TEXT_SIZE) / 2) - DAY_SEPARATOR_WIDTH) + getMonthHeaderSize();
        float dayWidthHalf = ((float) (this.mWidth - (this.mEdgePadding * 2))) / (((float) this.mNumDays) * 2.0f);
        int j = findDayOffset();
        for (int dayNumber = 1; dayNumber <= this.mNumCells; dayNumber++) {
            int x = (int) ((((float) ((j * 2) + 1)) * dayWidthHalf) + ((float) this.mEdgePadding));
            int startY = y - (((this.mRowHeight + MINI_DAY_NUMBER_TEXT_SIZE) / 2) - DAY_SEPARATOR_WIDTH);
            Canvas canvas2 = canvas;
            drawMonthDay(canvas2, this.mYear, this.mMonth, dayNumber, x, y, (int) (((float) x) - dayWidthHalf), (int) (((float) x) + dayWidthHalf), startY, startY + this.mRowHeight);
            j++;
            if (j == this.mNumDays) {
                j = 0;
                y += this.mRowHeight;
            }
        }
    }

    protected int findDayOffset() {
        return (this.mDayOfWeekStart < this.mWeekStart ? this.mDayOfWeekStart + this.mNumDays : this.mDayOfWeekStart) - this.mWeekStart;
    }

    public int getDayFromLocation(float x, float y) {
        int day = getInternalDayFromLocation(x, y);
        if (day < 1 || day > this.mNumCells) {
            return -1;
        }
        return day;
    }

    protected int getInternalDayFromLocation(float x, float y) {
        int dayStart = this.mEdgePadding;
        if (x < ((float) dayStart) || x > ((float) (this.mWidth - this.mEdgePadding))) {
            return -1;
        }
        return ((((int) (((x - ((float) dayStart)) * ((float) this.mNumDays)) / ((float) ((this.mWidth - dayStart) - this.mEdgePadding)))) - findDayOffset()) + 1) + (this.mNumDays * (((int) (y - ((float) getMonthHeaderSize()))) / this.mRowHeight));
    }

    private void onDayClick(int day) {
        if (!isOutOfRange(this.mYear, this.mMonth, day)) {
            if (this.mOnDayClickListener != null) {
                this.mOnDayClickListener.onDayClick(this, new CalendarDay(this.mYear, this.mMonth, day));
            }
            this.mTouchHelper.sendEventForVirtualView(day, 1);
        }
    }

    protected boolean isOutOfRange(int year, int month, int day) {
        if (this.mController.getSelectableDays() != null) {
            if (isSelectable(year, month, day)) {
                return false;
            }
            return true;
        } else if (isBeforeMin(year, month, day) || isAfterMax(year, month, day)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isSelectable(int year, int month, int day) {
        for (PersianCalendar c : this.mController.getSelectableDays()) {
            if (year < c.getPersianYear()) {
                break;
            }
            if (year <= c.getPersianYear()) {
                if (month >= c.getPersianMonth()) {
                    if (month <= c.getPersianMonth()) {
                        if (day < c.getPersianDay()) {
                            break;
                        } else if (day <= c.getPersianDay()) {
                            return true;
                        }
                    } else {
                        continue;
                    }
                } else {
                    break;
                }
            }
        }
        return false;
    }

    private boolean isBeforeMin(int year, int month, int day) {
        if (this.mController == null) {
            return false;
        }
        PersianCalendar minDate = this.mController.getMinDate();
        if (minDate == null) {
            return false;
        }
        if (year < minDate.getPersianYear()) {
            return true;
        }
        if (year > minDate.getPersianYear()) {
            return false;
        }
        if (month < minDate.getPersianMonth()) {
            return true;
        }
        if (month > minDate.getPersianMonth() || day >= minDate.getPersianDay()) {
            return false;
        }
        return true;
    }

    private boolean isAfterMax(int year, int month, int day) {
        if (this.mController == null) {
            return false;
        }
        PersianCalendar maxDate = this.mController.getMaxDate();
        if (maxDate == null) {
            return false;
        }
        if (year > maxDate.getPersianYear()) {
            return true;
        }
        if (year < maxDate.getPersianYear()) {
            return false;
        }
        if (month > maxDate.getPersianMonth()) {
            return true;
        }
        if (month < maxDate.getPersianMonth() || day <= maxDate.getPersianMonth()) {
            return false;
        }
        return true;
    }

    protected boolean isHighlighted(int year, int month, int day) {
        PersianCalendar[] highlightedDays = this.mController.getHighlightedDays();
        if (highlightedDays == null) {
            return false;
        }
        for (PersianCalendar c : highlightedDays) {
            if (year < c.getPersianYear()) {
                return false;
            }
            if (year <= c.getPersianYear()) {
                if (month < c.getPersianMonth()) {
                    return false;
                }
                if (month > c.getPersianMonth()) {
                    continue;
                } else if (day < c.getPersianDay()) {
                    return false;
                } else {
                    if (day <= c.getPersianDay()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public CalendarDay getAccessibilityFocus() {
        int day = this.mTouchHelper.getFocusedVirtualView();
        if (day >= 0) {
            return new CalendarDay(this.mYear, this.mMonth, day);
        }
        return null;
    }

    public void clearAccessibilityFocus() {
        this.mTouchHelper.clearFocusedVirtualView();
    }

    public boolean restoreAccessibilityFocus(CalendarDay day) {
        if (day.year != this.mYear || day.month != this.mMonth || day.day > this.mNumCells) {
            return false;
        }
        this.mTouchHelper.setFocusedVirtualView(day.day);
        return true;
    }
}
