package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Document;

public class TLRPC$TL_messages_getDocumentByHash extends TLObject {
    public static int constructor = 864953444;
    public String mime_type;
    public byte[] sha256;
    public int size;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return Document.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeByteArray(this.sha256);
        abstractSerializedData.writeInt32(this.size);
        abstractSerializedData.writeString(this.mime_type);
    }
}
