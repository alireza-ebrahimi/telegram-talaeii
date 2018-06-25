package org.telegram.tgnet;

public class TLRPC$TL_upload_cdnFile extends TLRPC$upload_CdnFile {
    public static int constructor = -1449145777;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.bytes = stream.readByteBuffer(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeByteBuffer(this.bytes);
    }

    public void freeResources() {
        if (!this.disableFree && this.bytes != null) {
            this.bytes.reuse();
            this.bytes = null;
        }
    }
}
