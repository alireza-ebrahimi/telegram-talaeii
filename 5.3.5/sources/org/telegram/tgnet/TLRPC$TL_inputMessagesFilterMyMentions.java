package org.telegram.tgnet;

public class TLRPC$TL_inputMessagesFilterMyMentions extends TLRPC$MessagesFilter {
    public static int constructor = -1040652646;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
