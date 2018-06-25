package com.mohamadamin.persianmaterialdatetimepicker.date;

import android.content.Context;

public class SimpleMonthAdapter extends MonthAdapter {
    public SimpleMonthAdapter(Context context, DatePickerController controller) {
        super(context, controller);
    }

    public MonthView createMonthView(Context context) {
        return new SimpleMonthView(context, null, this.mController);
    }
}
