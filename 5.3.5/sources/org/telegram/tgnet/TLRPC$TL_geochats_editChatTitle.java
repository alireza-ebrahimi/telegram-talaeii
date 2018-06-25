package org.telegram.tgnet;

public class TLRPC$TL_geochats_editChatTitle extends TLObject {
    public static int constructor = 1284383347;
    public String address;
    public TLRPC$TL_inputGeoChat peer;
    public String title;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_geochats_statedMessage.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
        stream.writeString(this.title);
        stream.writeString(this.address);
    }
}
