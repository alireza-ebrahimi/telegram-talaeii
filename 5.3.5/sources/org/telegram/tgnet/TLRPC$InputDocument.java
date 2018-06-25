package org.telegram.tgnet;

public abstract class TLRPC$InputDocument extends TLObject {
    public long access_hash;
    public long id;

    public static TLRPC$InputDocument TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$InputDocument result = null;
        switch (constructor) {
            case 410618194:
                result = new TLRPC$TL_inputDocument();
                break;
            case 1928391342:
                result = new TLRPC$TL_inputDocumentEmpty();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in InputDocument", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
