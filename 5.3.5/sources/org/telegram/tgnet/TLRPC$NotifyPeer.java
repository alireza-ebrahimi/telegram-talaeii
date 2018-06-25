package org.telegram.tgnet;

public abstract class TLRPC$NotifyPeer extends TLObject {
    public TLRPC$Peer peer;

    public static TLRPC$NotifyPeer TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$NotifyPeer result = null;
        switch (constructor) {
            case -1613493288:
                result = new TLRPC$TL_notifyPeer();
                break;
            case -1261946036:
                result = new TLRPC$TL_notifyUsers();
                break;
            case -1073230141:
                result = new TLRPC$TL_notifyChats();
                break;
            case 1959820384:
                result = new TLRPC$TL_notifyAll();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in NotifyPeer", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
