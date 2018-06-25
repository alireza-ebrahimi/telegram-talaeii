package org.telegram.tgnet;

public class TLRPC$TL_contactStatus extends TLObject {
    public static int constructor = -748155807;
    public TLRPC$UserStatus status;
    public int user_id;

    public static TLRPC$TL_contactStatus TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_contactStatus result = new TLRPC$TL_contactStatus();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_contactStatus", new Object[]{Integer.valueOf(constructor)}));
        }
    }

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
