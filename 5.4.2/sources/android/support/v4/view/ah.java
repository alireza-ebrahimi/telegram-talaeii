package android.support.v4.view;

import android.content.res.ColorStateList;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.p014d.C0432c;
import android.support.v4.view.aq.C0578a;
import android.support.v4.view.p023a.C0531e;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

public class ah {
    /* renamed from: a */
    static final C0566l f1323a;

    /* renamed from: android.support.v4.view.ah$l */
    interface C0566l {
        /* renamed from: A */
        boolean mo441A(View view);

        /* renamed from: B */
        void mo442B(View view);

        /* renamed from: C */
        boolean mo443C(View view);

        /* renamed from: D */
        boolean mo444D(View view);

        /* renamed from: E */
        ColorStateList mo445E(View view);

        /* renamed from: F */
        Mode mo446F(View view);

        /* renamed from: G */
        void mo447G(View view);

        /* renamed from: H */
        boolean mo448H(View view);

        /* renamed from: I */
        float mo449I(View view);

        /* renamed from: J */
        boolean mo450J(View view);

        /* renamed from: K */
        boolean mo451K(View view);

        /* renamed from: L */
        Display mo452L(View view);

        /* renamed from: a */
        int mo453a(int i, int i2);

        /* renamed from: a */
        int mo454a(int i, int i2, int i3);

        /* renamed from: a */
        be mo455a(View view, be beVar);

        /* renamed from: a */
        void mo456a(View view, float f);

        /* renamed from: a */
        void mo457a(View view, int i, int i2);

        /* renamed from: a */
        void mo458a(View view, int i, int i2, int i3, int i4);

        /* renamed from: a */
        void mo459a(View view, int i, Paint paint);

        /* renamed from: a */
        void mo460a(View view, ColorStateList colorStateList);

        /* renamed from: a */
        void mo461a(View view, Mode mode);

        /* renamed from: a */
        void mo462a(View view, Drawable drawable);

        /* renamed from: a */
        void mo463a(View view, C0531e c0531e);

        /* renamed from: a */
        void mo464a(View view, C0074a c0074a);

        /* renamed from: a */
        void mo465a(View view, ab abVar);

        /* renamed from: a */
        void mo466a(View view, C0081z c0081z);

        /* renamed from: a */
        void mo467a(View view, AccessibilityEvent accessibilityEvent);

        /* renamed from: a */
        void mo468a(View view, Runnable runnable);

        /* renamed from: a */
        void mo469a(View view, Runnable runnable, long j);

        /* renamed from: a */
        void mo470a(View view, boolean z);

        /* renamed from: a */
        void mo471a(ViewGroup viewGroup, boolean z);

        /* renamed from: a */
        boolean mo472a(View view);

        /* renamed from: a */
        boolean mo473a(View view, int i);

        /* renamed from: a */
        boolean mo474a(View view, int i, Bundle bundle);

        /* renamed from: b */
        be mo475b(View view, be beVar);

        /* renamed from: b */
        void mo476b(View view, float f);

        /* renamed from: b */
        void mo477b(View view, boolean z);

        /* renamed from: b */
        boolean mo478b(View view);

        /* renamed from: b */
        boolean mo479b(View view, int i);

        /* renamed from: c */
        void mo480c(View view);

        /* renamed from: c */
        void mo481c(View view, float f);

        /* renamed from: c */
        void mo482c(View view, int i);

        /* renamed from: c */
        void mo483c(View view, boolean z);

        /* renamed from: d */
        int mo484d(View view);

        /* renamed from: d */
        void mo485d(View view, float f);

        /* renamed from: d */
        void mo486d(View view, int i);

        /* renamed from: e */
        float mo487e(View view);

        /* renamed from: e */
        void mo488e(View view, float f);

        /* renamed from: e */
        void mo489e(View view, int i);

        /* renamed from: f */
        int mo490f(View view);

        /* renamed from: f */
        void mo491f(View view, float f);

        /* renamed from: f */
        void mo492f(View view, int i);

        /* renamed from: g */
        int mo493g(View view);

        /* renamed from: g */
        void mo494g(View view, float f);

        /* renamed from: h */
        int mo495h(View view);

        /* renamed from: h */
        void mo496h(View view, float f);

        /* renamed from: i */
        int mo497i(View view);

        /* renamed from: i */
        void mo498i(View view, float f);

        /* renamed from: j */
        int mo499j(View view);

        /* renamed from: j */
        void mo500j(View view, float f);

        /* renamed from: k */
        int mo501k(View view);

        /* renamed from: k */
        void mo502k(View view, float f);

        /* renamed from: l */
        boolean mo503l(View view);

        /* renamed from: m */
        float mo504m(View view);

        /* renamed from: n */
        float mo505n(View view);

        /* renamed from: o */
        float mo506o(View view);

        /* renamed from: p */
        float mo507p(View view);

        /* renamed from: q */
        float mo508q(View view);

        /* renamed from: r */
        Matrix mo509r(View view);

        /* renamed from: s */
        int mo510s(View view);

        /* renamed from: t */
        int mo511t(View view);

        /* renamed from: u */
        ax mo512u(View view);

        /* renamed from: v */
        String mo513v(View view);

        /* renamed from: w */
        int mo514w(View view);

        /* renamed from: x */
        void mo515x(View view);

        /* renamed from: y */
        float mo516y(View view);
    }

    /* renamed from: android.support.v4.view.ah$b */
    static class C0567b implements C0566l {
        /* renamed from: b */
        private static Method f1317b;
        /* renamed from: a */
        WeakHashMap<View, ax> f1318a = null;

        C0567b() {
        }

        /* renamed from: a */
        private boolean m2596a(ad adVar, int i) {
            int computeHorizontalScrollOffset = adVar.computeHorizontalScrollOffset();
            int computeHorizontalScrollRange = adVar.computeHorizontalScrollRange() - adVar.computeHorizontalScrollExtent();
            return computeHorizontalScrollRange == 0 ? false : i < 0 ? computeHorizontalScrollOffset > 0 : computeHorizontalScrollOffset < computeHorizontalScrollRange + -1;
        }

        /* renamed from: b */
        private boolean m2597b(ad adVar, int i) {
            int computeVerticalScrollOffset = adVar.computeVerticalScrollOffset();
            int computeVerticalScrollRange = adVar.computeVerticalScrollRange() - adVar.computeVerticalScrollExtent();
            return computeVerticalScrollRange == 0 ? false : i < 0 ? computeVerticalScrollOffset > 0 : computeVerticalScrollOffset < computeVerticalScrollRange + -1;
        }

        /* renamed from: A */
        public boolean mo441A(View view) {
            return false;
        }

        /* renamed from: B */
        public void mo442B(View view) {
        }

        /* renamed from: C */
        public boolean mo443C(View view) {
            return false;
        }

        /* renamed from: D */
        public boolean mo444D(View view) {
            return view instanceof C0078v ? ((C0078v) view).isNestedScrollingEnabled() : false;
        }

        /* renamed from: E */
        public ColorStateList mo445E(View view) {
            return aj.m2838a(view);
        }

        /* renamed from: F */
        public Mode mo446F(View view) {
            return aj.m2842b(view);
        }

        /* renamed from: G */
        public void mo447G(View view) {
            if (view instanceof C0078v) {
                ((C0078v) view).stopNestedScroll();
            }
        }

        /* renamed from: H */
        public boolean mo448H(View view) {
            return aj.m2844c(view);
        }

        /* renamed from: I */
        public float mo449I(View view) {
            return mo518z(view) + mo516y(view);
        }

        /* renamed from: J */
        public boolean mo450J(View view) {
            return aj.m2847f(view);
        }

        /* renamed from: K */
        public boolean mo451K(View view) {
            return false;
        }

        /* renamed from: L */
        public Display mo452L(View view) {
            return aj.m2848g(view);
        }

        /* renamed from: a */
        public int mo453a(int i, int i2) {
            return i | i2;
        }

        /* renamed from: a */
        public int mo454a(int i, int i2, int i3) {
            return View.resolveSize(i, i2);
        }

        /* renamed from: a */
        long mo517a() {
            return 10;
        }

        /* renamed from: a */
        public be mo455a(View view, be beVar) {
            return beVar;
        }

        /* renamed from: a */
        public void mo456a(View view, float f) {
        }

        /* renamed from: a */
        public void mo457a(View view, int i, int i2) {
        }

        /* renamed from: a */
        public void mo458a(View view, int i, int i2, int i3, int i4) {
            view.setPadding(i, i2, i3, i4);
        }

        /* renamed from: a */
        public void mo459a(View view, int i, Paint paint) {
        }

        /* renamed from: a */
        public void mo460a(View view, ColorStateList colorStateList) {
            aj.m2840a(view, colorStateList);
        }

        /* renamed from: a */
        public void mo461a(View view, Mode mode) {
            aj.m2841a(view, mode);
        }

        /* renamed from: a */
        public void mo462a(View view, Drawable drawable) {
            view.setBackgroundDrawable(drawable);
        }

        /* renamed from: a */
        public void mo463a(View view, C0531e c0531e) {
        }

        /* renamed from: a */
        public void mo464a(View view, C0074a c0074a) {
        }

        /* renamed from: a */
        public void mo465a(View view, ab abVar) {
        }

        /* renamed from: a */
        public void mo466a(View view, C0081z c0081z) {
        }

        /* renamed from: a */
        public void mo467a(View view, AccessibilityEvent accessibilityEvent) {
        }

        /* renamed from: a */
        public void mo468a(View view, Runnable runnable) {
            view.postDelayed(runnable, mo517a());
        }

        /* renamed from: a */
        public void mo469a(View view, Runnable runnable, long j) {
            view.postDelayed(runnable, mo517a() + j);
        }

        /* renamed from: a */
        public void mo470a(View view, boolean z) {
        }

        /* renamed from: a */
        public void mo471a(ViewGroup viewGroup, boolean z) {
            if (f1317b == null) {
                try {
                    f1317b = ViewGroup.class.getDeclaredMethod("setChildrenDrawingOrderEnabled", new Class[]{Boolean.TYPE});
                } catch (Throwable e) {
                    Log.e("ViewCompat", "Unable to find childrenDrawingOrderEnabled", e);
                }
                f1317b.setAccessible(true);
            }
            try {
                f1317b.invoke(viewGroup, new Object[]{Boolean.valueOf(z)});
            } catch (Throwable e2) {
                Log.e("ViewCompat", "Unable to invoke childrenDrawingOrderEnabled", e2);
            } catch (Throwable e22) {
                Log.e("ViewCompat", "Unable to invoke childrenDrawingOrderEnabled", e22);
            } catch (Throwable e222) {
                Log.e("ViewCompat", "Unable to invoke childrenDrawingOrderEnabled", e222);
            }
        }

        /* renamed from: a */
        public boolean mo472a(View view) {
            return false;
        }

        /* renamed from: a */
        public boolean mo473a(View view, int i) {
            return (view instanceof ad) && m2596a((ad) view, i);
        }

        /* renamed from: a */
        public boolean mo474a(View view, int i, Bundle bundle) {
            return false;
        }

        /* renamed from: b */
        public be mo475b(View view, be beVar) {
            return beVar;
        }

        /* renamed from: b */
        public void mo476b(View view, float f) {
        }

        /* renamed from: b */
        public void mo477b(View view, boolean z) {
        }

        /* renamed from: b */
        public boolean mo478b(View view) {
            return false;
        }

        /* renamed from: b */
        public boolean mo479b(View view, int i) {
            return (view instanceof ad) && m2597b((ad) view, i);
        }

        /* renamed from: c */
        public void mo480c(View view) {
            view.invalidate();
        }

        /* renamed from: c */
        public void mo481c(View view, float f) {
        }

        /* renamed from: c */
        public void mo482c(View view, int i) {
        }

        /* renamed from: c */
        public void mo483c(View view, boolean z) {
        }

        /* renamed from: d */
        public int mo484d(View view) {
            return 0;
        }

        /* renamed from: d */
        public void mo485d(View view, float f) {
        }

        /* renamed from: d */
        public void mo486d(View view, int i) {
        }

        /* renamed from: e */
        public float mo487e(View view) {
            return 1.0f;
        }

        /* renamed from: e */
        public void mo488e(View view, float f) {
        }

        /* renamed from: e */
        public void mo489e(View view, int i) {
            aj.m2843b(view, i);
        }

        /* renamed from: f */
        public int mo490f(View view) {
            return 0;
        }

        /* renamed from: f */
        public void mo491f(View view, float f) {
        }

        /* renamed from: f */
        public void mo492f(View view, int i) {
            aj.m2839a(view, i);
        }

        /* renamed from: g */
        public int mo493g(View view) {
            return 0;
        }

        /* renamed from: g */
        public void mo494g(View view, float f) {
        }

        /* renamed from: h */
        public int mo495h(View view) {
            return view.getMeasuredWidth();
        }

        /* renamed from: h */
        public void mo496h(View view, float f) {
        }

        /* renamed from: i */
        public int mo497i(View view) {
            return 0;
        }

        /* renamed from: i */
        public void mo498i(View view, float f) {
        }

        /* renamed from: j */
        public int mo499j(View view) {
            return view.getPaddingLeft();
        }

        /* renamed from: j */
        public void mo500j(View view, float f) {
        }

        /* renamed from: k */
        public int mo501k(View view) {
            return view.getPaddingRight();
        }

        /* renamed from: k */
        public void mo502k(View view, float f) {
        }

        /* renamed from: l */
        public boolean mo503l(View view) {
            return true;
        }

        /* renamed from: m */
        public float mo504m(View view) {
            return BitmapDescriptorFactory.HUE_RED;
        }

        /* renamed from: n */
        public float mo505n(View view) {
            return BitmapDescriptorFactory.HUE_RED;
        }

        /* renamed from: o */
        public float mo506o(View view) {
            return (float) view.getLeft();
        }

        /* renamed from: p */
        public float mo507p(View view) {
            return (float) view.getTop();
        }

        /* renamed from: q */
        public float mo508q(View view) {
            return BitmapDescriptorFactory.HUE_RED;
        }

        /* renamed from: r */
        public Matrix mo509r(View view) {
            return null;
        }

        /* renamed from: s */
        public int mo510s(View view) {
            return aj.m2845d(view);
        }

        /* renamed from: t */
        public int mo511t(View view) {
            return aj.m2846e(view);
        }

        /* renamed from: u */
        public ax mo512u(View view) {
            return new ax(view);
        }

        /* renamed from: v */
        public String mo513v(View view) {
            return null;
        }

        /* renamed from: w */
        public int mo514w(View view) {
            return 0;
        }

        /* renamed from: x */
        public void mo515x(View view) {
        }

        /* renamed from: y */
        public float mo516y(View view) {
            return BitmapDescriptorFactory.HUE_RED;
        }

        /* renamed from: z */
        public float mo518z(View view) {
            return BitmapDescriptorFactory.HUE_RED;
        }
    }

    /* renamed from: android.support.v4.view.ah$c */
    static class C0568c extends C0567b {
        C0568c() {
        }

        /* renamed from: B */
        public void mo442B(View view) {
            ak.m2877k(view);
        }

        /* renamed from: a */
        public int mo453a(int i, int i2) {
            return ak.m2850a(i, i2);
        }

        /* renamed from: a */
        public int mo454a(int i, int i2, int i3) {
            return ak.m2851a(i, i2, i3);
        }

        /* renamed from: a */
        long mo517a() {
            return ak.m2852a();
        }

        /* renamed from: a */
        public void mo456a(View view, float f) {
            ak.m2864d(view, f);
        }

        /* renamed from: a */
        public void mo459a(View view, int i, Paint paint) {
            ak.m2855a(view, i, paint);
        }

        /* renamed from: b */
        public void mo476b(View view, float f) {
            ak.m2853a(view, f);
        }

        /* renamed from: b */
        public void mo477b(View view, boolean z) {
            ak.m2856a(view, z);
        }

        /* renamed from: c */
        public void mo481c(View view, float f) {
            ak.m2858b(view, f);
        }

        /* renamed from: c */
        public void mo483c(View view, boolean z) {
            ak.m2860b(view, z);
        }

        /* renamed from: d */
        public void mo485d(View view, float f) {
            ak.m2862c(view, f);
        }

        /* renamed from: e */
        public float mo487e(View view) {
            return ak.m2849a(view);
        }

        /* renamed from: e */
        public void mo488e(View view, float f) {
            ak.m2866e(view, f);
        }

        /* renamed from: e */
        public void mo489e(View view, int i) {
            ak.m2859b(view, i);
        }

        /* renamed from: f */
        public int mo490f(View view) {
            return ak.m2857b(view);
        }

        /* renamed from: f */
        public void mo491f(View view, float f) {
            ak.m2868f(view, f);
        }

        /* renamed from: f */
        public void mo492f(View view, int i) {
            ak.m2854a(view, i);
        }

        /* renamed from: g */
        public void mo494g(View view, float f) {
            ak.m2870g(view, f);
        }

        /* renamed from: h */
        public int mo495h(View view) {
            return ak.m2861c(view);
        }

        /* renamed from: h */
        public void mo496h(View view, float f) {
            ak.m2872h(view, f);
        }

        /* renamed from: i */
        public int mo497i(View view) {
            return ak.m2863d(view);
        }

        /* renamed from: i */
        public void mo498i(View view, float f) {
            ak.m2874i(view, f);
        }

        /* renamed from: j */
        public void mo500j(View view, float f) {
            ak.m2876j(view, f);
        }

        /* renamed from: m */
        public float mo504m(View view) {
            return ak.m2865e(view);
        }

        /* renamed from: n */
        public float mo505n(View view) {
            return ak.m2867f(view);
        }

        /* renamed from: o */
        public float mo506o(View view) {
            return ak.m2869g(view);
        }

        /* renamed from: p */
        public float mo507p(View view) {
            return ak.m2871h(view);
        }

        /* renamed from: q */
        public float mo508q(View view) {
            return ak.m2873i(view);
        }

        /* renamed from: r */
        public Matrix mo509r(View view) {
            return ak.m2875j(view);
        }
    }

    /* renamed from: android.support.v4.view.ah$e */
    static class C0569e extends C0568c {
        /* renamed from: b */
        static Field f1319b;
        /* renamed from: c */
        static boolean f1320c = false;

        C0569e() {
        }

        /* renamed from: a */
        public void mo463a(View view, C0531e c0531e) {
            al.m2883b(view, c0531e.m2304a());
        }

        /* renamed from: a */
        public void mo464a(View view, C0074a c0074a) {
            al.m2880a(view, c0074a == null ? null : c0074a.getBridge());
        }

        /* renamed from: a */
        public void mo467a(View view, AccessibilityEvent accessibilityEvent) {
            al.m2879a(view, accessibilityEvent);
        }

        /* renamed from: a */
        public void mo470a(View view, boolean z) {
            al.m2881a(view, z);
        }

        /* renamed from: a */
        public boolean mo472a(View view) {
            boolean z = true;
            if (f1320c) {
                return false;
            }
            if (f1319b == null) {
                try {
                    f1319b = View.class.getDeclaredField("mAccessibilityDelegate");
                    f1319b.setAccessible(true);
                } catch (Throwable th) {
                    f1320c = true;
                    return false;
                }
            }
            try {
                if (f1319b.get(view) == null) {
                    z = false;
                }
                return z;
            } catch (Throwable th2) {
                f1320c = true;
                return false;
            }
        }

        /* renamed from: a */
        public boolean mo473a(View view, int i) {
            return al.m2882a(view, i);
        }

        /* renamed from: b */
        public boolean mo479b(View view, int i) {
            return al.m2884b(view, i);
        }

        /* renamed from: u */
        public ax mo512u(View view) {
            if (this.a == null) {
                this.a = new WeakHashMap();
            }
            ax axVar = (ax) this.a.get(view);
            if (axVar != null) {
                return axVar;
            }
            axVar = new ax(view);
            this.a.put(view, axVar);
            return axVar;
        }
    }

    /* renamed from: android.support.v4.view.ah$d */
    static class C0570d extends C0569e {
        C0570d() {
        }

        /* renamed from: K */
        public boolean mo451K(View view) {
            return am.m2885a(view);
        }
    }

    /* renamed from: android.support.v4.view.ah$f */
    static class C0571f extends C0570d {
        C0571f() {
        }

        /* renamed from: A */
        public boolean mo441A(View view) {
            return an.m2897g(view);
        }

        /* renamed from: a */
        public void mo462a(View view, Drawable drawable) {
            an.m2887a(view, drawable);
        }

        /* renamed from: a */
        public void mo468a(View view, Runnable runnable) {
            an.m2888a(view, runnable);
        }

        /* renamed from: a */
        public void mo469a(View view, Runnable runnable, long j) {
            an.m2889a(view, runnable, j);
        }

        /* renamed from: a */
        public boolean mo474a(View view, int i, Bundle bundle) {
            return an.m2891a(view, i, bundle);
        }

        /* renamed from: b */
        public boolean mo478b(View view) {
            return an.m2890a(view);
        }

        /* renamed from: c */
        public void mo480c(View view) {
            an.m2892b(view);
        }

        /* renamed from: c */
        public void mo482c(View view, int i) {
            if (i == 4) {
                i = 2;
            }
            an.m2886a(view, i);
        }

        /* renamed from: d */
        public int mo484d(View view) {
            return an.m2893c(view);
        }

        /* renamed from: l */
        public boolean mo503l(View view) {
            return an.m2898h(view);
        }

        /* renamed from: s */
        public int mo510s(View view) {
            return an.m2894d(view);
        }

        /* renamed from: t */
        public int mo511t(View view) {
            return an.m2895e(view);
        }

        /* renamed from: x */
        public void mo515x(View view) {
            an.m2896f(view);
        }
    }

    /* renamed from: android.support.v4.view.ah$g */
    static class C0572g extends C0571f {
        C0572g() {
        }

        /* renamed from: C */
        public boolean mo443C(View view) {
            return ao.m2904e(view);
        }

        /* renamed from: L */
        public Display mo452L(View view) {
            return ao.m2905f(view);
        }

        /* renamed from: a */
        public void mo458a(View view, int i, int i2, int i3, int i4) {
            ao.m2900a(view, i, i2, i3, i4);
        }

        /* renamed from: g */
        public int mo493g(View view) {
            return ao.m2899a(view);
        }

        /* renamed from: j */
        public int mo499j(View view) {
            return ao.m2901b(view);
        }

        /* renamed from: k */
        public int mo501k(View view) {
            return ao.m2902c(view);
        }

        /* renamed from: w */
        public int mo514w(View view) {
            return ao.m2903d(view);
        }
    }

    /* renamed from: android.support.v4.view.ah$h */
    static class C0573h extends C0572g {
        C0573h() {
        }
    }

    /* renamed from: android.support.v4.view.ah$i */
    static class C0574i extends C0573h {
        C0574i() {
        }

        /* renamed from: H */
        public boolean mo448H(View view) {
            return ap.m2907a(view);
        }

        /* renamed from: J */
        public boolean mo450J(View view) {
            return ap.m2908b(view);
        }

        /* renamed from: c */
        public void mo482c(View view, int i) {
            an.m2886a(view, i);
        }

        /* renamed from: d */
        public void mo486d(View view, int i) {
            ap.m2906a(view, i);
        }
    }

    /* renamed from: android.support.v4.view.ah$j */
    static class C0575j extends C0574i {
        C0575j() {
        }

        /* renamed from: D */
        public boolean mo444D(View view) {
            return aq.m2924g(view);
        }

        /* renamed from: E */
        public ColorStateList mo445E(View view) {
            return aq.m2922e(view);
        }

        /* renamed from: F */
        public Mode mo446F(View view) {
            return aq.m2923f(view);
        }

        /* renamed from: G */
        public void mo447G(View view) {
            aq.m2925h(view);
        }

        /* renamed from: I */
        public float mo449I(View view) {
            return aq.m2926i(view);
        }

        /* renamed from: a */
        public be mo455a(View view, be beVar) {
            return be.m3074a(aq.m2910a(view, be.m3075a(beVar)));
        }

        /* renamed from: a */
        public void mo460a(View view, ColorStateList colorStateList) {
            aq.m2914a(view, colorStateList);
        }

        /* renamed from: a */
        public void mo461a(View view, Mode mode) {
            aq.m2915a(view, mode);
        }

        /* renamed from: a */
        public void mo466a(View view, final C0081z c0081z) {
            if (c0081z == null) {
                aq.m2916a(view, null);
            } else {
                aq.m2916a(view, new C0578a(this) {
                    /* renamed from: b */
                    final /* synthetic */ C0575j f1322b;

                    /* renamed from: a */
                    public Object mo519a(View view, Object obj) {
                        return be.m3075a(c0081z.mo57a(view, be.m3074a(obj)));
                    }
                });
            }
        }

        /* renamed from: b */
        public be mo475b(View view, be beVar) {
            return be.m3074a(aq.m2917b(view, be.m3075a(beVar)));
        }

        /* renamed from: e */
        public void mo489e(View view, int i) {
            aq.m2919b(view, i);
        }

        /* renamed from: f */
        public void mo492f(View view, int i) {
            aq.m2913a(view, i);
        }

        /* renamed from: k */
        public void mo502k(View view, float f) {
            aq.m2912a(view, f);
        }

        /* renamed from: v */
        public String mo513v(View view) {
            return aq.m2911a(view);
        }

        /* renamed from: x */
        public void mo515x(View view) {
            aq.m2918b(view);
        }

        /* renamed from: y */
        public float mo516y(View view) {
            return aq.m2920c(view);
        }

        /* renamed from: z */
        public float mo518z(View view) {
            return aq.m2921d(view);
        }
    }

    /* renamed from: android.support.v4.view.ah$k */
    static class C0576k extends C0575j {
        C0576k() {
        }

        /* renamed from: a */
        public void mo457a(View view, int i, int i2) {
            ar.m2928a(view, i, i2);
        }

        /* renamed from: e */
        public void mo489e(View view, int i) {
            ar.m2929b(view, i);
        }

        /* renamed from: f */
        public void mo492f(View view, int i) {
            ar.m2927a(view, i);
        }
    }

    /* renamed from: android.support.v4.view.ah$a */
    static class C0577a extends C0576k {
        C0577a() {
        }

        /* renamed from: a */
        public void mo465a(View view, ab abVar) {
            ai.m2837a(view, abVar != null ? abVar.m2508a() : null);
        }
    }

    static {
        int i = VERSION.SDK_INT;
        if (C0432c.m1912a()) {
            f1323a = new C0577a();
        } else if (i >= 23) {
            f1323a = new C0576k();
        } else if (i >= 21) {
            f1323a = new C0575j();
        } else if (i >= 19) {
            f1323a = new C0574i();
        } else if (i >= 18) {
            f1323a = new C0573h();
        } else if (i >= 17) {
            f1323a = new C0572g();
        } else if (i >= 16) {
            f1323a = new C0571f();
        } else if (i >= 15) {
            f1323a = new C0570d();
        } else if (i >= 14) {
            f1323a = new C0569e();
        } else if (i >= 11) {
            f1323a = new C0568c();
        } else {
            f1323a = new C0567b();
        }
    }

    /* renamed from: A */
    public static boolean m2761A(View view) {
        return f1323a.mo503l(view);
    }

    /* renamed from: B */
    public static boolean m2762B(View view) {
        return f1323a.mo443C(view);
    }

    /* renamed from: C */
    public static ColorStateList m2763C(View view) {
        return f1323a.mo445E(view);
    }

    /* renamed from: D */
    public static Mode m2764D(View view) {
        return f1323a.mo446F(view);
    }

    /* renamed from: E */
    public static boolean m2765E(View view) {
        return f1323a.mo444D(view);
    }

    /* renamed from: F */
    public static void m2766F(View view) {
        f1323a.mo447G(view);
    }

    /* renamed from: G */
    public static boolean m2767G(View view) {
        return f1323a.mo448H(view);
    }

    /* renamed from: H */
    public static float m2768H(View view) {
        return f1323a.mo449I(view);
    }

    /* renamed from: I */
    public static boolean m2769I(View view) {
        return f1323a.mo450J(view);
    }

    /* renamed from: J */
    public static boolean m2770J(View view) {
        return f1323a.mo451K(view);
    }

    /* renamed from: K */
    public static Display m2771K(View view) {
        return f1323a.mo452L(view);
    }

    /* renamed from: a */
    public static int m2772a(int i, int i2) {
        return f1323a.mo453a(i, i2);
    }

    /* renamed from: a */
    public static int m2773a(int i, int i2, int i3) {
        return f1323a.mo454a(i, i2, i3);
    }

    /* renamed from: a */
    public static be m2774a(View view, be beVar) {
        return f1323a.mo455a(view, beVar);
    }

    /* renamed from: a */
    public static void m2775a(View view, float f) {
        f1323a.mo476b(view, f);
    }

    /* renamed from: a */
    public static void m2776a(View view, int i, int i2) {
        f1323a.mo457a(view, i, i2);
    }

    /* renamed from: a */
    public static void m2777a(View view, int i, int i2, int i3, int i4) {
        f1323a.mo458a(view, i, i2, i3, i4);
    }

    /* renamed from: a */
    public static void m2778a(View view, int i, Paint paint) {
        f1323a.mo459a(view, i, paint);
    }

    /* renamed from: a */
    public static void m2779a(View view, ColorStateList colorStateList) {
        f1323a.mo460a(view, colorStateList);
    }

    /* renamed from: a */
    public static void m2780a(View view, Mode mode) {
        f1323a.mo461a(view, mode);
    }

    /* renamed from: a */
    public static void m2781a(View view, Drawable drawable) {
        f1323a.mo462a(view, drawable);
    }

    /* renamed from: a */
    public static void m2782a(View view, C0531e c0531e) {
        f1323a.mo463a(view, c0531e);
    }

    /* renamed from: a */
    public static void m2783a(View view, C0074a c0074a) {
        f1323a.mo464a(view, c0074a);
    }

    /* renamed from: a */
    public static void m2784a(View view, ab abVar) {
        f1323a.mo465a(view, abVar);
    }

    /* renamed from: a */
    public static void m2785a(View view, C0081z c0081z) {
        f1323a.mo466a(view, c0081z);
    }

    /* renamed from: a */
    public static void m2786a(View view, AccessibilityEvent accessibilityEvent) {
        f1323a.mo467a(view, accessibilityEvent);
    }

    /* renamed from: a */
    public static void m2787a(View view, Runnable runnable) {
        f1323a.mo468a(view, runnable);
    }

    /* renamed from: a */
    public static void m2788a(View view, Runnable runnable, long j) {
        f1323a.mo469a(view, runnable, j);
    }

    /* renamed from: a */
    public static void m2789a(View view, boolean z) {
        f1323a.mo470a(view, z);
    }

    /* renamed from: a */
    public static void m2790a(ViewGroup viewGroup, boolean z) {
        f1323a.mo471a(viewGroup, z);
    }

    /* renamed from: a */
    public static boolean m2791a(View view) {
        return f1323a.mo472a(view);
    }

    /* renamed from: a */
    public static boolean m2792a(View view, int i) {
        return f1323a.mo473a(view, i);
    }

    /* renamed from: a */
    public static boolean m2793a(View view, int i, Bundle bundle) {
        return f1323a.mo474a(view, i, bundle);
    }

    /* renamed from: b */
    public static be m2794b(View view, be beVar) {
        return f1323a.mo475b(view, beVar);
    }

    /* renamed from: b */
    public static void m2795b(View view, float f) {
        f1323a.mo481c(view, f);
    }

    /* renamed from: b */
    public static void m2796b(View view, boolean z) {
        f1323a.mo477b(view, z);
    }

    /* renamed from: b */
    public static boolean m2797b(View view) {
        return f1323a.mo478b(view);
    }

    /* renamed from: b */
    public static boolean m2798b(View view, int i) {
        return f1323a.mo479b(view, i);
    }

    /* renamed from: c */
    public static void m2799c(View view) {
        f1323a.mo480c(view);
    }

    /* renamed from: c */
    public static void m2800c(View view, float f) {
        f1323a.mo485d(view, f);
    }

    /* renamed from: c */
    public static void m2801c(View view, int i) {
        f1323a.mo482c(view, i);
    }

    /* renamed from: c */
    public static void m2802c(View view, boolean z) {
        f1323a.mo483c(view, z);
    }

    /* renamed from: d */
    public static int m2803d(View view) {
        return f1323a.mo484d(view);
    }

    /* renamed from: d */
    public static void m2804d(View view, float f) {
        f1323a.mo456a(view, f);
    }

    /* renamed from: d */
    public static void m2805d(View view, int i) {
        f1323a.mo486d(view, i);
    }

    /* renamed from: e */
    public static float m2806e(View view) {
        return f1323a.mo487e(view);
    }

    /* renamed from: e */
    public static void m2807e(View view, float f) {
        f1323a.mo488e(view, f);
    }

    /* renamed from: e */
    public static void m2808e(View view, int i) {
        f1323a.mo492f(view, i);
    }

    /* renamed from: f */
    public static int m2809f(View view) {
        return f1323a.mo490f(view);
    }

    /* renamed from: f */
    public static void m2810f(View view, float f) {
        f1323a.mo491f(view, f);
    }

    /* renamed from: f */
    public static void m2811f(View view, int i) {
        f1323a.mo489e(view, i);
    }

    /* renamed from: g */
    public static int m2812g(View view) {
        return f1323a.mo493g(view);
    }

    /* renamed from: g */
    public static void m2813g(View view, float f) {
        f1323a.mo494g(view, f);
    }

    /* renamed from: h */
    public static int m2814h(View view) {
        return f1323a.mo495h(view);
    }

    /* renamed from: h */
    public static void m2815h(View view, float f) {
        f1323a.mo496h(view, f);
    }

    /* renamed from: i */
    public static int m2816i(View view) {
        return f1323a.mo497i(view);
    }

    /* renamed from: i */
    public static void m2817i(View view, float f) {
        f1323a.mo498i(view, f);
    }

    /* renamed from: j */
    public static int m2818j(View view) {
        return f1323a.mo499j(view);
    }

    /* renamed from: j */
    public static void m2819j(View view, float f) {
        f1323a.mo500j(view, f);
    }

    /* renamed from: k */
    public static int m2820k(View view) {
        return f1323a.mo501k(view);
    }

    /* renamed from: k */
    public static void m2821k(View view, float f) {
        f1323a.mo502k(view, f);
    }

    /* renamed from: l */
    public static float m2822l(View view) {
        return f1323a.mo504m(view);
    }

    /* renamed from: m */
    public static float m2823m(View view) {
        return f1323a.mo505n(view);
    }

    /* renamed from: n */
    public static Matrix m2824n(View view) {
        return f1323a.mo509r(view);
    }

    /* renamed from: o */
    public static int m2825o(View view) {
        return f1323a.mo510s(view);
    }

    /* renamed from: p */
    public static int m2826p(View view) {
        return f1323a.mo511t(view);
    }

    /* renamed from: q */
    public static ax m2827q(View view) {
        return f1323a.mo512u(view);
    }

    /* renamed from: r */
    public static float m2828r(View view) {
        return f1323a.mo508q(view);
    }

    /* renamed from: s */
    public static float m2829s(View view) {
        return f1323a.mo506o(view);
    }

    /* renamed from: t */
    public static float m2830t(View view) {
        return f1323a.mo507p(view);
    }

    /* renamed from: u */
    public static float m2831u(View view) {
        return f1323a.mo516y(view);
    }

    /* renamed from: v */
    public static String m2832v(View view) {
        return f1323a.mo513v(view);
    }

    /* renamed from: w */
    public static int m2833w(View view) {
        return f1323a.mo514w(view);
    }

    /* renamed from: x */
    public static void m2834x(View view) {
        f1323a.mo515x(view);
    }

    /* renamed from: y */
    public static boolean m2835y(View view) {
        return f1323a.mo441A(view);
    }

    /* renamed from: z */
    public static void m2836z(View view) {
        f1323a.mo442B(view);
    }
}
