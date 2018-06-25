package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_account_updateUsername extends TLObject {
    public static int constructor = 1040964988;
    public String username;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return User.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.username);
    }
}
