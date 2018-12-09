package com.google.android.gms.internal.measurement;

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

public final class zzwr {
    private static final ConcurrentHashMap<Uri, zzwr> zzbnf = new ConcurrentHashMap();
    private static final String[] zzbnm = new String[]{"key", C1797b.VALUE};
    private final Uri uri;
    private final ContentResolver zzbng;
    private final ContentObserver zzbnh;
    private final Object zzbni = new Object();
    private volatile Map<String, String> zzbnj;
    private final Object zzbnk = new Object();
    @GuardedBy("listenersLock")
    private final List<zzwt> zzbnl = new ArrayList();

    private zzwr(ContentResolver contentResolver, Uri uri) {
        this.zzbng = contentResolver;
        this.uri = uri;
        this.zzbnh = new zzws(this, null);
    }

    public static zzwr zza(ContentResolver contentResolver, Uri uri) {
        zzwr zzwr = (zzwr) zzbnf.get(uri);
        if (zzwr != null) {
            return zzwr;
        }
        zzwr zzwr2 = new zzwr(contentResolver, uri);
        zzwr = (zzwr) zzbnf.putIfAbsent(uri, zzwr2);
        if (zzwr != null) {
            return zzwr;
        }
        zzwr2.zzbng.registerContentObserver(zzwr2.uri, false, zzwr2.zzbnh);
        return zzwr2;
    }

    private final Map<String, String> zzse() {
        Cursor query;
        try {
            Map<String, String> hashMap = new HashMap();
            query = this.zzbng.query(this.uri, zzbnm, null, null, null);
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

    private final void zzsf() {
        synchronized (this.zzbnk) {
            for (zzwt zzsg : this.zzbnl) {
                zzsg.zzsg();
            }
        }
    }

    public final Map<String, String> zzsc() {
        Map<String, String> zzse = zzwu.zzd("gms:phenotype:phenotype_flag:debug_disable_caching", false) ? zzse() : this.zzbnj;
        if (zzse == null) {
            synchronized (this.zzbni) {
                zzse = this.zzbnj;
                if (zzse == null) {
                    zzse = zzse();
                    this.zzbnj = zzse;
                }
            }
        }
        return zzse != null ? zzse : Collections.emptyMap();
    }

    public final void zzsd() {
        synchronized (this.zzbni) {
            this.zzbnj = null;
        }
    }
}
