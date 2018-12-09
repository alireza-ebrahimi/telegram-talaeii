package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.BotInfo;
import org.telegram.tgnet.TLRPC.PeerNotifySettings;
import org.telegram.tgnet.TLRPC.Photo;
import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_userFull extends TLObject {
    public static int constructor = 253890367;
    public String about;
    public boolean blocked;
    public BotInfo bot_info;
    public int common_chats_count;
    public int flags;
    public TLRPC$TL_contacts_link link;
    public PeerNotifySettings notify_settings;
    public boolean phone_calls_available;
    public boolean phone_calls_private;
    public Photo profile_photo;
    public User user;

    public static TLRPC$TL_userFull TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_userFull tLRPC$TL_userFull = new TLRPC$TL_userFull();
            tLRPC$TL_userFull.readParams(abstractSerializedData, z);
            return tLRPC$TL_userFull;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_userFull", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        boolean z2 = true;
        this.flags = abstractSerializedData.readInt32(z);
        this.blocked = (this.flags & 1) != 0;
        this.phone_calls_available = (this.flags & 16) != 0;
        if ((this.flags & 32) == 0) {
            z2 = false;
        }
        this.phone_calls_private = z2;
        this.user = User.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        if ((this.flags & 2) != 0) {
            this.about = abstractSerializedData.readString(z);
        }
        this.link = TLRPC$TL_contacts_link.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        if ((this.flags & 4) != 0) {
            this.profile_photo = Photo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        this.notify_settings = PeerNotifySettings.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        if ((this.flags & 8) != 0) {
            this.bot_info = BotInfo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        this.common_chats_count = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.blocked ? this.flags | 1 : this.flags & -2;
        this.flags = this.phone_calls_available ? this.flags | 16 : this.flags & -17;
        this.flags = this.phone_calls_private ? this.flags | 32 : this.flags & -33;
        abstractSerializedData.writeInt32(this.flags);
        this.user.serializeToStream(abstractSerializedData);
        if ((this.flags & 2) != 0) {
            abstractSerializedData.writeString(this.about);
        }
        this.link.serializeToStream(abstractSerializedData);
        if ((this.flags & 4) != 0) {
            this.profile_photo.serializeToStream(abstractSerializedData);
        }
        this.notify_settings.serializeToStream(abstractSerializedData);
        if ((this.flags & 8) != 0) {
            this.bot_info.serializeToStream(abstractSerializedData);
        }
        abstractSerializedData.writeInt32(this.common_chats_count);
    }
}
