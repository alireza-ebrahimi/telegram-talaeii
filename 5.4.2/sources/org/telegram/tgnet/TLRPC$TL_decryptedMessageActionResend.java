package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.DecryptedMessageAction;

public class TLRPC$TL_decryptedMessageActionResend extends DecryptedMessageAction {
    public static int constructor = 1360072880;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.start_seq_no = abstractSerializedData.readInt32(z);
        this.end_seq_no = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.start_seq_no);
        abstractSerializedData.writeInt32(this.end_seq_no);
    }
}
