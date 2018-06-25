package org.telegram.tgnet;

public class TLRPC$TL_updateSavedGifs extends TLRPC$Update {
    public static int constructor = -1821035490;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
