package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.Bool;
import org.telegram.tgnet.TLRPC.InputUser;

public class TLRPC$TL_contacts_deleteContacts extends TLObject {
    public static int constructor = 1504393374;
    public ArrayList<InputUser> id = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return Bool.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(481674261);
        int size = this.id.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((InputUser) this.id.get(i)).serializeToStream(abstractSerializedData);
        }
    }
}
