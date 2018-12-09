package org.telegram.tgnet;

public class TLRPC$TL_sendMessageUploadDocumentAction_old extends TLRPC$TL_sendMessageUploadDocumentAction {
    public static int constructor = -1884362354;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
