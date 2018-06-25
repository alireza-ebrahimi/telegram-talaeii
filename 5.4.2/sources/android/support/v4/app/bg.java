package android.support.v4.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.content.C0235a;
import android.util.Log;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import java.util.ArrayList;
import java.util.Iterator;

public final class bg implements Iterable<Intent> {
    /* renamed from: a */
    private static final C0321b f1019a;
    /* renamed from: b */
    private final ArrayList<Intent> f1020b = new ArrayList();
    /* renamed from: c */
    private final Context f1021c;

    /* renamed from: android.support.v4.app.bg$a */
    public interface C0320a {
        /* renamed from: a */
        Intent mo601a();
    }

    /* renamed from: android.support.v4.app.bg$b */
    interface C0321b {
    }

    /* renamed from: android.support.v4.app.bg$c */
    static class C0322c implements C0321b {
        C0322c() {
        }
    }

    /* renamed from: android.support.v4.app.bg$d */
    static class C0323d implements C0321b {
        C0323d() {
        }
    }

    static {
        if (VERSION.SDK_INT >= 11) {
            f1019a = new C0323d();
        } else {
            f1019a = new C0322c();
        }
    }

    private bg(Context context) {
        this.f1021c = context;
    }

    /* renamed from: a */
    public static bg m1426a(Context context) {
        return new bg(context);
    }

    /* renamed from: a */
    public bg m1427a(Activity activity) {
        Intent intent = null;
        if (activity instanceof C0320a) {
            intent = ((C0320a) activity).mo601a();
        }
        Intent a = intent == null ? ag.m1188a(activity) : intent;
        if (a != null) {
            ComponentName component = a.getComponent();
            if (component == null) {
                component = a.resolveActivity(this.f1021c.getPackageManager());
            }
            m1428a(component);
            m1429a(a);
        }
        return this;
    }

    /* renamed from: a */
    public bg m1428a(ComponentName componentName) {
        int size = this.f1020b.size();
        try {
            Intent a = ag.m1189a(this.f1021c, componentName);
            while (a != null) {
                this.f1020b.add(size, a);
                a = ag.m1189a(this.f1021c, a.getComponent());
            }
            return this;
        } catch (Throwable e) {
            Log.e("TaskStackBuilder", "Bad ComponentName while traversing activity parent metadata");
            throw new IllegalArgumentException(e);
        }
    }

    /* renamed from: a */
    public bg m1429a(Intent intent) {
        this.f1020b.add(intent);
        return this;
    }

    /* renamed from: a */
    public void m1430a() {
        m1431a(null);
    }

    /* renamed from: a */
    public void m1431a(Bundle bundle) {
        if (this.f1020b.isEmpty()) {
            throw new IllegalStateException("No intents added to TaskStackBuilder; cannot startActivities");
        }
        Intent[] intentArr = (Intent[]) this.f1020b.toArray(new Intent[this.f1020b.size()]);
        intentArr[0] = new Intent(intentArr[0]).addFlags(268484608);
        if (!C0235a.m1069a(this.f1021c, intentArr, bundle)) {
            Intent intent = new Intent(intentArr[intentArr.length - 1]);
            intent.addFlags(ErrorDialogData.BINDER_CRASH);
            this.f1021c.startActivity(intent);
        }
    }

    @Deprecated
    public Iterator<Intent> iterator() {
        return this.f1020b.iterator();
    }
}
