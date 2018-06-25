package org.telegram.tgnet;

public abstract class TLRPC$InputFileLocation extends TLObject {
    public long access_hash;
    public long id;
    public int local_id;
    public long secret;
    public long volume_id;

    public static TLRPC$InputFileLocation TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$InputFileLocation result = null;
        switch (constructor) {
            case -182231723:
                result = new TLRPC$TL_inputEncryptedFileLocation();
                break;
            case 342061462:
                result = new TLRPC$TL_inputFileLocation();
                break;
            case 1313188841:
                result = new TLRPC$TL_inputDocumentFileLocation();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in InputFileLocation", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
