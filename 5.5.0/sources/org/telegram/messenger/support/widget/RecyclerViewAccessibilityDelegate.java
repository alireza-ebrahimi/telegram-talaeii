package org.telegram.messenger.support.widget;

import android.os.Bundle;
import android.support.v4.view.C0074a;
import android.support.v4.view.p023a.C0531e;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

public class RecyclerViewAccessibilityDelegate extends C0074a {
    final C0074a mItemDelegate = new C36811();
    final RecyclerView mRecyclerView;

    /* renamed from: org.telegram.messenger.support.widget.RecyclerViewAccessibilityDelegate$1 */
    class C36811 extends C0074a {
        C36811() {
        }

        public void onInitializeAccessibilityNodeInfo(View view, C0531e c0531e) {
            super.onInitializeAccessibilityNodeInfo(view, c0531e);
            if (!RecyclerViewAccessibilityDelegate.this.shouldIgnore() && RecyclerViewAccessibilityDelegate.this.mRecyclerView.getLayoutManager() != null) {
                RecyclerViewAccessibilityDelegate.this.mRecyclerView.getLayoutManager().onInitializeAccessibilityNodeInfoForItem(view, c0531e);
            }
        }

        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            return super.performAccessibilityAction(view, i, bundle) ? true : (RecyclerViewAccessibilityDelegate.this.shouldIgnore() || RecyclerViewAccessibilityDelegate.this.mRecyclerView.getLayoutManager() == null) ? false : RecyclerViewAccessibilityDelegate.this.mRecyclerView.getLayoutManager().performAccessibilityActionForItem(view, i, bundle);
        }
    }

    public RecyclerViewAccessibilityDelegate(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    public C0074a getItemDelegate() {
        return this.mItemDelegate;
    }

    public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(view, accessibilityEvent);
        accessibilityEvent.setClassName(RecyclerView.class.getName());
        if ((view instanceof RecyclerView) && !shouldIgnore()) {
            RecyclerView recyclerView = (RecyclerView) view;
            if (recyclerView.getLayoutManager() != null) {
                recyclerView.getLayoutManager().onInitializeAccessibilityEvent(accessibilityEvent);
            }
        }
    }

    public void onInitializeAccessibilityNodeInfo(View view, C0531e c0531e) {
        super.onInitializeAccessibilityNodeInfo(view, c0531e);
        c0531e.m2313b(RecyclerView.class.getName());
        if (!shouldIgnore() && this.mRecyclerView.getLayoutManager() != null) {
            this.mRecyclerView.getLayoutManager().onInitializeAccessibilityNodeInfo(c0531e);
        }
    }

    public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        return super.performAccessibilityAction(view, i, bundle) ? true : (shouldIgnore() || this.mRecyclerView.getLayoutManager() == null) ? false : this.mRecyclerView.getLayoutManager().performAccessibilityAction(i, bundle);
    }

    boolean shouldIgnore() {
        return this.mRecyclerView.hasPendingAdapterUpdates();
    }
}
