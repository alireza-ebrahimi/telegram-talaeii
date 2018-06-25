package com.mohamadamin.persianmaterialdatetimepicker.time;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction;
import android.widget.FrameLayout;
import com.mohamadamin.persianmaterialdatetimepicker.C2021a;
import com.mohamadamin.persianmaterialdatetimepicker.C2028b.C2022a;
import com.mohamadamin.persianmaterialdatetimepicker.p121a.C2017a;
import java.util.Calendar;
import org.telegram.messenger.MessagesController;

public class RadialPickerLayout extends FrameLayout implements OnTouchListener {
    /* renamed from: A */
    private AccessibilityManager f6079A;
    /* renamed from: B */
    private AnimatorSet f6080B;
    /* renamed from: C */
    private Handler f6081C = new Handler();
    /* renamed from: a */
    private final int f6082a;
    /* renamed from: b */
    private final int f6083b;
    /* renamed from: c */
    private int f6084c;
    /* renamed from: d */
    private C2021a f6085d;
    /* renamed from: e */
    private C2053a f6086e;
    /* renamed from: f */
    private boolean f6087f;
    /* renamed from: g */
    private int f6088g;
    /* renamed from: h */
    private int f6089h;
    /* renamed from: i */
    private boolean f6090i;
    /* renamed from: j */
    private boolean f6091j;
    /* renamed from: k */
    private int f6092k;
    /* renamed from: l */
    private C2055b f6093l;
    /* renamed from: m */
    private C2054a f6094m;
    /* renamed from: n */
    private C2061d f6095n;
    /* renamed from: o */
    private C2061d f6096o;
    /* renamed from: p */
    private C2058c f6097p;
    /* renamed from: q */
    private C2058c f6098q;
    /* renamed from: r */
    private View f6099r;
    /* renamed from: s */
    private int[] f6100s;
    /* renamed from: t */
    private boolean f6101t;
    /* renamed from: u */
    private int f6102u = -1;
    /* renamed from: v */
    private boolean f6103v;
    /* renamed from: w */
    private boolean f6104w;
    /* renamed from: x */
    private int f6105x;
    /* renamed from: y */
    private float f6106y;
    /* renamed from: z */
    private float f6107z;

    /* renamed from: com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout$1 */
    class C20511 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ RadialPickerLayout f6076a;

        C20511(RadialPickerLayout radialPickerLayout) {
            this.f6076a = radialPickerLayout;
        }

        public void run() {
            this.f6076a.f6094m.setAmOrPmPressed(this.f6076a.f6102u);
            this.f6076a.f6094m.invalidate();
        }
    }

    /* renamed from: com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout$a */
    public interface C2053a {
        /* renamed from: a */
        void mo3087a(int i, int i2, boolean z);
    }

    public RadialPickerLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setOnTouchListener(this);
        this.f6082a = ViewConfiguration.get(context).getScaledTouchSlop();
        this.f6083b = ViewConfiguration.getTapTimeout();
        this.f6103v = false;
        this.f6093l = new C2055b(context);
        addView(this.f6093l);
        this.f6094m = new C2054a(context);
        addView(this.f6094m);
        this.f6097p = new C2058c(context);
        addView(this.f6097p);
        this.f6098q = new C2058c(context);
        addView(this.f6098q);
        this.f6095n = new C2061d(context);
        addView(this.f6095n);
        this.f6096o = new C2061d(context);
        addView(this.f6096o);
        m9224a();
        this.f6084c = -1;
        this.f6101t = true;
        this.f6099r = new View(context);
        this.f6099r.setLayoutParams(new LayoutParams(-1, -1));
        this.f6099r.setBackgroundColor(getResources().getColor(C2022a.mdtp_transparent_black));
        this.f6099r.setVisibility(4);
        addView(this.f6099r);
        this.f6079A = (AccessibilityManager) context.getSystemService("accessibility");
        this.f6087f = false;
    }

    /* renamed from: a */
    private int m9219a(float f, float f2, boolean z, Boolean[] boolArr) {
        int currentItemShowing = getCurrentItemShowing();
        return currentItemShowing == 0 ? this.f6097p.m9244a(f, f2, z, boolArr) : currentItemShowing == 1 ? this.f6098q.m9244a(f, f2, z, boolArr) : -1;
    }

    /* renamed from: a */
    private int m9220a(int i, boolean z, boolean z2, boolean z3) {
        int i2 = -1;
        if (i != -1) {
            C2058c c2058c;
            int i3;
            int currentItemShowing = getCurrentItemShowing();
            i2 = (z2 || currentItemShowing != 1) ? 0 : 1;
            int b = i2 != 0 ? m9227b(i) : m9232d(i, 0);
            if (currentItemShowing == 0) {
                c2058c = this.f6097p;
                i3 = 30;
            } else {
                c2058c = this.f6098q;
                i3 = 6;
            }
            c2058c.m9245a(b, z, z3);
            c2058c.invalidate();
            if (currentItemShowing != 0) {
                if (b == 360 && currentItemShowing == 1) {
                    i2 = 0;
                }
                i2 = b;
            } else if (!this.f6090i) {
                if (b == 0) {
                    i2 = 360;
                }
                i2 = b;
            } else if (b == 0 && z) {
                i2 = 360;
            } else {
                if (b == 360 && !z) {
                    i2 = 0;
                }
                i2 = b;
            }
            i3 = i2 / i3;
            i2 = (currentItemShowing != 0 || !this.f6090i || z || i2 == 0) ? i3 : i3 + 12;
            if (getCurrentItemShowing() == 0) {
                this.f6095n.setSelection(i2);
                this.f6095n.invalidate();
            } else if (getCurrentItemShowing() == 1) {
                this.f6096o.setSelection(i2);
                this.f6096o.invalidate();
            }
        }
        return i2;
    }

    /* renamed from: a */
    private void m9224a() {
        this.f6100s = new int[361];
        int i = 0;
        int i2 = 8;
        int i3 = 1;
        for (int i4 = 0; i4 < 361; i4++) {
            this.f6100s[i4] = i;
            if (i3 == i2) {
                i3 = i + 6;
                i2 = i3 == 360 ? 7 : i3 % 30 == 0 ? 14 : 4;
                i = i3;
                i3 = 1;
            } else {
                i3++;
            }
        }
    }

    /* renamed from: a */
    private boolean m9225a(int i) {
        return this.f6090i && i <= 12 && i != 0;
    }

    /* renamed from: b */
    private int m9227b(int i) {
        return this.f6100s == null ? -1 : this.f6100s[i];
    }

    /* renamed from: b */
    private void m9229b(int i, int i2) {
        if (i == 0) {
            m9231c(0, i2);
            this.f6097p.m9245a((i2 % 12) * 30, m9225a(i2), false);
            this.f6097p.invalidate();
            this.f6095n.setSelection(i2);
            this.f6095n.invalidate();
        } else if (i == 1) {
            m9231c(1, i2);
            this.f6098q.m9245a(i2 * 6, false, false);
            this.f6098q.invalidate();
            this.f6096o.setSelection(i2);
            this.f6095n.invalidate();
        }
    }

    /* renamed from: c */
    private void m9231c(int i, int i2) {
        if (i == 0) {
            this.f6088g = i2;
        } else if (i == 1) {
            this.f6089h = i2;
        } else if (i != 2) {
        } else {
            if (i2 == 0) {
                this.f6088g %= 12;
            } else if (i2 == 1) {
                this.f6088g = (this.f6088g % 12) + 12;
            }
        }
    }

    /* renamed from: d */
    private static int m9232d(int i, int i2) {
        int i3 = (i / 30) * 30;
        int i4 = i3 + 30;
        return i2 == 1 ? i4 : i2 == -1 ? i == i3 ? i3 - 30 : i3 : i - i3 >= i4 - i ? i4 : i3;
    }

    private int getCurrentlyShowingValue() {
        int currentItemShowing = getCurrentItemShowing();
        return currentItemShowing == 0 ? this.f6088g : currentItemShowing == 1 ? this.f6089h : -1;
    }

    /* renamed from: a */
    public void m9234a(int i, int i2) {
        m9229b(0, i);
        m9229b(1, i2);
    }

    /* renamed from: a */
    public void m9235a(int i, boolean z) {
        int i2 = 255;
        if (i == 0 || i == 1) {
            int currentItemShowing = getCurrentItemShowing();
            this.f6092k = i;
            if (!z || i == currentItemShowing) {
                currentItemShowing = i == 0 ? 255 : 0;
                if (i != 1) {
                    i2 = 0;
                }
                this.f6095n.setAlpha((float) currentItemShowing);
                this.f6097p.setAlpha((float) currentItemShowing);
                this.f6096o.setAlpha((float) i2);
                this.f6098q.setAlpha((float) i2);
                return;
            }
            Animator[] animatorArr = new ObjectAnimator[4];
            if (i == 1) {
                animatorArr[0] = this.f6095n.getDisappearAnimator();
                animatorArr[1] = this.f6097p.getDisappearAnimator();
                animatorArr[2] = this.f6096o.getReappearAnimator();
                animatorArr[3] = this.f6098q.getReappearAnimator();
            } else if (i == 0) {
                animatorArr[0] = this.f6095n.getReappearAnimator();
                animatorArr[1] = this.f6097p.getReappearAnimator();
                animatorArr[2] = this.f6096o.getDisappearAnimator();
                animatorArr[3] = this.f6098q.getDisappearAnimator();
            }
            if (this.f6080B != null && this.f6080B.isRunning()) {
                this.f6080B.end();
            }
            this.f6080B = new AnimatorSet();
            this.f6080B.playTogether(animatorArr);
            this.f6080B.start();
            return;
        }
        Log.e("RadialPickerLayout", "TimePicker does not support view at index " + i);
    }

    /* renamed from: a */
    public void m9236a(Context context, C2021a c2021a, int i, int i2, boolean z) {
        if (this.f6087f) {
            Log.e("RadialPickerLayout", "Time has already been initialized.");
            return;
        }
        this.f6085d = c2021a;
        this.f6090i = z;
        boolean z2 = this.f6079A.isTouchExplorationEnabled() || this.f6090i;
        this.f6091j = z2;
        this.f6093l.m9242a(context, this.f6091j);
        this.f6093l.invalidate();
        if (!this.f6091j) {
            this.f6094m.m9240a(context, i < 12 ? 0 : 1);
            this.f6094m.invalidate();
        }
        Resources resources = context.getResources();
        int[] iArr = new int[]{12, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        int[] iArr2 = new int[]{0, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
        int[] iArr3 = new int[]{0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55};
        String[] strArr = new String[12];
        String[] strArr2 = new String[12];
        String[] strArr3 = new String[12];
        for (int i3 = 0; i3 < 12; i3++) {
            strArr[i3] = C2017a.m9087a(z ? String.format("%02d", new Object[]{Integer.valueOf(iArr2[i3])}) : String.format("%d", new Object[]{Integer.valueOf(iArr[i3])}));
            strArr2[i3] = C2017a.m9087a(String.format("%d", new Object[]{Integer.valueOf(iArr[i3])}));
            strArr3[i3] = C2017a.m9087a(String.format("%02d", new Object[]{Integer.valueOf(iArr3[i3])}));
        }
        C2061d c2061d = this.f6095n;
        if (!z) {
            strArr2 = null;
        }
        c2061d.m9252a(resources, strArr, strArr2, this.f6091j, true);
        this.f6095n.setSelection(z ? i : i % 12);
        this.f6095n.invalidate();
        this.f6096o.m9252a(resources, strArr3, null, this.f6091j, false);
        this.f6096o.setSelection(i2);
        this.f6096o.invalidate();
        m9231c(0, i);
        m9231c(1, i2);
        this.f6097p.m9247a(context, this.f6091j, z, true, (i % 12) * 30, m9225a(i));
        this.f6098q.m9247a(context, this.f6091j, false, false, i2 * 6, false);
        this.f6087f = true;
    }

    /* renamed from: a */
    void m9237a(Context context, boolean z) {
        this.f6093l.m9243b(context, z);
        this.f6094m.m9241a(context, z);
        this.f6095n.m9251a(context, z);
        this.f6096o.m9251a(context, z);
        this.f6097p.m9246a(context, z);
        this.f6098q.m9246a(context, z);
    }

    /* renamed from: a */
    public boolean m9238a(boolean z) {
        int i = 0;
        if (this.f6104w && !z) {
            return false;
        }
        this.f6101t = z;
        View view = this.f6099r;
        if (z) {
            i = 4;
        }
        view.setVisibility(i);
        return true;
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() != 32) {
            return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
        }
        accessibilityEvent.getText().clear();
        Calendar instance = Calendar.getInstance();
        instance.set(10, getHours());
        instance.set(12, getMinutes());
        accessibilityEvent.getText().add(C2017a.m9087a(DateUtils.formatDateTime(getContext(), instance.getTimeInMillis(), this.f6090i ? 129 : 1)));
        return true;
    }

    public int getCurrentItemShowing() {
        if (this.f6092k == 0 || this.f6092k == 1) {
            return this.f6092k;
        }
        Log.e("RadialPickerLayout", "Current item showing was unfortunately set to " + this.f6092k);
        return -1;
    }

    public int getHours() {
        return this.f6088g;
    }

    public int getIsCurrentlyAmOrPm() {
        return this.f6088g < 12 ? 0 : this.f6088g < 24 ? 1 : -1;
    }

    public int getMinutes() {
        return this.f6089h;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (VERSION.SDK_INT >= 21) {
            accessibilityNodeInfo.addAction(AccessibilityAction.ACTION_SCROLL_BACKWARD);
            accessibilityNodeInfo.addAction(AccessibilityAction.ACTION_SCROLL_FORWARD);
            return;
        }
        accessibilityNodeInfo.addAction(4096);
        accessibilityNodeInfo.addAction(MessagesController.UPDATE_MASK_CHANNEL);
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        final Boolean[] boolArr = new Boolean[]{Boolean.valueOf(false)};
        int a;
        switch (motionEvent.getAction()) {
            case 0:
                if (!this.f6101t) {
                    return true;
                }
                this.f6106y = x;
                this.f6107z = y;
                this.f6084c = -1;
                this.f6103v = false;
                this.f6104w = true;
                if (this.f6091j) {
                    this.f6102u = -1;
                } else {
                    this.f6102u = this.f6094m.m9239a(x, y);
                }
                if (this.f6102u == 0 || this.f6102u == 1) {
                    this.f6085d.m9112c();
                    this.f6105x = -1;
                    this.f6081C.postDelayed(new C20511(this), (long) this.f6083b);
                    return true;
                }
                this.f6105x = m9219a(x, y, this.f6079A.isTouchExplorationEnabled(), boolArr);
                if (this.f6105x == -1) {
                    return true;
                }
                this.f6085d.m9112c();
                this.f6081C.postDelayed(new Runnable(this) {
                    /* renamed from: b */
                    final /* synthetic */ RadialPickerLayout f6078b;

                    public void run() {
                        this.f6078b.f6103v = true;
                        int a = this.f6078b.m9220a(this.f6078b.f6105x, boolArr[0].booleanValue(), false, true);
                        this.f6078b.f6084c = a;
                        this.f6078b.f6086e.mo3087a(this.f6078b.getCurrentItemShowing(), a, false);
                    }
                }, (long) this.f6083b);
                return true;
            case 1:
                if (this.f6101t) {
                    this.f6081C.removeCallbacksAndMessages(null);
                    this.f6104w = false;
                    if (this.f6102u == 0 || this.f6102u == 1) {
                        a = this.f6094m.m9239a(x, y);
                        this.f6094m.setAmOrPmPressed(-1);
                        this.f6094m.invalidate();
                        if (a == this.f6102u) {
                            this.f6094m.setAmOrPm(a);
                            if (getIsCurrentlyAmOrPm() != a) {
                                this.f6086e.mo3087a(2, this.f6102u, false);
                                m9231c(2, a);
                            }
                        }
                        this.f6102u = -1;
                        break;
                    }
                    if (this.f6105x != -1) {
                        int a2 = m9219a(x, y, this.f6103v, boolArr);
                        if (a2 != -1) {
                            a = m9220a(a2, boolArr[0].booleanValue(), !this.f6103v, false);
                            if (getCurrentItemShowing() == 0 && !this.f6090i) {
                                a2 = getIsCurrentlyAmOrPm();
                                if (a2 == 0 && a == 12) {
                                    a = 0;
                                } else if (a2 == 1 && a != 12) {
                                    a += 12;
                                }
                            }
                            m9231c(getCurrentItemShowing(), a);
                            this.f6086e.mo3087a(getCurrentItemShowing(), a, true);
                        }
                    }
                    this.f6103v = false;
                    return true;
                }
                Log.d("RadialPickerLayout", "Input was disabled, but received ACTION_UP.");
                this.f6086e.mo3087a(3, 1, false);
                return true;
            case 2:
                if (this.f6101t) {
                    float abs = Math.abs(y - this.f6107z);
                    float abs2 = Math.abs(x - this.f6106y);
                    if (this.f6103v || abs2 > ((float) this.f6082a) || abs > ((float) this.f6082a)) {
                        if (this.f6102u == 0 || this.f6102u == 1) {
                            this.f6081C.removeCallbacksAndMessages(null);
                            if (this.f6094m.m9239a(x, y) != this.f6102u) {
                                this.f6094m.setAmOrPmPressed(-1);
                                this.f6094m.invalidate();
                                this.f6102u = -1;
                                break;
                            }
                        } else if (this.f6105x != -1) {
                            this.f6103v = true;
                            this.f6081C.removeCallbacksAndMessages(null);
                            a = m9219a(x, y, true, boolArr);
                            if (a == -1) {
                                return true;
                            }
                            a = m9220a(a, boolArr[0].booleanValue(), false, true);
                            if (a == this.f6084c) {
                                return true;
                            }
                            this.f6085d.m9112c();
                            this.f6084c = a;
                            this.f6086e.mo3087a(getCurrentItemShowing(), a, false);
                            return true;
                        }
                    }
                }
                Log.e("RadialPickerLayout", "Input was disabled, but received ACTION_MOVE.");
                return true;
                break;
        }
        return false;
    }

    @SuppressLint({"NewApi"})
    public boolean performAccessibilityAction(int i, Bundle bundle) {
        if (super.performAccessibilityAction(i, bundle)) {
            return true;
        }
        int i2 = i == 4096 ? 1 : i == MessagesController.UPDATE_MASK_CHANNEL ? -1 : 0;
        if (i2 == 0) {
            return false;
        }
        int i3;
        int currentlyShowingValue = getCurrentlyShowingValue();
        int currentItemShowing = getCurrentItemShowing();
        if (currentItemShowing == 0) {
            i3 = 30;
            currentlyShowingValue %= 12;
        } else {
            i3 = currentItemShowing == 1 ? 6 : 0;
        }
        i2 = m9232d(currentlyShowingValue * i3, i2) / i3;
        if (currentItemShowing != 0) {
            currentlyShowingValue = 55;
            i3 = 0;
        } else if (this.f6090i) {
            currentlyShowingValue = 23;
            i3 = 0;
        } else {
            currentlyShowingValue = 12;
            i3 = 1;
        }
        if (i2 <= currentlyShowingValue) {
            i3 = i2 < i3 ? currentlyShowingValue : i2;
        }
        m9229b(currentItemShowing, i3);
        this.f6086e.mo3087a(currentItemShowing, i3, false);
        return true;
    }

    public void setAmOrPm(int i) {
        this.f6094m.setAmOrPm(i);
        this.f6094m.invalidate();
        m9231c(2, i);
    }

    public void setOnValueSelectedListener(C2053a c2053a) {
        this.f6086e = c2053a;
    }
}
