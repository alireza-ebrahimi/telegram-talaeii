package org.telegram.tgnet;

public class TLRPC$TL_sendMessageGeoLocationAction extends TLRPC$SendMessageAction {
    public static int constructor = 393186209;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
