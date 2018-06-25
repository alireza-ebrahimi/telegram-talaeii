package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_userFull extends TLObject {
    public static int constructor = 253890367;
    public String about;
    public boolean blocked;
    public TLRPC$BotInfo bot_info;
    public int common_chats_count;
    public int flags;
    public TLRPC$TL_contacts_link link;
    public TLRPC$PeerNotifySettings notify_settings;
    public boolean phone_calls_available;
    public boolean phone_calls_private;
    public TLRPC$Photo profile_photo;
    public User user;

    public static TLRPC$TL_userFull TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_userFull result = new TLRPC$TL_userFull();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_userFull", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        boolean z2 = true;
        this.flags = stream.readInt32(exception);
        this.blocked = (this.flags & 1) != 0;
        if ((this.flags & 16) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.phone_calls_available = z;
        if ((this.flags & 32) == 0) {
            z2 = false;
        }
        this.phone_calls_private = z2;
        this.user = User.TLdeserialize(stream, stream.readInt32(exception), exception);
        if ((this.flags & 2) != 0) {
            this.about = stream.readString(exception);
        }
        this.link = TLRPC$TL_contacts_link.TLdeserialize(stream, stream.readInt32(exception), exception);
        if ((this.flags & 4) != 0) {
            this.profile_photo = TLRPC$Photo.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
        this.notify_settings = TLRPC$PeerNotifySettings.TLdeserialize(stream, stream.readInt32(exception), exception);
        if ((this.flags & 8) != 0) {
            this.bot_info = TLRPC$BotInfo.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
        this.common_chats_count = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.blocked ? this.flags | 1 : this.flags & -2;
        this.flags = this.phone_calls_available ? this.flags | 16 : this.flags & -17;
        this.flags = this.phone_calls_private ? this.flags | 32 : this.flags & -33;
        stream.writeInt32(this.flags);
        this.user.serializeToStream(stream);
        if ((this.flags & 2) != 0) {
            stream.writeString(this.about);
        }
        this.link.serializeToStream(stream);
        if ((this.flags & 4) != 0) {
            this.profile_photo.serializeToStream(stream);
        }
        this.notify_settings.serializeToStream(stream);
        if ((this.flags & 8) != 0) {
            this.bot_info.serializeToStream(stream);
        }
        stream.writeInt32(this.common_chats_count);
    }
}
