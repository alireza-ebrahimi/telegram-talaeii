package org.telegram.tgnet;

public class TLRPC$TL_messages_readEncryptedHistory extends TLObject {
    public static int constructor = 2135648522;
    public int max_date;
    public TLRPC$TL_inputEncryptedChat peer;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
        stream.writeInt32(this.max_date);
    }
}
