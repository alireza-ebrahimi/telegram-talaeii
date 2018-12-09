package android.support.p010c.p012b.p013a;

import android.annotation.TargetApi;
import android.content.ClipDescription;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.p010c.p012b.p013a.C0056d.C0047a;
import android.support.v4.p014d.C0432c;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;

@TargetApi(13)
/* renamed from: android.support.c.b.a.c */
public final class C0054c {
    /* renamed from: a */
    public static int f117a = 1;
    /* renamed from: b */
    private static final C0049c f118b;

    /* renamed from: android.support.c.b.a.c$c */
    private interface C0049c {
        /* renamed from: a */
        InputConnection mo36a(InputConnection inputConnection, EditorInfo editorInfo, C0053d c0053d);
    }

    /* renamed from: android.support.c.b.a.c$a */
    private static final class C0050a implements C0049c {
        private C0050a() {
        }

        /* renamed from: a */
        public InputConnection mo36a(InputConnection inputConnection, EditorInfo editorInfo, final C0053d c0053d) {
            return C0056d.m137a(inputConnection, new C0047a(this) {
                /* renamed from: b */
                final /* synthetic */ C0050a f107b;

                /* renamed from: a */
                public boolean mo35a(Object obj, int i, Bundle bundle) {
                    return c0053d.onCommitContent(C0060e.m150a(obj), i, bundle);
                }
            });
        }
    }

    /* renamed from: android.support.c.b.a.c$b */
    static final class C0052b implements C0049c {
        /* renamed from: a */
        private static String f110a = "android.support.v13.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT";
        /* renamed from: b */
        private static String f111b = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_URI";
        /* renamed from: c */
        private static String f112c = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
        /* renamed from: d */
        private static String f113d = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
        /* renamed from: e */
        private static String f114e = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
        /* renamed from: f */
        private static String f115f = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
        /* renamed from: g */
        private static String f116g = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER";

        C0052b() {
        }

        /* renamed from: a */
        static boolean m134a(String str, Bundle bundle, C0053d c0053d) {
            ResultReceiver resultReceiver;
            Throwable th;
            if (!TextUtils.equals(f110a, str) || bundle == null) {
                return false;
            }
            try {
                ResultReceiver resultReceiver2 = (ResultReceiver) bundle.getParcelable(f116g);
                try {
                    boolean onCommitContent = c0053d.onCommitContent(new C0060e((Uri) bundle.getParcelable(f111b), (ClipDescription) bundle.getParcelable(f112c), (Uri) bundle.getParcelable(f113d)), bundle.getInt(f115f), (Bundle) bundle.getParcelable(f114e));
                    if (resultReceiver2 != null) {
                        resultReceiver2.send(onCommitContent ? 1 : 0, null);
                    }
                    return onCommitContent;
                } catch (Throwable th2) {
                    Throwable th3 = th2;
                    resultReceiver = resultReceiver2;
                    th = th3;
                    if (resultReceiver != null) {
                        resultReceiver.send(0, null);
                    }
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                resultReceiver = null;
                if (resultReceiver != null) {
                    resultReceiver.send(0, null);
                }
                throw th;
            }
        }

        /* renamed from: a */
        public InputConnection mo36a(InputConnection inputConnection, EditorInfo editorInfo, final C0053d c0053d) {
            return C0044a.m127a(editorInfo).length == 0 ? inputConnection : new InputConnectionWrapper(this, inputConnection, false) {
                /* renamed from: b */
                final /* synthetic */ C0052b f109b;

                public boolean performPrivateCommand(String str, Bundle bundle) {
                    return C0052b.m134a(str, bundle, c0053d) ? true : super.performPrivateCommand(str, bundle);
                }
            };
        }
    }

    /* renamed from: android.support.c.b.a.c$d */
    public interface C0053d {
        boolean onCommitContent(C0060e c0060e, int i, Bundle bundle);
    }

    static {
        if (C0432c.m1913b()) {
            f118b = new C0050a();
        } else {
            f118b = new C0052b();
        }
    }

    /* renamed from: a */
    public static InputConnection m136a(InputConnection inputConnection, EditorInfo editorInfo, C0053d c0053d) {
        if (inputConnection == null) {
            throw new IllegalArgumentException("inputConnection must be non-null");
        } else if (editorInfo == null) {
            throw new IllegalArgumentException("editorInfo must be non-null");
        } else if (c0053d != null) {
            return f118b.mo36a(inputConnection, editorInfo, c0053d);
        } else {
            throw new IllegalArgumentException("onCommitContentListener must be non-null");
        }
    }
}
