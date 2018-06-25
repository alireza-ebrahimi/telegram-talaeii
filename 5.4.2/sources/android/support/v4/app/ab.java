package android.support.v4.app;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.v4.app.C0341l.C0339a;
import android.support.v4.p022f.C0464a;
import android.support.v4.view.ah;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

class ab {
    /* renamed from: a */
    private static final int[] f802a = new int[]{0, 3, 0, 1, 5, 4, 7, 6};

    /* renamed from: android.support.v4.app.ab$a */
    static class C0241a {
        /* renamed from: a */
        public Fragment f796a;
        /* renamed from: b */
        public boolean f797b;
        /* renamed from: c */
        public C0341l f798c;
        /* renamed from: d */
        public Fragment f799d;
        /* renamed from: e */
        public boolean f800e;
        /* renamed from: f */
        public C0341l f801f;

        C0241a() {
        }
    }

    /* renamed from: a */
    private static C0241a m1087a(C0241a c0241a, SparseArray<C0241a> sparseArray, int i) {
        if (c0241a != null) {
            return c0241a;
        }
        c0241a = new C0241a();
        sparseArray.put(i, c0241a);
        return c0241a;
    }

    /* renamed from: a */
    private static C0464a<String, String> m1088a(int i, ArrayList<C0341l> arrayList, ArrayList<Boolean> arrayList2, int i2, int i3) {
        C0464a<String, String> c0464a = new C0464a();
        for (int i4 = i3 - 1; i4 >= i2; i4--) {
            C0341l c0341l = (C0341l) arrayList.get(i4);
            if (c0341l.m1478b(i)) {
                boolean booleanValue = ((Boolean) arrayList2.get(i4)).booleanValue();
                if (c0341l.f1055s != null) {
                    ArrayList arrayList3;
                    ArrayList arrayList4;
                    int size = c0341l.f1055s.size();
                    if (booleanValue) {
                        arrayList3 = c0341l.f1055s;
                        arrayList4 = c0341l.f1056t;
                    } else {
                        ArrayList arrayList5 = c0341l.f1055s;
                        arrayList3 = c0341l.f1056t;
                        arrayList4 = arrayList5;
                    }
                    for (int i5 = 0; i5 < size; i5++) {
                        String str = (String) arrayList4.get(i5);
                        String str2 = (String) arrayList3.get(i5);
                        String str3 = (String) c0464a.remove(str2);
                        if (str3 != null) {
                            c0464a.put(str, str3);
                        } else {
                            c0464a.put(str, str2);
                        }
                    }
                }
            }
        }
        return c0464a;
    }

    /* renamed from: a */
    private static Object m1091a(Fragment fragment, Fragment fragment2, boolean z) {
        if (fragment == null || fragment2 == null) {
            return null;
        }
        return ac.m1139b(ac.m1118a(z ? fragment2.getSharedElementReturnTransition() : fragment.getSharedElementEnterTransition()));
    }

    /* renamed from: a */
    private static Object m1092a(Fragment fragment, boolean z) {
        if (fragment == null) {
            return null;
        }
        return ac.m1118a(z ? fragment.getReenterTransition() : fragment.getEnterTransition());
    }

    /* renamed from: a */
    private static Object m1093a(ViewGroup viewGroup, View view, C0464a<String, String> c0464a, C0241a c0241a, ArrayList<View> arrayList, ArrayList<View> arrayList2, Object obj, Object obj2) {
        View view2 = null;
        final Fragment fragment = c0241a.f796a;
        final Fragment fragment2 = c0241a.f799d;
        if (fragment != null) {
            fragment.getView().setVisibility(0);
        }
        if (fragment == null || fragment2 == null) {
            return null;
        }
        Object obj3;
        final boolean z = c0241a.f797b;
        Object a = c0464a.isEmpty() ? null : m1091a(fragment, fragment2, z);
        C0464a b = m1108b((C0464a) c0464a, a, c0241a);
        final C0464a c = m1117c(c0464a, a, c0241a);
        if (c0464a.isEmpty()) {
            if (b != null) {
                b.clear();
            }
            if (c != null) {
                c.clear();
                obj3 = null;
            } else {
                obj3 = null;
            }
        } else {
            m1107a((ArrayList) arrayList, b, c0464a.keySet());
            m1107a((ArrayList) arrayList2, c, c0464a.values());
            obj3 = a;
        }
        if (obj == null && obj2 == null && obj3 == null) {
            return null;
        }
        Rect rect;
        m1113b(fragment, fragment2, z, b, true);
        if (obj3 != null) {
            arrayList2.add(view);
            ac.m1129a(obj3, view, (ArrayList) arrayList);
            m1105a(obj3, obj2, b, c0241a.f800e, c0241a.f801f);
            rect = new Rect();
            view2 = m1109b(c, c0241a, obj, z);
            if (view2 != null) {
                ac.m1127a(obj, rect);
            }
        } else {
            rect = null;
        }
        ax.m1389a(viewGroup, new Runnable() {
            public void run() {
                ab.m1113b(fragment, fragment2, z, c, false);
                if (view2 != null) {
                    ac.m1122a(view2, rect);
                }
            }
        });
        return obj3;
    }

    /* renamed from: a */
    private static Object m1094a(Object obj, Object obj2, Object obj3, Fragment fragment, boolean z) {
        boolean z2 = true;
        if (!(obj == null || obj2 == null || fragment == null)) {
            z2 = z ? fragment.getAllowReturnTransitionOverlap() : fragment.getAllowEnterTransitionOverlap();
        }
        return z2 ? ac.m1119a(obj2, obj, obj3) : ac.m1140b(obj2, obj, obj3);
    }

    /* renamed from: a */
    private static String m1095a(C0464a<String, String> c0464a, String str) {
        int size = c0464a.size();
        for (int i = 0; i < size; i++) {
            if (str.equals(c0464a.m1986c(i))) {
                return (String) c0464a.m1985b(i);
            }
        }
        return null;
    }

    /* renamed from: a */
    private static void m1098a(C0341l c0341l, C0339a c0339a, SparseArray<C0241a> sparseArray, boolean z, boolean z2) {
        Fragment fragment = c0339a.f1032b;
        int i = fragment.mContainerId;
        if (i != 0) {
            int i2;
            int i3;
            C0241a a;
            boolean z3;
            int i4;
            int i5;
            boolean z4;
            int i6;
            switch (z ? f802a[c0339a.f1031a] : c0339a.f1031a) {
                case 1:
                case 7:
                    z3 = z2 ? fragment.mIsNewlyAdded : (fragment.mAdded || fragment.mHidden) ? false : true;
                    i4 = 1;
                    i2 = 0;
                    i5 = 0;
                    z4 = z3;
                    break;
                case 3:
                case 6:
                    i6 = z2 ? (fragment.mAdded || fragment.mView == null || fragment.mView.getVisibility() != 0 || fragment.mPostponedAlpha < BitmapDescriptorFactory.HUE_RED) ? 0 : 1 : (!fragment.mAdded || fragment.mHidden) ? 0 : 1;
                    i4 = 0;
                    i2 = i6;
                    i5 = 1;
                    i3 = 0;
                    break;
                case 4:
                    i6 = z2 ? (fragment.mHiddenChanged && fragment.mAdded && fragment.mHidden) ? 1 : 0 : (!fragment.mAdded || fragment.mHidden) ? 0 : 1;
                    i4 = 0;
                    i2 = i6;
                    i5 = 1;
                    i3 = 0;
                    break;
                case 5:
                    z3 = z2 ? fragment.mHiddenChanged && !fragment.mHidden && fragment.mAdded : fragment.mHidden;
                    i4 = 1;
                    i2 = 0;
                    i5 = 0;
                    z4 = z3;
                    break;
                default:
                    i4 = 0;
                    i2 = 0;
                    i5 = 0;
                    i3 = 0;
                    break;
            }
            C0241a c0241a = (C0241a) sparseArray.get(i);
            if (i3 != 0) {
                a = m1087a(c0241a, (SparseArray) sparseArray, i);
                a.f796a = fragment;
                a.f797b = z;
                a.f798c = c0341l;
            } else {
                a = c0241a;
            }
            if (!(z2 || r4 == 0)) {
                if (a != null && a.f799d == fragment) {
                    a.f799d = null;
                }
                C0366y c0366y = c0341l.f1038b;
                if (fragment.mState < 1 && c0366y.f1127m >= 1 && !c0341l.f1057u) {
                    c0366y.m1684e(fragment);
                    c0366y.m1651a(fragment, 1, 0, 0, false);
                }
            }
            if (i2 == 0 || !(a == null || a.f799d == null)) {
                c0241a = a;
            } else {
                c0241a = m1087a(a, (SparseArray) sparseArray, i);
                c0241a.f799d = fragment;
                c0241a.f800e = z;
                c0241a.f801f = c0341l;
            }
            if (!z2 && r7 != 0 && c0241a != null && c0241a.f796a == fragment) {
                c0241a.f796a = null;
            }
        }
    }

    /* renamed from: a */
    public static void m1099a(C0341l c0341l, SparseArray<C0241a> sparseArray, boolean z) {
        int size = c0341l.f1039c.size();
        for (int i = 0; i < size; i++) {
            m1098a(c0341l, (C0339a) c0341l.f1039c.get(i), (SparseArray) sparseArray, false, z);
        }
    }

    /* renamed from: a */
    private static void m1100a(C0366y c0366y, int i, C0241a c0241a, View view, C0464a<String, String> c0464a) {
        ViewGroup viewGroup = null;
        if (c0366y.f1129o.mo200a()) {
            viewGroup = (ViewGroup) c0366y.f1129o.mo199a(i);
        }
        if (viewGroup != null) {
            Fragment fragment = c0241a.f796a;
            Fragment fragment2 = c0241a.f799d;
            boolean z = c0241a.f797b;
            boolean z2 = c0241a.f800e;
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            Object a = m1092a(fragment, z);
            Object b = m1110b(fragment2, z2);
            Object a2 = m1093a(viewGroup, view, (C0464a) c0464a, c0241a, arrayList2, arrayList, a, b);
            if (a != null || a2 != null || b != null) {
                ArrayList b2 = m1112b(b, fragment2, arrayList2, view);
                ArrayList b3 = m1112b(a, fragment, arrayList, view);
                m1116b(b3, 4);
                Object a3 = m1094a(a, b, a2, fragment, z);
                if (a3 != null) {
                    m1104a(b, fragment2, b2);
                    ArrayList a4 = ac.m1121a(arrayList);
                    ac.m1130a(a3, a, b3, b, b2, a2, arrayList);
                    ac.m1125a(viewGroup, a3);
                    ac.m1123a(viewGroup, arrayList2, arrayList, a4, c0464a);
                    m1116b(b3, 0);
                    ac.m1132a(a2, arrayList2, arrayList);
                }
            }
        }
    }

    /* renamed from: a */
    static void m1101a(C0366y c0366y, ArrayList<C0341l> arrayList, ArrayList<Boolean> arrayList2, int i, int i2, boolean z) {
        if (c0366y.f1127m >= 1 && VERSION.SDK_INT >= 21) {
            SparseArray sparseArray = new SparseArray();
            for (int i3 = i; i3 < i2; i3++) {
                C0341l c0341l = (C0341l) arrayList.get(i3);
                if (((Boolean) arrayList2.get(i3)).booleanValue()) {
                    m1114b(c0341l, sparseArray, z);
                } else {
                    m1099a(c0341l, sparseArray, z);
                }
            }
            if (sparseArray.size() != 0) {
                View view = new View(c0366y.f1128n.m1510i());
                int size = sparseArray.size();
                for (int i4 = 0; i4 < size; i4++) {
                    int keyAt = sparseArray.keyAt(i4);
                    C0464a a = m1088a(keyAt, (ArrayList) arrayList, (ArrayList) arrayList2, i, i2);
                    C0241a c0241a = (C0241a) sparseArray.valueAt(i4);
                    if (z) {
                        m1100a(c0366y, keyAt, c0241a, view, a);
                    } else {
                        m1115b(c0366y, keyAt, c0241a, view, a);
                    }
                }
            }
        }
    }

    /* renamed from: a */
    private static void m1102a(C0464a<String, String> c0464a, C0464a<String, View> c0464a2) {
        for (int size = c0464a.size() - 1; size >= 0; size--) {
            if (!c0464a2.containsKey((String) c0464a.m1986c(size))) {
                c0464a.m1987d(size);
            }
        }
    }

    /* renamed from: a */
    private static void m1103a(ViewGroup viewGroup, Fragment fragment, View view, ArrayList<View> arrayList, Object obj, ArrayList<View> arrayList2, Object obj2, ArrayList<View> arrayList3) {
        final Object obj3 = obj;
        final View view2 = view;
        final Fragment fragment2 = fragment;
        final ArrayList<View> arrayList4 = arrayList;
        final ArrayList<View> arrayList5 = arrayList2;
        final ArrayList<View> arrayList6 = arrayList3;
        final Object obj4 = obj2;
        ax.m1389a(viewGroup, new Runnable() {
            public void run() {
                if (obj3 != null) {
                    ac.m1145c(obj3, view2);
                    arrayList5.addAll(ab.m1112b(obj3, fragment2, arrayList4, view2));
                }
                if (arrayList6 != null) {
                    if (obj4 != null) {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(view2);
                        ac.m1144b(obj4, arrayList6, arrayList);
                    }
                    arrayList6.clear();
                    arrayList6.add(view2);
                }
            }
        });
    }

    /* renamed from: a */
    private static void m1104a(Object obj, Fragment fragment, final ArrayList<View> arrayList) {
        if (fragment != null && obj != null && fragment.mAdded && fragment.mHidden && fragment.mHiddenChanged) {
            fragment.setHideReplaced(true);
            ac.m1143b(obj, fragment.getView(), (ArrayList) arrayList);
            ax.m1389a(fragment.mContainer, new Runnable() {
                public void run() {
                    ab.m1116b(arrayList, 4);
                }
            });
        }
    }

    /* renamed from: a */
    private static void m1105a(Object obj, Object obj2, C0464a<String, View> c0464a, boolean z, C0341l c0341l) {
        if (c0341l.f1055s != null && !c0341l.f1055s.isEmpty()) {
            Object obj3;
            if (z) {
                obj3 = (String) c0341l.f1056t.get(0);
            } else {
                String str = (String) c0341l.f1055s.get(0);
            }
            View view = (View) c0464a.get(obj3);
            ac.m1128a(obj, view);
            if (obj2 != null) {
                ac.m1128a(obj2, view);
            }
        }
    }

    /* renamed from: a */
    private static void m1107a(ArrayList<View> arrayList, C0464a<String, View> c0464a, Collection<String> collection) {
        for (int size = c0464a.size() - 1; size >= 0; size--) {
            View view = (View) c0464a.m1986c(size);
            if (collection.contains(ah.m2832v(view))) {
                arrayList.add(view);
            }
        }
    }

    /* renamed from: b */
    private static C0464a<String, View> m1108b(C0464a<String, String> c0464a, Object obj, C0241a c0241a) {
        if (c0464a.isEmpty() || obj == null) {
            c0464a.clear();
            return null;
        }
        ArrayList arrayList;
        bd bdVar;
        Fragment fragment = c0241a.f799d;
        Map c0464a2 = new C0464a();
        ac.m1135a(c0464a2, fragment.getView());
        C0341l c0341l = c0241a.f801f;
        bd enterTransitionCallback;
        if (c0241a.f800e) {
            enterTransitionCallback = fragment.getEnterTransitionCallback();
            arrayList = c0341l.f1056t;
            bdVar = enterTransitionCallback;
        } else {
            enterTransitionCallback = fragment.getExitTransitionCallback();
            arrayList = c0341l.f1055s;
            bdVar = enterTransitionCallback;
        }
        c0464a2.m1989a(arrayList);
        if (bdVar != null) {
            bdVar.m1423a(arrayList, c0464a2);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                String str = (String) arrayList.get(size);
                View view = (View) c0464a2.get(str);
                if (view == null) {
                    c0464a.remove(str);
                } else if (!str.equals(ah.m2832v(view))) {
                    c0464a.put(ah.m2832v(view), (String) c0464a.remove(str));
                }
            }
        } else {
            c0464a.m1989a(c0464a2.keySet());
        }
        return c0464a2;
    }

    /* renamed from: b */
    private static View m1109b(C0464a<String, View> c0464a, C0241a c0241a, Object obj, boolean z) {
        C0341l c0341l = c0241a.f798c;
        if (obj == null || c0464a == null || c0341l.f1055s == null || c0341l.f1055s.isEmpty()) {
            return null;
        }
        Object obj2;
        if (z) {
            obj2 = (String) c0341l.f1055s.get(0);
        } else {
            String str = (String) c0341l.f1056t.get(0);
        }
        return (View) c0464a.get(obj2);
    }

    /* renamed from: b */
    private static Object m1110b(Fragment fragment, boolean z) {
        if (fragment == null) {
            return null;
        }
        return ac.m1118a(z ? fragment.getReturnTransition() : fragment.getExitTransition());
    }

    /* renamed from: b */
    private static Object m1111b(ViewGroup viewGroup, View view, C0464a<String, String> c0464a, C0241a c0241a, ArrayList<View> arrayList, ArrayList<View> arrayList2, Object obj, Object obj2) {
        final Fragment fragment = c0241a.f796a;
        final Fragment fragment2 = c0241a.f799d;
        if (fragment == null || fragment2 == null) {
            return null;
        }
        Object obj3;
        final boolean z = c0241a.f797b;
        Object a = c0464a.isEmpty() ? null : m1091a(fragment, fragment2, z);
        C0464a b = m1108b((C0464a) c0464a, a, c0241a);
        if (c0464a.isEmpty()) {
            obj3 = null;
        } else {
            arrayList.addAll(b.values());
            obj3 = a;
        }
        if (obj == null && obj2 == null && obj3 == null) {
            return null;
        }
        Rect rect;
        m1113b(fragment, fragment2, z, b, true);
        if (obj3 != null) {
            rect = new Rect();
            ac.m1129a(obj3, view, (ArrayList) arrayList);
            m1105a(obj3, obj2, b, c0241a.f800e, c0241a.f801f);
            if (obj != null) {
                ac.m1127a(obj, rect);
            }
        } else {
            rect = null;
        }
        final C0464a<String, String> c0464a2 = c0464a;
        final C0241a c0241a2 = c0241a;
        final ArrayList<View> arrayList3 = arrayList2;
        final View view2 = view;
        final ArrayList<View> arrayList4 = arrayList;
        final Object obj4 = obj;
        ax.m1389a(viewGroup, new Runnable() {
            public void run() {
                C0464a a = ab.m1117c(c0464a2, obj3, c0241a2);
                if (a != null) {
                    arrayList3.addAll(a.values());
                    arrayList3.add(view2);
                }
                ab.m1113b(fragment, fragment2, z, a, false);
                if (obj3 != null) {
                    ac.m1132a(obj3, arrayList4, arrayList3);
                    View a2 = ab.m1109b(a, c0241a2, obj4, z);
                    if (a2 != null) {
                        ac.m1122a(a2, rect);
                    }
                }
            }
        });
        return obj3;
    }

    /* renamed from: b */
    private static ArrayList<View> m1112b(Object obj, Fragment fragment, ArrayList<View> arrayList, View view) {
        ArrayList<View> arrayList2 = null;
        if (obj != null) {
            arrayList2 = new ArrayList();
            View view2 = fragment.getView();
            if (view2 != null) {
                ac.m1133a((ArrayList) arrayList2, view2);
            }
            if (arrayList != null) {
                arrayList2.removeAll(arrayList);
            }
            if (!arrayList2.isEmpty()) {
                arrayList2.add(view);
                ac.m1131a(obj, (ArrayList) arrayList2);
            }
        }
        return arrayList2;
    }

    /* renamed from: b */
    private static void m1113b(Fragment fragment, Fragment fragment2, boolean z, C0464a<String, View> c0464a, boolean z2) {
        int i = 0;
        bd enterTransitionCallback = z ? fragment2.getEnterTransitionCallback() : fragment.getEnterTransitionCallback();
        if (enterTransitionCallback != null) {
            List arrayList = new ArrayList();
            List arrayList2 = new ArrayList();
            int size = c0464a == null ? 0 : c0464a.size();
            while (i < size) {
                arrayList2.add(c0464a.m1985b(i));
                arrayList.add(c0464a.m1986c(i));
                i++;
            }
            if (z2) {
                enterTransitionCallback.m1422a(arrayList2, arrayList, null);
            } else {
                enterTransitionCallback.m1424b(arrayList2, arrayList, null);
            }
        }
    }

    /* renamed from: b */
    public static void m1114b(C0341l c0341l, SparseArray<C0241a> sparseArray, boolean z) {
        if (c0341l.f1038b.f1129o.mo200a()) {
            for (int size = c0341l.f1039c.size() - 1; size >= 0; size--) {
                m1098a(c0341l, (C0339a) c0341l.f1039c.get(size), (SparseArray) sparseArray, true, z);
            }
        }
    }

    /* renamed from: b */
    private static void m1115b(C0366y c0366y, int i, C0241a c0241a, View view, C0464a<String, String> c0464a) {
        ViewGroup viewGroup = null;
        if (c0366y.f1129o.mo200a()) {
            viewGroup = (ViewGroup) c0366y.f1129o.mo199a(i);
        }
        if (viewGroup != null) {
            Fragment fragment = c0241a.f796a;
            Fragment fragment2 = c0241a.f799d;
            boolean z = c0241a.f797b;
            boolean z2 = c0241a.f800e;
            Object a = m1092a(fragment, z);
            Object b = m1110b(fragment2, z2);
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            Object b2 = m1111b(viewGroup, view, c0464a, c0241a, arrayList, arrayList2, a, b);
            if (a != null || b2 != null || b != null) {
                ArrayList b3 = m1112b(b, fragment2, arrayList, view);
                Object obj = (b3 == null || b3.isEmpty()) ? null : b;
                ac.m1142b(a, view);
                b = m1094a(a, obj, b2, fragment, c0241a.f797b);
                if (b != null) {
                    ArrayList arrayList3 = new ArrayList();
                    ac.m1130a(b, a, arrayList3, obj, b3, b2, arrayList2);
                    m1103a(viewGroup, fragment, view, arrayList2, a, arrayList3, obj, b3);
                    ac.m1124a((View) viewGroup, arrayList2, (Map) c0464a);
                    ac.m1125a(viewGroup, b);
                    ac.m1126a(viewGroup, arrayList2, (Map) c0464a);
                }
            }
        }
    }

    /* renamed from: b */
    private static void m1116b(ArrayList<View> arrayList, int i) {
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ((View) arrayList.get(size)).setVisibility(i);
            }
        }
    }

    /* renamed from: c */
    private static C0464a<String, View> m1117c(C0464a<String, String> c0464a, Object obj, C0241a c0241a) {
        Fragment fragment = c0241a.f796a;
        View view = fragment.getView();
        if (c0464a.isEmpty() || obj == null || view == null) {
            c0464a.clear();
            return null;
        }
        ArrayList arrayList;
        bd bdVar;
        C0464a<String, View> c0464a2 = new C0464a();
        ac.m1135a((Map) c0464a2, view);
        C0341l c0341l = c0241a.f798c;
        bd exitTransitionCallback;
        if (c0241a.f797b) {
            exitTransitionCallback = fragment.getExitTransitionCallback();
            arrayList = c0341l.f1055s;
            bdVar = exitTransitionCallback;
        } else {
            exitTransitionCallback = fragment.getEnterTransitionCallback();
            arrayList = c0341l.f1056t;
            bdVar = exitTransitionCallback;
        }
        if (arrayList != null) {
            c0464a2.m1989a(arrayList);
        }
        if (bdVar != null) {
            bdVar.m1423a(arrayList, c0464a2);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                String str = (String) arrayList.get(size);
                view = (View) c0464a2.get(str);
                if (view == null) {
                    str = m1095a((C0464a) c0464a, str);
                    if (str != null) {
                        c0464a.remove(str);
                    }
                } else if (!str.equals(ah.m2832v(view))) {
                    str = m1095a((C0464a) c0464a, str);
                    if (str != null) {
                        c0464a.put(str, ah.m2832v(view));
                    }
                }
            }
        } else {
            m1102a((C0464a) c0464a, (C0464a) c0464a2);
        }
        return c0464a2;
    }
}
