package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.InputMedia;
import org.telegram.tgnet.TLRPC.InputUser;

public class TLRPC$TL_messages_sendBroadcast extends TLObject {
    public static int constructor = -1082919718;
    public ArrayList<InputUser> contacts = new ArrayList();
    public InputMedia media;
    public String message;
    public ArrayList<Long> random_id = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        int i;
        int i2 = 0;
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(481674261);
        int size = this.contacts.size();
        abstractSerializedData.writeInt32(size);
        for (i = 0; i < size; i++) {
            ((InputUser) this.contacts.get(i)).serializeToStream(abstractSerializedData);
        }
        abstractSerializedData.writeInt32(481674261);
        i = this.random_id.size();
        abstractSerializedData.writeInt32(i);
        while (i2 < i) {
            abstractSerializedData.writeInt64(((Long) this.random_id.get(i2)).longValue());
            i2++;
        }
        abstractSerializedData.writeString(this.message);
        this.media.serializeToStream(abstractSerializedData);
    }
}
