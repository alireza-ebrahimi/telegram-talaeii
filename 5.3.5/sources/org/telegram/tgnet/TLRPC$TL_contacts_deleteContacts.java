package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_contacts_deleteContacts extends TLObject {
    public static int constructor = 1504393374;
    public ArrayList<TLRPC$InputUser> id = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(481674261);
        int count = this.id.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$InputUser) this.id.get(a)).serializeToStream(stream);
        }
    }
}
