package org.telegram.tgnet;

public abstract class TLRPC$PhoneCallDiscardReason extends TLObject {
    public byte[] encrypted_key;

    public static TLRPC$PhoneCallDiscardReason TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$PhoneCallDiscardReason result = null;
        switch (constructor) {
            case -2048646399:
                result = new TLRPC$TL_phoneCallDiscardReasonMissed();
                break;
            case -1344096199:
                result = new TLRPC$TL_phoneCallDiscardReasonAllowGroupCall();
                break;
            case -527056480:
                result = new TLRPC$TL_phoneCallDiscardReasonDisconnect();
                break;
            case -84416311:
                result = new TLRPC$TL_phoneCallDiscardReasonBusy();
                break;
            case 1471006352:
                result = new TLRPC$TL_phoneCallDiscardReasonHangup();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in PhoneCallDiscardReason", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
