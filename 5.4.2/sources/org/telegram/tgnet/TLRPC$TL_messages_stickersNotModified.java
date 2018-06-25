package org.telegram.tgnet;

public class TLRPC$TL_messages_stickersNotModified extends TLRPC$messages_Stickers {
    public static int constructor = -244016606;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
