package org.telegram.tgnet;

public class TLRPC$TL_updatePtsChanged extends TLRPC$Update {
    public static int constructor = 861169551;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
