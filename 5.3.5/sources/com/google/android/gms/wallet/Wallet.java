package com.google.android.gms.wallet;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.HasAccountOptions;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzm;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.internal.zzdlw;
import com.google.android.gms.internal.zzdmo;
import com.google.android.gms.internal.zzdmv;
import com.google.android.gms.internal.zzdnd;
import com.google.android.gms.internal.zzdne;
import com.google.android.gms.wallet.wobs.WalletObjects;
import java.util.Arrays;
import java.util.Locale;

public final class Wallet {
    public static final Api<WalletOptions> API = new Api("Wallet.API", zzegv, zzegu);
    @Deprecated
    public static final Payments Payments = new zzdmo();
    private static final zzf<zzdmv> zzegu = new zzf();
    private static final com.google.android.gms.common.api.Api.zza<zzdmv, WalletOptions> zzegv = new zzap();
    @Hide
    private static WalletObjects zzloa = new zzdne();
    @Hide
    private static zzdlw zzlob = new zzdnd();

    @Hide
    public static abstract class zza<R extends Result> extends zzm<R, zzdmv> {
        public zza(GoogleApiClient googleApiClient) {
            super(Wallet.API, googleApiClient);
        }

        @Hide
        public final /* bridge */ /* synthetic */ void setResult(Object obj) {
            super.setResult((Result) obj);
        }

        @VisibleForTesting
        protected abstract void zza(zzdmv zzdmv) throws RemoteException;
    }

    @Hide
    public static abstract class zzb extends zza<Status> {
        public zzb(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        protected final /* synthetic */ Result zzb(Status status) {
            return status;
        }
    }

    public static final class WalletOptions implements HasAccountOptions {
        @Hide
        private Account account;
        public final int environment;
        public final int theme;
        @VisibleForTesting
        final boolean zzloc;

        public static final class Builder {
            private int mTheme = 0;
            private int zzlod = 3;
            private boolean zzloe = true;

            public final WalletOptions build() {
                return new WalletOptions();
            }

            public final Builder setEnvironment(int i) {
                if (i == 0 || i == 0 || i == 2 || i == 1 || i == 3) {
                    this.zzlod = i;
                    return this;
                }
                throw new IllegalArgumentException(String.format(Locale.US, "Invalid environment value %d", new Object[]{Integer.valueOf(i)}));
            }

            public final Builder setTheme(int i) {
                if (i == 0 || i == 1) {
                    this.mTheme = i;
                    return this;
                }
                throw new IllegalArgumentException(String.format(Locale.US, "Invalid theme value %d", new Object[]{Integer.valueOf(i)}));
            }

            @Deprecated
            public final Builder useGoogleWallet() {
                this.zzloe = false;
                return this;
            }
        }

        private WalletOptions() {
            this(new Builder());
        }

        private WalletOptions(Builder builder) {
            this.environment = builder.zzlod;
            this.theme = builder.mTheme;
            this.zzloc = builder.zzloe;
            this.account = null;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof WalletOptions)) {
                return false;
            }
            WalletOptions walletOptions = (WalletOptions) obj;
            return zzbg.equal(Integer.valueOf(this.environment), Integer.valueOf(walletOptions.environment)) && zzbg.equal(Integer.valueOf(this.theme), Integer.valueOf(walletOptions.theme)) && zzbg.equal(null, null) && zzbg.equal(Boolean.valueOf(this.zzloc), Boolean.valueOf(walletOptions.zzloc));
        }

        public final Account getAccount() {
            return null;
        }

        public final int hashCode() {
            return Arrays.hashCode(new Object[]{Integer.valueOf(this.environment), Integer.valueOf(this.theme), null, Boolean.valueOf(this.zzloc)});
        }
    }

    private Wallet() {
    }

    public static PaymentsClient getPaymentsClient(@NonNull Activity activity, @NonNull WalletOptions walletOptions) {
        return new PaymentsClient(activity, walletOptions);
    }

    public static PaymentsClient getPaymentsClient(@NonNull Context context, @NonNull WalletOptions walletOptions) {
        return new PaymentsClient(context, walletOptions);
    }

    public static WalletObjectsClient getWalletObjectsClient(@NonNull Activity activity, @Nullable WalletOptions walletOptions) {
        return new WalletObjectsClient(activity, walletOptions);
    }
}
