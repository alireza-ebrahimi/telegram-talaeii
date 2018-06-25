package org.telegram.tgnet;

public class TLRPC$TL_messages_messageEmpty extends TLObject {
    public static int constructor = 1062078024;

    public static TLRPC$TL_messages_messageEmpty TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_messages_messageEmpty result = new TLRPC$TL_messages_messageEmpty();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_messages_messageEmpty", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
