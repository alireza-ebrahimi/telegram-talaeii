package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_messages_saveDraft extends TLObject {
    public static int constructor = -1137057461;
    public ArrayList<TLRPC$MessageEntity> entities = new ArrayList();
    public int flags;
    public String message;
    public boolean no_webpage;
    public TLRPC$InputPeer peer;
    public int reply_to_msg_id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        int i;
        stream.writeInt32(constructor);
        if (this.no_webpage) {
            i = this.flags | 2;
        } else {
            i = this.flags & -3;
        }
        this.flags = i;
        stream.writeInt32(this.flags);
        if ((this.flags & 1) != 0) {
            stream.writeInt32(this.reply_to_msg_id);
        }
        this.peer.serializeToStream(stream);
        stream.writeString(this.message);
        if ((this.flags & 8) != 0) {
            stream.writeInt32(481674261);
            int count = this.entities.size();
            stream.writeInt32(count);
            for (int a = 0; a < count; a++) {
                ((TLRPC$MessageEntity) this.entities.get(a)).serializeToStream(stream);
            }
        }
    }
}
