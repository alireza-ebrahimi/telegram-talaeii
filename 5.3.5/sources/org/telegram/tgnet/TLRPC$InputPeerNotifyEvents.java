package org.telegram.tgnet;

public abstract class TLRPC$InputPeerNotifyEvents extends TLObject {
    public static TLRPC$InputPeerNotifyEvents TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$InputPeerNotifyEvents result = null;
        switch (constructor) {
            case -395694988:
                result = new TLRPC$TL_inputPeerNotifyEventsAll();
                break;
            case -265263912:
                result = new TLRPC$TL_inputPeerNotifyEventsEmpty();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in InputPeerNotifyEvents", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
