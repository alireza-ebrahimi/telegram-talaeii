package com.mohamadamin.persianmaterialdatetimepicker.date;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog.OnDateChangedListener;
import com.mohamadamin.persianmaterialdatetimepicker.date.MonthAdapter.CalendarDay;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

public interface DatePickerController {
    int getFirstDayOfWeek();

    PersianCalendar[] getHighlightedDays();

    PersianCalendar getMaxDate();

    int getMaxYear();

    PersianCalendar getMinDate();

    int getMinYear();

    PersianCalendar[] getSelectableDays();

    CalendarDay getSelectedDay();

    boolean isThemeDark();

    void onDayOfMonthSelected(int i, int i2, int i3);

    void onYearSelected(int i);

    void registerOnDateChangedListener(OnDateChangedListener onDateChangedListener);

    void tryVibrate();

    void unregisterOnDateChangedListener(OnDateChangedListener onDateChangedListener);
}
