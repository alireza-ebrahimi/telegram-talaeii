package org.telegram.tgnet;

public class TLRPC$TL_geochats_editChatPhoto extends TLObject {
    public static int constructor = 903355029;
    public TLRPC$TL_inputGeoChat peer;
    public TLRPC$InputChatPhoto photo;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_geochats_statedMessage.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
        this.photo.serializeToStream(stream);
    }
}
