package org.telegram.tgnet;

public class TLRPC$TL_messageActionGameScore extends TLRPC$MessageAction {
    public static int constructor = -1834538890;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.game_id = stream.readInt64(exception);
        this.score = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.game_id);
        stream.writeInt32(this.score);
    }
}
