package p033b.p034a.p035a.p036a.p037a.p039b;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcel;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import p033b.p034a.p035a.p036a.C1230c;

/* renamed from: b.a.a.a.a.b.e */
class C1106e implements C1101f {
    /* renamed from: a */
    private final Context f3252a;

    /* renamed from: b.a.a.a.a.b.e$a */
    private static final class C1104a implements ServiceConnection {
        /* renamed from: a */
        private boolean f3249a;
        /* renamed from: b */
        private final LinkedBlockingQueue<IBinder> f3250b;

        private C1104a() {
            this.f3249a = false;
            this.f3250b = new LinkedBlockingQueue(1);
        }

        /* renamed from: a */
        public IBinder m5981a() {
            if (this.f3249a) {
                C1230c.m6414h().mo1069e("Fabric", "getBinder already called");
            }
            this.f3249a = true;
            try {
                return (IBinder) this.f3250b.poll(200, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                return null;
            }
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                this.f3250b.put(iBinder);
            } catch (InterruptedException e) {
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            this.f3250b.clear();
        }
    }

    /* renamed from: b.a.a.a.a.b.e$b */
    private static final class C1105b implements IInterface {
        /* renamed from: a */
        private final IBinder f3251a;

        public C1105b(IBinder iBinder) {
            this.f3251a = iBinder;
        }

        /* renamed from: a */
        public String m5982a() {
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            String str = null;
            try {
                obtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                this.f3251a.transact(1, obtain, obtain2, 0);
                obtain2.readException();
                str = obtain2.readString();
            } catch (Exception e) {
                C1230c.m6414h().mo1062a("Fabric", "Could not get parcel from Google Play Service to capture AdvertisingId");
            } finally {
                obtain2.recycle();
                obtain.recycle();
            }
            return str;
        }

        public IBinder asBinder() {
            return this.f3251a;
        }

        /* renamed from: b */
        public boolean m5983b() {
            /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.ssa.SSATransform.placePhi(SSATransform.java:82)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:50)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
*/
            /*
            r7 = this;
            r0 = 1;
            r1 = 0;
            r2 = android.os.Parcel.obtain();
            r3 = android.os.Parcel.obtain();
            r4 = "com.google.android.gms.ads.identifier.internal.IAdvertisingIdService";	 Catch:{ Exception -> 0x002d, all -> 0x0043 }
            r2.writeInterfaceToken(r4);	 Catch:{ Exception -> 0x002d, all -> 0x0043 }
            r4 = 1;	 Catch:{ Exception -> 0x002d, all -> 0x0043 }
            r2.writeInt(r4);	 Catch:{ Exception -> 0x002d, all -> 0x0043 }
            r4 = r7.f3251a;	 Catch:{ Exception -> 0x002d, all -> 0x0043 }
            r5 = 2;	 Catch:{ Exception -> 0x002d, all -> 0x0043 }
            r6 = 0;	 Catch:{ Exception -> 0x002d, all -> 0x0043 }
            r4.transact(r5, r2, r3, r6);	 Catch:{ Exception -> 0x002d, all -> 0x0043 }
            r3.readException();	 Catch:{ Exception -> 0x002d, all -> 0x0043 }
            r4 = r3.readInt();	 Catch:{ Exception -> 0x002d, all -> 0x0043 }
            if (r4 == 0) goto L_0x002b;
        L_0x0024:
            r3.recycle();
            r2.recycle();
        L_0x002a:
            return r0;
        L_0x002b:
            r0 = r1;
            goto L_0x0024;
        L_0x002d:
            r0 = move-exception;
            r0 = p033b.p034a.p035a.p036a.C1230c.m6414h();	 Catch:{ Exception -> 0x002d, all -> 0x0043 }
            r4 = "Fabric";	 Catch:{ Exception -> 0x002d, all -> 0x0043 }
            r5 = "Could not get parcel from Google Play Service to capture Advertising limitAdTracking";	 Catch:{ Exception -> 0x002d, all -> 0x0043 }
            r0.mo1062a(r4, r5);	 Catch:{ Exception -> 0x002d, all -> 0x0043 }
            r3.recycle();
            r2.recycle();
            r0 = r1;
            goto L_0x002a;
        L_0x0043:
            r0 = move-exception;
            r3.recycle();
            r2.recycle();
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: b.a.a.a.a.b.e.b.b():boolean");
        }
    }

    public C1106e(Context context) {
        this.f3252a = context.getApplicationContext();
    }

    /* renamed from: a */
    public C1097b mo1021a() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            C1230c.m6414h().mo1062a("Fabric", "AdvertisingInfoServiceStrategy cannot be called on the main thread");
            return null;
        }
        try {
            this.f3252a.getPackageManager().getPackageInfo("com.android.vending", 0);
            ServiceConnection c1104a = new C1104a();
            Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
            intent.setPackage("com.google.android.gms");
            try {
                if (this.f3252a.bindService(intent, c1104a, 1)) {
                    C1105b c1105b = new C1105b(c1104a.m5981a());
                    C1097b c1097b = new C1097b(c1105b.m5982a(), c1105b.m5983b());
                    this.f3252a.unbindService(c1104a);
                    return c1097b;
                }
                C1230c.m6414h().mo1062a("Fabric", "Could not bind to Google Play Service to capture AdvertisingId");
                return null;
            } catch (Throwable e) {
                C1230c.m6414h().mo1068d("Fabric", "Exception in binding to Google Play Service to capture AdvertisingId", e);
                this.f3252a.unbindService(c1104a);
                return null;
            } catch (Throwable e2) {
                C1230c.m6414h().mo1063a("Fabric", "Could not bind to Google Play Service to capture AdvertisingId", e2);
                return null;
            }
        } catch (NameNotFoundException e3) {
            C1230c.m6414h().mo1062a("Fabric", "Unable to find Google Play Services package name");
            return null;
        } catch (Throwable e22) {
            C1230c.m6414h().mo1063a("Fabric", "Unable to determine if Google Play Services is available", e22);
            return null;
        }
    }
}
