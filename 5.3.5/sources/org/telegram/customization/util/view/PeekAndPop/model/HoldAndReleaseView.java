package org.telegram.customization.util.view.PeekAndPop.model;

import android.support.annotation.NonNull;
import android.view.View;
import java.util.Timer;
import org.telegram.customization.util.view.PeekAndPop.PeekAndPop;

public class HoldAndReleaseView {
    protected Timer holdAndReleaseTimer = new Timer();
    private int position = -1;
    private View view;

    public HoldAndReleaseView(View view) {
        this.view = view;
    }

    public void startHoldAndReleaseTimer(@NonNull PeekAndPop peekAndPop, int position, long duration) {
        Timer holdAndReleaseTimer = new Timer();
        this.position = position;
        holdAndReleaseTimer.schedule(new HoldAndReleaseView$1(this, peekAndPop, position), duration);
        this.holdAndReleaseTimer = holdAndReleaseTimer;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public View getView() {
        return this.view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Timer getHoldAndReleaseTimer() {
        return this.holdAndReleaseTimer;
    }

    public void setHoldAndReleaseTimer(Timer holdAndReleaseTimer) {
        this.holdAndReleaseTimer = holdAndReleaseTimer;
    }
}
