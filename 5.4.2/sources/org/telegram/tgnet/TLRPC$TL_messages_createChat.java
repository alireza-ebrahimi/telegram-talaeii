package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.InputUser;

public class TLRPC$TL_messages_createChat extends TLObject {
    public static int constructor = 164303470;
    public String title;
    public ArrayList<InputUser> users = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(481674261);
        int size = this.users.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((InputUser) this.users.get(i)).serializeToStream(abstractSerializedData);
        }
        abstractSerializedData.writeString(this.title);
    }
}
