package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_messages_sendMultiMedia extends TLObject {
    public static int constructor = 546656559;
    public boolean background;
    public boolean clear_draft;
    public int flags;
    public ArrayList<TLRPC$TL_inputSingleMedia> multi_media = new ArrayList();
    public TLRPC$InputPeer peer;
    public int reply_to_msg_id;
    public boolean silent;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        int i;
        stream.writeInt32(constructor);
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
        stream.writeInt32(481674261);
        int count = this.multi_media.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$TL_inputSingleMedia) this.multi_media.get(a)).serializeToStream(stream);
        }
    }
}
