package android.support.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.design.C0073a.C0063b;
import android.support.design.C0073a.C0067f;
import android.support.design.C0073a.C0069h;
import android.support.design.C0073a.C0071j;
import android.support.design.widget.BottomSheetBehavior.C0097a;
import android.support.v4.view.C0074a;
import android.support.v4.view.ah;
import android.support.v4.view.p023a.C0531e;
import android.support.v7.app.C0146l;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import org.telegram.messenger.exoplayer2.source.ExtractorMediaSource;

/* renamed from: android.support.design.widget.c */
public class C0147c extends C0146l {
    /* renamed from: a */
    boolean f455a;
    /* renamed from: b */
    private BottomSheetBehavior<FrameLayout> f456b;
    /* renamed from: c */
    private boolean f457c;
    /* renamed from: d */
    private boolean f458d;
    /* renamed from: e */
    private C0097a f459e;

    /* renamed from: android.support.design.widget.c$1 */
    class C01421 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ C0147c f451a;

        C01421(C0147c c0147c) {
            this.f451a = c0147c;
        }

        public void onClick(View view) {
            if (this.f451a.f455a && this.f451a.isShowing() && this.f451a.m690a()) {
                this.f451a.cancel();
            }
        }
    }

    /* renamed from: android.support.design.widget.c$2 */
    class C01432 extends C0074a {
        /* renamed from: a */
        final /* synthetic */ C0147c f452a;

        C01432(C0147c c0147c) {
            this.f452a = c0147c;
        }

        public void onInitializeAccessibilityNodeInfo(View view, C0531e c0531e) {
            super.onInitializeAccessibilityNodeInfo(view, c0531e);
            if (this.f452a.f455a) {
                c0531e.m2305a((int) ExtractorMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES);
                c0531e.m2340k(true);
                return;
            }
            c0531e.m2340k(false);
        }

        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            if (i != ExtractorMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES || !this.f452a.f455a) {
                return super.performAccessibilityAction(view, i, bundle);
            }
            this.f452a.cancel();
            return true;
        }
    }

    /* renamed from: android.support.design.widget.c$3 */
    class C01443 extends C0097a {
        /* renamed from: a */
        final /* synthetic */ C0147c f453a;

        C01443(C0147c c0147c) {
            this.f453a = c0147c;
        }

        /* renamed from: a */
        public void mo131a(View view, float f) {
        }

        /* renamed from: a */
        public void mo132a(View view, int i) {
            if (i == 5) {
                this.f453a.cancel();
            }
        }
    }

    public C0147c(Context context) {
        this(context, 0);
    }

    public C0147c(Context context, int i) {
        super(context, C0147c.m688a(context, i));
        this.f455a = true;
        this.f457c = true;
        this.f459e = new C01443(this);
        m685a(1);
    }

    /* renamed from: a */
    private static int m688a(Context context, int i) {
        if (i != 0) {
            return i;
        }
        TypedValue typedValue = new TypedValue();
        return context.getTheme().resolveAttribute(C0063b.bottomSheetDialogTheme, typedValue, true) ? typedValue.resourceId : C0071j.Theme_Design_Light_BottomSheetDialog;
    }

    /* renamed from: a */
    private View m689a(int i, View view, LayoutParams layoutParams) {
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) View.inflate(getContext(), C0069h.design_bottom_sheet_dialog, null);
        if (i != 0 && view == null) {
            view = getLayoutInflater().inflate(i, coordinatorLayout, false);
        }
        View view2 = (FrameLayout) coordinatorLayout.findViewById(C0067f.design_bottom_sheet);
        this.f456b = BottomSheetBehavior.m466a(view2);
        this.f456b.m471a(this.f459e);
        this.f456b.m476a(this.f455a);
        if (layoutParams == null) {
            view2.addView(view);
        } else {
            view2.addView(view, layoutParams);
        }
        coordinatorLayout.findViewById(C0067f.touch_outside).setOnClickListener(new C01421(this));
        ah.m2783a(view2, new C01432(this));
        return coordinatorLayout;
    }

    /* renamed from: a */
    boolean m690a() {
        if (!this.f458d) {
            if (VERSION.SDK_INT < 11) {
                this.f457c = true;
            } else {
                TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(new int[]{16843611});
                this.f457c = obtainStyledAttributes.getBoolean(0, true);
                obtainStyledAttributes.recycle();
            }
            this.f458d = true;
        }
        return this.f457c;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setLayout(-1, -1);
    }

    protected void onStart() {
        super.onStart();
        if (this.f456b != null) {
            this.f456b.m483b(4);
        }
    }

    public void setCancelable(boolean z) {
        super.setCancelable(z);
        if (this.f455a != z) {
            this.f455a = z;
            if (this.f456b != null) {
                this.f456b.m476a(z);
            }
        }
    }

    public void setCanceledOnTouchOutside(boolean z) {
        super.setCanceledOnTouchOutside(z);
        if (z && !this.f455a) {
            this.f455a = true;
        }
        this.f457c = z;
        this.f458d = true;
    }

    public void setContentView(int i) {
        super.setContentView(m689a(i, null, null));
    }

    public void setContentView(View view) {
        super.setContentView(m689a(0, view, null));
    }

    public void setContentView(View view, LayoutParams layoutParams) {
        super.setContentView(m689a(0, view, layoutParams));
    }
}
