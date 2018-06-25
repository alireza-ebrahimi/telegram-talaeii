package org.telegram.tgnet;

public class TLRPC$TL_channelParticipantBanned extends TLRPC$ChannelParticipant {
    public static int constructor = 573315206;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.left = (this.flags & 1) != 0;
        this.user_id = stream.readInt32(exception);
        this.kicked_by = stream.readInt32(exception);
        this.date = stream.readInt32(exception);
        this.banned_rights = TLRPC$TL_channelBannedRights.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.left ? this.flags | 1 : this.flags & -2;
        stream.writeInt32(this.flags);
        stream.writeInt32(this.user_id);
        stream.writeInt32(this.kicked_by);
        stream.writeInt32(this.date);
        this.banned_rights.serializeToStream(stream);
    }
}
