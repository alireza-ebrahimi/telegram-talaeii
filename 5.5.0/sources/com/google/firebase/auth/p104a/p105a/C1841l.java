package com.google.firebase.auth.p104a.p105a;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.logging.Logger;
import com.google.android.gms.dynamite.DynamiteModule;

/* renamed from: com.google.firebase.auth.a.a.l */
public final class C1841l extends GmsClient<C1843q> implements C1840k {
    /* renamed from: a */
    private static Logger f5501a = new Logger("FirebaseAuth", "FirebaseAuth:");
    /* renamed from: b */
    private final Context f5502b;
    /* renamed from: c */
    private final C1847u f5503c;

    public C1841l(Context context, Looper looper, ClientSettings clientSettings, C1847u c1847u, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 112, clientSettings, connectionCallbacks, onConnectionFailedListener);
        this.f5502b = (Context) Preconditions.checkNotNull(context);
        this.f5503c = c1847u;
    }

    /* renamed from: a */
    public final /* synthetic */ C1843q mo3019a() {
        return (C1843q) super.getService();
    }

    protected final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.firebase.auth.api.internal.IFirebaseAuthService");
        return queryLocalInterface instanceof C1843q ? (C1843q) queryLocalInterface : new C1844r(iBinder);
    }

    protected final Bundle getGetServiceRequestExtraArgs() {
        Bundle getServiceRequestExtraArgs = super.getGetServiceRequestExtraArgs();
        if (getServiceRequestExtraArgs == null) {
            getServiceRequestExtraArgs = new Bundle();
        }
        if (this.f5503c != null) {
            getServiceRequestExtraArgs.putString("com.google.firebase.auth.API_KEY", this.f5503c.m8601b());
        }
        return getServiceRequestExtraArgs;
    }

    public final int getMinApkVersion() {
        return 12451000;
    }

    protected final String getServiceDescriptor() {
        return "com.google.firebase.auth.api.internal.IFirebaseAuthService";
    }

    protected final String getStartServiceAction() {
        return "com.google.firebase.auth.api.gms.service.START";
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected final java.lang.String getStartServicePackage() {
        /*
        r4 = this;
        r2 = -1;
        r1 = 0;
        r0 = "firebear.preference";
        r0 = com.google.firebase.auth.p104a.p105a.ah.m8563a(r0);
        r3 = android.text.TextUtils.isEmpty(r0);
        if (r3 == 0) goto L_0x0012;
    L_0x000f:
        r0 = "default";
    L_0x0012:
        r3 = r0.hashCode();
        switch(r3) {
            case 103145323: goto L_0x004c;
            case 1544803905: goto L_0x0057;
            default: goto L_0x0019;
        };
    L_0x0019:
        r3 = r2;
    L_0x001a:
        switch(r3) {
            case 0: goto L_0x0020;
            case 1: goto L_0x0020;
            default: goto L_0x001d;
        };
    L_0x001d:
        r0 = "default";
    L_0x0020:
        r3 = r0.hashCode();
        switch(r3) {
            case 103145323: goto L_0x0062;
            default: goto L_0x0027;
        };
    L_0x0027:
        r0 = r2;
    L_0x0028:
        switch(r0) {
            case 0: goto L_0x006d;
            default: goto L_0x002b;
        };
    L_0x002b:
        r0 = f5501a;
        r2 = "Loading module via FirebaseOptions.";
        r3 = new java.lang.Object[r1];
        r0.m8459i(r2, r3);
        r0 = r4.f5503c;
        r0 = r0.f5468a;
        if (r0 == 0) goto L_0x007e;
    L_0x003b:
        r0 = f5501a;
        r2 = "Preparing to create service connection to fallback implementation";
        r1 = new java.lang.Object[r1];
        r0.m8459i(r2, r1);
        r0 = r4.f5502b;
        r0 = r0.getPackageName();
    L_0x004b:
        return r0;
    L_0x004c:
        r3 = "local";
        r3 = r0.equals(r3);
        if (r3 == 0) goto L_0x0019;
    L_0x0055:
        r3 = r1;
        goto L_0x001a;
    L_0x0057:
        r3 = "default";
        r3 = r0.equals(r3);
        if (r3 == 0) goto L_0x0019;
    L_0x0060:
        r3 = 1;
        goto L_0x001a;
    L_0x0062:
        r3 = "local";
        r0 = r0.equals(r3);
        if (r0 == 0) goto L_0x0027;
    L_0x006b:
        r0 = r1;
        goto L_0x0028;
    L_0x006d:
        r0 = f5501a;
        r2 = "Loading fallback module override.";
        r1 = new java.lang.Object[r1];
        r0.m8459i(r2, r1);
        r0 = r4.f5502b;
        r0 = r0.getPackageName();
        goto L_0x004b;
    L_0x007e:
        r0 = f5501a;
        r2 = "Preparing to create service connection to gms implementation";
        r1 = new java.lang.Object[r1];
        r0.m8459i(r2, r1);
        r0 = "com.google.android.gms";
        goto L_0x004b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.auth.a.a.l.getStartServicePackage():java.lang.String");
    }

    public final boolean requiresGooglePlayServices() {
        return DynamiteModule.getLocalVersion(this.f5502b, "com.google.firebase.auth") == 0;
    }
}
