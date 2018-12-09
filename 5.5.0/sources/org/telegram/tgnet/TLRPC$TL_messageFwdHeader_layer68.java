package org.telegram.tgnet;

public class TLRPC$TL_messageFwdHeader_layer68 extends TLRPC$TL_messageFwdHeader {
    public static int constructor = -947462709;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        if ((this.flags & 1) != 0) {
            this.from_id = abstractSerializedData.readInt32(z);
        }
        this.date = abstractSerializedData.readInt32(z);
        if ((this.flags & 2) != 0) {
            this.channel_id = abstractSerializedData.readInt32(z);
        }
        if ((this.flags & 4) != 0) {
            this.channel_post = abstractSerializedData.readInt32(z);
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.flags);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeInt32(this.from_id);
        }
        abstractSerializedData.writeInt32(this.date);
        if ((this.flags & 2) != 0) {
            abstractSerializedData.writeInt32(this.channel_id);
        }
        if ((this.flags & 4) != 0) {
            abstractSerializedData.writeInt32(this.channel_post);
        }
    }
}
