package android.support.v4.p021e;

import java.util.Locale;

/* renamed from: android.support.v4.e.e */
public final class C0456e {
    /* renamed from: a */
    public static final C0449d f1208a = new C0454e(null, false);
    /* renamed from: b */
    public static final C0449d f1209b = new C0454e(null, true);
    /* renamed from: c */
    public static final C0449d f1210c = new C0454e(C0452b.f1204a, false);
    /* renamed from: d */
    public static final C0449d f1211d = new C0454e(C0452b.f1204a, true);
    /* renamed from: e */
    public static final C0449d f1212e = new C0454e(C0451a.f1201a, false);
    /* renamed from: f */
    public static final C0449d f1213f = C0455f.f1207a;

    /* renamed from: android.support.v4.e.e$c */
    private interface C0450c {
        /* renamed from: a */
        int mo319a(CharSequence charSequence, int i, int i2);
    }

    /* renamed from: android.support.v4.e.e$a */
    private static class C0451a implements C0450c {
        /* renamed from: a */
        public static final C0451a f1201a = new C0451a(true);
        /* renamed from: b */
        public static final C0451a f1202b = new C0451a(false);
        /* renamed from: c */
        private final boolean f1203c;

        private C0451a(boolean z) {
            this.f1203c = z;
        }

        /* renamed from: a */
        public int mo319a(CharSequence charSequence, int i, int i2) {
            int i3 = i + i2;
            int i4 = 0;
            while (i < i3) {
                switch (C0456e.m1943a(Character.getDirectionality(charSequence.charAt(i)))) {
                    case 0:
                        if (!this.f1203c) {
                            i4 = 1;
                            break;
                        }
                        return 0;
                    case 1:
                        if (this.f1203c) {
                            i4 = 1;
                            break;
                        }
                        return 1;
                    default:
                        break;
                }
                i++;
            }
            return i4 != 0 ? !this.f1203c ? 0 : 1 : 2;
        }
    }

    /* renamed from: android.support.v4.e.e$b */
    private static class C0452b implements C0450c {
        /* renamed from: a */
        public static final C0452b f1204a = new C0452b();

        private C0452b() {
        }

        /* renamed from: a */
        public int mo319a(CharSequence charSequence, int i, int i2) {
            int i3 = i + i2;
            int i4 = 2;
            while (i < i3 && i4 == 2) {
                i4 = C0456e.m1944b(Character.getDirectionality(charSequence.charAt(i)));
                i++;
            }
            return i4;
        }
    }

    /* renamed from: android.support.v4.e.e$d */
    private static abstract class C0453d implements C0449d {
        /* renamed from: a */
        private final C0450c f1205a;

        public C0453d(C0450c c0450c) {
            this.f1205a = c0450c;
        }

        /* renamed from: b */
        private boolean m1938b(CharSequence charSequence, int i, int i2) {
            switch (this.f1205a.mo319a(charSequence, i, i2)) {
                case 0:
                    return true;
                case 1:
                    return false;
                default:
                    return mo321a();
            }
        }

        /* renamed from: a */
        protected abstract boolean mo321a();

        /* renamed from: a */
        public boolean mo320a(CharSequence charSequence, int i, int i2) {
            if (charSequence != null && i >= 0 && i2 >= 0 && charSequence.length() - i2 >= i) {
                return this.f1205a == null ? mo321a() : m1938b(charSequence, i, i2);
            } else {
                throw new IllegalArgumentException();
            }
        }
    }

    /* renamed from: android.support.v4.e.e$e */
    private static class C0454e extends C0453d {
        /* renamed from: a */
        private final boolean f1206a;

        C0454e(C0450c c0450c, boolean z) {
            super(c0450c);
            this.f1206a = z;
        }

        /* renamed from: a */
        protected boolean mo321a() {
            return this.f1206a;
        }
    }

    /* renamed from: android.support.v4.e.e$f */
    private static class C0455f extends C0453d {
        /* renamed from: a */
        public static final C0455f f1207a = new C0455f();

        public C0455f() {
            super(null);
        }

        /* renamed from: a */
        protected boolean mo321a() {
            return C0459f.m1948a(Locale.getDefault()) == 1;
        }
    }

    /* renamed from: a */
    static int m1943a(int i) {
        switch (i) {
            case 0:
                return 1;
            case 1:
            case 2:
                return 0;
            default:
                return 2;
        }
    }

    /* renamed from: b */
    static int m1944b(int i) {
        switch (i) {
            case 0:
            case 14:
            case 15:
                return 1;
            case 1:
            case 2:
            case 16:
            case 17:
                return 0;
            default:
                return 2;
        }
    }
}
