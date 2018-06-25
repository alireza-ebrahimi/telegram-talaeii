package org.telegram.tgnet;

public abstract class TLRPC$PeerNotifyEvents extends TLObject {
    public static TLRPC$PeerNotifyEvents TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$PeerNotifyEvents result = null;
        switch (constructor) {
            case -1378534221:
                result = new TLRPC$TL_peerNotifyEventsEmpty();
                break;
            case 1830677896:
                result = new TLRPC$TL_peerNotifyEventsAll();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in PeerNotifyEvents", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
