package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.MessageFwdHeader;
import org.telegram.tgnet.TLRPC.Peer;

public class TLRPC$TL_messageFwdHeader extends MessageFwdHeader {
    public static int constructor = 1436466797;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        if ((this.flags & 1) != 0) {
            this.from_id = abstractSerializedData.readInt32(z);
        }
        this.date = abstractSerializedData.readInt32(z);
        if ((this.flags & 2) != 0) {
            this.channel_id = abstractSerializedData.readInt32(z);
        }
        if ((this.flags & 4) != 0) {
            this.channel_post = abstractSerializedData.readInt32(z);
        }
        if ((this.flags & 8) != 0) {
            this.post_author = abstractSerializedData.readString(z);
        }
        if ((this.flags & 16) != 0) {
            this.saved_from_peer = Peer.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        if ((this.flags & 16) != 0) {
            this.saved_from_msg_id = abstractSerializedData.readInt32(z);
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.flags);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeInt32(this.from_id);
        }
        abstractSerializedData.writeInt32(this.date);
        if ((this.flags & 2) != 0) {
            abstractSerializedData.writeInt32(this.channel_id);
        }
        if ((this.flags & 4) != 0) {
            abstractSerializedData.writeInt32(this.channel_post);
        }
        if ((this.flags & 8) != 0) {
            abstractSerializedData.writeString(this.post_author);
        }
        if ((this.flags & 16) != 0) {
            this.saved_from_peer.serializeToStream(abstractSerializedData);
        }
        if ((this.flags & 16) != 0) {
            abstractSerializedData.writeInt32(this.saved_from_msg_id);
        }
    }
}
