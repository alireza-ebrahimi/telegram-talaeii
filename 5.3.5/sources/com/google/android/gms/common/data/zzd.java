package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.data.DataHolder.zza;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgp;

@Hide
public class zzd<T extends zzbgp> extends AbstractDataBuffer<T> {
    private static final String[] zzgcj = new String[]{"data"};
    private final Creator<T> zzgck;

    public zzd(DataHolder dataHolder, Creator<T> creator) {
        super(dataHolder);
        this.zzgck = creator;
    }

    public static <T extends zzbgp> void zza(zza zza, T t) {
        Parcel obtain = Parcel.obtain();
        t.writeToParcel(obtain, 0);
        ContentValues contentValues = new ContentValues();
        contentValues.put("data", obtain.marshall());
        zza.zza(contentValues);
        obtain.recycle();
    }

    public static zza zzalh() {
        return DataHolder.zzb(zzgcj);
    }

    public /* synthetic */ Object get(int i) {
        return zzbx(i);
    }

    public T zzbx(int i) {
        byte[] zzg = this.zzfxb.zzg("data", i, this.zzfxb.zzby(i));
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(zzg, 0, zzg.length);
        obtain.setDataPosition(0);
        zzbgp zzbgp = (zzbgp) this.zzgck.createFromParcel(obtain);
        obtain.recycle();
        return zzbgp;
    }
}
