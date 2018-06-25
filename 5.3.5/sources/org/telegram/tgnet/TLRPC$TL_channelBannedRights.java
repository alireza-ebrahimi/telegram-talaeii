package org.telegram.tgnet;

public class TLRPC$TL_channelBannedRights extends TLObject {
    public static int constructor = 1489977929;
    public boolean embed_links;
    public int flags;
    public boolean send_games;
    public boolean send_gifs;
    public boolean send_inline;
    public boolean send_media;
    public boolean send_messages;
    public boolean send_stickers;
    public int until_date;
    public boolean view_messages;

    public static TLRPC$TL_channelBannedRights TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_channelBannedRights result = new TLRPC$TL_channelBannedRights();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_channelBannedRights", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        boolean z2 = true;
        this.flags = stream.readInt32(exception);
        this.view_messages = (this.flags & 1) != 0;
        if ((this.flags & 2) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.send_messages = z;
        if ((this.flags & 4) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.send_media = z;
        if ((this.flags & 8) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.send_stickers = z;
        if ((this.flags & 16) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.send_gifs = z;
        if ((this.flags & 32) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.send_games = z;
        if ((this.flags & 64) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.send_inline = z;
        if ((this.flags & 128) == 0) {
            z2 = false;
        }
        this.embed_links = z2;
        this.until_date = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.view_messages ? this.flags | 1 : this.flags & -2;
        this.flags = this.send_messages ? this.flags | 2 : this.flags & -3;
        this.flags = this.send_media ? this.flags | 4 : this.flags & -5;
        this.flags = this.send_stickers ? this.flags | 8 : this.flags & -9;
        this.flags = this.send_gifs ? this.flags | 16 : this.flags & -17;
        this.flags = this.send_games ? this.flags | 32 : this.flags & -33;
        this.flags = this.send_inline ? this.flags | 64 : this.flags & -65;
        this.flags = this.embed_links ? this.flags | 128 : this.flags & -129;
        stream.writeInt32(this.flags);
        stream.writeInt32(this.until_date);
    }
}
