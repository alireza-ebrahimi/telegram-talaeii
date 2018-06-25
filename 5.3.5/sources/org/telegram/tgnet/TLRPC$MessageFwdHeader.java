package org.telegram.tgnet;

public abstract class TLRPC$MessageFwdHeader extends TLObject {
    public int channel_id;
    public int channel_post;
    public int date;
    public int flags;
    public int from_id;
    public String post_author;
    public int saved_from_msg_id;
    public TLRPC$Peer saved_from_peer;

    public static TLRPC$MessageFwdHeader TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$MessageFwdHeader result = null;
        switch (constructor) {
            case -947462709:
                result = new TLRPC$TL_messageFwdHeader_layer68();
                break;
            case -85986132:
                result = new TLRPC$TL_messageFwdHeader_layer72();
                break;
            case 1436466797:
                result = new TLRPC$TL_messageFwdHeader();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in MessageFwdHeader", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
