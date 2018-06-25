package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.EncryptedFile;

public abstract class TLRPC$messages_SentEncryptedMessage extends TLObject {
    public int date;
    public EncryptedFile file;

    public static TLRPC$messages_SentEncryptedMessage TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$messages_SentEncryptedMessage tLRPC$messages_SentEncryptedMessage = null;
        switch (i) {
            case -1802240206:
                tLRPC$messages_SentEncryptedMessage = new TLRPC$TL_messages_sentEncryptedFile();
                break;
            case 1443858741:
                tLRPC$messages_SentEncryptedMessage = new TLRPC$TL_messages_sentEncryptedMessage();
                break;
        }
        if (tLRPC$messages_SentEncryptedMessage == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in messages_SentEncryptedMessage", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$messages_SentEncryptedMessage != null) {
            tLRPC$messages_SentEncryptedMessage.readParams(abstractSerializedData, z);
        }
        return tLRPC$messages_SentEncryptedMessage;
    }
}
