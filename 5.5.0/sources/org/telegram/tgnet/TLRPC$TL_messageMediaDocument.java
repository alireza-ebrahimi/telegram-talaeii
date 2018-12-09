package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.MessageMedia;

public class TLRPC$TL_messageMediaDocument extends MessageMedia {
    public static int constructor = 2084836563;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        if ((this.flags & 1) != 0) {
            this.document = Document.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        } else {
            this.document = new TLRPC$TL_documentEmpty();
        }
        if ((this.flags & 2) != 0) {
            this.caption = abstractSerializedData.readString(z);
        }
        if ((this.flags & 4) != 0) {
            this.ttl_seconds = abstractSerializedData.readInt32(z);
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.flags);
        if ((this.flags & 1) != 0) {
            this.document.serializeToStream(abstractSerializedData);
        }
        if ((this.flags & 2) != 0) {
            abstractSerializedData.writeString(this.caption);
        }
        if ((this.flags & 4) != 0) {
            abstractSerializedData.writeInt32(this.ttl_seconds);
        }
    }
}
