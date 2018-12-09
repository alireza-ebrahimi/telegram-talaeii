package org.telegram.tgnet;

public class TLRPC$TL_updateChannelWebPage extends TLRPC$Update {
    public static int constructor = 1081547008;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.channel_id = abstractSerializedData.readInt32(z);
        this.webpage = TLRPC$WebPage.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.pts = abstractSerializedData.readInt32(z);
        this.pts_count = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.channel_id);
        this.webpage.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.pts);
        abstractSerializedData.writeInt32(this.pts_count);
    }
}
