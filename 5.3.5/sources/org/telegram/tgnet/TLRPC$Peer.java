package org.telegram.tgnet;

public class TLRPC$Peer extends TLObject {
    public int channel_id;
    public int chat_id;
    public int user_id;

    public static TLRPC$Peer TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$Peer result = null;
        switch (constructor) {
            case -1649296275:
                result = new TLRPC$TL_peerUser();
                break;
            case -1160714821:
                result = new TLRPC$TL_peerChat();
                break;
            case -1109531342:
                result = new TLRPC$TL_peerChannel();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in Peer", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
