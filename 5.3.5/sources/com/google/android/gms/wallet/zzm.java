package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;

@Hide
public final class zzm implements Creator<FullWalletRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        Cart cart = null;
        int zzd = zzbgm.zzd(parcel);
        String str = null;
        String str2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    str2 = zzbgm.zzq(parcel, readInt);
                    break;
                case 3:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                case 4:
                    cart = (Cart) zzbgm.zza(parcel, readInt, Cart.CREATOR);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new FullWalletRequest(str2, str, cart);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new FullWalletRequest[i];
    }
}
