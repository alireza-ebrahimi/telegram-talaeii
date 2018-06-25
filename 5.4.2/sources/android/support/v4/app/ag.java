package android.support.v4.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.support.v4.content.C0418i;
import android.util.Log;
import org.telegram.tgnet.ConnectionsManager;

public final class ag {
    /* renamed from: a */
    private static final C0255a f845a;

    /* renamed from: android.support.v4.app.ag$a */
    interface C0255a {
        /* renamed from: a */
        Intent mo205a(Activity activity);

        /* renamed from: a */
        String mo206a(Context context, ActivityInfo activityInfo);

        /* renamed from: a */
        boolean mo207a(Activity activity, Intent intent);

        /* renamed from: b */
        void mo208b(Activity activity, Intent intent);
    }

    /* renamed from: android.support.v4.app.ag$b */
    static class C0256b implements C0255a {
        C0256b() {
        }

        /* renamed from: a */
        public Intent mo205a(Activity activity) {
            String b = ag.m1191b(activity);
            if (b == null) {
                return null;
            }
            ComponentName componentName = new ComponentName(activity, b);
            try {
                return ag.m1192b((Context) activity, componentName) == null ? C0418i.m1885a(componentName) : new Intent().setComponent(componentName);
            } catch (NameNotFoundException e) {
                Log.e("NavUtils", "getParentActivityIntent: bad parentActivityName '" + b + "' in manifest");
                return null;
            }
        }

        /* renamed from: a */
        public String mo206a(Context context, ActivityInfo activityInfo) {
            if (activityInfo.metaData == null) {
                return null;
            }
            String string = activityInfo.metaData.getString("android.support.PARENT_ACTIVITY");
            return string == null ? null : string.charAt(0) == '.' ? context.getPackageName() + string : string;
        }

        /* renamed from: a */
        public boolean mo207a(Activity activity, Intent intent) {
            String action = activity.getIntent().getAction();
            return (action == null || action.equals("android.intent.action.MAIN")) ? false : true;
        }

        /* renamed from: b */
        public void mo208b(Activity activity, Intent intent) {
            intent.addFlags(ConnectionsManager.FileTypeFile);
            activity.startActivity(intent);
            activity.finish();
        }
    }

    /* renamed from: android.support.v4.app.ag$c */
    static class C0257c extends C0256b {
        C0257c() {
        }

        /* renamed from: a */
        public Intent mo205a(Activity activity) {
            Intent a = ah.m1194a(activity);
            return a == null ? m1186b(activity) : a;
        }

        /* renamed from: a */
        public String mo206a(Context context, ActivityInfo activityInfo) {
            String a = ah.m1195a(activityInfo);
            return a == null ? super.mo206a(context, activityInfo) : a;
        }

        /* renamed from: a */
        public boolean mo207a(Activity activity, Intent intent) {
            return ah.m1196a(activity, intent);
        }

        /* renamed from: b */
        Intent m1186b(Activity activity) {
            return super.mo205a(activity);
        }

        /* renamed from: b */
        public void mo208b(Activity activity, Intent intent) {
            ah.m1197b(activity, intent);
        }
    }

    static {
        if (VERSION.SDK_INT >= 16) {
            f845a = new C0257c();
        } else {
            f845a = new C0256b();
        }
    }

    /* renamed from: a */
    public static Intent m1188a(Activity activity) {
        return f845a.mo205a(activity);
    }

    /* renamed from: a */
    public static Intent m1189a(Context context, ComponentName componentName) {
        String b = m1192b(context, componentName);
        if (b == null) {
            return null;
        }
        ComponentName componentName2 = new ComponentName(componentName.getPackageName(), b);
        return m1192b(context, componentName2) == null ? C0418i.m1885a(componentName2) : new Intent().setComponent(componentName2);
    }

    /* renamed from: a */
    public static boolean m1190a(Activity activity, Intent intent) {
        return f845a.mo207a(activity, intent);
    }

    /* renamed from: b */
    public static String m1191b(Activity activity) {
        try {
            return m1192b((Context) activity, activity.getComponentName());
        } catch (Throwable e) {
            throw new IllegalArgumentException(e);
        }
    }

    /* renamed from: b */
    public static String m1192b(Context context, ComponentName componentName) {
        return f845a.mo206a(context, context.getPackageManager().getActivityInfo(componentName, 128));
    }

    /* renamed from: b */
    public static void m1193b(Activity activity, Intent intent) {
        f845a.mo208b(activity, intent);
    }
}
