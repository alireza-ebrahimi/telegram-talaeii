package org.telegram.tgnet;

public class TLRPC$TL_channel_layer72 extends TLRPC$TL_channel {
    public static int constructor = 213142300;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        boolean z2 = true;
        this.flags = stream.readInt32(exception);
        this.creator = (this.flags & 1) != 0;
        if ((this.flags & 4) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.left = z;
        if ((this.flags & 32) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.broadcast = z;
        if ((this.flags & 128) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.verified = z;
        if ((this.flags & 256) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.megagroup = z;
        if ((this.flags & 512) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.restricted = z;
        if ((this.flags & 1024) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.democracy = z;
        if ((this.flags & 2048) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.signatures = z;
        if ((this.flags & 4096) == 0) {
            z2 = false;
        }
        this.min = z2;
        this.id = stream.readInt32(exception);
        if ((this.flags & 8192) != 0) {
            this.access_hash = stream.readInt64(exception);
        }
        this.title = stream.readString(exception);
        if ((this.flags & 64) != 0) {
            this.username = stream.readString(exception);
        }
        this.photo = TLRPC$ChatPhoto.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.date = stream.readInt32(exception);
        this.version = stream.readInt32(exception);
        if ((this.flags & 512) != 0) {
            this.restriction_reason = stream.readString(exception);
        }
        if ((this.flags & 16384) != 0) {
            this.admin_rights = TLRPC$TL_channelAdminRights.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
        if ((this.flags & 32768) != 0) {
            this.banned_rights = TLRPC$TL_channelBannedRights.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.creator ? this.flags | 1 : this.flags & -2;
        this.flags = this.kicked ? this.flags | 2 : this.flags & -3;
        this.flags = this.left ? this.flags | 4 : this.flags & -5;
        this.flags = this.broadcast ? this.flags | 32 : this.flags & -33;
        this.flags = this.verified ? this.flags | 128 : this.flags & -129;
        this.flags = this.megagroup ? this.flags | 256 : this.flags & -257;
        this.flags = this.restricted ? this.flags | 512 : this.flags & -513;
        this.flags = this.democracy ? this.flags | 1024 : this.flags & -1025;
        this.flags = this.signatures ? this.flags | 2048 : this.flags & -2049;
        this.flags = this.min ? this.flags | 4096 : this.flags & -4097;
        stream.writeInt32(this.flags);
        stream.writeInt32(this.id);
        if ((this.flags & 8192) != 0) {
            stream.writeInt64(this.access_hash);
        }
        stream.writeString(this.title);
        if ((this.flags & 64) != 0) {
            stream.writeString(this.username);
        }
        this.photo.serializeToStream(stream);
        stream.writeInt32(this.date);
        stream.writeInt32(this.version);
        if ((this.flags & 512) != 0) {
            stream.writeString(this.restriction_reason);
        }
        if ((this.flags & 16384) != 0) {
            this.admin_rights.serializeToStream(stream);
        }
        if ((this.flags & 32768) != 0) {
            this.banned_rights.serializeToStream(stream);
        }
    }
}
