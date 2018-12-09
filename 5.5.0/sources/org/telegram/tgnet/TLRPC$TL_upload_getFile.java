package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputFileLocation;

public class TLRPC$TL_upload_getFile extends TLObject {
    public static int constructor = -475607115;
    public int limit;
    public InputFileLocation location;
    public int offset;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$upload_File.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.location.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.offset);
        abstractSerializedData.writeInt32(this.limit);
    }
}
