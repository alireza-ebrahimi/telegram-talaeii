package org.telegram.tgnet;

public class TLRPC$TL_messageForwarded_old extends TLRPC$TL_messageForwarded_old2 {
    public static int constructor = 99903492;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt32(exception);
        this.fwd_from = new TLRPC$TL_messageFwdHeader();
        this.fwd_from.from_id = stream.readInt32(exception);
        TLRPC$MessageFwdHeader tLRPC$MessageFwdHeader = this.fwd_from;
        tLRPC$MessageFwdHeader.flags |= 1;
        this.fwd_from.date = stream.readInt32(exception);
        this.from_id = stream.readInt32(exception);
        this.to_id = TLRPC$Peer.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.out = stream.readBool(exception);
        this.unread = stream.readBool(exception);
        this.flags |= 772;
        this.date = stream.readInt32(exception);
        this.message = stream.readString(exception);
        this.media = TLRPC$MessageMedia.TLdeserialize(stream, stream.readInt32(exception), exception);
        if (this.id < 0) {
            this.fwd_msg_id = stream.readInt32(exception);
        }
        if (this.id < 0 || !(this.media == null || (this.media instanceof TLRPC$TL_messageMediaEmpty) || (this.media instanceof TLRPC$TL_messageMediaWebPage) || this.message == null || this.message.length() == 0 || !this.message.startsWith("-1"))) {
            this.attachPath = stream.readString(exception);
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.id);
        stream.writeInt32(this.fwd_from.from_id);
        stream.writeInt32(this.fwd_from.date);
        stream.writeInt32(this.from_id);
        this.to_id.serializeToStream(stream);
        stream.writeBool(this.out);
        stream.writeBool(this.unread);
        stream.writeInt32(this.date);
        stream.writeString(this.message);
        this.media.serializeToStream(stream);
        if (this.id < 0) {
            stream.writeInt32(this.fwd_msg_id);
        }
        stream.writeString(this.attachPath);
    }
}
