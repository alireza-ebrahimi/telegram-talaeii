package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.ContactLink;

public class TLRPC$TL_updateContactLink extends TLRPC$Update {
    public static int constructor = -1657903163;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.user_id = abstractSerializedData.readInt32(z);
        this.my_link = ContactLink.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.foreign_link = ContactLink.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.user_id);
        this.my_link.serializeToStream(abstractSerializedData);
        this.foreign_link.serializeToStream(abstractSerializedData);
    }
}
