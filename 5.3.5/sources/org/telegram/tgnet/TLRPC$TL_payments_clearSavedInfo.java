package org.telegram.tgnet;

public class TLRPC$TL_payments_clearSavedInfo extends TLObject {
    public static int constructor = -667062079;
    public boolean credentials;
    public int flags;
    public boolean info;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.credentials ? this.flags | 1 : this.flags & -2;
        this.flags = this.info ? this.flags | 2 : this.flags & -3;
        stream.writeInt32(this.flags);
    }
}
