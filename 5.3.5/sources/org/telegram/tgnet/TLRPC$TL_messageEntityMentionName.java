package org.telegram.tgnet;

public class TLRPC$TL_messageEntityMentionName extends TLRPC$MessageEntity {
    public static int constructor = 892193368;
    public int user_id;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.offset = stream.readInt32(exception);
        this.length = stream.readInt32(exception);
        this.user_id = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.offset);
        stream.writeInt32(this.length);
        stream.writeInt32(this.user_id);
    }
}
