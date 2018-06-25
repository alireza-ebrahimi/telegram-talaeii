package com.google.android.gms.common;

import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzat;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import org.apache.commons.lang3.CharEncoding;

@Hide
abstract class zzh extends zzau {
    private int zzfrd;

    protected zzh(byte[] bArr) {
        zzbq.checkArgument(bArr.length == 25);
        this.zzfrd = Arrays.hashCode(bArr);
    }

    protected static byte[] zzgf(String str) {
        try {
            return str.getBytes(CharEncoding.ISO_8859_1);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof zzat)) {
            return false;
        }
        try {
            zzat zzat = (zzat) obj;
            if (zzat.zzahh() != hashCode()) {
                return false;
            }
            IObjectWrapper zzahg = zzat.zzahg();
            if (zzahg == null) {
                return false;
            }
            return Arrays.equals(getBytes(), (byte[]) zzn.zzy(zzahg));
        } catch (Throwable e) {
            Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
            return false;
        }
    }

    abstract byte[] getBytes();

    public int hashCode() {
        return this.zzfrd;
    }

    public final IObjectWrapper zzahg() {
        return zzn.zzz(getBytes());
    }

    public final int zzahh() {
        return hashCode();
    }
}
