package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.IAccountAccessor.Stub;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.VersionField;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nullable;

@Class(creator = "AuthAccountRequestCreator")
public class AuthAccountRequest extends AbstractSafeParcelable {
    public static final Creator<AuthAccountRequest> CREATOR = new AuthAccountRequestCreator();
    @VersionField(id = 1)
    private final int zzal;
    @Field(id = 2)
    @Deprecated
    private final IBinder zzqv;
    @Field(id = 3)
    private final Scope[] zzqw;
    @Field(id = 4)
    private Integer zzqx;
    @Field(id = 5)
    private Integer zzqy;
    @Field(id = 6, type = "android.accounts.Account")
    private Account zzs;

    @Constructor
    AuthAccountRequest(@Param(id = 1) int i, @Param(id = 2) IBinder iBinder, @Param(id = 3) Scope[] scopeArr, @Param(id = 4) Integer num, @Param(id = 5) Integer num2, @Param(id = 6) Account account) {
        this.zzal = i;
        this.zzqv = iBinder;
        this.zzqw = scopeArr;
        this.zzqx = num;
        this.zzqy = num2;
        this.zzs = account;
    }

    public AuthAccountRequest(Account account, Set<Scope> set) {
        this(3, null, (Scope[]) set.toArray(new Scope[set.size()]), null, null, (Account) Preconditions.checkNotNull(account));
    }

    @Deprecated
    public AuthAccountRequest(IAccountAccessor iAccountAccessor, Set<Scope> set) {
        this(3, iAccountAccessor.asBinder(), (Scope[]) set.toArray(new Scope[set.size()]), null, null, null);
    }

    public Account getAccount() {
        return this.zzs != null ? this.zzs : this.zzqv != null ? AccountAccessor.getAccountBinderSafe(Stub.asInterface(this.zzqv)) : null;
    }

    @Nullable
    public Integer getOauthPolicy() {
        return this.zzqx;
    }

    @Nullable
    public Integer getPolicyAction() {
        return this.zzqy;
    }

    public Set<Scope> getScopes() {
        return new HashSet(Arrays.asList(this.zzqw));
    }

    public AuthAccountRequest setOauthPolicy(@Nullable Integer num) {
        this.zzqx = num;
        return this;
    }

    public AuthAccountRequest setPolicyAction(@Nullable Integer num) {
        this.zzqy = num;
        return this;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zzal);
        SafeParcelWriter.writeIBinder(parcel, 2, this.zzqv, false);
        SafeParcelWriter.writeTypedArray(parcel, 3, this.zzqw, i, false);
        SafeParcelWriter.writeIntegerObject(parcel, 4, this.zzqx, false);
        SafeParcelWriter.writeIntegerObject(parcel, 5, this.zzqy, false);
        SafeParcelWriter.writeParcelable(parcel, 6, this.zzs, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
