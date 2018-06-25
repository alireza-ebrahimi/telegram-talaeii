package com.google.android.gms.internal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;

final class zzcil extends zzcli {
    private static final String[] zzjgw = new String[]{"last_bundled_timestamp", "ALTER TABLE events ADD COLUMN last_bundled_timestamp INTEGER;", "last_sampled_complex_event_id", "ALTER TABLE events ADD COLUMN last_sampled_complex_event_id INTEGER;", "last_sampling_rate", "ALTER TABLE events ADD COLUMN last_sampling_rate INTEGER;", "last_exempt_from_sampling", "ALTER TABLE events ADD COLUMN last_exempt_from_sampling INTEGER;"};
    private static final String[] zzjgx = new String[]{"origin", "ALTER TABLE user_attributes ADD COLUMN origin TEXT;"};
    private static final String[] zzjgy = new String[]{"app_version", "ALTER TABLE apps ADD COLUMN app_version TEXT;", "app_store", "ALTER TABLE apps ADD COLUMN app_store TEXT;", "gmp_version", "ALTER TABLE apps ADD COLUMN gmp_version INTEGER;", "dev_cert_hash", "ALTER TABLE apps ADD COLUMN dev_cert_hash INTEGER;", "measurement_enabled", "ALTER TABLE apps ADD COLUMN measurement_enabled INTEGER;", "last_bundle_start_timestamp", "ALTER TABLE apps ADD COLUMN last_bundle_start_timestamp INTEGER;", "day", "ALTER TABLE apps ADD COLUMN day INTEGER;", "daily_public_events_count", "ALTER TABLE apps ADD COLUMN daily_public_events_count INTEGER;", "daily_events_count", "ALTER TABLE apps ADD COLUMN daily_events_count INTEGER;", "daily_conversions_count", "ALTER TABLE apps ADD COLUMN daily_conversions_count INTEGER;", "remote_config", "ALTER TABLE apps ADD COLUMN remote_config BLOB;", "config_fetched_time", "ALTER TABLE apps ADD COLUMN config_fetched_time INTEGER;", "failed_config_fetch_time", "ALTER TABLE apps ADD COLUMN failed_config_fetch_time INTEGER;", "app_version_int", "ALTER TABLE apps ADD COLUMN app_version_int INTEGER;", "firebase_instance_id", "ALTER TABLE apps ADD COLUMN firebase_instance_id TEXT;", "daily_error_events_count", "ALTER TABLE apps ADD COLUMN daily_error_events_count INTEGER;", "daily_realtime_events_count", "ALTER TABLE apps ADD COLUMN daily_realtime_events_count INTEGER;", "health_monitor_sample", "ALTER TABLE apps ADD COLUMN health_monitor_sample TEXT;", "android_id", "ALTER TABLE apps ADD COLUMN android_id INTEGER;", "adid_reporting_enabled", "ALTER TABLE apps ADD COLUMN adid_reporting_enabled INTEGER;"};
    private static final String[] zzjgz = new String[]{"realtime", "ALTER TABLE raw_events ADD COLUMN realtime INTEGER;"};
    private static final String[] zzjha = new String[]{"has_realtime", "ALTER TABLE queue ADD COLUMN has_realtime INTEGER;"};
    private static final String[] zzjhb = new String[]{"previous_install_count", "ALTER TABLE app2 ADD COLUMN previous_install_count INTEGER;"};
    private final zzcio zzjhc = new zzcio(this, getContext(), "google_app_measurement.db");
    private final zzcni zzjhd = new zzcni(zzxx());

    zzcil(zzckj zzckj) {
        super(zzckj);
    }

    @WorkerThread
    private final long zza(String str, String[] strArr, long j) {
        Cursor cursor = null;
        try {
            cursor = getWritableDatabase().rawQuery(str, strArr);
            if (cursor.moveToFirst()) {
                j = cursor.getLong(0);
                if (cursor != null) {
                    cursor.close();
                }
            } else if (cursor != null) {
                cursor.close();
            }
            return j;
        } catch (SQLiteException e) {
            zzayp().zzbau().zze("Database error", str, e);
            throw e;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @WorkerThread
    private final Object zza(Cursor cursor, int i) {
        int type = cursor.getType(i);
        switch (type) {
            case 0:
                zzayp().zzbau().log("Loaded invalid null value from database");
                return null;
            case 1:
                return Long.valueOf(cursor.getLong(i));
            case 2:
                return Double.valueOf(cursor.getDouble(i));
            case 3:
                return cursor.getString(i);
            case 4:
                zzayp().zzbau().log("Loaded invalid blob type value, ignoring it");
                return null;
            default:
                zzayp().zzbau().zzj("Loaded invalid unknown value type, ignoring it", Integer.valueOf(type));
                return null;
        }
    }

    @WorkerThread
    private static void zza(ContentValues contentValues, String str, Object obj) {
        zzbq.zzgv(str);
        zzbq.checkNotNull(obj);
        if (obj instanceof String) {
            contentValues.put(str, (String) obj);
        } else if (obj instanceof Long) {
            contentValues.put(str, (Long) obj);
        } else if (obj instanceof Double) {
            contentValues.put(str, (Double) obj);
        } else {
            throw new IllegalArgumentException("Invalid value type");
        }
    }

    static void zza(zzcjj zzcjj, SQLiteDatabase sQLiteDatabase) {
        if (zzcjj == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        File file = new File(sQLiteDatabase.getPath());
        if (!file.setReadable(false, false)) {
            zzcjj.zzbaw().log("Failed to turn off database read permission");
        }
        if (!file.setWritable(false, false)) {
            zzcjj.zzbaw().log("Failed to turn off database write permission");
        }
        if (!file.setReadable(true, true)) {
            zzcjj.zzbaw().log("Failed to turn on database read permission for owner");
        }
        if (!file.setWritable(true, true)) {
            zzcjj.zzbaw().log("Failed to turn on database write permission for owner");
        }
    }

    @WorkerThread
    static void zza(zzcjj zzcjj, SQLiteDatabase sQLiteDatabase, String str, String str2, String str3, String[] strArr) throws SQLiteException {
        if (zzcjj == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        if (!zza(zzcjj, sQLiteDatabase, str)) {
            sQLiteDatabase.execSQL(str2);
        }
        try {
            zza(zzcjj, sQLiteDatabase, str, str3, strArr);
        } catch (SQLiteException e) {
            zzcjj.zzbau().zzj("Failed to verify columns on table that was just created", str);
            throw e;
        }
    }

    @WorkerThread
    private static void zza(zzcjj zzcjj, SQLiteDatabase sQLiteDatabase, String str, String str2, String[] strArr) throws SQLiteException {
        int i = 0;
        if (zzcjj == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        Iterable zzb = zzb(sQLiteDatabase, str);
        String[] split = str2.split(",");
        int length = split.length;
        int i2 = 0;
        while (i2 < length) {
            String str3 = split[i2];
            if (zzb.remove(str3)) {
                i2++;
            } else {
                throw new SQLiteException(new StringBuilder((String.valueOf(str).length() + 35) + String.valueOf(str3).length()).append("Table ").append(str).append(" is missing required column: ").append(str3).toString());
            }
        }
        if (strArr != null) {
            while (i < strArr.length) {
                if (!zzb.remove(strArr[i])) {
                    sQLiteDatabase.execSQL(strArr[i + 1]);
                }
                i += 2;
            }
        }
        if (!zzb.isEmpty()) {
            zzcjj.zzbaw().zze("Table has extra columns. table, columns", str, TextUtils.join(", ", zzb));
        }
    }

    @WorkerThread
    private static boolean zza(zzcjj zzcjj, SQLiteDatabase sQLiteDatabase, String str) {
        Cursor query;
        Object e;
        Throwable th;
        Cursor cursor = null;
        if (zzcjj == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        try {
            SQLiteDatabase sQLiteDatabase2 = sQLiteDatabase;
            query = sQLiteDatabase2.query("SQLITE_MASTER", new String[]{"name"}, "name=?", new String[]{str}, null, null, null);
            try {
                boolean moveToFirst = query.moveToFirst();
                if (query == null) {
                    return moveToFirst;
                }
                query.close();
                return moveToFirst;
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    zzcjj.zzbaw().zze("Error querying for table", str, e);
                    if (query != null) {
                        query.close();
                    }
                    return false;
                } catch (Throwable th2) {
                    th = th2;
                    cursor = query;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
        } catch (SQLiteException e3) {
            e = e3;
            query = null;
            zzcjj.zzbaw().zze("Error querying for table", str, e);
            if (query != null) {
                query.close();
            }
            return false;
        } catch (Throwable th3) {
            th = th3;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    @WorkerThread
    private final boolean zza(String str, int i, zzcns zzcns) {
        zzyk();
        zzwj();
        zzbq.zzgv(str);
        zzbq.checkNotNull(zzcns);
        if (TextUtils.isEmpty(zzcns.zzjsy)) {
            zzayp().zzbaw().zzd("Event filter had no event name. Audience definition ignored. appId, audienceId, filterId", zzcjj.zzjs(str), Integer.valueOf(i), String.valueOf(zzcns.zzjsx));
            return false;
        }
        try {
            byte[] bArr = new byte[zzcns.zzhs()];
            zzflk zzp = zzflk.zzp(bArr, 0, bArr.length);
            zzcns.zza(zzp);
            zzp.zzcyx();
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", str);
            contentValues.put("audience_id", Integer.valueOf(i));
            contentValues.put("filter_id", zzcns.zzjsx);
            contentValues.put("event_name", zzcns.zzjsy);
            contentValues.put("data", bArr);
            try {
                if (getWritableDatabase().insertWithOnConflict("event_filters", null, contentValues, 5) == -1) {
                    zzayp().zzbau().zzj("Failed to insert event filter (got -1). appId", zzcjj.zzjs(str));
                }
                return true;
            } catch (SQLiteException e) {
                zzayp().zzbau().zze("Error storing event filter. appId", zzcjj.zzjs(str), e);
                return false;
            }
        } catch (IOException e2) {
            zzayp().zzbau().zze("Configuration loss. Failed to serialize event filter. appId", zzcjj.zzjs(str), e2);
            return false;
        }
    }

    @WorkerThread
    private final boolean zza(String str, int i, zzcnv zzcnv) {
        zzyk();
        zzwj();
        zzbq.zzgv(str);
        zzbq.checkNotNull(zzcnv);
        if (TextUtils.isEmpty(zzcnv.zzjtn)) {
            zzayp().zzbaw().zzd("Property filter had no property name. Audience definition ignored. appId, audienceId, filterId", zzcjj.zzjs(str), Integer.valueOf(i), String.valueOf(zzcnv.zzjsx));
            return false;
        }
        try {
            byte[] bArr = new byte[zzcnv.zzhs()];
            zzflk zzp = zzflk.zzp(bArr, 0, bArr.length);
            zzcnv.zza(zzp);
            zzp.zzcyx();
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", str);
            contentValues.put("audience_id", Integer.valueOf(i));
            contentValues.put("filter_id", zzcnv.zzjsx);
            contentValues.put("property_name", zzcnv.zzjtn);
            contentValues.put("data", bArr);
            try {
                if (getWritableDatabase().insertWithOnConflict("property_filters", null, contentValues, 5) != -1) {
                    return true;
                }
                zzayp().zzbau().zzj("Failed to insert property filter (got -1). appId", zzcjj.zzjs(str));
                return false;
            } catch (SQLiteException e) {
                zzayp().zzbau().zze("Error storing property filter. appId", zzcjj.zzjs(str), e);
                return false;
            }
        } catch (IOException e2) {
            zzayp().zzbau().zze("Configuration loss. Failed to serialize property filter. appId", zzcjj.zzjs(str), e2);
            return false;
        }
    }

    @WorkerThread
    private static Set<String> zzb(SQLiteDatabase sQLiteDatabase, String str) {
        Set<String> hashSet = new HashSet();
        Cursor rawQuery = sQLiteDatabase.rawQuery(new StringBuilder(String.valueOf(str).length() + 22).append("SELECT * FROM ").append(str).append(" LIMIT 0").toString(), null);
        try {
            Collections.addAll(hashSet, rawQuery.getColumnNames());
            return hashSet;
        } finally {
            rawQuery.close();
        }
    }

    private final boolean zzbae() {
        return getContext().getDatabasePath("google_app_measurement.db").exists();
    }

    @WorkerThread
    private final long zzc(String str, String[] strArr) {
        Cursor cursor = null;
        try {
            cursor = getWritableDatabase().rawQuery(str, strArr);
            if (cursor.moveToFirst()) {
                long j = cursor.getLong(0);
                if (cursor != null) {
                    cursor.close();
                }
                return j;
            }
            throw new SQLiteException("Database returned empty set");
        } catch (SQLiteException e) {
            zzayp().zzbau().zze("Database error", str, e);
            throw e;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private final boolean zze(String str, List<Integer> list) {
        zzbq.zzgv(str);
        zzyk();
        zzwj();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            if (zzc("select count(1) from audience_filter_values where app_id=?", new String[]{str}) <= ((long) Math.max(0, Math.min(2000, zzayr().zzb(str, zzciz.zzjjs))))) {
                return false;
            }
            Iterable arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                Integer num = (Integer) list.get(i);
                if (num == null || !(num instanceof Integer)) {
                    return false;
                }
                arrayList.add(Integer.toString(num.intValue()));
            }
            String join = TextUtils.join(",", arrayList);
            join = new StringBuilder(String.valueOf(join).length() + 2).append("(").append(join).append(")").toString();
            return writableDatabase.delete("audience_filter_values", new StringBuilder(String.valueOf(join).length() + 140).append("audience_id in (select audience_id from audience_filter_values where app_id=? and audience_id not in ").append(join).append(" order by rowid desc limit -1 offset ?)").toString(), new String[]{str, Integer.toString(r5)}) > 0;
        } catch (SQLiteException e) {
            zzayp().zzbau().zze("Database error querying filters. appId", zzcjj.zzjs(str), e);
            return false;
        }
    }

    @WorkerThread
    public final void beginTransaction() {
        zzyk();
        getWritableDatabase().beginTransaction();
    }

    @WorkerThread
    public final void endTransaction() {
        zzyk();
        getWritableDatabase().endTransaction();
    }

    @WorkerThread
    final SQLiteDatabase getWritableDatabase() {
        zzwj();
        try {
            return this.zzjhc.getWritableDatabase();
        } catch (SQLiteException e) {
            zzayp().zzbaw().zzj("Error opening database", e);
            throw e;
        }
    }

    @WorkerThread
    public final void setTransactionSuccessful() {
        zzyk();
        getWritableDatabase().setTransactionSuccessful();
    }

    public final long zza(zzcoe zzcoe) throws IOException {
        zzwj();
        zzyk();
        zzbq.checkNotNull(zzcoe);
        zzbq.zzgv(zzcoe.zzcm);
        try {
            long j;
            Object obj = new byte[zzcoe.zzhs()];
            zzflk zzp = zzflk.zzp(obj, 0, obj.length);
            zzcoe.zza(zzp);
            zzp.zzcyx();
            zzclh zzayl = zzayl();
            zzbq.checkNotNull(obj);
            zzayl.zzwj();
            MessageDigest zzeq = zzcno.zzeq("MD5");
            if (zzeq == null) {
                zzayl.zzayp().zzbau().log("Failed to get MD5");
                j = 0;
            } else {
                j = zzcno.zzt(zzeq.digest(obj));
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", zzcoe.zzcm);
            contentValues.put("metadata_fingerprint", Long.valueOf(j));
            contentValues.put(TtmlNode.TAG_METADATA, obj);
            try {
                getWritableDatabase().insertWithOnConflict("raw_events_metadata", null, contentValues, 4);
                return j;
            } catch (SQLiteException e) {
                zzayp().zzbau().zze("Error storing raw event metadata. appId", zzcjj.zzjs(zzcoe.zzcm), e);
                throw e;
            }
        } catch (IOException e2) {
            zzayp().zzbau().zze("Data loss. Failed to serialize event metadata. appId", zzcjj.zzjs(zzcoe.zzcm), e2);
            throw e2;
        }
    }

    @WorkerThread
    public final zzcim zza(long j, String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        Cursor query;
        Object e;
        Throwable th;
        zzbq.zzgv(str);
        zzwj();
        zzyk();
        String[] strArr = new String[]{str};
        zzcim zzcim = new zzcim();
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            query = writableDatabase.query("apps", new String[]{"day", "daily_events_count", "daily_public_events_count", "daily_conversions_count", "daily_error_events_count", "daily_realtime_events_count"}, "app_id=?", new String[]{str}, null, null, null);
            try {
                if (query.moveToFirst()) {
                    if (query.getLong(0) == j) {
                        zzcim.zzjhf = query.getLong(1);
                        zzcim.zzjhe = query.getLong(2);
                        zzcim.zzjhg = query.getLong(3);
                        zzcim.zzjhh = query.getLong(4);
                        zzcim.zzjhi = query.getLong(5);
                    }
                    if (z) {
                        zzcim.zzjhf++;
                    }
                    if (z2) {
                        zzcim.zzjhe++;
                    }
                    if (z3) {
                        zzcim.zzjhg++;
                    }
                    if (z4) {
                        zzcim.zzjhh++;
                    }
                    if (z5) {
                        zzcim.zzjhi++;
                    }
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("day", Long.valueOf(j));
                    contentValues.put("daily_public_events_count", Long.valueOf(zzcim.zzjhe));
                    contentValues.put("daily_events_count", Long.valueOf(zzcim.zzjhf));
                    contentValues.put("daily_conversions_count", Long.valueOf(zzcim.zzjhg));
                    contentValues.put("daily_error_events_count", Long.valueOf(zzcim.zzjhh));
                    contentValues.put("daily_realtime_events_count", Long.valueOf(zzcim.zzjhi));
                    writableDatabase.update("apps", contentValues, "app_id=?", strArr);
                    if (query != null) {
                        query.close();
                    }
                    return zzcim;
                }
                zzayp().zzbaw().zzj("Not updating daily counts, app is not known. appId", zzcjj.zzjs(str));
                if (query != null) {
                    query.close();
                }
                return zzcim;
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    zzayp().zzbau().zze("Error updating daily counts. appId", zzcjj.zzjs(str), e);
                    if (query != null) {
                        query.close();
                    }
                    return zzcim;
                } catch (Throwable th2) {
                    th = th2;
                    if (query != null) {
                        query.close();
                    }
                    throw th;
                }
            }
        } catch (SQLiteException e3) {
            e = e3;
            query = null;
            zzayp().zzbau().zze("Error updating daily counts. appId", zzcjj.zzjs(str), e);
            if (query != null) {
                query.close();
            }
            return zzcim;
        } catch (Throwable th3) {
            th = th3;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
    }

    @WorkerThread
    public final void zza(zzcie zzcie) {
        zzbq.checkNotNull(zzcie);
        zzwj();
        zzyk();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzcie.getAppId());
        contentValues.put("app_instance_id", zzcie.getAppInstanceId());
        contentValues.put("gmp_app_id", zzcie.getGmpAppId());
        contentValues.put("resettable_device_id_hash", zzcie.zzayt());
        contentValues.put("last_bundle_index", Long.valueOf(zzcie.zzazc()));
        contentValues.put("last_bundle_start_timestamp", Long.valueOf(zzcie.zzayv()));
        contentValues.put("last_bundle_end_timestamp", Long.valueOf(zzcie.zzayw()));
        contentValues.put("app_version", zzcie.zzwo());
        contentValues.put("app_store", zzcie.zzayy());
        contentValues.put("gmp_version", Long.valueOf(zzcie.zzayz()));
        contentValues.put("dev_cert_hash", Long.valueOf(zzcie.zzaza()));
        contentValues.put("measurement_enabled", Boolean.valueOf(zzcie.zzazb()));
        contentValues.put("day", Long.valueOf(zzcie.zzazg()));
        contentValues.put("daily_public_events_count", Long.valueOf(zzcie.zzazh()));
        contentValues.put("daily_events_count", Long.valueOf(zzcie.zzazi()));
        contentValues.put("daily_conversions_count", Long.valueOf(zzcie.zzazj()));
        contentValues.put("config_fetched_time", Long.valueOf(zzcie.zzazd()));
        contentValues.put("failed_config_fetch_time", Long.valueOf(zzcie.zzaze()));
        contentValues.put("app_version_int", Long.valueOf(zzcie.zzayx()));
        contentValues.put("firebase_instance_id", zzcie.zzayu());
        contentValues.put("daily_error_events_count", Long.valueOf(zzcie.zzazl()));
        contentValues.put("daily_realtime_events_count", Long.valueOf(zzcie.zzazk()));
        contentValues.put("health_monitor_sample", zzcie.zzazm());
        contentValues.put("android_id", Long.valueOf(zzcie.zzazo()));
        contentValues.put("adid_reporting_enabled", Boolean.valueOf(zzcie.zzazp()));
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (((long) writableDatabase.update("apps", contentValues, "app_id = ?", new String[]{zzcie.getAppId()})) == 0 && writableDatabase.insertWithOnConflict("apps", null, contentValues, 5) == -1) {
                zzayp().zzbau().zzj("Failed to insert/update app (got -1). appId", zzcjj.zzjs(zzcie.getAppId()));
            }
        } catch (SQLiteException e) {
            zzayp().zzbau().zze("Error storing app. appId", zzcjj.zzjs(zzcie.getAppId()), e);
        }
    }

    @WorkerThread
    public final void zza(zzcit zzcit) {
        Long l = null;
        zzbq.checkNotNull(zzcit);
        zzwj();
        zzyk();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzcit.zzcm);
        contentValues.put("name", zzcit.name);
        contentValues.put("lifetime_count", Long.valueOf(zzcit.zzjhs));
        contentValues.put("current_bundle_count", Long.valueOf(zzcit.zzjht));
        contentValues.put("last_fire_timestamp", Long.valueOf(zzcit.zzjhu));
        contentValues.put("last_bundled_timestamp", Long.valueOf(zzcit.zzjhv));
        contentValues.put("last_sampled_complex_event_id", zzcit.zzjhw);
        contentValues.put("last_sampling_rate", zzcit.zzjhx);
        if (zzcit.zzjhy != null && zzcit.zzjhy.booleanValue()) {
            l = Long.valueOf(1);
        }
        contentValues.put("last_exempt_from_sampling", l);
        try {
            if (getWritableDatabase().insertWithOnConflict("events", null, contentValues, 5) == -1) {
                zzayp().zzbau().zzj("Failed to insert/update event aggregates (got -1). appId", zzcjj.zzjs(zzcit.zzcm));
            }
        } catch (SQLiteException e) {
            zzayp().zzbau().zze("Error storing event aggregates. appId", zzcjj.zzjs(zzcit.zzcm), e);
        }
    }

    @WorkerThread
    final void zza(String str, zzcnr[] zzcnrArr) {
        int i = 0;
        zzyk();
        zzwj();
        zzbq.zzgv(str);
        zzbq.checkNotNull(zzcnrArr);
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            int i2;
            zzyk();
            zzwj();
            zzbq.zzgv(str);
            SQLiteDatabase writableDatabase2 = getWritableDatabase();
            writableDatabase2.delete("property_filters", "app_id=?", new String[]{str});
            writableDatabase2.delete("event_filters", "app_id=?", new String[]{str});
            for (zzcnr zzcnr : zzcnrArr) {
                zzyk();
                zzwj();
                zzbq.zzgv(str);
                zzbq.checkNotNull(zzcnr);
                zzbq.checkNotNull(zzcnr.zzjsv);
                zzbq.checkNotNull(zzcnr.zzjsu);
                if (zzcnr.zzjst == null) {
                    zzayp().zzbaw().zzj("Audience with no ID. appId", zzcjj.zzjs(str));
                } else {
                    int intValue = zzcnr.zzjst.intValue();
                    for (zzcns zzcns : zzcnr.zzjsv) {
                        if (zzcns.zzjsx == null) {
                            zzayp().zzbaw().zze("Event filter with no ID. Audience definition ignored. appId, audienceId", zzcjj.zzjs(str), zzcnr.zzjst);
                            break;
                        }
                    }
                    for (zzcnv zzcnv : zzcnr.zzjsu) {
                        if (zzcnv.zzjsx == null) {
                            zzayp().zzbaw().zze("Property filter with no ID. Audience definition ignored. appId, audienceId", zzcjj.zzjs(str), zzcnr.zzjst);
                            break;
                        }
                    }
                    for (zzcns zzcns2 : zzcnr.zzjsv) {
                        if (!zza(str, intValue, zzcns2)) {
                            i2 = 0;
                            break;
                        }
                    }
                    i2 = 1;
                    if (i2 != 0) {
                        for (zzcnv zzcnv2 : zzcnr.zzjsu) {
                            if (!zza(str, intValue, zzcnv2)) {
                                i2 = 0;
                                break;
                            }
                        }
                    }
                    if (i2 == 0) {
                        zzyk();
                        zzwj();
                        zzbq.zzgv(str);
                        SQLiteDatabase writableDatabase3 = getWritableDatabase();
                        writableDatabase3.delete("property_filters", "app_id=? and audience_id=?", new String[]{str, String.valueOf(intValue)});
                        writableDatabase3.delete("event_filters", "app_id=? and audience_id=?", new String[]{str, String.valueOf(intValue)});
                    }
                }
            }
            List arrayList = new ArrayList();
            i2 = zzcnrArr.length;
            while (i < i2) {
                arrayList.add(zzcnrArr[i].zzjst);
                i++;
            }
            zze(str, arrayList);
            writableDatabase.setTransactionSuccessful();
        } finally {
            writableDatabase.endTransaction();
        }
    }

    @WorkerThread
    public final boolean zza(zzcii zzcii) {
        zzbq.checkNotNull(zzcii);
        zzwj();
        zzyk();
        if (zzag(zzcii.packageName, zzcii.zzjgn.name) == null) {
            if (zzc("SELECT COUNT(1) FROM conditional_properties WHERE app_id=?", new String[]{zzcii.packageName}) >= 1000) {
                return false;
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzcii.packageName);
        contentValues.put("origin", zzcii.zzjgm);
        contentValues.put("name", zzcii.zzjgn.name);
        zza(contentValues, Param.VALUE, zzcii.zzjgn.getValue());
        contentValues.put("active", Boolean.valueOf(zzcii.zzjgp));
        contentValues.put("trigger_event_name", zzcii.zzjgq);
        contentValues.put("trigger_timeout", Long.valueOf(zzcii.zzjgs));
        zzayl();
        contentValues.put("timed_out_event", zzcno.zza(zzcii.zzjgr));
        contentValues.put("creation_timestamp", Long.valueOf(zzcii.zzjgo));
        zzayl();
        contentValues.put("triggered_event", zzcno.zza(zzcii.zzjgt));
        contentValues.put("triggered_timestamp", Long.valueOf(zzcii.zzjgn.zzjsi));
        contentValues.put("time_to_live", Long.valueOf(zzcii.zzjgu));
        zzayl();
        contentValues.put("expired_event", zzcno.zza(zzcii.zzjgv));
        try {
            if (getWritableDatabase().insertWithOnConflict("conditional_properties", null, contentValues, 5) == -1) {
                zzayp().zzbau().zzj("Failed to insert/update conditional user property (got -1)", zzcjj.zzjs(zzcii.packageName));
            }
        } catch (SQLiteException e) {
            zzayp().zzbau().zze("Error storing conditional user property", zzcjj.zzjs(zzcii.packageName), e);
        }
        return true;
    }

    public final boolean zza(zzcis zzcis, long j, boolean z) {
        zzwj();
        zzyk();
        zzbq.checkNotNull(zzcis);
        zzbq.zzgv(zzcis.zzcm);
        zzfls zzcob = new zzcob();
        zzcob.zzjuk = Long.valueOf(zzcis.zzjhq);
        zzcob.zzjui = new zzcoc[zzcis.zzjhr.size()];
        Iterator it = zzcis.zzjhr.iterator();
        int i = 0;
        while (it.hasNext()) {
            String str = (String) it.next();
            zzcoc zzcoc = new zzcoc();
            int i2 = i + 1;
            zzcob.zzjui[i] = zzcoc;
            zzcoc.name = str;
            zzayl().zza(zzcoc, zzcis.zzjhr.get(str));
            i = i2;
        }
        try {
            byte[] bArr = new byte[zzcob.zzhs()];
            zzflk zzp = zzflk.zzp(bArr, 0, bArr.length);
            zzcob.zza(zzp);
            zzp.zzcyx();
            zzayp().zzbba().zze("Saving event, name, data size", zzayk().zzjp(zzcis.name), Integer.valueOf(bArr.length));
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", zzcis.zzcm);
            contentValues.put("name", zzcis.name);
            contentValues.put(AppMeasurement.Param.TIMESTAMP, Long.valueOf(zzcis.timestamp));
            contentValues.put("metadata_fingerprint", Long.valueOf(j));
            contentValues.put("data", bArr);
            contentValues.put("realtime", Integer.valueOf(z ? 1 : 0));
            try {
                if (getWritableDatabase().insert("raw_events", null, contentValues) != -1) {
                    return true;
                }
                zzayp().zzbau().zzj("Failed to insert raw event (got -1). appId", zzcjj.zzjs(zzcis.zzcm));
                return false;
            } catch (SQLiteException e) {
                zzayp().zzbau().zze("Error storing raw event. appId", zzcjj.zzjs(zzcis.zzcm), e);
                return false;
            }
        } catch (IOException e2) {
            zzayp().zzbau().zze("Data loss. Failed to serialize event params/data. appId", zzcjj.zzjs(zzcis.zzcm), e2);
            return false;
        }
    }

    @WorkerThread
    public final boolean zza(zzcnn zzcnn) {
        zzbq.checkNotNull(zzcnn);
        zzwj();
        zzyk();
        if (zzag(zzcnn.zzcm, zzcnn.name) == null) {
            if (zzcno.zzkh(zzcnn.name)) {
                if (zzc("select count(1) from user_attributes where app_id=? and name not like '!_%' escape '!'", new String[]{zzcnn.zzcm}) >= 25) {
                    return false;
                }
            }
            if (zzc("select count(1) from user_attributes where app_id=? and origin=? AND name like '!_%' escape '!'", new String[]{zzcnn.zzcm, zzcnn.zzjgm}) >= 25) {
                return false;
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzcnn.zzcm);
        contentValues.put("origin", zzcnn.zzjgm);
        contentValues.put("name", zzcnn.name);
        contentValues.put("set_timestamp", Long.valueOf(zzcnn.zzjsi));
        zza(contentValues, Param.VALUE, zzcnn.value);
        try {
            if (getWritableDatabase().insertWithOnConflict("user_attributes", null, contentValues, 5) == -1) {
                zzayp().zzbau().zzj("Failed to insert/update user property (got -1). appId", zzcjj.zzjs(zzcnn.zzcm));
            }
        } catch (SQLiteException e) {
            zzayp().zzbau().zze("Error storing user property. appId", zzcjj.zzjs(zzcnn.zzcm), e);
        }
        return true;
    }

    @WorkerThread
    public final boolean zza(zzcoe zzcoe, boolean z) {
        zzwj();
        zzyk();
        zzbq.checkNotNull(zzcoe);
        zzbq.zzgv(zzcoe.zzcm);
        zzbq.checkNotNull(zzcoe.zzjuu);
        zzazy();
        long currentTimeMillis = zzxx().currentTimeMillis();
        if (zzcoe.zzjuu.longValue() < currentTimeMillis - zzcik.zzazs() || zzcoe.zzjuu.longValue() > zzcik.zzazs() + currentTimeMillis) {
            zzayp().zzbaw().zzd("Storing bundle outside of the max uploading time span. appId, now, timestamp", zzcjj.zzjs(zzcoe.zzcm), Long.valueOf(currentTimeMillis), zzcoe.zzjuu);
        }
        try {
            byte[] bArr = new byte[zzcoe.zzhs()];
            zzflk zzp = zzflk.zzp(bArr, 0, bArr.length);
            zzcoe.zza(zzp);
            zzp.zzcyx();
            bArr = zzayl().zzr(bArr);
            zzayp().zzbba().zzj("Saving bundle, size", Integer.valueOf(bArr.length));
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", zzcoe.zzcm);
            contentValues.put("bundle_end_timestamp", zzcoe.zzjuu);
            contentValues.put("data", bArr);
            contentValues.put("has_realtime", Integer.valueOf(z ? 1 : 0));
            try {
                if (getWritableDatabase().insert("queue", null, contentValues) != -1) {
                    return true;
                }
                zzayp().zzbau().zzj("Failed to insert bundle (got -1). appId", zzcjj.zzjs(zzcoe.zzcm));
                return false;
            } catch (SQLiteException e) {
                zzayp().zzbau().zze("Error storing bundle. appId", zzcjj.zzjs(zzcoe.zzcm), e);
                return false;
            }
        } catch (IOException e2) {
            zzayp().zzbau().zze("Data loss. Failed to serialize bundle. appId", zzcjj.zzjs(zzcoe.zzcm), e2);
            return false;
        }
    }

    public final boolean zza(String str, Long l, long j, zzcob zzcob) {
        zzwj();
        zzyk();
        zzbq.checkNotNull(zzcob);
        zzbq.zzgv(str);
        zzbq.checkNotNull(l);
        try {
            byte[] bArr = new byte[zzcob.zzhs()];
            zzflk zzp = zzflk.zzp(bArr, 0, bArr.length);
            zzcob.zza(zzp);
            zzp.zzcyx();
            zzayp().zzbba().zze("Saving complex main event, appId, data size", zzayk().zzjp(str), Integer.valueOf(bArr.length));
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", str);
            contentValues.put("event_id", l);
            contentValues.put("children_to_process", Long.valueOf(j));
            contentValues.put("main_event", bArr);
            try {
                if (getWritableDatabase().insertWithOnConflict("main_event_params", null, contentValues, 5) != -1) {
                    return true;
                }
                zzayp().zzbau().zzj("Failed to insert complex main event (got -1). appId", zzcjj.zzjs(str));
                return false;
            } catch (SQLiteException e) {
                zzayp().zzbau().zze("Error storing complex main event. appId", zzcjj.zzjs(str), e);
                return false;
            }
        } catch (IOException e2) {
            zzayp().zzbau().zzd("Data loss. Failed to serialize event params/data. appId, eventId", zzcjj.zzjs(str), l, e2);
            return false;
        }
    }

    @WorkerThread
    public final zzcit zzae(String str, String str2) {
        Object e;
        Cursor cursor;
        Throwable th;
        zzbq.zzgv(str);
        zzbq.zzgv(str2);
        zzwj();
        zzyk();
        Cursor query;
        try {
            query = getWritableDatabase().query("events", new String[]{"lifetime_count", "current_bundle_count", "last_fire_timestamp", "last_bundled_timestamp", "last_sampled_complex_event_id", "last_sampling_rate", "last_exempt_from_sampling"}, "app_id=? and name=?", new String[]{str, str2}, null, null, null);
            try {
                if (query.moveToFirst()) {
                    long j = query.getLong(0);
                    long j2 = query.getLong(1);
                    long j3 = query.getLong(2);
                    long j4 = query.isNull(3) ? 0 : query.getLong(3);
                    Long valueOf = query.isNull(4) ? null : Long.valueOf(query.getLong(4));
                    Long valueOf2 = query.isNull(5) ? null : Long.valueOf(query.getLong(5));
                    Boolean bool = null;
                    if (!query.isNull(6)) {
                        bool = Boolean.valueOf(query.getLong(6) == 1);
                    }
                    zzcit zzcit = new zzcit(str, str2, j, j2, j3, j4, valueOf, valueOf2, bool);
                    if (query.moveToNext()) {
                        zzayp().zzbau().zzj("Got multiple records for event aggregates, expected one. appId", zzcjj.zzjs(str));
                    }
                    if (query == null) {
                        return zzcit;
                    }
                    query.close();
                    return zzcit;
                }
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (SQLiteException e2) {
                e = e2;
                cursor = query;
                try {
                    zzayp().zzbau().zzd("Error querying events. appId", zzcjj.zzjs(str), zzayk().zzjp(str2), e);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    query = cursor;
                    if (query != null) {
                        query.close();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                if (query != null) {
                    query.close();
                }
                throw th;
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursor = null;
            zzayp().zzbau().zzd("Error querying events. appId", zzcjj.zzjs(str), zzayk().zzjp(str2), e);
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (Throwable th4) {
            th = th4;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
    }

    @WorkerThread
    public final void zzaf(String str, String str2) {
        zzbq.zzgv(str);
        zzbq.zzgv(str2);
        zzwj();
        zzyk();
        try {
            zzayp().zzbba().zzj("Deleted user attribute rows", Integer.valueOf(getWritableDatabase().delete("user_attributes", "app_id=? and name=?", new String[]{str, str2})));
        } catch (SQLiteException e) {
            zzayp().zzbau().zzd("Error deleting user attribute. appId", zzcjj.zzjs(str), zzayk().zzjr(str2), e);
        }
    }

    @WorkerThread
    public final zzcnn zzag(String str, String str2) {
        Object e;
        Cursor cursor;
        Throwable th;
        Cursor cursor2 = null;
        zzbq.zzgv(str);
        zzbq.zzgv(str2);
        zzwj();
        zzyk();
        try {
            Cursor query = getWritableDatabase().query("user_attributes", new String[]{"set_timestamp", Param.VALUE, "origin"}, "app_id=? and name=?", new String[]{str, str2}, null, null, null);
            try {
                if (query.moveToFirst()) {
                    String str3 = str;
                    zzcnn zzcnn = new zzcnn(str3, query.getString(2), str2, query.getLong(0), zza(query, 1));
                    if (query.moveToNext()) {
                        zzayp().zzbau().zzj("Got multiple records for user property, expected one. appId", zzcjj.zzjs(str));
                    }
                    if (query == null) {
                        return zzcnn;
                    }
                    query.close();
                    return zzcnn;
                }
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (SQLiteException e2) {
                e = e2;
                cursor = query;
                try {
                    zzayp().zzbau().zzd("Error querying user property. appId", zzcjj.zzjs(str), zzayk().zzjr(str2), e);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    cursor2 = cursor;
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                cursor2 = query;
                if (cursor2 != null) {
                    cursor2.close();
                }
                throw th;
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursor = null;
            zzayp().zzbau().zzd("Error querying user property. appId", zzcjj.zzjs(str), zzayk().zzjr(str2), e);
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (Throwable th4) {
            th = th4;
            if (cursor2 != null) {
                cursor2.close();
            }
            throw th;
        }
    }

    @WorkerThread
    public final zzcii zzah(String str, String str2) {
        Object e;
        Cursor cursor;
        Throwable th;
        zzbq.zzgv(str);
        zzbq.zzgv(str2);
        zzwj();
        zzyk();
        Cursor query;
        try {
            query = getWritableDatabase().query("conditional_properties", new String[]{"origin", Param.VALUE, "active", "trigger_event_name", "trigger_timeout", "timed_out_event", "creation_timestamp", "triggered_event", "triggered_timestamp", "time_to_live", "expired_event"}, "app_id=? and name=?", new String[]{str, str2}, null, null, null);
            try {
                if (query.moveToFirst()) {
                    String string = query.getString(0);
                    Object zza = zza(query, 1);
                    boolean z = query.getInt(2) != 0;
                    String string2 = query.getString(3);
                    long j = query.getLong(4);
                    zzcix zzcix = (zzcix) zzayl().zzb(query.getBlob(5), zzcix.CREATOR);
                    long j2 = query.getLong(6);
                    zzcix zzcix2 = (zzcix) zzayl().zzb(query.getBlob(7), zzcix.CREATOR);
                    long j3 = query.getLong(8);
                    zzcii zzcii = new zzcii(str, string, new zzcnl(str2, j3, zza, string), j2, z, string2, zzcix, j, zzcix2, query.getLong(9), (zzcix) zzayl().zzb(query.getBlob(10), zzcix.CREATOR));
                    if (query.moveToNext()) {
                        zzayp().zzbau().zze("Got multiple records for conditional property, expected one", zzcjj.zzjs(str), zzayk().zzjr(str2));
                    }
                    if (query == null) {
                        return zzcii;
                    }
                    query.close();
                    return zzcii;
                }
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (SQLiteException e2) {
                e = e2;
                cursor = query;
                try {
                    zzayp().zzbau().zzd("Error querying conditional property", zzcjj.zzjs(str), zzayk().zzjr(str2), e);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    query = cursor;
                    if (query != null) {
                        query.close();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                if (query != null) {
                    query.close();
                }
                throw th;
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursor = null;
            zzayp().zzbau().zzd("Error querying conditional property", zzcjj.zzjs(str), zzayk().zzjr(str2), e);
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (Throwable th4) {
            th = th4;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
    }

    @WorkerThread
    public final int zzai(String str, String str2) {
        int i = 0;
        zzbq.zzgv(str);
        zzbq.zzgv(str2);
        zzwj();
        zzyk();
        try {
            i = getWritableDatabase().delete("conditional_properties", "app_id=? and name=?", new String[]{str, str2});
        } catch (SQLiteException e) {
            zzayp().zzbau().zzd("Error deleting conditional property", zzcjj.zzjs(str), zzayk().zzjr(str2), e);
        }
        return i;
    }

    public final void zzai(List<Long> list) {
        zzbq.checkNotNull(list);
        zzwj();
        zzyk();
        StringBuilder stringBuilder = new StringBuilder("rowid in (");
        for (int i = 0; i < list.size(); i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(((Long) list.get(i)).longValue());
        }
        stringBuilder.append(")");
        int delete = getWritableDatabase().delete("raw_events", stringBuilder.toString(), null);
        if (delete != list.size()) {
            zzayp().zzbau().zze("Deleted fewer rows from raw events table than expected", Integer.valueOf(delete), Integer.valueOf(list.size()));
        }
    }

    final Map<Integer, List<zzcns>> zzaj(String str, String str2) {
        Cursor query;
        Object e;
        Throwable th;
        zzyk();
        zzwj();
        zzbq.zzgv(str);
        zzbq.zzgv(str2);
        Map<Integer, List<zzcns>> arrayMap = new ArrayMap();
        try {
            query = getWritableDatabase().query("event_filters", new String[]{"audience_id", "data"}, "app_id=? AND event_name=?", new String[]{str, str2}, null, null, null);
            if (query.moveToFirst()) {
                do {
                    try {
                        byte[] blob = query.getBlob(1);
                        zzflj zzo = zzflj.zzo(blob, 0, blob.length);
                        zzfls zzcns = new zzcns();
                        try {
                            zzcns.zza(zzo);
                            int i = query.getInt(0);
                            List list = (List) arrayMap.get(Integer.valueOf(i));
                            if (list == null) {
                                list = new ArrayList();
                                arrayMap.put(Integer.valueOf(i), list);
                            }
                            list.add(zzcns);
                        } catch (IOException e2) {
                            zzayp().zzbau().zze("Failed to merge filter. appId", zzcjj.zzjs(str), e2);
                        }
                    } catch (SQLiteException e3) {
                        e = e3;
                    }
                } while (query.moveToNext());
                if (query != null) {
                    query.close();
                }
                return arrayMap;
            }
            Map<Integer, List<zzcns>> emptyMap = Collections.emptyMap();
            if (query == null) {
                return emptyMap;
            }
            query.close();
            return emptyMap;
        } catch (SQLiteException e4) {
            e = e4;
            query = null;
            try {
                zzayp().zzbau().zze("Database error querying filters. appId", zzcjj.zzjs(str), e);
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (Throwable th2) {
                th = th2;
                if (query != null) {
                    query.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
    }

    final Map<Integer, List<zzcnv>> zzak(String str, String str2) {
        Object e;
        Throwable th;
        zzyk();
        zzwj();
        zzbq.zzgv(str);
        zzbq.zzgv(str2);
        Map<Integer, List<zzcnv>> arrayMap = new ArrayMap();
        Cursor query;
        try {
            query = getWritableDatabase().query("property_filters", new String[]{"audience_id", "data"}, "app_id=? AND property_name=?", new String[]{str, str2}, null, null, null);
            if (query.moveToFirst()) {
                do {
                    try {
                        byte[] blob = query.getBlob(1);
                        zzflj zzo = zzflj.zzo(blob, 0, blob.length);
                        zzfls zzcnv = new zzcnv();
                        try {
                            zzcnv.zza(zzo);
                            int i = query.getInt(0);
                            List list = (List) arrayMap.get(Integer.valueOf(i));
                            if (list == null) {
                                list = new ArrayList();
                                arrayMap.put(Integer.valueOf(i), list);
                            }
                            list.add(zzcnv);
                        } catch (IOException e2) {
                            zzayp().zzbau().zze("Failed to merge filter", zzcjj.zzjs(str), e2);
                        }
                    } catch (SQLiteException e3) {
                        e = e3;
                    }
                } while (query.moveToNext());
                if (query != null) {
                    query.close();
                }
                return arrayMap;
            }
            Map<Integer, List<zzcnv>> emptyMap = Collections.emptyMap();
            if (query == null) {
                return emptyMap;
            }
            query.close();
            return emptyMap;
        } catch (SQLiteException e4) {
            e = e4;
            query = null;
            try {
                zzayp().zzbau().zze("Database error querying filters. appId", zzcjj.zzjs(str), e);
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (Throwable th2) {
                th = th2;
                if (query != null) {
                    query.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
    }

    @WorkerThread
    protected final long zzal(String str, String str2) {
        long zza;
        Object e;
        zzbq.zzgv(str);
        zzbq.zzgv(str2);
        zzwj();
        zzyk();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            zza = zza(new StringBuilder(String.valueOf(str2).length() + 32).append("select ").append(str2).append(" from app2 where app_id=?").toString(), new String[]{str}, -1);
            if (zza == -1) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("app_id", str);
                contentValues.put("first_open_count", Integer.valueOf(0));
                contentValues.put("previous_install_count", Integer.valueOf(0));
                if (writableDatabase.insertWithOnConflict("app2", null, contentValues, 5) == -1) {
                    zzayp().zzbau().zze("Failed to insert column (got -1). appId", zzcjj.zzjs(str), str2);
                    writableDatabase.endTransaction();
                    return -1;
                }
                zza = 0;
            }
            try {
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("app_id", str);
                contentValues2.put(str2, Long.valueOf(1 + zza));
                if (((long) writableDatabase.update("app2", contentValues2, "app_id = ?", new String[]{str})) == 0) {
                    zzayp().zzbau().zze("Failed to update column (got 0). appId", zzcjj.zzjs(str), str2);
                    writableDatabase.endTransaction();
                    return -1;
                }
                writableDatabase.setTransactionSuccessful();
                writableDatabase.endTransaction();
                return zza;
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    zzayp().zzbau().zzd("Error inserting column. appId", zzcjj.zzjs(str), str2, e);
                    return zza;
                } finally {
                    writableDatabase.endTransaction();
                }
            }
        } catch (SQLiteException e3) {
            e = e3;
            zza = 0;
            zzayp().zzbau().zzd("Error inserting column. appId", zzcjj.zzjs(str), str2, e);
            return zza;
        }
    }

    protected final boolean zzazq() {
        return false;
    }

    @WorkerThread
    public final String zzazw() {
        Cursor rawQuery;
        Object e;
        Throwable th;
        String str = null;
        try {
            rawQuery = getWritableDatabase().rawQuery("select app_id from queue order by has_realtime desc, rowid asc limit 1;", null);
            try {
                if (rawQuery.moveToFirst()) {
                    str = rawQuery.getString(0);
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                } else if (rawQuery != null) {
                    rawQuery.close();
                }
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    zzayp().zzbau().zzj("Database error getting next bundle app id", e);
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    return str;
                } catch (Throwable th2) {
                    th = th2;
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    throw th;
                }
            }
        } catch (SQLiteException e3) {
            e = e3;
            rawQuery = null;
            zzayp().zzbau().zzj("Database error getting next bundle app id", e);
            if (rawQuery != null) {
                rawQuery.close();
            }
            return str;
        } catch (Throwable th3) {
            rawQuery = null;
            th = th3;
            if (rawQuery != null) {
                rawQuery.close();
            }
            throw th;
        }
        return str;
    }

    public final boolean zzazx() {
        return zzc("select count(1) > 0 from queue where has_realtime = 1", null) != 0;
    }

    @WorkerThread
    final void zzazy() {
        zzwj();
        zzyk();
        if (zzbae()) {
            long j = zzayq().zzjlq.get();
            long elapsedRealtime = zzxx().elapsedRealtime();
            if (Math.abs(elapsedRealtime - j) > ((Long) zzciz.zzjjl.get()).longValue()) {
                zzayq().zzjlq.set(elapsedRealtime);
                zzwj();
                zzyk();
                if (zzbae()) {
                    int delete = getWritableDatabase().delete("queue", "abs(bundle_end_timestamp - ?) > cast(? as integer)", new String[]{String.valueOf(zzxx().currentTimeMillis()), String.valueOf(zzcik.zzazs())});
                    if (delete > 0) {
                        zzayp().zzbba().zzj("Deleted stale rows. rowsDeleted", Integer.valueOf(delete));
                    }
                }
            }
        }
    }

    @WorkerThread
    public final long zzazz() {
        return zza("select max(bundle_end_timestamp) from queue", null, 0);
    }

    public final Pair<zzcob, Long> zzb(String str, Long l) {
        Cursor rawQuery;
        Object e;
        Throwable th;
        Pair<zzcob, Long> pair = null;
        zzwj();
        zzyk();
        try {
            rawQuery = getWritableDatabase().rawQuery("select main_event, children_to_process from main_event_params where app_id=? and event_id=?", new String[]{str, String.valueOf(l)});
            try {
                if (rawQuery.moveToFirst()) {
                    byte[] blob = rawQuery.getBlob(0);
                    Long valueOf = Long.valueOf(rawQuery.getLong(1));
                    zzflj zzo = zzflj.zzo(blob, 0, blob.length);
                    zzfls zzcob = new zzcob();
                    try {
                        zzcob.zza(zzo);
                        pair = Pair.create(zzcob, valueOf);
                        if (rawQuery != null) {
                            rawQuery.close();
                        }
                    } catch (IOException e2) {
                        zzayp().zzbau().zzd("Failed to merge main event. appId, eventId", zzcjj.zzjs(str), l, e2);
                        if (rawQuery != null) {
                            rawQuery.close();
                        }
                    }
                } else {
                    zzayp().zzbba().log("Main event not found");
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                }
            } catch (SQLiteException e3) {
                e = e3;
                try {
                    zzayp().zzbau().zzj("Error selecting main event", e);
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    return pair;
                } catch (Throwable th2) {
                    th = th2;
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    throw th;
                }
            }
        } catch (SQLiteException e4) {
            e = e4;
            rawQuery = pair;
            zzayp().zzbau().zzj("Error selecting main event", e);
            if (rawQuery != null) {
                rawQuery.close();
            }
            return pair;
        } catch (Throwable th3) {
            rawQuery = pair;
            th = th3;
            if (rawQuery != null) {
                rawQuery.close();
            }
            throw th;
        }
        return pair;
    }

    public final String zzba(long j) {
        Cursor rawQuery;
        Object e;
        Throwable th;
        String str = null;
        zzwj();
        zzyk();
        try {
            rawQuery = getWritableDatabase().rawQuery("select app_id from apps where app_id in (select distinct app_id from raw_events) and config_fetched_time < ? order by failed_config_fetch_time limit 1;", new String[]{String.valueOf(j)});
            try {
                if (rawQuery.moveToFirst()) {
                    str = rawQuery.getString(0);
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                } else {
                    zzayp().zzbba().log("No expired configs for apps with pending events");
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                }
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    zzayp().zzbau().zzj("Error selecting expired configs", e);
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    return str;
                } catch (Throwable th2) {
                    th = th2;
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    throw th;
                }
            }
        } catch (SQLiteException e3) {
            e = e3;
            rawQuery = str;
            zzayp().zzbau().zzj("Error selecting expired configs", e);
            if (rawQuery != null) {
                rawQuery.close();
            }
            return str;
        } catch (Throwable th3) {
            rawQuery = str;
            th = th3;
            if (rawQuery != null) {
                rawQuery.close();
            }
            throw th;
        }
        return str;
    }

    @WorkerThread
    public final long zzbaa() {
        return zza("select max(timestamp) from raw_events", null, 0);
    }

    public final boolean zzbab() {
        return zzc("select count(1) > 0 from raw_events", null) != 0;
    }

    public final boolean zzbac() {
        return zzc("select count(1) > 0 from raw_events where realtime = 1", null) != 0;
    }

    public final long zzbad() {
        long j = -1;
        Cursor cursor = null;
        try {
            cursor = getWritableDatabase().rawQuery("select rowid from raw_events order by rowid desc limit 1;", null);
            if (cursor.moveToFirst()) {
                j = cursor.getLong(0);
                if (cursor != null) {
                    cursor.close();
                }
            } else if (cursor != null) {
                cursor.close();
            }
        } catch (SQLiteException e) {
            zzayp().zzbau().zzj("Error querying raw events", e);
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return j;
    }

    public final List<zzcii> zzd(String str, String[] strArr) {
        Object e;
        Cursor cursor;
        Throwable th;
        zzwj();
        zzyk();
        List<zzcii> arrayList = new ArrayList();
        Cursor query;
        try {
            query = getWritableDatabase().query("conditional_properties", new String[]{"app_id", "origin", "name", Param.VALUE, "active", "trigger_event_name", "trigger_timeout", "timed_out_event", "creation_timestamp", "triggered_event", "triggered_timestamp", "time_to_live", "expired_event"}, str, strArr, null, null, "rowid", "1001");
            try {
                if (query.moveToFirst()) {
                    do {
                        if (arrayList.size() >= 1000) {
                            zzayp().zzbau().zzj("Read more than the max allowed conditional properties, ignoring extra", Integer.valueOf(1000));
                            break;
                        }
                        String string = query.getString(0);
                        String string2 = query.getString(1);
                        String string3 = query.getString(2);
                        Object zza = zza(query, 3);
                        boolean z = query.getInt(4) != 0;
                        String string4 = query.getString(5);
                        long j = query.getLong(6);
                        zzcix zzcix = (zzcix) zzayl().zzb(query.getBlob(7), zzcix.CREATOR);
                        long j2 = query.getLong(8);
                        zzcix zzcix2 = (zzcix) zzayl().zzb(query.getBlob(9), zzcix.CREATOR);
                        long j3 = query.getLong(10);
                        List<zzcii> list = arrayList;
                        list.add(new zzcii(string, string2, new zzcnl(string3, j3, zza, string2), j2, z, string4, zzcix, j, zzcix2, query.getLong(11), (zzcix) zzayl().zzb(query.getBlob(12), zzcix.CREATOR)));
                    } while (query.moveToNext());
                    if (query != null) {
                        query.close();
                    }
                    return arrayList;
                }
                if (query != null) {
                    query.close();
                }
                return arrayList;
            } catch (SQLiteException e2) {
                e = e2;
                cursor = query;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursor = null;
            try {
                zzayp().zzbau().zzj("Error querying conditional user property value", e);
                List<zzcii> emptyList = Collections.emptyList();
                if (cursor == null) {
                    return emptyList;
                }
                cursor.close();
                return emptyList;
            } catch (Throwable th3) {
                th = th3;
                query = cursor;
                if (query != null) {
                    query.close();
                }
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
    }

    @WorkerThread
    public final List<zzcnn> zzh(String str, String str2, String str3) {
        Object obj;
        Object e;
        Cursor cursor;
        Throwable th;
        Cursor cursor2 = null;
        zzbq.zzgv(str);
        zzwj();
        zzyk();
        List<zzcnn> arrayList = new ArrayList();
        try {
            List arrayList2 = new ArrayList(3);
            arrayList2.add(str);
            StringBuilder stringBuilder = new StringBuilder("app_id=?");
            if (!TextUtils.isEmpty(str2)) {
                arrayList2.add(str2);
                stringBuilder.append(" and origin=?");
            }
            if (!TextUtils.isEmpty(str3)) {
                arrayList2.add(String.valueOf(str3).concat("*"));
                stringBuilder.append(" and name glob ?");
            }
            String[] strArr = new String[]{"name", "set_timestamp", Param.VALUE, "origin"};
            Cursor query = getWritableDatabase().query("user_attributes", strArr, stringBuilder.toString(), (String[]) arrayList2.toArray(new String[arrayList2.size()]), null, null, "rowid", "1001");
            try {
                if (query.moveToFirst()) {
                    while (arrayList.size() < 1000) {
                        String string;
                        try {
                            String string2 = query.getString(0);
                            long j = query.getLong(1);
                            Object zza = zza(query, 2);
                            string = query.getString(3);
                            if (zza == null) {
                                zzayp().zzbau().zzd("(2)Read invalid user property value, ignoring it", zzcjj.zzjs(str), string, str3);
                            } else {
                                arrayList.add(new zzcnn(str, string, string2, j, zza));
                            }
                            if (!query.moveToNext()) {
                                break;
                            }
                            obj = string;
                        } catch (SQLiteException e2) {
                            e = e2;
                            cursor = query;
                            obj = string;
                        } catch (Throwable th2) {
                            th = th2;
                            cursor2 = query;
                        }
                    }
                    zzayp().zzbau().zzj("Read more than the max allowed user properties, ignoring excess", Integer.valueOf(1000));
                    if (query != null) {
                        query.close();
                    }
                    return arrayList;
                }
                if (query != null) {
                    query.close();
                }
                return arrayList;
            } catch (SQLiteException e3) {
                e = e3;
                cursor = query;
            } catch (Throwable th22) {
                th = th22;
                cursor2 = query;
            }
        } catch (SQLiteException e4) {
            e = e4;
            cursor = null;
            try {
                zzayp().zzbau().zzd("(2)Error querying user properties", zzcjj.zzjs(str), obj, e);
                if (cursor != null) {
                    cursor.close();
                }
                return null;
            } catch (Throwable th3) {
                th = th3;
                cursor2 = cursor;
                if (cursor2 != null) {
                    cursor2.close();
                }
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            if (cursor2 != null) {
                cursor2.close();
            }
            throw th;
        }
    }

    @WorkerThread
    public final List<zzcii> zzi(String str, String str2, String str3) {
        zzbq.zzgv(str);
        zzwj();
        zzyk();
        List arrayList = new ArrayList(3);
        arrayList.add(str);
        StringBuilder stringBuilder = new StringBuilder("app_id=?");
        if (!TextUtils.isEmpty(str2)) {
            arrayList.add(str2);
            stringBuilder.append(" and origin=?");
        }
        if (!TextUtils.isEmpty(str3)) {
            arrayList.add(String.valueOf(str3).concat("*"));
            stringBuilder.append(" and name glob ?");
        }
        return zzd(stringBuilder.toString(), (String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    @WorkerThread
    public final List<zzcnn> zzji(String str) {
        Object e;
        Cursor cursor;
        Throwable th;
        Cursor cursor2 = null;
        zzbq.zzgv(str);
        zzwj();
        zzyk();
        List<zzcnn> arrayList = new ArrayList();
        try {
            Cursor query = getWritableDatabase().query("user_attributes", new String[]{"name", "origin", "set_timestamp", Param.VALUE}, "app_id=?", new String[]{str}, null, null, "rowid", "1000");
            try {
                if (query.moveToFirst()) {
                    do {
                        String string = query.getString(0);
                        String string2 = query.getString(1);
                        if (string2 == null) {
                            string2 = "";
                        }
                        long j = query.getLong(2);
                        Object zza = zza(query, 3);
                        if (zza == null) {
                            zzayp().zzbau().zzj("Read invalid user property value, ignoring it. appId", zzcjj.zzjs(str));
                        } else {
                            arrayList.add(new zzcnn(str, string2, string, j, zza));
                        }
                    } while (query.moveToNext());
                    if (query != null) {
                        query.close();
                    }
                    return arrayList;
                }
                if (query != null) {
                    query.close();
                }
                return arrayList;
            } catch (SQLiteException e2) {
                e = e2;
                cursor = query;
            } catch (Throwable th2) {
                th = th2;
                cursor2 = query;
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursor = null;
            try {
                zzayp().zzbau().zze("Error querying user properties. appId", zzcjj.zzjs(str), e);
                if (cursor != null) {
                    cursor.close();
                }
                return null;
            } catch (Throwable th3) {
                th = th3;
                cursor2 = cursor;
                if (cursor2 != null) {
                    cursor2.close();
                }
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            if (cursor2 != null) {
                cursor2.close();
            }
            throw th;
        }
    }

    @WorkerThread
    public final zzcie zzjj(String str) {
        Object e;
        Throwable th;
        zzbq.zzgv(str);
        zzwj();
        zzyk();
        Cursor query;
        try {
            query = getWritableDatabase().query("apps", new String[]{"app_instance_id", "gmp_app_id", "resettable_device_id_hash", "last_bundle_index", "last_bundle_start_timestamp", "last_bundle_end_timestamp", "app_version", "app_store", "gmp_version", "dev_cert_hash", "measurement_enabled", "day", "daily_public_events_count", "daily_events_count", "daily_conversions_count", "config_fetched_time", "failed_config_fetch_time", "app_version_int", "firebase_instance_id", "daily_error_events_count", "daily_realtime_events_count", "health_monitor_sample", "android_id", "adid_reporting_enabled"}, "app_id=?", new String[]{str}, null, null, null);
            try {
                if (query.moveToFirst()) {
                    zzcie zzcie = new zzcie(this.zzjev, str);
                    zzcie.zziy(query.getString(0));
                    zzcie.zziz(query.getString(1));
                    zzcie.zzja(query.getString(2));
                    zzcie.zzaq(query.getLong(3));
                    zzcie.zzal(query.getLong(4));
                    zzcie.zzam(query.getLong(5));
                    zzcie.setAppVersion(query.getString(6));
                    zzcie.zzjc(query.getString(7));
                    zzcie.zzao(query.getLong(8));
                    zzcie.zzap(query.getLong(9));
                    boolean z = query.isNull(10) || query.getInt(10) != 0;
                    zzcie.setMeasurementEnabled(z);
                    zzcie.zzat(query.getLong(11));
                    zzcie.zzau(query.getLong(12));
                    zzcie.zzav(query.getLong(13));
                    zzcie.zzaw(query.getLong(14));
                    zzcie.zzar(query.getLong(15));
                    zzcie.zzas(query.getLong(16));
                    zzcie.zzan(query.isNull(17) ? -2147483648L : (long) query.getInt(17));
                    zzcie.zzjb(query.getString(18));
                    zzcie.zzay(query.getLong(19));
                    zzcie.zzax(query.getLong(20));
                    zzcie.zzjd(query.getString(21));
                    zzcie.zzaz(query.isNull(22) ? 0 : query.getLong(22));
                    z = query.isNull(23) || query.getInt(23) != 0;
                    zzcie.zzbq(z);
                    zzcie.zzays();
                    if (query.moveToNext()) {
                        zzayp().zzbau().zzj("Got multiple records for app, expected one. appId", zzcjj.zzjs(str));
                    }
                    if (query == null) {
                        return zzcie;
                    }
                    query.close();
                    return zzcie;
                }
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    zzayp().zzbau().zze("Error querying app. appId", zzcjj.zzjs(str), e);
                    if (query != null) {
                        query.close();
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    if (query != null) {
                        query.close();
                    }
                    throw th;
                }
            }
        } catch (SQLiteException e3) {
            e = e3;
            query = null;
            zzayp().zzbau().zze("Error querying app. appId", zzcjj.zzjs(str), e);
            if (query != null) {
                query.close();
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
    }

    public final long zzjk(String str) {
        zzbq.zzgv(str);
        zzwj();
        zzyk();
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            String valueOf = String.valueOf(Math.max(0, Math.min(1000000, zzayr().zzb(str, zzciz.zzjjc))));
            return (long) writableDatabase.delete("raw_events", "rowid in (select rowid from raw_events where app_id=? order by rowid desc limit -1 offset ?)", new String[]{str, valueOf});
        } catch (SQLiteException e) {
            zzayp().zzbau().zze("Error deleting over the limit events. appId", zzcjj.zzjs(str), e);
            return 0;
        }
    }

    @WorkerThread
    public final byte[] zzjl(String str) {
        Cursor query;
        Object e;
        Throwable th;
        zzbq.zzgv(str);
        zzwj();
        zzyk();
        try {
            query = getWritableDatabase().query("apps", new String[]{"remote_config"}, "app_id=?", new String[]{str}, null, null, null);
            try {
                if (query.moveToFirst()) {
                    byte[] blob = query.getBlob(0);
                    if (query.moveToNext()) {
                        zzayp().zzbau().zzj("Got multiple records for app config, expected one. appId", zzcjj.zzjs(str));
                    }
                    if (query == null) {
                        return blob;
                    }
                    query.close();
                    return blob;
                }
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    zzayp().zzbau().zze("Error querying remote config. appId", zzcjj.zzjs(str), e);
                    if (query != null) {
                        query.close();
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    if (query != null) {
                        query.close();
                    }
                    throw th;
                }
            }
        } catch (SQLiteException e3) {
            e = e3;
            query = null;
            zzayp().zzbau().zze("Error querying remote config. appId", zzcjj.zzjs(str), e);
            if (query != null) {
                query.close();
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
    }

    final Map<Integer, zzcof> zzjm(String str) {
        Cursor query;
        Object e;
        Throwable th;
        zzyk();
        zzwj();
        zzbq.zzgv(str);
        try {
            query = getWritableDatabase().query("audience_filter_values", new String[]{"audience_id", "current_results"}, "app_id=?", new String[]{str}, null, null, null);
            if (query.moveToFirst()) {
                Map<Integer, zzcof> arrayMap = new ArrayMap();
                do {
                    int i = query.getInt(0);
                    byte[] blob = query.getBlob(1);
                    zzflj zzo = zzflj.zzo(blob, 0, blob.length);
                    zzfls zzcof = new zzcof();
                    try {
                        zzcof.zza(zzo);
                    } catch (IOException e2) {
                        zzayp().zzbau().zzd("Failed to merge filter results. appId, audienceId, error", zzcjj.zzjs(str), Integer.valueOf(i), e2);
                    }
                    try {
                        arrayMap.put(Integer.valueOf(i), zzcof);
                    } catch (SQLiteException e3) {
                        e = e3;
                    }
                } while (query.moveToNext());
                if (query == null) {
                    return arrayMap;
                }
                query.close();
                return arrayMap;
            }
            if (query != null) {
                query.close();
            }
            return null;
        } catch (SQLiteException e4) {
            e = e4;
            query = null;
            try {
                zzayp().zzbau().zze("Database error querying filter results. appId", zzcjj.zzjs(str), e);
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (Throwable th2) {
                th = th2;
                if (query != null) {
                    query.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
    }

    public final long zzjn(String str) {
        zzbq.zzgv(str);
        return zza("select count(1) from events where app_id=? and name not like '!_%' escape '!'", new String[]{str}, 0);
    }

    @WorkerThread
    public final List<Pair<zzcoe, Long>> zzl(String str, int i, int i2) {
        List<Pair<zzcoe, Long>> arrayList;
        Object e;
        Cursor cursor;
        Throwable th;
        boolean z = true;
        zzwj();
        zzyk();
        zzbq.checkArgument(i > 0);
        if (i2 <= 0) {
            z = false;
        }
        zzbq.checkArgument(z);
        zzbq.zzgv(str);
        Cursor query;
        try {
            query = getWritableDatabase().query("queue", new String[]{"rowid", "data"}, "app_id=?", new String[]{str}, null, null, "rowid", String.valueOf(i));
            try {
                if (query.moveToFirst()) {
                    arrayList = new ArrayList();
                    int i3 = 0;
                    while (true) {
                        long j = query.getLong(0);
                        int length;
                        try {
                            byte[] zzs = zzayl().zzs(query.getBlob(1));
                            if (!arrayList.isEmpty() && zzs.length + i3 > i2) {
                                break;
                            }
                            zzflj zzo = zzflj.zzo(zzs, 0, zzs.length);
                            zzfls zzcoe = new zzcoe();
                            try {
                                zzcoe.zza(zzo);
                                length = zzs.length + i3;
                                arrayList.add(Pair.create(zzcoe, Long.valueOf(j)));
                            } catch (IOException e2) {
                                zzayp().zzbau().zze("Failed to merge queued bundle. appId", zzcjj.zzjs(str), e2);
                                length = i3;
                            }
                            if (!query.moveToNext() || length > i2) {
                                break;
                            }
                            i3 = length;
                        } catch (IOException e22) {
                            zzayp().zzbau().zze("Failed to unzip queued bundle. appId", zzcjj.zzjs(str), e22);
                            length = i3;
                        }
                    }
                    if (query != null) {
                        query.close();
                    }
                } else {
                    arrayList = Collections.emptyList();
                    if (query != null) {
                        query.close();
                    }
                }
            } catch (SQLiteException e3) {
                e = e3;
                cursor = query;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (SQLiteException e4) {
            e = e4;
            cursor = null;
            try {
                zzayp().zzbau().zze("Error querying bundles. appId", zzcjj.zzjs(str), e);
                arrayList = Collections.emptyList();
                if (cursor != null) {
                    cursor.close();
                }
                return arrayList;
            } catch (Throwable th3) {
                th = th3;
                query = cursor;
                if (query != null) {
                    query.close();
                }
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
        return arrayList;
    }
}
