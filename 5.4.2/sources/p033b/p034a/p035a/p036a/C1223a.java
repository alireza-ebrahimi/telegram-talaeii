package p033b.p034a.p035a.p036a;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import java.util.HashSet;
import java.util.Set;

/* renamed from: b.a.a.a.a */
public class C1223a {
    /* renamed from: a */
    private final Application f3542a;
    /* renamed from: b */
    private C1090a f3543b;

    /* renamed from: b.a.a.a.a$a */
    private static class C1090a {
        /* renamed from: a */
        private final Set<ActivityLifecycleCallbacks> f3232a = new HashSet();
        /* renamed from: b */
        private final Application f3233b;

        C1090a(Application application) {
            this.f3233b = application;
        }

        @TargetApi(14)
        /* renamed from: a */
        private void m5940a() {
            for (ActivityLifecycleCallbacks unregisterActivityLifecycleCallbacks : this.f3232a) {
                this.f3233b.unregisterActivityLifecycleCallbacks(unregisterActivityLifecycleCallbacks);
            }
        }

        @TargetApi(14)
        /* renamed from: a */
        private boolean m5943a(final C1091b c1091b) {
            if (this.f3233b == null) {
                return false;
            }
            ActivityLifecycleCallbacks c10891 = new ActivityLifecycleCallbacks(this) {
                /* renamed from: b */
                final /* synthetic */ C1090a f3231b;

                public void onActivityCreated(Activity activity, Bundle bundle) {
                    c1091b.mo1072a(activity, bundle);
                }

                public void onActivityDestroyed(Activity activity) {
                    c1091b.mo1132e(activity);
                }

                public void onActivityPaused(Activity activity) {
                    c1091b.mo1130c(activity);
                }

                public void onActivityResumed(Activity activity) {
                    c1091b.mo1073b(activity);
                }

                public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                    c1091b.mo1129b(activity, bundle);
                }

                public void onActivityStarted(Activity activity) {
                    c1091b.mo1071a(activity);
                }

                public void onActivityStopped(Activity activity) {
                    c1091b.mo1131d(activity);
                }
            };
            this.f3233b.registerActivityLifecycleCallbacks(c10891);
            this.f3232a.add(c10891);
            return true;
        }
    }

    /* renamed from: b.a.a.a.a$b */
    public static abstract class C1091b {
        /* renamed from: a */
        public void mo1071a(Activity activity) {
        }

        /* renamed from: a */
        public void mo1072a(Activity activity, Bundle bundle) {
        }

        /* renamed from: b */
        public void mo1073b(Activity activity) {
        }

        /* renamed from: b */
        public void mo1129b(Activity activity, Bundle bundle) {
        }

        /* renamed from: c */
        public void mo1130c(Activity activity) {
        }

        /* renamed from: d */
        public void mo1131d(Activity activity) {
        }

        /* renamed from: e */
        public void mo1132e(Activity activity) {
        }
    }

    public C1223a(Context context) {
        this.f3542a = (Application) context.getApplicationContext();
        if (VERSION.SDK_INT >= 14) {
            this.f3543b = new C1090a(this.f3542a);
        }
    }

    /* renamed from: a */
    public void m6369a() {
        if (this.f3543b != null) {
            this.f3543b.m5940a();
        }
    }

    /* renamed from: a */
    public boolean m6370a(C1091b c1091b) {
        return this.f3543b != null && this.f3543b.m5943a(c1091b);
    }
}
