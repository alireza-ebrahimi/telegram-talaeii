package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.DecryptedMessageMedia;

public class TLRPC$TL_decryptedMessage_layer17 extends TLRPC$TL_decryptedMessage {
    public static int constructor = 541931640;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.random_id = abstractSerializedData.readInt64(z);
        this.ttl = abstractSerializedData.readInt32(z);
        this.message = abstractSerializedData.readString(z);
        this.media = DecryptedMessageMedia.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.random_id);
        abstractSerializedData.writeInt32(this.ttl);
        abstractSerializedData.writeString(this.message);
        this.media.serializeToStream(abstractSerializedData);
    }
}
