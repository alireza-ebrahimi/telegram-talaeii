package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.MessageFwdHeader;
import org.telegram.tgnet.TLRPC.MessageMedia;
import org.telegram.tgnet.TLRPC.Peer;

public class TLRPC$TL_messageForwarded_old2 extends Message {
    public static int constructor = -1553471722;

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
        this.fwd_from = new TLRPC$TL_messageFwdHeader();
        this.fwd_from.from_id = abstractSerializedData.readInt32(z);
        MessageFwdHeader messageFwdHeader = this.fwd_from;
        messageFwdHeader.flags |= 1;
        this.fwd_from.date = abstractSerializedData.readInt32(z);
        this.from_id = abstractSerializedData.readInt32(z);
        this.to_id = Peer.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.date = abstractSerializedData.readInt32(z);
        this.message = abstractSerializedData.readString(z);
        this.flags |= 772;
        this.media = MessageMedia.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        if (this.id < 0) {
            this.fwd_msg_id = abstractSerializedData.readInt32(z);
        }
        if (this.id < 0 || !(this.media == null || (this.media instanceof TLRPC$TL_messageMediaEmpty) || this.message == null || this.message.length() == 0 || !this.message.startsWith("-1"))) {
            this.attachPath = abstractSerializedData.readString(z);
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.unread ? this.flags | 1 : this.flags & -2;
        this.flags = this.out ? this.flags | 2 : this.flags & -3;
        this.flags = this.mentioned ? this.flags | 16 : this.flags & -17;
        this.flags = this.media_unread ? this.flags | 32 : this.flags & -33;
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt32(this.id);
        abstractSerializedData.writeInt32(this.fwd_from.from_id);
        abstractSerializedData.writeInt32(this.fwd_from.date);
        abstractSerializedData.writeInt32(this.from_id);
        this.to_id.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.date);
        abstractSerializedData.writeString(this.message);
        this.media.serializeToStream(abstractSerializedData);
        if (this.id < 0) {
            abstractSerializedData.writeInt32(this.fwd_msg_id);
        }
        abstractSerializedData.writeString(this.attachPath);
    }
}
