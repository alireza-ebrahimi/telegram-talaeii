package org.telegram.tgnet;

public class TLRPC$TL_messages_uploadMedia extends TLObject {
    public static int constructor = 1369162417;
    public TLRPC$InputMedia media;
    public TLRPC$InputPeer peer;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$MessageMedia.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
        this.media.serializeToStream(stream);
    }
}
