package android.support.p010c.p012b.p013a;

import android.annotation.TargetApi;
import android.content.ClipDescription;
import android.net.Uri;
import android.view.inputmethod.InputContentInfo;

@TargetApi(25)
/* renamed from: android.support.c.b.a.f */
final class C0061f {
    /* renamed from: a */
    public static Uri m155a(Object obj) {
        return ((InputContentInfo) obj).getContentUri();
    }

    /* renamed from: a */
    public static Object m156a(Uri uri, ClipDescription clipDescription, Uri uri2) {
        return new InputContentInfo(uri, clipDescription, uri2);
    }

    /* renamed from: b */
    public static ClipDescription m157b(Object obj) {
        return ((InputContentInfo) obj).getDescription();
    }

    /* renamed from: c */
    public static void m158c(Object obj) {
        ((InputContentInfo) obj).requestPermission();
    }

    /* renamed from: d */
    public static void m159d(Object obj) {
        ((InputContentInfo) obj).releasePermission();
    }
}
