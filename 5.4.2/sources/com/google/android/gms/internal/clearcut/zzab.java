package com.google.android.gms.internal.clearcut;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.GuardedBy;
import android.util.Log;
import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class zzab {
    private static final ConcurrentHashMap<Uri, zzab> zzde = new ConcurrentHashMap();
    private static final String[] zzdl = new String[]{"key", C1797b.VALUE};
    private final Uri uri;
    private final ContentResolver zzdf;
    private final ContentObserver zzdg;
    private final Object zzdh = new Object();
    private volatile Map<String, String> zzdi;
    private final Object zzdj = new Object();
    @GuardedBy("listenersLock")
    private final List<zzad> zzdk = new ArrayList();

    private zzab(ContentResolver contentResolver, Uri uri) {
        this.zzdf = contentResolver;
        this.uri = uri;
        this.zzdg = new zzac(this, null);
    }

    public static zzab zza(ContentResolver contentResolver, Uri uri) {
        zzab zzab = (zzab) zzde.get(uri);
        if (zzab != null) {
            return zzab;
        }
        zzab zzab2 = new zzab(contentResolver, uri);
        zzab = (zzab) zzde.putIfAbsent(uri, zzab2);
        if (zzab != null) {
            return zzab;
        }
        zzab2.zzdf.registerContentObserver(zzab2.uri, false, zzab2.zzdg);
        return zzab2;
    }

    private final Map<String, String> zzi() {
        Cursor query;
        try {
            Map<String, String> hashMap = new HashMap();
            query = this.zzdf.query(this.uri, zzdl, null, null, null);
            if (query != null) {
                while (query.moveToNext()) {
                    hashMap.put(query.getString(0), query.getString(1));
                }
                query.close();
            }
            return hashMap;
        } catch (SecurityException e) {
            Log.e("ConfigurationContentLoader", "PhenotypeFlag unable to load ContentProvider, using default values");
            return null;
        } catch (SQLiteException e2) {
            Log.e("ConfigurationContentLoader", "PhenotypeFlag unable to load ContentProvider, using default values");
            return null;
        } catch (Throwable th) {
            query.close();
        }
    }

    private final void zzj() {
        synchronized (this.zzdj) {
            for (zzad zzk : this.zzdk) {
                zzk.zzk();
            }
        }
    }

    public final Map<String, String> zzg() {
        Map<String, String> zzi = zzae.zza("gms:phenotype:phenotype_flag:debug_disable_caching", false) ? zzi() : this.zzdi;
        if (zzi == null) {
            synchronized (this.zzdh) {
                zzi = this.zzdi;
                if (zzi == null) {
                    zzi = zzi();
                    this.zzdi = zzi;
                }
            }
        }
        return zzi != null ? zzi : Collections.emptyMap();
    }

    public final void zzh() {
        synchronized (this.zzdh) {
            this.zzdi = null;
        }
    }
}
