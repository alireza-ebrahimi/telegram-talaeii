package org.telegram.tgnet;

public class TLRPC$TL_groupCallParticipant extends TLRPC$GroupCallParticipant {
    public static int constructor = 1486730135;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.readonly = (this.flags & 1) != 0;
        this.user_id = stream.readInt32(exception);
        this.date = stream.readInt32(exception);
        this.member_tag_hash = stream.readByteArray(exception);
        this.streams = stream.readByteArray(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.readonly ? this.flags | 1 : this.flags & -2;
        stream.writeInt32(this.flags);
        stream.writeInt32(this.user_id);
        stream.writeInt32(this.date);
        stream.writeByteArray(this.member_tag_hash);
        stream.writeByteArray(this.streams);
    }
}
