package org.telegram.tgnet;

public class TLRPC$TL_geochats_sendMessage extends TLObject {
    public static int constructor = 102432836;
    public String message;
    public TLRPC$TL_inputGeoChat peer;
    public long random_id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_geochats_statedMessage.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
        stream.writeString(this.message);
        stream.writeInt64(this.random_id);
    }
}
