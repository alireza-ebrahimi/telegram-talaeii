package org.telegram.tgnet;

public class TLRPC$TL_help_setBotUpdatesStatus extends TLObject {
    public static int constructor = -333262899;
    public String message;
    public int pending_updates_count;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.pending_updates_count);
        stream.writeString(this.message);
    }
}
