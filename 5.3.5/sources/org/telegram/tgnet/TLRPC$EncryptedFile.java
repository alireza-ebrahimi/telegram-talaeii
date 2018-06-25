package org.telegram.tgnet;

public abstract class TLRPC$EncryptedFile extends TLObject {
    public long access_hash;
    public int dc_id;
    public long id;
    public int key_fingerprint;
    public int size;

    public static TLRPC$EncryptedFile TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$EncryptedFile result = null;
        switch (constructor) {
            case -1038136962:
                result = new TLRPC$TL_encryptedFileEmpty();
                break;
            case 1248893260:
                result = new TLRPC$TL_encryptedFile();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in EncryptedFile", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
