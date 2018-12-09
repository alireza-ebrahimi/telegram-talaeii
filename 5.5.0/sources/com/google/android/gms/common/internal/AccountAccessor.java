package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Binder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.internal.IAccountAccessor.Stub;

public class AccountAccessor extends Stub {
    private Context mContext;
    private int zzqu = -1;
    private Account zzs;

    public AccountAccessor(Context context, Account account) {
        this.mContext = context.getApplicationContext();
        this.zzs = account;
    }

    public static AccountAccessor fromGoogleAccountName(Context context, String str) {
        return new AccountAccessor(context, TextUtils.isEmpty(str) ? null : new Account(str, AccountType.GOOGLE));
    }

    public static Account getAccountBinderSafe(IAccountAccessor iAccountAccessor) {
        Account account = null;
        if (iAccountAccessor != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                account = iAccountAccessor.getAccount();
            } catch (RemoteException e) {
                Log.w("AccountAccessor", "Remote account accessor probably died");
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return account;
    }

    public boolean equals(Object obj) {
        return this == obj ? true : !(obj instanceof AccountAccessor) ? false : this.zzs.equals(((AccountAccessor) obj).zzs);
    }

    public Account getAccount() {
        int callingUid = Binder.getCallingUid();
        if (callingUid == this.zzqu) {
            return this.zzs;
        }
        if (GooglePlayServicesUtilLight.isGooglePlayServicesUid(this.mContext, callingUid)) {
            this.zzqu = callingUid;
            return this.zzs;
        }
        throw new SecurityException("Caller is not GooglePlayServices");
    }
}
