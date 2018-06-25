package org.telegram.customization.util.view;

import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class DoubleClickListener implements OnClickListener {
    private static final long DEFAULT_QUALIFICATION_SPAN = 200;
    private long doubleClickQualificationSpanInMillis;
    private long timestampLastClick;

    public abstract void onDoubleClick();

    public DoubleClickListener() {
        this.doubleClickQualificationSpanInMillis = 200;
        this.timestampLastClick = 0;
    }

    public DoubleClickListener(long doubleClickQualificationSpanInMillis) {
        this.doubleClickQualificationSpanInMillis = doubleClickQualificationSpanInMillis;
        this.timestampLastClick = 0;
    }

    public void onClick(View v) {
        if (SystemClock.elapsedRealtime() - this.timestampLastClick < this.doubleClickQualificationSpanInMillis) {
            onDoubleClick();
        }
        this.timestampLastClick = SystemClock.elapsedRealtime();
    }
}
