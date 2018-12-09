package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_messages_getAllChats extends TLObject {
    public static int constructor = -341307408;
    public ArrayList<Integer> except_ids = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$messages_Chats.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(481674261);
        int size = this.except_ids.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            abstractSerializedData.writeInt32(((Integer) this.except_ids.get(i)).intValue());
        }
    }
}
