package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.InputPeer;
import org.telegram.tgnet.TLRPC.MessageEntity;
import org.telegram.tgnet.TLRPC.ReplyMarkup;

public class TLRPC$TL_messages_sendMessage extends TLObject {
    public static int constructor = -91733382;
    public boolean background;
    public boolean clear_draft;
    public ArrayList<MessageEntity> entities = new ArrayList();
    public int flags;
    public String message;
    public boolean no_webpage;
    public InputPeer peer;
    public long random_id;
    public ReplyMarkup reply_markup;
    public int reply_to_msg_id;
    public boolean silent;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.no_webpage ? this.flags | 2 : this.flags & -3;
        this.flags = this.silent ? this.flags | 32 : this.flags & -33;
        this.flags = this.background ? this.flags | 64 : this.flags & -65;
        this.flags = this.clear_draft ? this.flags | 128 : this.flags & -129;
        abstractSerializedData.writeInt32(this.flags);
        this.peer.serializeToStream(abstractSerializedData);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeInt32(this.reply_to_msg_id);
        }
        abstractSerializedData.writeString(this.message);
        abstractSerializedData.writeInt64(this.random_id);
        if ((this.flags & 4) != 0) {
            this.reply_markup.serializeToStream(abstractSerializedData);
        }
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
