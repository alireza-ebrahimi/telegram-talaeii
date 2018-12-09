package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.MessagesFilter;

public class TLRPC$TL_inputMessagesFilterVoice extends MessagesFilter {
    public static int constructor = 1358283666;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
