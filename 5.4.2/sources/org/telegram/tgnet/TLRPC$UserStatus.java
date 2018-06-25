package org.telegram.tgnet;

public abstract class TLRPC$UserStatus extends TLObject {
    public int expires;

    public static TLRPC$UserStatus TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$UserStatus tLRPC$UserStatus = null;
        switch (i) {
            case -496024847:
                tLRPC$UserStatus = new TLRPC$TL_userStatusRecently();
                break;
            case -306628279:
                tLRPC$UserStatus = new TLRPC$TL_userStatusOnline();
                break;
            case 9203775:
                tLRPC$UserStatus = new TLRPC$TL_userStatusOffline();
                break;
            case 129960444:
                tLRPC$UserStatus = new TLRPC$TL_userStatusLastWeek();
                break;
            case 164646985:
                tLRPC$UserStatus = new TLRPC$TL_userStatusEmpty();
                break;
            case 2011940674:
                tLRPC$UserStatus = new TLRPC$TL_userStatusLastMonth();
                break;
        }
        if (tLRPC$UserStatus == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in UserStatus", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$UserStatus != null) {
            tLRPC$UserStatus.readParams(abstractSerializedData, z);
        }
        return tLRPC$UserStatus;
    }
}
