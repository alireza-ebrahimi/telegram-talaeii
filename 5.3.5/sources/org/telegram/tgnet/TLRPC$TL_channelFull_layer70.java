package org.telegram.tgnet;

public class TLRPC$TL_channelFull_layer70 extends TLRPC$TL_channelFull {
    public static int constructor = -1781833897;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        this.flags = stream.readInt32(exception);
        if ((this.flags & 8) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.can_view_participants = z;
        if ((this.flags & 64) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.can_set_username = z;
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
        if ((this.flags & 4) != 0) {
            this.banned_count = stream.readInt32(exception);
        }
        this.read_inbox_max_id = stream.readInt32(exception);
        this.read_outbox_max_id = stream.readInt32(exception);
        this.unread_count = stream.readInt32(exception);
        this.chat_photo = TLRPC$Photo.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.notify_settings = TLRPC$PeerNotifySettings.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.exported_invite = TLRPC$ExportedChatInvite.TLdeserialize(stream, stream.readInt32(exception), exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$BotInfo object = TLRPC$BotInfo.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.bot_info.add(object);
                    a++;
                } else {
                    return;
                }
            }
            if ((this.flags & 16) != 0) {
                this.migrated_from_chat_id = stream.readInt32(exception);
            }
            if ((this.flags & 16) != 0) {
                this.migrated_from_max_id = stream.readInt32(exception);
            }
            if ((this.flags & 32) != 0) {
                this.pinned_msg_id = stream.readInt32(exception);
            }
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        int i;
        stream.writeInt32(constructor);
        this.flags = this.can_view_participants ? this.flags | 8 : this.flags & -9;
        if (this.can_set_username) {
            i = this.flags | 64;
        } else {
            i = this.flags & -65;
        }
        this.flags = i;
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
        if ((this.flags & 4) != 0) {
            stream.writeInt32(this.banned_count);
        }
        stream.writeInt32(this.read_inbox_max_id);
        stream.writeInt32(this.read_outbox_max_id);
        stream.writeInt32(this.unread_count);
        this.chat_photo.serializeToStream(stream);
        this.notify_settings.serializeToStream(stream);
        this.exported_invite.serializeToStream(stream);
        stream.writeInt32(481674261);
        int count = this.bot_info.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$BotInfo) this.bot_info.get(a)).serializeToStream(stream);
        }
        if ((this.flags & 16) != 0) {
            stream.writeInt32(this.migrated_from_chat_id);
        }
        if ((this.flags & 16) != 0) {
            stream.writeInt32(this.migrated_from_max_id);
        }
        if ((this.flags & 32) != 0) {
            stream.writeInt32(this.pinned_msg_id);
        }
    }
}
