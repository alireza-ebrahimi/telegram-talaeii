package org.telegram.tgnet;

public class TLRPC$TL_highScore extends TLObject {
    public static int constructor = 1493171408;
    public int pos;
    public int score;
    public int user_id;

    public static TLRPC$TL_highScore TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_highScore result = new TLRPC$TL_highScore();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_highScore", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.pos = stream.readInt32(exception);
        this.user_id = stream.readInt32(exception);
        this.score = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.pos);
        stream.writeInt32(this.user_id);
        stream.writeInt32(this.score);
    }
}
