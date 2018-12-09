package org.telegram.tgnet;

public class TLRPC$TL_upload_getCdnFile extends TLObject {
    public static int constructor = 536919235;
    public byte[] file_token;
    public int limit;
    public int offset;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$upload_CdnFile.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeByteArray(this.file_token);
        abstractSerializedData.writeInt32(this.offset);
        abstractSerializedData.writeInt32(this.limit);
    }
}
