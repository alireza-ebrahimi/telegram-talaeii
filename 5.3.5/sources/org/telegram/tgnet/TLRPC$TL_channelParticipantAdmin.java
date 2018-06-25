package org.telegram.tgnet;

public class TLRPC$TL_channelParticipantAdmin extends TLRPC$ChannelParticipant {
    public static int constructor = -1473271656;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.can_edit = (this.flags & 1) != 0;
        this.user_id = stream.readInt32(exception);
        this.inviter_id = stream.readInt32(exception);
        this.promoted_by = stream.readInt32(exception);
        this.date = stream.readInt32(exception);
        this.admin_rights = TLRPC$TL_channelAdminRights.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.can_edit ? this.flags | 1 : this.flags & -2;
        stream.writeInt32(this.flags);
        stream.writeInt32(this.user_id);
        stream.writeInt32(this.inviter_id);
        stream.writeInt32(this.promoted_by);
        stream.writeInt32(this.date);
        this.admin_rights.serializeToStream(stream);
    }
}
