package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_account_updateProfile extends TLObject {
    public static int constructor = 2018596725;
    public String about;
    public String first_name;
    public int flags;
    public String last_name;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return User.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        if ((this.flags & 1) != 0) {
            stream.writeString(this.first_name);
        }
        if ((this.flags & 2) != 0) {
            stream.writeString(this.last_name);
        }
        if ((this.flags & 4) != 0) {
            stream.writeString(this.about);
        }
    }
}
