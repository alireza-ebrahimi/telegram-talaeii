package org.telegram.tgnet;

public class TLRPC$TL_messages_messageEmpty extends TLObject {
    public static int constructor = 1062078024;

    public static TLRPC$TL_messages_messageEmpty TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_messages_messageEmpty tLRPC$TL_messages_messageEmpty = new TLRPC$TL_messages_messageEmpty();
            tLRPC$TL_messages_messageEmpty.readParams(abstractSerializedData, z);
            return tLRPC$TL_messages_messageEmpty;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_messages_messageEmpty", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
