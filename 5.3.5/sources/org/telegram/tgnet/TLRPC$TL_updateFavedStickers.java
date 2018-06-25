package org.telegram.tgnet;

public class TLRPC$TL_updateFavedStickers extends TLRPC$Update {
    public static int constructor = -451831443;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
