package org.telegram.tgnet;

public class TLRPC$TL_channelFull_old extends TLRPC$TL_channelFull {
    public static int constructor = -88925533;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.can_view_participants = (this.flags & 8) != 0;
        this.id = stream.readInt32(exception);
        this.about = stream.readString(exception);
        if ((this.flags & 1) != 0) {
            this.participants_count = stream.readInt32(exception);
        }
        if ((this.flags & 2) != 0) {
            this.admins_count = stream.readInt32(exception);
        }
        if ((this.flags & 4) != 0) {
            this.kicked_count = stream.readInt32(exception);
        }
        this.read_inbox_max_id = stream.readInt32(exception);
        this.unread_count = stream.readInt32(exception);
        this.unread_important_count = stream.readInt32(exception);
        this.chat_photo = TLRPC$Photo.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.notify_settings = TLRPC$PeerNotifySettings.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.exported_invite = TLRPC$ExportedChatInvite.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.can_view_participants ? this.flags | 8 : this.flags & -9;
        stream.writeInt32(this.flags);
        stream.writeInt32(this.id);
        stream.writeString(this.about);
        if ((this.flags & 1) != 0) {
            stream.writeInt32(this.participants_count);
        }
        if ((this.flags & 2) != 0) {
            stream.writeInt32(this.admins_count);
        }
        if ((this.flags & 4) != 0) {
            stream.writeInt32(this.kicked_count);
        }
        stream.writeInt32(this.read_inbox_max_id);
        stream.writeInt32(this.unread_count);
        stream.writeInt32(this.unread_important_count);
        this.chat_photo.serializeToStream(stream);
        this.notify_settings.serializeToStream(stream);
        this.exported_invite.serializeToStream(stream);
    }
}
