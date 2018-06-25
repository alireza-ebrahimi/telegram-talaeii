package org.telegram.tgnet;

public class TLRPC$TL_highScore extends TLObject {
    public static int constructor = 1493171408;
    public int pos;
    public int score;
    public int user_id;

    public static TLRPC$TL_highScore TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_highScore tLRPC$TL_highScore = new TLRPC$TL_highScore();
            tLRPC$TL_highScore.readParams(abstractSerializedData, z);
            return tLRPC$TL_highScore;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_highScore", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.pos = abstractSerializedData.readInt32(z);
        this.user_id = abstractSerializedData.readInt32(z);
        this.score = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.pos);
        abstractSerializedData.writeInt32(this.user_id);
        abstractSerializedData.writeInt32(this.score);
    }
}
