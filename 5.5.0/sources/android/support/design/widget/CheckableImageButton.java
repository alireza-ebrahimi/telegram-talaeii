package android.support.design.widget;

import android.content.Context;
import android.support.v4.view.C0074a;
import android.support.v4.view.ah;
import android.support.v4.view.p023a.C0531e;
import android.support.v7.p025a.C0748a.C0738a;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Checkable;

public class CheckableImageButton extends AppCompatImageButton implements Checkable {
    /* renamed from: a */
    private static final int[] f287a = new int[]{16842912};
    /* renamed from: b */
    private boolean f288b;

    /* renamed from: android.support.design.widget.CheckableImageButton$1 */
    class C00991 extends C0074a {
        /* renamed from: a */
        final /* synthetic */ CheckableImageButton f284a;

        C00991(CheckableImageButton checkableImageButton) {
            this.f284a = checkableImageButton;
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setChecked(this.f284a.isChecked());
        }

        public void onInitializeAccessibilityNodeInfo(View view, C0531e c0531e) {
            super.onInitializeAccessibilityNodeInfo(view, c0531e);
            c0531e.m2309a(true);
            c0531e.m2315b(this.f284a.isChecked());
        }
    }

    public CheckableImageButton(Context context) {
        this(context, null);
    }

    public CheckableImageButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C0738a.imageButtonStyle);
    }

    public CheckableImageButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        ah.m2783a((View) this, new C00991(this));
    }

    public boolean isChecked() {
        return this.f288b;
    }

    public int[] onCreateDrawableState(int i) {
        return this.f288b ? mergeDrawableStates(super.onCreateDrawableState(f287a.length + i), f287a) : super.onCreateDrawableState(i);
    }

    public void setChecked(boolean z) {
        if (this.f288b != z) {
            this.f288b = z;
            refreshDrawableState();
            sendAccessibilityEvent(2048);
        }
    }

    public void toggle() {
        setChecked(!this.f288b);
    }
}
