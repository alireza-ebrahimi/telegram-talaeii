package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.DocumentAttribute;

public class TLRPC$TL_inputWebDocument extends TLObject {
    public static int constructor = -1678949555;
    public ArrayList<DocumentAttribute> attributes = new ArrayList();
    public String mime_type;
    public int size;
    public String url;

    public static TLRPC$TL_inputWebDocument TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_inputWebDocument tLRPC$TL_inputWebDocument = new TLRPC$TL_inputWebDocument();
            tLRPC$TL_inputWebDocument.readParams(abstractSerializedData, z);
            return tLRPC$TL_inputWebDocument;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_inputWebDocument", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.url = abstractSerializedData.readString(z);
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
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.url);
        abstractSerializedData.writeInt32(this.size);
        abstractSerializedData.writeString(this.mime_type);
        abstractSerializedData.writeInt32(481674261);
        int size = this.attributes.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((DocumentAttribute) this.attributes.get(i)).serializeToStream(abstractSerializedData);
        }
    }
}
