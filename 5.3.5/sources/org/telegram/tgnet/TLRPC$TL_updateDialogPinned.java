package org.telegram.tgnet;

public class TLRPC$TL_updateDialogPinned extends TLRPC$Update {
    public static int constructor = -686710068;
    public TLRPC$Peer peer;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.pinned = (this.flags & 1) != 0;
        this.peer = TLRPC$Peer.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.pinned ? this.flags | 1 : this.flags & -2;
        stream.writeInt32(this.flags);
        this.peer.serializeToStream(stream);
    }
}
