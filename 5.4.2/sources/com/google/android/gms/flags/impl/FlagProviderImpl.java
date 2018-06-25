package com.google.android.gms.flags.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.flags.IFlagProvider.Stub;
import com.google.android.gms.flags.impl.DataUtils.BooleanUtils;
import com.google.android.gms.flags.impl.DataUtils.IntegerUtils;
import com.google.android.gms.flags.impl.DataUtils.LongUtils;
import com.google.android.gms.flags.impl.DataUtils.StringUtils;

@DynamiteApi
public class FlagProviderImpl extends Stub {
    private boolean zzacf = false;
    private SharedPreferences zzacu;

    public boolean getBooleanFlagValue(String str, boolean z, int i) {
        return !this.zzacf ? z : BooleanUtils.getFromSharedPreferencesNoStrict(this.zzacu, str, Boolean.valueOf(z)).booleanValue();
    }

    public int getIntFlagValue(String str, int i, int i2) {
        return !this.zzacf ? i : IntegerUtils.getFromSharedPreferencesNoStrict(this.zzacu, str, Integer.valueOf(i)).intValue();
    }

    public long getLongFlagValue(String str, long j, int i) {
        return !this.zzacf ? j : LongUtils.getFromSharedPreferencesNoStrict(this.zzacu, str, Long.valueOf(j)).longValue();
    }

    public String getStringFlagValue(String str, String str2, int i) {
        return !this.zzacf ? str2 : StringUtils.getFromSharedPreferencesNoStrict(this.zzacu, str, str2);
    }

    public void init(IObjectWrapper iObjectWrapper) {
        Context context = (Context) ObjectWrapper.unwrap(iObjectWrapper);
        if (!this.zzacf) {
            try {
                this.zzacu = SharedPreferencesFactory.getSharedPreferences(context.createPackageContext("com.google.android.gms", 0));
                this.zzacf = true;
            } catch (NameNotFoundException e) {
            } catch (Exception e2) {
                String str = "FlagProviderImpl";
                String str2 = "Could not retrieve sdk flags, continuing with defaults: ";
                String valueOf = String.valueOf(e2.getMessage());
                Log.w(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            }
        }
    }
}
