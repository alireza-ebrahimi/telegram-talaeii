package org.telegram.tgnet;

public abstract class TLRPC$InputEncryptedFile extends TLObject {
    public long access_hash;
    public long id;
    public int key_fingerprint;
    public String md5_checksum;
    public int parts;

    public static TLRPC$InputEncryptedFile TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$InputEncryptedFile result = null;
        switch (constructor) {
            case 406307684:
                result = new TLRPC$TL_inputEncryptedFileEmpty();
                break;
            case 767652808:
                result = new TLRPC$TL_inputEncryptedFileBigUploaded();
                break;
            case 1511503333:
                result = new TLRPC$TL_inputEncryptedFile();
                break;
            case 1690108678:
                result = new TLRPC$TL_inputEncryptedFileUploaded();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in InputEncryptedFile", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
