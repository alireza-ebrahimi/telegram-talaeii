package org.telegram.tgnet;

public class TLRPC$TL_updateContactLink extends TLRPC$Update {
    public static int constructor = -1657903163;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.user_id = stream.readInt32(exception);
        this.my_link = TLRPC$ContactLink.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.foreign_link = TLRPC$ContactLink.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.user_id);
        this.my_link.serializeToStream(stream);
        this.foreign_link.serializeToStream(stream);
    }
}
