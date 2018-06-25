package com.mohamadamin.persianmaterialdatetimepicker.date;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.mohamadamin.persianmaterialdatetimepicker.C0610R;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog.OnDateChangedListener;
import com.mohamadamin.persianmaterialdatetimepicker.utils.LanguageUtils;
import java.util.ArrayList;
import java.util.List;

public class YearPickerView extends ListView implements OnItemClickListener, OnDateChangedListener {
    private static final String TAG = "YearPickerView";
    private YearAdapter mAdapter;
    private int mChildSize;
    private final DatePickerController mController;
    private TextViewWithCircularIndicator mSelectedView;
    private int mViewSize;

    private class YearAdapter extends ArrayAdapter<String> {
        public YearAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            TextViewWithCircularIndicator v = (TextViewWithCircularIndicator) super.getView(position, convertView, parent);
            v.requestLayout();
            boolean selected = YearPickerView.this.mController.getSelectedDay().year == YearPickerView.getYearFromTextView(v);
            v.drawIndicator(selected);
            if (selected) {
                YearPickerView.this.mSelectedView = v;
            }
            return v;
        }
    }

    public YearPickerView(Context context, DatePickerController controller) {
        super(context);
        this.mController = controller;
        this.mController.registerOnDateChangedListener(this);
        setLayoutParams(new LayoutParams(-1, -2));
        Resources res = context.getResources();
        this.mViewSize = res.getDimensionPixelOffset(C0610R.dimen.mdtp_date_picker_view_animator_height);
        this.mChildSize = res.getDimensionPixelOffset(C0610R.dimen.mdtp_year_label_height);
        setVerticalFadingEdgeEnabled(true);
        setFadingEdgeLength(this.mChildSize / 3);
        init(context);
        setOnItemClickListener(this);
        setSelector(new StateListDrawable());
        setDividerHeight(0);
        onDateChanged();
    }

    private void init(Context context) {
        ArrayList years = new ArrayList();
        for (int year = this.mController.getMinYear(); year <= this.mController.getMaxYear(); year++) {
            years.add(String.format("%d", new Object[]{Integer.valueOf(year)}));
        }
        this.mAdapter = new YearAdapter(context, C0610R.layout.mdtp_year_label_text_view, LanguageUtils.getPersianNumbers(years));
        setAdapter(this.mAdapter);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        this.mController.tryVibrate();
        TextViewWithCircularIndicator clickedView = (TextViewWithCircularIndicator) view;
        if (clickedView != null) {
            if (clickedView != this.mSelectedView) {
                if (this.mSelectedView != null) {
                    this.mSelectedView.drawIndicator(false);
                    this.mSelectedView.requestLayout();
                }
                clickedView.drawIndicator(true);
                clickedView.requestLayout();
                this.mSelectedView = clickedView;
            }
            this.mController.onYearSelected(getYearFromTextView(clickedView));
            this.mAdapter.notifyDataSetChanged();
        }
    }

    private static int getYearFromTextView(TextView view) {
        return Integer.valueOf(LanguageUtils.getLatinNumbers(view.getText().toString())).intValue();
    }

    public void postSetSelectionCentered(int position) {
        postSetSelectionFromTop(position, (this.mViewSize / 2) - (this.mChildSize / 2));
    }

    public void postSetSelectionFromTop(final int position, final int offset) {
        post(new Runnable() {
            public void run() {
                YearPickerView.this.setSelectionFromTop(position, offset);
                YearPickerView.this.requestLayout();
            }
        });
    }

    public int getFirstPositionOffset() {
        View firstChild = getChildAt(0);
        if (firstChild == null) {
            return 0;
        }
        return firstChild.getTop();
    }

    public void onDateChanged() {
        this.mAdapter.notifyDataSetChanged();
        postSetSelectionCentered(this.mController.getSelectedDay().year - this.mController.getMinYear());
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        if (event.getEventType() == 4096) {
            event.setFromIndex(0);
            event.setToIndex(0);
        }
    }
}
