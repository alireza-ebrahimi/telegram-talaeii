package com.google.firebase.components;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* renamed from: com.google.firebase.components.k */
public final class C1912k {
    /* renamed from: a */
    private final Context f5616a;
    /* renamed from: b */
    private final C1910b f5617b;

    /* renamed from: com.google.firebase.components.k$b */
    interface C1910b {
        /* renamed from: a */
        List<String> mo3041a(Context context);
    }

    /* renamed from: com.google.firebase.components.k$a */
    static class C1911a implements C1910b {
        private C1911a() {
        }

        /* renamed from: b */
        private static Bundle m8724b(Context context) {
            Bundle bundle = null;
            try {
                PackageManager packageManager = context.getPackageManager();
                if (packageManager == null) {
                    Log.w("ComponentDiscovery", "Context has no PackageManager.");
                } else {
                    ServiceInfo serviceInfo = packageManager.getServiceInfo(new ComponentName(context, ComponentDiscoveryService.class), 128);
                    if (serviceInfo == null) {
                        Log.w("ComponentDiscovery", "ComponentDiscoveryService has no service info.");
                    } else {
                        bundle = serviceInfo.metaData;
                    }
                }
            } catch (NameNotFoundException e) {
                Log.w("ComponentDiscovery", "Application info not found.");
            }
            return bundle;
        }

        /* renamed from: a */
        public final List<String> mo3041a(Context context) {
            Bundle b = C1911a.m8724b(context);
            if (b == null) {
                Log.w("ComponentDiscovery", "Could not retrieve metadata, returning empty list of registrars.");
                return Collections.emptyList();
            }
            List<String> arrayList = new ArrayList();
            for (String str : b.keySet()) {
                if ("com.google.firebase.components.ComponentRegistrar".equals(b.get(str)) && str.startsWith("com.google.firebase.components:")) {
                    arrayList.add(str.substring(31));
                }
            }
            return arrayList;
        }
    }

    public C1912k(Context context) {
        this(context, new C1911a());
    }

    private C1912k(Context context, C1910b c1910b) {
        this.f5616a = context;
        this.f5617b = c1910b;
    }

    /* renamed from: a */
    private static List<C1816e> m8726a(List<String> list) {
        List<C1816e> arrayList = new ArrayList();
        for (String cls : list) {
            try {
                Class cls2 = Class.forName(cls);
                if (C1816e.class.isAssignableFrom(cls2)) {
                    arrayList.add((C1816e) cls2.newInstance());
                } else {
                    Log.w("ComponentDiscovery", String.format("Class %s is not an instance of %s", new Object[]{cls, "com.google.firebase.components.ComponentRegistrar"}));
                }
            } catch (Throwable e) {
                Log.w("ComponentDiscovery", String.format("Class %s is not an found.", new Object[]{cls}), e);
            } catch (Throwable e2) {
                Log.w("ComponentDiscovery", String.format("Could not instantiate %s.", new Object[]{cls}), e2);
            } catch (Throwable e22) {
                Log.w("ComponentDiscovery", String.format("Could not instantiate %s.", new Object[]{cls}), e22);
            }
        }
        return arrayList;
    }

    /* renamed from: a */
    public final List<C1816e> m8727a() {
        return C1912k.m8726a(this.f5617b.mo3041a(this.f5616a));
    }
}
