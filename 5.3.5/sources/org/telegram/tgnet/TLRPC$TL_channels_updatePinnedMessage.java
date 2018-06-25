package org.telegram.tgnet;

public class TLRPC$TL_channels_updatePinnedMessage extends TLObject {
    public static int constructor = -1490162350;
    public TLRPC$InputChannel channel;
    public int flags;
    public int id;
    public boolean silent;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.silent ? this.flags | 1 : this.flags & -2;
        stream.writeInt32(this.flags);
        this.channel.serializeToStream(stream);
        stream.writeInt32(this.id);
    }
}
