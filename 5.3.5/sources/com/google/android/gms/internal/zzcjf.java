package com.google.android.gms.internal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteFullException;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.measurement.AppMeasurement.Param;
import java.util.ArrayList;
import java.util.List;

public final class zzcjf extends zzcli {
    private final zzcjg zzjka = new zzcjg(this, getContext(), "google_app_measurement_local.db");
    private boolean zzjkb;

    zzcjf(zzckj zzckj) {
        super(zzckj);
    }

    @WorkerThread
    private final SQLiteDatabase getWritableDatabase() throws SQLiteException {
        if (this.zzjkb) {
            return null;
        }
        SQLiteDatabase writableDatabase = this.zzjka.getWritableDatabase();
        if (writableDatabase != null) {
            return writableDatabase;
        }
        this.zzjkb = true;
        return null;
    }

    @WorkerThread
    private final boolean zzb(int i, byte[] bArr) {
        zzwj();
        if (this.zzjkb) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(Param.TYPE, Integer.valueOf(i));
        contentValues.put("entry", bArr);
        int i2 = 0;
        int i3 = 5;
        while (i2 < 5) {
            SQLiteDatabase sQLiteDatabase = null;
            Cursor cursor = null;
            try {
                sQLiteDatabase = getWritableDatabase();
                if (sQLiteDatabase == null) {
                    this.zzjkb = true;
                    if (sQLiteDatabase != null) {
                        sQLiteDatabase.close();
                    }
                    return false;
                }
                sQLiteDatabase.beginTransaction();
                long j = 0;
                cursor = sQLiteDatabase.rawQuery("select count(1) from messages", null);
                if (cursor != null && cursor.moveToFirst()) {
                    j = cursor.getLong(0);
                }
                if (j >= 100000) {
                    zzayp().zzbau().log("Data loss, local db full");
                    j = (100000 - j) + 1;
                    long delete = (long) sQLiteDatabase.delete("messages", "rowid in (select rowid from messages order by rowid asc limit ?)", new String[]{Long.toString(j)});
                    if (delete != j) {
                        zzayp().zzbau().zzd("Different delete count than expected in local db. expected, received, difference", Long.valueOf(j), Long.valueOf(delete), Long.valueOf(j - delete));
                    }
                }
                sQLiteDatabase.insertOrThrow("messages", null, contentValues);
                sQLiteDatabase.setTransactionSuccessful();
                sQLiteDatabase.endTransaction();
                if (cursor != null) {
                    cursor.close();
                }
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
                return true;
            } catch (SQLiteFullException e) {
                zzayp().zzbau().zzj("Error writing entry to local database", e);
                this.zzjkb = true;
                if (cursor != null) {
                    cursor.close();
                }
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
                i2++;
            } catch (SQLiteDatabaseLockedException e2) {
                SystemClock.sleep((long) i3);
                i3 += 20;
                if (cursor != null) {
                    cursor.close();
                }
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
                i2++;
            } catch (SQLiteException e3) {
                if (sQLiteDatabase != null) {
                    if (sQLiteDatabase.inTransaction()) {
                        sQLiteDatabase.endTransaction();
                    }
                }
                zzayp().zzbau().zzj("Error writing entry to local database", e3);
                this.zzjkb = true;
                if (cursor != null) {
                    cursor.close();
                }
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
                i2++;
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
            }
        }
        zzayp().zzbaw().log("Failed to write entry to local database");
        return false;
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    @WorkerThread
    public final void resetAnalyticsData() {
        zzwj();
        try {
            int delete = getWritableDatabase().delete("messages", null, null) + 0;
            if (delete > 0) {
                zzayp().zzbba().zzj("Reset local analytics data. records", Integer.valueOf(delete));
            }
        } catch (SQLiteException e) {
            zzayp().zzbau().zzj("Error resetting local analytics data. error", e);
        }
    }

    public final boolean zza(zzcix zzcix) {
        Parcel obtain = Parcel.obtain();
        zzcix.writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        if (marshall.length <= 131072) {
            return zzb(0, marshall);
        }
        zzayp().zzbaw().log("Event is too long for local database. Sending event directly to service");
        return false;
    }

    public final boolean zza(zzcnl zzcnl) {
        Parcel obtain = Parcel.obtain();
        zzcnl.writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        if (marshall.length <= 131072) {
            return zzb(1, marshall);
        }
        zzayp().zzbaw().log("User property too long for local database. Sending directly to service");
        return false;
    }

    public final /* bridge */ /* synthetic */ void zzaxz() {
        super.zzaxz();
    }

    public final /* bridge */ /* synthetic */ void zzaya() {
        super.zzaya();
    }

    public final /* bridge */ /* synthetic */ zzcia zzayb() {
        return super.zzayb();
    }

    public final /* bridge */ /* synthetic */ zzcih zzayc() {
        return super.zzayc();
    }

    public final /* bridge */ /* synthetic */ zzclk zzayd() {
        return super.zzayd();
    }

    public final /* bridge */ /* synthetic */ zzcje zzaye() {
        return super.zzaye();
    }

    public final /* bridge */ /* synthetic */ zzcir zzayf() {
        return super.zzayf();
    }

    public final /* bridge */ /* synthetic */ zzcme zzayg() {
        return super.zzayg();
    }

    public final /* bridge */ /* synthetic */ zzcma zzayh() {
        return super.zzayh();
    }

    public final /* bridge */ /* synthetic */ zzcjf zzayi() {
        return super.zzayi();
    }

    public final /* bridge */ /* synthetic */ zzcil zzayj() {
        return super.zzayj();
    }

    public final /* bridge */ /* synthetic */ zzcjh zzayk() {
        return super.zzayk();
    }

    public final /* bridge */ /* synthetic */ zzcno zzayl() {
        return super.zzayl();
    }

    public final /* bridge */ /* synthetic */ zzckd zzaym() {
        return super.zzaym();
    }

    public final /* bridge */ /* synthetic */ zzcnd zzayn() {
        return super.zzayn();
    }

    public final /* bridge */ /* synthetic */ zzcke zzayo() {
        return super.zzayo();
    }

    public final /* bridge */ /* synthetic */ zzcjj zzayp() {
        return super.zzayp();
    }

    public final /* bridge */ /* synthetic */ zzcju zzayq() {
        return super.zzayq();
    }

    public final /* bridge */ /* synthetic */ zzcik zzayr() {
        return super.zzayr();
    }

    protected final boolean zzazq() {
        return false;
    }

    public final boolean zzc(zzcii zzcii) {
        zzayl();
        byte[] zza = zzcno.zza((Parcelable) zzcii);
        if (zza.length <= 131072) {
            return zzb(2, zza);
        }
        zzayp().zzbaw().log("Conditional user property too long for local database. Sending directly to service");
        return false;
    }

    public final List<zzbgl> zzep(int i) {
        SQLiteDatabase sQLiteDatabase;
        Cursor cursor;
        Cursor cursor2;
        int i2;
        SQLiteException sQLiteException;
        Throwable th;
        Object obj;
        Throwable th2;
        zzwj();
        if (this.zzjkb) {
            return null;
        }
        List<zzbgl> arrayList = new ArrayList();
        if (!getContext().getDatabasePath("google_app_measurement_local.db").exists()) {
            return arrayList;
        }
        int i3 = 5;
        int i4 = 0;
        while (i4 < 5) {
            SQLiteDatabase sQLiteDatabase2 = null;
            try {
                SQLiteDatabase writableDatabase = getWritableDatabase();
                if (writableDatabase == null) {
                    try {
                        this.zzjkb = true;
                        if (writableDatabase != null) {
                            writableDatabase.close();
                        }
                        return null;
                    } catch (SQLiteFullException e) {
                        sQLiteDatabase = writableDatabase;
                        SQLiteFullException sQLiteFullException = e;
                        cursor = null;
                    } catch (SQLiteDatabaseLockedException e2) {
                        cursor2 = null;
                        sQLiteDatabase2 = writableDatabase;
                        SystemClock.sleep((long) i3);
                        i2 = i3 + 20;
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        if (sQLiteDatabase2 != null) {
                            sQLiteDatabase2.close();
                        }
                        i4++;
                        i3 = i2;
                    } catch (SQLiteException e3) {
                        cursor2 = null;
                        sQLiteException = e3;
                        sQLiteDatabase2 = writableDatabase;
                        SQLiteException sQLiteException2 = sQLiteException;
                        if (sQLiteDatabase2 != null) {
                            try {
                                if (sQLiteDatabase2.inTransaction()) {
                                    sQLiteDatabase2.endTransaction();
                                }
                            } catch (Throwable th3) {
                                th = th3;
                            }
                        }
                        zzayp().zzbau().zzj("Error reading entries from local database", obj);
                        this.zzjkb = true;
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        if (sQLiteDatabase2 != null) {
                            sQLiteDatabase2.close();
                            i2 = i3;
                            i4++;
                            i3 = i2;
                        }
                        i2 = i3;
                        i4++;
                        i3 = i2;
                    } catch (Throwable th4) {
                        cursor2 = null;
                        th2 = th4;
                        sQLiteDatabase2 = writableDatabase;
                        th = th2;
                    }
                } else {
                    writableDatabase.beginTransaction();
                    cursor2 = writableDatabase.query("messages", new String[]{"rowid", Param.TYPE, "entry"}, null, null, null, null, "rowid asc", Integer.toString(100));
                    long j = -1;
                    while (cursor2.moveToNext()) {
                        try {
                            j = cursor2.getLong(0);
                            int i5 = cursor2.getInt(1);
                            byte[] blob = cursor2.getBlob(2);
                            if (i5 == 0) {
                                Parcel obtain = Parcel.obtain();
                                try {
                                    obtain.unmarshall(blob, 0, blob.length);
                                    obtain.setDataPosition(0);
                                    zzcix zzcix = (zzcix) zzcix.CREATOR.createFromParcel(obtain);
                                    obtain.recycle();
                                    if (zzcix != null) {
                                        arrayList.add(zzcix);
                                    }
                                } catch (zzbgn e4) {
                                    zzayp().zzbau().log("Failed to load event from local database");
                                    obtain.recycle();
                                } catch (Throwable th42) {
                                    obtain.recycle();
                                    throw th42;
                                }
                            } else if (i5 == 1) {
                                r7 = Parcel.obtain();
                                try {
                                    r7.unmarshall(blob, 0, blob.length);
                                    r7.setDataPosition(0);
                                    r1 = (zzcnl) zzcnl.CREATOR.createFromParcel(r7);
                                    r7.recycle();
                                } catch (zzbgn e5) {
                                    zzayp().zzbau().log("Failed to load user property from local database");
                                    r7.recycle();
                                    r1 = null;
                                } catch (Throwable th422) {
                                    r7.recycle();
                                    throw th422;
                                }
                                if (r1 != null) {
                                    arrayList.add(r1);
                                }
                            } else if (i5 == 2) {
                                r7 = Parcel.obtain();
                                try {
                                    r7.unmarshall(blob, 0, blob.length);
                                    r7.setDataPosition(0);
                                    r1 = (zzcii) zzcii.CREATOR.createFromParcel(r7);
                                    r7.recycle();
                                } catch (zzbgn e6) {
                                    zzayp().zzbau().log("Failed to load user property from local database");
                                    r7.recycle();
                                    r1 = null;
                                } catch (Throwable th4222) {
                                    r7.recycle();
                                    throw th4222;
                                }
                                if (r1 != null) {
                                    arrayList.add(r1);
                                }
                            } else {
                                zzayp().zzbau().log("Unknown record type in local database");
                            }
                        } catch (SQLiteFullException e7) {
                            SQLiteFullException sQLiteFullException2 = e7;
                            cursor = cursor2;
                            sQLiteDatabase = writableDatabase;
                            obj = sQLiteFullException2;
                        } catch (SQLiteDatabaseLockedException e8) {
                            sQLiteDatabase2 = writableDatabase;
                        } catch (SQLiteException e32) {
                            sQLiteException = e32;
                            sQLiteDatabase2 = writableDatabase;
                            obj = sQLiteException;
                        } catch (Throwable th42222) {
                            th2 = th42222;
                            sQLiteDatabase2 = writableDatabase;
                            th = th2;
                        }
                    }
                    if (writableDatabase.delete("messages", "rowid <= ?", new String[]{Long.toString(j)}) < arrayList.size()) {
                        zzayp().zzbau().log("Fewer entries removed from local database than expected");
                    }
                    writableDatabase.setTransactionSuccessful();
                    writableDatabase.endTransaction();
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    if (writableDatabase != null) {
                        writableDatabase.close();
                    }
                    return arrayList;
                }
            } catch (SQLiteFullException e9) {
                obj = e9;
                sQLiteDatabase = null;
                cursor = null;
                try {
                    zzayp().zzbau().zzj("Error reading entries from local database", obj);
                    this.zzjkb = true;
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (sQLiteDatabase != null) {
                        sQLiteDatabase.close();
                        i2 = i3;
                        i4++;
                        i3 = i2;
                    }
                    i2 = i3;
                    i4++;
                    i3 = i2;
                } catch (Throwable th5) {
                    th = th5;
                    Cursor cursor3 = cursor;
                    sQLiteDatabase2 = sQLiteDatabase;
                    cursor2 = cursor3;
                }
            } catch (SQLiteDatabaseLockedException e10) {
                cursor2 = null;
                SystemClock.sleep((long) i3);
                i2 = i3 + 20;
                if (cursor2 != null) {
                    cursor2.close();
                }
                if (sQLiteDatabase2 != null) {
                    sQLiteDatabase2.close();
                }
                i4++;
                i3 = i2;
            } catch (SQLiteException e11) {
                obj = e11;
                cursor2 = null;
                if (sQLiteDatabase2 != null) {
                    if (sQLiteDatabase2.inTransaction()) {
                        sQLiteDatabase2.endTransaction();
                    }
                }
                zzayp().zzbau().zzj("Error reading entries from local database", obj);
                this.zzjkb = true;
                if (cursor2 != null) {
                    cursor2.close();
                }
                if (sQLiteDatabase2 != null) {
                    sQLiteDatabase2.close();
                    i2 = i3;
                    i4++;
                    i3 = i2;
                }
                i2 = i3;
                i4++;
                i3 = i2;
            } catch (Throwable th6) {
                th = th6;
                cursor2 = null;
            }
        }
        zzayp().zzbaw().log("Failed to read events from database in reasonable time");
        return null;
        if (cursor2 != null) {
            cursor2.close();
        }
        if (sQLiteDatabase2 != null) {
            sQLiteDatabase2.close();
        }
        throw th;
    }

    public final /* bridge */ /* synthetic */ void zzwj() {
        super.zzwj();
    }

    public final /* bridge */ /* synthetic */ zze zzxx() {
        return super.zzxx();
    }
}
