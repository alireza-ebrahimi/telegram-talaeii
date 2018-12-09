package org.telegram.tgnet;

public class TLRPC$TL_help_getAppUpdate extends TLObject {
    public static int constructor = -1372724842;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$help_AppUpdate.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
