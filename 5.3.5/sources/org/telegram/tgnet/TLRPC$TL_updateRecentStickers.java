package org.telegram.tgnet;

public class TLRPC$TL_updateRecentStickers extends TLRPC$Update {
    public static int constructor = -1706939360;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
