package android.support.v7.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources.Theme;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.LayerDrawable;
import android.os.Build.VERSION;
import android.support.p005a.p006a.C0008c;
import android.support.p005a.p006a.C0020g;
import android.support.v4.content.C0235a;
import android.support.v4.p007b.C0392a;
import android.support.v4.p007b.p008a.C0375a;
import android.support.v4.p022f.C0464a;
import android.support.v4.p022f.C0470f;
import android.support.v4.p022f.C0471g;
import android.support.v4.p022f.C0482l;
import android.support.v7.p025a.C0748a.C0738a;
import android.support.v7.p025a.C0748a.C0740c;
import android.support.v7.p025a.C0748a.C0742e;
import android.support.v7.p027c.p028a.C0825b;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParser;

/* renamed from: android.support.v7.widget.l */
public final class C1069l {
    /* renamed from: a */
    private static final Mode f3164a = Mode.SRC_IN;
    /* renamed from: b */
    private static C1069l f3165b;
    /* renamed from: c */
    private static final C1067b f3166c = new C1067b(6);
    /* renamed from: d */
    private static final int[] f3167d = new int[]{C0742e.abc_textfield_search_default_mtrl_alpha, C0742e.abc_textfield_default_mtrl_alpha, C0742e.abc_ab_share_pack_mtrl_alpha};
    /* renamed from: e */
    private static final int[] f3168e = new int[]{C0742e.abc_ic_commit_search_api_mtrl_alpha, C0742e.abc_seekbar_tick_mark_material, C0742e.abc_ic_menu_share_mtrl_alpha, C0742e.abc_ic_menu_copy_mtrl_am_alpha, C0742e.abc_ic_menu_cut_mtrl_alpha, C0742e.abc_ic_menu_selectall_mtrl_alpha, C0742e.abc_ic_menu_paste_mtrl_am_alpha};
    /* renamed from: f */
    private static final int[] f3169f = new int[]{C0742e.abc_textfield_activated_mtrl_alpha, C0742e.abc_textfield_search_activated_mtrl_alpha, C0742e.abc_cab_background_top_mtrl_alpha, C0742e.abc_text_cursor_material, C0742e.abc_text_select_handle_left_mtrl_dark, C0742e.abc_text_select_handle_middle_mtrl_dark, C0742e.abc_text_select_handle_right_mtrl_dark, C0742e.abc_text_select_handle_left_mtrl_light, C0742e.abc_text_select_handle_middle_mtrl_light, C0742e.abc_text_select_handle_right_mtrl_light};
    /* renamed from: g */
    private static final int[] f3170g = new int[]{C0742e.abc_popup_background_mtrl_mult, C0742e.abc_cab_background_internal_bg, C0742e.abc_menu_hardkey_panel_mtrl_mult};
    /* renamed from: h */
    private static final int[] f3171h = new int[]{C0742e.abc_tab_indicator_material, C0742e.abc_textfield_search_material};
    /* renamed from: i */
    private static final int[] f3172i = new int[]{C0742e.abc_btn_check_material, C0742e.abc_btn_radio_material};
    /* renamed from: j */
    private WeakHashMap<Context, C0482l<ColorStateList>> f3173j;
    /* renamed from: k */
    private C0464a<String, C1065c> f3174k;
    /* renamed from: l */
    private C0482l<String> f3175l;
    /* renamed from: m */
    private final Object f3176m = new Object();
    /* renamed from: n */
    private final WeakHashMap<Context, C0470f<WeakReference<ConstantState>>> f3177n = new WeakHashMap(0);
    /* renamed from: o */
    private TypedValue f3178o;
    /* renamed from: p */
    private boolean f3179p;

    /* renamed from: android.support.v7.widget.l$c */
    private interface C1065c {
        /* renamed from: a */
        Drawable mo1008a(Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme);
    }

    @TargetApi(11)
    /* renamed from: android.support.v7.widget.l$a */
    private static class C1066a implements C1065c {
        C1066a() {
        }

        @SuppressLint({"NewApi"})
        /* renamed from: a */
        public Drawable mo1008a(Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme) {
            try {
                return C0008c.m5a(context, context.getResources(), xmlPullParser, attributeSet, theme);
            } catch (Throwable e) {
                Log.e("AvdcInflateDelegate", "Exception while inflating <animated-vector>", e);
                return null;
            }
        }
    }

    /* renamed from: android.support.v7.widget.l$b */
    private static class C1067b extends C0471g<Integer, PorterDuffColorFilter> {
        public C1067b(int i) {
            super(i);
        }

        /* renamed from: b */
        private static int m5855b(int i, Mode mode) {
            return ((i + 31) * 31) + mode.hashCode();
        }

        /* renamed from: a */
        PorterDuffColorFilter m5856a(int i, Mode mode) {
            return (PorterDuffColorFilter) get(Integer.valueOf(C1067b.m5855b(i, mode)));
        }

        /* renamed from: a */
        PorterDuffColorFilter m5857a(int i, Mode mode, PorterDuffColorFilter porterDuffColorFilter) {
            return (PorterDuffColorFilter) put(Integer.valueOf(C1067b.m5855b(i, mode)), porterDuffColorFilter);
        }
    }

    /* renamed from: android.support.v7.widget.l$d */
    private static class C1068d implements C1065c {
        C1068d() {
        }

        @SuppressLint({"NewApi"})
        /* renamed from: a */
        public Drawable mo1008a(Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme) {
            try {
                return C0020g.m60a(context.getResources(), xmlPullParser, attributeSet, theme);
            } catch (Throwable e) {
                Log.e("VdcInflateDelegate", "Exception while inflating <vector>", e);
                return null;
            }
        }
    }

    /* renamed from: a */
    private static long m5859a(TypedValue typedValue) {
        return (((long) typedValue.assetCookie) << 32) | ((long) typedValue.data);
    }

    /* renamed from: a */
    static Mode m5860a(int i) {
        return i == C0742e.abc_switch_thumb_material ? Mode.MULTIPLY : null;
    }

    /* renamed from: a */
    public static PorterDuffColorFilter m5861a(int i, Mode mode) {
        PorterDuffColorFilter a = f3166c.m5856a(i, mode);
        if (a != null) {
            return a;
        }
        a = new PorterDuffColorFilter(i, mode);
        f3166c.m5857a(i, mode, a);
        return a;
    }

    /* renamed from: a */
    private static PorterDuffColorFilter m5862a(ColorStateList colorStateList, Mode mode, int[] iArr) {
        return (colorStateList == null || mode == null) ? null : C1069l.m5861a(colorStateList.getColorForState(iArr, 0), mode);
    }

    /* renamed from: a */
    private Drawable m5863a(Context context, int i, boolean z, Drawable drawable) {
        ColorStateList b = m5887b(context, i);
        if (b != null) {
            if (ai.m5432b(drawable)) {
                drawable = drawable.mutate();
            }
            drawable = C0375a.m1784g(drawable);
            C0375a.m1773a(drawable, b);
            Mode a = C1069l.m5860a(i);
            if (a == null) {
                return drawable;
            }
            C0375a.m1776a(drawable, a);
            return drawable;
        } else if (i == C0742e.abc_seekbar_track_material) {
            r0 = (LayerDrawable) drawable;
            C1069l.m5867a(r0.findDrawableByLayerId(16908288), bf.m5642a(context, C0738a.colorControlNormal), f3164a);
            C1069l.m5867a(r0.findDrawableByLayerId(16908303), bf.m5642a(context, C0738a.colorControlNormal), f3164a);
            C1069l.m5867a(r0.findDrawableByLayerId(16908301), bf.m5642a(context, C0738a.colorControlActivated), f3164a);
            return drawable;
        } else if (i != C0742e.abc_ratingbar_material && i != C0742e.abc_ratingbar_indicator_material && i != C0742e.abc_ratingbar_small_material) {
            return (C1069l.m5871a(context, i, drawable) || !z) ? drawable : null;
        } else {
            r0 = (LayerDrawable) drawable;
            C1069l.m5867a(r0.findDrawableByLayerId(16908288), bf.m5646c(context, C0738a.colorControlNormal), f3164a);
            C1069l.m5867a(r0.findDrawableByLayerId(16908303), bf.m5642a(context, C0738a.colorControlActivated), f3164a);
            C1069l.m5867a(r0.findDrawableByLayerId(16908301), bf.m5642a(context, C0738a.colorControlActivated), f3164a);
            return drawable;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    private android.graphics.drawable.Drawable m5864a(android.content.Context r5, long r6) {
        /*
        r4 = this;
        r2 = 0;
        r3 = r4.f3176m;
        monitor-enter(r3);
        r0 = r4.f3177n;	 Catch:{ all -> 0x002b }
        r0 = r0.get(r5);	 Catch:{ all -> 0x002b }
        r0 = (android.support.v4.p022f.C0470f) r0;	 Catch:{ all -> 0x002b }
        if (r0 != 0) goto L_0x0011;
    L_0x000e:
        monitor-exit(r3);	 Catch:{ all -> 0x002b }
        r0 = r2;
    L_0x0010:
        return r0;
    L_0x0011:
        r1 = r0.m2018a(r6);	 Catch:{ all -> 0x002b }
        r1 = (java.lang.ref.WeakReference) r1;	 Catch:{ all -> 0x002b }
        if (r1 == 0) goto L_0x0031;
    L_0x0019:
        r1 = r1.get();	 Catch:{ all -> 0x002b }
        r1 = (android.graphics.drawable.Drawable.ConstantState) r1;	 Catch:{ all -> 0x002b }
        if (r1 == 0) goto L_0x002e;
    L_0x0021:
        r0 = r5.getResources();	 Catch:{ all -> 0x002b }
        r0 = r1.newDrawable(r0);	 Catch:{ all -> 0x002b }
        monitor-exit(r3);	 Catch:{ all -> 0x002b }
        goto L_0x0010;
    L_0x002b:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x002b }
        throw r0;
    L_0x002e:
        r0.m2023b(r6);	 Catch:{ all -> 0x002b }
    L_0x0031:
        monitor-exit(r3);	 Catch:{ all -> 0x002b }
        r0 = r2;
        goto L_0x0010;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.l.a(android.content.Context, long):android.graphics.drawable.Drawable");
    }

    /* renamed from: a */
    public static C1069l m5865a() {
        if (f3165b == null) {
            f3165b = new C1069l();
            C1069l.m5869a(f3165b);
        }
        return f3165b;
    }

    /* renamed from: a */
    private void m5866a(Context context, int i, ColorStateList colorStateList) {
        if (this.f3173j == null) {
            this.f3173j = new WeakHashMap();
        }
        C0482l c0482l = (C0482l) this.f3173j.get(context);
        if (c0482l == null) {
            c0482l = new C0482l();
            this.f3173j.put(context, c0482l);
        }
        c0482l.m2047c(i, colorStateList);
    }

    /* renamed from: a */
    private static void m5867a(Drawable drawable, int i, Mode mode) {
        if (ai.m5432b(drawable)) {
            drawable = drawable.mutate();
        }
        if (mode == null) {
            mode = f3164a;
        }
        drawable.setColorFilter(C1069l.m5861a(i, mode));
    }

    /* renamed from: a */
    static void m5868a(Drawable drawable, bi biVar, int[] iArr) {
        if (!ai.m5432b(drawable) || drawable.mutate() == drawable) {
            if (biVar.f3042d || biVar.f3041c) {
                drawable.setColorFilter(C1069l.m5862a(biVar.f3042d ? biVar.f3039a : null, biVar.f3041c ? biVar.f3040b : f3164a, iArr));
            } else {
                drawable.clearColorFilter();
            }
            if (VERSION.SDK_INT <= 23) {
                drawable.invalidateSelf();
                return;
            }
            return;
        }
        Log.d("AppCompatDrawableManager", "Mutated drawable is not the same instance as the input.");
    }

    /* renamed from: a */
    private static void m5869a(C1069l c1069l) {
        if (VERSION.SDK_INT < 24) {
            c1069l.m5870a("vector", new C1068d());
            if (VERSION.SDK_INT >= 11) {
                c1069l.m5870a("animated-vector", new C1066a());
            }
        }
    }

    /* renamed from: a */
    private void m5870a(String str, C1065c c1065c) {
        if (this.f3174k == null) {
            this.f3174k = new C0464a();
        }
        this.f3174k.put(str, c1065c);
    }

    /* renamed from: a */
    static boolean m5871a(Context context, int i, Drawable drawable) {
        int i2;
        Mode mode;
        boolean z;
        int i3;
        Mode mode2 = f3164a;
        if (C1069l.m5874a(f3167d, i)) {
            i2 = C0738a.colorControlNormal;
            mode = mode2;
            z = true;
            i3 = -1;
        } else if (C1069l.m5874a(f3169f, i)) {
            i2 = C0738a.colorControlActivated;
            mode = mode2;
            z = true;
            i3 = -1;
        } else if (C1069l.m5874a(f3170g, i)) {
            z = true;
            mode = Mode.MULTIPLY;
            i2 = 16842801;
            i3 = -1;
        } else if (i == C0742e.abc_list_divider_mtrl_alpha) {
            i2 = 16842800;
            i3 = Math.round(40.8f);
            mode = mode2;
            z = true;
        } else if (i == C0742e.abc_dialog_material_background) {
            i2 = 16842801;
            mode = mode2;
            z = true;
            i3 = -1;
        } else {
            i3 = -1;
            i2 = 0;
            mode = mode2;
            z = false;
        }
        if (!z) {
            return false;
        }
        if (ai.m5432b(drawable)) {
            drawable = drawable.mutate();
        }
        drawable.setColorFilter(C1069l.m5861a(bf.m5642a(context, i2), mode));
        if (i3 == -1) {
            return true;
        }
        drawable.setAlpha(i3);
        return true;
    }

    /* renamed from: a */
    private boolean m5872a(Context context, long j, Drawable drawable) {
        ConstantState constantState = drawable.getConstantState();
        if (constantState == null) {
            return false;
        }
        synchronized (this.f3176m) {
            C0470f c0470f = (C0470f) this.f3177n.get(context);
            if (c0470f == null) {
                c0470f = new C0470f();
                this.f3177n.put(context, c0470f);
            }
            c0470f.m2024b(j, new WeakReference(constantState));
        }
        return true;
    }

    /* renamed from: a */
    private static boolean m5873a(Drawable drawable) {
        return (drawable instanceof C0020g) || "android.graphics.drawable.VectorDrawable".equals(drawable.getClass().getName());
    }

    /* renamed from: a */
    private static boolean m5874a(int[] iArr, int i) {
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: b */
    private ColorStateList m5875b(Context context) {
        return m5882f(context, bf.m5642a(context, C0738a.colorButtonNormal));
    }

    /* renamed from: c */
    private ColorStateList m5876c(Context context) {
        return m5882f(context, 0);
    }

    /* renamed from: c */
    private Drawable m5877c(Context context, int i) {
        if (this.f3178o == null) {
            this.f3178o = new TypedValue();
        }
        TypedValue typedValue = this.f3178o;
        context.getResources().getValue(i, typedValue, true);
        long a = C1069l.m5859a(typedValue);
        Drawable a2 = m5864a(context, a);
        if (a2 == null) {
            if (i == C0742e.abc_cab_background_top_material) {
                a2 = new LayerDrawable(new Drawable[]{m5883a(context, C0742e.abc_cab_background_internal_bg), m5883a(context, C0742e.abc_cab_background_top_mtrl_alpha)});
            }
            if (a2 != null) {
                a2.setChangingConfigurations(typedValue.changingConfigurations);
                m5872a(context, a, a2);
            }
        }
        return a2;
    }

    /* renamed from: d */
    private ColorStateList m5878d(Context context) {
        return m5882f(context, bf.m5642a(context, C0738a.colorAccent));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: d */
    private android.graphics.drawable.Drawable m5879d(android.content.Context r10, int r11) {
        /*
        r9 = this;
        r1 = 0;
        r8 = 2;
        r7 = 1;
        r0 = r9.f3174k;
        if (r0 == 0) goto L_0x00c5;
    L_0x0007:
        r0 = r9.f3174k;
        r0 = r0.isEmpty();
        if (r0 != 0) goto L_0x00c5;
    L_0x000f:
        r0 = r9.f3175l;
        if (r0 == 0) goto L_0x0030;
    L_0x0013:
        r0 = r9.f3175l;
        r0 = r0.m2040a(r11);
        r0 = (java.lang.String) r0;
        r2 = "appcompat_skip_skip";
        r2 = r2.equals(r0);
        if (r2 != 0) goto L_0x002e;
    L_0x0024:
        if (r0 == 0) goto L_0x0037;
    L_0x0026:
        r2 = r9.f3174k;
        r0 = r2.get(r0);
        if (r0 != 0) goto L_0x0037;
    L_0x002e:
        r0 = r1;
    L_0x002f:
        return r0;
    L_0x0030:
        r0 = new android.support.v4.f.l;
        r0.<init>();
        r9.f3175l = r0;
    L_0x0037:
        r0 = r9.f3178o;
        if (r0 != 0) goto L_0x0042;
    L_0x003b:
        r0 = new android.util.TypedValue;
        r0.<init>();
        r9.f3178o = r0;
    L_0x0042:
        r2 = r9.f3178o;
        r0 = r10.getResources();
        r0.getValue(r11, r2, r7);
        r4 = android.support.v7.widget.C1069l.m5859a(r2);
        r1 = r9.m5864a(r10, r4);
        if (r1 == 0) goto L_0x0057;
    L_0x0055:
        r0 = r1;
        goto L_0x002f;
    L_0x0057:
        r3 = r2.string;
        if (r3 == 0) goto L_0x008f;
    L_0x005b:
        r3 = r2.string;
        r3 = r3.toString();
        r6 = ".xml";
        r3 = r3.endsWith(r6);
        if (r3 == 0) goto L_0x008f;
    L_0x006a:
        r3 = r0.getXml(r11);	 Catch:{ Exception -> 0x0085 }
        r6 = android.util.Xml.asAttributeSet(r3);	 Catch:{ Exception -> 0x0085 }
    L_0x0072:
        r0 = r3.next();	 Catch:{ Exception -> 0x0085 }
        if (r0 == r8) goto L_0x007a;
    L_0x0078:
        if (r0 != r7) goto L_0x0072;
    L_0x007a:
        if (r0 == r8) goto L_0x009b;
    L_0x007c:
        r0 = new org.xmlpull.v1.XmlPullParserException;	 Catch:{ Exception -> 0x0085 }
        r2 = "No start tag found";
        r0.<init>(r2);	 Catch:{ Exception -> 0x0085 }
        throw r0;	 Catch:{ Exception -> 0x0085 }
    L_0x0085:
        r0 = move-exception;
        r2 = "AppCompatDrawableManager";
        r3 = "Exception while inflating drawable";
        android.util.Log.e(r2, r3, r0);
    L_0x008f:
        r0 = r1;
    L_0x0090:
        if (r0 != 0) goto L_0x002f;
    L_0x0092:
        r1 = r9.f3175l;
        r2 = "appcompat_skip_skip";
        r1.m2047c(r11, r2);
        goto L_0x002f;
    L_0x009b:
        r0 = r3.getName();	 Catch:{ Exception -> 0x0085 }
        r7 = r9.f3175l;	 Catch:{ Exception -> 0x0085 }
        r7.m2047c(r11, r0);	 Catch:{ Exception -> 0x0085 }
        r7 = r9.f3174k;	 Catch:{ Exception -> 0x0085 }
        r0 = r7.get(r0);	 Catch:{ Exception -> 0x0085 }
        r0 = (android.support.v7.widget.C1069l.C1065c) r0;	 Catch:{ Exception -> 0x0085 }
        if (r0 == 0) goto L_0x00b6;
    L_0x00ae:
        r7 = r10.getTheme();	 Catch:{ Exception -> 0x0085 }
        r1 = r0.mo1008a(r10, r3, r6, r7);	 Catch:{ Exception -> 0x0085 }
    L_0x00b6:
        if (r1 == 0) goto L_0x00c3;
    L_0x00b8:
        r0 = r2.changingConfigurations;	 Catch:{ Exception -> 0x0085 }
        r1.setChangingConfigurations(r0);	 Catch:{ Exception -> 0x0085 }
        r0 = r9.m5872a(r10, r4, r1);	 Catch:{ Exception -> 0x0085 }
        if (r0 == 0) goto L_0x00c3;
    L_0x00c3:
        r0 = r1;
        goto L_0x0090;
    L_0x00c5:
        r0 = r1;
        goto L_0x002f;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.l.d(android.content.Context, int):android.graphics.drawable.Drawable");
    }

    /* renamed from: e */
    private ColorStateList m5880e(Context context, int i) {
        if (this.f3173j == null) {
            return null;
        }
        C0482l c0482l = (C0482l) this.f3173j.get(context);
        return c0482l != null ? (ColorStateList) c0482l.m2040a(i) : null;
    }

    /* renamed from: e */
    private void m5881e(Context context) {
        if (!this.f3179p) {
            this.f3179p = true;
            Drawable a = m5883a(context, C0742e.abc_vector_test);
            if (a == null || !C1069l.m5873a(a)) {
                this.f3179p = false;
                throw new IllegalStateException("This app has been built with an incorrect configuration. Please configure your build for VectorDrawableCompat.");
            }
        }
    }

    /* renamed from: f */
    private ColorStateList m5882f(Context context, int i) {
        r0 = new int[4][];
        r1 = new int[4];
        int a = bf.m5642a(context, C0738a.colorControlHighlight);
        int c = bf.m5646c(context, C0738a.colorButtonNormal);
        r0[0] = bf.f3025a;
        r1[0] = c;
        r0[1] = bf.f3028d;
        r1[1] = C0392a.m1825a(a, i);
        r0[2] = bf.f3026b;
        r1[2] = C0392a.m1825a(a, i);
        r0[3] = bf.f3032h;
        r1[3] = i;
        return new ColorStateList(r0, r1);
    }

    /* renamed from: a */
    public Drawable m5883a(Context context, int i) {
        return m5884a(context, i, false);
    }

    /* renamed from: a */
    Drawable m5884a(Context context, int i, boolean z) {
        m5881e(context);
        Drawable d = m5879d(context, i);
        if (d == null) {
            d = m5877c(context, i);
        }
        if (d == null) {
            d = C0235a.m1066a(context, i);
        }
        if (d != null) {
            d = m5863a(context, i, z, d);
        }
        if (d != null) {
            ai.m5431a(d);
        }
        return d;
    }

    /* renamed from: a */
    Drawable m5885a(Context context, bm bmVar, int i) {
        Drawable d = m5879d(context, i);
        if (d == null) {
            d = bmVar.m5717a(i);
        }
        return d != null ? m5863a(context, i, false, d) : null;
    }

    /* renamed from: a */
    public void m5886a(Context context) {
        synchronized (this.f3176m) {
            C0470f c0470f = (C0470f) this.f3177n.get(context);
            if (c0470f != null) {
                c0470f.m2026c();
            }
        }
    }

    /* renamed from: b */
    ColorStateList m5887b(Context context, int i) {
        ColorStateList e = m5880e(context, i);
        if (e == null) {
            if (i == C0742e.abc_edit_text_material) {
                e = C0825b.m3936a(context, C0740c.abc_tint_edittext);
            } else if (i == C0742e.abc_switch_track_mtrl_alpha) {
                e = C0825b.m3936a(context, C0740c.abc_tint_switch_track);
            } else if (i == C0742e.abc_switch_thumb_material) {
                e = C0825b.m3936a(context, C0740c.abc_tint_switch_thumb);
            } else if (i == C0742e.abc_btn_default_mtrl_shape) {
                e = m5875b(context);
            } else if (i == C0742e.abc_btn_borderless_material) {
                e = m5876c(context);
            } else if (i == C0742e.abc_btn_colored_material) {
                e = m5878d(context);
            } else if (i == C0742e.abc_spinner_mtrl_am_alpha || i == C0742e.abc_spinner_textfield_background_material) {
                e = C0825b.m3936a(context, C0740c.abc_tint_spinner);
            } else if (C1069l.m5874a(f3168e, i)) {
                e = bf.m5645b(context, C0738a.colorControlNormal);
            } else if (C1069l.m5874a(f3171h, i)) {
                e = C0825b.m3936a(context, C0740c.abc_tint_default);
            } else if (C1069l.m5874a(f3172i, i)) {
                e = C0825b.m3936a(context, C0740c.abc_tint_btn_checkable);
            } else if (i == C0742e.abc_seekbar_thumb_material) {
                e = C0825b.m3936a(context, C0740c.abc_tint_seek_thumb);
            }
            if (e != null) {
                m5866a(context, i, e);
            }
        }
        return e;
    }
}
