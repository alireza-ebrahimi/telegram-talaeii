package com.google.android.gms.signin.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.VersionField;

@Class(creator = "RecordConsentRequestCreator")
public class RecordConsentRequest extends AbstractSafeParcelable {
    public static final Creator<RecordConsentRequest> CREATOR = new RecordConsentRequestCreator();
    @Field(getter = "getScopesToConsent", id = 3)
    private final Scope[] zzadr;
    @VersionField(id = 1)
    private final int zzal;
    @Field(getter = "getAccount", id = 2)
    private final Account zzs;
    @Field(getter = "getServerClientId", id = 4)
    private final String zzw;

    @Constructor
    RecordConsentRequest(@Param(id = 1) int i, @Param(id = 2) Account account, @Param(id = 3) Scope[] scopeArr, @Param(id = 4) String str) {
        this.zzal = i;
        this.zzs = account;
        this.zzadr = scopeArr;
        this.zzw = str;
    }

    public RecordConsentRequest(Account account, Scope[] scopeArr, String str) {
        this(1, account, scopeArr, str);
    }

    public Account getAccount() {
        return this.zzs;
    }

    public Scope[] getScopesToConsent() {
        return this.zzadr;
    }

    public String getServerClientId() {
        return this.zzw;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zzal);
        SafeParcelWriter.writeParcelable(parcel, 2, getAccount(), i, false);
        SafeParcelWriter.writeTypedArray(parcel, 3, getScopesToConsent(), i, false);
        SafeParcelWriter.writeString(parcel, 4, getServerClientId(), false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
