package android.support.v7.widget;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.v4.view.C0659t;
import android.support.v4.view.ax;
import android.support.v4.widget.C0710n;
import android.support.v7.p025a.C0748a.C0738a;
import android.view.MotionEvent;
import android.view.View;

class aj extends aq {
    /* renamed from: g */
    private boolean f2848g;
    /* renamed from: h */
    private boolean f2849h;
    /* renamed from: i */
    private boolean f2850i;
    /* renamed from: j */
    private ax f2851j;
    /* renamed from: k */
    private C0710n f2852k;

    public aj(Context context, boolean z) {
        super(context, null, C0738a.dropDownListViewStyle);
        this.f2849h = z;
        setCacheColorHint(0);
    }

    /* renamed from: a */
    private void m5442a(View view, int i) {
        performItemClick(view, i, getItemIdAtPosition(i));
    }

    /* renamed from: a */
    private void m5443a(View view, int i, float f, float f2) {
        this.f2850i = true;
        if (VERSION.SDK_INT >= 21) {
            drawableHotspotChanged(f, f2);
        }
        if (!isPressed()) {
            setPressed(true);
        }
        layoutChildren();
        if (this.f != -1) {
            View childAt = getChildAt(this.f - getFirstVisiblePosition());
            if (!(childAt == null || childAt == view || !childAt.isPressed())) {
                childAt.setPressed(false);
            }
        }
        this.f = i;
        float left = f - ((float) view.getLeft());
        float top = f2 - ((float) view.getTop());
        if (VERSION.SDK_INT >= 21) {
            view.drawableHotspotChanged(left, top);
        }
        if (!view.isPressed()) {
            view.setPressed(true);
        }
        m5436a(i, view, f, f2);
        setSelectorEnabled(false);
        refreshDrawableState();
    }

    /* renamed from: d */
    private void m5444d() {
        this.f2850i = false;
        setPressed(false);
        drawableStateChanged();
        View childAt = getChildAt(this.f - getFirstVisiblePosition());
        if (childAt != null) {
            childAt.setPressed(false);
        }
        if (this.f2851j != null) {
            this.f2851j.m3027b();
            this.f2851j = null;
        }
    }

    /* renamed from: a */
    protected boolean mo931a() {
        return this.f2850i || super.mo931a();
    }

    /* renamed from: a */
    public boolean mo937a(MotionEvent motionEvent, int i) {
        boolean z;
        boolean z2;
        int a = C0659t.m3205a(motionEvent);
        switch (a) {
            case 1:
                z = false;
                break;
            case 2:
                z = true;
                break;
            case 3:
                z = false;
                z2 = false;
                break;
            default:
                z = false;
                z2 = true;
                break;
        }
        int findPointerIndex = motionEvent.findPointerIndex(i);
        if (findPointerIndex < 0) {
            z = false;
            z2 = false;
        } else {
            int x = (int) motionEvent.getX(findPointerIndex);
            findPointerIndex = (int) motionEvent.getY(findPointerIndex);
            int pointToPosition = pointToPosition(x, findPointerIndex);
            if (pointToPosition == -1) {
                z2 = z;
                z = true;
            } else {
                View childAt = getChildAt(pointToPosition - getFirstVisiblePosition());
                m5443a(childAt, pointToPosition, (float) x, (float) findPointerIndex);
                if (a == 1) {
                    m5442a(childAt, pointToPosition);
                }
                z = false;
                z2 = true;
            }
        }
        if (!z2 || r0) {
            m5444d();
        }
        if (z2) {
            if (this.f2852k == null) {
                this.f2852k = new C0710n(this);
            }
            this.f2852k.m3297a(true);
            this.f2852k.onTouch(this, motionEvent);
        } else if (this.f2852k != null) {
            this.f2852k.m3297a(false);
        }
        return z2;
    }

    public boolean hasFocus() {
        return this.f2849h || super.hasFocus();
    }

    public boolean hasWindowFocus() {
        return this.f2849h || super.hasWindowFocus();
    }

    public boolean isFocused() {
        return this.f2849h || super.isFocused();
    }

    public boolean isInTouchMode() {
        return (this.f2849h && this.f2848g) || super.isInTouchMode();
    }

    void setListSelectionHidden(boolean z) {
        this.f2848g = z;
    }
}
