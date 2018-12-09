package org.telegram.tgnet;

public class TLRPC$TL_phone_getCallConfig extends TLObject {
    public static int constructor = 1430593449;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_dataJSON.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
