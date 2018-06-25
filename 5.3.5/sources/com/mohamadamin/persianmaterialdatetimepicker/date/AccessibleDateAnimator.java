package com.mohamadamin.persianmaterialdatetimepicker.date;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ViewAnimator;
import com.mohamadamin.persianmaterialdatetimepicker.utils.LanguageUtils;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

public class AccessibleDateAnimator extends ViewAnimator {
    private long mDateMillis;

    public AccessibleDateAnimator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDateMillis(long dateMillis) {
        this.mDateMillis = dateMillis;
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() != 32) {
            return super.dispatchPopulateAccessibilityEvent(event);
        }
        event.getText().clear();
        PersianCalendar mPersianCalendar = new PersianCalendar();
        mPersianCalendar.setTimeInMillis(this.mDateMillis);
        event.getText().add(LanguageUtils.getPersianNumbers(mPersianCalendar.getPersianMonthName() + " " + mPersianCalendar.getPersianYear()));
        return true;
    }
}
