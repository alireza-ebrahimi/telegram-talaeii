package org.telegram.tgnet;

public class TLRPC$TL_channelAdminLogEventsFilter extends TLObject {
    public static int constructor = -368018716;
    public boolean ban;
    public boolean delete;
    public boolean demote;
    public boolean edit;
    public int flags;
    public boolean info;
    public boolean invite;
    public boolean join;
    public boolean kick;
    public boolean leave;
    public boolean pinned;
    public boolean promote;
    public boolean settings;
    public boolean unban;
    public boolean unkick;

    public static TLRPC$TL_channelAdminLogEventsFilter TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_channelAdminLogEventsFilter result = new TLRPC$TL_channelAdminLogEventsFilter();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_channelAdminLogEventsFilter", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        boolean z2 = true;
        this.flags = stream.readInt32(exception);
        this.join = (this.flags & 1) != 0;
        if ((this.flags & 2) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.leave = z;
        if ((this.flags & 4) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.invite = z;
        if ((this.flags & 8) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.ban = z;
        if ((this.flags & 16) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.unban = z;
        if ((this.flags & 32) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.kick = z;
        if ((this.flags & 64) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.unkick = z;
        if ((this.flags & 128) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.promote = z;
        if ((this.flags & 256) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.demote = z;
        if ((this.flags & 512) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.info = z;
        if ((this.flags & 1024) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.settings = z;
        if ((this.flags & 2048) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.pinned = z;
        if ((this.flags & 4096) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.edit = z;
        if ((this.flags & 8192) == 0) {
            z2 = false;
        }
        this.delete = z2;
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.join ? this.flags | 1 : this.flags & -2;
        this.flags = this.leave ? this.flags | 2 : this.flags & -3;
        this.flags = this.invite ? this.flags | 4 : this.flags & -5;
        this.flags = this.ban ? this.flags | 8 : this.flags & -9;
        this.flags = this.unban ? this.flags | 16 : this.flags & -17;
        this.flags = this.kick ? this.flags | 32 : this.flags & -33;
        this.flags = this.unkick ? this.flags | 64 : this.flags & -65;
        this.flags = this.promote ? this.flags | 128 : this.flags & -129;
        this.flags = this.demote ? this.flags | 256 : this.flags & -257;
        this.flags = this.info ? this.flags | 512 : this.flags & -513;
        this.flags = this.settings ? this.flags | 1024 : this.flags & -1025;
        this.flags = this.pinned ? this.flags | 2048 : this.flags & -2049;
        this.flags = this.edit ? this.flags | 4096 : this.flags & -4097;
        this.flags = this.delete ? this.flags | 8192 : this.flags & -8193;
        stream.writeInt32(this.flags);
    }
}
