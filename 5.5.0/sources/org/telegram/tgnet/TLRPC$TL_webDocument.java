package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.DocumentAttribute;

public class TLRPC$TL_webDocument extends TLObject {
    public static int constructor = -971322408;
    public long access_hash;
    public ArrayList<DocumentAttribute> attributes = new ArrayList();
    public int dc_id;
    public String mime_type;
    public int size;
    public String url;

    public static TLRPC$TL_webDocument TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_webDocument tLRPC$TL_webDocument = new TLRPC$TL_webDocument();
            tLRPC$TL_webDocument.readParams(abstractSerializedData, z);
            return tLRPC$TL_webDocument;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_webDocument", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.url = abstractSerializedData.readString(z);
        this.access_hash = abstractSerializedData.readInt64(z);
        this.size = abstractSerializedData.readInt32(z);
        this.mime_type = abstractSerializedData.readString(z);
        int readInt32;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            readInt32 = abstractSerializedData.readInt32(z);
            while (i < readInt32) {
                DocumentAttribute TLdeserialize = DocumentAttribute.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.attributes.add(TLdeserialize);
                    i++;
                } else {
                    return;
                }
            }
            this.dc_id = abstractSerializedData.readInt32(z);
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.url);
        abstractSerializedData.writeInt64(this.access_hash);
        abstractSerializedData.writeInt32(this.size);
        abstractSerializedData.writeString(this.mime_type);
        abstractSerializedData.writeInt32(481674261);
        int size = this.attributes.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((DocumentAttribute) this.attributes.get(i)).serializeToStream(abstractSerializedData);
        }
        abstractSerializedData.writeInt32(this.dc_id);
    }
}
