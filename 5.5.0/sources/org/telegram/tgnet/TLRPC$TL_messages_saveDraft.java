package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.Bool;
import org.telegram.tgnet.TLRPC.InputPeer;
import org.telegram.tgnet.TLRPC.MessageEntity;

public class TLRPC$TL_messages_saveDraft extends TLObject {
    public static int constructor = -1137057461;
    public ArrayList<MessageEntity> entities = new ArrayList();
    public int flags;
    public String message;
    public boolean no_webpage;
    public InputPeer peer;
    public int reply_to_msg_id;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return Bool.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.no_webpage ? this.flags | 2 : this.flags & -3;
        abstractSerializedData.writeInt32(this.flags);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeInt32(this.reply_to_msg_id);
        }
        this.peer.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeString(this.message);
        if ((this.flags & 8) != 0) {
            abstractSerializedData.writeInt32(481674261);
            int size = this.entities.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                ((MessageEntity) this.entities.get(i)).serializeToStream(abstractSerializedData);
            }
        }
    }
}
