package android.support.v4.app;

import android.os.Build.VERSION;
import android.support.v4.app.C0366y.C0340c;
import android.support.v4.app.Fragment.C0230c;
import android.support.v4.p022f.C0469e;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/* renamed from: android.support.v4.app.l */
final class C0341l extends aa implements C0340c {
    /* renamed from: a */
    static final boolean f1037a = (VERSION.SDK_INT >= 21);
    /* renamed from: b */
    final C0366y f1038b;
    /* renamed from: c */
    ArrayList<C0339a> f1039c = new ArrayList();
    /* renamed from: d */
    int f1040d;
    /* renamed from: e */
    int f1041e;
    /* renamed from: f */
    int f1042f;
    /* renamed from: g */
    int f1043g;
    /* renamed from: h */
    int f1044h;
    /* renamed from: i */
    int f1045i;
    /* renamed from: j */
    boolean f1046j;
    /* renamed from: k */
    boolean f1047k = true;
    /* renamed from: l */
    String f1048l;
    /* renamed from: m */
    boolean f1049m;
    /* renamed from: n */
    int f1050n = -1;
    /* renamed from: o */
    int f1051o;
    /* renamed from: p */
    CharSequence f1052p;
    /* renamed from: q */
    int f1053q;
    /* renamed from: r */
    CharSequence f1054r;
    /* renamed from: s */
    ArrayList<String> f1055s;
    /* renamed from: t */
    ArrayList<String> f1056t;
    /* renamed from: u */
    boolean f1057u = false;

    /* renamed from: android.support.v4.app.l$a */
    static final class C0339a {
        /* renamed from: a */
        int f1031a;
        /* renamed from: b */
        Fragment f1032b;
        /* renamed from: c */
        int f1033c;
        /* renamed from: d */
        int f1034d;
        /* renamed from: e */
        int f1035e;
        /* renamed from: f */
        int f1036f;

        C0339a() {
        }
    }

    public C0341l(C0366y c0366y) {
        this.f1038b = c0366y;
    }

    /* renamed from: a */
    private void m1459a(int i, Fragment fragment, String str, int i2) {
        Class cls = fragment.getClass();
        int modifiers = cls.getModifiers();
        if (cls.isAnonymousClass() || !Modifier.isPublic(modifiers) || (cls.isMemberClass() && !Modifier.isStatic(modifiers))) {
            throw new IllegalStateException("Fragment " + cls.getCanonicalName() + " must be a public static class to be  properly recreated from" + " instance state.");
        }
        fragment.mFragmentManager = this.f1038b;
        if (str != null) {
            if (fragment.mTag == null || str.equals(fragment.mTag)) {
                fragment.mTag = str;
            } else {
                throw new IllegalStateException("Can't change tag of fragment " + fragment + ": was " + fragment.mTag + " now " + str);
            }
        }
        if (i != 0) {
            if (i == -1) {
                throw new IllegalArgumentException("Can't add fragment " + fragment + " with tag " + str + " to container view with no id");
            } else if (fragment.mFragmentId == 0 || fragment.mFragmentId == i) {
                fragment.mFragmentId = i;
                fragment.mContainerId = i;
            } else {
                throw new IllegalStateException("Can't change container ID of fragment " + fragment + ": was " + fragment.mFragmentId + " now " + i);
            }
        }
        C0339a c0339a = new C0339a();
        c0339a.f1031a = i2;
        c0339a.f1032b = fragment;
        m1469a(c0339a);
    }

    /* renamed from: b */
    private static boolean m1460b(C0339a c0339a) {
        Fragment fragment = c0339a.f1032b;
        return (!fragment.mAdded || fragment.mView == null || fragment.mDetached || fragment.mHidden || !fragment.isPostponed()) ? false : true;
    }

    /* renamed from: a */
    public int mo244a() {
        return m1462a(false);
    }

    /* renamed from: a */
    int m1462a(boolean z) {
        if (this.f1049m) {
            throw new IllegalStateException("commit already called");
        }
        if (C0366y.f1110a) {
            Log.v("FragmentManager", "Commit: " + this);
            PrintWriter printWriter = new PrintWriter(new C0469e("FragmentManager"));
            m1470a("  ", null, printWriter, null);
            printWriter.close();
        }
        this.f1049m = true;
        if (this.f1046j) {
            this.f1050n = this.f1038b.m1638a(this);
        } else {
            this.f1050n = -1;
        }
        this.f1038b.m1657a((C0340c) this, z);
        return this.f1050n;
    }

    /* renamed from: a */
    public aa mo245a(int i, Fragment fragment) {
        return m1464a(i, fragment, null);
    }

    /* renamed from: a */
    public aa m1464a(int i, Fragment fragment, String str) {
        if (i == 0) {
            throw new IllegalArgumentException("Must use non-zero containerViewId");
        }
        m1459a(i, fragment, str, 2);
        return this;
    }

    /* renamed from: a */
    public aa mo246a(Fragment fragment) {
        C0339a c0339a = new C0339a();
        c0339a.f1031a = 3;
        c0339a.f1032b = fragment;
        m1469a(c0339a);
        return this;
    }

    /* renamed from: a */
    public aa mo247a(Fragment fragment, String str) {
        m1459a(0, fragment, str, 1);
        return this;
    }

    /* renamed from: a */
    void m1467a(int i) {
        if (this.f1046j) {
            if (C0366y.f1110a) {
                Log.v("FragmentManager", "Bump nesting in " + this + " by " + i);
            }
            int size = this.f1039c.size();
            for (int i2 = 0; i2 < size; i2++) {
                C0339a c0339a = (C0339a) this.f1039c.get(i2);
                if (c0339a.f1032b != null) {
                    Fragment fragment = c0339a.f1032b;
                    fragment.mBackStackNesting += i;
                    if (C0366y.f1110a) {
                        Log.v("FragmentManager", "Bump nesting of " + c0339a.f1032b + " to " + c0339a.f1032b.mBackStackNesting);
                    }
                }
            }
        }
    }

    /* renamed from: a */
    void m1468a(C0230c c0230c) {
        for (int i = 0; i < this.f1039c.size(); i++) {
            C0339a c0339a = (C0339a) this.f1039c.get(i);
            if (C0341l.m1460b(c0339a)) {
                c0339a.f1032b.setOnStartEnterTransitionListener(c0230c);
            }
        }
    }

    /* renamed from: a */
    void m1469a(C0339a c0339a) {
        this.f1039c.add(c0339a);
        c0339a.f1033c = this.f1040d;
        c0339a.f1034d = this.f1041e;
        c0339a.f1035e = this.f1042f;
        c0339a.f1036f = this.f1043g;
    }

    /* renamed from: a */
    public void m1470a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        m1471a(str, printWriter, true);
    }

    /* renamed from: a */
    public void m1471a(String str, PrintWriter printWriter, boolean z) {
        if (z) {
            printWriter.print(str);
            printWriter.print("mName=");
            printWriter.print(this.f1048l);
            printWriter.print(" mIndex=");
            printWriter.print(this.f1050n);
            printWriter.print(" mCommitted=");
            printWriter.println(this.f1049m);
            if (this.f1044h != 0) {
                printWriter.print(str);
                printWriter.print("mTransition=#");
                printWriter.print(Integer.toHexString(this.f1044h));
                printWriter.print(" mTransitionStyle=#");
                printWriter.println(Integer.toHexString(this.f1045i));
            }
            if (!(this.f1040d == 0 && this.f1041e == 0)) {
                printWriter.print(str);
                printWriter.print("mEnterAnim=#");
                printWriter.print(Integer.toHexString(this.f1040d));
                printWriter.print(" mExitAnim=#");
                printWriter.println(Integer.toHexString(this.f1041e));
            }
            if (!(this.f1042f == 0 && this.f1043g == 0)) {
                printWriter.print(str);
                printWriter.print("mPopEnterAnim=#");
                printWriter.print(Integer.toHexString(this.f1042f));
                printWriter.print(" mPopExitAnim=#");
                printWriter.println(Integer.toHexString(this.f1043g));
            }
            if (!(this.f1051o == 0 && this.f1052p == null)) {
                printWriter.print(str);
                printWriter.print("mBreadCrumbTitleRes=#");
                printWriter.print(Integer.toHexString(this.f1051o));
                printWriter.print(" mBreadCrumbTitleText=");
                printWriter.println(this.f1052p);
            }
            if (!(this.f1053q == 0 && this.f1054r == null)) {
                printWriter.print(str);
                printWriter.print("mBreadCrumbShortTitleRes=#");
                printWriter.print(Integer.toHexString(this.f1053q));
                printWriter.print(" mBreadCrumbShortTitleText=");
                printWriter.println(this.f1054r);
            }
        }
        if (!this.f1039c.isEmpty()) {
            printWriter.print(str);
            printWriter.println("Operations:");
            str + "    ";
            int size = this.f1039c.size();
            for (int i = 0; i < size; i++) {
                String str2;
                C0339a c0339a = (C0339a) this.f1039c.get(i);
                switch (c0339a.f1031a) {
                    case 0:
                        str2 = "NULL";
                        break;
                    case 1:
                        str2 = "ADD";
                        break;
                    case 2:
                        str2 = "REPLACE";
                        break;
                    case 3:
                        str2 = "REMOVE";
                        break;
                    case 4:
                        str2 = "HIDE";
                        break;
                    case 5:
                        str2 = "SHOW";
                        break;
                    case 6:
                        str2 = "DETACH";
                        break;
                    case 7:
                        str2 = "ATTACH";
                        break;
                    default:
                        str2 = "cmd=" + c0339a.f1031a;
                        break;
                }
                printWriter.print(str);
                printWriter.print("  Op #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.print(str2);
                printWriter.print(" ");
                printWriter.println(c0339a.f1032b);
                if (z) {
                    if (!(c0339a.f1033c == 0 && c0339a.f1034d == 0)) {
                        printWriter.print(str);
                        printWriter.print("enterAnim=#");
                        printWriter.print(Integer.toHexString(c0339a.f1033c));
                        printWriter.print(" exitAnim=#");
                        printWriter.println(Integer.toHexString(c0339a.f1034d));
                    }
                    if (c0339a.f1035e != 0 || c0339a.f1036f != 0) {
                        printWriter.print(str);
                        printWriter.print("popEnterAnim=#");
                        printWriter.print(Integer.toHexString(c0339a.f1035e));
                        printWriter.print(" popExitAnim=#");
                        printWriter.println(Integer.toHexString(c0339a.f1036f));
                    }
                }
            }
        }
    }

    /* renamed from: a */
    void m1472a(ArrayList<Fragment> arrayList) {
        int i = 0;
        while (i < this.f1039c.size()) {
            C0339a c0339a = (C0339a) this.f1039c.get(i);
            switch (c0339a.f1031a) {
                case 1:
                case 7:
                    arrayList.add(c0339a.f1032b);
                    break;
                case 2:
                    Fragment fragment = c0339a.f1032b;
                    int i2 = fragment.mContainerId;
                    int size = arrayList.size() - 1;
                    int i3 = i;
                    int i4 = 0;
                    while (size >= 0) {
                        Fragment fragment2 = (Fragment) arrayList.get(size);
                        if (fragment2.mContainerId != i2) {
                            i = i4;
                            i4 = i3;
                        } else if (fragment2 == fragment) {
                            i = 1;
                            i4 = i3;
                        } else {
                            C0339a c0339a2 = new C0339a();
                            c0339a2.f1031a = 3;
                            c0339a2.f1032b = fragment2;
                            c0339a2.f1033c = c0339a.f1033c;
                            c0339a2.f1035e = c0339a.f1035e;
                            c0339a2.f1034d = c0339a.f1034d;
                            c0339a2.f1036f = c0339a.f1036f;
                            this.f1039c.add(i3, c0339a2);
                            arrayList.remove(fragment2);
                            int i5 = i4;
                            i4 = i3 + 1;
                            i = i5;
                        }
                        size--;
                        i3 = i4;
                        i4 = i;
                    }
                    if (i4 == 0) {
                        c0339a.f1031a = 1;
                        arrayList.add(fragment);
                        i = i3;
                        break;
                    }
                    this.f1039c.remove(i3);
                    i = i3 - 1;
                    break;
                case 3:
                case 6:
                    arrayList.remove(c0339a.f1032b);
                    break;
                default:
                    break;
            }
            i++;
        }
    }

    /* renamed from: a */
    boolean m1473a(ArrayList<C0341l> arrayList, int i, int i2) {
        if (i2 == i) {
            return false;
        }
        int size = this.f1039c.size();
        int i3 = -1;
        int i4 = 0;
        while (i4 < size) {
            int i5;
            int i6 = ((C0339a) this.f1039c.get(i4)).f1032b.mContainerId;
            if (i6 == 0 || i6 == i3) {
                i5 = i3;
            } else {
                for (int i7 = i; i7 < i2; i7++) {
                    C0341l c0341l = (C0341l) arrayList.get(i7);
                    int size2 = c0341l.f1039c.size();
                    for (int i8 = 0; i8 < size2; i8++) {
                        if (((C0339a) c0341l.f1039c.get(i8)).f1032b.mContainerId == i6) {
                            return true;
                        }
                    }
                }
                i5 = i6;
            }
            i4++;
            i3 = i5;
        }
        return false;
    }

    /* renamed from: a */
    public boolean mo248a(ArrayList<C0341l> arrayList, ArrayList<Boolean> arrayList2) {
        if (C0366y.f1110a) {
            Log.v("FragmentManager", "Run: " + this);
        }
        arrayList.add(this);
        arrayList2.add(Boolean.valueOf(false));
        if (this.f1046j) {
            this.f1038b.m1671b(this);
        }
        return true;
    }

    /* renamed from: b */
    public int mo249b() {
        return m1462a(true);
    }

    /* renamed from: b */
    void m1476b(ArrayList<Fragment> arrayList) {
        for (int i = 0; i < this.f1039c.size(); i++) {
            C0339a c0339a = (C0339a) this.f1039c.get(i);
            switch (c0339a.f1031a) {
                case 1:
                case 7:
                    arrayList.remove(c0339a.f1032b);
                    break;
                case 3:
                case 6:
                    arrayList.add(c0339a.f1032b);
                    break;
                default:
                    break;
            }
        }
    }

    /* renamed from: b */
    void m1477b(boolean z) {
        for (int size = this.f1039c.size() - 1; size >= 0; size--) {
            C0339a c0339a = (C0339a) this.f1039c.get(size);
            Fragment fragment = c0339a.f1032b;
            fragment.setNextTransition(C0366y.m1631d(this.f1044h), this.f1045i);
            switch (c0339a.f1031a) {
                case 1:
                    fragment.setNextAnim(c0339a.f1036f);
                    this.f1038b.m1691g(fragment);
                    break;
                case 3:
                    fragment.setNextAnim(c0339a.f1035e);
                    this.f1038b.m1655a(fragment, false);
                    break;
                case 4:
                    fragment.setNextAnim(c0339a.f1035e);
                    this.f1038b.m1697i(fragment);
                    break;
                case 5:
                    fragment.setNextAnim(c0339a.f1036f);
                    this.f1038b.m1694h(fragment);
                    break;
                case 6:
                    fragment.setNextAnim(c0339a.f1035e);
                    this.f1038b.m1701k(fragment);
                    break;
                case 7:
                    fragment.setNextAnim(c0339a.f1036f);
                    this.f1038b.m1699j(fragment);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown cmd: " + c0339a.f1031a);
            }
            if (!(this.f1057u || c0339a.f1031a == 3)) {
                this.f1038b.m1682d(fragment);
            }
        }
        if (!this.f1057u && z) {
            this.f1038b.m1646a(this.f1038b.f1127m, true);
        }
    }

    /* renamed from: b */
    boolean m1478b(int i) {
        int size = this.f1039c.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (((C0339a) this.f1039c.get(i2)).f1032b.mContainerId == i) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: c */
    void m1479c() {
        int size = this.f1039c.size();
        for (int i = 0; i < size; i++) {
            C0339a c0339a = (C0339a) this.f1039c.get(i);
            Fragment fragment = c0339a.f1032b;
            fragment.setNextTransition(this.f1044h, this.f1045i);
            switch (c0339a.f1031a) {
                case 1:
                    fragment.setNextAnim(c0339a.f1033c);
                    this.f1038b.m1655a(fragment, false);
                    break;
                case 3:
                    fragment.setNextAnim(c0339a.f1034d);
                    this.f1038b.m1691g(fragment);
                    break;
                case 4:
                    fragment.setNextAnim(c0339a.f1034d);
                    this.f1038b.m1694h(fragment);
                    break;
                case 5:
                    fragment.setNextAnim(c0339a.f1033c);
                    this.f1038b.m1697i(fragment);
                    break;
                case 6:
                    fragment.setNextAnim(c0339a.f1034d);
                    this.f1038b.m1699j(fragment);
                    break;
                case 7:
                    fragment.setNextAnim(c0339a.f1033c);
                    this.f1038b.m1701k(fragment);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown cmd: " + c0339a.f1031a);
            }
            if (!(this.f1057u || c0339a.f1031a == 1)) {
                this.f1038b.m1682d(fragment);
            }
        }
        if (!this.f1057u) {
            this.f1038b.m1646a(this.f1038b.f1127m, true);
        }
    }

    /* renamed from: d */
    boolean m1480d() {
        for (int i = 0; i < this.f1039c.size(); i++) {
            if (C0341l.m1460b((C0339a) this.f1039c.get(i))) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: e */
    public String m1481e() {
        return this.f1048l;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("BackStackEntry{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        if (this.f1050n >= 0) {
            stringBuilder.append(" #");
            stringBuilder.append(this.f1050n);
        }
        if (this.f1048l != null) {
            stringBuilder.append(" ");
            stringBuilder.append(this.f1048l);
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
