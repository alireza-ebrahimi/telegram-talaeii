package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.MessagesFilter;

public class TLRPC$TL_inputMessagesFilterRoundVideo extends MessagesFilter {
    public static int constructor = -1253451181;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
