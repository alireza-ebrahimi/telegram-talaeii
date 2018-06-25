package com.google.android.gms.internal.firebase_auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class zzcz<T extends zzco> {
    private static final Logger logger = Logger.getLogger(zzci.class.getName());
    private static String zzqv = "com.google.protobuf.BlazeGeneratedExtensionRegistryLiteLoader";

    zzcz() {
    }

    static <T extends zzco> T zza(Class<T> cls) {
        String str;
        ClassLoader classLoader = zzcz.class.getClassLoader();
        if (cls.equals(zzco.class)) {
            str = zzqv;
        } else if (cls.getPackage().equals(zzcz.class.getPackage())) {
            str = String.format("%s.BlazeGenerated%sLoader", new Object[]{cls.getPackage().getName(), cls.getSimpleName()});
        } else {
            throw new IllegalArgumentException(cls.getName());
        }
        try {
            return (zzco) cls.cast(((zzcz) Class.forName(str, true, classLoader).getConstructor(new Class[0]).newInstance(new Object[0])).zzdx());
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        } catch (Throwable e2) {
            throw new IllegalStateException(e2);
        } catch (Throwable e22) {
            throw new IllegalStateException(e22);
        } catch (Throwable e222) {
            throw new IllegalStateException(e222);
        } catch (ClassNotFoundException e3) {
            Iterator it = ServiceLoader.load(zzcz.class, classLoader).iterator();
            ArrayList arrayList = new ArrayList();
            while (it.hasNext()) {
                try {
                    arrayList.add((zzco) cls.cast(((zzcz) it.next()).zzdx()));
                } catch (Throwable e4) {
                    Logger logger = logger;
                    Level level = Level.SEVERE;
                    String str2 = "com.google.protobuf.GeneratedExtensionRegistryLoader";
                    String str3 = "load";
                    String str4 = "Unable to load ";
                    String valueOf = String.valueOf(cls.getSimpleName());
                    logger.logp(level, str2, str3, valueOf.length() != 0 ? str4.concat(valueOf) : new String(str4), e4);
                }
            }
            if (arrayList.size() == 1) {
                return (zzco) arrayList.get(0);
            }
            if (arrayList.size() == 0) {
                return null;
            }
            try {
                return (zzco) cls.getMethod("combine", new Class[]{Collection.class}).invoke(null, new Object[]{arrayList});
            } catch (Throwable e2222) {
                throw new IllegalStateException(e2222);
            } catch (Throwable e22222) {
                throw new IllegalStateException(e22222);
            } catch (Throwable e222222) {
                throw new IllegalStateException(e222222);
            }
        }
    }

    protected abstract T zzdx();
}
