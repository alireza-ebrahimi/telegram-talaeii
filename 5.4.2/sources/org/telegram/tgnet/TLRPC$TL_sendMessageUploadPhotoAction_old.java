package org.telegram.tgnet;

public class TLRPC$TL_sendMessageUploadPhotoAction_old extends TLRPC$TL_sendMessageUploadPhotoAction {
    public static int constructor = -1727382502;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
