package android.support.v7.app;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.v4.p022f.C0464a;
import android.support.v4.view.ah;
import android.support.v7.p025a.C0748a.C0747j;
import android.support.v7.view.C0844d;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.C0193m;
import android.support.v7.widget.C0855y;
import android.support.v7.widget.C0957g;
import android.support.v7.widget.C1062i;
import android.support.v7.widget.C1063j;
import android.support.v7.widget.C1071o;
import android.support.v7.widget.C1075r;
import android.support.v7.widget.C1076s;
import android.support.v7.widget.C1077t;
import android.support.v7.widget.C1085v;
import android.support.v7.widget.bh;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;

/* renamed from: android.support.v7.app.m */
class C0803m {
    /* renamed from: a */
    private static final Class<?>[] f1854a = new Class[]{Context.class, AttributeSet.class};
    /* renamed from: b */
    private static final int[] f1855b = new int[]{16843375};
    /* renamed from: c */
    private static final String[] f1856c = new String[]{"android.widget.", "android.view.", "android.webkit."};
    /* renamed from: d */
    private static final Map<String, Constructor<? extends View>> f1857d = new C0464a();
    /* renamed from: e */
    private final Object[] f1858e = new Object[2];

    /* renamed from: android.support.v7.app.m$a */
    private static class C0802a implements OnClickListener {
        /* renamed from: a */
        private final View f1850a;
        /* renamed from: b */
        private final String f1851b;
        /* renamed from: c */
        private Method f1852c;
        /* renamed from: d */
        private Context f1853d;

        public C0802a(View view, String str) {
            this.f1850a = view;
            this.f1851b = str;
        }

        /* renamed from: a */
        private void m3814a(Context context, String str) {
            for (Context context2 = context; context2 != null; context2 = context2 instanceof ContextWrapper ? ((ContextWrapper) context2).getBaseContext() : null) {
                try {
                    if (!context2.isRestricted()) {
                        Method method = context2.getClass().getMethod(this.f1851b, new Class[]{View.class});
                        if (method != null) {
                            this.f1852c = method;
                            this.f1853d = context2;
                            return;
                        }
                    }
                } catch (NoSuchMethodException e) {
                }
            }
            int id = this.f1850a.getId();
            throw new IllegalStateException("Could not find method " + this.f1851b + "(View) in a parent or ancestor Context for android:onClick " + "attribute defined on view " + this.f1850a.getClass() + (id == -1 ? TtmlNode.ANONYMOUS_REGION_ID : " with id '" + this.f1850a.getContext().getResources().getResourceEntryName(id) + "'"));
        }

        public void onClick(View view) {
            if (this.f1852c == null) {
                m3814a(this.f1850a.getContext(), this.f1851b);
            }
            try {
                this.f1852c.invoke(this.f1853d, new Object[]{view});
            } catch (Throwable e) {
                throw new IllegalStateException("Could not execute non-public method for android:onClick", e);
            } catch (Throwable e2) {
                throw new IllegalStateException("Could not execute method for android:onClick", e2);
            }
        }
    }

    C0803m() {
    }

    /* renamed from: a */
    private static Context m3815a(Context context, AttributeSet attributeSet, boolean z, boolean z2) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0747j.View, 0, 0);
        int resourceId = z ? obtainStyledAttributes.getResourceId(C0747j.View_android_theme, 0) : 0;
        if (z2 && resourceId == 0) {
            resourceId = obtainStyledAttributes.getResourceId(C0747j.View_theme, 0);
            if (resourceId != 0) {
                Log.i("AppCompatViewInflater", "app:theme is now deprecated. Please move to using android:theme instead.");
            }
        }
        int i = resourceId;
        obtainStyledAttributes.recycle();
        return i != 0 ? ((context instanceof C0844d) && ((C0844d) context).m4024a() == i) ? context : new C0844d(context, i) : context;
    }

    /* renamed from: a */
    private View m3816a(Context context, String str, AttributeSet attributeSet) {
        if (str.equals("view")) {
            str = attributeSet.getAttributeValue(null, "class");
        }
        try {
            this.f1858e[0] = context;
            this.f1858e[1] = attributeSet;
            View a;
            if (-1 == str.indexOf(46)) {
                for (String a2 : f1856c) {
                    a = m3817a(context, str, a2);
                    if (a != null) {
                        return a;
                    }
                }
                this.f1858e[0] = null;
                this.f1858e[1] = null;
                return null;
            }
            a = m3817a(context, str, null);
            this.f1858e[0] = null;
            this.f1858e[1] = null;
            return a;
        } catch (Exception e) {
            return null;
        } finally {
            this.f1858e[0] = null;
            this.f1858e[1] = null;
        }
    }

    /* renamed from: a */
    private View m3817a(Context context, String str, String str2) {
        Constructor constructor = (Constructor) f1857d.get(str);
        if (constructor == null) {
            try {
                constructor = context.getClassLoader().loadClass(str2 != null ? str2 + str : str).asSubclass(View.class).getConstructor(f1854a);
                f1857d.put(str, constructor);
            } catch (Exception e) {
                return null;
            }
        }
        constructor.setAccessible(true);
        return (View) constructor.newInstance(this.f1858e);
    }

    /* renamed from: a */
    private void m3818a(View view, AttributeSet attributeSet) {
        Context context = view.getContext();
        if (!(context instanceof ContextWrapper)) {
            return;
        }
        if (VERSION.SDK_INT < 15 || ah.m2770J(view)) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, f1855b);
            String string = obtainStyledAttributes.getString(0);
            if (string != null) {
                view.setOnClickListener(new C0802a(view, string));
            }
            obtainStyledAttributes.recycle();
        }
    }

    /* renamed from: a */
    public final View m3819a(View view, String str, Context context, AttributeSet attributeSet, boolean z, boolean z2, boolean z3, boolean z4) {
        Context context2 = (!z || view == null) ? context : view.getContext();
        if (z2 || z3) {
            context2 = C0803m.m3815a(context2, attributeSet, z2, z3);
        }
        if (z4) {
            context2 = bh.m5649a(context2);
        }
        View view2 = null;
        Object obj = -1;
        switch (str.hashCode()) {
            case -1946472170:
                if (str.equals("RatingBar")) {
                    obj = 11;
                    break;
                }
                break;
            case -1455429095:
                if (str.equals("CheckedTextView")) {
                    obj = 8;
                    break;
                }
                break;
            case -1346021293:
                if (str.equals("MultiAutoCompleteTextView")) {
                    obj = 10;
                    break;
                }
                break;
            case -938935918:
                if (str.equals("TextView")) {
                    obj = null;
                    break;
                }
                break;
            case -937446323:
                if (str.equals("ImageButton")) {
                    obj = 5;
                    break;
                }
                break;
            case -658531749:
                if (str.equals("SeekBar")) {
                    obj = 12;
                    break;
                }
                break;
            case -339785223:
                if (str.equals("Spinner")) {
                    obj = 4;
                    break;
                }
                break;
            case 776382189:
                if (str.equals("RadioButton")) {
                    obj = 7;
                    break;
                }
                break;
            case 1125864064:
                if (str.equals("ImageView")) {
                    obj = 1;
                    break;
                }
                break;
            case 1413872058:
                if (str.equals("AutoCompleteTextView")) {
                    obj = 9;
                    break;
                }
                break;
            case 1601505219:
                if (str.equals("CheckBox")) {
                    obj = 6;
                    break;
                }
                break;
            case 1666676343:
                if (str.equals("EditText")) {
                    obj = 3;
                    break;
                }
                break;
            case 2001146706:
                if (str.equals("Button")) {
                    obj = 2;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
                view2 = new C0855y(context2, attributeSet);
                break;
            case 1:
                view2 = new AppCompatImageView(context2, attributeSet);
                break;
            case 2:
                view2 = new AppCompatButton(context2, attributeSet);
                break;
            case 3:
                view2 = new C0193m(context2, attributeSet);
                break;
            case 4:
                view2 = new C1085v(context2, attributeSet);
                break;
            case 5:
                view2 = new AppCompatImageButton(context2, attributeSet);
                break;
            case 6:
                view2 = new C1062i(context2, attributeSet);
                break;
            case 7:
                view2 = new C1075r(context2, attributeSet);
                break;
            case 8:
                view2 = new C1063j(context2, attributeSet);
                break;
            case 9:
                view2 = new C0957g(context2, attributeSet);
                break;
            case 10:
                view2 = new C1071o(context2, attributeSet);
                break;
            case 11:
                view2 = new C1076s(context2, attributeSet);
                break;
            case 12:
                view2 = new C1077t(context2, attributeSet);
                break;
        }
        View a = (view2 != null || context == context2) ? view2 : m3816a(context2, str, attributeSet);
        if (a != null) {
            m3818a(a, attributeSet);
        }
        return a;
    }
}
