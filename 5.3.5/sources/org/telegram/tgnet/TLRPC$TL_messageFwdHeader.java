package org.telegram.tgnet;

public class TLRPC$TL_messageFwdHeader extends TLRPC$MessageFwdHeader {
    public static int constructor = 1436466797;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        if ((this.flags & 1) != 0) {
            this.from_id = stream.readInt32(exception);
        }
        this.date = stream.readInt32(exception);
        if ((this.flags & 2) != 0) {
            this.channel_id = stream.readInt32(exception);
        }
        if ((this.flags & 4) != 0) {
            this.channel_post = stream.readInt32(exception);
        }
        if ((this.flags & 8) != 0) {
            this.post_author = stream.readString(exception);
        }
        if ((this.flags & 16) != 0) {
            this.saved_from_peer = TLRPC$Peer.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
        if ((this.flags & 16) != 0) {
            this.saved_from_msg_id = stream.readInt32(exception);
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        if ((this.flags & 1) != 0) {
            stream.writeInt32(this.from_id);
        }
        stream.writeInt32(this.date);
        if ((this.flags & 2) != 0) {
            stream.writeInt32(this.channel_id);
        }
        if ((this.flags & 4) != 0) {
            stream.writeInt32(this.channel_post);
        }
        if ((this.flags & 8) != 0) {
            stream.writeString(this.post_author);
        }
        if ((this.flags & 16) != 0) {
            this.saved_from_peer.serializeToStream(stream);
        }
        if ((this.flags & 16) != 0) {
            stream.writeInt32(this.saved_from_msg_id);
        }
    }
}
