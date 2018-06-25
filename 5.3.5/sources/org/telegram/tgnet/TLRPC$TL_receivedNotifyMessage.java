package org.telegram.tgnet;

public class TLRPC$TL_receivedNotifyMessage extends TLObject {
    public static int constructor = -1551583367;
    public int flags;
    public int id;

    public static TLRPC$TL_receivedNotifyMessage TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_receivedNotifyMessage result = new TLRPC$TL_receivedNotifyMessage();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_receivedNotifyMessage", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt32(exception);
        this.flags = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.id);
        stream.writeInt32(this.flags);
    }
}
