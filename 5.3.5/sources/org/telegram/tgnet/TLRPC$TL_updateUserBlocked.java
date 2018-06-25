package org.telegram.tgnet;

public class TLRPC$TL_updateUserBlocked extends TLRPC$Update {
    public static int constructor = -2131957734;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.user_id = stream.readInt32(exception);
        this.blocked = stream.readBool(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.user_id);
        stream.writeBool(this.blocked);
    }
}
