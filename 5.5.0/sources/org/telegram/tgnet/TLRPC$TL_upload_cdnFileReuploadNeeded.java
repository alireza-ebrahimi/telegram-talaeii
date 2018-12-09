package org.telegram.tgnet;

public class TLRPC$TL_upload_cdnFileReuploadNeeded extends TLRPC$upload_CdnFile {
    public static int constructor = -290921362;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.request_token = abstractSerializedData.readByteArray(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeByteArray(this.request_token);
    }
}
