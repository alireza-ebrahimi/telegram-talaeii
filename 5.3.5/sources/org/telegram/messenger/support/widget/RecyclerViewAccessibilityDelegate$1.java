package org.telegram.messenger.support.widget;

import android.os.Bundle;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.View;

class RecyclerViewAccessibilityDelegate$1 extends AccessibilityDelegateCompat {
    final /* synthetic */ RecyclerViewAccessibilityDelegate this$0;

    RecyclerViewAccessibilityDelegate$1(RecyclerViewAccessibilityDelegate this$0) {
        this.this$0 = this$0;
    }

    public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
        super.onInitializeAccessibilityNodeInfo(host, info);
        if (!this.this$0.shouldIgnore() && this.this$0.mRecyclerView.getLayoutManager() != null) {
            this.this$0.mRecyclerView.getLayoutManager().onInitializeAccessibilityNodeInfoForItem(host, info);
        }
    }

    public boolean performAccessibilityAction(View host, int action, Bundle args) {
        if (super.performAccessibilityAction(host, action, args)) {
            return true;
        }
        if (this.this$0.shouldIgnore() || this.this$0.mRecyclerView.getLayoutManager() == null) {
            return false;
        }
        return this.this$0.mRecyclerView.getLayoutManager().performAccessibilityActionForItem(host, action, args);
    }
}
