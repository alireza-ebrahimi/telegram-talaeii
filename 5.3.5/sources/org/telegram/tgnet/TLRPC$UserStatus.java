package org.telegram.tgnet;

public abstract class TLRPC$UserStatus extends TLObject {
    public int expires;

    public static TLRPC$UserStatus TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$UserStatus result = null;
        switch (constructor) {
            case -496024847:
                result = new TLRPC$TL_userStatusRecently();
                break;
            case -306628279:
                result = new TLRPC$TL_userStatusOnline();
                break;
            case 9203775:
                result = new TLRPC$TL_userStatusOffline();
                break;
            case 129960444:
                result = new TLRPC$TL_userStatusLastWeek();
                break;
            case 164646985:
                result = new TLRPC$TL_userStatusEmpty();
                break;
            case 2011940674:
                result = new TLRPC$TL_userStatusLastMonth();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in UserStatus", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
