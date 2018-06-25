package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.ContactLink;
import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_contacts_link extends TLObject {
    public static int constructor = 986597452;
    public ContactLink foreign_link;
    public ContactLink my_link;
    public User user;

    public static TLRPC$TL_contacts_link TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_contacts_link tLRPC$TL_contacts_link = new TLRPC$TL_contacts_link();
            tLRPC$TL_contacts_link.readParams(abstractSerializedData, z);
            return tLRPC$TL_contacts_link;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_contacts_link", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.my_link = ContactLink.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.foreign_link = ContactLink.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.user = User.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.my_link.serializeToStream(abstractSerializedData);
        this.foreign_link.serializeToStream(abstractSerializedData);
        this.user.serializeToStream(abstractSerializedData);
    }
}
