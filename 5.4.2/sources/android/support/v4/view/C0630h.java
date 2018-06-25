package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.KeyEvent;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;

/* renamed from: android.support.v4.view.h */
public final class C0630h {
    /* renamed from: a */
    static final C0627c f1390a;

    /* renamed from: android.support.v4.view.h$c */
    interface C0627c {
        /* renamed from: a */
        boolean mo552a(int i, int i2);

        /* renamed from: a */
        boolean mo553a(KeyEvent keyEvent);

        /* renamed from: b */
        boolean mo554b(int i);
    }

    /* renamed from: android.support.v4.view.h$a */
    static class C0628a implements C0627c {
        C0628a() {
        }

        /* renamed from: a */
        private static int m3127a(int i, int i2, int i3, int i4, int i5) {
            Object obj = 1;
            Object obj2 = (i2 & i3) != 0 ? 1 : null;
            int i6 = i4 | i5;
            if ((i2 & i6) == 0) {
                obj = null;
            }
            if (obj2 == null) {
                return obj != null ? i & (i3 ^ -1) : i;
            } else {
                if (obj == null) {
                    return i & (i6 ^ -1);
                }
                throw new IllegalArgumentException("bad arguments");
            }
        }

        /* renamed from: a */
        public int mo555a(int i) {
            int i2 = (i & PsExtractor.AUDIO_STREAM) != 0 ? i | 1 : i;
            if ((i2 & 48) != 0) {
                i2 |= 2;
            }
            return i2 & 247;
        }

        /* renamed from: a */
        public boolean mo552a(int i, int i2) {
            return C0628a.m3127a(C0628a.m3127a(mo555a(i) & 247, i2, 1, 64, 128), i2, 2, 16, 32) == i2;
        }

        /* renamed from: a */
        public boolean mo553a(KeyEvent keyEvent) {
            return false;
        }

        /* renamed from: b */
        public boolean mo554b(int i) {
            return (mo555a(i) & 247) == 0;
        }
    }

    /* renamed from: android.support.v4.view.h$b */
    static class C0629b extends C0628a {
        C0629b() {
        }

        /* renamed from: a */
        public int mo555a(int i) {
            return C0631i.m3139a(i);
        }

        /* renamed from: a */
        public boolean mo552a(int i, int i2) {
            return C0631i.m3140a(i, i2);
        }

        /* renamed from: a */
        public boolean mo553a(KeyEvent keyEvent) {
            return C0631i.m3141a(keyEvent);
        }

        /* renamed from: b */
        public boolean mo554b(int i) {
            return C0631i.m3142b(i);
        }
    }

    static {
        if (VERSION.SDK_INT >= 11) {
            f1390a = new C0629b();
        } else {
            f1390a = new C0628a();
        }
    }

    /* renamed from: a */
    public static boolean m3136a(KeyEvent keyEvent) {
        return f1390a.mo554b(keyEvent.getMetaState());
    }

    /* renamed from: a */
    public static boolean m3137a(KeyEvent keyEvent, int i) {
        return f1390a.mo552a(keyEvent.getMetaState(), i);
    }

    /* renamed from: b */
    public static boolean m3138b(KeyEvent keyEvent) {
        return f1390a.mo553a(keyEvent);
    }
}
