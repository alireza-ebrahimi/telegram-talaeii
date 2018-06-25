package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.DocumentAttribute;

public class TLRPC$Document extends TLObject {
    public long access_hash;
    public ArrayList<DocumentAttribute> attributes = new ArrayList();
    public String caption;
    public int date;
    public int dc_id;
    public String file_name;
    public long id;
    public byte[] iv;
    public byte[] key;
    public String mime_type;
    public int size;
    public TLRPC$PhotoSize thumb;
    public int user_id;
    public int version;

    public static TLRPC$Document TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$Document result = null;
        switch (constructor) {
            case -2027738169:
                result = new TLRPC$TL_document();
                break;
            case -1627626714:
                result = new TLRPC$TL_document_old();
                break;
            case -106717361:
                result = new TLRPC$TL_document_layer53();
                break;
            case 922273905:
                result = new TLRPC$TL_documentEmpty();
                break;
            case 1431655766:
                result = new TLRPC$TL_documentEncrypted_old();
                break;
            case 1431655768:
                result = new TLRPC$TL_documentEncrypted();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in Document", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
