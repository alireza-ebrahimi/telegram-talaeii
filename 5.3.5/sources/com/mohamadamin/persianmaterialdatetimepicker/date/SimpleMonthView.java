package com.mohamadamin.persianmaterialdatetimepicker.date;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import com.mohamadamin.persianmaterialdatetimepicker.utils.LanguageUtils;

public class SimpleMonthView extends MonthView {
    public SimpleMonthView(Context context, AttributeSet attr, DatePickerController controller) {
        super(context, attr, controller);
    }

    public void drawMonthDay(Canvas canvas, int year, int month, int day, int x, int y, int startX, int stopX, int startY, int stopY) {
        if (this.mSelectedDay == day) {
            canvas.drawCircle((float) x, (float) (y - (MINI_DAY_NUMBER_TEXT_SIZE / 3)), (float) DAY_SELECTED_CIRCLE_SIZE, this.mSelectedCirclePaint);
        }
        if (isHighlighted(year, month, day)) {
            this.mMonthNumPaint.setTypeface(Typeface.create(Typeface.DEFAULT, 1));
        } else {
            this.mMonthNumPaint.setTypeface(Typeface.create(Typeface.DEFAULT, 0));
        }
        if (isOutOfRange(year, month, day)) {
            this.mMonthNumPaint.setColor(this.mDisabledDayTextColor);
        } else if (this.mSelectedDay == day) {
            this.mMonthNumPaint.setColor(this.mSelectedDayTextColor);
        } else if (this.mHasToday && this.mToday == day) {
            this.mMonthNumPaint.setColor(this.mTodayNumberColor);
        } else {
            this.mMonthNumPaint.setColor(isHighlighted(year, month, day) ? this.mHighlightedDayTextColor : this.mDayTextColor);
        }
        canvas.drawText(LanguageUtils.getPersianNumbers(String.format("%d", new Object[]{Integer.valueOf(day)})), (float) x, (float) y, this.mMonthNumPaint);
    }
}
