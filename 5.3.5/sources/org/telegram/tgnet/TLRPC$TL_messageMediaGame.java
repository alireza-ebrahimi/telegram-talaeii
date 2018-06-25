package org.telegram.tgnet;

public class TLRPC$TL_messageMediaGame extends TLRPC$MessageMedia {
    public static int constructor = -38694904;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.game = TLRPC$TL_game.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.game.serializeToStream(stream);
    }
}
