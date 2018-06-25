package android.support.p010c.p012b.p013a;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.p014d.C0432c;
import android.view.inputmethod.EditorInfo;

@TargetApi(13)
/* renamed from: android.support.c.b.a.a */
public final class C0044a {
    /* renamed from: a */
    private static final String[] f104a = new String[0];
    /* renamed from: b */
    private static final C0041c f105b;

    /* renamed from: android.support.c.b.a.a$c */
    private interface C0041c {
        /* renamed from: a */
        void mo33a(EditorInfo editorInfo, String[] strArr);

        /* renamed from: a */
        String[] mo34a(EditorInfo editorInfo);
    }

    /* renamed from: android.support.c.b.a.a$a */
    private static final class C0042a implements C0041c {
        private C0042a() {
        }

        /* renamed from: a */
        public void mo33a(EditorInfo editorInfo, String[] strArr) {
            C0045b.m128a(editorInfo, strArr);
        }

        /* renamed from: a */
        public String[] mo34a(EditorInfo editorInfo) {
            String[] a = C0045b.m129a(editorInfo);
            return a != null ? a : C0044a.f104a;
        }
    }

    /* renamed from: android.support.c.b.a.a$b */
    private static final class C0043b implements C0041c {
        /* renamed from: a */
        private static String f103a = "android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES";

        private C0043b() {
        }

        /* renamed from: a */
        public void mo33a(EditorInfo editorInfo, String[] strArr) {
            if (editorInfo.extras == null) {
                editorInfo.extras = new Bundle();
            }
            editorInfo.extras.putStringArray(f103a, strArr);
        }

        /* renamed from: a */
        public String[] mo34a(EditorInfo editorInfo) {
            if (editorInfo.extras == null) {
                return C0044a.f104a;
            }
            String[] stringArray = editorInfo.extras.getStringArray(f103a);
            return stringArray == null ? C0044a.f104a : stringArray;
        }
    }

    static {
        if (C0432c.m1913b()) {
            f105b = new C0042a();
        } else {
            f105b = new C0043b();
        }
    }

    /* renamed from: a */
    public static void m125a(EditorInfo editorInfo, String[] strArr) {
        f105b.mo33a(editorInfo, strArr);
    }

    /* renamed from: a */
    public static String[] m127a(EditorInfo editorInfo) {
        return f105b.mo34a(editorInfo);
    }
}
