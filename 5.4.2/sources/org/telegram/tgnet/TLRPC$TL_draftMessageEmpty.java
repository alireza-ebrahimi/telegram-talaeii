package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.DraftMessage;

public class TLRPC$TL_draftMessageEmpty extends DraftMessage {
    public static int constructor = -1169445179;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
