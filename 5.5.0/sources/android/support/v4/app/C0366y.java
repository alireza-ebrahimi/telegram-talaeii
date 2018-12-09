package android.support.v4.app;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.support.v4.app.C0357x.C0355a;
import android.support.v4.app.C0357x.C0356b;
import android.support.v4.app.Fragment.C0230c;
import android.support.v4.p014d.C0432c;
import android.support.v4.p022f.C0466b;
import android.support.v4.p022f.C0468d;
import android.support.v4.p022f.C0469e;
import android.support.v4.p022f.C0477i;
import android.support.v4.view.C0365n;
import android.support.v4.view.ah;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: android.support.v4.app.y */
final class C0366y extends C0357x implements C0365n {
    /* renamed from: D */
    static final Interpolator f1106D = new DecelerateInterpolator(2.5f);
    /* renamed from: E */
    static final Interpolator f1107E = new DecelerateInterpolator(1.5f);
    /* renamed from: F */
    static final Interpolator f1108F = new AccelerateInterpolator(2.5f);
    /* renamed from: G */
    static final Interpolator f1109G = new AccelerateInterpolator(1.5f);
    /* renamed from: a */
    static boolean f1110a = false;
    /* renamed from: b */
    static final boolean f1111b;
    /* renamed from: q */
    static Field f1112q = null;
    /* renamed from: A */
    SparseArray<Parcelable> f1113A = null;
    /* renamed from: B */
    ArrayList<C0364e> f1114B;
    /* renamed from: C */
    Runnable f1115C = new C03581(this);
    /* renamed from: H */
    private CopyOnWriteArrayList<C0477i<C0355a, Boolean>> f1116H;
    /* renamed from: c */
    ArrayList<C0340c> f1117c;
    /* renamed from: d */
    boolean f1118d;
    /* renamed from: e */
    ArrayList<Fragment> f1119e;
    /* renamed from: f */
    ArrayList<Fragment> f1120f;
    /* renamed from: g */
    ArrayList<Integer> f1121g;
    /* renamed from: h */
    ArrayList<C0341l> f1122h;
    /* renamed from: i */
    ArrayList<Fragment> f1123i;
    /* renamed from: j */
    ArrayList<C0341l> f1124j;
    /* renamed from: k */
    ArrayList<Integer> f1125k;
    /* renamed from: l */
    ArrayList<C0356b> f1126l;
    /* renamed from: m */
    int f1127m = 0;
    /* renamed from: n */
    C0350w f1128n;
    /* renamed from: o */
    C0225u f1129o;
    /* renamed from: p */
    Fragment f1130p;
    /* renamed from: r */
    boolean f1131r;
    /* renamed from: s */
    boolean f1132s;
    /* renamed from: t */
    boolean f1133t;
    /* renamed from: u */
    String f1134u;
    /* renamed from: v */
    boolean f1135v;
    /* renamed from: w */
    ArrayList<C0341l> f1136w;
    /* renamed from: x */
    ArrayList<Boolean> f1137x;
    /* renamed from: y */
    ArrayList<Fragment> f1138y;
    /* renamed from: z */
    Bundle f1139z = null;

    /* renamed from: android.support.v4.app.y$c */
    interface C0340c {
        /* renamed from: a */
        boolean mo248a(ArrayList<C0341l> arrayList, ArrayList<Boolean> arrayList2);
    }

    /* renamed from: android.support.v4.app.y$1 */
    class C03581 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C0366y f1091a;

        C03581(C0366y c0366y) {
            this.f1091a = c0366y;
        }

        public void run() {
            this.f1091a.m1686e();
        }
    }

    /* renamed from: android.support.v4.app.y$a */
    static class C0359a implements AnimationListener {
        /* renamed from: a */
        private AnimationListener f1092a;
        /* renamed from: b */
        private boolean f1093b;
        /* renamed from: c */
        View f1094c;

        /* renamed from: android.support.v4.app.y$a$1 */
        class C03611 implements Runnable {
            /* renamed from: a */
            final /* synthetic */ C0359a f1097a;

            C03611(C0359a c0359a) {
                this.f1097a = c0359a;
            }

            public void run() {
                ah.m2778a(this.f1097a.f1094c, 0, null);
            }
        }

        public C0359a(View view, Animation animation) {
            if (view != null && animation != null) {
                this.f1094c = view;
            }
        }

        public C0359a(View view, Animation animation, AnimationListener animationListener) {
            if (view != null && animation != null) {
                this.f1092a = animationListener;
                this.f1094c = view;
                this.f1093b = true;
            }
        }

        public void onAnimationEnd(Animation animation) {
            if (this.f1094c != null && this.f1093b) {
                if (ah.m2769I(this.f1094c) || C0432c.m1912a()) {
                    this.f1094c.post(new C03611(this));
                } else {
                    ah.m2778a(this.f1094c, 0, null);
                }
            }
            if (this.f1092a != null) {
                this.f1092a.onAnimationEnd(animation);
            }
        }

        public void onAnimationRepeat(Animation animation) {
            if (this.f1092a != null) {
                this.f1092a.onAnimationRepeat(animation);
            }
        }

        public void onAnimationStart(Animation animation) {
            if (this.f1092a != null) {
                this.f1092a.onAnimationStart(animation);
            }
        }
    }

    /* renamed from: android.support.v4.app.y$b */
    static class C0362b {
        /* renamed from: a */
        public static final int[] f1098a = new int[]{16842755, 16842960, 16842961};
    }

    /* renamed from: android.support.v4.app.y$d */
    private class C0363d implements C0340c {
        /* renamed from: a */
        final String f1099a;
        /* renamed from: b */
        final int f1100b;
        /* renamed from: c */
        final int f1101c;
        /* renamed from: d */
        final /* synthetic */ C0366y f1102d;

        C0363d(C0366y c0366y, String str, int i, int i2) {
            this.f1102d = c0366y;
            this.f1099a = str;
            this.f1100b = i;
            this.f1101c = i2;
        }

        /* renamed from: a */
        public boolean mo248a(ArrayList<C0341l> arrayList, ArrayList<Boolean> arrayList2) {
            return this.f1102d.m1664a((ArrayList) arrayList, (ArrayList) arrayList2, this.f1099a, this.f1100b, this.f1101c);
        }
    }

    /* renamed from: android.support.v4.app.y$e */
    static class C0364e implements C0230c {
        /* renamed from: a */
        private final boolean f1103a;
        /* renamed from: b */
        private final C0341l f1104b;
        /* renamed from: c */
        private int f1105c;

        C0364e(C0341l c0341l, boolean z) {
            this.f1103a = z;
            this.f1104b = c0341l;
        }

        /* renamed from: a */
        public void mo281a() {
            this.f1105c--;
            if (this.f1105c == 0) {
                this.f1104b.f1038b.m1634w();
            }
        }

        /* renamed from: b */
        public void mo282b() {
            this.f1105c++;
        }

        /* renamed from: c */
        public boolean m1607c() {
            return this.f1105c == 0;
        }

        /* renamed from: d */
        public void m1608d() {
            boolean z = false;
            boolean z2 = this.f1105c > 0;
            C0366y c0366y = this.f1104b.f1038b;
            int size = c0366y.f1120f.size();
            for (int i = 0; i < size; i++) {
                Fragment fragment = (Fragment) c0366y.f1120f.get(i);
                fragment.setOnStartEnterTransitionListener(null);
                if (z2 && fragment.isPostponed()) {
                    fragment.startPostponedEnterTransition();
                }
            }
            C0366y c0366y2 = this.f1104b.f1038b;
            C0341l c0341l = this.f1104b;
            boolean z3 = this.f1103a;
            if (!z2) {
                z = true;
            }
            c0366y2.m1614a(c0341l, z3, z, true);
        }

        /* renamed from: e */
        public void m1609e() {
            this.f1104b.f1038b.m1614a(this.f1104b, this.f1103a, false, false);
        }
    }

    static {
        boolean z = false;
        if (VERSION.SDK_INT >= 11) {
            z = true;
        }
        f1111b = z;
    }

    C0366y() {
    }

    /* renamed from: a */
    private int m1611a(ArrayList<C0341l> arrayList, ArrayList<Boolean> arrayList2, int i, int i2, C0466b<Fragment> c0466b) {
        int i3 = i2 - 1;
        int i4 = i2;
        while (i3 >= i) {
            int i5;
            C0341l c0341l = (C0341l) arrayList.get(i3);
            boolean booleanValue = ((Boolean) arrayList2.get(i3)).booleanValue();
            boolean z = c0341l.m1480d() && !c0341l.m1473a((ArrayList) arrayList, i3 + 1, i2);
            if (z) {
                if (this.f1114B == null) {
                    this.f1114B = new ArrayList();
                }
                C0230c c0364e = new C0364e(c0341l, booleanValue);
                this.f1114B.add(c0364e);
                c0341l.m1468a(c0364e);
                if (booleanValue) {
                    c0341l.m1479c();
                } else {
                    c0341l.m1477b(false);
                }
                int i6 = i4 - 1;
                if (i3 != i6) {
                    arrayList.remove(i3);
                    arrayList.add(i6, c0341l);
                }
                m1625b((C0466b) c0466b);
                i5 = i6;
            } else {
                i5 = i4;
            }
            i3--;
            i4 = i5;
        }
        return i4;
    }

    /* renamed from: a */
    static Animation m1612a(Context context, float f, float f2) {
        Animation alphaAnimation = new AlphaAnimation(f, f2);
        alphaAnimation.setInterpolator(f1107E);
        alphaAnimation.setDuration(220);
        return alphaAnimation;
    }

    /* renamed from: a */
    static Animation m1613a(Context context, float f, float f2, float f3, float f4) {
        Animation animationSet = new AnimationSet(false);
        Animation scaleAnimation = new ScaleAnimation(f, f2, f, f2, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setInterpolator(f1106D);
        scaleAnimation.setDuration(220);
        animationSet.addAnimation(scaleAnimation);
        scaleAnimation = new AlphaAnimation(f3, f4);
        scaleAnimation.setInterpolator(f1107E);
        scaleAnimation.setDuration(220);
        animationSet.addAnimation(scaleAnimation);
        return animationSet;
    }

    /* renamed from: a */
    private void m1614a(C0341l c0341l, boolean z, boolean z2, boolean z3) {
        ArrayList arrayList = new ArrayList(1);
        ArrayList arrayList2 = new ArrayList(1);
        arrayList.add(c0341l);
        arrayList2.add(Boolean.valueOf(z));
        C0366y.m1628b(arrayList, arrayList2, 0, 1);
        if (z2) {
            ab.m1101a(this, arrayList, arrayList2, 0, 1, true);
        }
        if (z3) {
            m1646a(this.f1127m, true);
        }
        if (this.f1119e != null) {
            int size = this.f1119e.size();
            for (int i = 0; i < size; i++) {
                Fragment fragment = (Fragment) this.f1119e.get(i);
                if (fragment != null && fragment.mView != null && fragment.mIsNewlyAdded && c0341l.m1478b(fragment.mContainerId)) {
                    if (VERSION.SDK_INT >= 11 && fragment.mPostponedAlpha > BitmapDescriptorFactory.HUE_RED) {
                        fragment.mView.setAlpha(fragment.mPostponedAlpha);
                    }
                    if (z3) {
                        fragment.mPostponedAlpha = BitmapDescriptorFactory.HUE_RED;
                    } else {
                        fragment.mPostponedAlpha = -1.0f;
                        fragment.mIsNewlyAdded = false;
                    }
                }
            }
        }
    }

    /* renamed from: a */
    private void m1617a(C0466b<Fragment> c0466b) {
        int size = c0466b.size();
        for (int i = 0; i < size; i++) {
            Fragment fragment = (Fragment) c0466b.m2006b(i);
            if (!fragment.mAdded) {
                View view = fragment.getView();
                if (VERSION.SDK_INT < 11) {
                    fragment.getView().setVisibility(4);
                } else {
                    fragment.mPostponedAlpha = view.getAlpha();
                    view.setAlpha(BitmapDescriptorFactory.HUE_RED);
                }
            }
        }
    }

    /* renamed from: a */
    private void m1618a(RuntimeException runtimeException) {
        Log.e("FragmentManager", runtimeException.getMessage());
        Log.e("FragmentManager", "Activity state:");
        PrintWriter printWriter = new PrintWriter(new C0469e("FragmentManager"));
        if (this.f1128n != null) {
            try {
                this.f1128n.mo262a("  ", null, printWriter, new String[0]);
            } catch (Throwable e) {
                Log.e("FragmentManager", "Failed dumping state", e);
            }
        } else {
            try {
                mo287a("  ", null, printWriter, new String[0]);
            } catch (Throwable e2) {
                Log.e("FragmentManager", "Failed dumping state", e2);
            }
        }
        throw runtimeException;
    }

    /* renamed from: a */
    private void m1619a(ArrayList<C0341l> arrayList, ArrayList<Boolean> arrayList2) {
        int i = 0;
        int size = this.f1114B == null ? 0 : this.f1114B.size();
        while (i < size) {
            int indexOf;
            int i2;
            C0364e c0364e = (C0364e) this.f1114B.get(i);
            if (!(arrayList == null || c0364e.f1103a)) {
                indexOf = arrayList.indexOf(c0364e.f1104b);
                if (indexOf != -1 && ((Boolean) arrayList2.get(indexOf)).booleanValue()) {
                    c0364e.m1609e();
                    i2 = i;
                    indexOf = size;
                    i = i2 + 1;
                    size = indexOf;
                }
            }
            if (c0364e.m1607c() || (arrayList != null && c0364e.f1104b.m1473a((ArrayList) arrayList, 0, arrayList.size()))) {
                this.f1114B.remove(i);
                i--;
                size--;
                if (!(arrayList == null || c0364e.f1103a)) {
                    indexOf = arrayList.indexOf(c0364e.f1104b);
                    if (indexOf != -1 && ((Boolean) arrayList2.get(indexOf)).booleanValue()) {
                        c0364e.m1609e();
                        i2 = i;
                        indexOf = size;
                        i = i2 + 1;
                        size = indexOf;
                    }
                }
                c0364e.m1608d();
            }
            i2 = i;
            indexOf = size;
            i = i2 + 1;
            size = indexOf;
        }
    }

    /* renamed from: a */
    private void m1620a(ArrayList<C0341l> arrayList, ArrayList<Boolean> arrayList2, int i, int i2) {
        int a;
        boolean z = ((C0341l) arrayList.get(i)).f1057u;
        if (this.f1138y == null) {
            this.f1138y = new ArrayList();
        } else {
            this.f1138y.clear();
        }
        if (this.f1120f != null) {
            this.f1138y.addAll(this.f1120f);
        }
        int i3 = i;
        boolean z2 = false;
        while (i3 < i2) {
            C0341l c0341l = (C0341l) arrayList.get(i3);
            if (((Boolean) arrayList2.get(i3)).booleanValue()) {
                c0341l.m1476b(this.f1138y);
            } else {
                c0341l.m1472a(this.f1138y);
            }
            boolean z3 = z2 || c0341l.f1046j;
            i3++;
            z2 = z3;
        }
        this.f1138y.clear();
        if (!z) {
            ab.m1101a(this, arrayList, arrayList2, i, i2, false);
        }
        C0366y.m1628b(arrayList, arrayList2, i, i2);
        if (z) {
            C0466b c0466b = new C0466b();
            m1625b(c0466b);
            a = m1611a((ArrayList) arrayList, (ArrayList) arrayList2, i, i2, c0466b);
            m1617a(c0466b);
        } else {
            a = i2;
        }
        if (a != i && z) {
            ab.m1101a(this, arrayList, arrayList2, i, a, true);
            m1646a(this.f1127m, true);
        }
        while (i < i2) {
            c0341l = (C0341l) arrayList.get(i);
            if (((Boolean) arrayList2.get(i)).booleanValue() && c0341l.f1050n >= 0) {
                m1677c(c0341l.f1050n);
                c0341l.f1050n = -1;
            }
            i++;
        }
        if (z2) {
            m1690g();
        }
    }

    /* renamed from: a */
    static boolean m1621a(View view, Animation animation) {
        return VERSION.SDK_INT >= 19 && ah.m2809f(view) == 0 && ah.m2761A(view) && C0366y.m1622a(animation);
    }

    /* renamed from: a */
    static boolean m1622a(Animation animation) {
        if (animation instanceof AlphaAnimation) {
            return true;
        }
        if (!(animation instanceof AnimationSet)) {
            return false;
        }
        List animations = ((AnimationSet) animation).getAnimations();
        for (int i = 0; i < animations.size(); i++) {
            if (animations.get(i) instanceof AlphaAnimation) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: a */
    private boolean m1623a(String str, int i, int i2) {
        m1686e();
        m1629c(true);
        boolean a = m1664a(this.f1136w, this.f1137x, str, i, i2);
        if (a) {
            this.f1118d = true;
            try {
                m1627b(this.f1136w, this.f1137x);
            } finally {
                m1635x();
            }
        }
        m1687f();
        return a;
    }

    /* renamed from: b */
    public static int m1624b(int i, boolean z) {
        switch (i) {
            case 4097:
                return z ? 1 : 2;
            case 4099:
                return z ? 5 : 6;
            case 8194:
                return z ? 3 : 4;
            default:
                return -1;
        }
    }

    /* renamed from: b */
    private void m1625b(C0466b<Fragment> c0466b) {
        if (this.f1127m >= 1) {
            int min = Math.min(this.f1127m, 4);
            boolean size = this.f1120f == null ? false : this.f1120f.size();
            for (boolean z = false; z < size; z++) {
                Fragment fragment = (Fragment) this.f1120f.get(z);
                if (fragment.mState < min) {
                    m1651a(fragment, min, fragment.getNextAnim(), fragment.getNextTransition(), false);
                    if (!(fragment.mView == null || fragment.mHidden || !fragment.mIsNewlyAdded)) {
                        c0466b.add(fragment);
                    }
                }
            }
        }
    }

    /* renamed from: b */
    private void m1626b(View view, Animation animation) {
        if (view != null && animation != null && C0366y.m1621a(view, animation)) {
            AnimationListener animationListener;
            try {
                if (f1112q == null) {
                    f1112q = Animation.class.getDeclaredField("mListener");
                    f1112q.setAccessible(true);
                }
                animationListener = (AnimationListener) f1112q.get(animation);
            } catch (Throwable e) {
                Log.e("FragmentManager", "No field with the name mListener is found in Animation class", e);
                animationListener = null;
            } catch (Throwable e2) {
                Log.e("FragmentManager", "Cannot access Animation's mListener field", e2);
                animationListener = null;
            }
            ah.m2778a(view, 2, null);
            animation.setAnimationListener(new C0359a(view, animation, animationListener));
        }
    }

    /* renamed from: b */
    private void m1627b(ArrayList<C0341l> arrayList, ArrayList<Boolean> arrayList2) {
        int i = 0;
        if (arrayList != null && !arrayList.isEmpty()) {
            if (arrayList2 == null || arrayList.size() != arrayList2.size()) {
                throw new IllegalStateException("Internal error with the back stack records");
            }
            m1619a((ArrayList) arrayList, (ArrayList) arrayList2);
            int size = arrayList.size();
            int i2 = 0;
            while (i < size) {
                int i3;
                if (((C0341l) arrayList.get(i)).f1057u) {
                    i3 = i;
                } else {
                    if (i2 != i) {
                        m1620a((ArrayList) arrayList, (ArrayList) arrayList2, i2, i);
                    }
                    i2 = i + 1;
                    if (((Boolean) arrayList2.get(i)).booleanValue()) {
                        while (i2 < size && ((Boolean) arrayList2.get(i2)).booleanValue() && !((C0341l) arrayList.get(i2)).f1057u) {
                            i2++;
                        }
                    }
                    i3 = i2;
                    m1620a((ArrayList) arrayList, (ArrayList) arrayList2, i, i3);
                    i2 = i3;
                    i3--;
                }
                i = i3 + 1;
            }
            if (i2 != size) {
                m1620a((ArrayList) arrayList, (ArrayList) arrayList2, i2, size);
            }
        }
    }

    /* renamed from: b */
    private static void m1628b(ArrayList<C0341l> arrayList, ArrayList<Boolean> arrayList2, int i, int i2) {
        while (i < i2) {
            C0341l c0341l = (C0341l) arrayList.get(i);
            if (((Boolean) arrayList2.get(i)).booleanValue()) {
                c0341l.m1467a(-1);
                c0341l.m1477b(i == i2 + -1);
            } else {
                c0341l.m1467a(1);
                c0341l.m1479c();
            }
            i++;
        }
    }

    /* renamed from: c */
    private void m1629c(boolean z) {
        if (this.f1118d) {
            throw new IllegalStateException("FragmentManager is already executing transactions");
        } else if (Looper.myLooper() != this.f1128n.m1511j().getLooper()) {
            throw new IllegalStateException("Must be called from main thread of fragment host");
        } else {
            if (!z) {
                m1633v();
            }
            if (this.f1136w == null) {
                this.f1136w = new ArrayList();
                this.f1137x = new ArrayList();
            }
            this.f1118d = true;
            try {
                m1619a(null, null);
            } finally {
                this.f1118d = false;
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: c */
    private boolean m1630c(java.util.ArrayList<android.support.v4.app.C0341l> r5, java.util.ArrayList<java.lang.Boolean> r6) {
        /*
        r4 = this;
        r1 = 0;
        monitor-enter(r4);
        r0 = r4.f1117c;	 Catch:{ all -> 0x003e }
        if (r0 == 0) goto L_0x000e;
    L_0x0006:
        r0 = r4.f1117c;	 Catch:{ all -> 0x003e }
        r0 = r0.size();	 Catch:{ all -> 0x003e }
        if (r0 != 0) goto L_0x0011;
    L_0x000e:
        monitor-exit(r4);	 Catch:{ all -> 0x003e }
        r0 = r1;
    L_0x0010:
        return r0;
    L_0x0011:
        r0 = r4.f1117c;	 Catch:{ all -> 0x003e }
        r3 = r0.size();	 Catch:{ all -> 0x003e }
        r2 = r1;
    L_0x0018:
        if (r2 >= r3) goto L_0x0029;
    L_0x001a:
        r0 = r4.f1117c;	 Catch:{ all -> 0x003e }
        r0 = r0.get(r2);	 Catch:{ all -> 0x003e }
        r0 = (android.support.v4.app.C0366y.C0340c) r0;	 Catch:{ all -> 0x003e }
        r0.mo248a(r5, r6);	 Catch:{ all -> 0x003e }
        r0 = r2 + 1;
        r2 = r0;
        goto L_0x0018;
    L_0x0029:
        r0 = r4.f1117c;	 Catch:{ all -> 0x003e }
        r0.clear();	 Catch:{ all -> 0x003e }
        r0 = r4.f1128n;	 Catch:{ all -> 0x003e }
        r0 = r0.m1511j();	 Catch:{ all -> 0x003e }
        r2 = r4.f1115C;	 Catch:{ all -> 0x003e }
        r0.removeCallbacks(r2);	 Catch:{ all -> 0x003e }
        monitor-exit(r4);	 Catch:{ all -> 0x003e }
        if (r3 <= 0) goto L_0x0041;
    L_0x003c:
        r0 = 1;
        goto L_0x0010;
    L_0x003e:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x003e }
        throw r0;
    L_0x0041:
        r0 = r1;
        goto L_0x0010;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.y.c(java.util.ArrayList, java.util.ArrayList):boolean");
    }

    /* renamed from: d */
    public static int m1631d(int i) {
        switch (i) {
            case 4097:
                return 8194;
            case 4099:
                return 4099;
            case 8194:
                return 4097;
            default:
                return 0;
        }
    }

    /* renamed from: n */
    private Fragment m1632n(Fragment fragment) {
        ViewGroup viewGroup = fragment.mContainer;
        View view = fragment.mView;
        if (viewGroup == null || view == null) {
            return null;
        }
        for (int indexOf = this.f1120f.indexOf(fragment) - 1; indexOf >= 0; indexOf--) {
            Fragment fragment2 = (Fragment) this.f1120f.get(indexOf);
            if (fragment2.mContainer == viewGroup && fragment2.mView != null) {
                return fragment2;
            }
        }
        return null;
    }

    /* renamed from: v */
    private void m1633v() {
        if (this.f1132s) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        } else if (this.f1134u != null) {
            throw new IllegalStateException("Can not perform this action inside of " + this.f1134u);
        }
    }

    /* renamed from: w */
    private void m1634w() {
        Object obj = 1;
        synchronized (this) {
            Object obj2 = (this.f1114B == null || this.f1114B.isEmpty()) ? null : 1;
            if (this.f1117c == null || this.f1117c.size() != 1) {
                obj = null;
            }
            if (!(obj2 == null && r0 == null)) {
                this.f1128n.m1511j().removeCallbacks(this.f1115C);
                this.f1128n.m1511j().post(this.f1115C);
            }
        }
    }

    /* renamed from: x */
    private void m1635x() {
        this.f1118d = false;
        this.f1137x.clear();
        this.f1136w.clear();
    }

    /* renamed from: y */
    private void m1636y() {
        if (this.f1114B != null) {
            while (!this.f1114B.isEmpty()) {
                ((C0364e) this.f1114B.remove(0)).m1608d();
            }
        }
    }

    /* renamed from: z */
    private void m1637z() {
        int size = this.f1119e == null ? 0 : this.f1119e.size();
        for (int i = 0; i < size; i++) {
            Fragment fragment = (Fragment) this.f1119e.get(i);
            if (!(fragment == null || fragment.getAnimatingAway() == null)) {
                int stateAfterAnimating = fragment.getStateAfterAnimating();
                View animatingAway = fragment.getAnimatingAway();
                fragment.setAnimatingAway(null);
                Animation animation = animatingAway.getAnimation();
                if (animation != null) {
                    animation.cancel();
                }
                m1651a(fragment, stateAfterAnimating, 0, 0, false);
            }
        }
    }

    /* renamed from: a */
    public int m1638a(C0341l c0341l) {
        int size;
        synchronized (this) {
            if (this.f1125k == null || this.f1125k.size() <= 0) {
                if (this.f1124j == null) {
                    this.f1124j = new ArrayList();
                }
                size = this.f1124j.size();
                if (f1110a) {
                    Log.v("FragmentManager", "Setting back stack index " + size + " to " + c0341l);
                }
                this.f1124j.add(c0341l);
            } else {
                size = ((Integer) this.f1125k.remove(this.f1125k.size() - 1)).intValue();
                if (f1110a) {
                    Log.v("FragmentManager", "Adding back stack index " + size + " with " + c0341l);
                }
                this.f1124j.set(size, c0341l);
            }
        }
        return size;
    }

    /* renamed from: a */
    public Fragment m1639a(Bundle bundle, String str) {
        int i = bundle.getInt(str, -1);
        if (i == -1) {
            return null;
        }
        if (i >= this.f1119e.size()) {
            m1618a(new IllegalStateException("Fragment no longer exists for key " + str + ": index " + i));
        }
        Fragment fragment = (Fragment) this.f1119e.get(i);
        if (fragment != null) {
            return fragment;
        }
        m1618a(new IllegalStateException("Fragment no longer exists for key " + str + ": index " + i));
        return fragment;
    }

    /* renamed from: a */
    public Fragment mo283a(String str) {
        int size;
        Fragment fragment;
        if (!(this.f1120f == null || str == null)) {
            for (size = this.f1120f.size() - 1; size >= 0; size--) {
                fragment = (Fragment) this.f1120f.get(size);
                if (fragment != null && str.equals(fragment.mTag)) {
                    return fragment;
                }
            }
        }
        if (!(this.f1119e == null || str == null)) {
            for (size = this.f1119e.size() - 1; size >= 0; size--) {
                fragment = (Fragment) this.f1119e.get(size);
                if (fragment != null && str.equals(fragment.mTag)) {
                    return fragment;
                }
            }
        }
        return null;
    }

    /* renamed from: a */
    public aa mo284a() {
        return new C0341l(this);
    }

    /* renamed from: a */
    public View mo285a(View view, String str, Context context, AttributeSet attributeSet) {
        if (!"fragment".equals(str)) {
            return null;
        }
        String attributeValue = attributeSet.getAttributeValue(null, "class");
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0362b.f1098a);
        String string = attributeValue == null ? obtainStyledAttributes.getString(0) : attributeValue;
        int resourceId = obtainStyledAttributes.getResourceId(1, -1);
        String string2 = obtainStyledAttributes.getString(2);
        obtainStyledAttributes.recycle();
        if (!Fragment.isSupportFragmentClass(this.f1128n.m1510i(), string)) {
            return null;
        }
        int id = view != null ? view.getId() : 0;
        if (id == -1 && resourceId == -1 && string2 == null) {
            throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Must specify unique android:id, android:tag, or have a parent with an id for " + string);
        }
        Fragment fragment;
        Fragment b = resourceId != -1 ? m1665b(resourceId) : null;
        if (b == null && string2 != null) {
            b = mo283a(string2);
        }
        if (b == null && id != -1) {
            b = m1665b(id);
        }
        if (f1110a) {
            Log.v("FragmentManager", "onCreateView: id=0x" + Integer.toHexString(resourceId) + " fname=" + string + " existing=" + b);
        }
        if (b == null) {
            Fragment instantiate = Fragment.instantiate(context, string);
            instantiate.mFromLayout = true;
            instantiate.mFragmentId = resourceId != 0 ? resourceId : id;
            instantiate.mContainerId = id;
            instantiate.mTag = string2;
            instantiate.mInLayout = true;
            instantiate.mFragmentManager = this;
            instantiate.mHost = this.f1128n;
            instantiate.onInflate(this.f1128n.m1510i(), attributeSet, instantiate.mSavedFragmentState);
            m1655a(instantiate, true);
            fragment = instantiate;
        } else if (b.mInLayout) {
            throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Duplicate id 0x" + Integer.toHexString(resourceId) + ", tag " + string2 + ", or parent id 0x" + Integer.toHexString(id) + " with another fragment for " + string);
        } else {
            b.mInLayout = true;
            b.mHost = this.f1128n;
            if (!b.mRetaining) {
                b.onInflate(this.f1128n.m1510i(), attributeSet, b.mSavedFragmentState);
            }
            fragment = b;
        }
        if (this.f1127m >= 1 || !fragment.mFromLayout) {
            m1667b(fragment);
        } else {
            m1651a(fragment, 1, 0, 0, false);
        }
        if (fragment.mView == null) {
            throw new IllegalStateException("Fragment " + string + " did not create a view.");
        }
        if (resourceId != 0) {
            fragment.mView.setId(resourceId);
        }
        if (fragment.mView.getTag() == null) {
            fragment.mView.setTag(string2);
        }
        return fragment.mView;
    }

    /* renamed from: a */
    Animation m1643a(Fragment fragment, int i, boolean z, int i2) {
        Animation onCreateAnimation = fragment.onCreateAnimation(i, z, fragment.getNextAnim());
        if (onCreateAnimation != null) {
            return onCreateAnimation;
        }
        if (fragment.getNextAnim() != 0) {
            onCreateAnimation = AnimationUtils.loadAnimation(this.f1128n.m1510i(), fragment.getNextAnim());
            if (onCreateAnimation != null) {
                return onCreateAnimation;
            }
        }
        if (i == 0) {
            return null;
        }
        int b = C0366y.m1624b(i, z);
        if (b < 0) {
            return null;
        }
        switch (b) {
            case 1:
                return C0366y.m1613a(this.f1128n.m1510i(), 1.125f, 1.0f, (float) BitmapDescriptorFactory.HUE_RED, 1.0f);
            case 2:
                return C0366y.m1613a(this.f1128n.m1510i(), 1.0f, 0.975f, 1.0f, (float) BitmapDescriptorFactory.HUE_RED);
            case 3:
                return C0366y.m1613a(this.f1128n.m1510i(), 0.975f, 1.0f, (float) BitmapDescriptorFactory.HUE_RED, 1.0f);
            case 4:
                return C0366y.m1613a(this.f1128n.m1510i(), 1.0f, 1.075f, 1.0f, (float) BitmapDescriptorFactory.HUE_RED);
            case 5:
                return C0366y.m1612a(this.f1128n.m1510i(), (float) BitmapDescriptorFactory.HUE_RED, 1.0f);
            case 6:
                return C0366y.m1612a(this.f1128n.m1510i(), 1.0f, (float) BitmapDescriptorFactory.HUE_RED);
            default:
                if (i2 == 0 && this.f1128n.mo268e()) {
                    i2 = this.f1128n.mo269f();
                }
                return i2 == 0 ? null : null;
        }
    }

    /* renamed from: a */
    public void mo286a(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException("Bad id: " + i);
        }
        m1657a(new C0363d(this, null, i, i2), false);
    }

    /* renamed from: a */
    public void m1645a(int i, C0341l c0341l) {
        synchronized (this) {
            if (this.f1124j == null) {
                this.f1124j = new ArrayList();
            }
            int size = this.f1124j.size();
            if (i < size) {
                if (f1110a) {
                    Log.v("FragmentManager", "Setting back stack index " + i + " to " + c0341l);
                }
                this.f1124j.set(i, c0341l);
            } else {
                while (size < i) {
                    this.f1124j.add(null);
                    if (this.f1125k == null) {
                        this.f1125k = new ArrayList();
                    }
                    if (f1110a) {
                        Log.v("FragmentManager", "Adding available back stack index " + size);
                    }
                    this.f1125k.add(Integer.valueOf(size));
                    size++;
                }
                if (f1110a) {
                    Log.v("FragmentManager", "Adding back stack index " + i + " with " + c0341l);
                }
                this.f1124j.add(c0341l);
            }
        }
    }

    /* renamed from: a */
    void m1646a(int i, boolean z) {
        if (this.f1128n == null && i != 0) {
            throw new IllegalStateException("No activity");
        } else if (z || i != this.f1127m) {
            this.f1127m = i;
            if (this.f1119e != null) {
                int size;
                int i2;
                int i3;
                Fragment fragment;
                if (this.f1120f != null) {
                    size = this.f1120f.size();
                    i2 = 0;
                    i3 = 0;
                    while (i2 < size) {
                        fragment = (Fragment) this.f1120f.get(i2);
                        m1682d(fragment);
                        i2++;
                        i3 = fragment.mLoaderManager != null ? fragment.mLoaderManager.mo204a() | i3 : i3;
                    }
                } else {
                    i3 = 0;
                }
                size = this.f1119e.size();
                i2 = 0;
                while (i2 < size) {
                    int a;
                    fragment = (Fragment) this.f1119e.get(i2);
                    if (fragment != null && ((fragment.mRemoving || fragment.mDetached) && !fragment.mIsNewlyAdded)) {
                        m1682d(fragment);
                        if (fragment.mLoaderManager != null) {
                            a = fragment.mLoaderManager.mo204a() | i3;
                            i2++;
                            i3 = a;
                        }
                    }
                    a = i3;
                    i2++;
                    i3 = a;
                }
                if (i3 == 0) {
                    m1681d();
                }
                if (this.f1131r && this.f1128n != null && this.f1127m == 5) {
                    this.f1128n.mo267d();
                    this.f1131r = false;
                }
            }
        }
    }

    /* renamed from: a */
    public void m1647a(Configuration configuration) {
        if (this.f1120f != null) {
            for (int i = 0; i < this.f1120f.size(); i++) {
                Fragment fragment = (Fragment) this.f1120f.get(i);
                if (fragment != null) {
                    fragment.performConfigurationChanged(configuration);
                }
            }
        }
    }

    /* renamed from: a */
    public void m1648a(Bundle bundle, String str, Fragment fragment) {
        if (fragment.mIndex < 0) {
            m1618a(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        }
        bundle.putInt(str, fragment.mIndex);
    }

    /* renamed from: a */
    void m1649a(Parcelable parcelable, C0367z c0367z) {
        if (parcelable != null) {
            FragmentManagerState fragmentManagerState = (FragmentManagerState) parcelable;
            if (fragmentManagerState.f751a != null) {
                List a;
                int size;
                int i;
                Fragment fragment;
                List list;
                if (c0367z != null) {
                    a = c0367z.m1714a();
                    List b = c0367z.m1715b();
                    if (a != null) {
                        size = a.size();
                    } else {
                        boolean z = false;
                    }
                    for (i = 0; i < size; i++) {
                        fragment = (Fragment) a.get(i);
                        if (f1110a) {
                            Log.v("FragmentManager", "restoreAllState: re-attaching retained " + fragment);
                        }
                        FragmentState fragmentState = fragmentManagerState.f751a[fragment.mIndex];
                        fragmentState.f765l = fragment;
                        fragment.mSavedViewState = null;
                        fragment.mBackStackNesting = 0;
                        fragment.mInLayout = false;
                        fragment.mAdded = false;
                        fragment.mTarget = null;
                        if (fragmentState.f764k != null) {
                            fragmentState.f764k.setClassLoader(this.f1128n.m1510i().getClassLoader());
                            fragment.mSavedViewState = fragmentState.f764k.getSparseParcelableArray("android:view_state");
                            fragment.mSavedFragmentState = fragmentState.f764k;
                        }
                    }
                    list = b;
                } else {
                    list = null;
                }
                this.f1119e = new ArrayList(fragmentManagerState.f751a.length);
                if (this.f1121g != null) {
                    this.f1121g.clear();
                }
                int i2 = 0;
                while (i2 < fragmentManagerState.f751a.length) {
                    FragmentState fragmentState2 = fragmentManagerState.f751a[i2];
                    if (fragmentState2 != null) {
                        C0367z c0367z2 = (list == null || i2 >= list.size()) ? null : (C0367z) list.get(i2);
                        fragment = fragmentState2.m1065a(this.f1128n, this.f1130p, c0367z2);
                        if (f1110a) {
                            Log.v("FragmentManager", "restoreAllState: active #" + i2 + ": " + fragment);
                        }
                        this.f1119e.add(fragment);
                        fragmentState2.f765l = null;
                    } else {
                        this.f1119e.add(null);
                        if (this.f1121g == null) {
                            this.f1121g = new ArrayList();
                        }
                        if (f1110a) {
                            Log.v("FragmentManager", "restoreAllState: avail #" + i2);
                        }
                        this.f1121g.add(Integer.valueOf(i2));
                    }
                    i2++;
                }
                if (c0367z != null) {
                    a = c0367z.m1714a();
                    if (a != null) {
                        i2 = a.size();
                    } else {
                        boolean z2 = false;
                    }
                    for (i = 0; i < i2; i++) {
                        fragment = (Fragment) a.get(i);
                        if (fragment.mTargetIndex >= 0) {
                            if (fragment.mTargetIndex < this.f1119e.size()) {
                                fragment.mTarget = (Fragment) this.f1119e.get(fragment.mTargetIndex);
                            } else {
                                Log.w("FragmentManager", "Re-attaching retained fragment " + fragment + " target no longer exists: " + fragment.mTargetIndex);
                                fragment.mTarget = null;
                            }
                        }
                    }
                }
                if (fragmentManagerState.f752b != null) {
                    this.f1120f = new ArrayList(fragmentManagerState.f752b.length);
                    for (size = 0; size < fragmentManagerState.f752b.length; size++) {
                        fragment = (Fragment) this.f1119e.get(fragmentManagerState.f752b[size]);
                        if (fragment == null) {
                            m1618a(new IllegalStateException("No instantiated fragment for index #" + fragmentManagerState.f752b[size]));
                        }
                        fragment.mAdded = true;
                        if (f1110a) {
                            Log.v("FragmentManager", "restoreAllState: added #" + size + ": " + fragment);
                        }
                        if (this.f1120f.contains(fragment)) {
                            throw new IllegalStateException("Already added!");
                        }
                        this.f1120f.add(fragment);
                    }
                } else {
                    this.f1120f = null;
                }
                if (fragmentManagerState.f753c != null) {
                    this.f1122h = new ArrayList(fragmentManagerState.f753c.length);
                    for (int i3 = 0; i3 < fragmentManagerState.f753c.length; i3++) {
                        C0341l a2 = fragmentManagerState.f753c[i3].m1036a(this);
                        if (f1110a) {
                            Log.v("FragmentManager", "restoreAllState: back stack #" + i3 + " (index " + a2.f1050n + "): " + a2);
                            PrintWriter printWriter = new PrintWriter(new C0469e("FragmentManager"));
                            a2.m1471a("  ", printWriter, false);
                            printWriter.close();
                        }
                        this.f1122h.add(a2);
                        if (a2.f1050n >= 0) {
                            m1645a(a2.f1050n, a2);
                        }
                    }
                    return;
                }
                this.f1122h = null;
            }
        }
    }

    /* renamed from: a */
    public void m1650a(Fragment fragment) {
        if (!fragment.mDeferStart) {
            return;
        }
        if (this.f1118d) {
            this.f1135v = true;
            return;
        }
        fragment.mDeferStart = false;
        m1651a(fragment, this.f1127m, 0, 0, false);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    void m1651a(final android.support.v4.app.Fragment r11, int r12, int r13, int r14, boolean r15) {
        /*
        r10 = this;
        r9 = 4;
        r6 = 3;
        r5 = 1;
        r7 = 0;
        r3 = 0;
        r0 = r11.mAdded;
        if (r0 == 0) goto L_0x000d;
    L_0x0009:
        r0 = r11.mDetached;
        if (r0 == 0) goto L_0x0010;
    L_0x000d:
        if (r12 <= r5) goto L_0x0010;
    L_0x000f:
        r12 = r5;
    L_0x0010:
        r0 = r11.mRemoving;
        if (r0 == 0) goto L_0x001a;
    L_0x0014:
        r0 = r11.mState;
        if (r12 <= r0) goto L_0x001a;
    L_0x0018:
        r12 = r11.mState;
    L_0x001a:
        r0 = r11.mDeferStart;
        if (r0 == 0) goto L_0x0025;
    L_0x001e:
        r0 = r11.mState;
        if (r0 >= r9) goto L_0x0025;
    L_0x0022:
        if (r12 <= r6) goto L_0x0025;
    L_0x0024:
        r12 = r6;
    L_0x0025:
        r0 = r11.mState;
        if (r0 >= r12) goto L_0x033a;
    L_0x0029:
        r0 = r11.mFromLayout;
        if (r0 == 0) goto L_0x0032;
    L_0x002d:
        r0 = r11.mInLayout;
        if (r0 != 0) goto L_0x0032;
    L_0x0031:
        return;
    L_0x0032:
        r0 = r11.getAnimatingAway();
        if (r0 == 0) goto L_0x0045;
    L_0x0038:
        r11.setAnimatingAway(r7);
        r2 = r11.getStateAfterAnimating();
        r0 = r10;
        r1 = r11;
        r4 = r3;
        r0.m1651a(r1, r2, r3, r4, r5);
    L_0x0045:
        r0 = r11.mState;
        switch(r0) {
            case 0: goto L_0x008a;
            case 1: goto L_0x01a6;
            case 2: goto L_0x02ae;
            case 3: goto L_0x02b3;
            case 4: goto L_0x02d9;
            default: goto L_0x004a;
        };
    L_0x004a:
        r0 = r11.mState;
        if (r0 == r12) goto L_0x0031;
    L_0x004e:
        r0 = "FragmentManager";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "moveToState: Fragment state for ";
        r1 = r1.append(r2);
        r1 = r1.append(r11);
        r2 = " not updated inline; ";
        r1 = r1.append(r2);
        r2 = "expected state ";
        r1 = r1.append(r2);
        r1 = r1.append(r12);
        r2 = " found ";
        r1 = r1.append(r2);
        r2 = r11.mState;
        r1 = r1.append(r2);
        r1 = r1.toString();
        android.util.Log.w(r0, r1);
        r11.mState = r12;
        goto L_0x0031;
    L_0x008a:
        r0 = f1110a;
        if (r0 == 0) goto L_0x00a8;
    L_0x008e:
        r0 = "FragmentManager";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "moveto CREATED: ";
        r1 = r1.append(r2);
        r1 = r1.append(r11);
        r1 = r1.toString();
        android.util.Log.v(r0, r1);
    L_0x00a8:
        r0 = r11.mSavedFragmentState;
        if (r0 == 0) goto L_0x00f4;
    L_0x00ac:
        r0 = r11.mSavedFragmentState;
        r1 = r10.f1128n;
        r1 = r1.m1510i();
        r1 = r1.getClassLoader();
        r0.setClassLoader(r1);
        r0 = r11.mSavedFragmentState;
        r1 = "android:view_state";
        r0 = r0.getSparseParcelableArray(r1);
        r11.mSavedViewState = r0;
        r0 = r11.mSavedFragmentState;
        r1 = "android:target_state";
        r0 = r10.m1639a(r0, r1);
        r11.mTarget = r0;
        r0 = r11.mTarget;
        if (r0 == 0) goto L_0x00e0;
    L_0x00d5:
        r0 = r11.mSavedFragmentState;
        r1 = "android:target_req_state";
        r0 = r0.getInt(r1, r3);
        r11.mTargetRequestCode = r0;
    L_0x00e0:
        r0 = r11.mSavedFragmentState;
        r1 = "android:user_visible_hint";
        r0 = r0.getBoolean(r1, r5);
        r11.mUserVisibleHint = r0;
        r0 = r11.mUserVisibleHint;
        if (r0 != 0) goto L_0x00f4;
    L_0x00ef:
        r11.mDeferStart = r5;
        if (r12 <= r6) goto L_0x00f4;
    L_0x00f3:
        r12 = r6;
    L_0x00f4:
        r0 = r10.f1128n;
        r11.mHost = r0;
        r0 = r10.f1130p;
        r11.mParentFragment = r0;
        r0 = r10.f1130p;
        if (r0 == 0) goto L_0x013f;
    L_0x0100:
        r0 = r10.f1130p;
        r0 = r0.mChildFragmentManager;
    L_0x0104:
        r11.mFragmentManager = r0;
        r0 = r10.f1128n;
        r0 = r0.m1510i();
        r10.m1652a(r11, r0, r3);
        r11.mCalled = r3;
        r0 = r10.f1128n;
        r0 = r0.m1510i();
        r11.onAttach(r0);
        r0 = r11.mCalled;
        if (r0 != 0) goto L_0x0146;
    L_0x011e:
        r0 = new android.support.v4.app.be;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "Fragment ";
        r1 = r1.append(r2);
        r1 = r1.append(r11);
        r2 = " did not call through to super.onAttach()";
        r1 = r1.append(r2);
        r1 = r1.toString();
        r0.<init>(r1);
        throw r0;
    L_0x013f:
        r0 = r10.f1128n;
        r0 = r0.m1512k();
        goto L_0x0104;
    L_0x0146:
        r0 = r11.mParentFragment;
        if (r0 != 0) goto L_0x0305;
    L_0x014a:
        r0 = r10.f1128n;
        r0.mo266b(r11);
    L_0x014f:
        r0 = r10.f1128n;
        r0 = r0.m1510i();
        r10.m1668b(r11, r0, r3);
        r0 = r11.mRetaining;
        if (r0 != 0) goto L_0x030c;
    L_0x015c:
        r0 = r11.mSavedFragmentState;
        r11.performCreate(r0);
        r0 = r11.mSavedFragmentState;
        r10.m1653a(r11, r0, r3);
    L_0x0166:
        r11.mRetaining = r3;
        r0 = r11.mFromLayout;
        if (r0 == 0) goto L_0x01a6;
    L_0x016c:
        r0 = r11.mSavedFragmentState;
        r0 = r11.getLayoutInflater(r0);
        r1 = r11.mSavedFragmentState;
        r0 = r11.performCreateView(r0, r7, r1);
        r11.mView = r0;
        r0 = r11.mView;
        if (r0 == 0) goto L_0x031f;
    L_0x017e:
        r0 = r11.mView;
        r11.mInnerView = r0;
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 11;
        if (r0 < r1) goto L_0x0315;
    L_0x0188:
        r0 = r11.mView;
        android.support.v4.view.ah.m2796b(r0, r3);
    L_0x018d:
        r0 = r11.mHidden;
        if (r0 == 0) goto L_0x0198;
    L_0x0191:
        r0 = r11.mView;
        r1 = 8;
        r0.setVisibility(r1);
    L_0x0198:
        r0 = r11.mView;
        r1 = r11.mSavedFragmentState;
        r11.onViewCreated(r0, r1);
        r0 = r11.mView;
        r1 = r11.mSavedFragmentState;
        r10.m1654a(r11, r0, r1, r3);
    L_0x01a6:
        if (r12 <= r5) goto L_0x02ae;
    L_0x01a8:
        r0 = f1110a;
        if (r0 == 0) goto L_0x01c6;
    L_0x01ac:
        r0 = "FragmentManager";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "moveto ACTIVITY_CREATED: ";
        r1 = r1.append(r2);
        r1 = r1.append(r11);
        r1 = r1.toString();
        android.util.Log.v(r0, r1);
    L_0x01c6:
        r0 = r11.mFromLayout;
        if (r0 != 0) goto L_0x0299;
    L_0x01ca:
        r0 = r11.mContainerId;
        if (r0 == 0) goto L_0x04a5;
    L_0x01ce:
        r0 = r11.mContainerId;
        r1 = -1;
        if (r0 != r1) goto L_0x01f6;
    L_0x01d3:
        r0 = new java.lang.IllegalArgumentException;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "Cannot create fragment ";
        r1 = r1.append(r2);
        r1 = r1.append(r11);
        r2 = " for a container view with no id";
        r1 = r1.append(r2);
        r1 = r1.toString();
        r0.<init>(r1);
        r10.m1618a(r0);
    L_0x01f6:
        r0 = r10.f1129o;
        r1 = r11.mContainerId;
        r0 = r0.mo199a(r1);
        r0 = (android.view.ViewGroup) r0;
        if (r0 != 0) goto L_0x0248;
    L_0x0202:
        r1 = r11.mRestored;
        if (r1 != 0) goto L_0x0248;
    L_0x0206:
        r1 = r11.getResources();	 Catch:{ NotFoundException -> 0x0323 }
        r2 = r11.mContainerId;	 Catch:{ NotFoundException -> 0x0323 }
        r1 = r1.getResourceName(r2);	 Catch:{ NotFoundException -> 0x0323 }
    L_0x0210:
        r2 = new java.lang.IllegalArgumentException;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r8 = "No view found for id 0x";
        r4 = r4.append(r8);
        r8 = r11.mContainerId;
        r8 = java.lang.Integer.toHexString(r8);
        r4 = r4.append(r8);
        r8 = " (";
        r4 = r4.append(r8);
        r1 = r4.append(r1);
        r4 = ") for fragment ";
        r1 = r1.append(r4);
        r1 = r1.append(r11);
        r1 = r1.toString();
        r2.<init>(r1);
        r10.m1618a(r2);
    L_0x0248:
        r11.mContainer = r0;
        r1 = r11.mSavedFragmentState;
        r1 = r11.getLayoutInflater(r1);
        r2 = r11.mSavedFragmentState;
        r1 = r11.performCreateView(r1, r0, r2);
        r11.mView = r1;
        r1 = r11.mView;
        if (r1 == 0) goto L_0x0336;
    L_0x025c:
        r1 = r11.mView;
        r11.mInnerView = r1;
        r1 = android.os.Build.VERSION.SDK_INT;
        r2 = 11;
        if (r1 < r2) goto L_0x0329;
    L_0x0266:
        r1 = r11.mView;
        android.support.v4.view.ah.m2796b(r1, r3);
    L_0x026b:
        if (r0 == 0) goto L_0x0272;
    L_0x026d:
        r1 = r11.mView;
        r0.addView(r1);
    L_0x0272:
        r0 = r11.mHidden;
        if (r0 == 0) goto L_0x027d;
    L_0x0276:
        r0 = r11.mView;
        r1 = 8;
        r0.setVisibility(r1);
    L_0x027d:
        r0 = r11.mView;
        r1 = r11.mSavedFragmentState;
        r11.onViewCreated(r0, r1);
        r0 = r11.mView;
        r1 = r11.mSavedFragmentState;
        r10.m1654a(r11, r0, r1, r3);
        r0 = r11.mView;
        r0 = r0.getVisibility();
        if (r0 != 0) goto L_0x0333;
    L_0x0293:
        r0 = r11.mContainer;
        if (r0 == 0) goto L_0x0333;
    L_0x0297:
        r11.mIsNewlyAdded = r5;
    L_0x0299:
        r0 = r11.mSavedFragmentState;
        r11.performActivityCreated(r0);
        r0 = r11.mSavedFragmentState;
        r10.m1669b(r11, r0, r3);
        r0 = r11.mView;
        if (r0 == 0) goto L_0x02ac;
    L_0x02a7:
        r0 = r11.mSavedFragmentState;
        r11.restoreViewState(r0);
    L_0x02ac:
        r11.mSavedFragmentState = r7;
    L_0x02ae:
        r0 = 2;
        if (r12 <= r0) goto L_0x02b3;
    L_0x02b1:
        r11.mState = r6;
    L_0x02b3:
        if (r12 <= r6) goto L_0x02d9;
    L_0x02b5:
        r0 = f1110a;
        if (r0 == 0) goto L_0x02d3;
    L_0x02b9:
        r0 = "FragmentManager";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "moveto STARTED: ";
        r1 = r1.append(r2);
        r1 = r1.append(r11);
        r1 = r1.toString();
        android.util.Log.v(r0, r1);
    L_0x02d3:
        r11.performStart();
        r10.m1670b(r11, r3);
    L_0x02d9:
        if (r12 <= r9) goto L_0x004a;
    L_0x02db:
        r0 = f1110a;
        if (r0 == 0) goto L_0x02f9;
    L_0x02df:
        r0 = "FragmentManager";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "moveto RESUMED: ";
        r1 = r1.append(r2);
        r1 = r1.append(r11);
        r1 = r1.toString();
        android.util.Log.v(r0, r1);
    L_0x02f9:
        r11.performResume();
        r10.m1680c(r11, r3);
        r11.mSavedFragmentState = r7;
        r11.mSavedViewState = r7;
        goto L_0x004a;
    L_0x0305:
        r0 = r11.mParentFragment;
        r0.onAttachFragment(r11);
        goto L_0x014f;
    L_0x030c:
        r0 = r11.mSavedFragmentState;
        r11.restoreChildFragmentState(r0);
        r11.mState = r5;
        goto L_0x0166;
    L_0x0315:
        r0 = r11.mView;
        r0 = android.support.v4.app.ai.m1198a(r0);
        r11.mView = r0;
        goto L_0x018d;
    L_0x031f:
        r11.mInnerView = r7;
        goto L_0x01a6;
    L_0x0323:
        r1 = move-exception;
        r1 = "unknown";
        goto L_0x0210;
    L_0x0329:
        r1 = r11.mView;
        r1 = android.support.v4.app.ai.m1198a(r1);
        r11.mView = r1;
        goto L_0x026b;
    L_0x0333:
        r5 = r3;
        goto L_0x0297;
    L_0x0336:
        r11.mInnerView = r7;
        goto L_0x0299;
    L_0x033a:
        r0 = r11.mState;
        if (r0 <= r12) goto L_0x004a;
    L_0x033e:
        r0 = r11.mState;
        switch(r0) {
            case 1: goto L_0x0345;
            case 2: goto L_0x03d7;
            case 3: goto L_0x03b4;
            case 4: goto L_0x038e;
            case 5: goto L_0x0367;
            default: goto L_0x0343;
        };
    L_0x0343:
        goto L_0x004a;
    L_0x0345:
        if (r12 >= r5) goto L_0x004a;
    L_0x0347:
        r0 = r10.f1133t;
        if (r0 == 0) goto L_0x035b;
    L_0x034b:
        r0 = r11.getAnimatingAway();
        if (r0 == 0) goto L_0x035b;
    L_0x0351:
        r0 = r11.getAnimatingAway();
        r11.setAnimatingAway(r7);
        r0.clearAnimation();
    L_0x035b:
        r0 = r11.getAnimatingAway();
        if (r0 == 0) goto L_0x045f;
    L_0x0361:
        r11.setStateAfterAnimating(r12);
        r12 = r5;
        goto L_0x004a;
    L_0x0367:
        r0 = 5;
        if (r12 >= r0) goto L_0x038e;
    L_0x036a:
        r0 = f1110a;
        if (r0 == 0) goto L_0x0388;
    L_0x036e:
        r0 = "FragmentManager";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "movefrom RESUMED: ";
        r1 = r1.append(r2);
        r1 = r1.append(r11);
        r1 = r1.toString();
        android.util.Log.v(r0, r1);
    L_0x0388:
        r11.performPause();
        r10.m1683d(r11, r3);
    L_0x038e:
        if (r12 >= r9) goto L_0x03b4;
    L_0x0390:
        r0 = f1110a;
        if (r0 == 0) goto L_0x03ae;
    L_0x0394:
        r0 = "FragmentManager";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "movefrom STARTED: ";
        r1 = r1.append(r2);
        r1 = r1.append(r11);
        r1 = r1.toString();
        android.util.Log.v(r0, r1);
    L_0x03ae:
        r11.performStop();
        r10.m1685e(r11, r3);
    L_0x03b4:
        if (r12 >= r6) goto L_0x03d7;
    L_0x03b6:
        r0 = f1110a;
        if (r0 == 0) goto L_0x03d4;
    L_0x03ba:
        r0 = "FragmentManager";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "movefrom STOPPED: ";
        r1 = r1.append(r2);
        r1 = r1.append(r11);
        r1 = r1.toString();
        android.util.Log.v(r0, r1);
    L_0x03d4:
        r11.performReallyStop();
    L_0x03d7:
        r0 = 2;
        if (r12 >= r0) goto L_0x0345;
    L_0x03da:
        r0 = f1110a;
        if (r0 == 0) goto L_0x03f8;
    L_0x03de:
        r0 = "FragmentManager";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "movefrom ACTIVITY_CREATED: ";
        r1 = r1.append(r2);
        r1 = r1.append(r11);
        r1 = r1.toString();
        android.util.Log.v(r0, r1);
    L_0x03f8:
        r0 = r11.mView;
        if (r0 == 0) goto L_0x040b;
    L_0x03fc:
        r0 = r10.f1128n;
        r0 = r0.mo263a(r11);
        if (r0 == 0) goto L_0x040b;
    L_0x0404:
        r0 = r11.mSavedViewState;
        if (r0 != 0) goto L_0x040b;
    L_0x0408:
        r10.m1703l(r11);
    L_0x040b:
        r11.performDestroyView();
        r10.m1689f(r11, r3);
        r0 = r11.mView;
        if (r0 == 0) goto L_0x0457;
    L_0x0415:
        r0 = r11.mContainer;
        if (r0 == 0) goto L_0x0457;
    L_0x0419:
        r0 = r10.f1127m;
        if (r0 <= 0) goto L_0x04a3;
    L_0x041d:
        r0 = r10.f1133t;
        if (r0 != 0) goto L_0x04a3;
    L_0x0421:
        r0 = r11.mView;
        r0 = r0.getVisibility();
        if (r0 != 0) goto L_0x04a3;
    L_0x0429:
        r0 = r11.mPostponedAlpha;
        r1 = 0;
        r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1));
        if (r0 < 0) goto L_0x04a3;
    L_0x0430:
        r0 = r10.m1643a(r11, r13, r3, r14);
    L_0x0434:
        r1 = 0;
        r11.mPostponedAlpha = r1;
        if (r0 == 0) goto L_0x0450;
    L_0x0439:
        r1 = r11.mView;
        r11.setAnimatingAway(r1);
        r11.setStateAfterAnimating(r12);
        r1 = r11.mView;
        r2 = new android.support.v4.app.y$2;
        r2.<init>(r10, r1, r0, r11);
        r0.setAnimationListener(r2);
        r1 = r11.mView;
        r1.startAnimation(r0);
    L_0x0450:
        r0 = r11.mContainer;
        r1 = r11.mView;
        r0.removeView(r1);
    L_0x0457:
        r11.mContainer = r7;
        r11.mView = r7;
        r11.mInnerView = r7;
        goto L_0x0345;
    L_0x045f:
        r0 = f1110a;
        if (r0 == 0) goto L_0x047d;
    L_0x0463:
        r0 = "FragmentManager";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "movefrom CREATED: ";
        r1 = r1.append(r2);
        r1 = r1.append(r11);
        r1 = r1.toString();
        android.util.Log.v(r0, r1);
    L_0x047d:
        r0 = r11.mRetaining;
        if (r0 != 0) goto L_0x0498;
    L_0x0481:
        r11.performDestroy();
        r10.m1692g(r11, r3);
    L_0x0487:
        r11.performDetach();
        r10.m1695h(r11, r3);
        if (r15 != 0) goto L_0x004a;
    L_0x048f:
        r0 = r11.mRetaining;
        if (r0 != 0) goto L_0x049b;
    L_0x0493:
        r10.m1688f(r11);
        goto L_0x004a;
    L_0x0498:
        r11.mState = r3;
        goto L_0x0487;
    L_0x049b:
        r11.mHost = r7;
        r11.mParentFragment = r7;
        r11.mFragmentManager = r7;
        goto L_0x004a;
    L_0x04a3:
        r0 = r7;
        goto L_0x0434;
    L_0x04a5:
        r0 = r7;
        goto L_0x0248;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.y.a(android.support.v4.app.Fragment, int, int, int, boolean):void");
    }

    /* renamed from: a */
    void m1652a(Fragment fragment, Context context, boolean z) {
        if (this.f1130p != null) {
            C0357x fragmentManager = this.f1130p.getFragmentManager();
            if (fragmentManager instanceof C0366y) {
                ((C0366y) fragmentManager).m1652a(fragment, context, true);
            }
        }
        if (this.f1116H != null) {
            Iterator it = this.f1116H.iterator();
            while (it.hasNext()) {
                C0477i c0477i = (C0477i) it.next();
                if (!z || ((Boolean) c0477i.f1265b).booleanValue()) {
                    ((C0355a) c0477i.f1264a).m1583a((C0357x) this, fragment, context);
                }
            }
        }
    }

    /* renamed from: a */
    void m1653a(Fragment fragment, Bundle bundle, boolean z) {
        if (this.f1130p != null) {
            C0357x fragmentManager = this.f1130p.getFragmentManager();
            if (fragmentManager instanceof C0366y) {
                ((C0366y) fragmentManager).m1653a(fragment, bundle, true);
            }
        }
        if (this.f1116H != null) {
            Iterator it = this.f1116H.iterator();
            while (it.hasNext()) {
                C0477i c0477i = (C0477i) it.next();
                if (!z || ((Boolean) c0477i.f1265b).booleanValue()) {
                    ((C0355a) c0477i.f1264a).m1584a((C0357x) this, fragment, bundle);
                }
            }
        }
    }

    /* renamed from: a */
    void m1654a(Fragment fragment, View view, Bundle bundle, boolean z) {
        if (this.f1130p != null) {
            C0357x fragmentManager = this.f1130p.getFragmentManager();
            if (fragmentManager instanceof C0366y) {
                ((C0366y) fragmentManager).m1654a(fragment, view, bundle, true);
            }
        }
        if (this.f1116H != null) {
            Iterator it = this.f1116H.iterator();
            while (it.hasNext()) {
                C0477i c0477i = (C0477i) it.next();
                if (!z || ((Boolean) c0477i.f1265b).booleanValue()) {
                    ((C0355a) c0477i.f1264a).m1585a(this, fragment, view, bundle);
                }
            }
        }
    }

    /* renamed from: a */
    public void m1655a(Fragment fragment, boolean z) {
        if (this.f1120f == null) {
            this.f1120f = new ArrayList();
        }
        if (f1110a) {
            Log.v("FragmentManager", "add: " + fragment);
        }
        m1684e(fragment);
        if (!fragment.mDetached) {
            if (this.f1120f.contains(fragment)) {
                throw new IllegalStateException("Fragment already added: " + fragment);
            }
            this.f1120f.add(fragment);
            fragment.mAdded = true;
            fragment.mRemoving = false;
            if (fragment.mView == null) {
                fragment.mHiddenChanged = false;
            }
            if (fragment.mHasMenu && fragment.mMenuVisible) {
                this.f1131r = true;
            }
            if (z) {
                m1667b(fragment);
            }
        }
    }

    /* renamed from: a */
    public void m1656a(C0350w c0350w, C0225u c0225u, Fragment fragment) {
        if (this.f1128n != null) {
            throw new IllegalStateException("Already attached");
        }
        this.f1128n = c0350w;
        this.f1129o = c0225u;
        this.f1130p = fragment;
    }

    /* renamed from: a */
    public void m1657a(C0340c c0340c, boolean z) {
        if (!z) {
            m1633v();
        }
        synchronized (this) {
            if (this.f1133t || this.f1128n == null) {
                throw new IllegalStateException("Activity has been destroyed");
            }
            if (this.f1117c == null) {
                this.f1117c = new ArrayList();
            }
            this.f1117c.add(c0340c);
            m1634w();
        }
    }

    /* renamed from: a */
    public void mo287a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int size;
        int i;
        Fragment fragment;
        int i2 = 0;
        String str2 = str + "    ";
        if (this.f1119e != null) {
            size = this.f1119e.size();
            if (size > 0) {
                printWriter.print(str);
                printWriter.print("Active Fragments in ");
                printWriter.print(Integer.toHexString(System.identityHashCode(this)));
                printWriter.println(":");
                for (i = 0; i < size; i++) {
                    fragment = (Fragment) this.f1119e.get(i);
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i);
                    printWriter.print(": ");
                    printWriter.println(fragment);
                    if (fragment != null) {
                        fragment.dump(str2, fileDescriptor, printWriter, strArr);
                    }
                }
            }
        }
        if (this.f1120f != null) {
            size = this.f1120f.size();
            if (size > 0) {
                printWriter.print(str);
                printWriter.println("Added Fragments:");
                for (i = 0; i < size; i++) {
                    fragment = (Fragment) this.f1120f.get(i);
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i);
                    printWriter.print(": ");
                    printWriter.println(fragment.toString());
                }
            }
        }
        if (this.f1123i != null) {
            size = this.f1123i.size();
            if (size > 0) {
                printWriter.print(str);
                printWriter.println("Fragments Created Menus:");
                for (i = 0; i < size; i++) {
                    fragment = (Fragment) this.f1123i.get(i);
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i);
                    printWriter.print(": ");
                    printWriter.println(fragment.toString());
                }
            }
        }
        if (this.f1122h != null) {
            size = this.f1122h.size();
            if (size > 0) {
                printWriter.print(str);
                printWriter.println("Back Stack:");
                for (i = 0; i < size; i++) {
                    C0341l c0341l = (C0341l) this.f1122h.get(i);
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i);
                    printWriter.print(": ");
                    printWriter.println(c0341l.toString());
                    c0341l.m1470a(str2, fileDescriptor, printWriter, strArr);
                }
            }
        }
        synchronized (this) {
            if (this.f1124j != null) {
                int size2 = this.f1124j.size();
                if (size2 > 0) {
                    printWriter.print(str);
                    printWriter.println("Back Stack Indices:");
                    for (i = 0; i < size2; i++) {
                        c0341l = (C0341l) this.f1124j.get(i);
                        printWriter.print(str);
                        printWriter.print("  #");
                        printWriter.print(i);
                        printWriter.print(": ");
                        printWriter.println(c0341l);
                    }
                }
            }
            if (this.f1125k != null && this.f1125k.size() > 0) {
                printWriter.print(str);
                printWriter.print("mAvailBackStackIndices: ");
                printWriter.println(Arrays.toString(this.f1125k.toArray()));
            }
        }
        if (this.f1117c != null) {
            i = this.f1117c.size();
            if (i > 0) {
                printWriter.print(str);
                printWriter.println("Pending Actions:");
                while (i2 < i) {
                    C0340c c0340c = (C0340c) this.f1117c.get(i2);
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i2);
                    printWriter.print(": ");
                    printWriter.println(c0340c);
                    i2++;
                }
            }
        }
        printWriter.print(str);
        printWriter.println("FragmentManager misc state:");
        printWriter.print(str);
        printWriter.print("  mHost=");
        printWriter.println(this.f1128n);
        printWriter.print(str);
        printWriter.print("  mContainer=");
        printWriter.println(this.f1129o);
        if (this.f1130p != null) {
            printWriter.print(str);
            printWriter.print("  mParent=");
            printWriter.println(this.f1130p);
        }
        printWriter.print(str);
        printWriter.print("  mCurState=");
        printWriter.print(this.f1127m);
        printWriter.print(" mStateSaved=");
        printWriter.print(this.f1132s);
        printWriter.print(" mDestroyed=");
        printWriter.println(this.f1133t);
        if (this.f1131r) {
            printWriter.print(str);
            printWriter.print("  mNeedMenuInvalidate=");
            printWriter.println(this.f1131r);
        }
        if (this.f1134u != null) {
            printWriter.print(str);
            printWriter.print("  mNoTransactionsBecause=");
            printWriter.println(this.f1134u);
        }
        if (this.f1121g != null && this.f1121g.size() > 0) {
            printWriter.print(str);
            printWriter.print("  mAvailIndices: ");
            printWriter.println(Arrays.toString(this.f1121g.toArray()));
        }
    }

    /* renamed from: a */
    public void m1659a(boolean z) {
        if (this.f1120f != null) {
            for (int size = this.f1120f.size() - 1; size >= 0; size--) {
                Fragment fragment = (Fragment) this.f1120f.get(size);
                if (fragment != null) {
                    fragment.performMultiWindowModeChanged(z);
                }
            }
        }
    }

    /* renamed from: a */
    boolean m1660a(int i) {
        return this.f1127m >= i;
    }

    /* renamed from: a */
    public boolean m1661a(Menu menu) {
        if (this.f1120f == null) {
            return false;
        }
        boolean z = false;
        for (int i = 0; i < this.f1120f.size(); i++) {
            Fragment fragment = (Fragment) this.f1120f.get(i);
            if (fragment != null && fragment.performPrepareOptionsMenu(menu)) {
                z = true;
            }
        }
        return z;
    }

    /* renamed from: a */
    public boolean m1662a(Menu menu, MenuInflater menuInflater) {
        boolean z;
        Fragment fragment;
        int i = 0;
        ArrayList arrayList = null;
        if (this.f1120f != null) {
            int i2 = 0;
            z = false;
            while (i2 < this.f1120f.size()) {
                fragment = (Fragment) this.f1120f.get(i2);
                if (fragment != null && fragment.performCreateOptionsMenu(menu, menuInflater)) {
                    z = true;
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(fragment);
                }
                i2++;
                z = z;
            }
        } else {
            z = false;
        }
        if (this.f1123i != null) {
            while (i < this.f1123i.size()) {
                fragment = (Fragment) this.f1123i.get(i);
                if (arrayList == null || !arrayList.contains(fragment)) {
                    fragment.onDestroyOptionsMenu();
                }
                i++;
            }
        }
        this.f1123i = arrayList;
        return z;
    }

    /* renamed from: a */
    public boolean m1663a(MenuItem menuItem) {
        if (this.f1120f == null) {
            return false;
        }
        for (int i = 0; i < this.f1120f.size(); i++) {
            Fragment fragment = (Fragment) this.f1120f.get(i);
            if (fragment != null && fragment.performOptionsItemSelected(menuItem)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: a */
    boolean m1664a(ArrayList<C0341l> arrayList, ArrayList<Boolean> arrayList2, String str, int i, int i2) {
        if (this.f1122h == null) {
            return false;
        }
        int size;
        if (str == null && i < 0 && (i2 & 1) == 0) {
            size = this.f1122h.size() - 1;
            if (size < 0) {
                return false;
            }
            arrayList.add(this.f1122h.remove(size));
            arrayList2.add(Boolean.valueOf(true));
        } else {
            int size2;
            size = -1;
            if (str != null || i >= 0) {
                C0341l c0341l;
                size2 = this.f1122h.size() - 1;
                while (size2 >= 0) {
                    c0341l = (C0341l) this.f1122h.get(size2);
                    if ((str != null && str.equals(c0341l.m1481e())) || (i >= 0 && i == c0341l.f1050n)) {
                        break;
                    }
                    size2--;
                }
                if (size2 < 0) {
                    return false;
                }
                if ((i2 & 1) != 0) {
                    size2--;
                    while (size2 >= 0) {
                        c0341l = (C0341l) this.f1122h.get(size2);
                        if ((str == null || !str.equals(c0341l.m1481e())) && (i < 0 || i != c0341l.f1050n)) {
                            break;
                        }
                        size2--;
                    }
                }
                size = size2;
            }
            if (size == this.f1122h.size() - 1) {
                return false;
            }
            for (size2 = this.f1122h.size() - 1; size2 > size; size2--) {
                arrayList.add(this.f1122h.remove(size2));
                arrayList2.add(Boolean.valueOf(true));
            }
        }
        return true;
    }

    /* renamed from: b */
    public Fragment m1665b(int i) {
        int size;
        Fragment fragment;
        if (this.f1120f != null) {
            for (size = this.f1120f.size() - 1; size >= 0; size--) {
                fragment = (Fragment) this.f1120f.get(size);
                if (fragment != null && fragment.mFragmentId == i) {
                    return fragment;
                }
            }
        }
        if (this.f1119e != null) {
            for (size = this.f1119e.size() - 1; size >= 0; size--) {
                fragment = (Fragment) this.f1119e.get(size);
                if (fragment != null && fragment.mFragmentId == i) {
                    return fragment;
                }
            }
        }
        return null;
    }

    /* renamed from: b */
    public Fragment m1666b(String str) {
        if (!(this.f1119e == null || str == null)) {
            for (int size = this.f1119e.size() - 1; size >= 0; size--) {
                Fragment fragment = (Fragment) this.f1119e.get(size);
                if (fragment != null) {
                    fragment = fragment.findFragmentByWho(str);
                    if (fragment != null) {
                        return fragment;
                    }
                }
            }
        }
        return null;
    }

    /* renamed from: b */
    void m1667b(Fragment fragment) {
        m1651a(fragment, this.f1127m, 0, 0, false);
    }

    /* renamed from: b */
    void m1668b(Fragment fragment, Context context, boolean z) {
        if (this.f1130p != null) {
            C0357x fragmentManager = this.f1130p.getFragmentManager();
            if (fragmentManager instanceof C0366y) {
                ((C0366y) fragmentManager).m1668b(fragment, context, true);
            }
        }
        if (this.f1116H != null) {
            Iterator it = this.f1116H.iterator();
            while (it.hasNext()) {
                C0477i c0477i = (C0477i) it.next();
                if (!z || ((Boolean) c0477i.f1265b).booleanValue()) {
                    ((C0355a) c0477i.f1264a).m1587b((C0357x) this, fragment, context);
                }
            }
        }
    }

    /* renamed from: b */
    void m1669b(Fragment fragment, Bundle bundle, boolean z) {
        if (this.f1130p != null) {
            C0357x fragmentManager = this.f1130p.getFragmentManager();
            if (fragmentManager instanceof C0366y) {
                ((C0366y) fragmentManager).m1669b(fragment, bundle, true);
            }
        }
        if (this.f1116H != null) {
            Iterator it = this.f1116H.iterator();
            while (it.hasNext()) {
                C0477i c0477i = (C0477i) it.next();
                if (!z || ((Boolean) c0477i.f1265b).booleanValue()) {
                    ((C0355a) c0477i.f1264a).m1588b((C0357x) this, fragment, bundle);
                }
            }
        }
    }

    /* renamed from: b */
    void m1670b(Fragment fragment, boolean z) {
        if (this.f1130p != null) {
            C0357x fragmentManager = this.f1130p.getFragmentManager();
            if (fragmentManager instanceof C0366y) {
                ((C0366y) fragmentManager).m1670b(fragment, true);
            }
        }
        if (this.f1116H != null) {
            Iterator it = this.f1116H.iterator();
            while (it.hasNext()) {
                C0477i c0477i = (C0477i) it.next();
                if (!z || ((Boolean) c0477i.f1265b).booleanValue()) {
                    ((C0355a) c0477i.f1264a).m1582a(this, fragment);
                }
            }
        }
    }

    /* renamed from: b */
    void m1671b(C0341l c0341l) {
        if (this.f1122h == null) {
            this.f1122h = new ArrayList();
        }
        this.f1122h.add(c0341l);
        m1690g();
    }

    /* renamed from: b */
    public void m1672b(Menu menu) {
        if (this.f1120f != null) {
            for (int i = 0; i < this.f1120f.size(); i++) {
                Fragment fragment = (Fragment) this.f1120f.get(i);
                if (fragment != null) {
                    fragment.performOptionsMenuClosed(menu);
                }
            }
        }
    }

    /* renamed from: b */
    public void m1673b(boolean z) {
        if (this.f1120f != null) {
            for (int size = this.f1120f.size() - 1; size >= 0; size--) {
                Fragment fragment = (Fragment) this.f1120f.get(size);
                if (fragment != null) {
                    fragment.performPictureInPictureModeChanged(z);
                }
            }
        }
    }

    /* renamed from: b */
    public boolean mo288b() {
        m1633v();
        return m1623a(null, -1, 0);
    }

    /* renamed from: b */
    public boolean m1675b(MenuItem menuItem) {
        if (this.f1120f == null) {
            return false;
        }
        for (int i = 0; i < this.f1120f.size(); i++) {
            Fragment fragment = (Fragment) this.f1120f.get(i);
            if (fragment != null && fragment.performContextItemSelected(menuItem)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: c */
    public List<Fragment> mo289c() {
        return this.f1119e;
    }

    /* renamed from: c */
    public void m1677c(int i) {
        synchronized (this) {
            this.f1124j.set(i, null);
            if (this.f1125k == null) {
                this.f1125k = new ArrayList();
            }
            if (f1110a) {
                Log.v("FragmentManager", "Freeing back stack index " + i);
            }
            this.f1125k.add(Integer.valueOf(i));
        }
    }

    /* renamed from: c */
    void m1678c(Fragment fragment) {
        if (fragment.mView != null) {
            Animation a = m1643a(fragment, fragment.getNextTransition(), !fragment.mHidden, fragment.getNextTransitionStyle());
            if (a != null) {
                m1626b(fragment.mView, a);
                fragment.mView.startAnimation(a);
                m1626b(fragment.mView, a);
                a.start();
            }
            int i = (!fragment.mHidden || fragment.isHideReplaced()) ? 0 : 8;
            fragment.mView.setVisibility(i);
            if (fragment.isHideReplaced()) {
                fragment.setHideReplaced(false);
            }
        }
        if (fragment.mAdded && fragment.mHasMenu && fragment.mMenuVisible) {
            this.f1131r = true;
        }
        fragment.mHiddenChanged = false;
        fragment.onHiddenChanged(fragment.mHidden);
    }

    /* renamed from: c */
    void m1679c(Fragment fragment, Bundle bundle, boolean z) {
        if (this.f1130p != null) {
            C0357x fragmentManager = this.f1130p.getFragmentManager();
            if (fragmentManager instanceof C0366y) {
                ((C0366y) fragmentManager).m1679c(fragment, bundle, true);
            }
        }
        if (this.f1116H != null) {
            Iterator it = this.f1116H.iterator();
            while (it.hasNext()) {
                C0477i c0477i = (C0477i) it.next();
                if (!z || ((Boolean) c0477i.f1265b).booleanValue()) {
                    ((C0355a) c0477i.f1264a).m1590c(this, fragment, bundle);
                }
            }
        }
    }

    /* renamed from: c */
    void m1680c(Fragment fragment, boolean z) {
        if (this.f1130p != null) {
            C0357x fragmentManager = this.f1130p.getFragmentManager();
            if (fragmentManager instanceof C0366y) {
                ((C0366y) fragmentManager).m1680c(fragment, true);
            }
        }
        if (this.f1116H != null) {
            Iterator it = this.f1116H.iterator();
            while (it.hasNext()) {
                C0477i c0477i = (C0477i) it.next();
                if (!z || ((Boolean) c0477i.f1265b).booleanValue()) {
                    ((C0355a) c0477i.f1264a).m1586b(this, fragment);
                }
            }
        }
    }

    /* renamed from: d */
    void m1681d() {
        if (this.f1119e != null) {
            for (int i = 0; i < this.f1119e.size(); i++) {
                Fragment fragment = (Fragment) this.f1119e.get(i);
                if (fragment != null) {
                    m1650a(fragment);
                }
            }
        }
    }

    /* renamed from: d */
    void m1682d(Fragment fragment) {
        if (fragment != null) {
            int i = this.f1127m;
            if (fragment.mRemoving) {
                i = fragment.isInBackStack() ? Math.min(i, 1) : Math.min(i, 0);
            }
            m1651a(fragment, i, fragment.getNextTransition(), fragment.getNextTransitionStyle(), false);
            if (fragment.mView != null) {
                Fragment n = m1632n(fragment);
                if (n != null) {
                    View view = n.mView;
                    ViewGroup viewGroup = fragment.mContainer;
                    int indexOfChild = viewGroup.indexOfChild(view);
                    i = viewGroup.indexOfChild(fragment.mView);
                    if (i < indexOfChild) {
                        viewGroup.removeViewAt(i);
                        viewGroup.addView(fragment.mView, indexOfChild);
                    }
                }
                if (fragment.mIsNewlyAdded && fragment.mContainer != null) {
                    if (VERSION.SDK_INT < 11) {
                        fragment.mView.setVisibility(0);
                    } else if (fragment.mPostponedAlpha > BitmapDescriptorFactory.HUE_RED) {
                        fragment.mView.setAlpha(fragment.mPostponedAlpha);
                    }
                    fragment.mPostponedAlpha = BitmapDescriptorFactory.HUE_RED;
                    fragment.mIsNewlyAdded = false;
                    Animation a = m1643a(fragment, fragment.getNextTransition(), true, fragment.getNextTransitionStyle());
                    if (a != null) {
                        m1626b(fragment.mView, a);
                        fragment.mView.startAnimation(a);
                    }
                }
            }
            if (fragment.mHiddenChanged) {
                m1678c(fragment);
            }
        }
    }

    /* renamed from: d */
    void m1683d(Fragment fragment, boolean z) {
        if (this.f1130p != null) {
            C0357x fragmentManager = this.f1130p.getFragmentManager();
            if (fragmentManager instanceof C0366y) {
                ((C0366y) fragmentManager).m1683d(fragment, true);
            }
        }
        if (this.f1116H != null) {
            Iterator it = this.f1116H.iterator();
            while (it.hasNext()) {
                C0477i c0477i = (C0477i) it.next();
                if (!z || ((Boolean) c0477i.f1265b).booleanValue()) {
                    ((C0355a) c0477i.f1264a).m1589c(this, fragment);
                }
            }
        }
    }

    /* renamed from: e */
    void m1684e(Fragment fragment) {
        if (fragment.mIndex < 0) {
            if (this.f1121g == null || this.f1121g.size() <= 0) {
                if (this.f1119e == null) {
                    this.f1119e = new ArrayList();
                }
                fragment.setIndex(this.f1119e.size(), this.f1130p);
                this.f1119e.add(fragment);
            } else {
                fragment.setIndex(((Integer) this.f1121g.remove(this.f1121g.size() - 1)).intValue(), this.f1130p);
                this.f1119e.set(fragment.mIndex, fragment);
            }
            if (f1110a) {
                Log.v("FragmentManager", "Allocated fragment index " + fragment);
            }
        }
    }

    /* renamed from: e */
    void m1685e(Fragment fragment, boolean z) {
        if (this.f1130p != null) {
            C0357x fragmentManager = this.f1130p.getFragmentManager();
            if (fragmentManager instanceof C0366y) {
                ((C0366y) fragmentManager).m1685e(fragment, true);
            }
        }
        if (this.f1116H != null) {
            Iterator it = this.f1116H.iterator();
            while (it.hasNext()) {
                C0477i c0477i = (C0477i) it.next();
                if (!z || ((Boolean) c0477i.f1265b).booleanValue()) {
                    ((C0355a) c0477i.f1264a).m1591d(this, fragment);
                }
            }
        }
    }

    /* renamed from: e */
    public boolean m1686e() {
        m1629c(true);
        boolean z = false;
        while (m1630c(this.f1136w, this.f1137x)) {
            this.f1118d = true;
            try {
                m1627b(this.f1136w, this.f1137x);
                m1635x();
                z = true;
            } catch (Throwable th) {
                m1635x();
                throw th;
            }
        }
        m1687f();
        return z;
    }

    /* renamed from: f */
    void m1687f() {
        if (this.f1135v) {
            int i = 0;
            for (int i2 = 0; i2 < this.f1119e.size(); i2++) {
                Fragment fragment = (Fragment) this.f1119e.get(i2);
                if (!(fragment == null || fragment.mLoaderManager == null)) {
                    i |= fragment.mLoaderManager.mo204a();
                }
            }
            if (i == 0) {
                this.f1135v = false;
                m1681d();
            }
        }
    }

    /* renamed from: f */
    void m1688f(Fragment fragment) {
        if (fragment.mIndex >= 0) {
            if (f1110a) {
                Log.v("FragmentManager", "Freeing fragment index " + fragment);
            }
            this.f1119e.set(fragment.mIndex, null);
            if (this.f1121g == null) {
                this.f1121g = new ArrayList();
            }
            this.f1121g.add(Integer.valueOf(fragment.mIndex));
            this.f1128n.m1503b(fragment.mWho);
            fragment.initState();
        }
    }

    /* renamed from: f */
    void m1689f(Fragment fragment, boolean z) {
        if (this.f1130p != null) {
            C0357x fragmentManager = this.f1130p.getFragmentManager();
            if (fragmentManager instanceof C0366y) {
                ((C0366y) fragmentManager).m1689f(fragment, true);
            }
        }
        if (this.f1116H != null) {
            Iterator it = this.f1116H.iterator();
            while (it.hasNext()) {
                C0477i c0477i = (C0477i) it.next();
                if (!z || ((Boolean) c0477i.f1265b).booleanValue()) {
                    ((C0355a) c0477i.f1264a).m1592e(this, fragment);
                }
            }
        }
    }

    /* renamed from: g */
    void m1690g() {
        if (this.f1126l != null) {
            for (int i = 0; i < this.f1126l.size(); i++) {
                ((C0356b) this.f1126l.get(i)).m1595a();
            }
        }
    }

    /* renamed from: g */
    public void m1691g(Fragment fragment) {
        if (f1110a) {
            Log.v("FragmentManager", "remove: " + fragment + " nesting=" + fragment.mBackStackNesting);
        }
        boolean z = !fragment.isInBackStack();
        if (!fragment.mDetached || z) {
            if (this.f1120f != null) {
                this.f1120f.remove(fragment);
            }
            if (fragment.mHasMenu && fragment.mMenuVisible) {
                this.f1131r = true;
            }
            fragment.mAdded = false;
            fragment.mRemoving = true;
        }
    }

    /* renamed from: g */
    void m1692g(Fragment fragment, boolean z) {
        if (this.f1130p != null) {
            C0357x fragmentManager = this.f1130p.getFragmentManager();
            if (fragmentManager instanceof C0366y) {
                ((C0366y) fragmentManager).m1692g(fragment, true);
            }
        }
        if (this.f1116H != null) {
            Iterator it = this.f1116H.iterator();
            while (it.hasNext()) {
                C0477i c0477i = (C0477i) it.next();
                if (!z || ((Boolean) c0477i.f1265b).booleanValue()) {
                    ((C0355a) c0477i.f1264a).m1593f(this, fragment);
                }
            }
        }
    }

    /* renamed from: h */
    C0367z m1693h() {
        List list;
        List list2;
        if (this.f1119e != null) {
            int i = 0;
            list = null;
            list2 = null;
            while (i < this.f1119e.size()) {
                ArrayList arrayList;
                Fragment fragment = (Fragment) this.f1119e.get(i);
                if (fragment != null) {
                    boolean z;
                    if (fragment.mRetainInstance) {
                        if (list2 == null) {
                            arrayList = new ArrayList();
                        }
                        arrayList.add(fragment);
                        fragment.mRetaining = true;
                        fragment.mTargetIndex = fragment.mTarget != null ? fragment.mTarget.mIndex : -1;
                        if (f1110a) {
                            Log.v("FragmentManager", "retainNonConfig: keeping retained " + fragment);
                        }
                    }
                    if (fragment.mChildFragmentManager != null) {
                        C0367z h = fragment.mChildFragmentManager.m1693h();
                        if (h != null) {
                            ArrayList arrayList2;
                            if (list == null) {
                                arrayList2 = new ArrayList();
                                for (int i2 = 0; i2 < i; i2++) {
                                    arrayList2.add(null);
                                }
                            } else {
                                arrayList2 = list;
                            }
                            arrayList2.add(h);
                            list = arrayList2;
                            z = true;
                            if (!(list == null || r0)) {
                                list.add(null);
                            }
                        }
                    }
                    z = false;
                    list.add(null);
                }
                i++;
                Object obj = arrayList;
            }
        } else {
            list = null;
            list2 = null;
        }
        return (list2 == null && list == null) ? null : new C0367z(list2, list);
    }

    /* renamed from: h */
    public void m1694h(Fragment fragment) {
        boolean z = true;
        if (f1110a) {
            Log.v("FragmentManager", "hide: " + fragment);
        }
        if (!fragment.mHidden) {
            fragment.mHidden = true;
            if (fragment.mHiddenChanged) {
                z = false;
            }
            fragment.mHiddenChanged = z;
        }
    }

    /* renamed from: h */
    void m1695h(Fragment fragment, boolean z) {
        if (this.f1130p != null) {
            C0357x fragmentManager = this.f1130p.getFragmentManager();
            if (fragmentManager instanceof C0366y) {
                ((C0366y) fragmentManager).m1695h(fragment, true);
            }
        }
        if (this.f1116H != null) {
            Iterator it = this.f1116H.iterator();
            while (it.hasNext()) {
                C0477i c0477i = (C0477i) it.next();
                if (!z || ((Boolean) c0477i.f1265b).booleanValue()) {
                    ((C0355a) c0477i.f1264a).m1594g(this, fragment);
                }
            }
        }
    }

    /* renamed from: i */
    Parcelable m1696i() {
        BackStackState[] backStackStateArr = null;
        m1636y();
        m1637z();
        m1686e();
        if (f1111b) {
            this.f1132s = true;
        }
        if (this.f1119e == null || this.f1119e.size() <= 0) {
            return null;
        }
        int size = this.f1119e.size();
        FragmentState[] fragmentStateArr = new FragmentState[size];
        int i = 0;
        boolean z = false;
        while (i < size) {
            boolean z2;
            Fragment fragment = (Fragment) this.f1119e.get(i);
            if (fragment != null) {
                if (fragment.mIndex < 0) {
                    m1618a(new IllegalStateException("Failure saving state: active " + fragment + " has cleared index: " + fragment.mIndex));
                }
                FragmentState fragmentState = new FragmentState(fragment);
                fragmentStateArr[i] = fragmentState;
                if (fragment.mState <= 0 || fragmentState.f764k != null) {
                    fragmentState.f764k = fragment.mSavedFragmentState;
                } else {
                    fragmentState.f764k = m1704m(fragment);
                    if (fragment.mTarget != null) {
                        if (fragment.mTarget.mIndex < 0) {
                            m1618a(new IllegalStateException("Failure saving state: " + fragment + " has target not in fragment manager: " + fragment.mTarget));
                        }
                        if (fragmentState.f764k == null) {
                            fragmentState.f764k = new Bundle();
                        }
                        m1648a(fragmentState.f764k, "android:target_state", fragment.mTarget);
                        if (fragment.mTargetRequestCode != 0) {
                            fragmentState.f764k.putInt("android:target_req_state", fragment.mTargetRequestCode);
                        }
                    }
                }
                if (f1110a) {
                    Log.v("FragmentManager", "Saved state of " + fragment + ": " + fragmentState.f764k);
                }
                z2 = true;
            } else {
                z2 = z;
            }
            i++;
            z = z2;
        }
        if (z) {
            int[] iArr;
            int i2;
            FragmentManagerState fragmentManagerState;
            if (this.f1120f != null) {
                i = this.f1120f.size();
                if (i > 0) {
                    iArr = new int[i];
                    for (i2 = 0; i2 < i; i2++) {
                        iArr[i2] = ((Fragment) this.f1120f.get(i2)).mIndex;
                        if (iArr[i2] < 0) {
                            m1618a(new IllegalStateException("Failure saving state: active " + this.f1120f.get(i2) + " has cleared index: " + iArr[i2]));
                        }
                        if (f1110a) {
                            Log.v("FragmentManager", "saveAllState: adding fragment #" + i2 + ": " + this.f1120f.get(i2));
                        }
                    }
                    if (this.f1122h != null) {
                        i = this.f1122h.size();
                        if (i > 0) {
                            backStackStateArr = new BackStackState[i];
                            for (i2 = 0; i2 < i; i2++) {
                                backStackStateArr[i2] = new BackStackState((C0341l) this.f1122h.get(i2));
                                if (f1110a) {
                                    Log.v("FragmentManager", "saveAllState: adding back stack #" + i2 + ": " + this.f1122h.get(i2));
                                }
                            }
                        }
                    }
                    fragmentManagerState = new FragmentManagerState();
                    fragmentManagerState.f751a = fragmentStateArr;
                    fragmentManagerState.f752b = iArr;
                    fragmentManagerState.f753c = backStackStateArr;
                    return fragmentManagerState;
                }
            }
            iArr = null;
            if (this.f1122h != null) {
                i = this.f1122h.size();
                if (i > 0) {
                    backStackStateArr = new BackStackState[i];
                    for (i2 = 0; i2 < i; i2++) {
                        backStackStateArr[i2] = new BackStackState((C0341l) this.f1122h.get(i2));
                        if (f1110a) {
                            Log.v("FragmentManager", "saveAllState: adding back stack #" + i2 + ": " + this.f1122h.get(i2));
                        }
                    }
                }
            }
            fragmentManagerState = new FragmentManagerState();
            fragmentManagerState.f751a = fragmentStateArr;
            fragmentManagerState.f752b = iArr;
            fragmentManagerState.f753c = backStackStateArr;
            return fragmentManagerState;
        } else if (!f1110a) {
            return null;
        } else {
            Log.v("FragmentManager", "saveAllState: no fragments!");
            return null;
        }
    }

    /* renamed from: i */
    public void m1697i(Fragment fragment) {
        boolean z = false;
        if (f1110a) {
            Log.v("FragmentManager", "show: " + fragment);
        }
        if (fragment.mHidden) {
            fragment.mHidden = false;
            if (!fragment.mHiddenChanged) {
                z = true;
            }
            fragment.mHiddenChanged = z;
        }
    }

    /* renamed from: j */
    public void m1698j() {
        this.f1132s = false;
    }

    /* renamed from: j */
    public void m1699j(Fragment fragment) {
        if (f1110a) {
            Log.v("FragmentManager", "detach: " + fragment);
        }
        if (!fragment.mDetached) {
            fragment.mDetached = true;
            if (fragment.mAdded) {
                if (this.f1120f != null) {
                    if (f1110a) {
                        Log.v("FragmentManager", "remove from detach: " + fragment);
                    }
                    this.f1120f.remove(fragment);
                }
                if (fragment.mHasMenu && fragment.mMenuVisible) {
                    this.f1131r = true;
                }
                fragment.mAdded = false;
            }
        }
    }

    /* renamed from: k */
    public void m1700k() {
        this.f1132s = false;
        this.f1118d = true;
        m1646a(1, false);
        this.f1118d = false;
    }

    /* renamed from: k */
    public void m1701k(Fragment fragment) {
        if (f1110a) {
            Log.v("FragmentManager", "attach: " + fragment);
        }
        if (fragment.mDetached) {
            fragment.mDetached = false;
            if (!fragment.mAdded) {
                if (this.f1120f == null) {
                    this.f1120f = new ArrayList();
                }
                if (this.f1120f.contains(fragment)) {
                    throw new IllegalStateException("Fragment already added: " + fragment);
                }
                if (f1110a) {
                    Log.v("FragmentManager", "add from attach: " + fragment);
                }
                this.f1120f.add(fragment);
                fragment.mAdded = true;
                if (fragment.mHasMenu && fragment.mMenuVisible) {
                    this.f1131r = true;
                }
            }
        }
    }

    /* renamed from: l */
    public void m1702l() {
        this.f1132s = false;
        this.f1118d = true;
        m1646a(2, false);
        this.f1118d = false;
    }

    /* renamed from: l */
    void m1703l(Fragment fragment) {
        if (fragment.mInnerView != null) {
            if (this.f1113A == null) {
                this.f1113A = new SparseArray();
            } else {
                this.f1113A.clear();
            }
            fragment.mInnerView.saveHierarchyState(this.f1113A);
            if (this.f1113A.size() > 0) {
                fragment.mSavedViewState = this.f1113A;
                this.f1113A = null;
            }
        }
    }

    /* renamed from: m */
    Bundle m1704m(Fragment fragment) {
        Bundle bundle;
        if (this.f1139z == null) {
            this.f1139z = new Bundle();
        }
        fragment.performSaveInstanceState(this.f1139z);
        m1679c(fragment, this.f1139z, false);
        if (this.f1139z.isEmpty()) {
            bundle = null;
        } else {
            bundle = this.f1139z;
            this.f1139z = null;
        }
        if (fragment.mView != null) {
            m1703l(fragment);
        }
        if (fragment.mSavedViewState != null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putSparseParcelableArray("android:view_state", fragment.mSavedViewState);
        }
        if (!fragment.mUserVisibleHint) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putBoolean("android:user_visible_hint", fragment.mUserVisibleHint);
        }
        return bundle;
    }

    /* renamed from: m */
    public void m1705m() {
        this.f1132s = false;
        this.f1118d = true;
        m1646a(4, false);
        this.f1118d = false;
    }

    /* renamed from: n */
    public void m1706n() {
        this.f1132s = false;
        this.f1118d = true;
        m1646a(5, false);
        this.f1118d = false;
    }

    /* renamed from: o */
    public void m1707o() {
        this.f1118d = true;
        m1646a(4, false);
        this.f1118d = false;
    }

    /* renamed from: p */
    public void m1708p() {
        this.f1132s = true;
        this.f1118d = true;
        m1646a(3, false);
        this.f1118d = false;
    }

    /* renamed from: q */
    public void m1709q() {
        this.f1118d = true;
        m1646a(2, false);
        this.f1118d = false;
    }

    /* renamed from: r */
    public void m1710r() {
        this.f1118d = true;
        m1646a(1, false);
        this.f1118d = false;
    }

    /* renamed from: s */
    public void m1711s() {
        this.f1133t = true;
        m1686e();
        this.f1118d = true;
        m1646a(0, false);
        this.f1118d = false;
        this.f1128n = null;
        this.f1129o = null;
        this.f1130p = null;
    }

    /* renamed from: t */
    public void m1712t() {
        if (this.f1120f != null) {
            for (int i = 0; i < this.f1120f.size(); i++) {
                Fragment fragment = (Fragment) this.f1120f.get(i);
                if (fragment != null) {
                    fragment.performLowMemory();
                }
            }
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("FragmentManager{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" in ");
        if (this.f1130p != null) {
            C0468d.m2014a(this.f1130p, stringBuilder);
        } else {
            C0468d.m2014a(this.f1128n, stringBuilder);
        }
        stringBuilder.append("}}");
        return stringBuilder.toString();
    }

    /* renamed from: u */
    C0365n m1713u() {
        return this;
    }
}
