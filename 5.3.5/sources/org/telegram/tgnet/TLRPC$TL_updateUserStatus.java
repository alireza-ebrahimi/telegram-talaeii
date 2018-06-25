package org.telegram.tgnet;

public class TLRPC$TL_updateUserStatus extends TLRPC$Update {
    public static int constructor = 469489699;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.user_id = stream.readInt32(exception);
        this.status = TLRPC$UserStatus.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.user_id);
        this.status.serializeToStream(stream);
    }
}
