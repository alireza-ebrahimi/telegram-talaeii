package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_messages_sendMessage extends TLObject {
    public static int constructor = -91733382;
    public boolean background;
    public boolean clear_draft;
    public ArrayList<TLRPC$MessageEntity> entities = new ArrayList();
    public int flags;
    public String message;
    public boolean no_webpage;
    public TLRPC$InputPeer peer;
    public long random_id;
    public TLRPC$ReplyMarkup reply_markup;
    public int reply_to_msg_id;
    public boolean silent;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        int i;
        stream.writeInt32(constructor);
        this.flags = this.no_webpage ? this.flags | 2 : this.flags & -3;
        this.flags = this.silent ? this.flags | 32 : this.flags & -33;
        this.flags = this.background ? this.flags | 64 : this.flags & -65;
        if (this.clear_draft) {
            i = this.flags | 128;
        } else {
            i = this.flags & -129;
        }
        this.flags = i;
        stream.writeInt32(this.flags);
        this.peer.serializeToStream(stream);
        if ((this.flags & 1) != 0) {
            stream.writeInt32(this.reply_to_msg_id);
        }
        stream.writeString(this.message);
        stream.writeInt64(this.random_id);
        if ((this.flags & 4) != 0) {
            this.reply_markup.serializeToStream(stream);
        }
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
