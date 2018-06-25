package org.telegram.tgnet;

public class TLRPC$TL_botInfoEmpty_layer48 extends TLRPC$TL_botInfo {
    public static int constructor = -1154598962;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
