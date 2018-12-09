package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.InputPeer;

public class TLRPC$TL_messages_sendMultiMedia extends TLObject {
    public static int constructor = 546656559;
    public boolean background;
    public boolean clear_draft;
    public int flags;
    public ArrayList<TLRPC$TL_inputSingleMedia> multi_media = new ArrayList();
    public InputPeer peer;
    public int reply_to_msg_id;
    public boolean silent;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.silent ? this.flags | 32 : this.flags & -33;
        this.flags = this.background ? this.flags | 64 : this.flags & -65;
        this.flags = this.clear_draft ? this.flags | 128 : this.flags & -129;
        abstractSerializedData.writeInt32(this.flags);
        this.peer.serializeToStream(abstractSerializedData);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeInt32(this.reply_to_msg_id);
        }
        abstractSerializedData.writeInt32(481674261);
        int size = this.multi_media.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((TLRPC$TL_inputSingleMedia) this.multi_media.get(i)).serializeToStream(abstractSerializedData);
        }
    }
}
