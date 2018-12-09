package org.telegram.tgnet;

public class TLRPC$TL_upload_cdnFile extends TLRPC$upload_CdnFile {
    public static int constructor = -1449145777;

    public void freeResources() {
        if (!this.disableFree && this.bytes != null) {
            this.bytes.reuse();
            this.bytes = null;
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.bytes = abstractSerializedData.readByteBuffer(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeByteBuffer(this.bytes);
    }
}
