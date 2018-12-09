package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.MessageAction;
import org.telegram.tgnet.TLRPC.Peer;

public class TLRPC$TL_messageService_old2 extends TLRPC$TL_messageService {
    public static int constructor = 495384334;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        boolean z2 = true;
        this.flags = abstractSerializedData.readInt32(z);
        this.unread = (this.flags & 1) != 0;
        this.out = (this.flags & 2) != 0;
        this.mentioned = (this.flags & 16) != 0;
        if ((this.flags & 32) == 0) {
            z2 = false;
        }
        this.media_unread = z2;
        this.id = abstractSerializedData.readInt32(z);
        this.from_id = abstractSerializedData.readInt32(z);
        this.to_id = Peer.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.date = abstractSerializedData.readInt32(z);
        this.action = MessageAction.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.flags |= 256;
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.unread ? this.flags | 1 : this.flags & -2;
        this.flags = this.out ? this.flags | 2 : this.flags & -3;
        this.flags = this.mentioned ? this.flags | 16 : this.flags & -17;
        this.flags = this.media_unread ? this.flags | 32 : this.flags & -33;
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt32(this.id);
        abstractSerializedData.writeInt32(this.from_id);
        this.to_id.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.date);
        this.action.serializeToStream(abstractSerializedData);
    }
}
