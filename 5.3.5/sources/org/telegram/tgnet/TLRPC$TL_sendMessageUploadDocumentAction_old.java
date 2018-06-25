package org.telegram.tgnet;

public class TLRPC$TL_sendMessageUploadDocumentAction_old extends TLRPC$TL_sendMessageUploadDocumentAction {
    public static int constructor = -1884362354;

    public void readParams(AbstractSerializedData stream, boolean exception) {
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
