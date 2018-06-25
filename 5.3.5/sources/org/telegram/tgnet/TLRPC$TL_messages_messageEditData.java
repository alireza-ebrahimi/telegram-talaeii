package org.telegram.tgnet;

public class TLRPC$TL_messages_messageEditData extends TLObject {
    public static int constructor = 649453030;
    public boolean caption;
    public int flags;

    public static TLRPC$TL_messages_messageEditData TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_messages_messageEditData result = new TLRPC$TL_messages_messageEditData();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_messages_messageEditData", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.caption = (this.flags & 1) != 0;
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.caption ? this.flags | 1 : this.flags & -2;
        stream.writeInt32(this.flags);
    }
}
