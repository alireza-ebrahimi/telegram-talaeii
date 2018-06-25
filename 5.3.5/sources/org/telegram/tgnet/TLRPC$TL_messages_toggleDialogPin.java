package org.telegram.tgnet;

public class TLRPC$TL_messages_toggleDialogPin extends TLObject {
    public static int constructor = 847887978;
    public int flags;
    public TLRPC$InputPeer peer;
    public boolean pinned;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.pinned ? this.flags | 1 : this.flags & -2;
        stream.writeInt32(this.flags);
        this.peer.serializeToStream(stream);
    }
}
