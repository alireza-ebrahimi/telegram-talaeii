package org.telegram.tgnet;

public abstract class TLRPC$InputPeer extends TLObject {
    public long access_hash;
    public int channel_id;
    public int chat_id;
    public int user_id;

    public static TLRPC$InputPeer TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$InputPeer result = null;
        switch (constructor) {
            case 396093539:
                result = new TLRPC$TL_inputPeerChat();
                break;
            case 548253432:
                result = new TLRPC$TL_inputPeerChannel();
                break;
            case 2072935910:
                result = new TLRPC$TL_inputPeerUser();
                break;
            case 2107670217:
                result = new TLRPC$TL_inputPeerSelf();
                break;
            case 2134579434:
                result = new TLRPC$TL_inputPeerEmpty();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in InputPeer", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
