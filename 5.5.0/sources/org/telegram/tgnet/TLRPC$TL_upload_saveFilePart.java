package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Bool;

public class TLRPC$TL_upload_saveFilePart extends TLObject {
    public static int constructor = -1291540959;
    public NativeByteBuffer bytes;
    public long file_id;
    public int file_part;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return Bool.TLdeserialize(abstractSerializedData, i, z);
    }

    public void freeResources() {
        if (!this.disableFree && this.bytes != null) {
            this.bytes.reuse();
            this.bytes = null;
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.file_id);
        abstractSerializedData.writeInt32(this.file_part);
        abstractSerializedData.writeByteBuffer(this.bytes);
    }
}
