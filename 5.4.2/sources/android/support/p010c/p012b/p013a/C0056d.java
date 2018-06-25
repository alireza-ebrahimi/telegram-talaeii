package android.support.p010c.p012b.p013a;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputContentInfo;

@TargetApi(25)
/* renamed from: android.support.c.b.a.d */
final class C0056d {

    /* renamed from: android.support.c.b.a.d$a */
    public interface C0047a {
        /* renamed from: a */
        boolean mo35a(Object obj, int i, Bundle bundle);
    }

    /* renamed from: a */
    public static InputConnection m137a(InputConnection inputConnection, final C0047a c0047a) {
        return new InputConnectionWrapper(inputConnection, false) {
            public boolean commitContent(InputContentInfo inputContentInfo, int i, Bundle bundle) {
                return c0047a.mo35a(inputContentInfo, i, bundle) ? true : super.commitContent(inputContentInfo, i, bundle);
            }
        };
    }
}
