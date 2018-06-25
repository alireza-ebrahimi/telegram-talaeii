package com.h6ah4i.android.widget.advrecyclerview.swipeable.action;

public abstract class SwipeResultAction {
    private final int mResultAction;

    protected SwipeResultAction(int resultAction) {
        this.mResultAction = resultAction;
    }

    public int getResultActionType() {
        return this.mResultAction;
    }

    public final void performAction() {
        onPerformAction();
    }

    public final void slideAnimationEnd() {
        onSlideAnimationEnd();
        onCleanUp();
    }

    protected void onPerformAction() {
    }

    protected void onSlideAnimationEnd() {
    }

    protected void onCleanUp() {
    }
}
