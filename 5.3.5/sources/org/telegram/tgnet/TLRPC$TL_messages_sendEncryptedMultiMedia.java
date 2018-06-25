package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_messages_sendEncryptedMultiMedia extends TLObject {
    public static int constructor = -892679478;
    public ArrayList<TLRPC$InputEncryptedFile> files = new ArrayList();
    public ArrayList<TLRPC$TL_decryptedMessage> messages = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$messages_SentEncryptedMessage.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
    }

    public void freeResources() {
    }
}
