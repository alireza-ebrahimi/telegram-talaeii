package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_contacts_link extends TLObject {
    public static int constructor = 986597452;
    public TLRPC$ContactLink foreign_link;
    public TLRPC$ContactLink my_link;
    public User user;

    public static TLRPC$TL_contacts_link TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_contacts_link result = new TLRPC$TL_contacts_link();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_contacts_link", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.my_link = TLRPC$ContactLink.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.foreign_link = TLRPC$ContactLink.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.user = User.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.my_link.serializeToStream(stream);
        this.foreign_link.serializeToStream(stream);
        this.user.serializeToStream(stream);
    }
}
