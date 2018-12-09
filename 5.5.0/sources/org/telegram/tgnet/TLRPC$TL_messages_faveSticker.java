package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Bool;
import org.telegram.tgnet.TLRPC.InputDocument;

public class TLRPC$TL_messages_faveSticker extends TLObject {
    public static int constructor = -1174420133;
    public InputDocument id;
    public boolean unfave;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return Bool.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.id.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeBool(this.unfave);
    }
}
