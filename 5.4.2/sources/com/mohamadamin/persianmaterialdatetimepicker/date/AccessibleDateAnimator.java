package com.mohamadamin.persianmaterialdatetimepicker.date;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ViewAnimator;
import com.mohamadamin.persianmaterialdatetimepicker.p121a.C2017a;
import com.mohamadamin.persianmaterialdatetimepicker.p121a.C2018b;

public class AccessibleDateAnimator extends ViewAnimator {
    /* renamed from: a */
    private long f5952a;

    public AccessibleDateAnimator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() != 32) {
            return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
        }
        accessibilityEvent.getText().clear();
        C2018b c2018b = new C2018b();
        c2018b.setTimeInMillis(this.f5952a);
        accessibilityEvent.getText().add(C2017a.m9087a(c2018b.m9097d() + " " + c2018b.m9095b()));
        return true;
    }

    public void setDateMillis(long j) {
        this.f5952a = j;
    }
}
