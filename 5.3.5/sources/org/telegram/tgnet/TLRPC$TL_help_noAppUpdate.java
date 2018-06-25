package org.telegram.tgnet;

public class TLRPC$TL_help_noAppUpdate extends TLRPC$help_AppUpdate {
    public static int constructor = -1000708810;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
