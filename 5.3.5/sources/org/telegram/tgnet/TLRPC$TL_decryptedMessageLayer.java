package org.telegram.tgnet;

public class TLRPC$TL_decryptedMessageLayer extends TLObject {
    public static int constructor = 467867529;
    public int in_seq_no;
    public int layer;
    public TLRPC$DecryptedMessage message;
    public int out_seq_no;
    public byte[] random_bytes;

    public static TLRPC$TL_decryptedMessageLayer TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_decryptedMessageLayer result = new TLRPC$TL_decryptedMessageLayer();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_decryptedMessageLayer", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.random_bytes = stream.readByteArray(exception);
        this.layer = stream.readInt32(exception);
        this.in_seq_no = stream.readInt32(exception);
        this.out_seq_no = stream.readInt32(exception);
        this.message = TLRPC$DecryptedMessage.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeByteArray(this.random_bytes);
        stream.writeInt32(this.layer);
        stream.writeInt32(this.in_seq_no);
        stream.writeInt32(this.out_seq_no);
        this.message.serializeToStream(stream);
    }
}
