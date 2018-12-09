package android.support.p010c.p011a;

import android.annotation.TargetApi;
import android.app.Fragment;

@TargetApi(15)
/* renamed from: android.support.c.a.d */
class C0038d {
    /* renamed from: a */
    public static void m115a(Fragment fragment, boolean z) {
        if (fragment.getFragmentManager() != null) {
            fragment.setUserVisibleHint(z);
        }
    }
}
