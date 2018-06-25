package android.support.v7.widget;

import android.os.Bundle;
import android.support.v4.view.C0074a;
import android.support.v4.view.p023a.C0531e;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

public class aw extends C0074a {
    /* renamed from: a */
    final RecyclerView f2943a;
    /* renamed from: b */
    final C0074a f2944b = new C10331(this);

    /* renamed from: android.support.v7.widget.aw$1 */
    class C10331 extends C0074a {
        /* renamed from: a */
        final /* synthetic */ aw f2942a;

        C10331(aw awVar) {
            this.f2942a = awVar;
        }

        public void onInitializeAccessibilityNodeInfo(View view, C0531e c0531e) {
            super.onInitializeAccessibilityNodeInfo(view, c0531e);
            if (!this.f2942a.m5569a() && this.f2942a.f2943a.getLayoutManager() != null) {
                this.f2942a.f2943a.getLayoutManager().m4532a(view, c0531e);
            }
        }

        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            return super.performAccessibilityAction(view, i, bundle) ? true : (this.f2942a.m5569a() || this.f2942a.f2943a.getLayoutManager() == null) ? false : this.f2942a.f2943a.getLayoutManager().m4547a(view, i, bundle);
        }
    }

    public aw(RecyclerView recyclerView) {
        this.f2943a = recyclerView;
    }

    /* renamed from: a */
    boolean m5569a() {
        return this.f2943a.m303v();
    }

    /* renamed from: b */
    public C0074a m5570b() {
        return this.f2944b;
    }

    public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(view, accessibilityEvent);
        accessibilityEvent.setClassName(RecyclerView.class.getName());
        if ((view instanceof RecyclerView) && !m5569a()) {
            RecyclerView recyclerView = (RecyclerView) view;
            if (recyclerView.getLayoutManager() != null) {
                recyclerView.getLayoutManager().mo811a(accessibilityEvent);
            }
        }
    }

    public void onInitializeAccessibilityNodeInfo(View view, C0531e c0531e) {
        super.onInitializeAccessibilityNodeInfo(view, c0531e);
        c0531e.m2313b(RecyclerView.class.getName());
        if (!m5569a() && this.f2943a.getLayoutManager() != null) {
            this.f2943a.getLayoutManager().m4513a(c0531e);
        }
    }

    public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        return super.performAccessibilityAction(view, i, bundle) ? true : (m5569a() || this.f2943a.getLayoutManager() == null) ? false : this.f2943a.getLayoutManager().m4537a(i, bundle);
    }
}
