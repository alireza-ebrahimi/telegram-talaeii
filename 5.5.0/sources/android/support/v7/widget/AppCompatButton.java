package android.support.v7.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ae;
import android.support.v7.p025a.C0748a.C0738a;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.TextView;

public class AppCompatButton extends Button implements ae {
    /* renamed from: a */
    private final C1061h f2382a;
    /* renamed from: b */
    private final C1086w f2383b;

    public AppCompatButton(Context context) {
        this(context, null);
    }

    public AppCompatButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C0738a.buttonStyle);
    }

    public AppCompatButton(Context context, AttributeSet attributeSet, int i) {
        super(bh.m5649a(context), attributeSet, i);
        this.f2382a = new C1061h(this);
        this.f2382a.m5841a(attributeSet, i);
        this.f2383b = C1086w.m5916a((TextView) this);
        this.f2383b.mo1016a(attributeSet, i);
        this.f2383b.mo1015a();
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.f2382a != null) {
            this.f2382a.m5844c();
        }
        if (this.f2383b != null) {
            this.f2383b.mo1015a();
        }
    }

    public ColorStateList getSupportBackgroundTintList() {
        return this.f2382a != null ? this.f2382a.m5836a() : null;
    }

    public Mode getSupportBackgroundTintMode() {
        return this.f2382a != null ? this.f2382a.m5842b() : null;
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(Button.class.getName());
    }

    @TargetApi(14)
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(Button.class.getName());
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        if (this.f2382a != null) {
            this.f2382a.m5840a(drawable);
        }
    }

    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        if (this.f2382a != null) {
            this.f2382a.m5837a(i);
        }
    }

    public void setSupportAllCaps(boolean z) {
        if (this.f2383b != null) {
            this.f2383b.m5921a(z);
        }
    }

    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        if (this.f2382a != null) {
            this.f2382a.m5838a(colorStateList);
        }
    }

    public void setSupportBackgroundTintMode(Mode mode) {
        if (this.f2382a != null) {
            this.f2382a.m5839a(mode);
        }
    }

    public void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        if (this.f2383b != null) {
            this.f2383b.m5918a(context, i);
        }
    }
}
