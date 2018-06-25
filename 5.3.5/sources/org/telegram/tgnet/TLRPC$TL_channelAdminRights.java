package org.telegram.tgnet;

public class TLRPC$TL_channelAdminRights extends TLObject {
    public static int constructor = 1568467877;
    public boolean add_admins;
    public boolean ban_users;
    public boolean change_info;
    public boolean delete_messages;
    public boolean edit_messages;
    public int flags;
    public boolean invite_link;
    public boolean invite_users;
    public boolean pin_messages;
    public boolean post_messages;

    public static TLRPC$TL_channelAdminRights TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_channelAdminRights result = new TLRPC$TL_channelAdminRights();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_channelAdminRights", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        boolean z2 = true;
        this.flags = stream.readInt32(exception);
        this.change_info = (this.flags & 1) != 0;
        if ((this.flags & 2) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.post_messages = z;
        if ((this.flags & 4) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.edit_messages = z;
        if ((this.flags & 8) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.delete_messages = z;
        if ((this.flags & 16) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.ban_users = z;
        if ((this.flags & 32) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.invite_users = z;
        if ((this.flags & 64) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.invite_link = z;
        if ((this.flags & 128) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.pin_messages = z;
        if ((this.flags & 512) == 0) {
            z2 = false;
        }
        this.add_admins = z2;
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.change_info ? this.flags | 1 : this.flags & -2;
        this.flags = this.post_messages ? this.flags | 2 : this.flags & -3;
        this.flags = this.edit_messages ? this.flags | 4 : this.flags & -5;
        this.flags = this.delete_messages ? this.flags | 8 : this.flags & -9;
        this.flags = this.ban_users ? this.flags | 16 : this.flags & -17;
        this.flags = this.invite_users ? this.flags | 32 : this.flags & -33;
        this.flags = this.invite_link ? this.flags | 64 : this.flags & -65;
        this.flags = this.pin_messages ? this.flags | 128 : this.flags & -129;
        this.flags = this.add_admins ? this.flags | 512 : this.flags & -513;
        stream.writeInt32(this.flags);
    }
}
