package org.telegram.tgnet;

public class TLRPC$TL_messages_saveGif extends TLObject {
    public static int constructor = 846868683;
    public TLRPC$InputDocument id;
    public boolean unsave;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.id.serializeToStream(stream);
        stream.writeBool(this.unsave);
    }
}
