package com.google.android.gms.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import com.google.android.gms.identity.intents.model.UserAddress;

@Class(creator = "PaymentDataCreator")
public final class PaymentData extends AbstractSafeParcelable implements AutoResolvableResult {
    public static final Creator<PaymentData> CREATOR = new zzac();
    @Field(id = 5)
    private String zzaw;
    @Field(id = 1)
    private String zzaz;
    @Field(id = 4)
    private PaymentMethodToken zzbg;
    @Field(id = 7)
    private String zzby;
    @Field(id = 2)
    private CardInfo zzds;
    @Field(id = 3)
    private UserAddress zzdt;
    @Field(id = 6)
    private Bundle zzdu;

    private PaymentData() {
    }

    @Constructor
    PaymentData(@Param(id = 1) String str, @Param(id = 2) CardInfo cardInfo, @Param(id = 3) UserAddress userAddress, @Param(id = 4) PaymentMethodToken paymentMethodToken, @Param(id = 5) String str2, @Param(id = 6) Bundle bundle, @Param(id = 7) String str3) {
        this.zzaz = str;
        this.zzds = cardInfo;
        this.zzdt = userAddress;
        this.zzbg = paymentMethodToken;
        this.zzaw = str2;
        this.zzdu = bundle;
        this.zzby = str3;
    }

    public static PaymentData getFromIntent(Intent intent) {
        return (PaymentData) SafeParcelableSerializer.deserializeFromIntentExtra(intent, "com.google.android.gms.wallet.PaymentData", CREATOR);
    }

    public final CardInfo getCardInfo() {
        return this.zzds;
    }

    public final String getEmail() {
        return this.zzaz;
    }

    public final Bundle getExtraData() {
        return this.zzdu;
    }

    public final String getGoogleTransactionId() {
        return this.zzaw;
    }

    public final PaymentMethodToken getPaymentMethodToken() {
        return this.zzbg;
    }

    public final UserAddress getShippingAddress() {
        return this.zzdt;
    }

    public final void putIntoIntent(Intent intent) {
        SafeParcelableSerializer.serializeToIntentExtra(this, intent, "com.google.android.gms.wallet.PaymentData");
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzaz, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzds, i, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzdt, i, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzbg, i, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzaw, false);
        SafeParcelWriter.writeBundle(parcel, 6, this.zzdu, false);
        SafeParcelWriter.writeString(parcel, 7, this.zzby, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
