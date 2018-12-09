package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.MessagesFilter;

public class TLRPC$TL_inputMessagesFilterRoundVoice extends MessagesFilter {
    public static int constructor = 2054952868;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
