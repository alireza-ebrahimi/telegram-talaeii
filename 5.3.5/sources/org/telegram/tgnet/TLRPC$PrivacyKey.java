package org.telegram.tgnet;

public abstract class TLRPC$PrivacyKey extends TLObject {
    public static TLRPC$PrivacyKey TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$PrivacyKey result = null;
        switch (constructor) {
            case -1137792208:
                result = new TLRPC$TL_privacyKeyStatusTimestamp();
                break;
            case 1030105979:
                result = new TLRPC$TL_privacyKeyPhoneCall();
                break;
            case 1343122938:
                result = new TLRPC$TL_privacyKeyChatInvite();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in PrivacyKey", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
