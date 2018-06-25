package org.telegram.tgnet;

public abstract class TLRPC$messages_SentEncryptedMessage extends TLObject {
    public int date;
    public TLRPC$EncryptedFile file;

    public static TLRPC$messages_SentEncryptedMessage TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$messages_SentEncryptedMessage result = null;
        switch (constructor) {
            case -1802240206:
                result = new TLRPC$TL_messages_sentEncryptedFile();
                break;
            case 1443858741:
                result = new TLRPC$TL_messages_sentEncryptedMessage();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in messages_SentEncryptedMessage", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
