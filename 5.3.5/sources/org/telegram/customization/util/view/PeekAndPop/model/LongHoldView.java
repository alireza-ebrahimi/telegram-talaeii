package org.telegram.customization.util.view.PeekAndPop.model;

import android.support.annotation.NonNull;
import android.view.View;
import java.util.Timer;
import org.telegram.customization.util.view.PeekAndPop.PeekAndPop;

public class LongHoldView {
    protected Timer longHoldTimer;
    private boolean receiveMultipleEvents;
    private View view;

    public LongHoldView(View view, boolean receiveMultipleEvents) {
        this.view = view;
        this.receiveMultipleEvents = receiveMultipleEvents;
    }

    public void startLongHoldViewTimer(@NonNull PeekAndPop peekAndPop, int position, long duration) {
        Timer longHoldTimer = new Timer();
        longHoldTimer.schedule(new LongHoldView$1(this, peekAndPop, position, duration), duration);
        this.longHoldTimer = longHoldTimer;
    }

    public View getView() {
        return this.view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public boolean isReceiveMultipleEvents() {
        return this.receiveMultipleEvents;
    }

    public void setReceiveMultipleEvents(boolean receiveMultipleEvents) {
        this.receiveMultipleEvents = receiveMultipleEvents;
    }

    public Timer getLongHoldTimer() {
        return this.longHoldTimer;
    }

    public void setLongHoldTimer(Timer longHoldTimer) {
        this.longHoldTimer = longHoldTimer;
    }
}
