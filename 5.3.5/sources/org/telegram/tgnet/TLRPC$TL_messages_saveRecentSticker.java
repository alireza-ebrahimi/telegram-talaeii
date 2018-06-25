package org.telegram.tgnet;

public class TLRPC$TL_messages_saveRecentSticker extends TLObject {
    public static int constructor = 958863608;
    public boolean attached;
    public int flags;
    public TLRPC$InputDocument id;
    public boolean unsave;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.attached ? this.flags | 1 : this.flags & -2;
        stream.writeInt32(this.flags);
        this.id.serializeToStream(stream);
        stream.writeBool(this.unsave);
    }
}
