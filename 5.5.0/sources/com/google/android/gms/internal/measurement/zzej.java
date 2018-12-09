package com.google.android.gms.internal.measurement;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v4.p022f.C0464a;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.data.DataBufferSafeParcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.measurement.AppMeasurement.Param;
import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

final class zzej extends zzjr {
    private static final String[] zzaew = new String[]{"last_bundled_timestamp", "ALTER TABLE events ADD COLUMN last_bundled_timestamp INTEGER;", "last_sampled_complex_event_id", "ALTER TABLE events ADD COLUMN last_sampled_complex_event_id INTEGER;", "last_sampling_rate", "ALTER TABLE events ADD COLUMN last_sampling_rate INTEGER;", "last_exempt_from_sampling", "ALTER TABLE events ADD COLUMN last_exempt_from_sampling INTEGER;"};
    private static final String[] zzaex = new String[]{"origin", "ALTER TABLE user_attributes ADD COLUMN origin TEXT;"};
    private static final String[] zzaey = new String[]{"app_version", "ALTER TABLE apps ADD COLUMN app_version TEXT;", "app_store", "ALTER TABLE apps ADD COLUMN app_store TEXT;", "gmp_version", "ALTER TABLE apps ADD COLUMN gmp_version INTEGER;", "dev_cert_hash", "ALTER TABLE apps ADD COLUMN dev_cert_hash INTEGER;", "measurement_enabled", "ALTER TABLE apps ADD COLUMN measurement_enabled INTEGER;", "last_bundle_start_timestamp", "ALTER TABLE apps ADD COLUMN last_bundle_start_timestamp INTEGER;", "day", "ALTER TABLE apps ADD COLUMN day INTEGER;", "daily_public_events_count", "ALTER TABLE apps ADD COLUMN daily_public_events_count INTEGER;", "daily_events_count", "ALTER TABLE apps ADD COLUMN daily_events_count INTEGER;", "daily_conversions_count", "ALTER TABLE apps ADD COLUMN daily_conversions_count INTEGER;", "remote_config", "ALTER TABLE apps ADD COLUMN remote_config BLOB;", "config_fetched_time", "ALTER TABLE apps ADD COLUMN config_fetched_time INTEGER;", "failed_config_fetch_time", "ALTER TABLE apps ADD COLUMN failed_config_fetch_time INTEGER;", "app_version_int", "ALTER TABLE apps ADD COLUMN app_version_int INTEGER;", "firebase_instance_id", "ALTER TABLE apps ADD COLUMN firebase_instance_id TEXT;", "daily_error_events_count", "ALTER TABLE apps ADD COLUMN daily_error_events_count INTEGER;", "daily_realtime_events_count", "ALTER TABLE apps ADD COLUMN daily_realtime_events_count INTEGER;", "health_monitor_sample", "ALTER TABLE apps ADD COLUMN health_monitor_sample TEXT;", "android_id", "ALTER TABLE apps ADD COLUMN android_id INTEGER;", "adid_reporting_enabled", "ALTER TABLE apps ADD COLUMN adid_reporting_enabled INTEGER;", "ssaid_reporting_enabled", "ALTER TABLE apps ADD COLUMN ssaid_reporting_enabled INTEGER;"};
    private static final String[] zzaez = new String[]{"realtime", "ALTER TABLE raw_events ADD COLUMN realtime INTEGER;"};
    private static final String[] zzafa = new String[]{"has_realtime", "ALTER TABLE queue ADD COLUMN has_realtime INTEGER;", "retry_count", "ALTER TABLE queue ADD COLUMN retry_count INTEGER;"};
    private static final String[] zzafb = new String[]{"previous_install_count", "ALTER TABLE app2 ADD COLUMN previous_install_count INTEGER;"};
    private final zzem zzafc = new zzem(this, getContext(), "google_app_measurement.db");
    private final zzjn zzafd = new zzjn(zzbt());

    zzej(zzjs zzjs) {
        super(zzjs);
    }

    private final long zza(String str, String[] strArr) {
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
            zzgf().zzis().zze("Database error", str, e);
            throw e;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

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
            zzgf().zzis().zze("Database error", str, e);
            throw e;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @VisibleForTesting
    private final Object zza(Cursor cursor, int i) {
        int type = cursor.getType(i);
        switch (type) {
            case 0:
                zzgf().zzis().log("Loaded invalid null value from database");
                return null;
            case 1:
                return Long.valueOf(cursor.getLong(i));
            case 2:
                return Double.valueOf(cursor.getDouble(i));
            case 3:
                return cursor.getString(i);
            case 4:
                zzgf().zzis().log("Loaded invalid blob type value, ignoring it");
                return null;
            default:
                zzgf().zzis().zzg("Loaded invalid unknown value type, ignoring it", Integer.valueOf(type));
                return null;
        }
    }

    private static void zza(ContentValues contentValues, String str, Object obj) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(obj);
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

    private final boolean zza(String str, int i, zzkg zzkg) {
        zzch();
        zzab();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzkg);
        if (TextUtils.isEmpty(zzkg.zzasc)) {
            zzgf().zziv().zzd("Event filter had no event name. Audience definition ignored. appId, audienceId, filterId", zzfh.zzbl(str), Integer.valueOf(i), String.valueOf(zzkg.zzasb));
            return false;
        }
        try {
            byte[] bArr = new byte[zzkg.zzvv()];
            zzaby zzb = zzaby.zzb(bArr, 0, bArr.length);
            zzkg.zza(zzb);
            zzb.zzvn();
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", str);
            contentValues.put("audience_id", Integer.valueOf(i));
            contentValues.put("filter_id", zzkg.zzasb);
            contentValues.put("event_name", zzkg.zzasc);
            contentValues.put(DataBufferSafeParcelable.DATA_FIELD, bArr);
            try {
                if (getWritableDatabase().insertWithOnConflict("event_filters", null, contentValues, 5) == -1) {
                    zzgf().zzis().zzg("Failed to insert event filter (got -1). appId", zzfh.zzbl(str));
                }
                return true;
            } catch (SQLiteException e) {
                zzgf().zzis().zze("Error storing event filter. appId", zzfh.zzbl(str), e);
                return false;
            }
        } catch (IOException e2) {
            zzgf().zzis().zze("Configuration loss. Failed to serialize event filter. appId", zzfh.zzbl(str), e2);
            return false;
        }
    }

    private final boolean zza(String str, int i, zzkj zzkj) {
        zzch();
        zzab();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzkj);
        if (TextUtils.isEmpty(zzkj.zzasr)) {
            zzgf().zziv().zzd("Property filter had no property name. Audience definition ignored. appId, audienceId, filterId", zzfh.zzbl(str), Integer.valueOf(i), String.valueOf(zzkj.zzasb));
            return false;
        }
        try {
            byte[] bArr = new byte[zzkj.zzvv()];
            zzaby zzb = zzaby.zzb(bArr, 0, bArr.length);
            zzkj.zza(zzb);
            zzb.zzvn();
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", str);
            contentValues.put("audience_id", Integer.valueOf(i));
            contentValues.put("filter_id", zzkj.zzasb);
            contentValues.put("property_name", zzkj.zzasr);
            contentValues.put(DataBufferSafeParcelable.DATA_FIELD, bArr);
            try {
                if (getWritableDatabase().insertWithOnConflict("property_filters", null, contentValues, 5) != -1) {
                    return true;
                }
                zzgf().zzis().zzg("Failed to insert property filter (got -1). appId", zzfh.zzbl(str));
                return false;
            } catch (SQLiteException e) {
                zzgf().zzis().zze("Error storing property filter. appId", zzfh.zzbl(str), e);
                return false;
            }
        } catch (IOException e2) {
            zzgf().zzis().zze("Configuration loss. Failed to serialize property filter. appId", zzfh.zzbl(str), e2);
            return false;
        }
    }

    private final boolean zza(String str, List<Integer> list) {
        Preconditions.checkNotEmpty(str);
        zzch();
        zzab();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            if (zza("select count(1) from audience_filter_values where app_id=?", new String[]{str}) <= ((long) Math.max(0, Math.min(2000, zzgh().zzb(str, zzey.zzahu))))) {
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
            zzgf().zzis().zze("Database error querying filters. appId", zzfh.zzbl(str), e);
            return false;
        }
    }

    private final boolean zzhz() {
        return getContext().getDatabasePath("google_app_measurement.db").exists();
    }

    public final void beginTransaction() {
        zzch();
        getWritableDatabase().beginTransaction();
    }

    public final void endTransaction() {
        zzch();
        getWritableDatabase().endTransaction();
    }

    @VisibleForTesting
    final SQLiteDatabase getWritableDatabase() {
        zzab();
        try {
            return this.zzafc.getWritableDatabase();
        } catch (SQLiteException e) {
            zzgf().zziv().zzg("Error opening database", e);
            throw e;
        }
    }

    public final void setTransactionSuccessful() {
        zzch();
        getWritableDatabase().setTransactionSuccessful();
    }

    public final long zza(zzks zzks) {
        zzab();
        zzch();
        Preconditions.checkNotNull(zzks);
        Preconditions.checkNotEmpty(zzks.zzti);
        try {
            long j;
            Object obj = new byte[zzks.zzvv()];
            zzaby zzb = zzaby.zzb(obj, 0, obj.length);
            zzks.zza(zzb);
            zzb.zzvn();
            zzhh zzgc = zzgc();
            Preconditions.checkNotNull(obj);
            zzgc.zzab();
            MessageDigest messageDigest = zzkc.getMessageDigest("MD5");
            if (messageDigest == null) {
                zzgc.zzgf().zzis().log("Failed to get MD5");
                j = 0;
            } else {
                j = zzkc.zzc(messageDigest.digest(obj));
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", zzks.zzti);
            contentValues.put("metadata_fingerprint", Long.valueOf(j));
            contentValues.put(TtmlNode.TAG_METADATA, obj);
            try {
                getWritableDatabase().insertWithOnConflict("raw_events_metadata", null, contentValues, 4);
                return j;
            } catch (SQLiteException e) {
                zzgf().zzis().zze("Error storing raw event metadata. appId", zzfh.zzbl(zzks.zzti), e);
                throw e;
            }
        } catch (IOException e2) {
            zzgf().zzis().zze("Data loss. Failed to serialize event metadata. appId", zzfh.zzbl(zzks.zzti), e2);
            throw e2;
        }
    }

    public final Pair<zzkp, Long> zza(String str, Long l) {
        Cursor rawQuery;
        Object e;
        Throwable th;
        Pair<zzkp, Long> pair = null;
        zzab();
        zzch();
        try {
            rawQuery = getWritableDatabase().rawQuery("select main_event, children_to_process from main_event_params where app_id=? and event_id=?", new String[]{str, String.valueOf(l)});
            try {
                if (rawQuery.moveToFirst()) {
                    byte[] blob = rawQuery.getBlob(0);
                    Long valueOf = Long.valueOf(rawQuery.getLong(1));
                    zzabx zza = zzabx.zza(blob, 0, blob.length);
                    zzacg zzkp = new zzkp();
                    try {
                        zzkp.zzb(zza);
                        pair = Pair.create(zzkp, valueOf);
                        if (rawQuery != null) {
                            rawQuery.close();
                        }
                    } catch (IOException e2) {
                        zzgf().zzis().zzd("Failed to merge main event. appId, eventId", zzfh.zzbl(str), l, e2);
                        if (rawQuery != null) {
                            rawQuery.close();
                        }
                    }
                } else {
                    zzgf().zziz().log("Main event not found");
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                }
            } catch (SQLiteException e3) {
                e = e3;
                try {
                    zzgf().zzis().zzg("Error selecting main event", e);
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
            zzgf().zzis().zzg("Error selecting main event", e);
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

    public final zzek zza(long j, String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        Cursor query;
        Object e;
        Throwable th;
        Preconditions.checkNotEmpty(str);
        zzab();
        zzch();
        String[] strArr = new String[]{str};
        zzek zzek = new zzek();
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            query = writableDatabase.query("apps", new String[]{"day", "daily_events_count", "daily_public_events_count", "daily_conversions_count", "daily_error_events_count", "daily_realtime_events_count"}, "app_id=?", new String[]{str}, null, null, null);
            try {
                if (query.moveToFirst()) {
                    if (query.getLong(0) == j) {
                        zzek.zzaff = query.getLong(1);
                        zzek.zzafe = query.getLong(2);
                        zzek.zzafg = query.getLong(3);
                        zzek.zzafh = query.getLong(4);
                        zzek.zzafi = query.getLong(5);
                    }
                    if (z) {
                        zzek.zzaff++;
                    }
                    if (z2) {
                        zzek.zzafe++;
                    }
                    if (z3) {
                        zzek.zzafg++;
                    }
                    if (z4) {
                        zzek.zzafh++;
                    }
                    if (z5) {
                        zzek.zzafi++;
                    }
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("day", Long.valueOf(j));
                    contentValues.put("daily_public_events_count", Long.valueOf(zzek.zzafe));
                    contentValues.put("daily_events_count", Long.valueOf(zzek.zzaff));
                    contentValues.put("daily_conversions_count", Long.valueOf(zzek.zzafg));
                    contentValues.put("daily_error_events_count", Long.valueOf(zzek.zzafh));
                    contentValues.put("daily_realtime_events_count", Long.valueOf(zzek.zzafi));
                    writableDatabase.update("apps", contentValues, "app_id=?", strArr);
                    if (query != null) {
                        query.close();
                    }
                    return zzek;
                }
                zzgf().zziv().zzg("Not updating daily counts, app is not known. appId", zzfh.zzbl(str));
                if (query != null) {
                    query.close();
                }
                return zzek;
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    zzgf().zzis().zze("Error updating daily counts. appId", zzfh.zzbl(str), e);
                    if (query != null) {
                        query.close();
                    }
                    return zzek;
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
            zzgf().zzis().zze("Error updating daily counts. appId", zzfh.zzbl(str), e);
            if (query != null) {
                query.close();
            }
            return zzek;
        } catch (Throwable th3) {
            th = th3;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
    }

    public final void zza(zzdy zzdy) {
        Preconditions.checkNotNull(zzdy);
        zzab();
        zzch();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzdy.zzah());
        contentValues.put("app_instance_id", zzdy.getAppInstanceId());
        contentValues.put("gmp_app_id", zzdy.getGmpAppId());
        contentValues.put("resettable_device_id_hash", zzdy.zzgk());
        contentValues.put("last_bundle_index", Long.valueOf(zzdy.zzgs()));
        contentValues.put("last_bundle_start_timestamp", Long.valueOf(zzdy.zzgm()));
        contentValues.put("last_bundle_end_timestamp", Long.valueOf(zzdy.zzgn()));
        contentValues.put("app_version", zzdy.zzag());
        contentValues.put("app_store", zzdy.zzgp());
        contentValues.put("gmp_version", Long.valueOf(zzdy.zzgq()));
        contentValues.put("dev_cert_hash", Long.valueOf(zzdy.zzgr()));
        contentValues.put("measurement_enabled", Boolean.valueOf(zzdy.isMeasurementEnabled()));
        contentValues.put("day", Long.valueOf(zzdy.zzgw()));
        contentValues.put("daily_public_events_count", Long.valueOf(zzdy.zzgx()));
        contentValues.put("daily_events_count", Long.valueOf(zzdy.zzgy()));
        contentValues.put("daily_conversions_count", Long.valueOf(zzdy.zzgz()));
        contentValues.put("config_fetched_time", Long.valueOf(zzdy.zzgt()));
        contentValues.put("failed_config_fetch_time", Long.valueOf(zzdy.zzgu()));
        contentValues.put("app_version_int", Long.valueOf(zzdy.zzgo()));
        contentValues.put("firebase_instance_id", zzdy.zzgl());
        contentValues.put("daily_error_events_count", Long.valueOf(zzdy.zzhb()));
        contentValues.put("daily_realtime_events_count", Long.valueOf(zzdy.zzha()));
        contentValues.put("health_monitor_sample", zzdy.zzhc());
        contentValues.put("android_id", Long.valueOf(zzdy.zzhe()));
        contentValues.put("adid_reporting_enabled", Boolean.valueOf(zzdy.zzhf()));
        contentValues.put("ssaid_reporting_enabled", Boolean.valueOf(zzdy.zzhg()));
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (((long) writableDatabase.update("apps", contentValues, "app_id = ?", new String[]{zzdy.zzah()})) == 0 && writableDatabase.insertWithOnConflict("apps", null, contentValues, 5) == -1) {
                zzgf().zzis().zzg("Failed to insert/update app (got -1). appId", zzfh.zzbl(zzdy.zzah()));
            }
        } catch (SQLiteException e) {
            zzgf().zzis().zze("Error storing app. appId", zzfh.zzbl(zzdy.zzah()), e);
        }
    }

    public final void zza(zzes zzes) {
        Long l = null;
        Preconditions.checkNotNull(zzes);
        zzab();
        zzch();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzes.zzti);
        contentValues.put("name", zzes.name);
        contentValues.put("lifetime_count", Long.valueOf(zzes.zzafs));
        contentValues.put("current_bundle_count", Long.valueOf(zzes.zzaft));
        contentValues.put("last_fire_timestamp", Long.valueOf(zzes.zzafu));
        contentValues.put("last_bundled_timestamp", Long.valueOf(zzes.zzafv));
        contentValues.put("last_sampled_complex_event_id", zzes.zzafw);
        contentValues.put("last_sampling_rate", zzes.zzafx);
        if (zzes.zzafy != null && zzes.zzafy.booleanValue()) {
            l = Long.valueOf(1);
        }
        contentValues.put("last_exempt_from_sampling", l);
        try {
            if (getWritableDatabase().insertWithOnConflict("events", null, contentValues, 5) == -1) {
                zzgf().zzis().zzg("Failed to insert/update event aggregates (got -1). appId", zzfh.zzbl(zzes.zzti));
            }
        } catch (SQLiteException e) {
            zzgf().zzis().zze("Error storing event aggregates. appId", zzfh.zzbl(zzes.zzti), e);
        }
    }

    final void zza(String str, zzkf[] zzkfArr) {
        int i = 0;
        zzch();
        zzab();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzkfArr);
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            int i2;
            zzch();
            zzab();
            Preconditions.checkNotEmpty(str);
            SQLiteDatabase writableDatabase2 = getWritableDatabase();
            writableDatabase2.delete("property_filters", "app_id=?", new String[]{str});
            writableDatabase2.delete("event_filters", "app_id=?", new String[]{str});
            for (zzkf zzkf : zzkfArr) {
                zzch();
                zzab();
                Preconditions.checkNotEmpty(str);
                Preconditions.checkNotNull(zzkf);
                Preconditions.checkNotNull(zzkf.zzarz);
                Preconditions.checkNotNull(zzkf.zzary);
                if (zzkf.zzarx == null) {
                    zzgf().zziv().zzg("Audience with no ID. appId", zzfh.zzbl(str));
                } else {
                    int intValue = zzkf.zzarx.intValue();
                    for (zzkg zzkg : zzkf.zzarz) {
                        if (zzkg.zzasb == null) {
                            zzgf().zziv().zze("Event filter with no ID. Audience definition ignored. appId, audienceId", zzfh.zzbl(str), zzkf.zzarx);
                            break;
                        }
                    }
                    for (zzkj zzkj : zzkf.zzary) {
                        if (zzkj.zzasb == null) {
                            zzgf().zziv().zze("Property filter with no ID. Audience definition ignored. appId, audienceId", zzfh.zzbl(str), zzkf.zzarx);
                            break;
                        }
                    }
                    for (zzkg zzkg2 : zzkf.zzarz) {
                        if (!zza(str, intValue, zzkg2)) {
                            i2 = 0;
                            break;
                        }
                    }
                    i2 = 1;
                    if (i2 != 0) {
                        for (zzkj zzkj2 : zzkf.zzary) {
                            if (!zza(str, intValue, zzkj2)) {
                                i2 = 0;
                                break;
                            }
                        }
                    }
                    if (i2 == 0) {
                        zzch();
                        zzab();
                        Preconditions.checkNotEmpty(str);
                        SQLiteDatabase writableDatabase3 = getWritableDatabase();
                        writableDatabase3.delete("property_filters", "app_id=? and audience_id=?", new String[]{str, String.valueOf(intValue)});
                        writableDatabase3.delete("event_filters", "app_id=? and audience_id=?", new String[]{str, String.valueOf(intValue)});
                    }
                }
            }
            List arrayList = new ArrayList();
            i2 = zzkfArr.length;
            while (i < i2) {
                arrayList.add(zzkfArr[i].zzarx);
                i++;
            }
            zza(str, arrayList);
            writableDatabase.setTransactionSuccessful();
        } finally {
            writableDatabase.endTransaction();
        }
    }

    public final boolean zza(zzee zzee) {
        Preconditions.checkNotNull(zzee);
        zzab();
        zzch();
        if (zzh(zzee.packageName, zzee.zzaeq.name) == null) {
            if (zza("SELECT COUNT(1) FROM conditional_properties WHERE app_id=?", new String[]{zzee.packageName}) >= 1000) {
                return false;
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzee.packageName);
        contentValues.put("origin", zzee.origin);
        contentValues.put("name", zzee.zzaeq.name);
        zza(contentValues, C1797b.VALUE, zzee.zzaeq.getValue());
        contentValues.put("active", Boolean.valueOf(zzee.active));
        contentValues.put("trigger_event_name", zzee.triggerEventName);
        contentValues.put("trigger_timeout", Long.valueOf(zzee.triggerTimeout));
        zzgc();
        contentValues.put("timed_out_event", zzkc.zza(zzee.zzaer));
        contentValues.put("creation_timestamp", Long.valueOf(zzee.creationTimestamp));
        zzgc();
        contentValues.put("triggered_event", zzkc.zza(zzee.zzaes));
        contentValues.put("triggered_timestamp", Long.valueOf(zzee.zzaeq.zzarl));
        contentValues.put("time_to_live", Long.valueOf(zzee.timeToLive));
        zzgc();
        contentValues.put("expired_event", zzkc.zza(zzee.zzaet));
        try {
            if (getWritableDatabase().insertWithOnConflict("conditional_properties", null, contentValues, 5) == -1) {
                zzgf().zzis().zzg("Failed to insert/update conditional user property (got -1)", zzfh.zzbl(zzee.packageName));
            }
        } catch (SQLiteException e) {
            zzgf().zzis().zze("Error storing conditional user property", zzfh.zzbl(zzee.packageName), e);
        }
        return true;
    }

    public final boolean zza(zzer zzer, long j, boolean z) {
        zzab();
        zzch();
        Preconditions.checkNotNull(zzer);
        Preconditions.checkNotEmpty(zzer.zzti);
        zzacg zzkp = new zzkp();
        zzkp.zzato = Long.valueOf(zzer.zzafq);
        zzkp.zzatm = new zzkq[zzer.zzafr.size()];
        Iterator it = zzer.zzafr.iterator();
        int i = 0;
        while (it.hasNext()) {
            String str = (String) it.next();
            zzkq zzkq = new zzkq();
            int i2 = i + 1;
            zzkp.zzatm[i] = zzkq;
            zzkq.name = str;
            zzjc().zza(zzkq, zzer.zzafr.get(str));
            i = i2;
        }
        try {
            byte[] bArr = new byte[zzkp.zzvv()];
            zzaby zzb = zzaby.zzb(bArr, 0, bArr.length);
            zzkp.zza(zzb);
            zzb.zzvn();
            zzgf().zziz().zze("Saving event, name, data size", zzgb().zzbi(zzer.name), Integer.valueOf(bArr.length));
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", zzer.zzti);
            contentValues.put("name", zzer.name);
            contentValues.put(Param.TIMESTAMP, Long.valueOf(zzer.timestamp));
            contentValues.put("metadata_fingerprint", Long.valueOf(j));
            contentValues.put(DataBufferSafeParcelable.DATA_FIELD, bArr);
            contentValues.put("realtime", Integer.valueOf(z ? 1 : 0));
            try {
                if (getWritableDatabase().insert("raw_events", null, contentValues) != -1) {
                    return true;
                }
                zzgf().zzis().zzg("Failed to insert raw event (got -1). appId", zzfh.zzbl(zzer.zzti));
                return false;
            } catch (SQLiteException e) {
                zzgf().zzis().zze("Error storing raw event. appId", zzfh.zzbl(zzer.zzti), e);
                return false;
            }
        } catch (IOException e2) {
            zzgf().zzis().zze("Data loss. Failed to serialize event params/data. appId", zzfh.zzbl(zzer.zzti), e2);
            return false;
        }
    }

    public final boolean zza(zzkb zzkb) {
        Preconditions.checkNotNull(zzkb);
        zzab();
        zzch();
        if (zzh(zzkb.zzti, zzkb.name) == null) {
            if (zzkc.zzcb(zzkb.name)) {
                if (zza("select count(1) from user_attributes where app_id=? and name not like '!_%' escape '!'", new String[]{zzkb.zzti}) >= 25) {
                    return false;
                }
            }
            if (zza("select count(1) from user_attributes where app_id=? and origin=? AND name like '!_%' escape '!'", new String[]{zzkb.zzti, zzkb.origin}) >= 25) {
                return false;
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzkb.zzti);
        contentValues.put("origin", zzkb.origin);
        contentValues.put("name", zzkb.name);
        contentValues.put("set_timestamp", Long.valueOf(zzkb.zzarl));
        zza(contentValues, C1797b.VALUE, zzkb.value);
        try {
            if (getWritableDatabase().insertWithOnConflict("user_attributes", null, contentValues, 5) == -1) {
                zzgf().zzis().zzg("Failed to insert/update user property (got -1). appId", zzfh.zzbl(zzkb.zzti));
            }
        } catch (SQLiteException e) {
            zzgf().zzis().zze("Error storing user property. appId", zzfh.zzbl(zzkb.zzti), e);
        }
        return true;
    }

    public final boolean zza(zzks zzks, boolean z) {
        zzab();
        zzch();
        Preconditions.checkNotNull(zzks);
        Preconditions.checkNotEmpty(zzks.zzti);
        Preconditions.checkNotNull(zzks.zzaty);
        zzht();
        long currentTimeMillis = zzbt().currentTimeMillis();
        if (zzks.zzaty.longValue() < currentTimeMillis - zzeg.zzhl() || zzks.zzaty.longValue() > zzeg.zzhl() + currentTimeMillis) {
            zzgf().zziv().zzd("Storing bundle outside of the max uploading time span. appId, now, timestamp", zzfh.zzbl(zzks.zzti), Long.valueOf(currentTimeMillis), zzks.zzaty);
        }
        try {
            byte[] bArr = new byte[zzks.zzvv()];
            zzaby zzb = zzaby.zzb(bArr, 0, bArr.length);
            zzks.zza(zzb);
            zzb.zzvn();
            bArr = zzgc().zza(bArr);
            zzgf().zziz().zzg("Saving bundle, size", Integer.valueOf(bArr.length));
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", zzks.zzti);
            contentValues.put("bundle_end_timestamp", zzks.zzaty);
            contentValues.put(DataBufferSafeParcelable.DATA_FIELD, bArr);
            contentValues.put("has_realtime", Integer.valueOf(z ? 1 : 0));
            if (zzks.zzauv != null) {
                contentValues.put("retry_count", zzks.zzauv);
            }
            try {
                if (getWritableDatabase().insert("queue", null, contentValues) != -1) {
                    return true;
                }
                zzgf().zzis().zzg("Failed to insert bundle (got -1). appId", zzfh.zzbl(zzks.zzti));
                return false;
            } catch (SQLiteException e) {
                zzgf().zzis().zze("Error storing bundle. appId", zzfh.zzbl(zzks.zzti), e);
                return false;
            }
        } catch (IOException e2) {
            zzgf().zzis().zze("Data loss. Failed to serialize bundle. appId", zzfh.zzbl(zzks.zzti), e2);
            return false;
        }
    }

    public final boolean zza(String str, Long l, long j, zzkp zzkp) {
        zzab();
        zzch();
        Preconditions.checkNotNull(zzkp);
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(l);
        try {
            byte[] bArr = new byte[zzkp.zzvv()];
            zzaby zzb = zzaby.zzb(bArr, 0, bArr.length);
            zzkp.zza(zzb);
            zzb.zzvn();
            zzgf().zziz().zze("Saving complex main event, appId, data size", zzgb().zzbi(str), Integer.valueOf(bArr.length));
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", str);
            contentValues.put("event_id", l);
            contentValues.put("children_to_process", Long.valueOf(j));
            contentValues.put("main_event", bArr);
            try {
                if (getWritableDatabase().insertWithOnConflict("main_event_params", null, contentValues, 5) != -1) {
                    return true;
                }
                zzgf().zzis().zzg("Failed to insert complex main event (got -1). appId", zzfh.zzbl(str));
                return false;
            } catch (SQLiteException e) {
                zzgf().zzis().zze("Error storing complex main event. appId", zzfh.zzbl(str), e);
                return false;
            }
        } catch (IOException e2) {
            zzgf().zzis().zzd("Data loss. Failed to serialize event params/data. appId, eventId", zzfh.zzbl(str), l, e2);
            return false;
        }
    }

    public final String zzab(long j) {
        Object e;
        Throwable th;
        String str = null;
        zzab();
        zzch();
        Cursor rawQuery;
        try {
            rawQuery = getWritableDatabase().rawQuery("select app_id from apps where app_id in (select distinct app_id from raw_events) and config_fetched_time < ? order by failed_config_fetch_time limit 1;", new String[]{String.valueOf(j)});
            try {
                if (rawQuery.moveToFirst()) {
                    str = rawQuery.getString(0);
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                } else {
                    zzgf().zziz().log("No expired configs for apps with pending events");
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                }
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    zzgf().zzis().zzg("Error selecting expired configs", e);
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
            zzgf().zzis().zzg("Error selecting expired configs", e);
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

    public final List<Pair<zzks, Long>> zzb(String str, int i, int i2) {
        Cursor query;
        List<Pair<zzks, Long>> arrayList;
        Object e;
        Cursor cursor;
        Throwable th;
        boolean z = true;
        zzab();
        zzch();
        Preconditions.checkArgument(i > 0);
        if (i2 <= 0) {
            z = false;
        }
        Preconditions.checkArgument(z);
        Preconditions.checkNotEmpty(str);
        try {
            query = getWritableDatabase().query("queue", new String[]{"rowid", DataBufferSafeParcelable.DATA_FIELD, "retry_count"}, "app_id=?", new String[]{str}, null, null, "rowid", String.valueOf(i));
            try {
                if (query.moveToFirst()) {
                    arrayList = new ArrayList();
                    int i3 = 0;
                    while (true) {
                        long j = query.getLong(0);
                        int length;
                        try {
                            byte[] zzb = zzgc().zzb(query.getBlob(1));
                            if (!arrayList.isEmpty() && zzb.length + i3 > i2) {
                                break;
                            }
                            zzabx zza = zzabx.zza(zzb, 0, zzb.length);
                            zzacg zzks = new zzks();
                            try {
                                zzks.zzb(zza);
                                if (!query.isNull(2)) {
                                    zzks.zzauv = Integer.valueOf(query.getInt(2));
                                }
                                length = zzb.length + i3;
                                arrayList.add(Pair.create(zzks, Long.valueOf(j)));
                            } catch (IOException e2) {
                                zzgf().zzis().zze("Failed to merge queued bundle. appId", zzfh.zzbl(str), e2);
                                length = i3;
                            }
                            if (!query.moveToNext() || length > i2) {
                                break;
                            }
                            i3 = length;
                        } catch (IOException e22) {
                            zzgf().zzis().zze("Failed to unzip queued bundle. appId", zzfh.zzbl(str), e22);
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
                zzgf().zzis().zze("Error querying bundles. appId", zzfh.zzbl(str), e);
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

    public final List<zzkb> zzb(String str, String str2, String str3) {
        Object obj;
        Object e;
        Cursor cursor;
        Throwable th;
        Cursor cursor2 = null;
        Preconditions.checkNotEmpty(str);
        zzab();
        zzch();
        List<zzkb> arrayList = new ArrayList();
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
            String[] strArr = new String[]{"name", "set_timestamp", C1797b.VALUE, "origin"};
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
                                zzgf().zzis().zzd("(2)Read invalid user property value, ignoring it", zzfh.zzbl(str), string, str3);
                            } else {
                                arrayList.add(new zzkb(str, string, string2, j, zza));
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
                    zzgf().zzis().zzg("Read more than the max allowed user properties, ignoring excess", Integer.valueOf(1000));
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
                zzgf().zzis().zzd("(2)Error querying user properties", zzfh.zzbl(str), obj, e);
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

    public final List<zzee> zzb(String str, String[] strArr) {
        Object e;
        Cursor cursor;
        Throwable th;
        zzab();
        zzch();
        List<zzee> arrayList = new ArrayList();
        Cursor query;
        try {
            query = getWritableDatabase().query("conditional_properties", new String[]{"app_id", "origin", "name", C1797b.VALUE, "active", "trigger_event_name", "trigger_timeout", "timed_out_event", "creation_timestamp", "triggered_event", "triggered_timestamp", "time_to_live", "expired_event"}, str, strArr, null, null, "rowid", "1001");
            try {
                if (query.moveToFirst()) {
                    do {
                        if (arrayList.size() >= 1000) {
                            zzgf().zzis().zzg("Read more than the max allowed conditional properties, ignoring extra", Integer.valueOf(1000));
                            break;
                        }
                        String string = query.getString(0);
                        String string2 = query.getString(1);
                        String string3 = query.getString(2);
                        Object zza = zza(query, 3);
                        boolean z = query.getInt(4) != 0;
                        String string4 = query.getString(5);
                        long j = query.getLong(6);
                        zzew zzew = (zzew) zzgc().zza(query.getBlob(7), zzew.CREATOR);
                        long j2 = query.getLong(8);
                        zzew zzew2 = (zzew) zzgc().zza(query.getBlob(9), zzew.CREATOR);
                        long j3 = query.getLong(10);
                        List<zzee> list = arrayList;
                        list.add(new zzee(string, string2, new zzjz(string3, j3, zza, string2), j2, z, string4, zzew, j, zzew2, query.getLong(11), (zzew) zzgc().zza(query.getBlob(12), zzew.CREATOR)));
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
                zzgf().zzis().zzg("Error querying conditional user property value", e);
                List<zzee> emptyList = Collections.emptyList();
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

    public final List<zzkb> zzba(String str) {
        Object e;
        Cursor cursor;
        Throwable th;
        Cursor cursor2 = null;
        Preconditions.checkNotEmpty(str);
        zzab();
        zzch();
        List<zzkb> arrayList = new ArrayList();
        try {
            Cursor query = getWritableDatabase().query("user_attributes", new String[]{"name", "origin", "set_timestamp", C1797b.VALUE}, "app_id=?", new String[]{str}, null, null, "rowid", "1000");
            try {
                if (query.moveToFirst()) {
                    do {
                        String string = query.getString(0);
                        String string2 = query.getString(1);
                        if (string2 == null) {
                            string2 = TtmlNode.ANONYMOUS_REGION_ID;
                        }
                        long j = query.getLong(2);
                        Object zza = zza(query, 3);
                        if (zza == null) {
                            zzgf().zzis().zzg("Read invalid user property value, ignoring it. appId", zzfh.zzbl(str));
                        } else {
                            arrayList.add(new zzkb(str, string2, string, j, zza));
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
                zzgf().zzis().zze("Error querying user properties. appId", zzfh.zzbl(str), e);
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

    public final zzdy zzbb(String str) {
        Object e;
        Throwable th;
        Preconditions.checkNotEmpty(str);
        zzab();
        zzch();
        Cursor query;
        try {
            query = getWritableDatabase().query("apps", new String[]{"app_instance_id", "gmp_app_id", "resettable_device_id_hash", "last_bundle_index", "last_bundle_start_timestamp", "last_bundle_end_timestamp", "app_version", "app_store", "gmp_version", "dev_cert_hash", "measurement_enabled", "day", "daily_public_events_count", "daily_events_count", "daily_conversions_count", "config_fetched_time", "failed_config_fetch_time", "app_version_int", "firebase_instance_id", "daily_error_events_count", "daily_realtime_events_count", "health_monitor_sample", "android_id", "adid_reporting_enabled", "ssaid_reporting_enabled"}, "app_id=?", new String[]{str}, null, null, null);
            try {
                if (query.moveToFirst()) {
                    zzdy zzdy = new zzdy(this.zzajy.zzlj(), str);
                    zzdy.zzak(query.getString(0));
                    zzdy.zzal(query.getString(1));
                    zzdy.zzam(query.getString(2));
                    zzdy.zzr(query.getLong(3));
                    zzdy.zzm(query.getLong(4));
                    zzdy.zzn(query.getLong(5));
                    zzdy.setAppVersion(query.getString(6));
                    zzdy.zzao(query.getString(7));
                    zzdy.zzp(query.getLong(8));
                    zzdy.zzq(query.getLong(9));
                    boolean z = query.isNull(10) || query.getInt(10) != 0;
                    zzdy.setMeasurementEnabled(z);
                    zzdy.zzu(query.getLong(11));
                    zzdy.zzv(query.getLong(12));
                    zzdy.zzw(query.getLong(13));
                    zzdy.zzx(query.getLong(14));
                    zzdy.zzs(query.getLong(15));
                    zzdy.zzt(query.getLong(16));
                    zzdy.zzo(query.isNull(17) ? -2147483648L : (long) query.getInt(17));
                    zzdy.zzan(query.getString(18));
                    zzdy.zzz(query.getLong(19));
                    zzdy.zzy(query.getLong(20));
                    zzdy.zzap(query.getString(21));
                    zzdy.zzaa(query.isNull(22) ? 0 : query.getLong(22));
                    z = query.isNull(23) || query.getInt(23) != 0;
                    zzdy.zzd(z);
                    z = query.isNull(24) || query.getInt(24) != 0;
                    zzdy.zze(z);
                    zzdy.zzgj();
                    if (query.moveToNext()) {
                        zzgf().zzis().zzg("Got multiple records for app, expected one. appId", zzfh.zzbl(str));
                    }
                    if (query == null) {
                        return zzdy;
                    }
                    query.close();
                    return zzdy;
                }
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    zzgf().zzis().zze("Error querying app. appId", zzfh.zzbl(str), e);
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
            zzgf().zzis().zze("Error querying app. appId", zzfh.zzbl(str), e);
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

    public final long zzbc(String str) {
        Preconditions.checkNotEmpty(str);
        zzab();
        zzch();
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            String valueOf = String.valueOf(Math.max(0, Math.min(1000000, zzgh().zzb(str, zzey.zzahe))));
            return (long) writableDatabase.delete("raw_events", "rowid in (select rowid from raw_events where app_id=? order by rowid desc limit -1 offset ?)", new String[]{str, valueOf});
        } catch (SQLiteException e) {
            zzgf().zzis().zze("Error deleting over the limit events. appId", zzfh.zzbl(str), e);
            return 0;
        }
    }

    public final byte[] zzbd(String str) {
        Cursor query;
        Object e;
        Throwable th;
        Preconditions.checkNotEmpty(str);
        zzab();
        zzch();
        try {
            query = getWritableDatabase().query("apps", new String[]{"remote_config"}, "app_id=?", new String[]{str}, null, null, null);
            try {
                if (query.moveToFirst()) {
                    byte[] blob = query.getBlob(0);
                    if (query.moveToNext()) {
                        zzgf().zzis().zzg("Got multiple records for app config, expected one. appId", zzfh.zzbl(str));
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
                    zzgf().zzis().zze("Error querying remote config. appId", zzfh.zzbl(str), e);
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
            zzgf().zzis().zze("Error querying remote config. appId", zzfh.zzbl(str), e);
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

    final Map<Integer, zzkt> zzbe(String str) {
        Object e;
        Throwable th;
        zzch();
        zzab();
        Preconditions.checkNotEmpty(str);
        Cursor query;
        try {
            query = getWritableDatabase().query("audience_filter_values", new String[]{"audience_id", "current_results"}, "app_id=?", new String[]{str}, null, null, null);
            if (query.moveToFirst()) {
                Map<Integer, zzkt> c0464a = new C0464a();
                do {
                    int i = query.getInt(0);
                    byte[] blob = query.getBlob(1);
                    zzabx zza = zzabx.zza(blob, 0, blob.length);
                    zzacg zzkt = new zzkt();
                    try {
                        zzkt.zzb(zza);
                    } catch (IOException e2) {
                        zzgf().zzis().zzd("Failed to merge filter results. appId, audienceId, error", zzfh.zzbl(str), Integer.valueOf(i), e2);
                    }
                    try {
                        c0464a.put(Integer.valueOf(i), zzkt);
                    } catch (SQLiteException e3) {
                        e = e3;
                    }
                } while (query.moveToNext());
                if (query == null) {
                    return c0464a;
                }
                query.close();
                return c0464a;
            }
            if (query != null) {
                query.close();
            }
            return null;
        } catch (SQLiteException e4) {
            e = e4;
            query = null;
            try {
                zzgf().zzis().zze("Database error querying filter results. appId", zzfh.zzbl(str), e);
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

    public final long zzbf(String str) {
        Preconditions.checkNotEmpty(str);
        return zza("select count(1) from events where app_id=? and name not like '!_%' escape '!'", new String[]{str}, 0);
    }

    public final List<zzee> zzc(String str, String str2, String str3) {
        Preconditions.checkNotEmpty(str);
        zzab();
        zzch();
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
        return zzb(stringBuilder.toString(), (String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    @VisibleForTesting
    final void zzc(List<Long> list) {
        zzab();
        zzch();
        Preconditions.checkNotNull(list);
        Preconditions.checkNotZero(list.size());
        if (zzhz()) {
            String join = TextUtils.join(",", list);
            join = new StringBuilder(String.valueOf(join).length() + 2).append("(").append(join).append(")").toString();
            if (zza(new StringBuilder(String.valueOf(join).length() + 80).append("SELECT COUNT(1) FROM queue WHERE rowid IN ").append(join).append(" AND retry_count =  2147483647 LIMIT 1").toString(), null) > 0) {
                zzgf().zziv().log("The number of upload retries exceeds the limit. Will remain unchanged.");
            }
            try {
                getWritableDatabase().execSQL(new StringBuilder(String.valueOf(join).length() + 127).append("UPDATE queue SET retry_count = IFNULL(retry_count, 0) + 1 WHERE rowid IN ").append(join).append(" AND (retry_count IS NULL OR retry_count < 2147483647)").toString());
            } catch (SQLiteException e) {
                zzgf().zzis().zzg("Error incrementing retry count. error", e);
            }
        }
    }

    public final zzes zzf(String str, String str2) {
        Cursor query;
        Object e;
        Cursor cursor;
        Throwable th;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzab();
        zzch();
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
                    zzes zzes = new zzes(str, str2, j, j2, j3, j4, valueOf, valueOf2, bool);
                    if (query.moveToNext()) {
                        zzgf().zzis().zzg("Got multiple records for event aggregates, expected one. appId", zzfh.zzbl(str));
                    }
                    if (query == null) {
                        return zzes;
                    }
                    query.close();
                    return zzes;
                }
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (SQLiteException e2) {
                e = e2;
                cursor = query;
                try {
                    zzgf().zzis().zzd("Error querying events. appId", zzfh.zzbl(str), zzgb().zzbi(str2), e);
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
            zzgf().zzis().zzd("Error querying events. appId", zzfh.zzbl(str), zzgb().zzbi(str2), e);
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

    public final void zzg(String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzab();
        zzch();
        try {
            zzgf().zziz().zzg("Deleted user attribute rows", Integer.valueOf(getWritableDatabase().delete("user_attributes", "app_id=? and name=?", new String[]{str, str2})));
        } catch (SQLiteException e) {
            zzgf().zzis().zzd("Error deleting user attribute. appId", zzfh.zzbl(str), zzgb().zzbk(str2), e);
        }
    }

    public final zzkb zzh(String str, String str2) {
        Object e;
        Cursor cursor;
        Throwable th;
        Cursor cursor2 = null;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzab();
        zzch();
        try {
            Cursor query = getWritableDatabase().query("user_attributes", new String[]{"set_timestamp", C1797b.VALUE, "origin"}, "app_id=? and name=?", new String[]{str, str2}, null, null, null);
            try {
                if (query.moveToFirst()) {
                    String str3 = str;
                    zzkb zzkb = new zzkb(str3, query.getString(2), str2, query.getLong(0), zza(query, 1));
                    if (query.moveToNext()) {
                        zzgf().zzis().zzg("Got multiple records for user property, expected one. appId", zzfh.zzbl(str));
                    }
                    if (query == null) {
                        return zzkb;
                    }
                    query.close();
                    return zzkb;
                }
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (SQLiteException e2) {
                e = e2;
                cursor = query;
                try {
                    zzgf().zzis().zzd("Error querying user property. appId", zzfh.zzbl(str), zzgb().zzbk(str2), e);
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
            zzgf().zzis().zzd("Error querying user property. appId", zzfh.zzbl(str), zzgb().zzbk(str2), e);
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

    protected final boolean zzhh() {
        return false;
    }

    public final String zzhr() {
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
                    zzgf().zzis().zzg("Database error getting next bundle app id", e);
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
            zzgf().zzis().zzg("Database error getting next bundle app id", e);
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

    public final boolean zzhs() {
        return zza("select count(1) > 0 from queue where has_realtime = 1", null) != 0;
    }

    final void zzht() {
        zzab();
        zzch();
        if (zzhz()) {
            long j = zzgg().zzakg.get();
            long elapsedRealtime = zzbt().elapsedRealtime();
            if (Math.abs(elapsedRealtime - j) > ((Long) zzey.zzahn.get()).longValue()) {
                zzgg().zzakg.set(elapsedRealtime);
                zzab();
                zzch();
                if (zzhz()) {
                    int delete = getWritableDatabase().delete("queue", "abs(bundle_end_timestamp - ?) > cast(? as integer)", new String[]{String.valueOf(zzbt().currentTimeMillis()), String.valueOf(zzeg.zzhl())});
                    if (delete > 0) {
                        zzgf().zziz().zzg("Deleted stale rows. rowsDeleted", Integer.valueOf(delete));
                    }
                }
            }
        }
    }

    public final long zzhu() {
        return zza("select max(bundle_end_timestamp) from queue", null, 0);
    }

    public final long zzhv() {
        return zza("select max(timestamp) from raw_events", null, 0);
    }

    public final boolean zzhw() {
        return zza("select count(1) > 0 from raw_events", null) != 0;
    }

    public final boolean zzhx() {
        return zza("select count(1) > 0 from raw_events where realtime = 1", null) != 0;
    }

    public final long zzhy() {
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
            zzgf().zzis().zzg("Error querying raw events", e);
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

    public final zzee zzi(String str, String str2) {
        Cursor query;
        Object e;
        Cursor cursor;
        Throwable th;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzab();
        zzch();
        try {
            query = getWritableDatabase().query("conditional_properties", new String[]{"origin", C1797b.VALUE, "active", "trigger_event_name", "trigger_timeout", "timed_out_event", "creation_timestamp", "triggered_event", "triggered_timestamp", "time_to_live", "expired_event"}, "app_id=? and name=?", new String[]{str, str2}, null, null, null);
            try {
                if (query.moveToFirst()) {
                    String string = query.getString(0);
                    Object zza = zza(query, 1);
                    boolean z = query.getInt(2) != 0;
                    String string2 = query.getString(3);
                    long j = query.getLong(4);
                    zzew zzew = (zzew) zzgc().zza(query.getBlob(5), zzew.CREATOR);
                    long j2 = query.getLong(6);
                    zzew zzew2 = (zzew) zzgc().zza(query.getBlob(7), zzew.CREATOR);
                    long j3 = query.getLong(8);
                    zzee zzee = new zzee(str, string, new zzjz(str2, j3, zza, string), j2, z, string2, zzew, j, zzew2, query.getLong(9), (zzew) zzgc().zza(query.getBlob(10), zzew.CREATOR));
                    if (query.moveToNext()) {
                        zzgf().zzis().zze("Got multiple records for conditional property, expected one", zzfh.zzbl(str), zzgb().zzbk(str2));
                    }
                    if (query == null) {
                        return zzee;
                    }
                    query.close();
                    return zzee;
                }
                if (query != null) {
                    query.close();
                }
                return null;
            } catch (SQLiteException e2) {
                e = e2;
                cursor = query;
                try {
                    zzgf().zzis().zzd("Error querying conditional property", zzfh.zzbl(str), zzgb().zzbk(str2), e);
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
            zzgf().zzis().zzd("Error querying conditional property", zzfh.zzbl(str), zzgb().zzbk(str2), e);
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

    public final int zzj(String str, String str2) {
        int i = 0;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzab();
        zzch();
        try {
            i = getWritableDatabase().delete("conditional_properties", "app_id=? and name=?", new String[]{str, str2});
        } catch (SQLiteException e) {
            zzgf().zzis().zzd("Error deleting conditional property", zzfh.zzbl(str), zzgb().zzbk(str2), e);
        }
        return i;
    }

    final Map<Integer, List<zzkg>> zzk(String str, String str2) {
        Object e;
        Throwable th;
        zzch();
        zzab();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        Map<Integer, List<zzkg>> c0464a = new C0464a();
        Cursor query;
        try {
            query = getWritableDatabase().query("event_filters", new String[]{"audience_id", DataBufferSafeParcelable.DATA_FIELD}, "app_id=? AND event_name=?", new String[]{str, str2}, null, null, null);
            if (query.moveToFirst()) {
                do {
                    try {
                        byte[] blob = query.getBlob(1);
                        zzabx zza = zzabx.zza(blob, 0, blob.length);
                        zzacg zzkg = new zzkg();
                        try {
                            zzkg.zzb(zza);
                            int i = query.getInt(0);
                            List list = (List) c0464a.get(Integer.valueOf(i));
                            if (list == null) {
                                list = new ArrayList();
                                c0464a.put(Integer.valueOf(i), list);
                            }
                            list.add(zzkg);
                        } catch (IOException e2) {
                            zzgf().zzis().zze("Failed to merge filter. appId", zzfh.zzbl(str), e2);
                        }
                    } catch (SQLiteException e3) {
                        e = e3;
                    }
                } while (query.moveToNext());
                if (query != null) {
                    query.close();
                }
                return c0464a;
            }
            Map<Integer, List<zzkg>> emptyMap = Collections.emptyMap();
            if (query == null) {
                return emptyMap;
            }
            query.close();
            return emptyMap;
        } catch (SQLiteException e4) {
            e = e4;
            query = null;
            try {
                zzgf().zzis().zze("Database error querying filters. appId", zzfh.zzbl(str), e);
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

    final Map<Integer, List<zzkj>> zzl(String str, String str2) {
        Object e;
        Throwable th;
        zzch();
        zzab();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        Map<Integer, List<zzkj>> c0464a = new C0464a();
        Cursor query;
        try {
            query = getWritableDatabase().query("property_filters", new String[]{"audience_id", DataBufferSafeParcelable.DATA_FIELD}, "app_id=? AND property_name=?", new String[]{str, str2}, null, null, null);
            if (query.moveToFirst()) {
                do {
                    try {
                        byte[] blob = query.getBlob(1);
                        zzabx zza = zzabx.zza(blob, 0, blob.length);
                        zzacg zzkj = new zzkj();
                        try {
                            zzkj.zzb(zza);
                            int i = query.getInt(0);
                            List list = (List) c0464a.get(Integer.valueOf(i));
                            if (list == null) {
                                list = new ArrayList();
                                c0464a.put(Integer.valueOf(i), list);
                            }
                            list.add(zzkj);
                        } catch (IOException e2) {
                            zzgf().zzis().zze("Failed to merge filter", zzfh.zzbl(str), e2);
                        }
                    } catch (SQLiteException e3) {
                        e = e3;
                    }
                } while (query.moveToNext());
                if (query != null) {
                    query.close();
                }
                return c0464a;
            }
            Map<Integer, List<zzkj>> emptyMap = Collections.emptyMap();
            if (query == null) {
                return emptyMap;
            }
            query.close();
            return emptyMap;
        } catch (SQLiteException e4) {
            e = e4;
            query = null;
            try {
                zzgf().zzis().zze("Database error querying filters. appId", zzfh.zzbl(str), e);
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

    @VisibleForTesting
    protected final long zzm(String str, String str2) {
        Object e;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzab();
        zzch();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.beginTransaction();
        long zza;
        try {
            zza = zza(new StringBuilder(String.valueOf(str2).length() + 32).append("select ").append(str2).append(" from app2 where app_id=?").toString(), new String[]{str}, -1);
            if (zza == -1) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("app_id", str);
                contentValues.put("first_open_count", Integer.valueOf(0));
                contentValues.put("previous_install_count", Integer.valueOf(0));
                if (writableDatabase.insertWithOnConflict("app2", null, contentValues, 5) == -1) {
                    zzgf().zzis().zze("Failed to insert column (got -1). appId", zzfh.zzbl(str), str2);
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
                    zzgf().zzis().zze("Failed to update column (got 0). appId", zzfh.zzbl(str), str2);
                    writableDatabase.endTransaction();
                    return -1;
                }
                writableDatabase.setTransactionSuccessful();
                writableDatabase.endTransaction();
                return zza;
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    zzgf().zzis().zzd("Error inserting column. appId", zzfh.zzbl(str), str2, e);
                    return zza;
                } finally {
                    writableDatabase.endTransaction();
                }
            }
        } catch (SQLiteException e3) {
            e = e3;
            zza = 0;
            zzgf().zzis().zzd("Error inserting column. appId", zzfh.zzbl(str), str2, e);
            return zza;
        }
    }
}
