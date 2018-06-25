package com.mohamadamin.persianmaterialdatetimepicker.date;

import android.animation.ObjectAnimator;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mohamadamin.persianmaterialdatetimepicker.C0610R;
import com.mohamadamin.persianmaterialdatetimepicker.HapticFeedbackController;
import com.mohamadamin.persianmaterialdatetimepicker.TypefaceHelper;
import com.mohamadamin.persianmaterialdatetimepicker.Utils;
import com.mohamadamin.persianmaterialdatetimepicker.date.MonthAdapter.CalendarDay;
import com.mohamadamin.persianmaterialdatetimepicker.utils.LanguageUtils;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

public class DatePickerDialog extends DialogFragment implements OnClickListener, DatePickerController {
    private static final int ANIMATION_DELAY = 500;
    private static final int ANIMATION_DURATION = 300;
    private static final int DEFAULT_END_YEAR = 1450;
    private static final int DEFAULT_START_YEAR = 1350;
    private static final String KEY_CURRENT_VIEW = "current_view";
    private static final String KEY_HIGHLIGHTED_DAYS = "highlighted_days";
    private static final String KEY_LIST_POSITION = "list_position";
    private static final String KEY_LIST_POSITION_OFFSET = "list_position_offset";
    private static final String KEY_MAX_DATE = "max_date";
    private static final String KEY_MIN_DATE = "min_date";
    private static final String KEY_SELECTABLE_DAYS = "selectable_days";
    private static final String KEY_SELECTED_DAY = "day";
    private static final String KEY_SELECTED_MONTH = "month";
    private static final String KEY_SELECTED_YEAR = "year";
    private static final String KEY_THEME_DARK = "theme_dark";
    private static final String KEY_WEEK_START = "week_start";
    private static final String KEY_YEAR_END = "year_end";
    private static final String KEY_YEAR_START = "year_start";
    private static final int MONTH_AND_DAY_VIEW = 0;
    private static final String TAG = "DatePickerDialog";
    private static final int UNINITIALIZED = -1;
    private static final int YEAR_VIEW = 1;
    private PersianCalendar[] highlightedDays;
    private AccessibleDateAnimator mAnimator;
    private OnDateSetListener mCallBack;
    private int mCurrentView = -1;
    private TextView mDayOfWeekView;
    private String mDayPickerDescription;
    private DayPickerView mDayPickerView;
    private boolean mDelayAnimation = true;
    private HapticFeedbackController mHapticFeedbackController;
    private HashSet<OnDateChangedListener> mListeners = new HashSet();
    private PersianCalendar mMaxDate;
    private int mMaxYear = DEFAULT_END_YEAR;
    private PersianCalendar mMinDate;
    private int mMinYear = DEFAULT_START_YEAR;
    private LinearLayout mMonthAndDayView;
    private OnCancelListener mOnCancelListener;
    private OnDismissListener mOnDismissListener;
    private final PersianCalendar mPersianCalendar = new PersianCalendar();
    private String mSelectDay;
    private String mSelectYear;
    private TextView mSelectedDayTextView;
    private TextView mSelectedMonthTextView;
    private boolean mThemeDark;
    private int mWeekStart = 7;
    private String mYearPickerDescription;
    private YearPickerView mYearPickerView;
    private TextView mYearView;
    private PersianCalendar[] selectableDays;

    /* renamed from: com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog$1 */
    class C06111 implements OnClickListener {
        C06111() {
        }

        public void onClick(View v) {
            DatePickerDialog.this.tryVibrate();
            if (DatePickerDialog.this.mCallBack != null) {
                DatePickerDialog.this.mCallBack.onDateSet(DatePickerDialog.this, DatePickerDialog.this.mPersianCalendar.getPersianYear(), DatePickerDialog.this.mPersianCalendar.getPersianMonth(), DatePickerDialog.this.mPersianCalendar.getPersianDay());
            }
            DatePickerDialog.this.dismiss();
        }
    }

    /* renamed from: com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog$2 */
    class C06122 implements OnClickListener {
        C06122() {
        }

        public void onClick(View v) {
            DatePickerDialog.this.tryVibrate();
            DatePickerDialog.this.getDialog().cancel();
        }
    }

    public interface OnDateChangedListener {
        void onDateChanged();
    }

    public interface OnDateSetListener {
        void onDateSet(DatePickerDialog datePickerDialog, int i, int i2, int i3);
    }

    public static DatePickerDialog newInstance(OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        DatePickerDialog ret = new DatePickerDialog();
        ret.initialize(callBack, year, monthOfYear, dayOfMonth);
        return ret;
    }

    public void initialize(OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        this.mCallBack = callBack;
        this.mPersianCalendar.setPersianDate(year, monthOfYear, dayOfMonth);
        this.mThemeDark = false;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(3);
        if (savedInstanceState != null) {
            this.mPersianCalendar.setPersianDate(savedInstanceState.getInt("year"), savedInstanceState.getInt("month"), savedInstanceState.getInt(KEY_SELECTED_DAY));
        }
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("year", this.mPersianCalendar.getPersianYear());
        outState.putInt("month", this.mPersianCalendar.getPersianMonth());
        outState.putInt(KEY_SELECTED_DAY, this.mPersianCalendar.getPersianDay());
        outState.putInt("week_start", this.mWeekStart);
        outState.putInt(KEY_YEAR_START, this.mMinYear);
        outState.putInt(KEY_YEAR_END, this.mMaxYear);
        outState.putInt(KEY_CURRENT_VIEW, this.mCurrentView);
        int listPosition = -1;
        if (this.mCurrentView == 0) {
            listPosition = this.mDayPickerView.getMostVisiblePosition();
        } else if (this.mCurrentView == 1) {
            listPosition = this.mYearPickerView.getFirstVisiblePosition();
            outState.putInt(KEY_LIST_POSITION_OFFSET, this.mYearPickerView.getFirstPositionOffset());
        }
        outState.putInt(KEY_LIST_POSITION, listPosition);
        outState.putSerializable(KEY_MIN_DATE, this.mMinDate);
        outState.putSerializable(KEY_MAX_DATE, this.mMaxDate);
        outState.putSerializable(KEY_HIGHLIGHTED_DAYS, this.highlightedDays);
        outState.putSerializable(KEY_SELECTABLE_DAYS, this.selectableDays);
        outState.putBoolean(KEY_THEME_DARK, this.mThemeDark);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        getDialog().getWindow().requestFeature(1);
        View view = inflater.inflate(C0610R.layout.mdtp_date_picker_dialog, null);
        this.mDayOfWeekView = (TextView) view.findViewById(C0610R.id.date_picker_header);
        this.mMonthAndDayView = (LinearLayout) view.findViewById(C0610R.id.date_picker_month_and_day);
        this.mMonthAndDayView.setOnClickListener(this);
        this.mSelectedMonthTextView = (TextView) view.findViewById(C0610R.id.date_picker_month);
        this.mSelectedDayTextView = (TextView) view.findViewById(C0610R.id.date_picker_day);
        this.mYearView = (TextView) view.findViewById(C0610R.id.date_picker_year);
        this.mYearView.setOnClickListener(this);
        int listPosition = -1;
        int listPositionOffset = 0;
        int currentView = 0;
        if (savedInstanceState != null) {
            this.mWeekStart = savedInstanceState.getInt("week_start");
            this.mMinYear = savedInstanceState.getInt(KEY_YEAR_START);
            this.mMaxYear = savedInstanceState.getInt(KEY_YEAR_END);
            currentView = savedInstanceState.getInt(KEY_CURRENT_VIEW);
            listPosition = savedInstanceState.getInt(KEY_LIST_POSITION);
            listPositionOffset = savedInstanceState.getInt(KEY_LIST_POSITION_OFFSET);
            this.mMinDate = (PersianCalendar) savedInstanceState.getSerializable(KEY_MIN_DATE);
            this.mMaxDate = (PersianCalendar) savedInstanceState.getSerializable(KEY_MAX_DATE);
            this.highlightedDays = (PersianCalendar[]) savedInstanceState.getSerializable(KEY_HIGHLIGHTED_DAYS);
            this.selectableDays = (PersianCalendar[]) savedInstanceState.getSerializable(KEY_SELECTABLE_DAYS);
            this.mThemeDark = savedInstanceState.getBoolean(KEY_THEME_DARK);
        }
        Context activity = getActivity();
        this.mDayPickerView = new SimpleDayPickerView(activity, (DatePickerController) this);
        this.mYearPickerView = new YearPickerView(activity, this);
        Resources res = getResources();
        this.mDayPickerDescription = res.getString(C0610R.string.mdtp_day_picker_description);
        this.mSelectDay = res.getString(C0610R.string.mdtp_select_day);
        this.mYearPickerDescription = res.getString(C0610R.string.mdtp_year_picker_description);
        this.mSelectYear = res.getString(C0610R.string.mdtp_select_year);
        view.setBackgroundColor(activity.getResources().getColor(this.mThemeDark ? C0610R.color.mdtp_date_picker_view_animator_dark_theme : C0610R.color.mdtp_date_picker_view_animator));
        this.mAnimator = (AccessibleDateAnimator) view.findViewById(C0610R.id.animator);
        this.mAnimator.addView(this.mDayPickerView);
        this.mAnimator.addView(this.mYearPickerView);
        this.mAnimator.setDateMillis(this.mPersianCalendar.getTimeInMillis());
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(300);
        this.mAnimator.setInAnimation(animation);
        Animation animation2 = new AlphaAnimation(1.0f, 0.0f);
        animation2.setDuration(300);
        this.mAnimator.setOutAnimation(animation2);
        Button okButton = (Button) view.findViewById(C0610R.id.ok);
        okButton.setOnClickListener(new C06111());
        okButton.setTypeface(TypefaceHelper.get(activity, "Roboto-Medium"));
        Button cancelButton = (Button) view.findViewById(C0610R.id.cancel);
        cancelButton.setOnClickListener(new C06122());
        cancelButton.setTypeface(TypefaceHelper.get(activity, "Roboto-Medium"));
        cancelButton.setVisibility(isCancelable() ? 0 : 8);
        updateDisplay(false);
        setCurrentView(currentView);
        if (listPosition != -1) {
            if (currentView == 0) {
                this.mDayPickerView.postSetSelection(listPosition);
            } else if (currentView == 1) {
                this.mYearPickerView.postSetSelectionFromTop(listPosition, listPositionOffset);
            }
        }
        this.mHapticFeedbackController = new HapticFeedbackController(activity);
        return view;
    }

    public void onResume() {
        super.onResume();
        this.mHapticFeedbackController.start();
    }

    public void onPause() {
        super.onPause();
        this.mHapticFeedbackController.stop();
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (this.mOnCancelListener != null) {
            this.mOnCancelListener.onCancel(dialog);
        }
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (this.mOnDismissListener != null) {
            this.mOnDismissListener.onDismiss(dialog);
        }
    }

    private void setCurrentView(int viewIndex) {
        ObjectAnimator pulseAnimator;
        switch (viewIndex) {
            case 0:
                pulseAnimator = Utils.getPulseAnimator(this.mMonthAndDayView, 0.9f, 1.05f);
                if (this.mDelayAnimation) {
                    pulseAnimator.setStartDelay(500);
                    this.mDelayAnimation = false;
                }
                this.mDayPickerView.onDateChanged();
                if (this.mCurrentView != viewIndex) {
                    this.mMonthAndDayView.setSelected(true);
                    this.mYearView.setSelected(false);
                    this.mAnimator.setDisplayedChild(0);
                    this.mCurrentView = viewIndex;
                }
                pulseAnimator.start();
                this.mAnimator.setContentDescription(this.mDayPickerDescription + ": " + LanguageUtils.getPersianNumbers(this.mPersianCalendar.getPersianLongDate()));
                Utils.tryAccessibilityAnnounce(this.mAnimator, this.mSelectDay);
                return;
            case 1:
                pulseAnimator = Utils.getPulseAnimator(this.mYearView, 0.85f, 1.1f);
                if (this.mDelayAnimation) {
                    pulseAnimator.setStartDelay(500);
                    this.mDelayAnimation = false;
                }
                this.mYearPickerView.onDateChanged();
                if (this.mCurrentView != viewIndex) {
                    this.mMonthAndDayView.setSelected(false);
                    this.mYearView.setSelected(true);
                    this.mAnimator.setDisplayedChild(1);
                    this.mCurrentView = viewIndex;
                }
                pulseAnimator.start();
                this.mAnimator.setContentDescription(this.mYearPickerDescription + ": " + LanguageUtils.getPersianNumbers(String.valueOf(this.mPersianCalendar.getPersianYear())));
                Utils.tryAccessibilityAnnounce(this.mAnimator, this.mSelectYear);
                return;
            default:
                return;
        }
    }

    private void updateDisplay(boolean announce) {
        if (this.mDayOfWeekView != null) {
            this.mDayOfWeekView.setText(this.mPersianCalendar.getPersianWeekDayName());
        }
        this.mSelectedMonthTextView.setText(LanguageUtils.getPersianNumbers(this.mPersianCalendar.getPersianMonthName()));
        this.mSelectedDayTextView.setText(LanguageUtils.getPersianNumbers(String.valueOf(this.mPersianCalendar.getPersianDay())));
        this.mYearView.setText(LanguageUtils.getPersianNumbers(String.valueOf(this.mPersianCalendar.getPersianYear())));
        this.mAnimator.setDateMillis(this.mPersianCalendar.getTimeInMillis());
        this.mMonthAndDayView.setContentDescription(LanguageUtils.getPersianNumbers(this.mPersianCalendar.getPersianMonthName() + " " + this.mPersianCalendar.getPersianDay()));
        if (announce) {
            Utils.tryAccessibilityAnnounce(this.mAnimator, LanguageUtils.getPersianNumbers(this.mPersianCalendar.getPersianLongDate()));
        }
    }

    public void setThemeDark(boolean themeDark) {
        this.mThemeDark = themeDark;
    }

    public boolean isThemeDark() {
        return this.mThemeDark;
    }

    public void setFirstDayOfWeek(int startOfWeek) {
        if (startOfWeek < 1 || startOfWeek > 7) {
            throw new IllegalArgumentException("Value must be between Calendar.SUNDAY and Calendar.SATURDAY");
        }
        this.mWeekStart = startOfWeek;
        if (this.mDayPickerView != null) {
            this.mDayPickerView.onChange();
        }
    }

    public void setYearRange(int startYear, int endYear) {
        if (endYear < startYear) {
            throw new IllegalArgumentException("Year end must be larger than or equal to year start");
        }
        this.mMinYear = startYear;
        this.mMaxYear = endYear;
        if (this.mDayPickerView != null) {
            this.mDayPickerView.onChange();
        }
    }

    public void setMinDate(PersianCalendar calendar) {
        this.mMinDate = calendar;
        if (this.mDayPickerView != null) {
            this.mDayPickerView.onChange();
        }
    }

    public PersianCalendar getMinDate() {
        return this.mMinDate;
    }

    public void setMaxDate(PersianCalendar calendar) {
        this.mMaxDate = calendar;
        if (this.mDayPickerView != null) {
            this.mDayPickerView.onChange();
        }
    }

    public PersianCalendar getMaxDate() {
        return this.mMaxDate;
    }

    public void setHighlightedDays(PersianCalendar[] highlightedDays) {
        Arrays.sort(highlightedDays);
        this.highlightedDays = highlightedDays;
    }

    public PersianCalendar[] getHighlightedDays() {
        return this.highlightedDays;
    }

    public void setSelectableDays(PersianCalendar[] selectableDays) {
        Arrays.sort(selectableDays);
        this.selectableDays = selectableDays;
    }

    public PersianCalendar[] getSelectableDays() {
        return this.selectableDays;
    }

    public void setOnDateSetListener(OnDateSetListener listener) {
        this.mCallBack = listener;
    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        this.mOnCancelListener = onCancelListener;
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    private void adjustDayInMonthIfNeeded(int month, int year) {
    }

    public void onClick(View v) {
        tryVibrate();
        if (v.getId() == C0610R.id.date_picker_year) {
            setCurrentView(1);
        } else if (v.getId() == C0610R.id.date_picker_month_and_day) {
            setCurrentView(0);
        }
    }

    public void onYearSelected(int year) {
        adjustDayInMonthIfNeeded(this.mPersianCalendar.getPersianMonth(), year);
        this.mPersianCalendar.setPersianDate(year, this.mPersianCalendar.getPersianMonth(), this.mPersianCalendar.getPersianDay());
        updatePickers();
        setCurrentView(0);
        updateDisplay(true);
    }

    public void onDayOfMonthSelected(int year, int month, int day) {
        this.mPersianCalendar.setPersianDate(year, month, day);
        updatePickers();
        updateDisplay(true);
    }

    private void updatePickers() {
        Iterator i$ = this.mListeners.iterator();
        while (i$.hasNext()) {
            ((OnDateChangedListener) i$.next()).onDateChanged();
        }
    }

    public CalendarDay getSelectedDay() {
        return new CalendarDay(this.mPersianCalendar);
    }

    public int getMinYear() {
        if (this.selectableDays != null) {
            return this.selectableDays[0].getPersianYear();
        }
        return (this.mMinDate == null || this.mMinDate.getPersianYear() <= this.mMinYear) ? this.mMinYear : this.mMinDate.getPersianYear();
    }

    public int getMaxYear() {
        if (this.selectableDays != null) {
            return this.selectableDays[this.selectableDays.length - 1].getPersianYear();
        }
        return (this.mMaxDate == null || this.mMaxDate.getPersianYear() >= this.mMaxYear) ? this.mMaxYear : this.mMaxDate.getPersianYear();
    }

    public int getFirstDayOfWeek() {
        return this.mWeekStart;
    }

    public void registerOnDateChangedListener(OnDateChangedListener listener) {
        this.mListeners.add(listener);
    }

    public void unregisterOnDateChangedListener(OnDateChangedListener listener) {
        this.mListeners.remove(listener);
    }

    public void tryVibrate() {
        this.mHapticFeedbackController.tryVibrate();
    }
}
