package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_auth_sendInvites extends TLObject {
    public static int constructor = 1998331287;
    public String message;
    public ArrayList<String> phone_numbers = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(481674261);
        int count = this.phone_numbers.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            stream.writeString((String) this.phone_numbers.get(a));
        }
        stream.writeString(this.message);
    }
}
