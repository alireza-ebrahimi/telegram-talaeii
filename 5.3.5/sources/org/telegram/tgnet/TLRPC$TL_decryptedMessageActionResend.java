package org.telegram.tgnet;

public class TLRPC$TL_decryptedMessageActionResend extends TLRPC$DecryptedMessageAction {
    public static int constructor = 1360072880;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.start_seq_no = stream.readInt32(exception);
        this.end_seq_no = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.start_seq_no);
        stream.writeInt32(this.end_seq_no);
    }
}
