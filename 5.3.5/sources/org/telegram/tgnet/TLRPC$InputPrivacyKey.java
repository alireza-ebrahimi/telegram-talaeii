package org.telegram.tgnet;

public abstract class TLRPC$InputPrivacyKey extends TLObject {
    public static TLRPC$InputPrivacyKey TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$InputPrivacyKey result = null;
        switch (constructor) {
            case -1107622874:
                result = new TLRPC$TL_inputPrivacyKeyChatInvite();
                break;
            case -88417185:
                result = new TLRPC$TL_inputPrivacyKeyPhoneCall();
                break;
            case 1335282456:
                result = new TLRPC$TL_inputPrivacyKeyStatusTimestamp();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in InputPrivacyKey", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
