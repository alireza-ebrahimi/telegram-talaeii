package com.google.android.gms.internal.measurement;

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
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader.ParseException;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.measurement.AppMeasurement.Param;
import java.util.ArrayList;
import java.util.List;

public final class zzfd extends zzhi {
    private final zzfe zzaip = new zzfe(this, getContext(), "google_app_measurement_local.db");
    private boolean zzaiq;

    zzfd(zzgm zzgm) {
        super(zzgm);
    }

    @VisibleForTesting
    private final SQLiteDatabase getWritableDatabase() {
        if (this.zzaiq) {
            return null;
        }
        SQLiteDatabase writableDatabase = this.zzaip.getWritableDatabase();
        if (writableDatabase != null) {
            return writableDatabase;
        }
        this.zzaiq = true;
        return null;
    }

    private final boolean zza(int i, byte[] bArr) {
        zzfs();
        zzab();
        if (this.zzaiq) {
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
                    this.zzaiq = true;
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
                    zzgf().zzis().log("Data loss, local db full");
                    j = (100000 - j) + 1;
                    long delete = (long) sQLiteDatabase.delete("messages", "rowid in (select rowid from messages order by rowid asc limit ?)", new String[]{Long.toString(j)});
                    if (delete != j) {
                        zzgf().zzis().zzd("Different delete count than expected in local db. expected, received, difference", Long.valueOf(j), Long.valueOf(delete), Long.valueOf(j - delete));
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
                zzgf().zzis().zzg("Error writing entry to local database", e);
                this.zzaiq = true;
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
                zzgf().zzis().zzg("Error writing entry to local database", e3);
                this.zzaiq = true;
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
        zzgf().zziv().log("Failed to write entry to local database");
        return false;
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final void resetAnalyticsData() {
        zzfs();
        zzab();
        try {
            int delete = getWritableDatabase().delete("messages", null, null) + 0;
            if (delete > 0) {
                zzgf().zziz().zzg("Reset local analytics data. records", Integer.valueOf(delete));
            }
        } catch (SQLiteException e) {
            zzgf().zzis().zzg("Error resetting local analytics data. error", e);
        }
    }

    public final boolean zza(zzew zzew) {
        Parcel obtain = Parcel.obtain();
        zzew.writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        if (marshall.length <= 131072) {
            return zza(0, marshall);
        }
        zzgf().zziv().log("Event is too long for local database. Sending event directly to service");
        return false;
    }

    public final boolean zza(zzjz zzjz) {
        Parcel obtain = Parcel.obtain();
        zzjz.writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        if (marshall.length <= 131072) {
            return zza(1, marshall);
        }
        zzgf().zziv().log("User property too long for local database. Sending directly to service");
        return false;
    }

    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    public final /* bridge */ /* synthetic */ Clock zzbt() {
        return super.zzbt();
    }

    public final boolean zzc(zzee zzee) {
        zzgc();
        byte[] zza = zzkc.zza((Parcelable) zzee);
        if (zza.length <= 131072) {
            return zza(2, zza);
        }
        zzgf().zziv().log("Conditional user property too long for local database. Sending directly to service");
        return false;
    }

    public final /* bridge */ /* synthetic */ void zzfr() {
        super.zzfr();
    }

    public final /* bridge */ /* synthetic */ void zzfs() {
        super.zzfs();
    }

    public final /* bridge */ /* synthetic */ void zzft() {
        super.zzft();
    }

    public final /* bridge */ /* synthetic */ zzdu zzfu() {
        return super.zzfu();
    }

    public final /* bridge */ /* synthetic */ zzhl zzfv() {
        return super.zzfv();
    }

    public final /* bridge */ /* synthetic */ zzfc zzfw() {
        return super.zzfw();
    }

    public final /* bridge */ /* synthetic */ zzeq zzfx() {
        return super.zzfx();
    }

    public final /* bridge */ /* synthetic */ zzij zzfy() {
        return super.zzfy();
    }

    public final /* bridge */ /* synthetic */ zzig zzfz() {
        return super.zzfz();
    }

    public final /* bridge */ /* synthetic */ zzfd zzga() {
        return super.zzga();
    }

    public final /* bridge */ /* synthetic */ zzff zzgb() {
        return super.zzgb();
    }

    public final /* bridge */ /* synthetic */ zzkc zzgc() {
        return super.zzgc();
    }

    public final /* bridge */ /* synthetic */ zzji zzgd() {
        return super.zzgd();
    }

    public final /* bridge */ /* synthetic */ zzgh zzge() {
        return super.zzge();
    }

    public final /* bridge */ /* synthetic */ zzfh zzgf() {
        return super.zzgf();
    }

    public final /* bridge */ /* synthetic */ zzfs zzgg() {
        return super.zzgg();
    }

    public final /* bridge */ /* synthetic */ zzeg zzgh() {
        return super.zzgh();
    }

    public final /* bridge */ /* synthetic */ zzec zzgi() {
        return super.zzgi();
    }

    protected final boolean zzhh() {
        return false;
    }

    public final List<AbstractSafeParcelable> zzp(int i) {
        SQLiteDatabase sQLiteDatabase;
        Cursor cursor;
        int i2;
        SQLiteException sQLiteException;
        Throwable th;
        Object obj;
        Throwable th2;
        Parcel obtain;
        zzab();
        zzfs();
        if (this.zzaiq) {
            return null;
        }
        List<AbstractSafeParcelable> arrayList = new ArrayList();
        if (!getContext().getDatabasePath("google_app_measurement_local.db").exists()) {
            return arrayList;
        }
        int i3 = 5;
        int i4 = 0;
        while (i4 < 5) {
            Cursor cursor2;
            SQLiteDatabase sQLiteDatabase2 = null;
            try {
                SQLiteDatabase writableDatabase = getWritableDatabase();
                if (writableDatabase == null) {
                    try {
                        this.zzaiq = true;
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
                        zzgf().zzis().zzg("Error reading entries from local database", obj);
                        this.zzaiq = true;
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
                                Parcel obtain2 = Parcel.obtain();
                                try {
                                    obtain2.unmarshall(blob, 0, blob.length);
                                    obtain2.setDataPosition(0);
                                    zzew zzew = (zzew) zzew.CREATOR.createFromParcel(obtain2);
                                    obtain2.recycle();
                                    if (zzew != null) {
                                        arrayList.add(zzew);
                                    }
                                } catch (ParseException e4) {
                                    zzgf().zzis().log("Failed to load event from local database");
                                    obtain2.recycle();
                                } catch (Throwable th42) {
                                    obtain2.recycle();
                                    throw th42;
                                }
                            } else if (i5 == 1) {
                                obtain = Parcel.obtain();
                                try {
                                    obtain.unmarshall(blob, 0, blob.length);
                                    obtain.setDataPosition(0);
                                    r1 = (zzjz) zzjz.CREATOR.createFromParcel(obtain);
                                    obtain.recycle();
                                } catch (ParseException e5) {
                                    zzgf().zzis().log("Failed to load user property from local database");
                                    obtain.recycle();
                                    r1 = null;
                                } catch (Throwable th422) {
                                    obtain.recycle();
                                    throw th422;
                                }
                                if (r1 != null) {
                                    arrayList.add(r1);
                                }
                            } else if (i5 == 2) {
                                obtain = Parcel.obtain();
                                try {
                                    obtain.unmarshall(blob, 0, blob.length);
                                    obtain.setDataPosition(0);
                                    r1 = (zzee) zzee.CREATOR.createFromParcel(obtain);
                                    obtain.recycle();
                                } catch (ParseException e6) {
                                    zzgf().zzis().log("Failed to load user property from local database");
                                    obtain.recycle();
                                    r1 = null;
                                } catch (Throwable th4222) {
                                    obtain.recycle();
                                    throw th4222;
                                }
                                if (r1 != null) {
                                    arrayList.add(r1);
                                }
                            } else {
                                zzgf().zzis().log("Unknown record type in local database");
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
                        zzgf().zzis().log("Fewer entries removed from local database than expected");
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
                    zzgf().zzis().zzg("Error reading entries from local database", obj);
                    this.zzaiq = true;
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
                zzgf().zzis().zzg("Error reading entries from local database", obj);
                this.zzaiq = true;
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
        zzgf().zziv().log("Failed to read events from database in reasonable time");
        return null;
        if (cursor2 != null) {
            cursor2.close();
        }
        if (sQLiteDatabase2 != null) {
            sQLiteDatabase2.close();
        }
        throw th;
    }
}
