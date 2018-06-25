package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.DecryptedMessage;

public class TLRPC$TL_decryptedMessageLayer extends TLObject {
    public static int constructor = 467867529;
    public int in_seq_no;
    public int layer;
    public DecryptedMessage message;
    public int out_seq_no;
    public byte[] random_bytes;

    public static TLRPC$TL_decryptedMessageLayer TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_decryptedMessageLayer tLRPC$TL_decryptedMessageLayer = new TLRPC$TL_decryptedMessageLayer();
            tLRPC$TL_decryptedMessageLayer.readParams(abstractSerializedData, z);
            return tLRPC$TL_decryptedMessageLayer;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_decryptedMessageLayer", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.random_bytes = abstractSerializedData.readByteArray(z);
        this.layer = abstractSerializedData.readInt32(z);
        this.in_seq_no = abstractSerializedData.readInt32(z);
        this.out_seq_no = abstractSerializedData.readInt32(z);
        this.message = DecryptedMessage.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeByteArray(this.random_bytes);
        abstractSerializedData.writeInt32(this.layer);
        abstractSerializedData.writeInt32(this.in_seq_no);
        abstractSerializedData.writeInt32(this.out_seq_no);
        this.message.serializeToStream(abstractSerializedData);
    }
}
