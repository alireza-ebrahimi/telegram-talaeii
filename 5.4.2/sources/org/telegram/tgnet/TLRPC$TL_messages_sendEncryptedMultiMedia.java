package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.InputEncryptedFile;

public class TLRPC$TL_messages_sendEncryptedMultiMedia extends TLObject {
    public static int constructor = -892679478;
    public ArrayList<InputEncryptedFile> files = new ArrayList();
    public ArrayList<TLRPC$TL_decryptedMessage> messages = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$messages_SentEncryptedMessage.TLdeserialize(abstractSerializedData, i, z);
    }

    public void freeResources() {
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
    }
}
