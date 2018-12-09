package p033b.p034a.p035a.p036a.p037a.p039b;

import android.content.Context;
import p033b.p034a.p035a.p036a.C1230c;
import p033b.p034a.p035a.p036a.p037a.p038a.C1094b;
import p033b.p034a.p035a.p036a.p037a.p038a.C1095d;

/* renamed from: b.a.a.a.a.b.q */
public class C1124q {
    /* renamed from: a */
    private final C1095d<String> f3309a = new C11231(this);
    /* renamed from: b */
    private final C1094b<String> f3310b = new C1094b();

    /* renamed from: b.a.a.a.a.b.q$1 */
    class C11231 implements C1095d<String> {
        /* renamed from: a */
        final /* synthetic */ C1124q f3308a;

        C11231(C1124q c1124q) {
            this.f3308a = c1124q;
        }

        /* renamed from: a */
        public String m6073a(Context context) {
            String installerPackageName = context.getPackageManager().getInstallerPackageName(context.getPackageName());
            return installerPackageName == null ? TtmlNode.ANONYMOUS_REGION_ID : installerPackageName;
        }

        /* renamed from: b */
        public /* synthetic */ Object mo1022b(Context context) {
            return m6073a(context);
        }
    }

    /* renamed from: a */
    public String m6075a(Context context) {
        try {
            String str = (String) this.f3310b.mo1017a(context, this.f3309a);
            return TtmlNode.ANONYMOUS_REGION_ID.equals(str) ? null : str;
        } catch (Throwable e) {
            C1230c.m6414h().mo1070e("Fabric", "Failed to determine installer package name", e);
            return null;
        }
    }
}
